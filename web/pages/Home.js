import {createElement} from '../utils/component.js';
import {OpenDataSyncGET} from "../components/FetchData.js";
import {Search} from "./main/Search.js";
import {Submission} from "../components/Submission.js";

const bookShopList1 = OpenDataSyncGET(contextPath + "/bookshoplist");
const bookShopList3 = OpenDataSyncGET(contextPath + "/bookshoptype?tradecode=SB");
const bookShopList4 = OpenDataSyncGET(contextPath + "/bookshoptype?tradecode=SS");
export const Home = async (target) => {
    const params = Submission(Home);
    const dropdown = Submission(Home);
    params.set("");
    dropdown.set("");
    const render = () => {
        target.replaceChildren(createElement(`
    <div class="container">
        <div id="search-area"></div>
        <div id="homenav-area">
            <div class="tabs is-centered">
                <ul>
                    <li class="is-active"><a id="tab-book-area1" class="home-tab" href="${contextPath}/">전체보기</a></li>
                    <li><a href="${contextPath}/booksubmit">등록하기</a></li>
                    <li><a id="tab-book-area3" class="home-tab" href="${contextPath}/">대여목록</a></li>
                    <li><a id="tab-book-area4" class="home-tab" href="${contextPath}/">판매목록</a></li>
                </ul>
            </div>        
        </div><br><br>
        <div class="home-div" id="book-area1">${bookShopList1}</div><br>
        <div class="home-div" id="book-area3" hidden>${bookShopList3}</div><br>
        <div class="home-div" id="book-area4" hidden>${bookShopList4}</div><br>
    </div>`));
        Search(document.getElementById("search-area"));
        setEvent();
    }

    const setEvent = () => {
        setMainPaging();
        setRender('id-200', 'book-area3', contextPath + "/bookshoptype?tradecode=SB");
        setRender('id-400', 'book-area4', contextPath + "/bookshoptype?tradecode=SS");

        function setRender(id, target, url) {
            setTypePaging(id, target, url, params.get[0]);
            setTypeSubmit(id, target, url);
            setDropdown(id);
        }

        function setDropdown(id) {
            if (dropdown.get[0] !== "") {
                document.querySelector(`#${id}-form option[value='${dropdown.get[0]}']`).selected = true;
            }
        }

        function setMainPaging() {
            const page = document.getElementById('id-list1-page').value;
            document.getElementById('id-list1-page-prev')
                .addEventListener('click', (ev) => {
                    ev.preventDefault();
                    if (page !== "1") {
                        const prev = parseInt(page) - 1;
                        document.getElementById('book-area1').innerHTML = OpenDataSyncGET(contextPath + "/bookshoplist?page=" + prev);
                        setMainPaging();
                    }
                });
            document.getElementById('id-list1-page-next')
                .addEventListener('click', (ev) => {
                    ev.preventDefault();
                    if (page !== "5") {
                        const next = parseInt(page) + 1;
                        document.getElementById('book-area1').innerHTML = OpenDataSyncGET(contextPath + "/bookshoplist?page=" + next);
                        setMainPaging();
                    }
                });
        }

        function setTypePaging(id, target, url, param) {
            const page = document.getElementById(id + "-page").value;
            const amount = document.getElementById(id + "-amount").value;
            document.getElementById(id + '-page-prev')
                .addEventListener('click', (ev) => {
                    ev.preventDefault();
                    if (page !== "0") {
                        let prev = parseInt(page) - parseInt(amount);
                        document.getElementById(target).innerHTML = OpenDataSyncGET(url + param + "&page=" + prev);
                        setRender(id, target, url);
                    }
                });
            document.getElementById(id + '-page-next')
                .addEventListener('click', (ev) => {
                    ev.preventDefault();
                    if (document.getElementById(id + '-size').value === amount) {
                        const next = parseInt(page) + parseInt(amount);
                        document.getElementById(target).innerHTML = OpenDataSyncGET(url + "&page=" + next);
                        setRender(id, target, url);
                    }
                });
        }

        function setTypeSubmit(id, target, url) {
            document.getElementById(id + "-form")
                .addEventListener("submit", (ev) => {
                    const categ = document.querySelector(`#${id}-form select[name="categorycode"]`).selectedOptions[0].value;
                    const status = document.querySelector(`#${id}-form select[name="statuscode"]`).selectedOptions[0].value;
                    const param = "&categorycode=" + categ + "&statuscode=" + status + "";
                    params.set(param);
                    dropdown.set(categ);
                    document.getElementById(target).innerHTML = OpenDataSyncGET(url + param);
                    setRender(id, target, url);
                    ev.preventDefault();
                });
        }

        document.querySelectorAll('.home-tab')
            .forEach(value => value.addEventListener('click', (ev) => {
                tabEvent();
                ev.target.parentElement.className = "is-active";
                document.getElementById(ev.target.id.substr(4)).hidden = false;
            }));

        function tabEvent() {
            document.querySelectorAll('.home-div')
                .forEach(value => value.hidden = true);
            document.querySelectorAll('.tabs ul li').forEach(value => value.className = '');
            params.set("");
        }
    }
    render();
};
