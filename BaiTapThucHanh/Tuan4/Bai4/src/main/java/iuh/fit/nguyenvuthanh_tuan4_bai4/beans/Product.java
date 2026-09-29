/*
 * @ (#) Product.java   9/24/2026
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

public class Product implements Serializable {
    private int id;
    private String title;
    private String author;
    private String imgBook;
    private double price;
    private int quantity;

    public Product() {}

    public Product(int id, String title, String author, String imgBook, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.imgBook = imgBook;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getImgBook() { return imgBook; }
    public void setImgBook(String imgBook) { this.imgBook = imgBook; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}