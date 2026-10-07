
package iuh.edu.ongk_05.service;

import iuh.edu.ongk_05.model.TinTuc;
import iuh.edu.ongk_05.repo.TinTucRepo;
import iuh.edu.ongk_05.repo.TinTucRepoImpl;

import java.util.List;

public class TinTucService {
    private final TinTucRepo tinTucRepo;

    public TinTucService() {
        this.tinTucRepo = new TinTucRepoImpl();
    }

    public List<TinTuc> getAll() {
        return tinTucRepo.getAll();
    }

    public List<TinTuc> getByMaDM(int id) {
        return tinTucRepo.getByMaDM(id);
    }

    public TinTuc getById(int id) {
        return tinTucRepo.getById(id);
    }

    public String validate(TinTuc tinTuc){
        if(tinTuc == null){
            return "Dữ liệu tin tức không được rỗng";
        }
        if(tinTuc.getTieuDe() == null && tinTuc.getTieuDe().trim().isEmpty()){
            return "Tiêu đề tin tức không được rỗng";
        }
        if(tinTuc.getNoiDungTT() == null && tinTuc.getNoiDungTT().trim().isEmpty()){
            return "Nội dung tin tức không được rỗng";
        }
        if(!tinTuc.getNoiDungTT().matches("^.{1,255}$")){
            return "Nội dung tin tức không quá 255 ký tự";
        }
        if(tinTuc.getLienKet() == null && tinTuc.getLienKet().trim().isEmpty()){
            return "Liên kết tin tức không được rỗng";
        }
        if(!tinTuc.getLienKet().matches("^http://.+")){
            return "Liên kết phải bắt đầu bởi http://";
        }
        if(tinTuc.getMaDM() <= 0){
            return "Mã danh mục không được rỗng";
        }
        return null;
    }

    public boolean save(TinTuc tinTuc) {
        return tinTucRepo.save(tinTuc);
    }

    public boolean delete(int id) {
        return tinTucRepo.delete(id);
    }
}
