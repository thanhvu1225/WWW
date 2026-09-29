<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 9/24/2026
  Time: 9:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<div class="text-center mb-3">
    <span style="font-size: 13px; color: #555;">Checkout - Already registered? <a href="#">...</a></span>
</div>

<div class="card p-3 mx-auto" style="max-width: 600px; border: 1px solid #ccc; border-radius: 0;">
    <form action="thanhtoan" method="post">
        <table class="table table-borderless" style="font-size: 13px; margin-bottom: 0;">
            <tr>
                <td style="width: 140px; vertical-align: middle;">Fullname:</td>
                <td><input type="text" name="fullname" class="form-control form-control-sm" style="width: 220px;" required/></td>
            </tr>
            <tr>
                <td style="vertical-align: middle;">Shipping address:</td>
                <td><input type="text" name="address" class="form-control form-control-sm" style="width: 340px;" required/></td>
            </tr>
            <tr>
                <td style="vertical-align: middle;">Total price:</td>
                <td><input type="text" value="${cart != null ? cart.total : 0}" readonly class="form-control form-control-sm" style="width: 150px; background-color: #eee;"/></td>
            </tr>
            <tr>
                <td style="vertical-align: middle;">Payment method:</td>
                <td>
                    <div class="d-flex gap-3">
                        <label><input type="radio" name="payment" value="Paypal" checked/> Paypal</label>
                        <label><input type="radio" name="payment" value="ATM"/> ATM Debit</label>
                        <label><input type="radio" name="payment" value="Visa"/> Visa/Master card</label>
                    </div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <input type="submit" value="Save" class="btn btn-sm btn-secondary px-3"/>
                    <a href="giohang" class="btn btn-sm btn-secondary px-3">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<%@ include file="footer.jsp" %>
