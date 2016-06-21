/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tikape.runko.domain.Alue;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Timestamp aika = rs.getTimestamp("aika");
        String sisalto = rs.getString("sisalto");
        Keskustelu k = new KeskusteluDao(database).findOne(id);
        Viesti o = new Viesti(id, k, aika, sisalto);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    public int palautaSuurinId() throws SQLException { //En keksinyt järkevää tapaa saada "seuraavaa id:tä" uudelle viestille,
                                                       // joten tein tällaisen metodin :)
        List<Viesti> viestit = this.findAll();
        int id = 0;
        for (Viesti v : viestit) {
            int luku = v.getId();
            if (luku > id) {
                id = luku;
            }
        }
        return id;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti");

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();

        Map<Integer, List<Viesti>> viestienKeskustelut = new HashMap<>();

        while (rs.next()) {

            Integer id = rs.getInt("id");
            Timestamp aika = rs.getTimestamp("aika");
            String sisalto = rs.getString("sisalto");
            Keskustelu k = new KeskusteluDao(database).findOne(id);
            Viesti v = new Viesti(id, k, aika, sisalto);
            viestit.add(v);

            Integer keskustelu = rs.getInt("keskustelu");

            if (!viestienKeskustelut.containsKey(keskustelu)) {
                viestienKeskustelut.put(keskustelu, new ArrayList<>());
            }
            viestienKeskustelut.get(keskustelu).add(v);
        }

        rs.close();
        stmt.close();
        connection.close();

        return viestit;
    }

    public List<Viesti> findAllIn(Collection<Integer> keys) throws SQLException {
        if (keys.isEmpty()) {
            return new ArrayList<>();
        }

        // Luodaan IN-kyselyä varten paikat, joihin arvot asetetaan --
        // toistaiseksi IN-parametrille ei voi antaa suoraan kokoelmaa
        StringBuilder muuttujat = new StringBuilder("?");
        for (int i = 1; i < keys.size(); i++) {
            muuttujat.append(", ?");
        }

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id IN (" + muuttujat + ")");
        int laskuri = 1;
        for (Integer key : keys) {
            stmt.setObject(laskuri, key);
            laskuri++;
        }

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Timestamp aika = rs.getTimestamp("aika");
            String sisalto = rs.getString("sisalto");
            KeskusteluDao k = new KeskusteluDao(database);
            Keskustelu ab = k.findOne(id);
            viestit.add(new Viesti(id, ab, aika, sisalto));
        }

        return viestit;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    //@Override
    //public Keskustelu findOne(Integer key) throws SQLException {
    // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    public List<Viesti> findAllIn(String n) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAllIn(Integer keskustelunId) throws SQLException {
        if (keskustelunId == null) {
            return new ArrayList<>();
        }

        // Luodaan IN-kyselyä varten paikat, joihin arvot asetetaan --
        // toistaiseksi IN-parametrille ei voi antaa suoraan kokoelmaa
//        StringBuilder muuttujat = new StringBuilder("?");
//        for (int i = 1; i < alueenNimi.size(); i++) {
//            muuttujat.append(", ?");
//        }
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE keskustelu = ?");
        stmt.setInt(1, keskustelunId);
        int laskuri = 1;
//        for (Integer key : keys) {
//            stmt.setObject(laskuri, key);
//            laskuri++;
//        }

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Timestamp aika = rs.getTimestamp("aika");
            String sisalto = rs.getString("sisalto");
            KeskusteluDao k = new KeskusteluDao(database);
            Keskustelu ab = k.findOne(id);
            viestit.add(new Viesti(id, ab, aika, sisalto));
        }

        return viestit;
    }
}
