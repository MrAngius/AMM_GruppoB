/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.classi;

import amm.milestone3.classi.UserFactory.SQLnoResultException;
import com.sun.corba.se.pept.protocol.ClientDelegate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marco
 */
public class ObjectSaleFactory {

    // SINGLETON 
    private static ObjectSaleFactory singleton;
    String connectionString;

    public static ObjectSaleFactory getInstance() {
        if (singleton == null) {
            singleton = new ObjectSaleFactory();
        }
        return singleton;
    }

    // costruttore
    public ObjectSaleFactory() {
    }

    // METODI
    // metodi precedenti DB
    public ArrayList<OggettoVendita> getListaOggetti()
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        Statement ricerca = null;

        // query
        String query = "SELECT * FROM oggetto_vendita";

        try {

            // inizializziamo il preparedStatement
            ricerca = conn.createStatement();

            // eseguo
            ResultSet objs = ricerca.executeQuery(query);

            // ci aspettiamo un solo oggetto
            if (objs.getFetchSize() != 0) {

                ArrayList<OggettoVendita> listaOggetti = new ArrayList<>();

                while (objs.next()) {
                    OggettoVendita oggetto = new OggettoVendita();

                    // non ci serve recuperare di nuovo username e password
                    oggetto.setId(objs.getInt("id"));
                    oggetto.setNome(objs.getString("nome"));
                    oggetto.setDescrizione(objs.getString("descrizione"));
                    oggetto.setUrl(objs.getString("url"));
                    oggetto.setQuantita(objs.getInt("quantita"));
                    oggetto.setPrezzo(objs.getDouble("prezzo"));

                    listaOggetti.add(oggetto);
                }

                return listaOggetti;

            } else {
                throw new UserFactory.SQLnoResultException("La ricerca non ha recuperato oggetti!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricerca != null) {
                ricerca.close();
            }

            conn.close();
        }

        return null;
    }

    public OggettoVendita getOggetto(int id)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement ricercaOggetto = null;

        // query
        String query = "SELECT * FROM oggetto_vendita WHERE id = ?";

        try {

            // inizializziamo il preparedStatement
            ricercaOggetto = conn.prepareStatement(query);
            ricercaOggetto.setInt(1, id);
            // eseguo
            ResultSet obj = ricercaOggetto.executeQuery();

            // ci aspettiamo un solo oggetto
            if (obj.next()) {

                OggettoVendita oggetto = new OggettoVendita();

                // non ci serve recuperare di nuovo username e password
                oggetto.setId(obj.getInt("id"));
                oggetto.setNome(obj.getString("nome"));
                oggetto.setDescrizione(obj.getString("descrizione"));
                oggetto.setUrl(obj.getString("url"));
                oggetto.setQuantita(obj.getInt("quantita"));
                oggetto.setPrezzo(obj.getDouble("prezzo"));

                return oggetto;

            } else {
                throw new UserFactory.SQLnoResultException("La ricerca non ha trovato l'oggetto con id:" + id + "!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaOggetto != null) {
                ricercaOggetto.close();
            }

            conn.close();
        }

        return null;
    }

    public ArrayList<OggettoVendita> oggettiVenditore(int id)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement ricercaObjVenditore = null;

        // query
        String query = "SELECT *"
                + " FROM oggetto_vendita JOIN venditore ON oggetto_vendita.ID_VENDITORE = venditore.ID"
                + " WHERE venditore.ID = ?";

