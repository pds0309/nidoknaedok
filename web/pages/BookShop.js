import {createElement} from "../utils/component.js";
import {OpenDataSyncGET, FetchData} from "../components/FetchData.js";
import {QuillEditor} from "../components/QuillEditor.js";
import {EditorIconEvent} from "../components/EditorIconEvent.js";

export const BookShop = (target) => {
    const currentURL = new URL(window.location);
    const bookshopResult = JSON.parse(OpenDataSyncGET(contextPath + "/bookshops?bookshopid=" + currentURL.searchParams.get("bookshopid") + ""));
    if (bookshopResult.status !== 200) {
        window.location.href = contextPath + "/error";
    }
    const bookshopBookMemberVO = bookshopResult.data;
    const book = bookshopBookMemberVO.book;
    const bookshop = bookshopBookMemberVO.bookShop;
    const member = bookshopBookMemberVO.member;
    const render = () => {

        target.replaceChildren(createElement(`            <div class="container">
                <br>
                <p class="is-hidden-mobile"><br><br></p>
                <div class="hero-body">
                    <div class="column is-8 is-offset-2 is-full">
                        <h1 class="title is-spaced">${book.bookTitle}</h1>
                        <h1 class="is-hidden subtitle is-spaced has-text-danger">거래신청하셨습니다.</h1><br>
                        <div class="columns">
                            <p class="tag is-rounded is-medium is-danger">${bookshop.sell_status_id.bookStatusDetail}</p>&nbsp;
                            <p class="tag is-rounded is-medium is-info">${bookshop.selltype_id.selltypeDetail}</p>&nbsp;
                            <p class="tag is-rounded is-medium is-success">${bookshop.category.name}</p>&nbsp;
                        </div><br>
                        <div class="columns">
                            <div class="colume is-full-desktop is-full-tablet is-half-mobile">
                                <span>저자: ${book.author}</span>&nbsp;|&nbsp;
                                <span>출판사: ${book.publisher}</span>&nbsp;
                                <span class="is-hidden-mobile">|</span>&nbsp;
                            </div>
                            <div class="colume is-full-desktop is-full-tablet is-half-mobile">
                                <span>출간일: ${book.publishTime.split(" ")[0]}</span>&nbsp;|&nbsp;
                                <span>등록일: 2021/04/09 18:45</span>&nbsp;
                            </div>
                        </div>
                        <hr>
                        <div class="columns">
                            <div class="column is-half-desktop is-half-tablet is-full-mobile has-text-centered p-1">
                                <figure class="image is-inline-block is-128x128">
                                    <img id="id-book-image"
                                        src="${book.thumbnail}">
                                </figure>
                                <br>
                                <br>
                                <p class="is-hidden-desktop is-hidden-tablet"><br><br></p>
                            </div>
                            <div class="column is-half-desktop is-half-tablet is-full-mobile">
                                <br>
                                <div class="columns is-mobile">
                                    <div class="column is-one-quarter has-text-left ml-4" style="white-space: nowrap;">
                                        <p class="font-weight-bold subtitle">ISBN</p>
                                        <p class="font-weight-bold subtitle">가격</p>
                                        <p class="font-weight-bold subtitle">코멘트</p>
                                    </div>
                                    <div class="column" style="white-space: nowrap;">
                                        <p class="subtitle">${book.bookId}</p>
                                        <p><span class="subtitle">${bookshop["sell_price"].toLocaleString('ko-KR')}</span> 원</p>
                                        <p class="mt-5">
                                            <small>${bookshop.seller_short === null ? '코멘트가 없습니다' : bookshop.seller_short}</small>
                                        </p>                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div class="column is-8 is-offset-2 is-full">
                        <h1 class="subtitle is-spaced">책 소개</h1>
                        <div class="columns p-2 box">
                            <p class="p-5 has-text-grey-dark">${book.detail}</p>
                        </div>
                        <hr>
                    </div>
                    <div class="column is-8 is-offset-2 is-full">
                        <h1 class="subtitle is-spaced">거래 등록자</h1>
                        <div class="columns">
                            <div class="column is-half-desktop is-half-tablet is-full-mobile">
                                <div class="has-text-centered">
                                    <figure class="image is-128x128 is-inline-block">
                                        <img class="is-rounded"
                                            src="${member.profileImage}"
                                            alt="" style="height: inherit">
                                    </figure>
                                    <br><br>
                                    <h2 class="subtitle">${member.name}&nbsp;<small>님</small></h2>
                                </div>
                            </div>
                            <div class="column is-half-desktop is-half-tablet is-full-mobile">
                                <div class="has-text-left">
                                    <p class="subtitle font-weight-bold">소개</p>
                                    <p class="is-6 p-2 has-text-grey-dark">${member.introduction === null ? '소개가 없습니다' : member.introduction}</p>
                                </div>
                            </div><br>
                        </div>
                    </div>
                    <div class="column is-8 is-offset-2 is-full">
                        <details>
                            <summary class="subtitle is-spaced">${member.name}&nbsp;<small>님 상세보기</small></summary>
                            <div class="column is-8 is-offset-2 is-full">
                                <h1 class="subtitle is-spaced">가입일자</h1>
                                    <p class="has-text-grey">${member.createdAt.substr(0, 10)}</p>
                            </div>
                            <div class="column is-8 is-offset-2 is-full">
                                <h1 class="subtitle is-spaced">주소</h1>
                                    <p class="has-text-grey">${member.address}</p>
                            </div>
                            <br>
                            <div class="is-mobile tile is-ancestor has-text-centered columns">
                                <div class="tile is-parent is-4-desktop is-4-tablet is-3-mobile column">
                                    <article class="tile is-child box p-3">
                                        <p class="is-4-mobile is-1-desktop has-text-danger">0<span class="is-6 has-text-grey">회</span></p>
                                        <p class="is-6-mobile is-4-desktop">대여/판매등록</p>
                                    </article>
                                </div>
                                <div class="tile is-parent is-4-desktop is-4-tablet is-3-mobile column">
                                    <article class="tile is-child box p-3">
                                        <p class="is-4-mobile is-1-desktop has-text-danger">14<span class="is-6 has-text-grey">회</span></p>
                                        <p class="is-6-mobile is-4-desktop">대여/판매성공</p>
                                    </article>
                                </div>
                                <div class="tile is-parent is-4-desktop is-4-tablet is-3-mobile column">
                                    <article class="tile is-child box p-3">
                                        <p class="is-4-mobile is-1-desktop has-text-danger">14<span class="is-6 has-text-grey">회</span></p>
                                        <p class="is-6-mobile is-4-desktop">대출/구매성공</p>
                                    </article>
                                </div>
                            </div>
                        </details>
                        <hr>
                    </div>
                    <div class="column is-8 is-offset-2 is-full">
                        <h1 class="subtitle is-spaced">게시 내용</h1>
                        <div class="columns">
                            <div class="column is-full">
                                <div class="has-background-white-bis pl-3 pt-5 pb-5 user-content-body ql-editor">
                                    ${bookshop.seller_comment === null ? '코멘트가 없습니다' : bookshop.seller_comment}
                                </div>
                            </div><br>
                        </div><hr>
                        <div class="columns is-mobile i-am-client">
                            <div class="column is-half has-text-right">
                                    <button class="button is-info is-rounded-custom">신청하기</button>
                            </div>
                            <div class="column is-half has-text-left">
                                <button class="button is-rounded-custom">쪽지전송</button>
                            </div><br>
                        </div>
                        <div class="columns is-mobile i-am-trader" style="display: none">
                            <div class="column is-half has-text-right">
                                    <button id="id-btn-update" class="button is-info is-rounded-custom">등록수정</button>
                            </div>
                            <div class="column is-half has-text-left">
                                <button id="id-btn-delete" class="button is-rounded-custom">등록취소</button>
                            </div><br>
                        </div>                            
                        <div class="">
                            <form>
                                <br>
                                <div id="id-div-update" class="field box is-hidden">
                                    <h2 class="subtitle">가격&nbsp;<a class="class-update-icon">
                                    <i class="fa-solid fa-pencil"></i></a></h2>
                                    <input id="id-price" style="background-color: #efecec"
                                        class="input is-rounded-custom" type="number" readonly="" value="" placeholder="${bookshop.sell_price}"><br><br>
                                    <h2 class="subtitle">코멘트&nbsp;<a class="class-update-icon"><i
                                                class="fa-solid fa-pencil"></i></a></h2>
                                    <input id="id-short" style="background-color: #efecec"
                                        class="input is-rounded-custom" type="text" readonly="" value="" 
                                        placeholder="${bookshop.seller_short === null ? '코멘트가 없습니다' : bookshop.seller_short}"><br><br>
                                    <h2 class="subtitle">게시 내용&nbsp;</h2>
                                    <div id="editor" class="control has-icons-left has-icons-right" style="height: 400px"></div><br><br>
                                        <div class="has-text-centered columns is-mobile">
                                            <div class="column is-half">
                                                <button class="button is-info is-rounded-custom">수정하기</button>                                            
                                            </div>
                                            <div id="id-btn-update-cancel" class="column">
                                                <button class="button is-rounded-custom">수정취소</button>                                            
                                            </div>                                            
                                        </div>
                                </div>
                                <br>
                            </form>
                        </div>                                           
                        <hr><br>
                    </div>
                    <div class="column is-8 is-offset-2 is-full i-am-trader" style="display: none">
                        <h1 class="subtitle is-spaced">거래신청한 회원들이예요!</h1>
                        <small class="has-text-danger">&nbsp;※ 잠깐! 충분한 대화는 나누셨나요??</small><br>
                        <div class="columns is-mobile box">
                            <div class="column is-9">
                                    <p class="subtitle">이름이름이름이름이름이름</p>
                                    <p><span>[주소]&nbsp;</span><span class="has-text-grey">경기도 광명시 하안 1동</span></p>
                                    <p><span>[일시]&nbsp;</span><span class="has-text-grey">2022/04/06 16:57</span></p>
                                    <p><span>[메모]&nbsp;</span><span class="has-text-grey">치킨무는 뺴주시고 단무지 많이 주세요</span></p>
                            </div>
                            <div class="column has-text-centered">
                                <p>&nbsp;</p><p>&nbsp;</p>    
                                <button class="button is-info is-rounded-custom mb-1 mr-1">수락</button>
                                <button class="button is-danger is-rounded-custom mr-1">거절</button>
                            </div>
                        </div><br><br>
                        <div class="columns is-mobile box">
                            <div class="column is-9">
                                    <p class="subtitle">이름이름이름이름이름이름</p>
                                    <p><span>[주소]&nbsp;</span><span class="has-text-grey">경기도 광명시 하안 1동</span></p>
                                    <p><span>[일시]&nbsp;</span><span class="has-text-grey">2022/04/06 16:57</span></p>
                                    <p><span>[메모]&nbsp;</span><span class="has-text-grey">치킨무는 뺴주시고 단무지 많이 주세요</span></p>
                            </div>
                            <div class="column has-text-centered">
                                <p>&nbsp;</p><p>&nbsp;</p>    
                                <button class="button is-info is-rounded-custom mb-1 mr-1">수락</button>
                                <button class="button is-danger is-rounded-custom mr-1">쪽지</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`
        ));
        setEvent();
    }

    const setEvent = () => {
        QuillEditor("#editor");
        EditorIconEvent('.class-update-icon');
        document.querySelector('#editor .ql-editor').innerHTML = bookshop.seller_comment + "";

        if (bookshopBookMemberVO.itIsMe) {
            document.querySelectorAll('.i-am-trader')
                .forEach(value => value.style.display = "");
            document.querySelectorAll('.i-am-client')
                .forEach(value => value.style.display = "none");
        }

        document.getElementById("id-btn-update")
            .addEventListener("click", () => {
                const updateElement = document.getElementById("id-div-update");
                updateElement.classList.remove("is-hidden");
            });

        document.getElementById("id-btn-update-cancel")
            .addEventListener("click", (ev) => {
                ev.preventDefault();
                const updateElement = document.getElementById("id-div-update");
                updateElement.classList.add("is-hidden");
            });

        document.querySelector("form")
            .addEventListener("submit", (ev) => {
                ev.preventDefault();
                const price = document.getElementById("id-price").value;
                const short = document.getElementById("id-short").value;
                const comment = document.querySelector('#editor .ql-editor').innerHTML + "";

                if (price !== "" && parseInt(isNaN(price))) {
                    alert("가격 입력을 확인하세요");
                    return;
                }
                const request = {
                    sell_price: parseInt(price),
                    seller_short: short,
                    seller_comment: comment,
                    seller_id: member.id,
                    bookshop_id: bookshop.bookshop_id,
                };
                (async () => {
                    const result = await FetchData(contextPath + "/bookshops", "PUT", "application/json", JSON.stringify(request));
                    if (result.status === 200) {
                        result.data.result === 1 ? alert("수정 성공!") : alert("수정 실패");
                        location.reload();
                        return;
                    }
                    alert(result.error.message);
                })();
            });
    }
    render();
};