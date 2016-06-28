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
import tikape.runko.domain.Alue;
import tikape.runko.domain.Keskustelu;

public class KeskusteluDao implements Dao<Keskustelu, Integer> {

    private Database database;

    public KeskusteluDao(Database database) {
        this.database = database;
    }

    public Integer haeSuurinId() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT MAX(Keskustelu.id) AS id FROM Keskustelu");
        ResultSet rs = stmt.executeQuery();
        Integer id = rs.getInt("id");
        rs.close();
        stmt.close();
        connection.close();
        return id;
    }

    public List<Keskustelu> keskusteluListaus(Integer alueenId) throws SQLException {
        if (alueenId == null) {
            return new ArrayList<>();
        }
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT Keskustelu.id AS id, Keskustelu.aihe AS aihe, COUNT(Viesti.id) AS viestien_lkm, MAX(Viesti.aika) AS viimeisin FROM Keskustelu, Viesti WHERE Keskustelu.id = Viesti.keskustelu AND Keskustelu.alue = ? GROUP BY Keskustelu.id ORDER BY viimeisin DESC LIMIT 10");
        stmt.setInt(1, alueenId);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String aihe = rs.getString("aihe");
            Integer viestien_lkm = rs.getInt("viestien_lkm");
            Timestamp viimeisin = rs.getTimestamp("viimeisin");
            keskustelut.add(new Keskustelu(id, aihe, viestien_lkm, viimeisin));
        }
        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    public Keskustelu findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String aihe = rs.getString("aihe");
        Keskustelu k = new Keskustelu(id, aihe);

        Integer alue = rs.getInt("alue");

        rs.close();
        stmt.close();
        connection.close();

        AlueDao alueDao = new AlueDao(database);
        k.setAlue(alueDao.findOne(alue));

        return k;
    }

    @Override
    public List<Keskustelu> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu");

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();

        Map<String, List<Keskustelu>> keskusteluidenAlueet = new HashMap<>();

        while (rs.next()) {

            Integer id = rs.getInt("id");
            String aihe = rs.getString("aihe");

            Keskustelu k = new Keskustelu(id, aihe);
            keskustelut.add(k);

            String alue = rs.getString("alue");

            if (!keskusteluidenAlueet.containsKey(alue)) {
                keskusteluidenAlueet.put(alue, new ArrayList<>());
            }
            keskusteluidenAlueet.get(alue).add(k);
        }

        rs.close();
        stmt.close();
        connection.close();

        return keskustelut;
    }

    public List<Keskustelu> findAllIn(Integer alueenId) throws SQLException {
        if (alueenId == null) {
            return new ArrayList<>();
        }

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Keskustelu WHERE alue = ?");
        stmt.setInt(1, alueenId);

        ResultSet rs = stmt.executeQuery();
        List<Keskustelu> keskustelut = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String aihe = rs.getString("aihe");
            AlueDao a = new AlueDao(database);
            Alue ab = a.findOne(id);
            keskustelut.add(new Keskustelu(id, ab, aihe));
        }

        return keskustelut;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