        try {

            // inizializziamo il preparedStatement
            ricercaObjVenditore = conn.prepareStatement(query);
            ricercaObjVenditore.setInt(1, id);
            // eseguo
            ResultSet objs = ricercaObjVenditore.executeQuery();

            // ci aspettiamo un solo oggetto
            if (objs.getFetchSize() != 0) {

                ArrayList<OggettoVendita> listaOggettiVenditore = new ArrayList<>();

                while (objs.next()) {
                    OggettoVendita oggetto = new OggettoVendita();

                    // non ci serve recuperare di nuovo username e password
                    oggetto.setId(objs.getInt("id"));
                    oggetto.setNome(objs.getString("nome"));
                    oggetto.setDescrizione(objs.getString("descrizione"));
                    oggetto.setUrl(objs.getString("url"));
                    oggetto.setQuantita(objs.getInt("quantita"));
                    oggetto.setPrezzo(objs.getDouble("prezzo"));

                    listaOggettiVenditore.add(oggetto);
                }

                return listaOggettiVenditore;

            } else {
                throw new UserFactory.SQLnoResultException("La ricerca non ha recuperato oggetti!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaObjVenditore != null) {
                ricercaObjVenditore.close();
            }

            conn.close();
        }
        return null;
    }

    // metodi post DB
    public void eliminaOggettoVendita(int id)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement eliminaOggetto = null;

        // query
        String queryDelate = "DELETE FROM oggetto_vendita WHERE id = ?";

        try {

            // inizializziamo il preparedStatement
            eliminaOggetto = conn.prepareStatement(queryDelate);
            eliminaOggetto.setInt(1, id);
            // eseguo
            int obj = eliminaOggetto.executeUpdate();

            // ci aspettiamo un solo oggetto
            if (obj != 1) {
                // se non è cosi facciamo un roll back
                conn.rollback();
            } else {
                throw new UserFactory.SQLnoResultException("L'eliminazione dell oggetto con id: " + id + " non riuscita!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (eliminaOggetto != null) {
                eliminaOggetto.close();
            }

            conn.close();
        }
    }

    public void inserisciOggettoVendita(OggettoVendita obj, int idVenditore)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement inserisciOggetto = null;

        // query
        String queryInsert = "INSERT INTO oggetto_vendita (id, nome, descrizione, url, prezzo, quantita, id_venditore) "
                + "VALUES (default, ?, ?, ?, ?, ?, ?)";

        try {

            // inizializziamo il preparedStatement
            inserisciOggetto = conn.prepareStatement(queryInsert);
            inserisciOggetto.setString(1, obj.getNome());
            inserisciOggetto.setString(2, obj.getDescrizione());
            inserisciOggetto.setString(3, obj.getUrl());
            inserisciOggetto.setDouble(4, obj.getPrezzo());
            inserisciOggetto.setInt(5, obj.getQuantita());
            inserisciOggetto.setInt(6, idVenditore);
            // eseguo
            int result = inserisciOggetto.executeUpdate();

            // ci aspettiamo un solo oggetto
            if (result != 1) {
                // se non è cosi facciamo un roll back
                conn.rollback();
                throw new UserFactory.SQLnoResultException("L'inserimento dell oggetto non è riuscita!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (inserisciOggetto != null) {
                inserisciOggetto.close();
            }

            conn.close();
        }
    }

    public void modificaOggettoVendita(OggettoVendita obj)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement modificaOggetto = null;

        // query
        String queryUpdate = "UPDATE oggetto_vendita SET  nome = ?, descrizione = ?, url = ?, prezzo = ?, quantita = ? "
                + "WHERE id = ?";

        try {

            // inizializziamo il preparedStatement
            modificaOggetto = conn.prepareStatement(queryUpdate);
            modificaOggetto.setString(1, obj.getNome());
            modificaOggetto.setString(2, obj.getDescrizione());
            modificaOggetto.setString(3, obj.getUrl());
            modificaOggetto.setDouble(4, obj.getPrezzo());
            modificaOggetto.setInt(5, obj.getQuantita());
            modificaOggetto.setInt(6, obj.getId());
            // eseguo
            int result = modificaOggetto.executeUpdate();

            // ci aspettiamo un solo oggetto
            if (result != 1) {
                // se non è cosi facciamo un roll back
                conn.rollback();
                throw new UserFactory.SQLnoResultException("La modifica dell oggetto con id " + obj.getId() + " non è riuscita!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (modificaOggetto != null) {
                modificaOggetto.close();
            }

            conn.close();
        }

    }

