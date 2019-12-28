<%-- 
    Document   : PromotionListPage
    Created on : Nov 17, 2019, 11:41:43 PM
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
    <script>
        function submit() {
            document.getElementById("myForm").submit();
        }

        function testRank(quantity) {
            var findError = false;
            var rank;
            var errorMes = "Rank is a number bigger than 0 and smaller than 10. <br/>";
            var rankFloat;
            for (var i = 1; i <= quantity; i++) {
                rank = document.getElementById("rank_" + i);
                rankFloat = parseFloat("" + rank.value);
                if (isNaN(rankFloat)) {
                    findError = true;
                    errorMes += "Error at row " + i + "<br/>";

                } else if (rankFloat < 0 || rankFloat > 10) {
                    errorMes += "Error at row " + i + "<br/>";
                    findError = true;
                }
            }

            if (findError) {
                document.getElementById("mesUpdate").innerHTML = errorMes;
                document.getElementById("mesUpdate").style.color = "red";
                return false;
            } else {
                return true;
            }




        }
    </script>
    <body>
        Welcome, ${sessionScope.ADMIN.fullname}<br/>
        <a href="DispatchServlet?button=Logout">Logout</a>  
        <h1>Promotion Page</h1>
        <c:set var="MesConfirm" value="${requestScope.MesConfirm}"/>
        <c:if test="${MesConfirm != null}">
            <h1 style="color: green">
                ${MesConfirm}
            </h1>
        </c:if>

        <c:set var="list" value="${sessionScope.PROMOTIONLIST}"/>
        <c:if test="${list!=null}" >

            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>UserID</th>
                        <th>Rank</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <form id="myForm" action="DispatchServlet" method="get" onsubmit="return testRank(${sessionScope.PROMOTIONQUANTITY});" >
                    <tbody>

                        <c:forEach var="dto" items="${list}" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${dto.userID}
                                    <input type="hidden" name="userID_${counter.count}" value="${dto.userID}"/>
                                </td>
                                <td>
                                    <input id="rank_${counter.count}" type="text" name="myRank_${counter.count}" value="${dto.rank}"/>

                                </td>

                                <td>
                                    <form action="DispatchServlet" method="post">
                                        <input type="hidden" name="searchValue" value="${param.searchValue}"/>
                                        <input type="hidden" name="userID" value="${dto.userID}"/>
                                        <input type="submit" name="button" value="Remove Promotion"/>
                                    </form>

                                </td>

                            </tr>
                        </c:forEach>
                             <!-- <input type="hidden" name="searchValue" value="${param.searchValue}"/> -->
                    <input type="submit" name="buttonConfirm" value="Confirm" form="myForm"/>




                    </tbody>
                </form>
            </table>

        </c:if>

        <c:if test="${list==null}">
            <h1>Promotion List is empty.</h1>
        </c:if>
        <c:set var="mes" value="${requestScope.Mes}"/>
        <span id="mesUpdate" style="color: green">
            <c:if test="${mes!= null}">

                ${mes}

            </c:if>
        </span><br/>
        <a href="DispatchServlet?button=ViewHistoryPromotionList&searchValue=${param.searchValue}">View History Promotion List</a><br/>
        <a href="DispatchServlet?button=Search&searchValue=${param.searchValue}">Back</a>
    </body>

</html>
