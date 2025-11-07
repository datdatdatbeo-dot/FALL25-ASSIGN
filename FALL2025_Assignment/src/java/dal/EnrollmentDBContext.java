package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Employee;

public class EnrollmentDBContext extends DBContext<Employee> {

    public Employee getEmployeeByUserId(int uid) {
        Employee e = null;
        try {
            String sql = "SELECT e.eid, e.ename FROM Enrollment en "
                       + "JOIN Employee e ON en.eid = e.eid "
                       + "WHERE en.uid = ? AND en.active = 1";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, uid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi getEmployeeByUserId: " + ex.getMessage());
        } finally {
            closeConnection();
        }
        return e;
    }

    @Override
    public void insert(Employee model) {}
    @Override
    public void update(Employee model) {}
    @Override
    public void delete(Employee model) {}
    @Override
    public Employee get(int id) { return null; }
    @Override
    public java.util.ArrayList<Employee> list() { return null; }
}

