/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlets;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.accounts.AccountDAO;
import sample.accounts.AccountDTO;
import sample.roles.RoleDAO;
import sample.roles.RoleDTO;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author mrhie
 */
public class LoadUserServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String tag = "";
        if (request.getParameter("tag") != null) {
            tag = (String) request.getParameter("tag");
            session.setAttribute("HistoryTag", tag); //luu tag vo session
        }

        try {

            UserDAO daoUser = new UserDAO();
            AccountDAO daoAccount = new AccountDAO();

            RoleDAO daoRole = new RoleDAO();
            // 
            Vector<AccountDTO> listAccount = daoAccount.getAllAccountByAdmin();// lay het account
            session.setAttribute("LISTACCOUNT", listAccount);
            Vector<UserDTO> listUser = daoUser.getAllDataByAdmin();// lay het user
            Vector<RoleDTO> listRole = daoRole.getRole();// lay het role
            session.setAttribute("LISTROLE", listRole); // cap nhap role
            
            if (tag.equalsIgnoreCase("All") || tag.equals("")) {// kiem tra co phai tag all ko
                request.setAttribute("LISTUSER", listUser);
            } else if (!tag.equals("") && !tag.equalsIgnoreCase("All")) { // ko phai tag All

                for (int i = 0; i < listRole.size(); i++) {
                    if (tag.equalsIgnoreCase(listRole.get(i).getDescription())) {
                        Vector<UserDTO> list = daoUser.getUserByRole(listRole.get(i).getRole());// lay user theo tag
                        request.setAttribute("LISTUSER", list);
                    }
                }
            }

        } catch (Exception e) {
            log("Error at LoadUserServlet: " + e.getMessage());

        } finally {
            request.getRequestDispatcher("AdminPage.jsp").forward(request, response);
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
