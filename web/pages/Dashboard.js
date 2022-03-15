import {createElement} from "../utils/component.js";
import {OpenDataSyncGET} from "../components/FetchData.js";
import {PageState} from "../components/PageState.js";

export const Dashboard = async (target) => {
    const member_status = JSON.parse(OpenDataSyncGET(contextPath + "/bookshops/stats"));
    if (member_status.status !== 200) {
        window.location.href = contextPath + "/error";
    }
    const sellCount = member_status.data.SELL_COUNT;
    const bookshopSellResult = JSON.parse(OpenDataSyncGET(contextPath + "/bookshops?total=" + sellCount));
    const sellPage = PageState(Dashboard);
    sellPage.setAll(
        {
            page: parseInt(bookshopSellResult.data.page),
            prev: bookshopSellResult.data.prev,
            next: bookshopSellResult.data.next,
        });
    const member = JSON.parse(OpenDataSyncGET(contextPath + "/members")).data;
    const bookshops = bookshopSellResult.data.bookshops;
    const render = () => {
        target.replaceChildren(createElement(`
                <div class="hero-body">
                    <div class="is-full">
                        <h1 class="title is-spaced">대시보드</h1>
                        <div class="columns">
                            <div class="column is-half-desktop mr-3">
                                <div class="card is-rounded-custom pl-2"><br>
                                    <h2 class="title is-spaced has-text-centered is-underlined">프로필</h2>
                                    <div class="columns">
                                        <br>
                                        <div class="column is-two-fifths-desktop is-two-fifths-tablet is-full-mobile">
                                            <div class="has-text-centered">
                                                <figure class="image is-128x128 is-inline-block">
                                                    <img class="is-rounded"
                                                        src="${member.profileImage === null ? contextPath + '/images/service/kakaounknown.png' : member.profileImage}"
                                                        alt="" style="height: inherit">
                                                </figure>
                                                <br><br>
                                                <h2 class="subtitle">${member.name}</h2>
                                            </div>
                                        </div>
                                        <div class="column is-full-mobile pl-6">
                                            <div class="has-text-left">
                                                <p class="is-6 ">이메일</p>
                                                <p class="is-5 p-2 has-text-grey">${member.email}</p>
                                            </div>
                                            <div class="has-text-left">
                                                <p class="is-6 ">가입일</p>
                                                <p class="is-5 p-2 has-text-grey">${member.createdAt}</p>
                                            </div>
                                            <div class="has-text-left">
                                                <p class="is-6 ">SNS</p>
                                                <p class="is-5  p-2 has-text-grey">
                                                <figure class="image is-16x16 is-inline-block">
                                                    <img class="is-rounded" src="${contextPath}/images/service/${member.socialType.toLowerCase()}-circle.png"
                                                        alt="" style="height: inherit">
                                                </figure>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="has-text-left pl-5 pr-5">
                                        <h2 class="is-5 mb-1">&nbsp;소개</h2>
                                        <p class="box has-text-grey">${member.introduction === null ? '소개 없음' : member.introduction}</p>
                                        <br>
                                    </div>
                                    <div class="has-text-centered">
                                        <a class="button is-rounded-custom is-info mb-3" href="${contextPath}/mypage">프로필 수정</a><br><br>
                                    </div>
                                </div><br>
                                <div class="card is-rounded-custom">
                                    <h2 class="title is-spaced has-text-centered is-underlined">거래기록</h2>
                                    <div class="is-mobile tile is-ancestor has-text-centered columns">
                                        <div class="tile is-parent is-4-desktop is-4-tablet is-4-mobile column">
                                            <article class="tile is-child box">
                                                <p class="is-4-mobile is-1-desktop has-text-danger">${sellCount}<span
                                                        class="is-6 has-text-grey">회</span></p>
                                                <p class="is-6-mobile is-4-desktop">대여/판매등록</p>
                                            </article>
                                        </div>
                                        <div class="tile is-parent is-4-desktop is-4-tablet is-4-mobile column">
                                            <article class="tile is-child box">
                                                <p class="is-4-mobile is-1-desktop has-text-danger">99<span
                                                        class="is-6 has-text-grey">회</span></p>
                                                <p class="is-6-mobile is-4-desktop">대여/판매성공</p>
                                            </article>
                                        </div>
                                        <div class="tile is-parent is-4-desktop is-4-tablet is-4-mobile column">
                                            <article class="tile is-child box">
                                                <p class="is-4-mobile is-1-desktop has-text-danger">99<span
                                                        class="is-6 has-text-grey">회</span></p>
                                                <p class="is-6-mobile is-4-desktop">대출/구매성공</p>
                                            </article>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="column is-half-desktop">
                                <div class="card is-rounded-custom"><br>
                                    <h2 class="title is-spaced has-text-centered is-underlined">내 판매/대여</h2>
                                    <table class="table is-bordered is-striped is-narrow is-fullwidth mt-4">
                                        <thead>
                                            <tr>
                                                <th><abbr title="City">이름</abbr></th>
                                                <th><abbr title="DOB">날짜</abbr></th>
                                                <th>형태</th>
                                                <th>상태</th>
                                            </tr>
                                        </thead>
                                        <tbody id="id-table-sell">
                                        </tbody>
                                    </table>
                                    <div id="id-table-sell-pager" class="field has-addons has-addons-centered">
                                        <div class="control">
                                            <button id="id-table-sell-page-prev" class="button is-small">&lt;</button>
                                        </div>
                                        <div class="control">
                                            <button id="id-table-sell-page-next" class="button is-small">&gt;</button>
                                        </div>
                                    </div>
                                    <br>
                                </div><br><br>
                                <div class="card is-rounded-custom"><br>
                                    <h2 class="title is-spaced has-text-centered is-underlined">내 구매/대출</h2>
                                    <table class="table is-bordered is-striped is-narrow is-fullwidth mt-4">
                                        <thead>
                                            <tr>
                                                <th><abbr title="City">이름</abbr></th>
                                                <th><abbr title="DOB">날짜</abbr></th>
                                                <th>형태</th>
                                                <th>상태</th>
                                            </tr>
                                        </thead>
                                    </table>
                                    <div class="field has-addons has-addons-centered">
                                        <div class="control">
                                            <button id="id-dash2-page-prev" class="button is-small">&lt;</button>
                                        </div>
                                        <div class="control">
                                            <button id="id-dash2-page-next" class="button is-small">&gt;</button>
                                        </div>
                                    </div>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </div>`
        ));
        setEvent();
    }
    const setEvent = () => {
        setTradeTable("id-table-sell", bookshops);

        function setTradeTable(targetId, object) {
            if (object.length === 0) {
                document.getElementById(targetId + "-pager").classList.add("is-hidden");
                document.getElementById(targetId).replaceChildren(createElement('<tr><td colspan="4">목록이 존재하지 않습니다.</td></tr><br>'));
            }
            const tableHTML =
                object.map(value => {
                    const book = value.book;
                    const bookshop = value.bookShop;
                    return `<tr>
                    <td class="pt-2 is-hidden-mobile"><abbr title="${book.bookTitle}">${book.bookTitle.substr(0, 14) + "..."}</abbr></td>
                    <td class="pt-2 is-hidden-desktop is-hidden-tablet"><abbr title="${book.bookTitle}">${book.bookTitle.substr(0, 8) + "..."}</abbr></td>
                    <td class="pt-2"><abbr title="${bookshop.created_at.substr(0, 16)}">${bookshop.created_at.substr(2, 8)}</abbr></td>
                    <td class="pt-2">${bookshop.selltype_id.selltypeDetail}</td>
                    <td class="pt-2">
                        ${bookshop.sell_status_id.bookStatusDetail}&nbsp;
                        <a href="${contextPath + "/bookshop?bookshopid=" + bookshop.bookshop_id}" class="icon is-small">
                            <i class="fa fa-edit"></i>
                        </a>
                    </td>
                </tr>`
                })
            document.getElementById(targetId).replaceChildren(createElement(tableHTML));
        }

        pagination("id-table-sell", bookshopSellResult.data, contextPath + "/bookshops?total=" + sellCount);

        function pagination(targetId, object, url) {
            document.getElementById(targetId + "-page-prev")
                .addEventListener("click", () => {
                    if (sellPage.get[0].prev) {
                        sellPage.setPage(sellPage.get[0].page - 1);
                        const currentBookShop = JSON.parse(OpenDataSyncGET(url + "&page=" + sellPage.get[0].page)).data;
                        setTradeTable(targetId, currentBookShop.bookshops);
                        sellPage.setPrev(currentBookShop.prev);
                        sellPage.setNext(currentBookShop.next);
                    }
                });
            document.getElementById(targetId + "-page-next")
                .addEventListener("click", () => {
                    if (sellPage.get[0].next) {
                        sellPage.setPage(sellPage.get[0].page + 1);
                        const currentBookShop = JSON.parse(OpenDataSyncGET(url + "&page=" + sellPage.get[0].page)).data;
                        setTradeTable(targetId, currentBookShop.bookshops);
                        sellPage.setPrev(currentBookShop.prev);
                        sellPage.setNext(currentBookShop.next);
                    }
                });
        }
    }
    render();
}