var stompClient = null;

function connect() {
    var socket = new SockJS('/logs');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/logs', function (messageOutput) {
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
    var isScrolledToBottom = logOutput.scrollHeight - logOutput.clientHeight <= logOutput.scrollTop + 1;

    if (isArray(messageOutput)) {
        var fragment = document.createDocumentFragment();
        for (var i = 0; i < messageOutput.length; i++) {
            var line = createMessageLine(messageOutput[i]);
            fragment.appendChild(line);
            fragment.appendChild(document.createElement('br'));
        }
        logOutput.appendChild(fragment);
    } else {
        var span = createMessageLine(messageOutput);
        logOutput.appendChild(span);
        logOutput.appendChild(document.createElement('br'));
    }

    //Optionally scroll lines
    if (isScrolledToBottom) {
        logOutput.scrollTop = logOutput.scrollHeight;
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
    var span = document.createElement('span');
    span.appendChild(document.createTextNode(message.line));
    span.classList.add('line');
    span.classList.add(message.logType);
    return span;
}

function startServer() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/application/start", true);
    xmlHttp.send(null);
}

function stopServer() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/application/stop", true);
    xmlHttp.send(null);
}

function restartServer() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", "/application/restart", true);
    xmlHttp.send(null);
    xmlHttp.close();
}

function sendCommand() {
    var inputField = document.getElementById('commandInput');
    if (inputField.value) {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.open("POST", "/application/sendCommand", true);
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