<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/24/2026
  Time: 8:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Product Detail</title>
</head>
<body>
<h2>Product Detail</h2>
<div style="border: 1px solid #333; padding: 20px; width: 300px;">
  <p><strong>Id:</strong> ${product.id}</p>
  <p><strong>Model:</strong> ${product.model}</p>
  <p><strong>Description:</strong> ${product.description}</p>
  <p><strong>Quantity In Stock:</strong> ${product.quantity}</p>
  <p><strong>Price:</strong> $${product.price}</p>

  <form action="cart" method="post">
    <input type="hidden" name="action" value="add"/>
    <input type="hidden" name="id" value="${product.id}"/>
    <input type="submit" value="Add to Cart"/>
  </form>
</div>
<br/>
<a href="products">Back to Product List</a>
</body>
</html>
