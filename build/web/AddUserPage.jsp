<%-- 
    Document   : AddUserPage
    Created on : Nov 15, 2019, 10:53:06 PM
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

        <h1>Create New User</h1>
        <div style="height: 500px; width: 100%; ">

            <form action="DispatchServlet" method="post" onsubmit="return CheckErrorCreate();" >
                <div style="height: 230px; width: 100%; margin-top: 50px;" >
                    <img id="image" style="height: 200px; width: 200px;"/>
                    <input id="file"  onchange="ShowImg(event)" multiple="false"  type="file"  style="display: none;"/><br/>
                    <span id="errorPicture"></span><br/>
                    <input type="button" value="Select Image" onclick="SelectFile()" />
                    <input type="hidden" id="picturePath" name="picture" value="${user.picture}"/> 

                </div><br/><br/>
                <div>

                    UserID:  <input id="userID" type="text" value="" name="userID" /><span id="errorUserID"></span><br/>
                    <br/>
                    Fullname:  <input id="name" type="text" value="" name="fullname" /><span id="errorFullname"></span><br/><br/>
                    Password:  <input id="pass" type="password" value="" name="password" /><span id="errorPassword"></span><br/><br/>
                    Confirm:   <input id="confirm" type="password" value="" name="confirm" /><span id="errorConfirm"></span><br/><br/>
                    Email:   <input id="email" type="text" value="" name="email" /><span id="errorEmail"></span><br/><br/>
                    Phone:   <input id="phone" type="text" value="" name="phone" /><span id="errorPhone"></span><br/><br/>

                    Role: <select name="role">
                        <c:forEach var="role" items="${sessionScope.LISTROLE}">
                            <option <c:if test="${role.role == user.role}">
                                    selected="selected"

                                </c:if> value="${role.role}">
                                ${role.description} 
                            </option>
                        </c:forEach>

                    </select><br/><br/>
                    <input type="submit" name="button" value="Create"/>
                </div>
            </form><br/>
            <c:set var="Mes" value="${requestScope.Mes}"/>

            <c:if test="${Mes!=null}">
                <span style="color: red">
                    ${Mes}
                </span><br/>

            </c:if>
            <a href="DispatchServlet?button=Load">Back</a>




        </div>


    </body>
    <script>


        function SelectFile() {
            document.getElementById('file').click();
        }
        
        function ShowImg(event) {
                    
            
            var files = event.target.files;

            var file = files[0];

            var fileReader = new FileReader();

            fileReader.readAsDataURL(file);
            fileReader.onload = function () {

                var url = fileReader.result;
                document.getElementById("image").src = url;
                document.getElementById("picturePath").value = url;
                document.getElementById("errorPicture").innerHTML = "";
            };
        }

        
        function CheckErrorCreate() {

            var findError = false;

            var pass = document.getElementById("pass").value;

            var fullname = document.getElementById("name").value;
            var email = document.getElementById("email").value;
            var phone = document.getElementById("phone").value;
            var confirm = document.getElementById("confirm").value;
            var userID = document.getElementById("userID").value;
            var picture = document.getElementById("image").src; //  


            //

            var pattern = new RegExp("^[a-zA-Z ]+$");
            //
            if (pass.search(" ") !== -1 || pass.length === 0) {
                document.getElementById("errorPassword").innerHTML = "Password not allow contain space character or empty.";
                findError = true;
            } else if (pass.search(" ") === -1 || pass.length > 0) {
                document.getElementById("errorPassword").innerHTML = "";
            }
            if (findError === false) {
                if (pass !== confirm) {
                    document.getElementById("errorConfirm").innerHTML = "Confirm password is not match with password.";
                    findError = true;

                } else {
                    document.getElementById("errorConfirm").innerHTML = "";

                }
            }

            if (userID.search(" ") !== -1 || userID.length === 0) {
                findError = true;
                document.getElementById("errorUserID").innerHTML = "UserID not allow contain space character or empty.";
            } else {
                document.getElementById("errorUserID").innerHTML = "";
            }



            if (pattern.test(fullname.trim()) === false) {
                document.getElementById("errorFullname").innerHTML = "Fullname just allow a-z or A-Z character.";
                findError = true;
            } else {
                document.getElementById("errorFullname").innerHTML = "";
            }
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
            if (picture.length === 0) {
                document.getElementById("errorPicture").innerHTML = "Picture is not allow empty.";
                findError = true;
            } else {
                document.getElementById("errorPicture").innerHTML = "";
            }


            if (findError) {
                return false;
            }
            return true;

        }

    </script>
</html>
