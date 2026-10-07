
package iuh.edu.ongk_05.service;

import iuh.edu.ongk_05.model.DanhMuc;
import iuh.edu.ongk_05.repo.DanhMucRepo;
import iuh.edu.ongk_05.repo.DanhMucRepoImpl;

import java.util.List;

public class DanhMucService {
    private final DanhMucRepo danhMucRepo;

    public DanhMucService() {
        this.danhMucRepo = new DanhMucRepoImpl();
    }

    public List<DanhMuc> getAll() {
        return danhMucRepo.getAll();
    }

    public List<DanhMuc> searchByName(String keyword) {
        return danhMucRepo.searchByName(keyword);
    }

    public DanhMuc getById(int id) {
        return danhMucRepo.getById(id);
    }

    public boolean save(DanhMuc danhMuc) {
        return danhMucRepo.save(danhMuc);
    }

    public boolean update(DanhMuc danhMuc) {
        return danhMucRepo.update(danhMuc);
    }

    public boolean delete(int id) {
        return danhMucRepo.delete(id);
    }
}
