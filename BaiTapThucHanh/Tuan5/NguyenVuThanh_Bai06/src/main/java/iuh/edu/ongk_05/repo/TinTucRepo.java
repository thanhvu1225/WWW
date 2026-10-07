
package iuh.edu.ongk_05.repo;

import iuh.edu.ongk_05.model.TinTuc;

import java.util.List;

public interface TinTucRepo {
    List<TinTuc> getAll();
    List<TinTuc> getByMaDM(int id);
    TinTuc getById(int id);
    boolean save(TinTuc tinTuc);
    boolean delete(int id);
}
