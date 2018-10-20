function onLoad() {
    var applicationsContainer = document.getElementById("applications");
    var addButton = document.getElementById("addButton");
    for (var applicationProperty in applications) {
        if (applications.hasOwnProperty(applicationProperty)) {
            var application = applications[applicationProperty];
            var block = document.createElement("div");
            var applicationText = document.createElement("span");
            applicationText.classList.add("applicationText");
            applicationText.textContent = application.applicationName;
            block.classList.add("buttonBlock");
            block.style.backgroundImage = "url(/application/" + applicationProperty + "/icon)";
            block.style.backgroundColor = "hsl(" + application.colorHue + ", 100%, 34.5%)";
            block.appendChild(applicationText);
            block.addEventListener('click', navigateTo(applicationProperty), false);
            applicationsContainer.insertBefore(block, addButton);
        }
    }
}

function navigateTo(destination) {
    return function () {
        console.log("Clicked button: " + destination);
        window.location.href = '/application/' + destination;
    }
}