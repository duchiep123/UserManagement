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
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.accounts.AccountDAO;
import sample.roles.RoleDAO;
import sample.roles.RoleDTO;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author mrhie
 */
public class LoginServlet extends HttpServlet {

    private final String LOGIN_PAGE = "LoginPage.jsp";
    private final String ADMIN = "LoadUserServlet";
    private final String NONADMIN = "NonAdminPage.jsp";

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
        String url = "";
        HttpSession session = request.getSession();
        try {
            String id = request.getParameter("txtUserID");
            String pass = request.getParameter("txtPassword");
            String passEncrypted = encrypted(pass);
            AccountDAO daoAccount = new AccountDAO();
            RoleDAO daoRole = new RoleDAO();
            int role = daoAccount.login(id, pass);
            if (role != 0) {
                Vector<RoleDTO> listRole = daoRole.getRole();
                session.setAttribute("LISTROLE", listRole);// lay all role
                for (int i = 0; i < listRole.size(); i++) {
                    if (role == listRole.get(i).getRole()) {
                        UserDAO daoUser = new UserDAO();
                        UserDTO user = daoUser.getUserToLogin(id);
                        if (listRole.get(i).getDescription().equalsIgnoreCase("admin")) {

                            url = ADMIN;
                            session.setAttribute("ADMIN", user);

                        } else {
                            url = NONADMIN;
                            session.setAttribute("NONADMIN", user);

                        }
                    }
                }
            } else {

                url = LOGIN_PAGE;
                request.setAttribute("MesLogin", "UserID or password maybe incorrect.");

            }

        } catch (Exception e) {

            log("Error at LoginServlet: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
