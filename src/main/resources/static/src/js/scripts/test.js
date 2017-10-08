$( document ).ready(function() {
    var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

    $(window).scroll(function () {
    if ($(window).scrollTop() >= 200) {
        $('.test_navigation').addClass('fixed');
    } else {
        $('.test_navigation').removeClass('fixed');
    }

});

    $('.collapse').collapse();

    if ($('main').hasClass('test_page')) {

        var testId = getUrlParameter('test');
        var testTime = getUrlParameter('time');
        var failedTest = getUrlParameter('failed');
        var brainstormTest = getUrlParameter('brainstorm');
        var refreshIntervalId;
        var final_testId;
        var testUrl;
        var startTime;

        if(failedTest == 'true'){
            testUrl = "/user/failed/questions/"+testId;
            testResultUrl = '/user/failed/questions';
        } else if(brainstormTest == 'true'){
            testUrl = "/api/brainstorm/"+testId;
        } else{
            testUrl = "/api/test/"+testId;
        }

        jQuery.ajax({
            type: "GET",
            url: testUrl,
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
                            if(json.name == 'Brainstorm'){
                                $('h1').text('Brainstorm');
                            } else{
                                $('h1').text(val);
                            }
                            break;
                        case 'name':
                            if(val == 'Brainstorm'){
                                $('h2').text(json.subjectName);
                            } else{
                                $('h2').text(val);
                            }
                            break;
                        case 'duration':
                            startTime = val;
                            var Minutes = 60 * val,
                                display = document.querySelector('#timer span');
                            if(testTime == 'true'){
                                $('#timer').css('display','block');
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
                                            $('.quest_form-'+questionId+'').append('<ul class="sub_questions"></ul><ul class="sub_answers"></ul><ul class="sub_variables btn-group"></ul>');
                                            $.each(text, function(x, y) {
                                                var answerId;
                                                $.each(y, function(keys, answer) {
                                                    if(keys == 'id'){
                                                        answerId = answer;
                                                    }
                                                    if(keys == 'questionText'){
                                                        $('.quest_form-'+questionId+' .sub_variables').append('<li><select class="selectpicker" name="'+answerId+'"></select></li>');
                                                        $('.quest_form-'+questionId+' .sub_questions').append('<li class="button-remember button-remember_'+radio+'">'+answer+'</li>');
                                                    }
                                                })
                                            });
                                            var alphabet = ['A', 'B', 'C', 'D', 'E', 'F', 'G'];
                                            var alph = 0;
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
                                                                        $(this).append('<option href="'+mark+'" name="'+subId+'">'+alphabet[alph]+'</option>');
                                                                    });
                                                                    alph = alph+1;
                                                                }
                                                                if(key == 'answerText'){
                                                                    $('.quest_form-'+questionId+' .sub_answers').append('<li class="button-remember button-remember_'+radio+'">'+val+'</li>');
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

                            $('.test_blocks label').click(function () {
                                var liId = $(this).parents('li').attr('id');
                                var labelType = $(this).parents('li').attr('name');

                                if(labelType == 'checkbox'){
                                    var checkedInput = $(this).parents('li').find('form').find('label input:checked').length;
                                    if(checkedInput > 0){
                                        $('.test_navigation ul li a[href="#' + liId + '"]').addClass('checked');
                                    } else{
                                        $('.test_navigation ul li a[href="#' + liId + '"]').removeClass('checked');
                                    }
                                } else{
                                    $('.test_navigation ul li a[href="#' + liId + '"]').addClass('checked');
                                }
                            });

                            $('.test_blocks select').change(function () {
                                var liId = $(this).parents('form').parents('li').attr('id');
                                $('.test_navigation ul li a[href="#' + liId + '"]').addClass('checked');
                            });

                            break
                    }
                });
            }
        });

        var final_questionId;
        var final_duration;
        var user_time;
        var final_answerId;
        var final_answerText;

        var user_answer;

        $('#test-save').click(function () {
            $('#test_finish').modal('toggle');
            $('.test-end').css('display', 'none');

            final_duration = startTime;
            user_time = $('#timer span').text();
            user_time = user_time.substring(0, user_time.indexOf(':'));
            final_duration = final_duration - user_time;
            clearInterval(refreshIntervalId);

            var userAnswersPerQuestionDTO = [];

            $('.test_blocks li').each(function () {

                $(this).find("input").prop('disabled', true);

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
                    $('.test_result').fadeIn();

                    if(testResultUrl == '/api/result'){
                        $('.test_result a').css('display','none');
                    }

                    if(data < 140){
                        $('.test_result-img').attr('src', 'src/img/test_result-bad.svg');
                    } else if(data > 140 && data < 170){
                        $('.test_result-img').attr('src', 'src/img/test_result-good.svg');
                    } else{
                        $('.test_result-img').attr('src', 'src/img/test_result.svg');
                    }

                    new JustGage({
                        id: 'test-result',
                        value: data,
                        min: 100,
                        max: 200,
                        symbol: '',
                        pointer: true,
                        pointerOptions: {
                            toplength: 8,
                            bottomlength: -20,
                            bottomwidth: 6,
                            color: '#8e8e93'
                        },
                        gaugeWidthScale: 0.1,
                        counter: true
                    });
                },
                error: function (request, textStatus, errorThrown) {
                    console.log(request);
                }
            });
        })
    }

});