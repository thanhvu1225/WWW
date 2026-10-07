
package iuh.edu.ongk_05.util;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static DataSource dataSource;

    static {
        try {
            InitialContext initialContext = new InitialContext();
            dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/quanlydanhmuc");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() throws SQLException {
        if(dataSource == null){
            throw new SQLException("DataSource chưa được khởi tạo");
        }
        return dataSource.getConnection();
    }
}
