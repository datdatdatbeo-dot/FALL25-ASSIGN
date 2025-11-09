<%-- 
    Document   : list
    Created on : Oct 21, 2025, 11:32:00 PM
    Author     : datdt
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta charset="UTF-8">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn nghỉ phép</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7fb;
            margin: 20px;
        }
        h2 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 6px;
            overflow: hidden;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center;
        }
        th {
            background: #f2f2f2;
        }
        .btn {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 6px 10px;
            text-decoration: none;
            border-radius: 5px;
            font-size: 14px;
            margin-right: 5px;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .badge-warning { color: orange; font-weight: bold; }
        .badge-success { color: green; font-weight: bold; }
        .badge-danger { color: red; font-weight: bold; }
    </style>
</head>

<body>

    <jsp:include page="../util/greeting.jsp"></jsp:include>

    <h2>Số đơn nghỉ phép: ${fn:length(rfls)}</h2>

    <div style="margin: 12px 0;">
        <a href="${pageContext.request.contextPath}/request/list" class="btn">📄 Tất cả</a>
        <a href="${pageContext.request.contextPath}/request/list?status=0" class="btn">🕓 Chờ duyệt</a>
        <a href="${pageContext.request.contextPath}/request/list?status=1" class="btn">✅ Đã duyệt</a>
        <a href="${pageContext.request.contextPath}/request/list?status=2" class="btn">❌ Bị từ chối</a>
        <a href="${pageContext.request.contextPath}/request/create" class="btn" style="float:right;">➕ Tạo đơn mới</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Người tạo</th>
                <th>Lý do</th>
                <th>Từ ngày</th>
                <th>Đến ngày</th>
                <th>Trạng thái</th>
                <th>Người xử lý</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="r" items="${rfls}">
                <tr>
                    <td>${r.id}</td>
                    <td>
                        <c:choose>
                            <c:when test="${r.created_by ne null}">
                                ${r.created_by.name}
                            </c:when>
                            <c:otherwise>--</c:otherwise>
                        </c:choose>
                    </td>
                    <td>${r.reason}</td>
                    <td>${r.from}</td>
                    <td>${r.to}</td>
                    <td>
                        <c:choose>
                            <c:when test="${r.status == 0}">
                                <span class="badge-warning">Chờ duyệt</span>
                            </c:when>
                            <c:when test="${r.status == 1}">
                                <span class="badge-success">Đã duyệt</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge-danger">Bị từ chối</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${r.processed_by ne null}">
                                ${r.processed_by.name}
                            </c:when>
                            <c:otherwise>--</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
    <c:if test="${r.status == 0}">
        <a href="${pageContext.request.contextPath}/request/review?id=${r.id}&action=approve" class="btn">✅ Duyệt</a>
        <a href="${pageContext.request.contextPath}/request/review?id=${r.id}&action=reject" class="btn">❌ Từ chối</a>
    </c:if>
    <c:if test="${r.status != 0}">
        <c:choose>
            <c:when test="${r.status == 1}">Đã duyệt</c:when>
            <c:otherwise>Bị từ chối</c:otherwise>
        </c:choose>
    </c:if>
</td>

                </tr>
            </c:forEach>

            <c:if test="${empty rfls}">
                <tr>
                    <td colspan="7" style="color: gray; font-style: italic;">
                        Khong co don nghi phep nao
                    </td>
                </tr>
            </c:if>
        </tbody>
    </table>
<div style="margin-top: 16px; text-align: center;">
    <c:if test="${totalPage > 1}">
        <c:forEach var="i" begin="1" end="${totalPage}">
            <c:choose>
                <c:when test="${i == pageIndex}">
                    <span style="background:#007bff;color:white;padding:6px 10px;border-radius:5px;">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/request/list?page=${i}" 
                       style="margin:0 4px;text-decoration:none;padding:6px 10px;border:1px solid #007bff;border-radius:5px;">
                       ${i}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </c:if>
</div>

</body>
</html>
