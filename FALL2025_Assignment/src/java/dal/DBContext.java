
package dal;

import model.BaseModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DBContext<T extends BaseModel> {

    protected Connection connection;

    public DBContext() {
        try {
            // ⚙️ Cấu hình kết nối SQL Server
            String serverName = "localhost";       // hoặc "127.0.0.1"
            String portNumber = "1433";
            String dbName = "FALL25_Assignment";   // đúng với script SQL của bro
            String username = "sa";                // đổi theo tài khoản SQL Server thật
            String password = "12345";             // đổi theo mật khẩu SQL thật

            // ✅ Bắt buộc dùng encrypt=false nếu server chưa cấu hình SSL
            String url = "jdbc:sqlserver://" + serverName + ":" + portNumber
                       + ";databaseName=" + dbName
                       + ";encrypt=false;trustServerCertificate=true;loginTimeout=30;";

            // Nạp driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Mở kết nối
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Kết nối SQL Server thành công: " + dbName);
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Không tìm thấy JDBC Driver!");
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            System.out.println("❌ Lỗi kết nối SQL Server: " + e.getMessage());
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Đã đóng kết nối SQL Server.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public abstract ArrayList<T> list();
    public abstract T get(int id);
    public abstract void insert(T model);
    public abstract void update(T model);
    public abstract void delete(T model);
}
