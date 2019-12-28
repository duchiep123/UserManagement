/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.accounts;

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
public class AccountDAO implements Serializable {

    Connection cnn = null;
    PreparedStatement pre = null;
    ResultSet rs = null;

    public AccountDAO() {
    }

    public boolean updateRole(String userID, int newRole) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "update AccountTable set Role=? where UserID=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setInt(1, newRole);
            pre.setString(2, userID);
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

    public boolean addNewAccount(AccountDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "Insert into AccountTable(UserID, Password, Role, Available) values(?,?,?,?)";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, dto.getUserID());
            pre.setString(2, dto.getPassword());
            pre.setInt(3, dto.getRole());
            pre.setBoolean(4, dto.isAvailable());
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

    public boolean updatePassword(String userID, String newPassword) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "Update AccountTable set Password=? where UserID=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, newPassword);
            pre.setString(2, userID);

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

    public int login(String id, String password) throws NamingException, SQLException {
        int role = 0;
        try {
            String sql = "Select Role from AccountTable where UserID=? and Password=? and Available=1";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, id);
            pre.setString(2, password);
            rs = pre.executeQuery();
            if (rs.next()) {
                role = rs.getInt("Role");
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
        return role;
    }

    public boolean checkPassword(String password, String userID) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "Select UserID from AccountTable where UserID=? and Password=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, userID);
            pre.setString(2, password);
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

    public Vector<AccountDTO> getAllAccountByAdmin() throws NamingException, SQLException {
        Vector<AccountDTO> result = new Vector<>();
        try {
            String sql = "Select UserID, Password, Role, Available from AccountTable";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()) {
                AccountDTO dto = new AccountDTO(rs.getString("UserID"), rs.getString("Password"), rs.getInt("Role"), rs.getBoolean("Available"));
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

    public boolean removeUser(String userID) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "Update AccountTable set Available=0 where UserID=?";
            cnn = MyConnection.makeConnection();
            pre = cnn.prepareStatement(sql);
            pre.setString(1, userID);
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

}
