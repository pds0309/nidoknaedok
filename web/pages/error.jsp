<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
    <link rel="stylesheet" href="main.css">
    <title>에러!!</title>
</head>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="errorinfo" value="${errorinfo}"/>
<body>
<nav class="navbar is-info">
    <div class="container">
        <div class="navbar-brand">
            <a class="navbar-item brand-text" href="${contextPath}/">
                이미지넣기
            </a>
            <div class="navbar-burger burger" data-target="navMenu">
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
        <div id="navMenu" class="navbar-menu">
            <div class="navbar-start">
                <a class="navbar-item" href="${contextPath}/">Home</a>
            </div>
        </div>
    </div>
</nav>
<div class="container">
    <br><br><br><br>
    <section class="section">
        <div class="columns">
            <div class="column is-4 is-offset-4">
                <div class="has-text-centered">
                    <br><br><br><br><br>
                    <h1 class="title has-text-weight-bold is-size-1 has-text-danger">${errorinfo.status}</h1>
                </div>
                <br><br>
                <div class="has-text-centered">
                    <h3 class="subtitle">['${errorinfo.code}']&nbsp;'${errorinfo.message}'</h3>
                </div>
                <br>
                <div class="field">
                    <div class="control">
                        <a class="button is-blick is-large is-fullwidth is-info" href="${contextPath}/">홈으로
                        </a>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>
