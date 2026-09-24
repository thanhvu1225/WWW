/*
 * @ (#) CartItemBean.java   9/24/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.nguyenvuthanh_tuan4_bai4.beans;

import java.io.Serializable;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 9/24/2026
 * @version 1.0
 */
public class CartItemBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private Product product;
    private int quantity;

    public CartItemBean() {}

    public CartItemBean(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Tính thành tiền cho từng cuốn sách
    public double getSubtotal() {
        if (product != null) {
            return product.getPrice() * quantity;
        }
        return 0.0;
    }
}
