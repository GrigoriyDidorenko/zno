$( document ).ready(function() {

    $('#fullpage').fullpage({
        sectionsColor: ['#2980b9'],
        anchors: ['welcome'],
        menu: '#menu'
    });

    var pass_email;
    var old_pass;
    var new_pass;

    $('#change-pass').click(function () {

        pass_email = $('#pass-email').val();
        old_pass = $('#pass-old').val();
        new_pass = $('#pass-new').val();

        var changePassObject = {
            "email": pass_email,
            "oldPassword": old_pass,
            "newPassword": new_pass
        };

        console.log(JSON.stringify(changePassObject));

        $.ajax({
            type: "POST",
            url: "/changePassword",
            data: JSON.stringify(changePassObject),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            error: function (XMLHttpRequest) {
                $('#changePass_confirm').modal('toggle');
                $('#user_changePass').text(pass_email);
                console.log('user status:' + XMLHttpRequest.status + ', status text: ' + XMLHttpRequest.statusText);
            }
        })

    })

});