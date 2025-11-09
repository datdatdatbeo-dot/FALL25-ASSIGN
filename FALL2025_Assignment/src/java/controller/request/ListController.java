package controller.request;

import controller.iam.BaseRequiredAuthenticationController;
import dal.EnrollmentDBContext;
import dal.RequestForLeaveDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import model.Employee;
import model.RequestForLeave;
import model.iam.User;

@WebServlet(urlPatterns = "/request/list")
public class ListController extends BaseRequiredAuthenticationController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {

        // Lấy employee theo user id
        EnrollmentDBContext enrollDB = new EnrollmentDBContext();
        Employee emp = enrollDB.getEmployeeByUserId(user.getId());

        if (emp == null) {
            resp.getWriter().println("❌ Không tìm thấy employee cho user id: " + user.getId());
            return;
        }

        RequestForLeaveDBContext db = new RequestForLeaveDBContext();

        // ======= PHÂN TRANG =======
        int pageSize = 5; // Số đơn mỗi trang
        String pageParam = req.getParameter("page");
        int pageIndex = (pageParam == null) ? 1 : Integer.parseInt(pageParam);

        int total = db.countByEmployeeId(emp.getId());
        int totalPage = (int) Math.ceil((double) total / pageSize);

        ArrayList<RequestForLeave> list = db.listByEmployeeIdPaging(emp.getId(), pageIndex, pageSize);

        req.setAttribute("rfls", list);
        req.setAttribute("pageIndex", pageIndex);
        req.setAttribute("totalPage", totalPage);

        req.getRequestDispatcher("/view/request/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        doGet(req, resp, user);
    }
}
