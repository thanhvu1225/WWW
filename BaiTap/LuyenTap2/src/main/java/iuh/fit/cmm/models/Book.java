package iuh.fit.cmm.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {
    @Id
    private int id;
    private String title;
    private String author;
    private double price;
    private int publisherId;



    public Book(String title, String author, double price, int publisherId) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisherId = publisherId;
    }
}