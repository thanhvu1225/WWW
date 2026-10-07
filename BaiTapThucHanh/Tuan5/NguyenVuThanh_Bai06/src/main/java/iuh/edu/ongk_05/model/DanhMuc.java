
package iuh.edu.ongk_05.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class DanhMuc implements Serializable {

    private static final long serialVersionUID = 1L;

    private int maDM;
    private String tenDanhMuc;
    private String nguoiQuanLy;
    private String ghiChu;

    public DanhMuc(String tenDanhMuc, String nguoiQuanLy, String ghiChu) {
        this.tenDanhMuc = tenDanhMuc;
        this.nguoiQuanLy = nguoiQuanLy;
        this.ghiChu = ghiChu;
    }
}
