<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Web Console</title>
    <script>var environment = '${environment}'</script>
    <script src="<c:url value="/js/sock.js"/>"></script>
    <script src="<c:url value="/js/stomp.js"/>"></script>
    <script src="<c:url value="/js/web-console.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/console.css"/>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>

<body onload="connect()">
<section id="control">
    <img class="controlButton altButton" src="<c:url value="/img/icon_start.svg"/>" onclick="startServer(event)"/>
    <img class="controlButton" src="<c:url value="/img/icon_stop.svg"/>" onclick="stopServer()"/>
    <img class="controlButton" src="<c:url value="/img/icon_restart.svg"/>" onclick="restartServer()"/>
    <img class="controlButton endButton" src="<c:url value="/img/icon_delete.svg"/>" onclick="clearLog()"/>
</section>
<section id="console" onclick="focusCommandInput()">
    <section id="logOuput">
    </section>
    <div id="command">
        <span>&gt;&nbsp;</span>
        <input id="commandInput" autocomplete="off" onkeydown="onCommandInputEnter(event)">
    </div>
</section>
</body>
</html>