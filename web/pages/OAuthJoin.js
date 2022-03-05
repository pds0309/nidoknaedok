import {createElement} from '../utils/component.js';
import {Validation} from "../components/Validation.js";
import {OAuthInfo} from "../components/OAuthInfo.js";
import {sample6_execDaumPostcode} from "../utils/daumemail.js";
import {nameValidator} from "../utils/inputValidator.js";
import {FetchData} from "../components/FetchData.js";

export const OAuthJoin = async (target, oathName) => {
    const info = OAuthInfo(OAuthJoin).get;
    const oauthName = oathName.toUpperCase();
    const validation = Validation(OAuthJoin);
    const submitData = {
        name: "name",
        address: "address"
    }
    const profileImage =
        info.profile_image === "" ? contextPath + "/images/service/" + oathName + "unknown.png" : info.profile_image;

    const render = () => {
        target.replaceChildren(createElement(`<br>
        <section class="section">
            <form>
                <div class="columns">
                    <div class="column is-4 is-offset-4">
                        <div class="has-text-centered">
                            <figure class="image is-96x96 is-inline-block">
                                <img class="is-rounded" src="${profileImage}" alt="세계최고미남미녀" />
                            </figure>
                            <br>
                            <p class="subtitle">${info.name}&nbsp;<small>님</small></p>
                        </div><br><br>
                        <div class="has-text-centered">
                            <h4><span class="title ${oathName}-font">${oauthName}</span> 로 참여하세요!</h4><br>
                        </div>
                        <div class="field">
                            <label class="label">이름</label>
                            <div class="control">
                                <input class="input is-rounded-custom" type="text" placeholder="${info.name} 보다 멋진이름을 원해요!" 
                                name="name">
                                <p class="help is-danger is-hidden">2~12글자의 한글,영문,숫자를 입력해주세요</p>
                            </div>
                            <p id="id-join-name" class="help is-hidden is-link">사용 불가능한 아이디입니다.</p>
                        </div>
                        <div class="field">
                            <label class="label">주소</label>
                            <div class="control">
                            <div class="columns">
                                <div class="column">
                                      <input class="input is-rounded-custom" type="text" id="sample6_postcode" placeholder="우편번호" readonly>                          
                                </div>
                                <div class="column">
                                      <input class="button login-${oathName}-btn" id="id-btn-getaddr" type="button" value="우편번호 찾기"><br>
                                </div>
                            </div>
                            <input class="input is-rounded-custom" type="text" id="sample6_address" placeholder="주소" readonly><br>
                            <input class="input is-rounded-custom" name="addressdetail" type="text" id="sample6_detailAddress" placeholder="상세주소">
                            <input class="input is-rounded-custom" name="${submitData.address}" type="hidden" id="id-h-address">
                            <input class="is-hidden" type="text" id="sample6_extraAddress" placeholder="참고항목" readonly>
                            </div>
                        </div><br>
                        <div class="field">
                            <div class="control">
                                <label class="checkbox" style="color: #cbc8c8">
                                    <input id="id-input-check-image" type="checkbox" name="profile_image" disabled>&nbsp;제 프로필 이미지를 그대로 사용하고 싶어요!
                                </label>
                            </div>
                        </div>
                        <br>
                        <div class="field">
                            <div class="control">
                                <button id="id-btn-oauth-submit" class="button login-${oathName}-btn is-large is-fullwidth">
                                    <img src="${contextPath}/images/service/${oathName}talk_svg.svg" srcset="${contextPath}/images/service/${oathName}talk_svg.svg" alt="kimg">&nbsp;${oauthName}로 시작
                                </button><br> 
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </section>`
        ));
        setEvent();
    }

    const setEvent = () => {

        nameValidator('input[name=name]', validation, submitData.name);

        window.onload = () => {
            if (info.profile_image !== "") {
                const checkbox = document.getElementById('id-input-check-image');
                checkbox.parentElement.style.color = 'black';
                checkbox.disabled = false;
            }
        }

        document.getElementById("id-btn-getaddr")
            .addEventListener("click", () => {
                sample6_execDaumPostcode(validation)
            });

        document.getElementById('id-btn-oauth-submit')
            .addEventListener('click', (ev) => {
                ev.preventDefault();
                if (validation.get.length !== 2 ||
                    validation.get.indexOf("address:" + document.getElementById("id-h-address").value) === -1) {
                    alert("입력을 확인하세요!!");
                    return;
                }
                const request = {
                    signType: oauthName,
                    social_id: info.social_id,
                    email: info.email,
                    password: Math.floor((Math.random() * 10000000 + 10000000))+"x!"
                };
                Array.from(document.querySelectorAll("input"))
                    .filter(value => value.name !== "")
                    .forEach(value => {
                        request[value.name]
                            = value.type !== 'checkbox' ? value.value.trim()
                            : (value.checked) ? info.profile_image : '';
                        value.value = "";
                    });
                (async () => {
                    const result
                        = await FetchData(contextPath + "/members", "POST", "application/json", JSON.stringify(request));
                    if (result.status === 201) {
                        location.replace(contextPath + "/");
                    } else {
                        window.alert(oauthName + "연동에 실패했습니다!");
                    }
                })();
            });
    }
    render();
};