/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.milestone3.classi;

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
public class UserFactory {

    // SINGLETON
    private static UserFactory singleton;
    String connectionString;

    public static UserFactory getInstance() {
        if (singleton == null) {
            singleton = new UserFactory();
        }
        return singleton;
    }

    // Liste
//    private ArrayList<Cliente> listaClienti = new ArrayList<Cliente>();
//    private ArrayList<Venditore> listaVenditori = new ArrayList<Venditore>();
    // COSTRUTTORE
    public UserFactory() {

    }

    // METODI
    // metodi pre DB
    public ArrayList<Cliente> getListaClienti()
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // è sufficiente una statement 
        Statement listaStmt = null;

        // query
        String query = "SELECT * FROM cliente";

        try {

            listaStmt = conn.createStatement();

            // eseguo
            ResultSet lista = listaStmt.executeQuery(query);
            ArrayList<Cliente> listaClienti = new ArrayList<>();

            if (lista.getFetchSize() != 0) {
                while (lista.next()) {

                    Cliente corrente = new Cliente();

                    // non ci serve recuperare di nuovo username e password
                    corrente.setId(lista.getInt("id"));
                    corrente.setName(lista.getString("nome"));
                    corrente.setSurnmame(lista.getString("cognome"));
                    corrente.setUsername(lista.getString("username"));
                    corrente.setPassword(lista.getString("user_password"));
                    SaldoConto conto = new SaldoConto(lista.getDouble("conto"));
                    corrente.setDisponibilita(conto);

                    listaClienti.add(corrente);
                }

                return listaClienti;

            } else {
                throw new SQLnoResultException("La ricerca dei Clienti non ha prodotto risultati");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (listaStmt != null) {
                listaStmt.close();
            }

            conn.close();
        }

        return null;

    }

    public ArrayList<Venditore> getListaVenditori()
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // è sufficiente una statement 
        Statement listaStmt = null;

        // query
        String query = "SELECT * FROM cliente";

        try {

            listaStmt = conn.createStatement();

            // eseguo
            ResultSet lista = listaStmt.executeQuery(query);
            ArrayList<Venditore> listaVenditori = new ArrayList<>();

            if (lista.getFetchSize() != 0) {
                while (lista.next()) {

                    Venditore corrente = new Venditore();

                    // non ci serve recuperare di nuovo username e password
                    corrente.setId(lista.getInt("id"));
                    corrente.setName(lista.getString("nome"));
                    corrente.setSurnmame(lista.getString("cognome"));
                    corrente.setUsername(lista.getString("username"));
                    corrente.setPassword(lista.getString("user_password"));
                    SaldoConto conto = new SaldoConto(lista.getDouble("conto"));
                    corrente.setDisponibilita(conto);

                    listaVenditori.add(corrente);
                }

                return listaVenditori;

            } else {
                throw new SQLnoResultException("La ricerca dei Venditori non ha prodotto risultati");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (listaStmt != null) {
                listaStmt.close();
            }

            conn.close();
        }

        return null;
    }

    public ArrayList<Utente> getListaUtenti()
            throws SQLException, SQLnoResultException {

        ArrayList<Utente> listaUtenti = new ArrayList<>();
        listaUtenti.addAll(this.getListaClienti());
        listaUtenti.addAll(this.getListaVenditori());

        return listaUtenti;
    }

    public Cliente getCliente(int id)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement ricercaCliente = null;

        // query
        String queryCliente = "SELECT * FROM cliente WHERE id = ?";

        try {

            // inizializziamo il preparedStatement
            ricercaCliente = conn.prepareStatement(queryCliente);
            ricercaCliente.setInt(1, id);
            // eseguo
            ResultSet cliente = ricercaCliente.executeQuery();

            if (cliente.next()) {

                Cliente corrente = new Cliente();

                // non ci serve recuperare di nuovo username e password
                corrente.setId(cliente.getInt("id"));
                corrente.setName(cliente.getString("nome"));
                corrente.setSurnmame(cliente.getString("cognome"));
                SaldoConto conto = new SaldoConto(cliente.getDouble("conto"));
                corrente.setDisponibilita(conto);

                return corrente;

            } else {
                throw new SQLnoResultException("La ricerca non ha trovato l'utente con id:" + id);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaCliente != null) {
                ricercaCliente.close();
            }

            conn.close();
        }

