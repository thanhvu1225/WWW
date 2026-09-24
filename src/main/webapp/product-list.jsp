<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<h2>Product Catalog</h2>
<p><a href="cart">View Cart</a></p>
<hr/>

<div style="display: flex; gap: 20px; flex-wrap: wrap;">
    <c:forEach var="p" items="${products}">
        <div style="border: 1px solid #ccc; padding: 15px; width: 220px;">
            <h3>${p.model}</h3>
            <p>Price: $${p.price}</p>
            <form action="cart" method="post">
                <input type="hidden" name="action" value="add"/>
                <input type="hidden" name="id" value="${p.id}"/>
                <input type="submit" value="Add to Cart"/>
            </form>
            <p><a href="product?id=${p.id}">Product Detail</a></p>
        </div>
    </c:forEach>
</div>
</body>
</html>