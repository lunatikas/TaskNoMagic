package lt.tomas.test.model.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteJDBCDriverConnection {
    private String JDBC_CLASS = "org.sqlite.JDBC";
    private String DATABASE = "data.db";

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName(JDBC_CLASS);
            String url = "jdbc:sqlite:"+(new File((getClass().getClassLoader()).getResource(DATABASE).getFile())).getAbsolutePath();
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
        } catch (ClassNotFoundException ce) {
        }
        return conn;
    }

    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {

            }
        }
    }
}

