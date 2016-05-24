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

    private Double conto;
    
    // costruttori
    public SaldoConto() {
        this.setConto(0.0);
    }

    public SaldoConto(Double conto) {
        this.setConto(conto);

    }

    // metodi
    public Double getConto() {
        return conto;
    }

    public void setConto(Double conto) {
        this.conto = conto;
    }
}