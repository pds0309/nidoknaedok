import {createElement} from "../../utils/component.js";
import {getDday} from "../../utils/dateHandler.js";
import {FetchData} from "../../components/FetchData.js";

export const OwnerConfirm = (target, result, owner_id) => {
    if (result.status !== 200) {
        return;
    }
    const history = result.data;
    const loanStatus = history[0].history.status_id.bookStatusId;
    if (loanStatus < 3000) {
        return;
    }
    let inner = '';
    if (loanStatus === 3000) {
        inner = `<div class="box">
            <div class="has-text-centered"><br>
                <p class="subtitle">${history[0].member.name}<span class="has-text-grey">&nbsp;회원님에게 대여중이예요!</span></p>
                <p>반납까지</p>
                <div>
                    <h3 id="id-return-dday" class="title"></h3>
                </div>
            </div><br>
        </div>`;
    }
    if (loanStatus === 4000) {
        inner = `<div class="box" >
            <div class="has-text-centered"><br>
                <p class="subtitle">${history[0].member.name}<span class="has-text-grey is-6">&nbsp;회원님이 반납하셨나요?</span></p><br>
                <p>반납 요청일: <span>${history[0].history.requested_at.substr(0, 16)}</span></p>
            </div>
            <div class="column has-text-centered">
                <button id="id-btn-return-history" class="button is-success is-rounded-custom mr-1">반납승인</button>
            </div>
        </div>`;
    }
    const render = () => {
        target.replaceChildren(createElement(inner));
        setEvent();
    }

    const setEvent = () => {
        if (loanStatus === 3000) {
            getDday(new Date(history[0].history.expired_at.substr(0, 10)), document.getElementById('id-return-dday'),
                "반납 기간 지남: " + history[0].history.expired_at.substr(0, 10));
        }

        if (loanStatus === 4000) {
            const request = {
                member_id: owner_id,
                bookshop_id: history[0].history.bookshop_id,
            };
            document.getElementById('id-btn-return-history')
                .addEventListener("click", () => {
                    if (confirm("책을 돌려받으신거죠? 확인하시면 다시 책을 대여해주실 수 있어요")) {
                        (async () => {
                            const result = await FetchData(contextPath + "/bookshops/historylist", 'DELETE', 'application/json', JSON.stringify(request));
                            if (result.status === 201) {
                                location.reload();
                                return;
                            }
                            alert(result.error.detail);
                        })();
                    } else {
                        return false;
                    }
                });
        }
    }
    render();
};