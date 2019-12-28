<%-- 
    Document   : AdminPage
    Created on : Nov 12, 2019, 1:15:38 AM
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
        <h1>Admin Page</h1>
        <form action="DispatchServlet" method="get">
            Search by name: <input type="text" name="searchValue" value="${param.searchValue}"/>

            <input type="submit" name="button" value="Search"/>
        </form>

        <!-- --------------------------------------------------------------------------------------- -->


        <a href="DispatchServlet?button=Load&tag=All">All</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

        <c:forEach var="role" items="${sessionScope.LISTROLE}">

            <a href="DispatchServlet?button=Load&tag=${role.description}">${role.description}</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </c:forEach>
        <!-- --------------------------------------------------------------------------------------- -->
        <c:set var="listUser" value="${requestScope.LISTUSER}"/>
        <c:if test="${listUser !=null }">

            <div id="divTable">
                <table border="1">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th>Image</th>
                            <th>UserID</th>
                            <th>Fullname</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Available</th>
                            <th>Remove</th>
                            <th>Update</th>
                            <th>Promotion</th>
                        </tr>
                    </thead>
                    <tbody>


                        <c:forEach var="user" items="${listUser}" varStatus="counter">
                            <tr>
                        <form action="DispatchServlet" method="post">
                            <td>${counter.count}</td>
                            <td><img src="${user.picture}" height="200px" width="200px"/></td>
                            <td>${user.userID}
                                <input type="hidden" name="userID" value="${user.userID}"/>
                            </td>
                            <td>
                                ${user.fullname}


                            </td>
                            <td>
                                ${user.email}

                            </td>

                            <td>
                                ${user.phone}

                            </td>

                            <td>
                                <c:forEach var="role" items="${sessionScope.LISTROLE}">
                                    <c:if test="${role.role == user.role}">
                                        ${role.description}
                                    </c:if>

                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="account" items="${sessionScope.LISTACCOUNT}">
                                    <c:if test="${account.userID == user.userID}">
                                        ${account.available}
                                    </c:if> 

                                </c:forEach>


                            </td>
                        </form>
                        <td>
                            <c:if test="${user.userID != sessionScope.ADMIN.userID}">
                                <form action="DispatchServlet" method="post">
                                    <input type="hidden" name="searchValue" value="${param.searchValue}"/>
                                    <input type="hidden" name="txtUserID" value="${user.userID}"/>
                                    <input type="submit" name="button" value="Remove"/>
                                </form>
                            </c:if>

                        </td>
                        <td>
                            <form action="DispatchServlet" method="post">
                                <input type="hidden" name="userID" value="${user.userID}"/>
                                <input type="hidden" name="searchValue" value="${param.searchValue}"/>
                                <input type="submit" name="button" value="Edit"/>

                            </form>
                        </td>
                        <td>
                            <form action="DispatchServlet" method="get">
                                <input type="hidden" name="searchValue" value="${param.searchValue}"/>
                                <input type="hidden" name="userID" value="${user.userID}"/>
                                <input type="hidden" name="fullname" value="${user.fullname}"/>
                                <input type="submit" name="button" value="Add To Promotion List"/>
                            </form>
                        </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>
            <c:set var="mes" value="${requestScope.Mes}"/>
            <c:if test="${mes !=null}">
                <span>
                    ${mes}<br/>
                </span>
            </c:if>


        </c:if>
        <c:if test="${listUser == null}">
            <h1>No user found.</h1>
        </c:if>
        <a href="DispatchServlet?button=GoToInsertPage">Create new user</a><br/>
        <a href="DispatchServlet?button=ShowPromotionList&searchValue=${param.searchValue}">View Promotion List</a>



    </body>
</html>
