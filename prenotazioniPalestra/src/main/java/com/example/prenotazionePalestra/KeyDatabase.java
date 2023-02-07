package com.example.prenotazionePalestra;

import lombok.Data;

@Data
public class KeyDatabase {

    String dbutente;
    String dbpassword;

    public String getDBusername() {
         return dbutente;
    }
     
     public String getDBpassword() {
        return dbpassword;
    }
    
}
