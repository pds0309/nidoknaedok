import {createElement} from '../utils/component.js';

const Navigation = async () => {

    const loginContent =
        `<a class="navbar-item" href="${contextPath}/login">로그인</a>`;
    const signInContent =
        `<a class="navbar-item" href="${contextPath}/join">회원가입</a>`;
    const logOutContent =
        `<a class="navbar-item" href="${contextPath}/members/logout">로그아웃</a>`;
    
    let content = `${loginContent}${signInContent}`;
    if (currentUserName) {
        content = `
                        <div class="navbar-item has-dropdown is-hoverable">
                    <a class="navbar-link" href="${contextPath}/">
                    <figure class="image is-32x32" style="margin-right: 0.5em">
                        <img class="is-rounded mt-1" src="https://avatars1.githubusercontent.com/u/7221389?v=4&amp;s=32">
                    </figure>${currentUserName}
                    </a>
                    <div class="navbar-dropdown is-right">
                        <a class="navbar-item" href="${contextPath}/dashboard" rel="noopener noreferrer">
                        <span class="icon is-small"><i class="fa fa-user-o"></i> </span>&nbsp;&nbsp;  대시보드</a>
                        <a class="navbar-item" href="${contextPath}/mypage" rel="noopener noreferrer">
                        <span class="icon is-small"><i class="fa-solid fa-wrench"></i> </span>&nbsp;&nbsp; 프로필 수정</a>
                        <hr class="navbar-divider">
                        <a class="navbar-item" href='${contextPath}/members/logout'>
                        <span class="icon is-small">
                        <i class="fa fa-power-off"></i> </span>&nbsp;&nbsp;  로그아웃</a>
                    </div>
                </div>`;
    }
    return createElement(`
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
                ${content}
            </div>
        </div>`);
};

export default Navigation;