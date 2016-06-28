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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tikape.runko.domain.Keskustelu;
import tikape.runko.domain.Viesti;

public class ViestiDao implements Dao<Viesti, Integer> {

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    public Viesti findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE id = ?");
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

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

    public List<Viesti> findAllIn(String n) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Viesti> findAllIn(Integer keskustelunId) throws SQLException {
        if (keskustelunId == null) {
            return new ArrayList<>();
        }
        
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Viesti WHERE keskustelu = ?");
        stmt.setInt(1, keskustelunId);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            Timestamp aika = rs.getTimestamp("aika");
            String sisalto = rs.getString("sisalto");
            String nimimerkki = rs.getString("nimimerkki");
            KeskusteluDao k = new KeskusteluDao(database);
            Keskustelu ab = k.findOne(id);
            viestit.add(new Viesti(id, ab, aika, sisalto, nimimerkki));
        }
        return viestit;
    }

}
