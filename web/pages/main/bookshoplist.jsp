<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<section id="id-book-area1" class="featured">
    <div>
        <input type="hidden" id="id-list1-page" value="${page}" />
        <div>
            <div>
                <h2 class="subtitle is-4 has-text-centered">최근 등록 도서</h2>
            </div>
        </div><br>
        <br>
        <div>
            <div>
                <div class="field has-addons has-addons-centered">
                    <div class="control">
                        <button id="id-list1-page-prev" class="button is-small">&lt;</button>
                    </div>
                    <div class="control">
                        <button id="id-list1-page-next" class="button is-small">&gt;</button>
                    </div>
                </div>
            </div><br>
        </div>
    </div>
    <div class="columns is-mobile is-multiline is-centered">
        <c:forEach var="bookshops" items="${bookshoplist}" varStatus="status">
            <c:set var="book" value="${bookshops.getBook()}"/>
            <c:set var="bookshop" value="${bookshops.getBookShop()}"/>
            <div class="column is-3-desktop is-5-mobile p-5">
                <article class="has-text-centered">
                    <figure class="image is-4by5">
                        <img class="p-6-desktop" src="${book.getThumbnail()}" alt="">
                    </figure>
                    <h2 class="is-size-5-desktop is-hidden-mobile">${book.getBookTitle()}</h2>
                    <h2 class="is-size-6-mobile is-hidden-desktop is-hidden-tablet">
                            ${fn:substring(book.getBookTitle(),0,12)}...</h2>
                    <p class="is-size-6-desktop is-size-6-mobile">
                        <span style="text-align: left">[${book.getAuthor()}]&nbsp;</span>
                        <span style="text-align: right">&nbsp;
                            ₩<fmt:formatNumber value="${bookshop.getSellPrice()}" pattern="#,###"/>
                        </span>
                    </p><br>
                    <p class="tag is-rounded is-success is-medium">${bookshop.getSelltypeId().getSelltypeDetail()}</p>
                </article>
            </div>
        </c:forEach>
    </div>
</section>