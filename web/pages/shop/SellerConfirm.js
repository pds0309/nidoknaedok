import {createElement} from "../../utils/component.js";
import {FetchData} from "../../components/FetchData.js";
import {DateConfirmModal} from "../../components/modals/DateConfirmModal.js";

export const SellerConfirm = (target, list, seller_id) => {
    if (list.length === 0 || list[0].history.status_id.bookStatusId >= 3000) {
        return;
    }
    const hasConfirmed = list[0].history.status_id.bookStatusId === 2000;
    const render = () => {
        let inner = '';
        list.forEach((data, idx) => {
            const mem = data.member;
            const history = data.history;
            inner += `<h1 class="subtitle is-spaced">거래신청한 회원들이예요!</h1>
                        <small class="has-text-danger">&nbsp;※ 잠깐! 충분한 대화는 나누셨나요??</small><br>
        <div class="columns box"><br>
            <div id="id-modal-date-submit-${idx}"></div>
            <div class="column is-3"><br>
                <div class="has-text-centered">
                    <figure class="image is-128x128 is-inline-block">
                        <img class="is-rounded"
                            src="${mem.profileImage === null ? contextPath + '/images/service/kakaounknown.png' : mem.profileImage}"
                            alt="" style="height: inherit">
                    </figure>
                    <br><br>
                    <h2 class="subtitle">${mem.name}&nbsp;<small>님</small></h2>
                </div>
            </div>
            <div class="column is-9">
                <div class="pl-5 pt-5 pb-1">
                    <span class="has-text-grey">신청날짜: ${history.created_at.substr(0, 16)}</span><br>
                </div>
                <br>
                <details class="pl-5 pb-2">
                    <summary class="subtitle"><small>상세보기</small></summary>
                    <div class="column is-8 is-offset-2 is-full">
                        <p><span>[가입일]&nbsp;</span><span class="has-text-grey">${mem.createdAt.substr(0, 10)}</span></p>
                    </div>
                    <div class="column is-8 is-offset-2 is-full">
                        <p><span>[주소]&nbsp;</span><span class="has-text-grey">${mem.address}</span></p>
                    </div>
                    <div class="column is-8 is-offset-2 is-full">
                        <p><span>[메모]&nbsp;</span><span class="has-text-grey">${history.memo}</span></p>
                    </div>
                </details>
                <div class="is-full has-text-left ml-5">
                <div><button id="id-btn-${idx}" data-target="modal-confirm-date-${idx}" class="button is-info is-rounded-custom mb-1 mr-4 is-hidden">완료신청</button>
                &nbsp;<button class="button is-success is-rounded-custom mb-1 mr-4">쪽지전송</button>
                </div><br>
                </div>
            </div>
        </div><br>`;
        });
        target.replaceChildren(createElement(inner));
        setEvent();
    }
    const setEvent = () => {
        if (hasConfirmed) {
            const btn = document.getElementById("id-btn-0");
            btn.className = "button is-danger is-rounded-custom mb-1 mr-4";
            btn.innerText = '취소하기';
            btn.addEventListener("click", () => {
                cancel(list[0].history);
            });
        } else {
            for (let i = 0; i < list.length; i++) {
                const btn = document.getElementById(`id-btn-${i}`);
                DateConfirmModal(document.getElementById(`id-modal-date-submit-${i}`),
                    `modal-confirm-date-${i}`, `#id-btn-${i}`, (date) => commit(list[i].history, date));
                btn.classList.remove('is-hidden');
            }
        }

        function cancel(history) {
            if (confirm("정말 거래를 취소하시겠어요?") === true) {
                (async () => {
                    history.status_id = 'SUBMIT';
                    history.seller_id = seller_id;
                    const res = await FetchData(contextPath + "/bookshops/historylist", 'PUT', "application/json", JSON.stringify(history));
                    if (res.status === 200) {
                        alert("취소되었습니다");
                        window.location.reload();
                        return;
                    }
                    alert(res.error.detail);
                })();
            } else {
                return false;
            }
        }

        function commit(history, date) {
            history.status_id = 'PROCESSING';
            history.seller_id = seller_id;
            history.expired_at = date;
            if (confirm("정말 이 사람과 거래하실건가요??") === true) {
                (async () => {
                    const res = await FetchData(contextPath + "/bookshops/historylist", 'PUT', "application/json", JSON.stringify(history));
                    if (res.status === 200) {
                        alert("상대방이 확정하면 거래가 완료됩니다!");
                        window.location.reload();
                        return;
                    }
                    alert(res.error.detail);
                })();
            } else {
                return false;
            }
        }
    }
    render();
};