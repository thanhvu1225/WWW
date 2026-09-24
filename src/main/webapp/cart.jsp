<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/24/2026
  Time: 8:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Shopping Cart</title>
</head>
<body>
<h2>Your Cart</h2>

<c:choose>
  <c:when test="${empty cart.items}">
    <p>Cart is empty!</p>
  </c:when>
  <c:otherwise>
    <table border="1" cellpadding="8" cellspacing="0">
      <thead>
      <tr>
        <th>Model</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Subtotal</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="item" items="${cart.items}">
        <tr>
          <td>${item.product.model}</td>
          <td>
            <form action="cart" method="post" style="display:inline;">
              <input type="hidden" name="action" value="update"/>
              <input type="hidden" name="productId" value="${item.product.id}"/>
              <input type="number" name="quantity" value="${item.quantity}" min="0" style="width: 50px;"/>
              <input type="submit" value="Update"/>
            </form>
          </td>
          <td>$${item.product.price}</td>
          <td>$${item.subtotal}</td>
          <td>
            <form action="cart" method="post" style="display:inline;">
              <input type="hidden" name="action" value="remove"/>
              <input type="hidden" name="productId" value="${item.product.id}"/>
              <input type="submit" value="Remove"/>
            </form>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <h3>Total: $${cart.total}</h3>

    <form action="cart" method="post">
      <input type="hidden" name="action" value="clear"/>
      <input type="submit" value="Clear Cart"/>
    </form>
  </c:otherwise>
</c:choose>

<br/>
<a href="products">Continue Shopping</a>
</body>
</html>
