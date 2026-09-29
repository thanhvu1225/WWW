/*
 * @ (#) DBUtil.java   9/24/2026
 *
 * Copyright (c) 2026 IUH. All rights reserved.
 */

package iuh.fit.nguyenvuthanh_tuan4.util;

/*
 * @description: this class represents
 * @author: Nguyen Vu Thanh
 * @created: 9/24/2026
 * @version 1.0
 */
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private DataSource dataSource;

    public DBUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lấy Connection từ Pool", e);
        }
    }
}
