
package iuh.edu.restdb.model;

import lombok.*;

import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private int departmentId;
    private double salary;

    public Employee(String name, int departmentId, double salary) {
        this.name = name;
        this.departmentId = departmentId;
        this.salary = salary;
    }
}
