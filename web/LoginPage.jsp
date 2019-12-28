<%-- 
    Document   : LoginPage
    Created on : Nov 21, 2019, 8:37:47 AM
    Author     : mrhie
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form action="DispatchServlet" method="post">
            UserID: <input type="text" name="txtUserID" /><br/>
            Password: <input type="password" name="txtPassword"/><br/>
            <input type="submit" name="button" value="Login"/>
        </form>


        <c:if test="${requestScope.MesLogin !=null}">
            <span style="color: red">
                ${requestScope.MesLogin}
            </span>
        </c:if>
    </body>
</html>
