import {createElement} from "../../utils/component.js";
import {FetchData} from "../../components/FetchData.js";

export const BuyerConfirm = (target, result, seller_id) => {
    if (result.status !== 200) {
        return;
    }
    const history = result.data;

    let confirmation = ``;
    const needConfirm = history.status_id.bookStatusId === 2000;
    if (needConfirm) {
        confirmation = `<p id="id-p-confirm-history-msg m-1">반납일을 확인하시고 책을 수령하셨다면 '확정'해주세요</p>
                        <p><span>반납:&nbsp;</span><span class="has-text-grey">${history.expired_at.substr(0, 10)}<small>까지</small></span></p>        
                <button id="id-btn-confirm-history" class="button is-success is-rounded-custom mr-1">확정</button>`;
    }
    const inner = `<div class="columns box">
            <div class="column is-6-desktop is-6-tablet is-full-mobile">
                <br>
                <p class="subtitle">거래 신청하셨습니다</p>
                <p><span>[일시]&nbsp;</span><span class="has-text-grey">${history.created_at.substr(0, 16)}</span></p>
                <p><span>[메모]&nbsp;</span><span class="has-text-grey">${history.memo}</span></p>
            </div>
            <div class="column has-text-centered">
                <p>&nbsp;</p>
                ${confirmation}
                <button id="id-btn-cancel-history" class="button is-danger is-rounded-custom mr-1">취소</button>
            </div>
        </div>`;
    const render = () => {
        target.replaceChildren(createElement(inner));
        setCancelEvent();
        setConfirmEvent(needConfirm);
    }
    const setCancelEvent = () => {
        document.getElementById("id-btn-cancel-history")
            .addEventListener("click", () => {
                const request = {
                    member_id: history.member_id,
                    bookshop_id: history.bookshop_id,
                };
                if (confirm("정말 취소하시겠어요?") === true) {
                    (async () => {
                        const result = await FetchData(contextPath + "/bookshops/history", 'DELETE', 'application/json', JSON.stringify(request));
                        if (result.status === 201) {
                            alert("삭제 완료");
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
    const setConfirmEvent = (need) => {
        if (!need) {
            return;
        }
        history.status_id = 'LOANING';
        history.seller_id = seller_id;
        history.expired_at = '';
        document.getElementById("id-btn-confirm-history").addEventListener("click", () => {
            if (confirm("정말 책을 수령하셨어요??") === true) {
                (async () => {
                    const res = await FetchData(contextPath + "/bookshops/history", 'PUT', "application/json", JSON.stringify(history));
                    if (res.status === 200) {
                        alert("잘 읽으세요!");
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