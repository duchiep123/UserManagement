/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.promotion.PromotionDAO;
import sample.promotion.PromotionDTO;

/**
 *
 * @author mrhie
 */
public class AddUserToPromotionList extends HttpServlet {

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
        try {
            String userID = request.getParameter("userID").trim();

            float rank = 5.0f;
            LocalDate date = LocalDate.now();
            PromotionDTO dto = new PromotionDTO(userID, rank, date);
            HttpSession session = request.getSession(false);
            PromotionDAO dao = new PromotionDAO();
            if (session.getAttribute("PROMOTIONLIST") == null) {
                Vector<PromotionDTO> listPromotion = new Vector<>();
                if (dao.checkDuplicate(userID) == false) { // check in db
                    listPromotion.add(dto);
                    session.setAttribute("PROMOTIONLIST", listPromotion);
                    session.setAttribute("PROMOTIONQUANTITY", listPromotion.size());// lay size
                    request.setAttribute("Mes", "Add success.");
                } else {
                    request.setAttribute("Mes", "This user is exsited in promotion list.");
                }

            } else {

                Vector<PromotionDTO> listPromotion = (Vector<PromotionDTO>) session.getAttribute("PROMOTIONLIST");
                if (dao.checkDuplicate(userID) == false) { // check in db
                    for (int i = 0; i < listPromotion.size(); i++) {
                        if (listPromotion.get(i).getUserID().equals(userID)) {
                            request.setAttribute("Mes", "This user is exsited in promotion list.");
                            break;
                        }
                    }
                } else {
                    request.setAttribute("Mes", "This user is exsited in promotion list.");

                }
                if (request.getAttribute("Mes") == null) {
                    listPromotion.add(dto);
                    request.setAttribute("Mes", "Add success.");
                    session.setAttribute("PROMOTIONLIST", listPromotion);
                    session.setAttribute("PROMOTIONQUANTITY", listPromotion.size());
                }
            }

        } catch (Exception e) {
            log("Error at AddUserToPromotionList: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("SearchServlet").forward(request, response);
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