    public void transazioneOggetto (Cliente cliente, OggettoVendita oggetto)
            throws SQLException, SQLnoResultException {

        // controllo se dell'oggetto ci sono piu pezzi, se c'è ne uso solo devo eliminarlo 
        // se sono presenti piu pezzi decremento il numero
        
        // dall'oggetto recupero il saldo venditore e la quantitia
        int id_venditore = UserFactory.getInstance().getIdVenditore(oggetto.getId());
        Double oldSaldoVenditore = UserFactory.getInstance().creditoVenditore(id_venditore);
        
        int id_cliente = cliente.getId();
        Double oldSaldoCliente = cliente.getDisponibilita().getConto();
        
        // noto il credito del venditore e del cliente
        Double newSaldoCliente = oldSaldoCliente - oggetto.getPrezzo();
        Double newSaldoVenditore = oldSaldoVenditore + oggetto.getPrezzo();

        // connessine DB
        Connection conn = connettiDB();

        PreparedStatement eliminaObj = null;
        PreparedStatement modifObj = null;
        PreparedStatement decrementaSaldo = null;
        PreparedStatement incrementaSaldo = null;

        String qEliminaObj = "DELETE FROM oggetto_vendita WHERE id = ?";
        String qModificaObj = "UPDATE oggetto_vendita SET quantita = ? "
                + "WHERE id = ?";
        String qDecremetaSaldo = "UPDATE cliente SET conto = ? "
                + "WHERE id = ?";
        String qIncrementaSaldo = "UPDATE venditore SET conto = ? "
                + "WHERE id = ?";

        // una flag per il commit
        Boolean commit = false;
        
        
        try {

            conn.setAutoCommit(false);

            decrementaSaldo = conn.prepareStatement(qDecremetaSaldo);
            incrementaSaldo = conn.prepareStatement(qIncrementaSaldo);

            if (oggetto.getQuantita() > 1) {
                modifObj = conn.prepareStatement(qModificaObj);
                modifObj.setInt(1, (oggetto.getQuantita() - 1));
                modifObj.setInt(2, oggetto.getId());

                int r = modifObj.executeUpdate();

                if (r != 1) {
                    conn.rollback();
                }

            } else {
                eliminaObj = conn.prepareStatement(qEliminaObj);
                eliminaObj.setInt(1, oggetto.getId());

                int r = eliminaObj.executeUpdate();

                if (r != 1) {
                    conn.rollback();
                }
            }

            decrementaSaldo.setDouble(1, newSaldoCliente);
            decrementaSaldo.setInt(2, id_cliente);

            int r1 = decrementaSaldo.executeUpdate();

            incrementaSaldo.setDouble(1, newSaldoVenditore);
            incrementaSaldo.setInt(2, id_venditore);

            int r2 = incrementaSaldo.executeUpdate();

            if (r1 != 1 || r2 != 1) {
                conn.rollback();
            } else {
               
                conn.commit();
                // aggiorno la flag
                commit = true;
            }

        } catch (Exception e) {
            conn.rollback();
            e.printStackTrace();

        } finally {

            if (modifObj != null) {
                modifObj.close();
            }
            if (eliminaObj != null) {
                eliminaObj.close();
            }
            if (incrementaSaldo != null) {
                incrementaSaldo.close();
            }
            if (decrementaSaldo != null) {
                decrementaSaldo.close();
            }

            conn.setAutoCommit(true);
            conn.close();
            
            if (commit) {
                // registro l'acquisto
                registraAcquisto(cliente, oggetto);
            }

        }
    }

