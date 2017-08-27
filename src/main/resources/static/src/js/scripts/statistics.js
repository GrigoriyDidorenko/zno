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