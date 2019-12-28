/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.user;

import java.io.Serializable;

/**
 *
 * @author mrhie
 */
public class UserDTO implements Serializable {

    String userID;
    String fullname;
    String email;
    String phone;
    int role;
    String picture;

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public UserDTO() {
    }

    public UserDTO(String userID, String fullname, String email, String phone, int role, String picture) {
        this.userID = userID;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.picture = picture;
    }
   

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

}
