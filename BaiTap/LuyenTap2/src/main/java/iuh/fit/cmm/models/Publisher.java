/*
 * @ (#) Pulisher.java   11/1/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.cmm.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 11/1/2026
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Data
@Table(name = "publishers")
public class Publisher {

    @Id
    private int id;
    private String name;
    private String address;
    @Transient
    private List<Book> books;

}
