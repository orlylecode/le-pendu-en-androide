package com.cogitech.lependu.Activities.Services;

import android.content.SharedPreferences;

import com.cogitech.lependu.Activities.Models.Terminal;

public class Services {

 public boolean pendu(Terminal terminal , int val){
     boolean gagne = false ;
     int nomberMistere = (int) Math.random()*terminal.getMarge();
     if (terminal.getEssai() > 0){
         if (nomberMistere != val){
             gagne=  false;
         }else {
             gagne = true;
         }
         terminal.setEssai(terminal.getEssai()-1);
     }else {gagne=false;}
     return gagne ;
 }

}
