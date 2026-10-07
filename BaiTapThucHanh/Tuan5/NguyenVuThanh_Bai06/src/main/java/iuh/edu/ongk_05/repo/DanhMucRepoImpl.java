
package iuh.edu.ongk_05.repo;

import iuh.edu.ongk_05.model.DanhMuc;
import iuh.edu.ongk_05.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DanhMucRepoImpl implements DanhMucRepo{

    @Override
    public List<DanhMuc> getAll() {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "select MADM, TENDANHMUC, NGUOIQUANLY, GHICHU from danhmuc";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while(resultSet.next()){
                list.add(new DanhMuc(
                        resultSet.getInt("MADM"),
                        resultSet.getString("TENDANHMUC"),
                        resultSet.getString("NGUOIQUANLY"),
                        resultSet.getString("GHICHU")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<DanhMuc> searchByName(String keyword) {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "select MaDM, TENDANHMUC, NGUOIQUANLY, GHICHU from danhmuc where TENDANHMUC like ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setString(1,"%" + keyword + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    list.add(new DanhMuc(
                            resultSet.getInt("MADM"),
                            resultSet.getString("TENDANHMUC"),
                            resultSet.getString("NGUOIQUANLY"),
                            resultSet.getString("GHICHU")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public DanhMuc getById(int id) {
        String sql = "select MaDM, TENDANHMUC, NGUOIQUANLY, GHICHU from danhmuc where MADM = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return new DanhMuc(
                            resultSet.getInt("MADM"),
                            resultSet.getString("TENDANHMUC"),
                            resultSet.getString("NGUOIQUANLY"),
                            resultSet.getString("GHICHU")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean save(DanhMuc danhMuc) {
        String sql = "insert into danhmuc (TENDANHMUC, NGUOIQUANLY, GHICHU) values (?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setString(1, danhMuc.getTenDanhMuc());
            preparedStatement.setString(2, danhMuc.getNguoiQuanLy());
            preparedStatement.setString(3, danhMuc.getGhiChu());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean update(DanhMuc danhMuc) {
        String sql = "update danhmuc set TENDANHMUC = ?, NGUOIQUANLY = ?, GHICHU = ? where MADM = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setString(1, danhMuc.getTenDanhMuc());
            preparedStatement.setString(2, danhMuc.getNguoiQuanLy());
            preparedStatement.setString(3, danhMuc.getGhiChu());
            preparedStatement.setInt(4, danhMuc.getMaDM());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from danhmuc where MADM = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
