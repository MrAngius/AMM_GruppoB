/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.classi;

import java.util.ArrayList;

/**
 *
 * @author Marco
 */
public class UserFactory {

    // singleton 
    private static UserFactory singleton;

    public static UserFactory getInstance() {
        if (singleton == null) {
            singleton = new UserFactory();
        }
        return singleton;
    }

    // Liste
    private ArrayList<Cliente> listaClienti = new ArrayList<Cliente>();
    private ArrayList<Venditore> listaVenditori = new ArrayList<Venditore>();

    // costruttori
    public UserFactory() {

        Cliente cliente1 = new Cliente(01, "Marco", "password", new SaldoConto(01, 300.00));
        listaClienti.add(cliente1);

        Cliente cliente2 = new Cliente(02, "Francesco", "password", new SaldoConto(02, 20.00));
        listaClienti.add(cliente2);

        Venditore venditore1 = new Venditore(001, "Tore", "password", new SaldoConto(001, 100.00));
        listaVenditori.add(venditore1);

        Venditore venditore2 = new Venditore(002, "Sara", "passowrd", new SaldoConto(002, 50.00));
        listaVenditori.add(venditore2);
    }

    // metodi
    public ArrayList<Cliente> getListaClienti() {
        return listaClienti;
    }

    public ArrayList<Venditore> getListaVenditori() {
        return listaVenditori;
    }

    public Cliente getCliente(int id) {
        for (Cliente c : listaClienti) {
            if (c.isId(id)) {
                return c;
            }
        }
        return null;
    }

    public Venditore getVenditore(int id) {
        for (Venditore v : listaVenditori) {
            if (v.isId(id)) {
                return v;
            }
        }
        return null;
    }

    public ArrayList<Utente> getListaUtenti() {
        ArrayList<Utente> listaUtenti = new ArrayList<Utente>();
        listaUtenti.addAll(this.getListaClienti());
        listaUtenti.addAll(this.getListaVenditori());

        return listaUtenti;
    }

}
