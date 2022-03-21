import {createElement} from "../../utils/component.js";
import {FetchData} from "../FetchData.js";
import {modalBasicEvents} from "../../utils/modalBasicEvents.js";

export function SubmitModal(target, id, [...content], title, btnTitle, url, type, endEvSuccessFunc) {
    let contents = ''
    content.forEach(v => contents += v);
    const render = () => {
        target.replaceChildren(createElement(`
    <div id="${id}" class="modal">
        <div class="modal-background"></div>
        <div class="modal-card">
            <header class="modal-card-head">
                <p class="modal-card-title">${title}</p>
                <button class="delete" aria-label="close"></button>
            </header>
            <section class="modal-card-body">
                ${contents}
            <footer class="modal-card-foot">
                <button class="button is-info btn-modal-submit">${btnTitle}</button>
                <button class="button cancel" type="reset">취소</button>
            </footer>
        </div>
    </div>`));
        setEvent();
    }

    const setEvent = () => {
        modalBasicEvents(".js-modal-trigger");

        document.querySelector('.btn-modal-submit')
            .addEventListener('click', () => {
                const request = {};
                Array.from(document.querySelectorAll(".modal-card-body input"))
                    .filter(value => value.name !== "")
                    .forEach(value => {
                        request[value.name] = value.value.trim();
                        value.value = "";
                    });
                FetchData(url, type, "application/json", JSON.stringify(request))
                    .then(function (response) {
                        if (response.status === 201) {
                            alert("등록 완료!");
                            endEvSuccessFunc();
                        } else if (response.error.status === 403) {
                            alert("로그인 후 가능합니다");
                        } else {
                            alert(response.error.detail);
                        }
                        SubmitModal(target, id, [...content], title, btnTitle, url, type)
                    });
            });


    }
    render();
}