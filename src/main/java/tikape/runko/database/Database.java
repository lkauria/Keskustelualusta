package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.net.*;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
        init();
    }
  
    public void init() {
        List<String> lauseet = sqliteLauseet();

        if (this.databaseAddress.contains("postgres")) {
            lauseet = postgreLauseet();
        } else {
            lauseet = sqliteLauseet();
        }    
        
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

    
    public Connection getConnection() throws SQLException {
        if (this.databaseAddress.contains("postgres")) {
            try {
                URI dbUri = new URI(databaseAddress);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];
                String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

                return DriverManager.getConnection(dbUrl, username, password);
            } catch (Throwable t) {
                System.out.println("Error: " + t.getMessage());
                t.printStackTrace();
            }
        }
        return DriverManager.getConnection(databaseAddress);
    }
    
     private List<String> postgreLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("DROP TABLE Alue;");
        // heroku käyttää SERIAL-avainsanaa uuden tunnuksen automaattiseen luomiseen
        lista.add("CREATE TABLE Alue ((id SERIAL PRIMARY KEY, nimi varchar(100)));");
        
        lista.add("DROP TABLE Keskustelu;");
        lista.add("CREATE TABLE Keskustelu (id SERIAL PRIMARY KEY, aihe varchar(100) NOT NULL, alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue(id));");
        
        lista.add("DROP TABLE Viesti;");
        lista.add("CREATE TABLE Viesti ((id integer PRIMARY KEY, aika timestamp NOT NULL, sisalto varchar(5000) NOT NULL, nimimerkki varchar(100) NOT NULL, keskustelu integer NOT NULL, FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id));");

        return lista;
    }
    
    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Alue (id integer PRIMARY KEY, nimi varchar(100));");
        lista.add("CREATE TABLE Keskustelu (id integer PRIMARY KEY, aihe varchar(100) NOT NULL, alue integer NOT NULL, FOREIGN KEY(alue) REFERENCES Alue(id));");
        lista.add("CREATE TABLE Viesti (id integer PRIMARY KEY, aika timestamp NOT NULL, sisalto varchar(5000) NOT NULL, nimimerkki varchar(100) NOT NULL, keskustelu integer NOT NULL, FOREIGN KEY(keskustelu) REFERENCES Keskustelu(id));");

        return lista;
    }
}
