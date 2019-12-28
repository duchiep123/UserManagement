<%-- 
    Document   : HitoryPromotionListPage
    Created on : Nov 19, 2019, 4:52:26 PM
    Author     : mrhie
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        Welcome, ${sessionScope.ADMIN.fullname}<br/>
        <a href="DispatchServlet?button=Logout">Logout</a>  
        <h1>History Promotion List</h1>

        <c:set var="list" value="${requestScope.LISTPROMOTION}"/>
        <c:if test="${list !=null}">

            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>UserID</th>
                        <th>Rank</th>
                        <th>Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" items="${list}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${dto.userID}</td>

                            <td>${dto.rank}</td>
                            <td>${dto.date}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>


        </c:if>
        <c:if test="${list == null}">
            <h1>No found history</h1><br/>
        </c:if>
        <a href="DispatchServlet?button=BackToPromotionListPage&searchValue=${param.searchValue}">Back</a>


    </body>
</html>
