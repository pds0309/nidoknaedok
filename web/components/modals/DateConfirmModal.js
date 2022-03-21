import {createElement} from "../../utils/component.js";
import {modalBasicEvents} from "../../utils/modalBasicEvents.js";
import {dateToChar} from "../../utils/dateHandler.js";

export function DateConfirmModal(target, id, trigger, func) {
    const minDate = new Date();
    minDate.setDate(minDate.getDate() + 7);
    const render = () => {
        target.replaceChildren(createElement(`
    <div id="${id}" class="modal">
        <div class="modal-background"></div>
        <div class="modal-card">
            <header class="modal-card-head">
                <p class="modal-card-title">합의한 대여종료 날짜를 선택하세요</p>
                <button class="delete" aria-label="close"></button>
            </header>
            <section class="modal-card-body">
                <input id="id-date-${id}" class="input is-rounded-custom" min="${dateToChar(minDate, '-')}" type="date">
            <footer class="modal-card-foot">
                <button id="id-btn-${id}" class="button is-info btn-modal-submit">확인</button>
                <button class="button cancel" type="reset">취소</button>
            </footer>
        </div>
    </div>`));
        setEvent();
    }

    const setEvent = () => {
        modalBasicEvents(trigger);
        document.getElementById(`id-btn-${id}`)
            .addEventListener('click', () => {
                func(validDate());
            });

        function validDate() {
            const selectedDate = document.getElementById(`id-date-${id}`).value;
            if (selectedDate !== '' && minDate <= new Date(selectedDate)) {
                return selectedDate;
            }
            alert("잘못된 반납일자입니다.");
        }
    }
    render();
}