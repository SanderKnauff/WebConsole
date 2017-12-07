<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script>var applications = ${applications}</script>
    <script src="<c:url value="/js/applications-list.js"/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/application-list.css"/>">
    <title>Applications</title>
</head>
<body onload="onLoad()">
<nav></nav>
<div id="applicationsContainer">
    <section id="applications">
    </section>
</div>
</body>
</html>