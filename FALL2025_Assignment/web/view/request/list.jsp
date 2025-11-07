<%-- 
    Document   : list
    Created on : Oct 21, 2025, 11:32:00 PM
    Author     : datdt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn nghỉ phép</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 24px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 12px;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f3f3f3;
        }
        .btn {
            background: #0078D7;
            color: white;
            text-decoration: none;
            padding: 6px 12px;
            border-radius: 5px;
            margin-right: 6px;
        }
        .btn:hover {
            background: #005fa3;
        }
        .badge-success { color: green; font-weight: bold; }
        .badge-warning { color: orange; font-weight: bold; }
        .badge-danger { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <jsp:include page="../util/greeting.jsp"></jsp:include>

    <div style="margin: 12px 0;">
        <a href="${pageContext.request.contextPath}/request/list" class="btn">📋 Tất cả</a>
        <a href="${pageContext.request.contextPath}/request/list?status=0" class="btn">🕓 Chờ duyệt</a>
        <a href="${pageContext.request.contextPath}/request/list?status=1" class="btn">✅ Đã duyệt</a>
        <a href="${pageContext.request.contextPath}/request/list?status=2" class="btn">❌ Bị từ chối</a>
        <a href="${pageContext.request.contextPath}/request/create" class="btn" style="float:right;">➕ Tạo đơn mới</a>
    </div>

    <table>
        <tr>
            <th>ID</th>
            <th>Người tạo</th>
            <th>Lý do</th>
            <th>Từ ngày</th>
            <th>Đến ngày</th>
            <th>Trạng thái</th>
            <th>Người xử lý</th>
        </tr>

        <c:forEach var="r" items="${requestScope.rfls}">
            <tr>
                <td>${r.id}</td>
                <td>${r.created_by.name}</td>
                <td>${r.reason}</td>
                <td>${r.from}</td>
                <td>${r.to}</td>
                <td>
                    <c:choose>
                        <c:when test="${r.status == 0}">
                            <span class="badge-warning">🕓 Chờ duyệt</span>
                        </c:when>
                        <c:when test="${r.status == 1}">
                            <span class="badge-success">✅ Đã duyệt</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge-danger">❌ Bị từ chối</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:if test="${r.processed_by ne null}">
                        ${r.processed_by.name}
                    </c:if>
                    <c:if test="${r.processed_by eq null}">
                        --
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
