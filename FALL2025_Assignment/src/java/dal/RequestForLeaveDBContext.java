package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Employee;
import model.RequestForLeave;

public class RequestForLeaveDBContext extends DBContext<RequestForLeave> {

    @Override
    public ArrayList<RequestForLeave> list() {
        ArrayList<RequestForLeave> list = new ArrayList<>();
        try {
            String sql = "SELECT r.rid, r.[from], r.[to], r.reason, r.status, "
                       + "e.eid, e.ename "
                       + "FROM RequestForLeave r "
                       + "JOIN Employee e ON r.created_by = e.eid";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                RequestForLeave r = new RequestForLeave();
                r.setId(rs.getInt("rid"));
                r.setFrom(rs.getDate("from"));
                r.setTo(rs.getDate("to"));
                r.setReason(rs.getString("reason"));
                r.setStatus(rs.getInt("status"));

                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                r.setCreated_by(e);

                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public ArrayList<RequestForLeave> listByEmployeeId(int empId) {
        ArrayList<RequestForLeave> list = new ArrayList<>();
        try {
            String sql = "SELECT r.rid, r.[from], r.[to], r.reason, r.status, "
                       + "e.eid, e.ename "
                       + "FROM RequestForLeave r "
                       + "JOIN Employee e ON r.created_by = e.eid "
                       + "WHERE e.eid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, empId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                RequestForLeave r = new RequestForLeave();
                r.setId(rs.getInt("rid"));
                r.setFrom(rs.getDate("from"));
                r.setTo(rs.getDate("to"));
                r.setReason(rs.getString("reason"));
                r.setStatus(rs.getInt("status"));

                Employee e = new Employee();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("ename"));
                r.setCreated_by(e);

                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public void insert(RequestForLeave model) {
        try {
            String sql = "INSERT INTO RequestForLeave (created_by, [from], [to], reason, status, created_time) "
                       + "VALUES (?, ?, ?, ?, ?, GETDATE())";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, model.getCreated_by().getId());
            stm.setDate(2, model.getFrom());
            stm.setDate(3, model.getTo());
            stm.setString(4, model.getReason());
            stm.setInt(5, model.getStatus());
            stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public RequestForLeave get(int id) { 
        try {
        String sql = "SELECT rid, created_by, [from], [to], reason, status, processed_by "
                   + "FROM RequestForLeave WHERE rid = ?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            RequestForLeave r = new RequestForLeave();
            r.setId(rs.getInt("rid"));
            r.setReason(rs.getString("reason"));
            r.setFrom(rs.getDate("from"));
            r.setTo(rs.getDate("to"));
            r.setStatus(rs.getInt("status"));

            Employee created = new Employee();
            created.setId(rs.getInt("created_by"));
            r.setCreated_by(created);

            int processedId = rs.getInt("processed_by");
            if (processedId != 0) {
                Employee processed = new Employee();
                processed.setId(processedId);
                r.setProcessed_by(processed);
            }
            return r;
        }
    } catch (SQLException ex) {
        Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }

    @Override
    public void update(RequestForLeave model) { 
 try {
        String sql = "UPDATE RequestForLeave SET status = ?, processed_by = ? WHERE rid = ?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, model.getStatus());
        stm.setInt(2, model.getProcessed_by().getId());
        stm.setInt(3, model.getId());
        stm.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    @Override
    public void delete(RequestForLeave model) { }
    public ArrayList<RequestForLeave> listPaging(int pageIndex, int pageSize) {
    ArrayList<RequestForLeave> list = new ArrayList<>();
    try {
        String sql = "SELECT rid, [from], [to], reason, status, e.eid, e.ename " +
                     "FROM RequestForLeave r " +
                     "JOIN Employee e ON r.created_by = e.eid " +
                     "ORDER BY r.rid OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, (pageIndex - 1) * pageSize);
        stm.setInt(2, pageSize);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            RequestForLeave r = new RequestForLeave();
            r.setId(rs.getInt("rid"));
            r.setFrom(rs.getDate("from"));
            r.setTo(rs.getDate("to"));
            r.setReason(rs.getString("reason"));
            r.setStatus(rs.getInt("status"));

            Employee e = new Employee();
            e.setId(rs.getInt("eid"));
            e.setName(rs.getString("ename"));
            r.setCreated_by(e);

            list.add(r);
        }
    } catch (SQLException ex) {
        Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}
public int countAll() {
    try {
        String sql = "SELECT COUNT(*) AS total FROM RequestForLeave";
        PreparedStatement stm = connection.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        }
    } catch (SQLException ex) {
        Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0;
}
public ArrayList<RequestForLeave> listByEmployeeIdPaging(int empId, int pageIndex, int pageSize) {
    ArrayList<RequestForLeave> list = new ArrayList<>();
    try {
        String sql = "SELECT r.rid, r.[from], r.[to], r.reason, r.status, e.eid, e.ename "
                   + "FROM RequestForLeave r "
                   + "JOIN Employee e ON r.created_by = e.eid "
                   + "WHERE e.eid = ? "
                   + "ORDER BY r.rid OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, empId);
        stm.setInt(2, (pageIndex - 1) * pageSize);
        stm.setInt(3, pageSize);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            RequestForLeave r = new RequestForLeave();
            r.setId(rs.getInt("rid"));
            r.setFrom(rs.getDate("from"));
            r.setTo(rs.getDate("to"));
            r.setReason(rs.getString("reason"));
            r.setStatus(rs.getInt("status"));

            Employee e = new Employee();
            e.setId(rs.getInt("eid"));
            e.setName(rs.getString("ename"));
            r.setCreated_by(e);

            list.add(r);
        }
    } catch (SQLException ex) {
        Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}

public int countByEmployeeId(int empId) {
    try {
        String sql = "SELECT COUNT(*) AS total FROM RequestForLeave WHERE created_by = ?";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, empId);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        }
    } catch (SQLException ex) {
        Logger.getLogger(RequestForLeaveDBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return 0;
}

}
