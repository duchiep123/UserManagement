/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.accounts;

import java.io.Serializable;

/**
 *
 * @author mrhie
 */
public class AccountDTO implements Serializable {

    String userID;
    String password;
    int role;
    boolean available;

    public AccountDTO() {
    }

    public AccountDTO(String userID, String password, int role, boolean available) {
        this.userID = userID;
        this.password = password;
        this.role = role;
        this.available = available;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
