import {createElement} from '../utils/component.js';
import {OpenDataSyncGET} from "../components/FetchData.js";
import {Search} from "./main/Search.js";

const bookShopList1 = OpenDataSyncGET(contextPath + "/bookshoplist");
export const Home = async (target) => {
    const render = () => {
        target.replaceChildren(createElement(`
    <div class="container">
        <div id="search-area"></div>
        <div id="homenav-area">
            <div class="tabs is-centered">
                <ul>
                    <li class="is-active"><a href="">전체보기</a></li>
                    <li><a href="${contextPath}/booksubmit">등록하기</a></li>
                    <li><a>대여목록</a></li>
                    <li><a>판매목록</a></li>
                </ul>
            </div>        
        </div><br><br>
        <div id="book-area1">${bookShopList1}</div><br>
    </div>`));
        Search(document.getElementById("search-area"));
        setEvent();
    }


    const setEvent = () => {
        setPaging();

        function setPaging() {
            const page = document.getElementById('id-list1-page').value;
            document.getElementById('id-list1-page-prev')
                .addEventListener('click', (ev) => {
                    ev.preventDefault();
                    if (page !== "1") {
                        const prev = parseInt(page) - 1;
                        document.getElementById('book-area1').innerHTML = OpenDataSyncGET(contextPath + "/bookshoplist?page=" + prev);
                        setPaging();
                    }
                });
            document.getElementById('id-list1-page-next')
                .addEventListener('click', (ev) => {
                    ev.preventDefault();
                    if (page !== "5") {
                        const next = parseInt(page) + 1;
                        document.getElementById('book-area1').innerHTML = OpenDataSyncGET(contextPath + "/bookshoplist?page=" + next);
                        setPaging();
                    }
                });
        }
    }
    render();
};
