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
import javax.servlet.http.HttpSession;
import sample.accounts.AccountDAO;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author mrhie
 */
public class UpdateUserServlet extends HttpServlet {

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

            AccountDAO daoAccount = new AccountDAO();
            String userID = request.getParameter("userID");
            String sign = request.getParameter("sign"); // co phai update password hay ko

            if (sign != null) { // just update password

                String oldPassword = request.getParameter("oldPassword");
                String oldPasswordEncrypted = encrypted(oldPassword);
                String newPassword = request.getParameter("newPassword");
                String newPasswordEncrypted = encrypted(newPassword);

                if (daoAccount.checkPassword(oldPasswordEncrypted, userID)) {
                    daoAccount.updatePassword(userID, newPasswordEncrypted);

                    request.setAttribute("MesPassword", "Change password success.");
                } else {
                    request.setAttribute("MesPassword", "Old Password is not correct");
                }
            } else { // update all without password
                HttpSession session = request.getSession(false);
                UserDTO admin = (UserDTO) session.getAttribute("ADMIN");// lay thag Admin
                int role = 0;
                if (!admin.getUserID().equals(userID)) {// ko phai admin
                    role = (Integer.parseInt(request.getParameter("role")));
                }
                // neu la admin thi update tru role ra 

                String fullname = request.getParameter("fullname").trim();
                String email = request.getParameter("email").trim();
                String phone = request.getParameter("phone").trim();
                String picture = request.getParameter("picture");
                UserDTO dto = new UserDTO(userID, fullname, email, phone, role, picture);
                UserDAO daoUser = new UserDAO();
                if (daoUser.updateUser(dto)) { // update bang user (co update role trong bang user)
                    if (!userID.equals(admin.getUserID())) {// kiem tra xem thag dc update co phai admin ko
                        if (daoAccount.updateRole(userID, role)) { // update role trong bang account cua thag ko phai admin hien hanh                         

                            request.setAttribute("Mes", "Update success.");
                        }
                    } else {
                        if (userID.equals(admin.getUserID())) {
                            session.setAttribute("ADMIN", dto); // cap nhap la session cua admin
                        }
                        request.setAttribute("Mes", "Update success.");
                    }

                }
            }

        } catch (Exception e) {
           log("Error at UpdateUserServlet: " + e.getMessage());
            
        } finally {
            request.getRequestDispatcher("EditUserServlet").forward(request, response);
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
