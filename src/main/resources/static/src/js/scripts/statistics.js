$( document ).ready(function() {

    jQuery.ajax({
        type: "GET",
        url: "/user/statistics",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (json) {

            console.log(json);

            if(json.length == 0){
                $('.statistic_nodata').css('display', 'block')
            }

            var avrgDuration;
            var avrgMark;
            var numOfFailedQuestions;
            var subjectName;
            var subjectID;

            var duration;
            var mark;
            var testNumOfFailedQuestions;
            var testName;

            $.each(json, function (key, val) {
                $.each(val, function (subject, data) {
                    switch (subject) {
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
                        case 'id':
                            subjectID = data;
                            break;
                    }
                });
                $('.statistic_subjects').append('<div class="statistic_subject">' +
                    '<div class="subject_info"><p class="test_name" name="'+subjectID+'">' + subjectName + '</p><div class="subject_info-data"><div><p>cередній бал</p><section><svg class="circle-chart" viewbox="0 0 33.83098862 33.83098862" width="50" height="50" xmlns="http://www.w3.org/2000/svg">' +
                    '<circle class="circle-chart__background" stroke="#efefef" stroke-width="2" fill="none" cx="16.91549431" cy="16.91549431" r="15.91549431" />' +
                    '<circle class="circle-chart__circle" stroke="#00acc1" stroke-width="2" stroke-dasharray="' + Math.round(avrgMark-100) + ',100" stroke-linecap="round" fill="none" cx="16.91549431" cy="16.91549431" r="15.91549431" />' +
                    '<g class="circle-chart__info"><text class="circle-chart__percent" x="16.91549431" y="15.5" alignment-baseline="central" text-anchor="middle" font-size="9">' + Math.round(avrgMark) + '%</text></g></svg></section></div><div><p>cередній час</p><p>' + Math.round(avrgDuration) + '<span>хв</span></p></div>' +
                    '<div class="failed_test"><p>неправильні відповіді</p><div class="statistics_bell""><img src="src/img/bell.svg" alt="bell"><span class="statistics_bell-numb">' + numOfFailedQuestions + '</span></div></div></div><div class="statistic_subject-arrow">дані по тестам <i class="fa fa-angle-down" aria-hidden="true"></i></div>' +
                    '</div><div class="test_info" name="' + key + '"></div>');
                $.each(val, function (subject, data) {
                    switch (subject) {
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
                                $('.test_info[name="' + key + '"]').append('<div class="test_inform"><p class="test_name">' + testName + '</p><div class="subject_info-data"><div><p>cередній бал</p><section><svg class="circle-chart" viewbox="0 0 33.83098862 33.83098862" width="50" height="50" xmlns="http://www.w3.org/2000/svg">' +
                                    '<circle class="circle-chart__background" stroke="#efefef" stroke-width="2" fill="none" cx="16.91549431" cy="16.91549431" r="15.91549431" />' +
                                    '<circle class="circle-chart__circle" stroke="#00acc1" stroke-width="2" stroke-dasharray="' + Math.round(mark-100) + ',100" stroke-linecap="round" fill="none" cx="16.91549431" cy="16.91549431" r="15.91549431" />' +
                                    '<g class="circle-chart__info"><text class="circle-chart__percent" x="16.91549431" y="15.5" alignment-baseline="central" text-anchor="middle" font-size="9">' + Math.round(mark) + '%</text></g></svg></section></div><div><p>cередній час</p><p>' + Math.round(duration) + '<span>хв</span></p></div>' +
                                    '<div><p>неправильні відповіді</p><p>' + testNumOfFailedQuestions + '</p></div></div></div>');
                            });
                            break;
                    }
                });
            });
        }
    });

    $('body').on('click', '.statistic_subject-arrow', function () {
        $(this).parent().next().fadeToggle();
    }).on('click', '.failed_test', function () {
        var moduleName = $(this).parent().prev().text();
        $('#test_info .modal-title').text(moduleName);
        $('#test_info').modal('show');
        $('#test_info .btn').attr('href', 'test.html?failed=true&time=false&test='+$(this).parent().prev().attr('name'));
    });

});