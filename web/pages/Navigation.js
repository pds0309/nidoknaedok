import {createElement} from '../utils/component.js';

const Navigation = async () => {

    const loginContent =
        `<a class="navbar-item" href="${contextPath}/login">로그인</a>`;
    const signInContent =
        `<a class="navbar-item" href="${contextPath}/join">회원가입</a>`;
    const logOutContent =
        `<a class="navbar-item" href="${contextPath}/members/logout">로그아웃</a>`;
    const myPageContent =
        `<a class="navbar-item" href="${contextPath}/mypage">마이페이지</a>`;

    let content = `${loginContent}${signInContent}`;
    if (currentUserName) {
        content = `${myPageContent}${logOutContent}`;
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
            ${content}
                </div>
            </div>
        </div>`);
};

export default Navigation;