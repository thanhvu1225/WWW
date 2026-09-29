/*
 * @ (#) ProductServlet.java   9/24/2026
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
import iuh.fit.nguyenvuthanh_tuan4_bai4.beans.Product;
import iuh.fit.nguyenvuthanh_tuan4_bai4.dao.ProductDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet({"/sach", "/chitietsach"})
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO;

    @Resource(name = "jdbc/shopdb")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            productDAO = new ProductDAO(dataSource);
        } catch (Exception e) {
            throw new ServletException("Lỗi khởi tạo ProductDAO trong ProductServlet", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Thiết lập mã hóa UTF-8
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String path = req.getServletPath();
        String idStr = req.getParameter("id");
        String keyword = req.getParameter("keyword");

        // 1. Trường hợp xem chi tiết 1 cuốn sách: URL /chitietsach?id=...
        if ("/chitietsach".equals(path) && idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                Product product = productDAO.getProductById(id);
                if (product != null) {
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("/chitietsach.jsp").forward(req, resp);
                    return;
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy cuốn sách này!");
                    return;
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Mã sách không hợp lệ!");
                return;
            }
        }

        // 2. Trường hợp lấy danh sách sách hoặc tìm kiếm: URL /sach hoặc /sach?keyword=...
        List<Product> products;
        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productDAO.searchProducts(keyword.trim());
        } else {
            products = productDAO.getAllProducts();
        }

        // Gửi danh sách qua trang danhsach.jsp để render
        req.setAttribute("products", products);
        req.getRequestDispatcher("/danhsach.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
