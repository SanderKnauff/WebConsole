<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="errors" type="java.util.List"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script>var errors = '${errors}'</script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/setup.css"/>">
    <script src="<c:url value="/js/users/newUser.js"/>"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebConsole | Create new user</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body onload="onLoad()">
<form id="setup" action="<c:url value="/users/new"/>" method="post" name="UserDetails">
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_user_transparant.svg"/>"/>
        <input id="usernameInput" class="input" name="username"/>
        <span id="usernameEmptyError" class="inputError">Username can not be empty</span>
    </div>
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_password_transparant.svg"/>"/>
        <input id="passwordInput" class="input" name="password" type="password"/>
        <span id="passwordEmptyError" class="inputError">Password can not be empty</span>
    </div>
    <div class="inputContainer">
        <img class="inputImage" src="<c:url value="/img/icon_password_transparant.svg"/>"/>
        <input id="passwordConfirmInput" class="input" name="newPassword" type="password"/>
        <span id="passwordNotEqualError" class="inputError">Passwords do no match</span>
    </div>
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <img id="createButton" onclick="submit()" src="<c:url value="/img/icon_user_create.svg"/>">
</form>
</body>
</html>