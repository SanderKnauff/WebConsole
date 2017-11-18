var stompClient = null;

function connect() {
    var socket = new SockJS('/logs');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/logs', function (messageOutput) {
            var message = JSON.parse(messageOutput.body);
            // The initial message is a list of messages
            if(isArray(message)) {
                for(var i = 0; i < message.length; i++) {
                    appendLog(message[i]);
                }
            } else {
                appendLog(message);
            }
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function appendLog(messageOutput) {
    console.log(messageOutput);
    var logOutput = document.getElementById('logOuput');

    var isScrolledToBottom = logOutput.scrollHeight - logOutput.clientHeight <= logOutput.scrollTop + 1;
    console.log("Top: " + logOutput.scrollTop + "; scrollHeight: " + logOutput.scrollHeight);

    //Prepare the new log line element
    var span = document.createElement('span');
    span.appendChild(document.createTextNode(messageOutput.line));
    span.classList.add('line');
    span.classList.add(messageOutput.logType);

    //Append new log lines
    logOutput.appendChild(span);
    logOutput.appendChild(document.createElement('br'));

    //Optionally scroll lines
    if (isScrolledToBottom) {
        logOutput.scrollTop = logOutput.scrollHeight;
    } else {
        //Notify that there are new messages
    }
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
    if(inputField.value) {
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

function isArray(obj){
    return !!obj && obj.constructor === Array;
}