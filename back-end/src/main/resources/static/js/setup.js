function onLoad() {
    if(errors.includes('username.empty')) {
        document.getElementById("usernameInput").classList.add('hasError');
        document.getElementById("usernameEmptyError").style.display = 'block';
    }
    if(errors.includes('password.empty')) {
        document.getElementById("passwordInput").classList.add('hasError');
        document.getElementById("passwordEmptyError").style.display = 'block';
    }
    if(errors.includes('password.not.equal')) {
        document.getElementById("passwordInput").classList.add('hasError');
        document.getElementById("passwordConfirmInput").classList.add('hasError');
        document.getElementById("passwordNotEqualError").style.display = 'block';
    }
}