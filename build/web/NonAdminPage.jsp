<%-- 
    Document   : SubPage
    Created on : Nov 12, 2019, 1:26:34 AM
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
        <c:set var="user" value="${sessionScope.NONADMIN}"/>
        Welcome, ${user.fullname} 
        <c:forEach var="role" items="${sessionScope.LISTROLE}">
            <c:if test="${role.role == user.role}">
                (${role.description})
            </c:if>
        </c:forEach>
        <br/>
        <a href="DispatchServlet?button=Logout">Logout</a>  
        <h1>Information Page</h1>
        <div style="height: 500px; width: 100%;">

            <div style="height: 270px; width: 100%;  margin-top: 50px;" >
                <img id="image"  src="${user.picture}"  style="height: 240px; width: 240px; "/>


            </div><br/>

            UserID: &nbsp;&nbsp;&nbsp; ${user.userID}<br/>

            Fullname:&nbsp;&nbsp;&nbsp; ${user.fullname}<br/>
            Email:&nbsp;&nbsp;&nbsp; ${user.email}<br/>

            Phone:&nbsp;&nbsp;&nbsp;${user.phone}<br/>
            Role: &nbsp;&nbsp;&nbsp;
            <c:forEach var="role" items="${sessionScope.LISTROLE}">
              
                <c:if test="${user.role == role.role}">
                    ${role.description}
                </c:if>
            </c:forEach>
               

           



    </body>
</html>
