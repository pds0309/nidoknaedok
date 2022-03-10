<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<section class="featured">
    <div>
        <input type="hidden" id="id-${id}-page" value="${start}"/>
        <input type="hidden" id="id-${id}-amount" value="${amount}"/>
        <input type="hidden" id="id-${id}-size" value="${bookshoplist.size()}"/>
        <div class="">
            <div class="has-text-center">
                <div class="level-item">
                    <h2 class="subtitle">&nbsp;</h2>
                </div>
            </div>
            <div class="p-1">
                <form id="id-${id}-form">
                    <div class="columns is-mobile">
                        <div class="column is-1">&nbsp;</div>
                        <div class="column is-3 control has-icons-left has-icons-right">
                            <label class="label">카테고리</label>
                            <div class="select">
                                <select name="categorycode">
                                    <option class="has-text-weight-bold" value="" selected>전체보기</option>
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
                        <div class="column is-3 control has-icons-left has-icons-right">
                            <label class="label">거래상태</label>
                            <div class="select">
                                <select name="statuscode">
                                    <option class="has-text-weight-bold" value="" selected>전체 보기</option>
                                    <option value="SUBMIT">등록중</option>
                                    <option value="PROCESSING">진행중</option>
                                    <option value="COMMIT">완료됨</option>
                                </select>
                            </div>
                        </div>
                        <div class="column  control has-icons-left has-icons-right">
                            <br>
                            <button class="button is-rounded-custom is-info" type="submit">검색하기</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <br>
        <div class="is-centered p-5">
            <c:forEach var="bookshops" items="${bookshoplist}" varStatus="i">
                <c:set var="book" value="${bookshops.getBook()}"/>
                <c:set var="bookshop" value="${bookshops.getBookShop()}"/>
                <c:set var="trader" value="${bookshops.getMember()}"/>
                <div class="p-1">
                    <article class="has-text-centered columns is-mobile">
                        <div class="column is-centered is-one-fifth-desktop is-one-fifth-tablet is-4-mobile">
                            <figure class="image is-4by5">
                                <img src="${book.getThumbnail()}" alt="">
                            </figure>
                        </div>
                        <div class="column has-text-left p-6-desktop">
                            <a class="is-size-2-desktop is-hidden-mobile p-3"
                               style="color: #7a0bbf">${book.getBookTitle()}</a>
                            <h2 class="is-size-6-mobile is-hidden-desktop is-hidden-tablet">${book.getBookTitle()}</h2>
                            <p class="is-size-4-desktop is-size-7-mobile mb-1">
                                <span>[${book.getAuthor()}]&nbsp;&nbsp;</span>
                                <span>₩<fmt:formatNumber value="${bookshop.getSellPrice()}" pattern="#,###"/>&nbsp;&nbsp;</span>
                                <span>${bookshop.getSellStatusId().getBookStatusDetail()}&nbsp;&nbsp;</span>
                            </p>
                            <p class="is-hidden-mobile tag is-rounded is-medium is-danger">${bookshop.getSelltypeId().getSelltypeDetail()}</p>
                            <p class="is-hidden-desktop is-hidden-tablet tag is-rounded is-success">${bookshop.getSelltypeId().getSelltypeDetail()}</p>
                            <p class="is-hidden-desktop is-hidden-tablet tag is-rounded is-danger">${bookshop.getCategory().getName()}</p>
                            <p class="is-hidden-mobile tag is-medium is-rounded is-success">${bookshop.getCategory().getName()}</p>
                            <c:set var="sellerShort" value="${bookshop.getSellerShort()}"/>
                            <c:if test="${sellerShort ne null}">
                                <p class="is-hidden-mobile tag is-rounded is-info is-medium">${bookshop.getSellerShort()}</p>
                                <p class="is-hidden-desktop is-hidden-tablet tag is-rounded is-info">
                                    <span>${fn:substring(bookshop.getSellerShort(),0,10)}..</span>
                                </p>&nbsp;
                            </c:if>
                            <p class="is-hidden-mobile mt-3"><br/>
                                    ${book.getDetail()}
                            </p>
                            <p class="mt-1">
                                <span class="is-size-6">등록자: <a>[${trader.getName()}]</a>&nbsp;</span>
                                <span class="is-size-6-desktop is-size-6-tablet is-size-7-mobile"
                                      style="color: #9c9595;text-align: right">
                                        ${fn:substring(bookshop.getCreatedAt(),0,16)}
                                </span>
                            </p>
                        </div>
                    </article>
                </div>
                <br><br>
            </c:forEach>
        </div>
        <div class="">
            <div class="has-text-center">
                <div class="level-item">
                    <div class="field has-addons has-addons-centered">
                        <div class="control">
                            <button id="id-${id}-page-prev" class="button">&lt;</button>
                        </div>
                        <div class="control">
                            <button id="id-${id}-page-next" class="button">&gt;</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>