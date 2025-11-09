package controller.request;

import controller.iam.BaseRequiredAuthenticationController;
import dal.RequestForLeaveDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Employee;
import model.RequestForLeave;
import model.iam.User;

@WebServlet("/request/review")
public class ReviewController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String action = req.getParameter("action");

        RequestForLeaveDBContext db = new RequestForLeaveDBContext();
        RequestForLeave r = db.get(id);
        if (r == null) {
            resp.getWriter().println("Don khong ton tai!");
            return;
        }

        Employee approver = new Employee();
        approver.setId(user.getEmployee().getId());

        if ("approve".equalsIgnoreCase(action)) {
            r.setStatus(1);
        } else if ("reject".equalsIgnoreCase(action)) {
            r.setStatus(2);
        } else {
            resp.getWriter().println("Khong hop le!");
            return;
        }

        r.setProcessed_by(approver);
        db.update(r);

        resp.sendRedirect(req.getContextPath() + "/request/list");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        doGet(req, resp, user);
    }
}
