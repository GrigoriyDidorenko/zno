window.fbAsyncInit = function() {
    FB.init({
        appId      : '314517038979180',
        cookie     : true,  // enable cookies to allow the server to access
        // the session
        xfbml      : true,  // parse social plugins on this page
        version    : 'v2.8' // use graph api version 2.8
    });

    // Now that we've initialized the JavaScript SDK, we call
    // FB.getLoginStatus().  This function gets the state of the
    // person visiting this page and can return one of three states to
    // the callback you provide.  They can be:
    //
    // 1. Logged into your app ('connected')
    // 2. Logged into Facebook, but not your app ('not_authorized')
    // 3. Not logged into Facebook and can't tell if they are logged into
    //    your app or not.
    //
    // These three cases are handled in the callback function.
};

function fb_login(){
    FB.login(function(response) {

        if (response.authResponse) {
            console.log('Welcome!  Fetching your information.... ');
            //console.log(response); // dump complete info
            var access_token = response.authResponse.accessToken; //get access token
            var userId = response.authResponse.userID; //get FB UID
            if (response.status === 'connected') {
                // Logged into your app and Facebook.

                FB.api('/me?fields=name,email', function(response) {
                    var email = response.email;
                    var name = response.name;

                    var user = {
                        "user_id": userId,
                        "name": name,
                        "email": email,
                        "access_token": access_token,
                        "password": access_token /*bool shit not needed*/ //FIXME
                    };
                    send_post(JSON.stringify(user), "/facebookLogin");
                });
            }

        } else {
            //user hit cancel button
            console.log('User cancelled login or did not fully authorize.');

        }
    }, {
        scope: 'email'
    });
}
// Load the SDK asynchronously
(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));


// Google


var clientId = '485893597957-ovu7l997llgukgmdcr8fpekti6ooi3bm.apps.googleusercontent.com';

var scopes = 'profile';
function handleClientLoad() {
    gapi.load('client:auth2', initClient);
}
function initClient() {
    gapi.client.init({
        clientId: clientId,
        scope: scopes
    })
}

function g_login() {
    gapi.auth2.getAuthInstance().signIn().then(g_callback_s, g_callback_e);
}

function g_callback_s() {
    send_post(gapi.client.getToken().id_token, "/googleLogin");
}

function g_callback_e() {
    console.log("error while authenticate with google");
}


function send_post(data, url) {
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: url,
        data: data,
        success: function () {
            console.log("success");
        },
        error: function (response) {
            console.log("fail:" + response);
        }
    })
}