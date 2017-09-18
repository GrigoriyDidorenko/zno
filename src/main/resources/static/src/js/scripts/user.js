jQuery.ajax({
    type: "GET",
    url: "/loginStatus",
    dataType: "json",
    contentType: "application/json; charset=utf-8",
    error: function(XMLHttpRequest){
        console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
    },
    success: function(data){
        console.log(data);
        if(data.loggedIn == true){

            $('.logo').attr('href', '/subjects.html');

            testResultUrl = '/user/result';

            if($('main').hasClass('home_page')){
                window.location.href = "subjects.html";
            }

            $('.header_registr, .header_login').css('display', 'none');

            $('.header_user').css('display', 'block').on('mouseover', function () {
                $('.header_header-info').fadeIn();
            }).on('mouseleave', function () {
                $('.header_header-info').fadeOut();
            });


            jQuery.ajax({
                type: "GET",
                url: "/user/failed/notification",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                error: function (XMLHttpRequest) {
                    console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
                },
                success: function (data) {
                    console.log(data);

                    var failed = data;

                    $('.user_bell-numb').text(failed.length);

                    $.each(failed, function (key, val) {
                        $('.user_bell-block').append('<div class="bell_subject" data-toggle="modal" data-target="#bell_subject"><p name="'+val.id+'">'+val.name+'</p><span>'+val.failedQuestionsAmount+'</span></div>')
                    });

                    $('.bell_subject').click(function () {
                        var subjectName = $(this).find('p').text();
                        $('#test_info .modal-title').text(subjectName);
                        $('#test_info').modal('toggle');
                        $('#test_info .btn').attr('href', 'test.html?failed=true&time=false&test='+$(this).find('p').attr('name'))

                    });

                    if(failed.length !== 0){
                        $('.user_bell').css('display', 'block').click(function (e) {
                            $('.user_bell-block').fadeIn();
                            e.stopPropagation();
                        });

                    }
                }

            });

            $.each(data, function (key, val) {
                if(key == 'username'){
                    $('.header-info-username').text(val);
                }
            });
        } else{
            testResultUrl = '/api/result';
            if($('main').hasClass('statistics_page')){
                window.location.href = "index.html";
            }
        }
    }
});

$( document ).ready(function() {

    $(document).on('click', function (e) {
        $('.user_bell-block').fadeOut();
    });

    $('.menu .registration').click(function () {
        if($('#modal_signup').length){
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
                        var login_error = JSON.parse(exception.responseText);
                        $('.login_error').text(login_error.failReason);
                    }
                })
            }
        });
    });

    $('#lostPass').click(function () {
        $('#signin').modal('toggle');

        var username;
        $('#lost_email-sent').click(function () {

            $('.lost_pass-error').css('display', 'none');

            username = $('#lost-Email').val();

            if(username == ''){
                $('.lost_pass-error').css('display', 'block').text('Вкажіть email');
            } else {
                $.ajax({
                    type: "POST",
                    url: "/resetPassword?email="+username,
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    error: function (XMLHttpRequest) {
                        $('.lost_pass-error').css('display', 'block').text('На вказаний email надіслано новий пароль');
                        console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
                    }
                })
            }
        });
    });

    $('.mob_menu').click(function () {
        $('.menu').animate({right: '0'}, 300).click(function () {
            $('.menu').animate({right: '-230px'}, 300);
        });
    });

});