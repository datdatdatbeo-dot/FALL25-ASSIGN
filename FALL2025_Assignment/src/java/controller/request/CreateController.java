package controller.request;

import controller.iam.BaseRequiredAuthenticationController;
import dal.EnrollmentDBContext;
import dal.RequestForLeaveDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import model.Employee;
import model.RequestForLeave;
import model.iam.User;

@WebServlet(urlPatterns = "/request/create")
public class CreateController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        req.getRequestDispatcher("/view/request/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        String reason = req.getParameter("reason");
        Date from = Date.valueOf(req.getParameter("from"));
        Date to = Date.valueOf(req.getParameter("to"));

        // Lấy employee theo user ID (Enrollment)
        EnrollmentDBContext enrollDB = new EnrollmentDBContext();
        Employee emp = enrollDB.getEmployeeByUserId(user.getId());
        if (emp == null) {
            resp.getWriter().println("Khong tim thay employee cho user id: " + user.getId());
            return;
        }

        RequestForLeave r = new RequestForLeave();
        r.setCreated_by(emp);
        r.setFrom(from);
        r.setTo(to);
        r.setReason(reason);
        r.setStatus(0);

        RequestForLeaveDBContext db = new RequestForLeaveDBContext();
        db.insert(r);

        resp.sendRedirect(req.getContextPath() + "/request/list");
    }
}
