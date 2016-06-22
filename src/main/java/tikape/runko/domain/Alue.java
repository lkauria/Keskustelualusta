package tikape.runko.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Alue {

    private Integer id;
    private String nimi;
    private List<Keskustelu> keskustelut;
    private Integer lkm;
    private Timestamp viimeisin;

    public Alue(Integer id, String nimi) {
        this.nimi = nimi;
        this.id = id;
        this.keskustelut = new ArrayList<>();
    }

    public Alue(Integer id, String nimi, Integer lkm, Timestamp viimeisin) {
        this.id = id;
        this.nimi = nimi;
        this.lkm = lkm;
        this.viimeisin = viimeisin;
    }

    public String getNimi() {
        return nimi;
    }

    public Integer getId() {
        return id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public List<Keskustelu> getKeskustelut() {
        return keskustelut;
    }

    public Integer getLkm() {
        return lkm;
    }

    public Timestamp getViimeisin() {
        return viimeisin;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKeskustelut(List<Keskustelu> keskustelut) {
        this.keskustelut = keskustelut;
    }

    public void setLkm(Integer lkm) {
        this.lkm = lkm;
    }

    public void setViimeisin(Timestamp viimeisin) {
        this.viimeisin = viimeisin;
    }

}
