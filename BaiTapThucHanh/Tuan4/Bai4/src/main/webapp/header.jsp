<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>IUH Bookstore</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f7f7f7; font-family: Arial, sans-serif; }
        .site-container { width: 960px; margin: 20px auto; background: #fff; border: 1px solid #ccc; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        .top-banner { background: #5c6258 url('https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=960&h=100&fit=crop') no-repeat center; background-size: cover; height: 90px; display: flex; align-items: center; justify-content: space-between; padding: 0 20px; }
        .brand-title { background: rgba(0,0,0,0.4); color: #fff; font-size: 24px; font-weight: bold; padding: 5px 15px; border: 2px solid #fff; }
        .nav-links a { color: #fff; text-decoration: none; margin-left: 8px; font-weight: bold; font-size: 12px; padding: 6px 12px; background: rgba(0,0,0,0.35); border-radius: 2px; }
        .nav-links a:hover { background: rgba(0,0,0,0.6); }
        .sidebar { width: 220px; border-right: 1px solid #ddd; padding: 15px; flex-shrink: 0; }
        .sidebar h5 { font-size: 14px; font-weight: bold; color: #555; margin-bottom: 8px; }
        .sidebar p { font-size: 12px; color: #777; }
        .sidebar input[type="text"] { width: 100%; padding: 4px; font-size: 13px; margin-top: 5px; }
    </style>
</head>
<body>
<div class="site-container">
    <!-- Banner Header -->
    <div class="top-banner">
        <div class="brand-title">IUH BOOKSTORE</div>
        <div class="nav-links">
            <a href="sach">HOME</a>
            <a href="#">EXAMPLES</a>
            <a href="#">SERVICES</a>
            <a href="sach">PRODUCTS</a>
            <a href="#">CONTACT</a>
        </div>
    </div>

    <!-- Body Layout -->
    <div class="d-flex">
        <!-- Sidebar bên trái -->
        <div class="sidebar">
            <h5>ABOUT US</h5>
            <p>About us information will be here.... <a href="#" style="font-size: 11px;">Read More »</a></p>
            <hr/>
            <h5>SEARCH SITE</h5>
            <form action="sach" method="get">
                <input type="text" name="keyword" placeholder="Nhập từ khóa..."/>
            </form>
            <div class="mt-4">
                <a href="giohang" style="font-size: 13px;">Shopping cart (${cart != null ? cart.items.size() : 0})</a>
            </div>
        </div>

        <!-- Main Content bên phải -->
        <div class="flex-grow-1 p-3">