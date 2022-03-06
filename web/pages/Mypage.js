import {OpenDataSyncGET, FetchData} from "../components/FetchData.js";
import {createElement} from "../utils/component.js";

export const Mypage = async (target) => {
    const myinfo = JSON.parse(OpenDataSyncGET(contextPath + "/members")).data;
    const profileImage =
        myinfo.profileImage === null ? contextPath + "/images/service/kakaounknown.png" : myinfo.profileImage;

    const render = () => {
        target.replaceChildren(createElement(`<br>
        <section class="section">
                <div class="columns">
                    <div class="column is-4 is-offset-4">
                        <div class="has-text-centered">
                            <figure class="image is-128x128 is-inline-block">
                                <img class="is-rounded" src="${profileImage}" alt="" style="height: inherit"/>
                            </figure>
                            <br><br>
                            <h2 class="subtitle">${myinfo.name}&nbsp;<small>님의 프로필</small></h2>
                        </div><br><br>
                        <div class="field">
                        <h2 class="subtitle">소개&nbsp;&nbsp;<a class="class-update-icon" href="${contextPath}/mypage"><i class="fa-solid fa-pencil"></i></a></h2>
                        <textarea id="id-textarea" style="background-color: #efecec" class="textarea has-fixed-size" placeholder='' readonly>${myinfo.introduction === null ? '없음' : myinfo.introduction}</textarea><br>
                        <button id="id-btn-update-intro" class="button is-info is-rounded-custom">저장</button>&nbsp;&nbsp;
                        </div><br>                        
                        <div class="field">
                        <h2 class="subtitle">이메일</h2>
                        <p class="input is-rounded-custom" type="text">${myinfo.email}</p>
                        </div><br>
                        <div class="field">
                        <h2 class="subtitle">주소</h2>
                        <input class="input is-rounded-custom" type="text" readonly value="${myinfo.address}" /><br><br>
                        </div><br>
                        <div class="field">
                        <h2 class="subtitle">상세주소&nbsp;<a class="class-update-icon"><i class="fa-solid fa-pencil"></i></a></h2>
                        <input id='id-addressdetail' style="background-color: #efecec" class="input is-rounded-custom" type="text" readonly value="${myinfo.addressDetail === null ? '' : myinfo.addressDetail}" />
                        <br><br>
                        <button id="id-btn-update-addressdetail" class="button is-info is-rounded-custom">저장</button>&nbsp;&nbsp;
                        </div>
                        <br>
                        <div id='id-div-password' class="field" hidden>
                            <h2 class="subtitle">비밀번호 입력</h2>
                            <p class="control has-icons-left">
                                <input class="input is-rounded-custom" type="password" placeholder="비밀번호 입력" name="password">
                            <span class="icon is-small is-left">
                            <i class="fas fa-lock"></i>
                            </span>
                            </p><br>
                            <div class="control has-text-centered">
                                <button id="id-btn-delete-cancel" class="button is-rounded-custom">취소하기</button><br>                                                            
                            </div>     
                        </div><br><br>
                        <div class="field">
                            <div class="control has-text-centered">
                                    <button id="id-btn-delete-submit" class="button is-info is-rounded-custom login-${myinfo.socialType.toLowerCase()}-btn">회원탈퇴</button><br>                                
                            </div>
                        </div>
                    </div>
                </div>                                
        </section>`
        ));
        setEvent();
    }
    const setEvent = () => {

        document.querySelectorAll('.class-update-icon')
            .forEach(value => value.addEventListener('click', (ev) => {
                ev.preventDefault();
                const currentBox = ev.target.parentElement.parentElement.nextElementSibling;
                if (currentBox.readOnly) {
                    currentBox.style.backgroundColor = 'white';
                    currentBox.readOnly = false;
                    return;
                }
                currentBox.style.backgroundColor = '#efecec';
                currentBox.readOnly = true;
            }));

        document.getElementById('id-btn-update-intro')
            .addEventListener("click", (ev) => {
                const content = document.getElementById('id-textarea');
                ev.preventDefault();
                if (content.readOnly) {
                    return;
                }
                const data = {
                    id: myinfo.id,
                    introduction: content.value.substr(0, 100)
                };
                (async () => {
                    const result = await FetchData("members", 'PUT', 'application/json', JSON.stringify(data));
                    if (result.status === 201 && result.data.result === '1') {
                        alert("수정 성공!");
                    }
                })();
            });

        document.getElementById('id-btn-update-addressdetail')
            .addEventListener("click", (ev) => {
                const content = document.getElementById('id-addressdetail');
                ev.preventDefault();
                if (content.readOnly) {
                    return;
                }
                const data = {
                    id: myinfo.id,
                    address_detail: content.value.substr(0, 60)
                };
                (async () => {
                    const result = await FetchData("members", 'PUT', 'application/json', JSON.stringify(data));
                    if (result.status === 201 && result.data.result === '1') {
                        alert("수정 성공!");
                    }
                })();
            });

        document.getElementById('id-btn-delete-submit')
            .addEventListener('click', (ev) => {
                const passwordDiv = document.getElementById("id-div-password");
                const password = document.querySelector('input[type="password"]');
                const data = {
                    id: myinfo.id,
                    password: password.value.trim(),
                    social_id: myinfo.socialId
                };
                console.log(data);
                console.log(myinfo.socialType);
                if ((!passwordDiv.hidden && password.value !== '') || myinfo.socialType !== 'NORMAL') {
                    (async () => {
                        const result = await FetchData(contextPath + "/members", 'DELETE', 'application/json', JSON.stringify(data));
                        if (result.status === 200 && result.data.result === '1') {
                            alert("회원탈퇴가 완료되었습니다.");
                            location.replace(contextPath + "/");
                            return;
                        }
                        alert("비밀번호가 일치하지 않습니다");
                    })();
                    return;
                }
                passwordDiv.hidden = !passwordDiv.hidden;
            });

        document.getElementById('id-btn-delete-cancel')
            .addEventListener('click', (ev) => {
                ev.target.parentElement.parentElement.hidden = true;
            });
    }
    render();
}