/*
 * @ (#) CartBean.java   9/24/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.nguyenvuthanh_tuan4_bai4.beans;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 9/24/2026
 * @version 1.0
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<CartItemBean> items;

    public CartBean() {
        items = new ArrayList<>();
    }

    public List<CartItemBean> getItems() {
        return items;
    }

    // Thêm sản phẩm vào giỏ
    public void addProduct(Product p) {
        if (p == null) return;
        for (CartItemBean item : items) {
            if (item.getProduct().getId() == p.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItemBean(p, 1));
    }

    // Xóa sản phẩm khỏi giỏ theo id sách
    public void removeProduct(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    // Cập nhật số lượng
    public void updateQuantity(int productId, int quantity) {
        for (CartItemBean item : items) {
            if (item.getProduct().getId() == productId) {
                if (quantity > 0) {
                    item.setQuantity(quantity);
                } else {
                    removeProduct(productId);
                }
                return;
            }
        }
    }

    // Tính tổng tiền toàn bộ giỏ hàng
    public double getTotal() {
        double total = 0.0;
        for (CartItemBean item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Xóa toàn bộ giỏ hàng
    public void clear() {
        items.clear();
    }
}
