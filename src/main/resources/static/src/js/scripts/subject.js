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
                if (val.name.toLowerCase() == 'історія україни') {
                    imgUrl = 'src/img/' + val.name.toLowerCase().replace(/ /g, '_') + '.png';
                }

                if (val.id == subjectId) {
                    currentSubject = 'subject currentSubject';
                    subjectUrl = '';
                } else {
                    currentSubject = 'subject';
                    subjectUrl = 'href="subject.html?subject=' + val.id + '"';
                }

                $('.subjects_menu').append('<a ' + subjectUrl + ' class="' + currentSubject + '"><div class="subject_img"><img src="' + imgUrl + '" alt=""></div><p>' + val.name + '</p></a>');

                if (subjectId == val.id) {
                    $('h1').text(val.name);

                    var json = val.articleJSON;

                    var obj = jQuery.parseJSON(json);

                    $('.advice_title').append("Корисні поради з " + obj.name);
                    $('.subject_block-info_text i').append(obj.articleJSON.adviceTeacher);

                    $.each(obj.articleJSON.advices, function (i, val) {
                        $('.advices').append('<p>' + val + '</p>');
                    });

                    $.each(obj.articleJSON.additionalResources, function (key, val) {
                        $('.advices_link').append('<li><a href="' + val + '" target="_blank">' + key + '</a></li>');
                    });
                }

                var brainshtorm;

                switch (val.name.toLowerCase()) {
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
                    $('.subject_brainshtorm').attr('href', 'test.html?time=false&brainstorm=true&test=' + val.id);
                }

            });
        }
    });

    jQuery.ajax({
        type: "GET",
        url: "/api/subject/" + subjectId,
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

            $.each(json, function (key, val) {
                years.push(val.year);
            });

            unique = years.filter(onlyUnique);

            $.each(unique, function (i, val) {

                $('.panel-group').append('<div class="panel panel-default">' +
                    '<div class="panel-heading" role="tab" id="heading-' + i + '"><h4 class="panel-title">' +
                    '<a role="button" data-toggle="collapse" data-parent="#accordion" href="#-' + i + '" ' +
                    'aria-expanded="true" aria-controls="-' + i + '">Тести за ' + val + ' рік</a></h4></div><div ' +
                    'id="collapse-' + i + '" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading-' + i + '">' +
                    '<div class="panel-body panel-body_' + i + '">');
                $('.panel').on("click", function (e) {
                    var $_target = $(e.currentTarget);
                    var $_panelBody = $_target.find(".panel-collapse");
                    if ($_panelBody) {
                        $_panelBody.collapse('toggle')
                    }
                }).click();
                $.each(json, function (key, value) {
                    if (value.year == val) {
                        $('.panel-body_' + i).append('<a data-toggle="modal" data-target="#test_info" class="test_link" name="' + value.id + '" value="' + value.duration + '">' +
                            value.name + '</a>');
                    }
                });
                $('.panel-group').append('</div></div></div>');
            });

            $('body').on('click', '.btn_test', function () {
                var inp = $(this).prev().find('input').is(":checked");
                var test = $('#test_info .modal-title').attr('name');
                $(this).attr("href", "test.html?time=" + inp + "&test=" + test + "");
            }).on('click', '.test_link', function () {

                var modalTest = $(this).attr('name');
                $('#test_info .modal-title').text($(this).text()).attr('name', modalTest);
                $('#test_info .test_duration').text($(this).attr('value'));

                jQuery.ajax({
                    type: "GET",
                    url: "/api/test/" + modalTest,
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (test) {
                        $.each(test, function (key, val) {
                            switch (key) {
                                case 'questions':
                                    questions_numb = val.length;
                                    $('#test_info').find('.questions_numb').text(questions_numb);
                            }
                        })
                    }
                });
            });

            $('.collapse').collapse();

        }
    });
});