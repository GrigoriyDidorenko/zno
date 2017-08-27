if ($('main').hasClass('test_page')) {

    var testId = getUrlParameter('test');
    var testTime = getUrlParameter('time');
    var refreshIntervalId;

    var final_testId;

    jQuery.ajax({
        type: "GET",
        url: "/api/test/"+testId,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {

            console.log(json);

            var questionText;
            var questionId;
            var radio;
            var mark;
            var count = 1;

            $.each(json, function(key, val) {
                switch(key){
                    case 'id':
                        final_testId = val;
                    break;
                    case 'subjectName':
                        $('h1').text(val);
                    break;
                    case 'name':
                        $('h2').text(val);
                    break;
                    case 'duration':
                        var Minutes = 60 * val,
                            display = document.querySelector('#timer');
                        if(testTime == 'true'){
                            startTimer(Minutes, display);
                        }

                        function startTimer(duration, display) {
                            var timer = duration, minutes, seconds;
                            refreshIntervalId = setInterval(function () {
                                minutes = parseInt(timer / 60, 10);
                                seconds = parseInt(timer % 60, 10);

                                minutes = minutes < 10 ? "0" + minutes : minutes;
                                seconds = seconds < 10 ? "0" + seconds : seconds;

                                display.textContent = minutes + ":" + seconds;

                                if (--timer < 0) {
                                    clearInterval(refreshIntervalId);
                                    alert('done');
                                }
                            }, 1000);
                        }
                        break;
                    case 'questions':
                        $.each(val, function(i, value) {
                            $.each(value, function(data, text) {
                                if(data == 'questionText'){
                                    questionText = text;
                                } else if(data == 'type') {
                                    switch (text) {
                                        case 'QUESTION_WITH_ONE_CORRECT_ANSWER':
                                            radio = 'radio';
                                        break;
                                        case 'QUESTION_WITH_MULTIPLY_CORRECT_ANSWERS':
                                            radio = 'checkbox';
                                        break;
                                        case 'QUESTION_WITH_SUB_QUESTIONS':
                                            radio = 'subquestions';
                                            break;
                                        case 'QUESTION_OPEN':
                                            radio = 'open';
                                            break;
                                        default:
                                            radio = 'checkbox';
                                    }
                                } else if(data == 'id'){
                                        questionId = text;
                                        $('.test_navigation ul').append('<li><a href="#'+questionId+'" class="scrollto">'+count+'</a></li>');
                                        count++;
                                    }
                            });

                            $('.test_blocks').append('<li name="'+radio+'" id="'+questionId+'"><p class="question">'+questionText+'</p><form class="quest_form quest_form-'+questionId+'"></form></li>');

                             $.each(value, function(data, text) {
                                     if(data == 'type') {
                                         if (text == 'QUESTION_OPEN') {
                                             $('.quest_form-'+questionId+'').append('<textarea></textarea>');
                                         }
                                     }
                                    else if (data == 'answers') {
                                        $.each(text, function(x, y) {
                                            var answerId;
                                            $.each(y, function(keys, answer) {
                                                if(keys == 'id'){
                                                   answerId = answer;
                                                }
                                                if(keys == 'answerText'){
                                                   $('.quest_form-'+questionId+'').append('<label class="button-remember button-remember_'+radio+'" name="'+answerId+'"><input type="'+radio+'" name="question'+questionId+'"><i class="fa" aria-hidden="true"></i>'+answer+'</label>');
                                                }
                                            });
                                            $.each(y, function(keys, answer) {
                                                if(keys == 'mark'){
                                                    if(answer > 0){
                                                        mark = 'true';
                                                    } else{
                                                        mark = 'false';
                                                    }
                                                    $('.quest_form-'+questionId+' label[name='+answerId+']').attr('href', mark);
                                                }
                                            })
                                        })
                                    }
                                    else if (data == 'subQuestions') {
                                         if(text.length){
                                             $('.quest_form-'+questionId+'').append('<div class="sub_questions"></div><div class="sub_answers"></div><div class="sub_variables btn-group"></div>');
                                             $.each(text, function(x, y) {
                                                 var answerId;
                                                 $.each(y, function(keys, answer) {
                                                     if(keys == 'id'){
                                                         answerId = answer;
                                                     }
                                                     if(keys == 'questionText'){
                                                         $('.quest_form-'+questionId+' .sub_variables').append('<select class="selectpicker" name="'+answerId+'"></select>');
                                                         $('.quest_form-'+questionId+' .sub_questions').append('<label class="button-remember button-remember_'+radio+'">'+answer+'</label>');
                                                     }
                                                 })
                                             });
                                             $.each(text, function(x, y) {
                                                 var answerId;
                                                 var subId;
                                                 $.each(y, function(keys, answer) {
                                                     if(keys == 'id'){
                                                         answerId = answer;
                                                     }
                                                     if(keys == 'answers'){
                                                         $.each(answer, function(x, y) {
                                                             $.each(y, function(key, val) {
                                                                 if(key == 'id'){
                                                                     subId = val;
                                                                     $('.quest_form-'+questionId+' .sub_variables .selectpicker').each(function(){
                                                                         $(this).append('<option href="'+mark+'" name="'+subId+'">'+subId+'</option>');
                                                                     });
                                                                 }
                                                                 if(key == 'answerText'){
                                                                     $('.quest_form-'+questionId+' .sub_answers').append('<label class="button-remember button-remember_'+radio+'">'+val+'</label>');
                                                                 }
                                                             });
                                                             $.each(y, function(key, val) {
                                                                 if(key == 'mark'){
                                                                     if(val > 0){
                                                                         mark = 'true';
                                                                     } else{
                                                                         mark = 'false';
                                                                     }
                                                                     subId = val;
                                                                     $('.quest_form-'+questionId+' .sub_variables .selectpicker').each(function(){
                                                                         $('option[name="'+subId+'"]').attr('href', mark);
                                                                     });
                                                                 }
                                                             });
                                                         });

                                                     }
                                                 })
                                             })
                                         }

                                    }
                                });

                            $('.test__block label').click(function () {
                                var liId = $(this).parents('li').attr('id');
                                $('.test_navigation ul li:nth-child(' + liId + ') a').addClass('checked');
                            });

                            $("a.scrollto").click(function () {
                                var elementClick = $(this).attr("href");
                                var destination = $(elementClick).offset().top - 40;
                                jQuery("html:not(:animated),body:not(:animated)").animate({
                                    scrollTop: destination
                                }, 800);
                                return false;
                            });

                            var blockWidth;
                            $('.test_navigation ul').removeClass('fixed');

                            $(window).scroll(function () {
                                blockWidth = $('.test__block').outerWidth();
                                if ($(window).scrollTop() >= 280) {
                                    $('.test_navigation ul').addClass('fixed').css('width', blockWidth);
                                } else {
                                    $('.test_navigation ul').removeClass('fixed');
                                }

                            });

                        });

                    break
                }
            });
        }
    });

    var final_questionId;
    var final_duration;
    var final_answerId;
    var final_answerText;

    var user_answer;

    $('#test-save').click(function () {
        $('#test_finish').modal('toggle');

        final_duration = $('#timer').text();
        final_duration = final_duration.substring(0, final_duration.indexOf(':'));
        clearInterval(refreshIntervalId);

        var userAnswersPerQuestionDTO = [];

        $('.test_blocks li').each(function () {

            final_questionId = $(this).attr('id');

            switch($(this).attr('name')){
                case 'open':

                    userAnswersPerQuestionDTO.push({
                        "answerText": $(this).find('textarea').val(),
                        "id": +final_questionId
                    });

                    if($(this).find('textarea').val() == ''){
                        $(this).addClass('user_ignore');
                    }
                break;
                case 'radio':

                    final_answerId = $(this).find('form label input:checked').parent().attr('name');

                    final_answerText = $(this).find('form label input:checked').parent().text();

                    userAnswersPerQuestionDTO.push({
                        "answerIds": [+final_answerId],
                        "answerText": final_answerText,
                        "id": +final_questionId
                    });

                    user_answer = $(this).find('form label input:checked');
                    if(user_answer.parent().attr('href') == 'true'){
                        user_answer.parent().addClass('user_answer_correct');
                    } else{
                        user_answer.parent().addClass('user_answer_incorrect');
                        $(this).find('form label[href="true"]').addClass('correct_answer');
                    }
                    if(user_answer.length <= 0){
                        $(this).addClass('user_ignore');
                    }
                break;
                case 'checkbox':
                    $.each($(this).find('form label input:checked'), function() {

                        final_answerId = $(this).parent().attr('name');

                        final_answerText = $(this).parent().text();

                        userAnswersPerQuestionDTO.push({
                            "answerIds": [+final_answerId],
                            "answerText": final_answerText,
                            "id": +final_questionId
                        });

                        user_answer = $(this).parent();

                        if(user_answer.attr('href') == 'true'){
                            user_answer.addClass('user_answer_correct');
                        } else{
                            user_answer.addClass('user_answer_incorrect');
                            user_answer.parent().find('label[href="true"]').addClass('correct_answer');
                        }
                    });
                    if($(this).find('form label input:checked').length <= 0){
                        $(this).addClass('user_ignore');
                        $(this).find('form label[href="true"]').addClass('correct_answer');

                    }
                break;
                case 'subquestions':
                    $.each($(this).find('form .sub_variables select'), function() {

                        final_questionId = $(this).attr('name');

                        final_answerId = $(this).find('option:selected').attr('name');

                        final_answerText = $(this).find('option:selected').text();

                        userAnswersPerQuestionDTO.push({
                            "answerIds": [+final_answerId],
                            "answerText": final_answerText,
                            "id": +final_questionId
                        });
                    });
                break;
            }

        });

        var testObject = {
            "duration": +final_duration,
            "id": final_testId,
            "userAnswersPerQuestionDTO": userAnswersPerQuestionDTO
        };

        console.log(JSON.stringify(testObject));

        $.ajax({
            type: "POST",
            url: testResultUrl,
            data: JSON.stringify(testObject),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data, textStatus, request) {
                $('.test_result').text('Ваша оцінка ' + data);
            },
            error: function (request, textStatus, errorThrown) {
                console.log(data);
            }
        });
    })

}