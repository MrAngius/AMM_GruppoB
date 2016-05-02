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
public class Cliente extends Utente {

    public Cliente(){
        super();
    }
    
    public Cliente(int id, String username, String password, SaldoConto conto) {
        super(id, username, password, conto);
    }
}
