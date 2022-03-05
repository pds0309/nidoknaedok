<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
    <link rel="stylesheet" href="main.css">
    <title>OAuth 통합</title>
</head>

<c:set var="update" value="${integration}"></c:set>
<c:set var="member" value="${meminfo}"></c:set>
<c:set var="lowerSocialType" value="${fn:toLowerCase(update.socialType)}"></c:set>
<c:set var="contextpath" value="${pageContext.request.contextPath}"/>
<body>
<div class="container">
    <section class="section">
        &nbsp;
    </section>
    <br><br><br><br>
    <section class="section">
        <div class="columns">
            <div class="column is-4 is-offset-4">
                <div class="has-text-centered">
                    <figure class="image is-128x128 is-inline-block">
                        <img id="id-current-image" class="is-rounded my-profile-image" src="${member.profileImage}"
                             alt="세계최고미남미녀" style="height: inherit"/>
                    </figure>
                    <br><br><br><br><br>
                    <p class="title">${member.name}&nbsp;<small>님!</small></p>
                </div>
                <br><br>
                <div class="has-text-centered">
                    <h4><span class="title ${lowerSocialType}-font">${update.socialType}</span> 로 통합하세요
                    </h4><br>
                </div>
                <div class="field">
                    <div class="control">
                        <label class="checkbox" style="color: #cbc8c8">
                            <input id="id-input-check-image" type="checkbox" name="profile_image">&nbsp;
                            ${update.socialType} 프로필 이미지로 변경
                        </label>
                    </div>
                </div>
                <br>
                <div class="field">
                    <div class="control">
                        <button id="id-btn-oauth-submit"
                                class="button login-${lowerSocialType}-btn is-large is-fullwidth">
                            <img src="images/images/service/${lowerSocialType}talk_svg.svg"
                                 srcset="images/service/${lowerSocialType}talk_svg.svg"
                                 alt="kimg">&nbsp;${update.socialType}로 통합하기
                        </button>
                        <br>
                    </div>
                </div>
                <div class="field">
                    <div class="control">
                        <button id="id-btn-cancel" class="button is-blick is-large is-fullwidth is-rounded-custom">취소하고
                            로그인
                        </button>
                        <br>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<script type="module">
    const contextPath = '${contextpath}';
    document.getElementById("id-input-check-image")
        .addEventListener('change', (ev) => {
            const updateImage = '${update.profileImage}'
            === '' ? "images/service/" + '${lowerSocialType}' + "unknown.png" : '${update.profileImage}'
            const currentImage = document.getElementById("id-current-image");
            if (ev.target.checked) {
                currentImage.src = updateImage;
                return;
            }
            currentImage.src = '${member.profileImage}'
        });
    document.getElementById('id-btn-oauth-submit')
        .addEventListener('click', () => {
            const currentImage = document.getElementById("id-current-image");
            const member = {
                id: parseInt('${member.id}'),
                profile_image: currentImage.src,
                social_id: parseInt('${update.socialId}'),
                social_type: '${update.socialType}',
            };

            (async () => {
                const result = await (
                    await fetch(contextPath + "/members",
                        {
                            method: 'PUT',
                            headers: {
                                "Content-Type": 'application/json'
                            },
                            body: JSON.stringify(member)
                        }
                    )
                )
                if (result.status === 201) {
                    window.alert("통합되었습니다!");
                    location.replace(contextPath + "/");
                } else {
                    window.alert('${update.socialType}' + " 통합 실패!");
                }
            })()
        });

    document.getElementById('id-btn-cancel')
        .addEventListener('click', () => {
            location.replace(contextPath + "/");
        });
</script>
</body>
</html>
