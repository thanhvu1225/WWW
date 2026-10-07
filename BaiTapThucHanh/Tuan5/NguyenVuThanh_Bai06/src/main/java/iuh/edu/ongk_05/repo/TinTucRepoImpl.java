
package iuh.edu.ongk_05.repo;

import iuh.edu.ongk_05.model.TinTuc;
import iuh.edu.ongk_05.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TinTucRepoImpl implements TinTucRepo{
    @Override
    public List<TinTuc> getAll() {
        List<TinTuc> list = new ArrayList<>();
        String sql = "select MATT, TIEUDE, NOIDUNGTT, LIENKET, MADM from tintuc";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while (resultSet.next()){
                list.add(new TinTuc(
                        resultSet.getInt("MATT"),
                        resultSet.getString("TIEUDE"),
                        resultSet.getString("NOIDUNGTT"),
                        resultSet.getString("LIENKET"),
                        resultSet.getInt("MADM")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<TinTuc> getByMaDM(int id) {
        List<TinTuc> list = new ArrayList<>();
        String sql = "select MATT, TIEUDE, NOIDUNGTT, LIENKET, MADM from tintuc where MADM = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()){
                    list.add(new TinTuc(
                            resultSet.getInt("MATT"),
                            resultSet.getString("TIEUDE"),
                            resultSet.getString("NOIDUNGTT"),
                            resultSet.getString("LIENKET"),
                            resultSet.getInt("MADM")
                    ));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public TinTuc getById(int id) {
        String sql = "select MATT, TIEUDE, NOIDUNGTT, LIENKET, MADM from tintuc where MATT = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return new TinTuc(
                            resultSet.getInt("MATT"),
                            resultSet.getString("TIEUDE"),
                            resultSet.getString("NOIDUNGTT"),
                            resultSet.getString("LIENKET"),
                            resultSet.getInt("MADM")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean save(TinTuc tinTuc) {
        String sql = "insert into tintuc (TIEUDE, NOIDUNGTT, LIENKET, MADM) values (?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setString(1, tinTuc.getTieuDe());
            preparedStatement.setString(2, tinTuc.getNoiDungTT());
            preparedStatement.setString(3, tinTuc.getLienKet());
            preparedStatement.setInt(4, tinTuc.getMaDM());
            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from tintuc where MATT = ?";
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
