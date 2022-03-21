import {createElement} from "../../utils/component.js";
import {FetchData} from "../../components/FetchData.js";
import {getDday, dateToChar} from "../../utils/dateHandler.js";

export const ClientConfirm = (target, result, seller_id) => {
    if (result.status !== 200) {
        return;
    }
    const history = result.data;
    const clientStatus = history.status_id.bookStatusId;
    if (clientStatus < 3000) {
        return;
    }
    let inner = '';
    if (clientStatus === 3000) {
        inner = `<div class="box">
            <div class="has-text-centered"><br>
                <p class="subtitle">회원님이 대여중이예요!</p>
                <small class="has-text-danger is-6 m-1">대여해준 회원에게 반납 후 반납 신청해주세요!</small>
                <p>반납까지</p>
                <div>
                    <h3 id="id-return-dday" class="title"></h3>
                </div>
            </div>
            <div class="column has-text-centered">
                <p>&nbsp;</p>
                <button id="id-btn-return-history" class="button is-success is-rounded-custom mr-1">반납할래요</button>
            </div>
        </div>`;
    } else {
        inner = `<div class="box">
            <div class="has-text-centered"><br>
                <p class="subtitle">반납신청 하셨어요</p><br>
                <p>${history.requested_at.substr(0,16)}</p>
                <small class="has-text-danger is-6 m-1">대여해준 회원님의 응답을 기다려주세요!</small>
                <p>&nbsp;</p>
            </div>
        </div>`;
    }
    const render = () => {
        target.replaceChildren(createElement(inner));
        if (clientStatus === 3000) {
            getDday(new Date(history.expired_at.substr(0, 10)), document.getElementById('id-return-dday')
                , "반납 기간 지남");
            setClientEvent();
        }
    }

    const setClientEvent = () => {
        history.status_id = 'WAIT';
        history.seller_id = seller_id;
        document.getElementById("id-btn-return-history").addEventListener("click", () => {
            if (confirm("정말 책을 반납하셨나요?? 확인 후 취소하실 수 없습니다") === true) {
                (async () => {
                    const res = await FetchData(contextPath + "/bookshops/history", 'PUT', "application/json", JSON.stringify(history));
                    if (res.status === 200) {
                        alert("대여자가 승인하면 반납처리 됩니다!");
                        window.location.reload();
                        return;
                    }
                    alert(res.error.detail);
                })();
            } else {
                return false;
            }
        });

    }
    render();
};