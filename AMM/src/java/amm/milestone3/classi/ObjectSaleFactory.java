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
public class ObjectSaleFactory {

    public static final String URL_ALIMENTATORE = "images/alimentatore.jpg";
    public static final String URL_CASE = "images/case.jpg";
    public static final String URL_DISSIPATORE = "images/dissipatore.jpg";
    public static final String URL_RAM = "images/ram.jpg";
    public static final String URL_VENTOLA = "images/ventola.jpg";

    public static final String NAME_ALIMENTATORE = "Alimentatore Corsair AX760";
    public static final String NAME_CASE = "Case Corsair Titanium";
    public static final String NAME_VENTOLA = "Ventola Corsair AFX";
    public static final String NAME_DISSIPATORE = "Dissipatore Liquido Corsair H60";
    public static final String NAME_RAM = "Ram Corsair Vegeance LPX DDR4 8Gb";

    // singleton 
    private static ObjectSaleFactory singleton;

    public static ObjectSaleFactory getInstance() {
        if (singleton == null) {
            singleton = new ObjectSaleFactory();
        }
        return singleton;
    }

    // lista
    private ArrayList<OggettoVendita> listaOggetti = new ArrayList<OggettoVendita>();

    // costruttore
    public ObjectSaleFactory() {
        OggettoVendita object1 = new OggettoVendita(0001, NAME_ALIMENTATORE, NAME_ALIMENTATORE, URL_ALIMENTATORE, 90.00, 4);
        OggettoVendita object2 = new OggettoVendita(0002, NAME_CASE, NAME_CASE, URL_CASE, 75.00, 6);
        OggettoVendita object3 = new OggettoVendita(0003, NAME_VENTOLA, NAME_VENTOLA, URL_VENTOLA, 12.00, 7);
        OggettoVendita object4 = new OggettoVendita(0004, NAME_DISSIPATORE, NAME_DISSIPATORE, URL_DISSIPATORE, 110.00, 3);
        OggettoVendita object5 = new OggettoVendita(0005, NAME_RAM, NAME_RAM, URL_RAM, 83.00, 9);

        listaOggetti.add(object1);
        listaOggetti.add(object2);
        listaOggetti.add(object3);
        listaOggetti.add(object4);
        listaOggetti.add(object5);
    }

    // metodi
    public ArrayList<OggettoVendita> getListaOggetti() {
        return this.listaOggetti;
    }

    public OggettoVendita getOggetto(int id) {
        for (OggettoVendita obj : this.listaOggetti) {
            if (obj.isId(id)) {
                return obj;
            }
        }
        return null;
    }
    
    public int nuovoId(){
       int lastIndex = listaOggetti.lastIndexOf(this);
       lastIndex += 2;
        return(Integer.parseInt("000" + String.valueOf(lastIndex)));
    }

}
