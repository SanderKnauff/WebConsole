<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script>var errors = '${errors}'</script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/setup.css"/>">
    <script src="<c:url value="/js/setup.js"/>"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebConsole | Setup</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body onload="onLoad()">
<form id="setup" action="<c:url value="/setup"/>" method="post" name="UserDetails">
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_user_transparant.svg"/>"/>
        <input class="input" name="username" />
    </div>
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_password_transparant.svg"/>"/>
        <input class="input" name="password" type="password"/>
    </div>
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_password_transparant.svg"/>"/>
        <input class="input" name="newPassword" type="password"/>
    </div>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <img id="createButton" onclick="submit()" src="<c:url value="/img/icon_user_create.svg"/>">
</form>
</body>
</html>