        return null;
    }

    public Venditore getVenditore(int id)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement ricercaVenditore = null;

        // query
        String queryVenditore = "SELECT * FROM venditore WHERE id = ?";

        try {

            // inizializziamo il preparedStatement
            ricercaVenditore = conn.prepareStatement(queryVenditore);
            ricercaVenditore.setInt(1, id);
            // eseguo
            ResultSet venditore = ricercaVenditore.executeQuery();

            if (venditore.next()) {

                Venditore corrente = new Venditore();

                // non ci serve recuperare di nuovo username e password
                corrente.setId(venditore.getInt("id"));
                corrente.setName(venditore.getString("nome"));
                corrente.setSurnmame(venditore.getString("cognome"));
                SaldoConto conto = new SaldoConto(venditore.getDouble("conto"));
                corrente.setDisponibilita(conto);

                return corrente;

            } else {
                throw new SQLnoResultException("La ricerca non ha trovato l'utente con id:" + id);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaVenditore != null) {
                ricercaVenditore.close();
            }

            conn.close();
        }

        return null;
    }

    public int getIdVenditore(int id_oggetto)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement 
        PreparedStatement ricercaVenditore = null;

        // query
        String queryVenditore = "SELECT id_venditore FROM oggetto_vendita WHERE id = ?";

        try {

            // inizializziamo il preparedStatement
            ricercaVenditore = conn.prepareStatement(queryVenditore);
            ricercaVenditore.setInt(1, id_oggetto);
            // eseguo
            ResultSet venditore = ricercaVenditore.executeQuery();

            if (venditore.next()) {
                return venditore.getInt("id_venditore");

            } else {
                throw new SQLnoResultException("La ricerca non ha trovato lil venditore assocciato all'oggetto con id:" + id_oggetto);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaVenditore != null) {
                ricercaVenditore.close();
            }

            conn.close();
        }

        return -1;
    }

    // metodi post DB
    public Utente getUtente(String username, String password)
            throws SQLException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement
        // poiche ho due tabelle con clienti e venditori, devo ricercare username e password in entrambi
        PreparedStatement ricercaCliente = null;
        PreparedStatement ricercaVenditore = null;

        // query
        String queryCliente = "SELECT * FROM cliente WHERE username = ? "
                + "AND user_password = ?";

        String queryVenditore = "SELECT * FROM venditore WHERE username = ? "
                + "AND user_password = ?";

        try {

            // inizializziamo i preparedStatement
            ricercaCliente = conn.prepareStatement(queryCliente);
            ricercaVenditore = conn.prepareStatement(queryVenditore);

            ricercaCliente.setString(1, username);
            ricercaCliente.setString(2, password);

            ricercaVenditore.setString(1, username);
            ricercaVenditore.setString(2, password);

            // eseguiamo e recuepro le riche
            // uan delle due è null
            ResultSet cliente = ricercaCliente.executeQuery();
            ResultSet venditore = ricercaVenditore.executeQuery();

            if (cliente.next()) {

                Cliente corrente = new Cliente();

                // non ci serve recuperare di nuovo username e password
                corrente.setId(cliente.getInt("id"));
                corrente.setName(cliente.getString("nome"));
                corrente.setSurnmame(cliente.getString("cognome"));
                SaldoConto conto = new SaldoConto(cliente.getDouble("conto"));
                corrente.setDisponibilita(conto);

                return corrente;

            } else if (venditore.next()) {

                Venditore corrente = new Venditore();

                // non ci serve recuperare di nuovo username e password
                corrente.setId(venditore.getInt("id"));
                corrente.setName(venditore.getString("nome"));
                corrente.setSurnmame(venditore.getString("cognome"));
                SaldoConto conto = new SaldoConto(venditore.getDouble("conto"));
                corrente.setDisponibilita(conto);

                return corrente;
            } else {
                throw new SQLnoResultException("La ricerca non ha prodotto risultati");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaCliente != null) {
                ricercaCliente.close();
            }
            if (ricercaVenditore != null) {
                ricercaVenditore.close();
            }
            conn.close();

        }
        return null;
    }

    public Double creditoVenditore(int id)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement
        PreparedStatement ricercaConto = null;

        // query
        String query = "SELECT conto FROM venditore WHERE id = ?";

        try {

            // inizializziamo i preparedStatement
            ricercaConto = conn.prepareStatement(query);
            ricercaConto.setInt(1, id);

            // eseguiamo e recuepro le riche
            ResultSet conto = ricercaConto.executeQuery();

            if (conto.next()) {

                return conto.getDouble("conto");

            } else {
                throw new SQLnoResultException("La ricerca del conto del venditore con id: " + id + " non ha prodotto risultati!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaConto != null) {
                ricercaConto.close();
            }
            conn.close();

        }
        return null;
    }

    public void modificaContoVenditore(int id, Double credito, int mod)
            throws SQLException, SQLnoResultException {
        /*
        * >> "mod" consente di selezionare la modalita:
        *       0 : rimuovi
        *       1 : inserisci
         */

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement
        PreparedStatement updateConto = null;

        // query
        String query = "UPDATE venditore SET conto = ? WHERE id = ?";

        try {

            // recupero il credito attuale e applico la modalita
            Double contoAttuale = this.creditoVenditore(id);

            if (mod == 0) {
                contoAttuale -= credito;
            } else {
                contoAttuale += credito;
            }

            // inizializziamo i preparedStatement
            updateConto = conn.prepareStatement(query);
            updateConto.setDouble(1, contoAttuale);

            // eseguiamo e recuepro le riche
            int conto = updateConto.executeUpdate();

            if (conto != 1) {

                conn.rollback();
                throw new SQLnoResultException("Non è stato possibile aggiornare i dati!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (updateConto != null) {
                updateConto.close();
            }
            conn.close();

        }

    }

    public Double creditoCliente(int id)
            throws SQLException, SQLnoResultException {

        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement
        PreparedStatement ricercaConto = null;

        // query
        String query = "SELECT conto FROM cliente WHERE id = ?";

        try {

            // inizializziamo i preparedStatement
            ricercaConto = conn.prepareStatement(query);
            ricercaConto.setInt(1, id);

            // eseguiamo e recuepro le riche
            ResultSet conto = ricercaConto.executeQuery();

            if (conto.next()) {

                return conto.getDouble("conto");

            } else {
                throw new SQLnoResultException("La ricerca del conto del cliente con id: " + id + " non ha prodotto risultati!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (ricercaConto != null) {
                ricercaConto.close();
            }
            conn.close();

        }
        return null;

    }

    public void modificaContoCliente(int id, Double credito, int mod)
            throws SQLException, SQLnoResultException {
        /*
        * >> "mod" consente di selezionare la modalita:
        *       0 : rimuovi
        *       1 : inserisci
         */

        Double contoAttuale = this.creditoCliente(id);
        
        // connessione
        Connection conn = connettiDB();

        // dichiaro preparedStatement
        PreparedStatement updateConto = null;

        // query
        String query = "UPDATE cliente SET conto = ? WHERE id = ?";

        try {

            // recupero il credito attuale e applico la modalita
            

            if (mod == 0) {
                contoAttuale -= credito;
            } else {
                contoAttuale += credito;
            }

            // inizializziamo i preparedStatement
            updateConto = conn.prepareStatement(query);
            updateConto.setDouble(1, contoAttuale);
            updateConto.setInt(2, id);

            // eseguiamo e recuepro le riche
            int conto = updateConto.executeUpdate();

            if (conto != 1) {

                conn.rollback();
                throw new SQLnoResultException("Non è stato possibile aggiornare i dati!");
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (updateConto != null) {
                updateConto.close();
            }
            conn.close();

        }
    }

    // metodi DBhelper
    public void setConnectionString(String s) {
        this.connectionString = s;
    }

    public String getConnectionString() {
        return this.connectionString;
    }

    // se volessi modificare username e password
    public Connection connettiDB()
            throws SQLException {
        Connection connessione = DriverManager.getConnection(connectionString, "marcoangius", "password");
        return connessione;
    }

    // inner class per gestione errore
    public static class SQLnoResultException extends Exception {

        private String info;

        public SQLnoResultException(String info) {
            this.setInfo(info);
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

    }
}
