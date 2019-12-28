<%-- 
    Document   : InfoUserPage
    Created on : Nov 14, 2019, 7:17:15 PM
    Author     : mrhie
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            span{
                color: red;

            }
        </style>
    </head>
    <body >
        Welcome, ${sessionScope.ADMIN.fullname}<br/>
        <a href="DispatchServlet?button=Logout">Logout</a>  
        <h1>User Information</h1>
        <c:set var="user" value="${requestScope.USER}"/>
        <div style="height: 500px; width: 100%;">
            <form action="DispatchServlet" method="post" onsubmit="return CheckError();">
                <div style="height: 270px; width: 100%;  margin-top: 50px;" >
                    <img id="image"  src="${user.picture}"  style="height: 240px; width: 240px; "/>
                    <input id="file" onchange="ShowImg(event)" type="file" multiple="false" style="display: none;"accept="image/x-png,image/gif,image/jpeg"/><br/>
                    <input type="button" value="Change image" onclick="SelectFile()" />
                    <input type="hidden" id="picturePath" name="picture" value="${user.picture}"/> 

                </div><br/>
                <input type="hidden" name="userID" value="${param.userID}"/>

                UserID: ${user.userID}<br/>

                Fullname: <input id="name" type="text" name="fullname" value="${user.fullname}" ><span id="errorFullname"></span><br/>
                Email <input id="email" type="text" name="email" value="${user.email}"><span id="errorEmail"></span><br/>

                Phone: <input id="phone" type="text" name="phone" value="${user.phone}"/><span id="errorPhone"></span><br/>
                Role: <select name="role" required="" <c:if test="${user.userID == sessionScope.ADMIN.userID}">disabled=""</c:if>>
                    <c:forEach var="role" items="${sessionScope.LISTROLE}">
                        <option <c:if test="${role.role == user.role}">
                                selected="selected"

                            </c:if> value="${role.role}">
                            ${role.description} 
                        </option>
                    </c:forEach>

                </select><br/>
                <input type="submit" name="button" value="Update" /><br/>
            </form>

            <div id="divPass" style="border: 1px">
                <form action="DispatchServlet" method="post" onsubmit="return checkPassword();">
                    <input type="hidden" name="userID" value="${user.userID}"/>
                    Old password: <input type="password" value="" name="oldPassword" /><br/>

                    New Password: <input id="pass" type="password" value="" name="newPassword"/><span id="errorPassword"></span><br/>
                    Confirm Password: <input type="password" id="confirmPass" value=""/><span id="errorConfirm"></span><br/>
                    <input type="hidden" name="searchValue" value="${param.searchValue}"/>
                    <input type="hidden" name="sign" value="updatePassword"/>
                    <span id="MesPassword">
                        <c:if test="${requestScope.MesPassword !=null}">

                            ${requestScope.MesPassword}


                        </c:if>
                    </span><br/>
                    <input type="submit" name="button" value="Change"/>

                </form>



            </div><br/>


            <c:set var="Mes" value="${requestScope.Mes}"/>
            <span id="Mes" style="color: green">
                <c:if test="${Mes !=null}">
                    ${Mes}
                </c:if>
            </span><br/>

            <a href="DispatchServlet?button=Search&searchValue=${param.searchValue}">Back</a>



        </div>

        <script>


            function SelectFile() {
                document.getElementById('file').click();
            }
            function ShowImg(event) {
                var files = event.target.files;// file co s

                var file = files[0]; // lay phan tu dau cua file

                var fileReader = new FileReader();

                fileReader.readAsDataURL(file);
                fileReader.onload = function () {

                    var url = fileReader.result;
                    document.getElementById("image").src = url;
                    document.getElementById("picturePath").value = url; // gan vo bien de gui sang server
                };

            }
            function checkPassword() {
                document.getElementById("MesPassword").innerHTML = "";
                var findError = false;
                var pass = document.getElementById("pass").value;
                var confirm = document.getElementById("confirmPass").value;
                if (pass.search(" ") !== -1 || pass.length === 0) {// kiem tra pass trc

                    document.getElementById("errorPassword").innerHTML = "Password not allow contain space character or empty.";
                    findError = true;
                } else if (pass.search(" ") === -1 || pass.length > 0) {
                    document.getElementById("errorPassword").innerHTML = "";
                }
                if (findError === false) { // neu pass dung ms kiem tra confirm
                    if (pass !== confirm) {
                        document.getElementById("errorConfirm").innerHTML = "Confirm password is not match with password.";
                        findError = true;

                    } else {
                        document.getElementById("errorConfirm").innerHTML = "";

                    }
                }
                if (findError) {
                    return false;
                } else {

                    return true;
                }


            }


            function CheckError() {
                var findError = false;
                var fullname = document.getElementById("name").value;
                var email = document.getElementById("email").value;
                var phone = document.getElementById("phone").value;
                //
                var pattern = new RegExp("^[a-zA-Z ]+$");
                //

                if (pattern.test(fullname.trim()) === false) {
                    document.getElementById("errorFullname").innerHTML = "Fullname just allow a-z or A-Z character.";
                    findError = true;
                } else {
                    document.getElementById("errorFullname").innerHTML = "";
                }
                //
                pattern = new RegExp("^[a-zA-Z0-9]+@gmail.com$");
                if (pattern.test(email.trim()) === false) {
                    document.getElementById("errorEmail").innerHTML = "Email is wrong fomart ([a-zA-Z0-9]+@gmail.com)";
                    findError = true;
                } else {
                    document.getElementById("errorEmail").innerHTML = "";
                }
                pattern = new RegExp("^[0-9]{10}$");
                if (pattern.test(phone.trim()) === false) {
                    document.getElementById("errorPhone").innerHTML = "Phone number is 10 digits.";
                    findError = true;
                } else {
                    document.getElementById("errorPhone").innerHTML = "";
                }

                if (findError) {

                    document.getElementById("Mes").innerHTML = "";

                    return false;
                } else {

                    return true;
                }

            }


        </script>

    </body>
</html>
