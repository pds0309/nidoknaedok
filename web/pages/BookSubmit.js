import {createElement} from "../utils/component.js";
import {OpenDataSyncGET, FetchData} from "../components/FetchData.js";
import {Submission} from "../components/Submission.js";
import {QuillEditor} from "../components/QuillEditor.js";

export const BookSubmit = (target) => {
    const submission = Submission(BookSubmit);
    const render = () => {
        target.replaceChildren(createElement(`<br>
        <section class="section">
            <form method="" action="">
                <div class="columns">
                    <div class="column is-6 is-offset-3">
                        <div class="has-text-centered">
                            <h2 class="title">도서 신청</h2><br>
                        </div>
                        <div class="field">
                            <h3 class="subtitle">신청 형태</h3>
                            <div class="control has-text-centered">
                                <label class="radio">
                                <input type="radio" name="selltype_id" value="SB" checked>&nbsp;대여등록</label>&nbsp;                                                                
<!--                                <input type="radio" name="selltype_id" value="SS">&nbsp;판매등록</label>&nbsp;-->
                            </div>
                        </div><br>
                        <div class="field">
                            <h3 class="subtitle">ISBN을 입력해주세요</h3>
                            <div class="control">
                                <div class="columns">
                                    <div class="column is-four-fifths">
                                        <input id="id-book-isbn" class="input is-rounded-custom" type="text" placeholder="ISBN 입력" name="isbn" >                                    
                                    </div>
                                    <div class="column">
                                        <button id="id-btn-findbook" class="button is-info is-rounded-custom">찾기</button>    
                                    </div>
                                </div>
                            </div>
                        </div><br><br>
                        <div id="id-isbn-explain" class="content has-text-centered">
                            <h4>ISBN ??</h4>
                            <p>책에 붙는 고유 식별자를 말해요</p>
                            <div class="has-text-centered">
                                <figure class="image is-128x128 is-inline-block">
                                    <img src="${contextPath}/images/service/isbn.gif">
                                </figure>
                            </div>                            
                        </div>     
                        <div class="columns" id="id-book-summary" hidden>
                            <div class="column has-text-centered">    
                                <figure class="image is-128x128 is-inline-block" style="width:80%;height:75%">
                                    <img id="id-book-image" src="${contextPath}/images/service/isbn.gif">
                                </figure><br><br>
                                <button id="id-btn-delete-book" class="button is-rounded-custom">초기화</button>                                                               
                            </div>
                            <div class="column">
                                <div class="field">
                                    <label class="label">책 제목</label>
                                    <div class="control has-icons-left has-icons-right">
                                        <input id="id-book-title" class="input is-rounded-custom" type="text" placeholder="책 제목" name="title" readonly>
                                        <span class="icon is-small is-left">
                                        <i class="fa-solid fa-book"></i>
                                        </span>
                                    </div>
                                </div>     
                                <div class="field">
                                    <label class="label">저자</label>
                                    <div class="control has-icons-left has-icons-right">
                                        <input id="id-book-author" class="input is-rounded-custom" type="text" placeholder="저자" name="author" readonly>
                                        <span class="icon is-small is-left">
                                        <i class="fa-solid fa-person"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="field">
                                    <label class="label">출판사</label>
                                    <div class="control has-icons-left has-icons-right">
                                        <input id="id-book-publisher" class="input is-rounded-custom" type="text" placeholder="출판사" name="publisher" readonly>
                                        <span class="icon is-small is-left">
                                        <i class="fa-solid fa-buildings"></i>
                                        </span>
                                    </div>
                                </div>
                                <div class="field">
                                    <label class="label">출판날짜</label>
                                    <div class="control has-icons-left has-icons-right">
                                        <input id="id-book-pubdate" class="input is-rounded-custom" type="text" placeholder="출판날짜" name="pubdate" readonly>
                                        <span class="icon is-small is-left">
                                        <i class="fa-solid fa-calendar-lines-pen"></i>
                                        </span>
                                    </div>
                                </div>                                                                                                                                                              
                            </div>                            
                        </div>                   
                        <div id="id-book-desc" class="field">
                            <label class="label">책 소개</label>
                            <div class="control has-icons-left has-icons-right">
                                <input id="id-book-description" class="textarea is-rounded-custom has-fixed-size"  placeholder="" name="description" readonly />
                            <span class="icon is-small is-left">
                            </span>
                            </div>
                        </div><br>
                        <div class="field">
                            <div class="columns">
                                <div class="column">
                            <div class="control has-icons-left has-icons-right">
                                <label class="label">카테고리</label>
                                <div class="select">
                                    <select name="category">
                                        <option value="C01">문학,인물</option>
                                        <option value="C02">유아,아동</option>
                                        <option value="C03">소설,수필</option>
                                        <option value="C04">교육,전문</option>
                                        <option value="C05">역사,문화</option>
                                        <option value="C06">철학,심리</option>
                                        <option value="C08">만화,오락</option>
                                        <option value="C09">영화,음반</option>
                                        <option value="C10">총류,전집</option>
                                    </select>
                                </div>                                
                            </div>                                
                                </div>                          
                            </div>
                        </div><br>
                        <div class="field">
                            <label class="label">짧은 Comment(20글자만 적용)</label>
                            <div class="control has-icons-left has-icons-right">
                                <input class="input is-rounded-custom" type="text" placeholder=" 코멘트" name="seller_short" />
                            <span class="icon is-small is-left">
                                <i class="fa-solid fa-martini-glass"></i>
                            </span>
                            </div>
                        </div><br>
                        <div class="field">
                            <label class="label">게시할 내용</label>
                            <div id="editor" class="control has-icons-left has-icons-right" style="height: 400px"></div>
                        </div><br>
                        <div class="field">
                            <button id="id-btn-submit" class="button is-info is-rounded-custom is-fullwidth">등록하기</button>
                        </div><br>
                        </div><br><br>                                                                                                                                   
                        </div><br><br>                                            
                    </div>
                </div>
            </form>
        </section>`
        ));
        setEvent();
    }

    const setEvent = () => {
        QuillEditor("#editor");

        document.getElementById("id-btn-findbook")
            .addEventListener('click', (ev) => {
                ev.preventDefault();
                const isbn = document.getElementById("id-book-isbn").value.trim();
                if (isbn === '') {
                    alert("ISBN을 입력해주세요");
                    return;
                }
                const result = JSON.parse(OpenDataSyncGET(`books?isbn=${isbn}`));
                if (result.status !== 200) {
                    alert("해당 ISBN으로 도서를 찾을 수 없습니다!");
                    return;
                }
                document.getElementById('id-book-summary').hidden = false;
                document.getElementById('id-book-image').src = result.data.image;
                document.getElementById('id-book-title').value = result.data.title;
                document.getElementById('id-book-image').value = result.data.image;
                document.getElementById('id-book-pubdate').value = result.data.pubdate;
                document.getElementById('id-book-description').value = result.data.description;
                document.getElementById('id-book-author').value = result.data.author;
                document.getElementById('id-book-publisher').value = result.data.publisher;
                // 상태 저장으로 유효성 검증할 필요가 없다.
                submission.set(result.data);
            });

        document.getElementById('id-btn-delete-book')
            .addEventListener('click', (ev) => {
                document.querySelectorAll('input[type="text"]')
                    .forEach(value => value.value = '');
                ev.preventDefault();
            });

        document.getElementById("id-btn-submit")
            .addEventListener('click', (ev) => {
                ev.preventDefault();
                const request = submission.get[0];
                request.selltype_id = document.querySelector('input[type="radio"]:checked').value;
                request.seller_comment = "" + document.querySelector(".ql-editor").innerHTML + "";
                request.category = document.querySelector('select[name="category"]').selectedOptions[0].value;
                request.image = document.getElementById('id-book-image').src;
                request.seller_short = document.querySelector('input[name="seller_short"]').value;
                (async () => {
                    const result = await FetchData(contextPath + "/bookshops", "POST", 'application/json', JSON.stringify(request));
                    if (result.status === 200) {
                        location.replace(contextPath + "/");
                        return;
                    }
                    alert("등록에 실패했습니다. 입력을 확인해주세요");
                })();
            });
    }
    render();
};