/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlets;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.accounts.AccountDAO;
import sample.accounts.AccountDTO;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author mrhie
 */
public class InsertUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

    private String encrypted(String password) {
        String passEncrypted = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            BigInteger n = new BigInteger(1, hash);
            StringBuilder hexString = new StringBuilder(n.toString(16));
            while (hexString.length() < 32) {
                hexString.insert(0, '0');
            }
            passEncrypted = hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            log("Error at encrypted password: " + e.getMessage());
        }
        return passEncrypted;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            // user
            String userID = request.getParameter("userID");
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String picture = request.getParameter("picture");
            int role = Integer.parseInt(request.getParameter("role"));
            //account

            String password = request.getParameter("password");
            String passwordEncrypted = encrypted(password);
            UserDTO newUser = new UserDTO(userID, fullname, email, phone, role, picture);
            AccountDTO newAccount = new AccountDTO(userID, passwordEncrypted, role, true);

            UserDAO daoUser = new UserDAO();
            if (daoUser.checkDuplicateUserID(userID) == false) {
                AccountDAO daoAccount = new AccountDAO();

                if (daoUser.addNewUser(newUser)) {
                    if (daoAccount.addNewAccount(newAccount)) {
                        request.setAttribute("Mes", "Add new User success.");
                    }
                }
            } else {
                request.setAttribute("Mes", "UserID is exsited.");
            }

        } catch (Exception e) {

            log("Error at InsertUserSerlvet: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("AddUserPage.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
