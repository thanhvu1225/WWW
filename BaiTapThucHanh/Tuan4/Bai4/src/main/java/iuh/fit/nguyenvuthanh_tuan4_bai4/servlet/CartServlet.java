/*
 * @ (#) CartServlet.java   9/24/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.nguyenvuthanh_tuan4_bai4.servlet;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 9/24/2026
 * @version 1.0
 */
import iuh.fit.nguyenvuthanh_tuan4_bai4.beans.CartBean;
import iuh.fit.nguyenvuthanh_tuan4_bai4.beans.Product;
import iuh.fit.nguyenvuthanh_tuan4_bai4.dao.ProductDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.sql.DataSource;
import java.io.IOException;

@WebServlet({"/giohang", "/thanhtoan"})
public class CartServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Resource(name = "jdbc/shopdb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/thanhtoan".equals(path)) {
            req.getRequestDispatcher("/thanhtoan.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/giohang.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        HttpSession session = req.getSession();
        CartBean cart = (CartBean) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartBean();
            session.setAttribute("cart", cart);
        }

        if ("/thanhtoan".equals(path)) {
            // Xử lý lưu đơn hàng hoặc reset giỏ hàng khi bấm Save
            cart.clear();
            resp.sendRedirect("sach");
            return;
        }

        String action = req.getParameter("action");
        if ("add".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product p = productDAO.getProductById(id);
            cart.addProduct(p);
        } else if ("remove".equals(action)) {
            int id = Integer.parseInt(req.getParameter("productId"));
            cart.removeProduct(id);
        }

        resp.sendRedirect("giohang");
    }
}
