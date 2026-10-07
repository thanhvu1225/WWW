
package iuh.edu.ongk_05.repo;

import iuh.edu.ongk_05.model.DanhMuc;

import java.util.List;

public interface DanhMucRepo {
    List<DanhMuc> getAll();
    List<DanhMuc> searchByName(String keyword);
    DanhMuc getById(int id);
    boolean save(DanhMuc danhMuc);
    boolean update(DanhMuc danhMuc);
    boolean delete(int id);
}
