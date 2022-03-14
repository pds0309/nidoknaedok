import {createElement} from "../utils/component.js";
import {FetchData} from "../components/FetchData.js";
import {SetCookie} from "../components/CookieHandler.js";

export const Login = async (target) => {
    const render = () => {
        target.replaceChildren(createElement(`<section class="hero is-fullheight">
      <div class="hero-body">
        <div class="container has-text-centered">
            <div class="has-text-centered">
                <h2 class="title">어서와요!</h2>
            </div>
          <div class="column is-4-desktop is-offset-4-desktop is-fullwidth-mobile">
            <div class="box">
              <br />
                <div class="field">
                  <p class="control has-icons-left has-icons-right">
                    <input class="input is-medium is-rounded-custom" type="email" placeholder="이메일" />
                    <span class="icon is-medium is-left">
                      <i class="fas fa-envelope"></i>
                    </span>
                    <span class="icon is-medium is-right">
                      <i class="fas fa-check"></i>
                    </span>
                  </p>
                </div>
                <div class="field">
                  <p class="control has-icons-left">
                    <input class="input is-medium is-rounded-custom" type="password" placeholder="비밀번호" />
                    <span class="icon is-small is-left">
                      <i class="fas fa-lock"></i>
                    </span>
                  </p>
                </div>
                <div class="field">
                    <p id="id-input-check" class="help is-danger"></p>
                </div>
<!--                <div class="field">-->
<!--                  <label class="checkbox">-->
<!--                    <input type="checkbox" />-->
<!--                    Remember me-->
<!--                  </label>-->
<!--                </div>-->
                <button id="id-btn-login-normal" class="button is-block is-info is-large is-fullwidth is-rounded-custom">로그인</button><br />
                <p class="subtitle is-6">SNS 계정으로 로그인하기</p>
                <button id="id-kakao-btn" class="button login-kakao-btn is-large is-fullwidth">
                    <img src="${contextPath}/images/service/kakaotalk_svg.svg" srcset="${contextPath}/images/service/kakaotalk_svg.svg" alt="kimg">&nbsp;
                    카카오 로그인
                </button><br>
                <a class="button is-blick is-large is-fullwidth is-rounded-custom">SNS2 로그인</a>
            </div>
            <p class="has-text-grey">
              <a href="${contextPath}/join">회원가입</a> &nbsp;·&nbsp; <a href="#">비밀번호 찾기</a> &nbsp;·&nbsp;
              <a href="#">Help?</a>
            </p>
          </div>
        </div>
      </div>
    </section>`));
        setEvent();
    }
    const setEvent = () => {
        document.querySelector('button#id-btn-login-normal')
            .addEventListener('click', (ev) => {
                const email = document.querySelector("input[type='email']");
                const password = document.querySelector("input[type='password']");
                const inputCheckElement = document.getElementById("id-input-check");
                if (!email || email.value.length < 4) {
                    inputCheckElement.innerText = "이메일을 입력해주세요";
                    return;
                }
                if (!password || password.value.length < 4) {
                    inputCheckElement.innerText = "비밀번호 입력을 확인하세요";
                    return;
                }
                const body = {
                    email: email.value,
                    password: password.value,
                };
                (async () => {
                    const result =
                        await FetchData(contextPath + "/members/login", "POST", "application/json", JSON.stringify(body));
                    // TODO 회원가입 등의 의미없는 페이지가 이전 페이지였을 경우 그냥 메인으로 보내는 처리를 해야 한다
                    if (result.status === 200) {
                        if (history.state !== undefined && history.state !== null && history.state.prev !== "/") {
                            location.replace(history.state.prev);
                            return;
                        }
                        location.replace(contextPath + getParameterByName("req"));

                    } else {
                        inputCheckElement.innerText = result.error.detail;
                    }
                })();
            });

        function getParameterByName(name) {
            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"), results = regex.exec(location.search);
            return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
        }

        document.getElementById("id-kakao-btn")
            .addEventListener('click', () => {
                SetCookie("req", getParameterByName("req").substr(1), 5);
                location.href = 'https://kauth.kakao.com/oauth/authorize?client_id=8861433d60e2a2021bb2d209868e868c&redirect_uri=http://localhost:8080/nidoknaedok/members/oauth/kakao&response_type=code';
            });
    }
    render();
}