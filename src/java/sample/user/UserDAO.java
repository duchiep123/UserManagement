/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.naming.NamingException;
import sample.db.MyConnection;

/**
 *
 * @author mrhie
 */
public class UserDAO implements Serializable {

    Connection cnn = null;
    PreparedStatement pre = null;
    ResultSet rs = null;

    public UserDAO() {
    }

    public boolean checkDuplicateUserID(String userID) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "Select UserID from UserTable where UserID=?";
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

    public boolean updateUser(UserDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "";
            if (dto.getRole() == 0) {
                sql = "Update UserTable set Fullname=?, Email=?, Phone=?, Picture=?  where UserID=?";
            } else {
                sql = "Update UserTable set Fullname=?, Email=?, Phone=?, Picture=?, Role=? where UserID=?";
            }
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, dto.getFullname());
            pre.setString(2, dto.getEmail());
            pre.setString(3, dto.getPhone());
            if (dto.getRole() != 0) {
                pre.setString(4, dto.getPicture());
                pre.setInt(5, dto.getRole());
                pre.setString(6, dto.getUserID());
            }else{
                pre.setString(4, dto.getPicture());
                pre.setString(5, dto.getUserID());
                
            }

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

    public UserDTO getUserToLogin(String id) throws NamingException, SQLException {
        UserDTO dto = new UserDTO();
        try {
            String sql = "select Fullname, UserID, Email, Phone, Picture, Role from UserTable where UserID=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, id);
            rs = pre.executeQuery();
            if (rs.next()) {
                dto.setFullname(rs.getString("Fullname"));
                dto.setUserID(rs.getString("UserID"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
                dto.setPicture(rs.getString("Picture"));
                dto.setRole(rs.getInt("Role"));
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
        return dto;
    }

    public boolean addNewUser(UserDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "Insert into UserTable(UserID, Fullname, Email, Phone, Picture, Role) values (?,?,?,?,?,?)";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, dto.getUserID());
            pre.setString(2, dto.getFullname());
            pre.setString(3, dto.getEmail());
            pre.setString(4, dto.getPhone());
            pre.setString(5, dto.getPicture());
            pre.setInt(6, dto.getRole());
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

    public Vector<UserDTO> getAllDataByAdmin() throws NamingException, SQLException {
        Vector<UserDTO> result = new Vector<>();
        try {
            String sql = "Select UserID, Fullname, Email, Phone, Role,Picture from UserTable";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO(rs.getString("UserID"), rs.getString("Fullname"), rs.getString("Email"), rs.getString("Phone"), rs.getInt("Role"), rs.getString("Picture"));
                result.add(dto);

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

    public Vector<UserDTO> searchUserByLikeName(String searchValue, int role) throws NamingException, SQLException {
        Vector<UserDTO> result = new Vector<>();

        String sql = "";
        try {
            if (role == 0) {
                sql = "Select UserID, Fullname, Email,  Phone, Role, Picture from UserTable where Fullname LIKE ?";
            } else {
                sql = "Select UserID, Fullname, Email,  Phone,Role, Picture from UserTable where Fullname LIKE ? and Role=?";
            }

            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, "%" + searchValue + "%");
            if (role != 0) {
                pre.setInt(2, role);
            }
            rs = pre.executeQuery();
            while (rs.next()) {

                UserDTO dto = new UserDTO();
                dto.setUserID(rs.getString("UserID"));
                dto.setFullname(rs.getString("Fullname"));
                dto.setEmail(rs.getString("Email"));

                dto.setPhone(rs.getString("Phone"));
                dto.setRole(rs.getInt("Role"));
                dto.setPicture(rs.getString("Picture"));

                result.add(dto);
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

    public UserDTO getUserByUserID(String userID) throws NamingException, SQLException {
        UserDTO dto = new UserDTO();
        try {
            String sql = "Select UserID, Fullname, Email, Phone, Picture, Role from UserTable where UserID=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, userID);
            rs = pre.executeQuery();
            if (rs.next()) {
                dto.setUserID(rs.getString("UserID"));
                dto.setFullname(rs.getString("Fullname"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
                dto.setPicture(rs.getString("Picture"));

                dto.setRole(rs.getInt("Role"));
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
        return dto;
    }

    public Vector<UserDTO> getUserByRole(int role) throws NamingException, SQLException {
        Vector<UserDTO> result = new Vector<>();
        try {
            String sql = "Select UserID, Fullname, Email, Phone, Role, Picture from UserTable where Role=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setInt(1, role);
            rs = pre.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserID(rs.getString("UserID"));
                dto.setFullname(rs.getString("Fullname"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhone(rs.getString("Phone"));
                dto.setRole(rs.getInt("Role"));
                dto.setPicture(rs.getString("Picture"));
                result.add(dto);
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

}
