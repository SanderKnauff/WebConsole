<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="users" type="java.util.List"--%>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script>var users = '${users}'</script>
    <%--<link rel="stylesheet" type="text/css" href="<c:url value="/css/setup.css"/>">--%>
    <%--<script src="<c:url value="/js/newuser.js"/>"></script>--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>WebConsole | Users</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body>
<section id="users">
    <c:forEach items="${users}" var = "user">
        <div class="userEntry">
            <span><c:out value="${user.username}" /></span>
            <span><c:out value="${user.roles}" /></span>
        </div>
    </c:forEach>
</section>
</body>
</html>