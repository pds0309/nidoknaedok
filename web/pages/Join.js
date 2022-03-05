import {createElement} from '../utils/component.js';
import {Validation} from "../components/Validation.js";
import {sample6_execDaumPostcode} from "../utils/daumemail.js";
import {FetchData} from "../components/FetchData.js";
import {
    checkPasswordConfirm,
    emailValidator,
    nameValidator,
    passwordConfirmValidator,
    passwordValidator
} from "../utils/inputValidator.js";

export const Join = async (target) => {

    const validation = Validation(Join);
    const submitData = {
        name: "name",
        password: "password",
        email: "email",
        password_confirm: "password_confirm",
        address: "address"
    }

    const render = () => {
        target.replaceChildren(createElement(`<br>
        <section class="section">
            <form method="" action="">
                <div class="columns">
                    <div class="column is-4 is-offset-4">
                        <div class="has-text-centered">
                            <h2 class="title">OOO에 참여하세요!</h2><br>
                        </div>
                        <div class="field">
                            <label class="label">이름</label>
                            <div class="control">
                                <input class="input is-rounded-custom" type="text" placeholder="활동이름 입력" name="${submitData.name}">
                                <p class="help is-danger is-hidden">2~12글자의 한글,영문,숫자를 입력해주세요</p>
                            </div>
                            <p id="id-join-name" class="help is-link"></p>
                        </div>
                        <div class="field">
                            <label class="label">이메일</label>
                            <div class="control has-icons-left has-icons-right">
                                <input class="input is-rounded-custom" type="email" placeholder="이메일 입력" name="${submitData.email}">
                            <span class="icon is-small is-left">
                                <i class="fa fa-envelope"></i>
                            </span>
                            <span class="icon is-small is-right"></span>
                            </div>
                            <p id="id-join-email" class="help is-danger"></p>
                            <p id="id-join-email-2" class="help"></p>
                        </div>
                        <div class="field">
                            <label class="label">비밀번호</label>
                            <p class="control has-icons-left">
                                <input class="input is-rounded-custom" type="password" placeholder="비밀번호 입력" name="${submitData.password}">
                    <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                    </span>
                            </p>
                        <p id="id-join-pwd" class="help is-danger">영문 숫자, ~!@# 가 포함된 8~20글자여야 합니다.</p>                                
                        </div>
                        <div class="field">
                            <label class="label">비밀번호 확인</label>
                            <p class="control has-icons-left">
                                <input class="input is-rounded-custom" type="password" placeholder="비밀번호 확인" name="${submitData.password_confirm}">
                    <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                    </span>
                            </p>
                            <p id="id-join-pwdcheck" class="help is-danger">비밀번호화 일치시켜주세요</p>                                
                        </div>
                        <div class="field">
                            <label class="label">주소</label>
                            <div class="control">
                            <div class="columns">
                                <div class="column">
                                      <input class="input is-rounded-custom" type="text" id="sample6_postcode" placeholder="우편번호" readonly>                          
                                </div>
                                <div class="column">
                                      <input class="button is-info is-rounded-custom" id="id-btn-getaddr" type="button" value="우편번호 찾기"><br>
                                </div>
                            </div>
                            <input class="input is-rounded-custom" type="text" id="sample6_address" placeholder="주소" readonly><br>
                            <input class="input is-rounded-custom" name="addressdetail" type="text" id="sample6_detailAddress" placeholder="상세주소">
                            <input class="input is-rounded-custom" name="${submitData.address}" type="hidden" id="id-h-address">
                            <input class="is-hidden" type="text" id="sample6_extraAddress" placeholder="참고항목" readonly>
                            </div>
                        </div>
                        <div class="field">
                            <div class="control">
                                <input type="submit" class="button is-info is-fullwidth is-rounded-custom" value="가입하기"/>
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
        document.getElementById("id-btn-getaddr")
            .addEventListener("click", () => {
                sample6_execDaumPostcode(validation)
            });

        nameValidator('input[name=name]', validation, submitData.name);
        emailValidator('input[name=email]', validation, submitData.email);
        passwordValidator('input[name=password]', validation, submitData.password);
        passwordConfirmValidator('input[name=password_confirm]', validation, 'input[name=password]', submitData.password_confirm);
        checkPasswordConfirm('input[name=password_confirm]', 'input[name=password]')
        document.querySelector('form')
            .addEventListener('submit', (ev) => {
                ev.preventDefault();
                if (validation.get.length !== 5 ||
                    validation.get.indexOf("address:" + document.getElementById("id-h-address").value) === -1) {
                    alert("입력을 확인하세요!!");
                    return;
                }
                const request = {signType: "NORMAL"};
                Array.from(document.querySelectorAll("input"))
                    .filter(value => value.name !== "")
                    .forEach(value => {
                        request[value.name] = value.value.trim();
                        value.value = "";
                    });
                (async () => {
                    const result
                        = await FetchData(contextPath + "/members", "POST", "application/json", JSON.stringify(request));
                    if (result.status === 201) {
                        window.alert(result.data.message);
                        location.replace(contextPath + "/");
                    } else {
                        window.alert(result.status + "가입 실패!");
                    }
                })();
            });
    }
    render();
};