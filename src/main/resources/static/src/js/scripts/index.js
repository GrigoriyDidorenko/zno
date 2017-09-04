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

$(document).on('click', function (e) {
    $('.user_bell-block').fadeOut();
});

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
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
                },
                success: function (data) {
                    console.log(data);

                    var failed = data;

                    $.each(failed, function (key, val) {
                        $('.user_bell-block').append('<div class="bell_subject" data-toggle="modal" data-target="#bell_subject"><p>'+key+'</p><span>'+val+'</span></div>')
                    });

                    $('.bell_subject').click(function () {
                        var subjectName = $(this).find('p').text();
                        $('#bell_subject .modal-title').text(subjectName);
                        $('#bell_subject').modal('toggle');
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
            type: "GET",
            url: "/resetPassword",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            error: function (XMLHttpRequest) {
                console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
            },
            success: function (data) {
                console.log(data);
            }
        })
    });
});