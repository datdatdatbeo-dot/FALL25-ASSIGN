<%-- 
    Document   : create
    Created on : Nov 5, 2025, 9:22:55 PM
    Author     : datda
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Tạo đơn nghỉ phép</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
  </head>
  <body>
    <div class="container">
      <div class="card">
        <h2>➕ Tạo đơn nghỉ phép mới</h2>
        <form action="${pageContext.request.contextPath}/request/create" method="post">
          <label>Lý do nghỉ:</label><br>
          <textarea name="reason" rows="3" style="width:100%" required></textarea><br><br>

          <label>Từ ngày:</label>
          <input type="date" name="from" required><br><br>

          <label>Đến ngày:</label>
          <input type="date" name="to" required><br><br>

          <button type="submit" class="btn">Gửi đơn</button>
          <a href="${pageContext.request.contextPath}/request/list" class="btn" style="background:#6c757d;">Hủy</a>
        </form>
      </div>
    </div>
  </body>
</html>
