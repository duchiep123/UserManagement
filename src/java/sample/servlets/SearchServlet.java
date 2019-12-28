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
import sample.roles.RoleDTO;
import sample.user.UserDAO;
import sample.user.UserDTO;

/**
 *
 * @author mrhie
 */
public class SearchServlet extends HttpServlet {

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
        String searchValue = "";
        searchValue = request.getParameter("searchValue");

        int role = 0;
        try {
            String tag = (String) session.getAttribute("HistoryTag");// lay history tag

            if (tag != null) {// kiem tra tag 
                Vector<RoleDTO> listRole = (Vector<RoleDTO>) session.getAttribute("LISTROLE");

                for (int i = 0; i < listRole.size(); i++) {
                    if (tag.equalsIgnoreCase(listRole.get(i).getDescription())) {
                        role = listRole.get(i).getRole();
                        break;
                    }
                }
            }
            UserDAO dao = new UserDAO();

            Vector<UserDTO> listUser = dao.searchUserByLikeName(searchValue, role);
            if (!listUser.isEmpty()) {
                request.setAttribute("LISTUSER", listUser);
            }

        } catch (Exception e) {
            log("Error at SearchServlet: " + e.getMessage());
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
