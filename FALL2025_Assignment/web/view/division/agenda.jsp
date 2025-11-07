<%-- 
    Document   : agenda
    Created on : Nov 5, 2025, 9:06:02 PM
    Author     : datda
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Division Agenda</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>
<jsp:include page="../util/greeting.jsp"></jsp:include>

<h2 style="text-align:center;margin-top:20px;">📅 Lịch nghỉ của phòng ban</h2>

<div style="width:90%;margin:auto;margin-top:20px;">
    <table border="1" cellspacing="0" cellpadding="8" width="100%">
        <tr style="background:#f3f3f3;">
            <th>STT</th>
            <th>Nhân viên</th>
            <th>Từ ngày</th>
            <th>Đến ngày</th>
            <th>Lý do</th>
            <th>Trạng thái</th>
        </tr>

        <c:forEach var="r" items="${rfls}" varStatus="loop">
            <tr>
                <td>${loop.index + 1}</td>
                <td>${r.created_by.name}</td>
                <td>${r.from}</td>
                <td>${r.to}</td>
                <td>${r.reason}</td>
                <td>
                    <c:choose>
                        <c:when test="${r.status == 0}">🕓 Chờ duyệt</c:when>
                        <c:when test="${r.status == 1}">✅ Đã duyệt</c:when>
                        <c:otherwise>❌ Từ chối</c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>