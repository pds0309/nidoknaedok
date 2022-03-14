import {createElement} from "../utils/component.js";
import {FetchData} from "./FetchData.js";

export function SubmitModal(target, id, [...content], title, btnTitle, url, type) {
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
        // Functions to open and close a modal
        function openModal($el) {
            $el.classList.add('is-active');
        }

        function closeModal($el) {
            $el.classList.remove('is-active');
        }

        function closeAllModals() {
            (document.querySelectorAll('.modal') || []).forEach(($modal) => {
                closeModal($modal);
            });
        }

        // Add a click event on buttons to open a specific modal
        (document.querySelectorAll('.js-modal-trigger') || []).forEach(($trigger) => {
            const modal = $trigger.dataset.target;
            const $target = document.getElementById(modal);

            $trigger.addEventListener('click', () => {
                openModal($target);
            });
        });
        // Add a click event on various child elements to close the parent modal
        (document.querySelectorAll('.modal-background, .modal-close, .modal-card-head .delete, .modal-card-foot .button') || []).forEach(($close) => {
            const $target = $close.closest('.modal');

            $close.addEventListener('click', () => {
                closeModal($target);
            });
        });
        // Add a keyboard event to close all modals
        document.addEventListener('keydown', (event) => {
            const e = event || window.event;
            if (e.keyCode === 27) { // Escape key
                closeAllModals();
            }
        });


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
                        }
                        else if (response.error.status === 403) {
                            alert("로그인 후 가능합니다");
                        }
                        else if(response.error.status < 500){
                            alert(response.error.message);
                        }
                        else{
                            alert("이미 요청하셨습니다");
                        }
                        SubmitModal(target, id, [...content], title, btnTitle, url, type)
                    });
            });


    }
    render();
}