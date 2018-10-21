/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.daw.helper;

/**
 *
 * @author Alejandro
 */
public class ValidationHelper {
    
 
public static boolean numPositivo(String id){
    try{
        if((Integer.parseInt(id))>0){
            return true;
        }else{
           return false; 
        }
        
    }catch(NumberFormatException e){
        return false;
    }
}
    
}
