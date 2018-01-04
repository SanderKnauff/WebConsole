<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<%--@elvariable id="applications" type="java.util.List"--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script>var applications = JSON.parse('${applications}')</script>
    <script src="<c:url value="/js/applications-list.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/application-list.css"/>">
    <title>WebConsole | Applications</title>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
</head>
<body onload="onLoad()">
<nav></nav>
<div id="applicationsContainer">
    <section id="applications">
    </section>
</div>
</body>
</html>