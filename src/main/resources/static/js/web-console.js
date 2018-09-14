var stompClient = null;

function connect() {
    var socket = new SockJS('/sockets');

    var headers = {};
    headers[document.querySelector("meta[name='_csrf_header'").getAttribute("content")] = document.querySelector("meta[name='_csrf'").getAttribute("content");

    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect(headers, function (frame) {
        stompClient.subscribe('/logs/' + environment, function (messageOutput) {
            // The initial message is a list of messages
            appendLog(JSON.parse(messageOutput.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function appendLog(messageOutput) {
    var logOutput = document.getElementById('logOuput');
    var console = document.getElementById('console');
    var isScrolledToBottom = console.scrollHeight - console.clientHeight <= console.scrollTop + 1;

    if (isArray(messageOutput)) {
        var fragment = document.createDocumentFragment();
        for (var i = 0; i < messageOutput.length; i++) {
            var line = createMessageLine(messageOutput[i]);
            fragment.appendChild(line);
        }
        logOutput.appendChild(fragment);
    } else {
        var span = createMessageLine(messageOutput);
        logOutput.appendChild(span);
        logOutput.appendChild(document.createElement('br'));
    }

    //Optionally scroll lines
    if (isScrolledToBottom) {
        console.scrollTop = console.scrollHeight;
    } else {
        //Notify that there are new messages
    }
}

function clearLog() {
    var logOutput = document.getElementById('logOuput');
    while (logOutput.firstChild) {
        logOutput.removeChild(logOutput.firstChild);
    }
}

function createMessageLine(message) {
    var span = document.createElement('div');
    span.appendChild(document.createTextNode(message.line));
    span.classList.add('line');
    span.classList.add(message.logType);
    return span;
}

function startServer() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/application/" + environment + "/start?debug=" + event.shiftKey, true);
    xmlHttp.send(null);
}

function stopServer() {
    var xmlHttp = new XMLHttpRequest();
    if(!event.shiftKey) {
        xmlHttp.open("GET", "/application/" + environment + "/stop", true);
    } else {
        xmlHttp.open("GET", "/application/" + environment + "/kill");
    }
    xmlHttp.send(null);
}

function restartServer() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/application/" + environment + "/restart", true);
    xmlHttp.send(null);
    xmlHttp.close();
}

function sendCommand() {
    var inputField = document.getElementById('commandInput');
    if (inputField.value) {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("POST", "/application/" + environment + "/sendCommand", true);
        xmlHttp.setRequestHeader(document.head.querySelector("[name=_csrf_header]").content, document.head.querySelector("[name=_csrf]").content);
        xmlHttp.send(inputField.value);
        inputField.value = "";
    }
}

function onCommandInputEnter(event) {
    if (event.key === 'Enter') {
        sendCommand();
    }
}

function isArray(obj) {
    return !!obj && obj.constructor === Array;
}

function focusCommandInput() {
    document.getElementById("commandInput").focus();
}

document.addEventListener("keydown", isShiftHeld, true);
document.addEventListener("keyup", isShiftHeld, true);

function isShiftHeld(event) {
    var altButtons = document.getElementsByClassName("altButton");
    for(var index = 0; index < altButtons.length; index++) {
        var altButton = altButtons[index];
        if(event.shiftKey) {
            altButton.classList.add("altButtonActive");
        } else {
            altButton.classList.remove("altButtonActive");
        }

    }
}