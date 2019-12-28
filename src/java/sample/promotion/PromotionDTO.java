/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.promotion;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author mrhie
 */
public class PromotionDTO implements Serializable {

    String userID;
    float rank;
    LocalDate date;

    public PromotionDTO() {
    }

    public PromotionDTO(String userID, float rank, LocalDate date) {
        this.userID = userID;
        this.rank = rank;
        this.date = date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

   

    public float getRank() {
        return rank;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

}
