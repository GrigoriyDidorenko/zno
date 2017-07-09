$('#fullpage').fullpage({
    sectionsColor: ['#2980b9', '#c73145', '#5f47a2', '#cc6f2b', '#cc6f2b'],
    anchors: ['welcome', 'pass-test', 'get-plan', 'start-test', 'mobile-app'],
    menu: '#menu'
});

$('.mob_menu').click(function () {
    $('.menu').animate({ right: '0' }, 300).click(function () {
        $('.menu').animate({ right: '-230px' }, 300);
    });
});
$('.more_social').click(function () {
    $('.socials').fadeToggle(300);
});
function loop(a) {
    $(''+a+'').animate({'margin-bottom': 5}, {
        duration: 300,
        complete: function() {
            $(a).animate({'margin-bottom': 0}, {
                duration: 300,
                complete: loop(a)});
        }}
    );
}
loop('.first-page');
loop('.second-page');
loop('.third-page');

$('.collapse').collapse();

$(window).scroll(function() {
    if($(window).scrollTop() >= 200){
        $('.test_navigation').addClass('fixed');
    } else{
        $('.test_navigation').removeClass('fixed');
    }
});