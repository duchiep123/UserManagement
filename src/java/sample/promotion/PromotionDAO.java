/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.promotion;

import java.io.Serializable;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Vector;
import javax.naming.NamingException;
import sample.db.MyConnection;

/**
 *
 * @author mrhie
 */
public class PromotionDAO implements Serializable {

    Connection cnn = null;
    PreparedStatement pre = null;
    ResultSet rs = null;

    public PromotionDAO() {
    }

    public boolean checkDuplicate(String userID) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "Select UserID from PromotionTable where userID=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, userID);
            rs = pre.executeQuery();
            if (rs.next()) {
                result = true;
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return result;
    }

    public boolean storeUserToPromotionInDB(PromotionDTO dto) throws SQLException, NamingException {
        boolean result = false;

        try {
            String sql = "Insert into PromotionTable(UserID,Rank,Date) values(?,?,?)";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, dto.getUserID());
            pre.setFloat(2, dto.getRank());
            pre.setDate(3, Date.valueOf(dto.getDate()));
            result = pre.executeUpdate() > 0;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return result;
    }

    public Vector<PromotionDTO> getPromotionList() throws SQLException, NamingException {
        Vector<PromotionDTO> list = new Vector<>();
        try {
            
            String sql = "Select UserID,  Rank, Date from PromotionTable Order by Date ASC";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            rs = pre.executeQuery();
            
            while (rs.next()) {
                PromotionDTO dto = new PromotionDTO(rs.getString("UserID"), rs.getFloat("Rank"), rs.getDate("Date").toLocalDate());
                list.add(dto);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pre != null) {
                pre.close();
            }
            if (cnn != null) {
                cnn.close();
            }
        }
        return list;
    }

   

}
