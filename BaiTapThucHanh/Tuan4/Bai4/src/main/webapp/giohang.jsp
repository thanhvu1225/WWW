<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/24/2026
  Time: 9:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<style>
  .cart-table th { background-color: #2b3a4a; color: #fff; font-size: 13px; font-weight: normal; text-align: center; }
  .cart-table td { font-size: 13px; vertical-align: middle; }
</style>

<div class="text-center mb-3">
  <h6 style="color: #444; font-weight: bold; letter-spacing: 0.5px;">YOUR SHOPPING CART</h6>
</div>

<c:choose>
  <c:when test="${empty cart.items}">
    <p class="text-center text-muted">Giỏ hàng của bạn đang trống!</p>
    <div class="text-center">
      <a href="sach" class="btn btn-sm btn-secondary">Tiếp tục mua sắm</a>
    </div>
  </c:when>
  <c:otherwise>
    <table class="table table-bordered cart-table">
      <thead>
      <tr>
        <th style="width: 90px;">Product ID</th>
        <th>Product name</th>
        <th style="width: 80px;">Price</th>
        <th style="width: 50px;">Qty</th>
        <th style="width: 90px;">Total</th>
        <th style="width: 70px;">Remove</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${cart.items}">
        <tr>
          <td class="text-center">pro0${item.product.id}</td>
          <td>${item.product.title} - Tác giả: ${item.product.author}</td>
          <td class="text-end">${item.product.price}</td>
          <td class="text-center">${item.quantity}</td>
          <td class="text-end">${item.subtotal}</td>
          <td class="text-center">
            <form action="giohang" method="post" style="margin: 0;">
              <input type="hidden" name="action" value="remove"/>
              <input type="hidden" name="productId" value="${item.product.id}"/>
              <button type="submit" class="btn btn-link p-0 text-decoration-underline" style="font-size: 12px; color: #333;">Remove</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <tr>
        <td colspan="4" class="text-end fw-bold">Total price</td>
        <td colspan="2" class="fw-bold">(VNĐ) ${cart.total}</td>
      </tr>
      </tbody>
    </table>

    <div class="d-flex gap-2">
      <a href="thanhtoan" class="btn btn-sm btn-secondary">Checkout</a>
      <a href="sach" class="btn btn-sm btn-secondary">Continue shopping</a>
    </div>
  </c:otherwise>
</c:choose>

<%@ include file="footer.jsp" %>