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

var testResultUrl;

jQuery.ajax({
    type: "GET",
    url: "/loginStatus",
    dataType: "json",
    contentType: "application/json; charset=utf-8",
    error: function(XMLHttpRequest, textStatus, errorThrown){
        console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
    },
    success: function(data){
        console.log(data);
        if(data.loggedIn == true){

            testResultUrl = '/user/result';

            if($('main').hasClass('home_page')){
                window.location.href = "subjects.html";
            };
            $('.header_registr, .header_login').css('display', 'none');

            $('.header_user').css('display', 'block').on('mouseover', function () {
                $('.header_header-info').fadeIn();
            }).on('mouseleave', function () {
                $('.header_header-info').fadeOut();
            });

            $('.user_bell').css('display', 'block').click(function () {

                $('.user_bell-block').fadeIn();

                $('.bell_subject').click(function () {
                    var subjectName = $(this).find('p').text();
                    $('#bell_subject .modal-title').text(subjectName);
                });


                jQuery.ajax({
                    type: "GET",
                    url: "/user/failed/notification",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
                    },
                    success: function (data) {
                        console.log(data);
                    }
                });

            });

            $.each(data, function (key, val) {
                if(key == 'username'){
                    $('.header-info-username').text(val);
                }
            });
        } else{
            testResultUrl = '/api/result';
        }
    }
});

$('#fullpage').fullpage({
    sectionsColor: ['#2980b9', '#c73145', '#5f47a2', '#009688', '#cc6f2b'],
    anchors: ['welcome', 'pass-test', 'get-plan', 'start-test', 'mobile-app'],
    menu: '#menu'
});

$('.mob_menu').click(function () {
    $('.menu').animate({right: '0'}, 300).click(function () {
        $('.menu').animate({right: '-230px'}, 300);
    });
});

$('.more_social').click(function () {
    $('.socials').fadeToggle(300);
});

function loop(a) {
    $('' + a + '').animate({'margin-bottom': 5}, {
            duration: 300,
            complete: function () {
                $(a).animate({'margin-bottom': 0}, {
                    duration: 300,
                    complete: loop(a)
                });
            }
        }
    );
}
loop('.first-page');
loop('.second-page');
loop('.third-page');

$('.collapse').collapse();

$(window).scroll(function () {
    if ($(window).scrollTop() >= 200) {
        $('.test_navigation').addClass('fixed');
    } else {
        $('.test_navigation').removeClass('fixed');
    }

});

$('.menu .registration').click(function () {
    if($('#modal_signup').length){
        console.log('yes');
        $('#modal_signup').modal('toggle');
    }
    $('#email').focus();
});


$('#registration').click(function () {
    $("form[name='registration']").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 5
            }
        },
        messages: {
            password: {
                required: "Будь ласка, вкажіть пароль",
                minlength: "Пароль має бути не менше 5 символів"
            },
            email: "Будь ласка, вкажіть валідний email"
        },
        submitHandler: function() {
            var email = $('#email').val();
            var password = $('#password').val();
            var newUserCredentials = {"email": email, "password": password};

            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "/registration",
                data: JSON.stringify(newUserCredentials),
                success: function () {
                    $('.registration_error').text('');
                    $('#user_email').text(email);
                    if($('#modal_signup').length){
                        $('#modal_signup').modal('toggle');
                    }
                    $('#registration_confirm').modal('toggle');
                    $('#email').val('');
                    $('#password').val('');
                },
                error: function (response) {
                    $('.registration_error').text(response.responseText);
                }
            })
        }
    });

});

$('#login').click(function () {
    $("form[name='loginForm']").validate({
        rules: {
            email: {
                required: true,
                email: true
            },
            password: {
                required: true,
                minlength: 5
            }
        },
        messages: {
            password: {
                required: "Будь ласка, вкажіть пароль",
                minlength: "Пароль має бути не менше 5 символів"
            },
            email: "Будь ласка, вкажіть валідний email"
        },
        submitHandler: function() {
            var username = $('#loginEmail').val();
            var password = $('#loginPassword').val();
            var userCredentials = {"email": username, "password": password};
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "/login",
                data: JSON.stringify(userCredentials),
                success: function () {
                    window.location.href = "subjects.html";
                },
                error: function (exception) {
                    $('.login_error').text(exception.responseText);
                }
            })
        }
    });
});

