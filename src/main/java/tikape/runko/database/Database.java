package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Alue (nimi varchar(100) PRIMARY KEY);");
        lista.add("CREATE TABLE Keskustelu(id integer PRIMARY KEY,aihe varchar(100) NOT NULL,alue varchar(100) NOT NULL,FOREIGN KEY(alue) REFERENCES Alue(nimi));");
        lista.add("CREATE TABLE Viesti (aika timestamp NOT NULL, sisalto varchar(5000) NOT NULL, keskustelu integer NOT NULL,FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id));");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Ohjelmointi');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Urheilu');");
        lista.add("INSERT INTO Alue (nimi) VALUES ('Uutiset');");
        lista.add("INSERT INTO Keskustelu (aihe, alue) VALUES ('Java', 'Ohjelmointi');");
        lista.add("INSERT INTO Keskustelu (aihe, alue) VALUES ('Lisp', 'Ohjelmointi');");

        return lista;
    }
}
