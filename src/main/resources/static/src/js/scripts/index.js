// var getUrlParameter = function getUrlParameter(sParam) {
//     var sPageURL = decodeURIComponent(window.location.search.substring(1)),
//         sURLVariables = sPageURL.split('&'),
//         sParameterName,
//         i;
//
//     for (i = 0; i < sURLVariables.length; i++) {
//         sParameterName = sURLVariables[i].split('=');
//
//         if (sParameterName[0] === sParam) {
//             return sParameterName[1] === undefined ? true : sParameterName[1];
//         }
//     }
// };
//
// var testResultUrl;

$('#fullpage').fullpage({
    sectionsColor: ['#2980b9', '#c73145', '#5f47a2', '#009688', '#cc6f2b'],
    anchors: ['welcome', 'pass-test', 'get-plan', 'start-test', 'mobile-app'],
    menu: '#menu'
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