    public void registraAcquisto(Cliente cliente, OggettoVendita oggetto) 
            throws SQLnoResultException, SQLException {

        Connection conn = connettiDB();

        PreparedStatement verificaOgetto = null;
        PreparedStatement inserisciOggetto = null;
        PreparedStatement incrementaOggetto = null;

        String qVerificaOggetto = "SELECT * FROM acquisto WHERE id_cliente = ? AND id_oggetto = ?";
        String qInserisciOggetto = "INSERT INTO acquisto (id_cliente, id_oggetto, quantita) VALUES (?,?,?)";
        String qIncrementaOggetto = "UPDATE acquisto SET quantita = ? WHERE id_oggetto = ? AND id_cliente = ?";

        try {

            verificaOgetto = conn.prepareStatement(qVerificaOggetto);
            incrementaOggetto = conn.prepareStatement(qIncrementaOggetto);
            inserisciOggetto = conn.prepareStatement(qInserisciOggetto);

            verificaOgetto.setInt(1, cliente.getId());
            verificaOgetto.setInt(2, oggetto.getId());

            ResultSet result = verificaOgetto.executeQuery();

            int r = 0;

            if (result.next()) {

                incrementaOggetto.setInt(1, (result.getInt("quantita") + 1));
                incrementaOggetto.setInt(2, oggetto.getId());
                incrementaOggetto.setInt(3, cliente.getId());

                r = incrementaOggetto.executeUpdate();

            } else {

                inserisciOggetto.setInt(3, 1);
                inserisciOggetto.setInt(2, oggetto.getId());
                inserisciOggetto.setInt(1, cliente.getId());

                r = inserisciOggetto.executeUpdate();
            }

            if (r != 1) {
                conn.rollback();
            }

        } catch (Exception e) {

            throw new UserFactory.SQLnoResultException("La modifica dell oggetto con id " + oggetto.getId() + " non è riuscita!");

        } finally {

            if (verificaOgetto != null) {
                verificaOgetto.close();
            }
            if (inserisciOggetto != null) {
                inserisciOggetto.close();
            }
            if (incrementaOggetto != null) {
                incrementaOggetto.close();
            }

            conn.close();
        }

    }
 
    public ArrayList<OggettoVendita> getOggettiAcquistati(int id_cliente) 
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        Statement ricerca = null;

        // query
        String query = "SELECT id, nome, descrizione, url, prezzo, quantita "
                + "FROM acquisto JOIN (SELECT id, nome, descrizione, url, prezzo FROM oggetto_vendita) AS oggetto_vendita "
                + "ON oggetto_vendita.id = acquisto.id_oggetto "
                + "WHERE acquisto.id_cliente = " + id_cliente;

        try {

            // inizializziamo il preparedStatement
            ricerca = conn.createStatement();

            // eseguo
            ResultSet objs = ricerca.executeQuery(query);

            // ci aspettiamo un solo oggetto
            if (objs.getFetchSize() != 0) {

                ArrayList<OggettoVendita> listaOggetti = new ArrayList<>();

                while (objs.next()) {
                    OggettoVendita oggetto = new OggettoVendita();

                    // non ci serve recuperare di nuovo username e password
                    oggetto.setId(objs.getInt("id"));
                    oggetto.setNome(objs.getString("nome"));
                    oggetto.setDescrizione(objs.getString("descrizione"));
                    oggetto.setUrl(objs.getString("url"));
                    oggetto.setQuantita(objs.getInt("quantita"));
                    oggetto.setPrezzo(objs.getDouble("prezzo"));

                    listaOggetti.add(oggetto);
                }

                return listaOggetti;

            } else {
                throw new UserFactory.SQLnoResultException("La ricerca non ha recuperato oggetti!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricerca != null) {
                ricerca.close();
            }

            conn.close();
        }

        return null;

    }
    
    // metodi DBhelper
    public void setConnectionString(String s) {
        this.connectionString = s;
    }

    public String getConnectionString() {
        return this.connectionString;
    }

    public Connection connettiDB()
            throws SQLException {
        Connection connessione = DriverManager.getConnection(connectionString, "marcoangius", "password");
        return connessione;
    }

}
