function onLoad() {
    if(errors) {
        console.log(errors);
    } else {
        console.log("no Errors");
    }
    if(window.location.href.endsWith("?error")) {
        document.getElementById("invalidCredentialsMessage").style.display = 'inline';
    }
}