/*
 * @ (#) ProductDAO.java   9/24/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.nguyenvuthanh_tuan4.dao;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 9/24/2026
 * @version 1.0
 */
import iuh.fit.nguyenvuthanh_tuan4.beans.Product;
import iuh.fit.nguyenvuthanh_tuan4.util.DBUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private DBUtil dbUtil;

    public ProductDAO(DataSource dataSource) {
        this.dbUtil = new DBUtil(dataSource);
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection conn = dbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String model = rs.getString("name");
                String description = rs.getString("description");
                int quantity = 10;
                double price = rs.getDouble("price");
                String image = rs.getString("image");
                Product p = new Product(id, model, description, quantity, price, image);
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE ID = ?";
        try (Connection conn = dbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int proid = rs.getInt("id");
                    String model = rs.getString("name");
                    String description = rs.getString("description");
                    int quantity = 10;
                    double price = rs.getDouble("price");
                    String image = rs.getString("image");

                    return new Product(proid, model, description, quantity, price, image);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}