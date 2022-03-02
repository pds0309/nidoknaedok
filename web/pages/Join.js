import {createElement} from '../utils/component.js';
import {Validation} from "../components/Validation.js";
import {sample6_execDaumPostcode} from "../utils/daumemail.js";
import {FetchData} from "../components/FetchData.js";

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
        target.replaceChildren(createElement(`
        <section class="section">
            <form method="post" action="members">
                <div class="columns">
                    <div class="column is-4 is-offset-4">
                        <div class="field">
                            <label class="label">이름</label>
                            <div class="control">
                                <input class="input" type="text" placeholder="활동이름 입력" name="${submitData.name}">
                                <p class="help is-danger is-hidden">2~12글자의 한글,영문,숫자를 입력해주세요</p>
                            </div>
                            <p id="id-join-name" class="help is-hidden is-link">사용 불가능한 아이디입니다.</p>
                        </div>
                        <div class="field">
                            <label class="label">이메일</label>
                            <div class="control has-icons-left has-icons-right">
                                <input class="input" type="email" placeholder="이메일 입력" name="${submitData.email}">
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
                                <input class="input" type="password" placeholder="비밀번호 입력" name="${submitData.password}">
                    <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                    </span>
                            </p>
                        <p id="id-join-pwd" class="help is-danger">영문 숫자, ~!@# 가 포함된 8~20글자여야 합니다.</p>                                
                        </div>
                        <div class="field">
                            <label class="label">비밀번호 확인</label>
                            <p class="control has-icons-left">
                                <input class="input" type="password" placeholder="비밀번호 확인" name="${submitData.password_confirm}">
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
                                      <input class="input" type="text" id="sample6_postcode" placeholder="우편번호" readonly>                          
                                </div>
                                <div class="column">
                                      <input class="button is-info" id="id-btn-getaddr" type="button" value="우편번호 찾기"><br>
                                </div>
                            </div>
                            <input class="input" type="text" id="sample6_address" placeholder="주소" readonly><br>
                            <input class="input" name="addressdetail" type="text" id="sample6_detailAddress" placeholder="상세주소">
                            <input class="input" name="${submitData.address}" type="hidden" id="id-h-address">
                            <input class="is-hidden" type="text" id="sample6_extraAddress" placeholder="참고항목" readonly>
                            </div>
                        </div>
                        <div class="field is-grouped">
                            <div class="control">
                                <input type="submit" class="button is-info" value="제출"/>
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
        document.querySelector('input[name="name"]')
            .addEventListener('blur', (ev) => {
                const nameElement = ev.target;
                // 정규식도 그냥 한번 더 검증해 서버의 부하를 줄이자
                let nameRegex = /^[가-힣A-Za-z0-9]{2,12}$/;
                if (nameElement.value === '' || !nameRegex.test(nameElement.value)) {
                    nameElement.nextElementSibling.classList.remove('is-hidden');
                    nameElement.classList.remove('is-success');
                    validation.pop(submitData.name);
                } else {
                    nameElement.nextElementSibling.classList.add('is-hidden');
                    nameElement.classList.add('is-success');
                    (async () => {
                        const result = await FetchData("auth/name?name=" + nameElement.value, "GET");
                        const resultElement = document.getElementById("id-join-name");
                        resultElement.classList.remove("is-hidden");
                        if (result.status === 200) {
                            resultElement.innerText = "사용가능한 아이디 입니다";
                            validation.push(submitData.name);
                        } else {
                            resultElement.innerText = "사용 불가능한 아이디 입니다";
                            nameElement.classList.remove('is-success');
                            validation.pop(submitData.name);
                        }
                    })();
                }
            });

        document.querySelector('input[name=password]')
            .addEventListener('blur', (ev) => {
                let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
                if (!passwordRegex.test(ev.target.value)) {
                    document.getElementById("id-join-pwd").className = "help is-danger";
                    ev.target.classList.remove('is-success');
                    validation.pop(submitData.password);
                } else {
                    document.getElementById("id-join-pwd").className = "help is-success";
                    ev.target.classList.add('is-success');
                    validation.push(submitData.password);
                }
            });

        document.querySelector('input[name=password_confirm]')
            .addEventListener('blur', (ev) => {
                const password = document.querySelector('input[name="password"]').value;
                const pwdConfirmElement = ev.target;
                if (pwdConfirmElement.value === "" || password !== pwdConfirmElement.value) {
                    document.getElementById("id-join-pwdcheck").className = "help is-danger";
                    pwdConfirmElement.classList.remove('is-success');
                    validation.pop(submitData.password_confirm);
                } else {
                    document.getElementById("id-join-pwdcheck").className = "help is-success";
                    pwdConfirmElement.classList.add('is-success');
                    validation.push(submitData.password_confirm);
                }
            });

        document.querySelector('input[name=email]')
            .addEventListener('blur', (ev) => {
                const emailElement = ev.target;
                const emailRegex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
                if (!emailRegex.test(emailElement.value)) {
                    document.getElementById("id-join-email").innerText = "이메일 형식을 확인하세요";
                    document.getElementById("id-join-email-2").innerText = "";
                    ev.target.classList.remove('is-success');
                    validation.pop(submitData.email);
                } else {
                    document.getElementById("id-join-email").innerText = "";
                    emailElement.classList.add('is-success');
                    (async () => {
                        const result = await FetchData("auth/email?email=" + emailElement.value, "GET");
                        const resultElement = document.getElementById("id-join-email-2");
                        resultElement.classList.remove("is-hidden");
                        if (result.status === 200) {
                            resultElement.innerText = "사용 가능한 이메일 주소입니다.";
                            validation.push(submitData.email);
                        } else {
                            resultElement.innerText = "이미 존재하는 이메일 주소입니다.";
                            validation.pop(submitData.email);
                        }
                    })();
                }
            });

        document.querySelector('form[action="members"]')
            .addEventListener('submit', (ev) => {
                if (validation.get.length !== 5 ||
                    validation.get.indexOf("address:" + document.getElementById("id-h-address").value) === -1) {
                    console.log(validation.get);
                    alert("입력을 확인하세요!!");
                    ev.preventDefault();
                }
            });
    }
    render();

};