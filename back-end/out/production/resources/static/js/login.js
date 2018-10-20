function onLoad() {
    if(window.location.href.endsWith("?error")) {
        document.getElementById("invalidCredentialsMessage").style.display = 'inline';
    }
}