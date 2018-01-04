<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/login.css"/>">
    <script src="<c:url value="/js/login.js"/>"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebConsole | Login</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body onload="onLoad()">
<span id="invalidCredentialsMessage">Invalid credentials</span>
<form id="login" action="<c:url value="/login"/>" method="post">
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_user_transparant.svg"/>"/>
        <input class="input" name="username" />
    </div>
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_password_transparant.svg"/>"/>
        <input class="input" name="password" type="password"/>
    </div>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <img id="loginButton" onclick="submit()" src="<c:url value="/img/icon_login_transparant.svg"/>">
</form>
</body>
</html>