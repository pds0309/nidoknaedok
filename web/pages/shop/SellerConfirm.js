import {createElement} from "../../utils/component.js";
import {FetchData} from "../../components/FetchData.js";

export const SellerConfirm = (target, list, hasConfirmed, seller_id) => {
    const render = () => {
        let inner = '';
        list.forEach((data, idx) => {
            const mem = data.member;
            const history = data.history;
            inner += `
        <div class="columns box"><br>
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
                <div>
                    <span class="has-text-grey">신청날짜: ${history.created_at.substr(0, 16)}</span><br>
                </div>
                <details>
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
                <div><button id="id-btn-${idx}" class="button is-info is-rounded-custom mb-1 mr-4 is-hidden">완료신청</button>
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
                btn.classList.remove('is-hidden');
                btn.addEventListener("click", () => {
                    commit(list[i].history);
                });
            }
        }

        function cancel(history) {
            if (confirm("정말 거래를 취소하시겠어요?") === true) {
                (async () => {
                    history.status_id = 'PROCESSING';
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

        function commit(history) {
            history.status_id = 'WAITING';
            history.seller_id = seller_id;
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