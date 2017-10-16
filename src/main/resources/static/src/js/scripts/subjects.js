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
            $('.pageSubjects').append('<a href="subject.html?subject=' + val.id + '" class="subject"><div class="subject_img"><img src='+imgUrl+' alt="' + val.name.toLowerCase() + '"><span>'+val.name+'</span></div></a>');
        });
    }
});

$( document ).ready(function() {

    $('#fullpage').fullpage({
        sectionsColor: ['#2980b9'],
        anchors: ['welcome'],
        menu: '#menu'
    })

});