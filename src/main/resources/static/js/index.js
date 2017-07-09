$('#fullpage').fullpage({
    sectionsColor: ['#2980b9', '#c73145', '#5f47a2', '#cc6f2b', '#cc6f2b'],
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

$('#registration').click(function () {
    var email = $('#email').val();
    var password = $('#password').val();
    //TODO validate it
    var newUserCredentials = {"email": email, "password": password};

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/registration",
        data: JSON.stringify(newUserCredentials),
        success: function () {
            alert('Success');
        },
        error: function (response) {
            alert('Error appeared: ' + response.responseText);
        }
    })
});

$('#login').click(function () {
    var username = $('#loginEmail').val();
    var password = $('#loginPassword').val();
    //TODO validate it
    var userCredentials = {"email": username, "password": password};

    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "/login",
        data: JSON.stringify(userCredentials),
        success: function () {
            window.location.href = "subject.html";
        },
        error: function (exception) {
            alert('Error appeared: ' + exception.responseText);
        }
    })
});