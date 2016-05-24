/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.classi;

/**
 *
 * @author Marco
 */
public class Venditore extends Utente {
   
    public Venditore(){
        super();
    }
    
    public Venditore(int id, String name, String surname, String username, String password, SaldoConto conto) {
        super(id, name, surname, username, password, conto);
    }
}
