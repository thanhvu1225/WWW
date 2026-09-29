<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<style>
    .book-card {
        border: 1px solid #999;
        padding: 10px;
        text-align: center;
        height: 100%;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        background-color: #fff;
    }
    .book-title {
        font-size: 13px;
        font-weight: bold;
        min-height: 38px;
        line-height: 1.3;
    }
    .book-img {
        height: 190px;
        width: 135px;
        object-fit: cover;
        margin: 5px auto;
        display: block;
        border: 1px solid #eee;
    }
    .book-info {
        font-size: 13px;
        color: #333;
        margin-top: 5px;
    }
</style>

<div class="row row-cols-3 g-3">
    <c:forEach var="p" items="${products}">
        <div class="col">
            <div class="book-card">
                <div class="book-title">${p.title} - Tác giả: ${p.author}</div>
                <div>
                    <img src="${pageContext.request.contextPath}/images/${p.imgBook}"
                         alt="${p.title}"
                         style="height: 190px; width: 135px; object-fit: cover; margin: 5px auto; display: block;" />
                </div>
                <div class="book-info">
                    Price: ${p.price}<br/>
                    Quantity: ${p.quantity}<br/>
                    <a href="chitietsach?id=${p.id}">Product details</a>
                </div>
                <div class="mt-2">
                    <form action="giohang" method="post" style="margin: 0;">
                        <input type="hidden" name="action" value="add"/>
                        <input type="hidden" name="id" value="${p.id}"/>
                        <input type="submit" value="Add to cart" class="btn btn-sm btn-outline-secondary" style="font-size: 12px;"/>
                    </form>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<%@ include file="footer.jsp" %>