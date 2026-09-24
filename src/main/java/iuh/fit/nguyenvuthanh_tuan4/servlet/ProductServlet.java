/*
 * @ (#) ProductServlet.java   9/24/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.nguyenvuthanh_tuan4.servlet;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 9/24/2026
 * @version 1.0
 */
import iuh.fit.nguyenvuthanh_tuan4.beans.Product;
import iuh.fit.nguyenvuthanh_tuan4.dao.ProductDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet({"/products", "/product"})
public class ProductServlet extends HttpServlet {
    private ProductDAO productDAO;

    @Resource(name = "jdbc/shopdb")
    private DataSource dataSource;

    @Override
    public void init() {
        productDAO = new ProductDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                Product product = productDAO.getProductById(id);
                if (product != null) {
                    req.setAttribute("product", product);
                    req.getRequestDispatcher("/product-detail.jsp").forward(req, resp);
                    return;
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                    return;
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
                return;
            }
        }

        List<Product> products = productDAO.getAllProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/product-list.jsp").forward(req, resp);
    }
}
