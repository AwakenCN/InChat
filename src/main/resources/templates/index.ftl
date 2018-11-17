<html>

<head>
    <title>WebRTC Video Demo</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">
</head>

<style>

    body {
        background: #eee;
        padding: 5% 0;
    }

    video {
        background: black;
        border: 1px solid gray;
    }

    .call-page {
        position: relative;
        display: block;
        margin: 0 auto;
        width: 500px;
        height: 500px;
    }

    #localVideo {
        width: 150px;
        height: 150px;
        position: absolute;
        top: 15px;
        right: 15px;
    }

    #remoteVideo {
        width: 500px;
        height: 500px;
    }

</style>

<body>

<div id = "loginPage" class = "container text-center">

    <div class = "row">
        <div class = "col-md-4 col-md-offset-4">

            <h2>WebRTC Video Demo. Please sign in</h2>
            <label for = "usernameInput" class = "sr-only">Login</label>
            <input type = "email" id = "usernameInput" class = "form-control formgroup" placeholder = "Login"
                   required = "" autofocus = "">
            <button id = "loginBtn" class = "btn btn-lg btn-primary btnblock">
                Sign in</button>

        </div>
    </div>

</div>

<div id = "callPage" class = "call-page">
    <video id = "localVideo" muted autoplay></video>
    <video id = "remoteVideo" autoplay></video>

    <div class = "row text-center">
        <div class = "col-md-12">
            <input id = "callToUsernameInput" type = "text"
                   placeholder = "username to call" />
            <button id = "callBtn" class = "btn-success btn">Call</button>
            <button id = "hangUpBtn" class = "btn-danger btn">Hang Up</button>
        </div>
    </div>

</div>

<script src = "/susu/js/client_demo.js"></script>

</body>

</html>
