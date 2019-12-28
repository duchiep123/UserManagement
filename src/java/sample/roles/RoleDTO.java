/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.roles;

import java.io.Serializable;

/**
 *
 * @author mrhie
 */
public class RoleDTO implements Serializable {

    int role;
    String description;

    public RoleDTO() {
    }

    public RoleDTO(int role, String description) {
        this.role = role;
        this.description = description;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
