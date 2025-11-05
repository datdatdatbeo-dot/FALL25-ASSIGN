<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Đăng nhập hệ thống</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
  </head>
  <body>
    <div class="login-card">
      <h1>Đăng nhập</h1>
      <p>Hệ thống quản lý đơn xin nghỉ phép</p>

      <form action="login" method="post">
        <input type="text" name="username" placeholder="Tên đăng nhập" required />
        <input type="password" name="password" placeholder="Mật khẩu" required />
        <button type="submit">Đăng nhập</button>
      </form>

      <div class="message">
        ${message != null ? message : ""}
      </div>

      <footer>© 2025 Công ty TNHH Smart HR</footer>
    </div>
  </body>
</html>