$('#lostPass').click(function () {
    $('#signin').modal('toggle');

    var username;
    $('#lost_email-sent').click(function () {

        username = $('#lost-Email').val();

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "/lostPassword",
            data: JSON.stringify({"username":username}),
            dataType: "json",
            success: function () {
                $('#lost_password .modal-body').html('<p>На пошту <span id="user_email">username</span> надіслано повідомлення з паролем.</p>')
            },
            error: function (exception) {
                $('.login_error').text(exception.responseText);
            }
        })
    });
});
if ($('main').hasClass('statistics_page')) {
    jQuery.ajax({
        type: "GET",
        url: "/user/statistics",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {

            console.log(json);

            var avrgDuration;
            var avrgMark;
            var numOfFailedQuestions;
            var subjectName;

            var duration;
            var mark;
            var testNumOfFailedQuestions;
            var testName;

            $.each(json, function (key, val) {
                $.each(val, function (subject, data) {
                    switch (subject){
                        case 'avrgDuration':
                            avrgDuration = data;
                        break;
                        case 'avrgMark':
                            avrgMark = data;
                        break;
                        case 'numOfFailedQuestions':
                            numOfFailedQuestions = data;
                        break;
                        case 'subjectName':
                            subjectName = data;
                        break;
                        case 'testStatistics':
                            $.each(data, function (tests, values) {
                                $.each(values, function (test, value) {
                                    switch (test) {
                                        case 'duration':
                                            duration = value;
                                            break;
                                        case 'mark':
                                            mark = value;
                                            break;
                                        case 'numOfFailedQuestions':
                                            testNumOfFailedQuestions = value;
                                            break;
                                        case 'testName':
                                            testName = value;
                                            break;
                                    }
                                });
                            });
                        break;

                    }
                });
                $('.statistic_subjects').append('<div class="statistic_subject"><div class="subject_info"><p>'+avrgDuration+'</p>' +
                    '<p>'+avrgMark+'</p><p>'+numOfFailedQuestions+'</p><p>'+subjectName+'</p></div>' +
                    '<div class="test_info"><div class="statistic_subject-test"><p>'+duration+'</p><p>'+mark+'</p>' +
                    '<p>'+testNumOfFailedQuestions+'</p><p>'+testName+'</p>' +
                    '</div></div></div>');
            });


        }
    });
}
if ($('main').hasClass('subject_page')) {

    var subjectId = getUrlParameter('subject');

    jQuery.ajax({
        type: "GET",
        url: "/api/subject",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {

            var imgUrl;
            var currentSubject;
            var subjectUrl;

            $.each(json, function (key, val) {

                imgUrl = val.name.toLowerCase().replace(/ /g, '_');
                imgUrl = 'src/img/' + imgUrl + '.svg';
                if(val.name.toLowerCase() == 'історія україни'){
                    imgUrl = 'src/img/' + val.name.toLowerCase().replace(/ /g, '_') + '.png';
                }

                if(val.id == subjectId){
                    currentSubject = 'subject currentSubject';
                    subjectUrl = '';
                } else{
                    currentSubject = 'subject';
                    subjectUrl = 'href="subject.html?subject=' + val.id + '"';
                }

                $('.subjects_menu').append('<a '+subjectUrl+' class="'+currentSubject+'"><div class="subject_img"><img src="' + imgUrl + '" alt=""></div><p>' + val.name + '</p></a>');

                if (subjectId == val.id) {
                    $('h1').text(val.name);

                    var json = val.articleJSON;

                    var obj = jQuery.parseJSON(json);

                    $('.advice_title').append("Корисні поради з "+obj.name);
                    $('.subject_block-info_text i').append(obj.articleJSON.adviceTeacher);

                    $.each(obj.articleJSON.advices, function(i, val){
                        $('.advices').append('<p>'+val+'</p>');
                    });

                    $.each(obj.articleJSON.additionalResources, function(key, val){
                        $('.advices_link').append('<li><a href="'+val+'" target="_blank">'+key+'</a></li>');
                    });
                }

                var brainshtorm;

                switch(val.name.toLowerCase()){
                    case 'українська мова':
                        brainshtorm = 'з української мови';
                        break;
                    case 'англійська мова':
                        brainshtorm = 'з англійської мови';
                        break;
                    case 'німецька мова':
                        brainshtorm = 'з німецької мови';
                        break;
                    case 'математика':
                        brainshtorm = 'з математики';
                        break;
                    case 'хімія':
                        brainshtorm = 'з хімії';
                        break;
                    case 'фізика':
                        brainshtorm = 'з фізики';
                        break;
                    case 'географія':
                        brainshtorm = 'з георгафії';
                        break;
                    case 'біологія':
                        brainshtorm = 'з біології';
                        break;
                    case 'всесвітня історія':
                        brainshtorm = 'зі всесвітньої історії';
                        break;
                    case 'історія україни':
                        brainshtorm = 'з історії України';
                        break;
                   default:
                       brainshtorm = val.name;
                       break;
                }

                if (subjectId == val.id) {
                    $('.subject_brainshtorm p span').text(brainshtorm);
                }

            });
        }
    });

    jQuery.ajax({
        type: "GET",
        url: "/api/subject/"+subjectId,
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {

            console.log(json);

            var years = [];
            var unique;
            var questions_numb;

            function onlyUnique(value, index, self) {
                return self.indexOf(value) === index;
            }

            $.each(json, function(key, val) {
                years.push(val.year);
            });

            unique = years.filter( onlyUnique );

            $.each(unique, function(i, val) {

                $('.panel-group').append('<div class="panel panel-default">'+
                    '<div class="panel-heading" role="tab" id="heading-'+i+'"><h4 class="panel-title">'+
                    '<a role="button" data-toggle="collapse" data-parent="#accordion" href="#-'+i+'" '+
                    'aria-expanded="true" aria-controls="-'+i+'">Тести за ' + val + ' рік</a></h4></div><div '+
                    'id="collapse-'+i+'" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-'+i+'">'+
                    '<div class="panel-body panel-body_'+i+'">');
                $('.panel').on("click", function(e){
                        var $_target =  $(e.currentTarget);
                        var $_panelBody = $_target.find(".panel-collapse");
                        if($_panelBody){
                            $_panelBody.collapse('toggle')
                        }
                    }).click();
                $.each(json, function(key, value) {
                    if(value.year == val){
                        $('.panel-body_'+i).append('<a data-toggle="modal" data-target="#testinfo_'+key+'" class="test_link" name="'+value.id+'">'+
                            value.name+'</a>');
                    }

                    <!-- Modal testinfo -->
                    $('body').append('<div class="modal fade testinfo" name="'+value.id+'" id="testinfo_'+key+'" role="dialog">'+
                        '<div class="modal-dialog"><div class="modal-content"><div class="modal-header">'+
                        '<button type="button" class="close" data-dismiss="modal">&times;</button><h4 class="modal-title">'+
                        value.name+'</h4></div><div class="modal-body">'+
                        '<div><p>Кількість питань: <span class="questions_numb"></span></p><p>Тривалість тесту: <span>'+value.duration+' хв</span></p></div>'+
                        '<label for="time'+key+'" class="button-remember" name="'+value.id+'"><input type="checkbox" id="time'+key+'" name="time" value="on"'+
                        ' checked><i class="fa" aria-hidden="true"></i>з урахуванням часу</label><a class="btn btn_test">Розпочати</a></div></div></div></div>');
                });
                $('.panel-group').append('</div></div></div>');
            });

            $('body').on('click','.btn_test', function() {
                var inp = $(this).prev().find('input').is(":checked");
                var test = $(this).prev().attr('name');
                $(this).attr("href", "test.html?time="+inp+"&test="+test+"");
            }).on('click','.test_link', function() {
                var modalTest = $(this).attr('name');
                jQuery.ajax({
                    type: "GET",
                    url: "/api/test/" + modalTest,
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (test) {
                        $.each(test, function(key, val) {
                            switch(key) {
                                case 'questions':
                                    questions_numb = val.length;
                                    $('.modal[name="'+modalTest+'"]').find('.questions_numb').text(questions_numb);
                            }})
                    }
                });
            });

            $('.collapse').collapse();

        }
    });
}
if ($('main').hasClass('subjects_page')) {
    jQuery.ajax({
        type: "GET",
        url: "api/subject",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {
            console.log(json);
            var imgUrl;
            $.each(json, function (key, val) {
                imgUrl = val.name.toLowerCase().replace(/ /g, '_');
                imgUrl = 'src/img/' + imgUrl + '.svg';
                if(val.name.toLowerCase() == 'історія україни'){
                    imgUrl = 'src/img/' + val.name.toLowerCase().replace(/ /g, '_') + '.png';
                }
                $('.pageSubjects').append('<a href="subject.html?subject=' + val.id + '" class="subject"><div class="subject_img"><img src='+imgUrl+' alt="' + val.name.toLowerCase() + '"></div><p>' + val.name + '</p></a>');
            });
        }
    });
}
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