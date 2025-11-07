package controller.home;

import controller.iam.BaseRequiredAuthenticationController;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import model.iam.User;

@WebServlet(urlPatterns = "/home")
public class HomeController extends HttpServlet {
    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
    resp.sendRedirect(req.getContextPath() + "/request/list");
}

}

