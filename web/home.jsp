<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
    <link rel="stylesheet" href="main.css">
    <script src="https://kit.fontawesome.com/54c286718e.js" crossorigin="anonymous"></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
    <title>메인페이지</title>
</head>
<c:set var="contextpath" value="${pageContext.request.contextPath}"/>
<script>
    const contextPath = '${contextpath}';
    const currentUserName = '${meminfo.getName()}';
</script>
<c:if test="${not empty kakaoinfo}">
    <c:set var="info" value="${kakaoinfo}"/>
    <c:set var="name" value="KAKAO"/>
    <script type="module">
        import {OAuthInfo} from "./components/OAuthInfo.js";
        import {OAuthJoin} from "./pages/OAuthJoin.js";

        const name = '${name}';
        const info = {
            social_id: '${info.id}',
            name: '${info.properties.nickname}',
            profile_image: '${info.properties.thumbnailImage}',
            email: '${info.kakaoAccount.email}',
        }
        OAuthInfo(OAuthJoin, name).set(info);
    </script>
</c:if>
<body>
<nav id="navigation" class="navbar is-info"></nav>
<div class="container">
    <section id="body"></section>
</div>
<script src="./nav.js" type="module"></script>
<script src="./index.js" type="module"></script>
</body>

</html>