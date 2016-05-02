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
public abstract class Utente {

    private int id;
    private String username;
    private String password;
    private SaldoConto disponibilita;

    // ctruttore
    public Utente() {
        this.setId(-1);
        this.setUsername("");
        this.setPassword("");
        this.setDisponibilita(null);
    }

    public Utente(int id, String username, String password, SaldoConto conto) {
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setDisponibilita(conto);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SaldoConto getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(SaldoConto conto) {
        this.disponibilita = conto;
    }

    // metodo 
    public boolean isId(int id) {
        return this.getId() == id;
    }

    public boolean hasCash(Double prezzo) {
        return this.getDisponibilita().getConto() >= prezzo;
    }
}
