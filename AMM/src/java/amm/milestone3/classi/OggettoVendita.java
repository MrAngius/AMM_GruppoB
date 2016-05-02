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
public class OggettoVendita {

    private int id;
    private String nome;
    private String descrizione;
    private String url;
    private Double prezzo;
    private int quantita;

    // costruttori 
    public OggettoVendita() {
        this.setId(-1);
        this.setNome("");
        this.setUrl("");
        this.setDescrizione("");
        this.prezzo = -1.0; // salto il controllo ma mi aspetto che in inizializzazione non ci siano errori
        this.quantita = -1; // salto il controllo ma mi aspetto che in inizializzazione non ci siano errori
    }

    public OggettoVendita(int id, String nome, String descrizione, String url, Double prezzo, int quantita) {
        this.setId(id);
        this.setNome(nome);
        this.setDescrizione(descrizione);
        this.setUrl(url);
        this.prezzo = prezzo; // salto il controllo ma mi aspetto che in inizializzazione non ci siano errori
        this.quantita = quantita; // salto il controllo ma mi aspetto che in inizializzazione non ci siano errori
    }

    // metodi
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrione) {
        this.descrizione = descrione;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Double prezzo)
            throws IllegalArgumentException, ValueException {
        if (prezzo >= 0) {
            this.prezzo = prezzo;
        } else {
            throw new ValueException("Prezzo deve essere maggiore o uguale a zero!");
        }
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quntita)
            throws IllegalArgumentException, ValueException {
        if (quntita >= 0) {
            this.quantita = quntita;
        } else {
            throw new ValueException("Quantita deve essere maggiore o uguale a zero!");
        }
    }

    // metodo 
    public boolean isId(int id) {
        return this.getId() == id;
    }
    
    
    public boolean soldiSufficienti(Double conto){
        return this.getPrezzo() < conto;
    }

    // inner class 
    public static class ValueException extends Exception {

        private String info;

        // construttore
        public ValueException(String info) {
            setInfo(info);
        }

        public String getInfo() {
            return this.info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

    }
}
