
package iuh.edu.restdb.model;

import lombok.*;

import java.io.Serializable;


@NoArgsConstructor
@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    public Department(String name) {
        this.name = name;
    }
}
