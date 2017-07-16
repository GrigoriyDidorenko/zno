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

$("a.scrollto").click(function() {
    var elementClick = $(this).attr("href");
    var destination = $(elementClick).offset().top;
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

$('.test__block label').click(function () {
    $(this).unbind();
    var liId = $(this).parents('li').attr('id');
    $(this).parent('form').each('input')
    $('.test_navigation ul li:nth-child('+liId+') a').addClass('checked');
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