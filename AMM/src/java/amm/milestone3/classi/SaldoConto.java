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
public class SaldoConto {

    private int id;
    private Double conto;

    // costruttore 
    public SaldoConto() {
        this.setId(-1);
        this.setConto(0.0);
    }

    // costruttore 
    public SaldoConto(int id, Double conto) {
        this.setId(id);
        this.setConto(conto);

    }

    public Double getConto() {
        return conto;
    }

    public void setConto(Double conto) {
        this.conto = conto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //metodi 
    public Double aggiungiSoldi(Double soldi) {
        this.setConto(this.getConto() + soldi);
        return this.getConto();
    }

    public Double rimuoviSoldi(Double soldi) {
        this.setConto(this.getConto() - soldi);
        return this.getConto();
    }
}
