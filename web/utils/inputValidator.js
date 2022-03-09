import {FetchData} from "../components/FetchData.js";

export const nameValidator = (selectors, validation, data) => {
    document.querySelector(selectors)
        .addEventListener('blur', (ev) => {
            const resultElement = document.getElementById("id-join-name");
            const nameElement = ev.target;
            // 정규식도 그냥 한번 더 검증해 서버의 부하를 줄이자
            let nameRegex = /^[가-힣A-Za-z0-9]{2,12}$/;
            if (nameElement.value === '' || !nameRegex.test(nameElement.value)) {
                nameElement.nextElementSibling.classList.remove('is-hidden');
                nameElement.classList.remove('is-success');
                resultElement.innerText = "사용 불가능한 아이디 입니다";
                validation.pop(data);
            } else {
                nameElement.nextElementSibling.classList.add('is-hidden');
                nameElement.classList.add('is-success');
                (async () => {
                    const result = await FetchData(contextPath + "/auth/name?name=" + nameElement.value, "GET");
                    resultElement.classList.remove("is-hidden");
                    if (result.status === 200) {
                        resultElement.innerText = "사용가능한 아이디 입니다";
                        validation.push(data);
                    } else {
                        resultElement.innerText = "사용 불가능한 아이디 입니다";
                        nameElement.classList.remove('is-success');
                        validation.pop(data);
                    }
                })();
            }
        });
};

export const emailValidator = (selectors, validation, data) => {
    document.querySelector(selectors)
        .addEventListener('blur', (ev) => {
            const emailElement = ev.target;
            const emailRegex = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
            if (!emailRegex.test(emailElement.value)) {
                document.getElementById("id-join-email").innerText = "이메일 형식을 확인하세요";
                document.getElementById("id-join-email-2").innerText = "";
                ev.target.className = 'input is-rounded-custom is-danger';
                validation.pop(data);
            } else {
                document.getElementById("id-join-email").innerText = "";
                emailElement.classList.add('is-success');
                (async () => {
                    const result = await FetchData(contextPath + "/auth/email?email=" + emailElement.value, "GET");
                    const resultElement = document.getElementById("id-join-email-2");
                    resultElement.classList.remove("is-hidden");
                    if (result.status === 200) {
                        resultElement.innerText = "사용 가능한 이메일 주소입니다.";
                        ev.target.className = 'input is-rounded-custom is-success';
                        validation.push(data);
                        return;
                    }
                    resultElement.innerText = result.error.detail;
                    ev.target.className = 'input is-rounded-custom is-danger';
                    validation.pop(data);

                })();
            }
        });
};

export const passwordValidator = (selectors, validation, data) => {
    document.querySelector(selectors)
        .addEventListener('blur', (ev) => {
            let passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
            if (!passwordRegex.test(ev.target.value)) {
                document.getElementById("id-join-pwd").className = "help is-danger";
                ev.target.classList.remove('is-success');
                validation.pop(data);
            } else {
                document.getElementById("id-join-pwd").className = "help is-success";
                ev.target.classList.add('is-success');
                validation.push(data);
            }
        });
};

export const checkPasswordConfirm = (selectors, con_selectors) => {
    document.querySelector(selectors)
        .addEventListener('keyup', (ev) => {
            const password = document.querySelector(con_selectors).value;
            const pwdConfirmElement = ev.target;
            const noticeElement = document.getElementById("id-join-pwdcheck");
            if (pwdConfirmElement.value === "" || password !== pwdConfirmElement.value) {
                noticeElement.className = "help is-danger";
                pwdConfirmElement.classList.remove('is-success');
            } else {
                noticeElement.className = "help is-success";
                pwdConfirmElement.classList.add('is-success');
            }
        });
}

export const passwordConfirmValidator = (selectors, validation, con_selectors, data) => {

    document.querySelector(selectors)
        .addEventListener('blur', (ev) => {
            const password = document.querySelector(con_selectors).value;
            const pwdConfirmElement = ev.target;
            if (pwdConfirmElement.value === "" || password !== pwdConfirmElement.value) {
                validation.pop(data);
            } else {
                validation.push(data);
            }
        });
}
