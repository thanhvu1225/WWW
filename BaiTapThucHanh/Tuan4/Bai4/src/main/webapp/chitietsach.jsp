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

<div style="font-size: 14px; margin-bottom: 15px;">
  <strong>Product details:</strong> ${product.title} - Tác giả: ${product.author}
</div>

<div class="text-center" style="width: 250px;">
  <img src="images/${product.imgBook}" alt="${product.title}"
       style="width: 180px; height: 260px; object-fit: cover; border: 1px solid #ddd;"
       onerror="this.src='https://via.placeholder.com/180x260?text=No+Cover';"/>

  <div class="mt-3 text-start" style="font-size: 13px; margin-left: 35px;">
    <p class="mb-1"><strong>Price (VNĐ):</strong> ${product.price}</p>
    <p class="mb-2"><strong>Quantity:</strong> ${product.quantity}</p>

    <form action="giohang" method="post" class="mb-2">
      <input type="hidden" name="action" value="add"/>
      <input type="hidden" name="id" value="${product.id}"/>
      <input type="submit" value="Add to cart" class="btn btn-sm btn-outline-secondary" style="font-size: 12px;"/>
    </form>

    <a href="sach" style="text-decoration: underline; font-size: 12px;">Back to Product List</a>
  </div>
</div>

<%@ include file="footer.jsp" %>