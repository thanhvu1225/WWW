
package iuh.edu.ongk_05.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class TinTuc implements Serializable {

    private static final long serialVersionUID = 1L;

    private int maTT;
    private String tieuDe;
    private String noiDungTT;
    private String lienKet;
    private int maDM;

    public TinTuc(String tieuDe, String noiDungTT, String lienKet, int maDM) {
        this.tieuDe = tieuDe;
        this.noiDungTT = noiDungTT;
        this.lienKet = lienKet;
        this.maDM = maDM;
    }
}
