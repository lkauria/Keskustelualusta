package tikape.runko.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Keskustelu {

    private Integer id;
    private Alue alue;
    private String aihe;
    private List<Viesti> viestit;
    private Integer viestien_lkm;
    private Timestamp viimeisin;

    public Keskustelu(Integer id, String aihe) {
        this.id = id;
        this.aihe = aihe;
        this.viestit = new ArrayList<>();
    }

    public Keskustelu(Integer id, Alue alue, String aihe) {
        this.id = id;
        this.alue = alue;
        this.aihe = aihe;
        this.viestit = new ArrayList<>();
    }

    public Keskustelu(Integer id, String aihe, Integer viestien_lkm, Timestamp viimeisin) {
        this.id = id;
        this.aihe = aihe;
        this.viestien_lkm = viestien_lkm;
        this.viimeisin = viimeisin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Alue getAlue() {
        return alue;
    }

    public void setAlue(Alue alue) {
        this.alue = alue;
    }

    public String getAihe() {
        return aihe;
    }

    public void setAihe(String aihe) {
        this.aihe = aihe;
    }

    public List<Viesti> getViestit() {
        return viestit;
    }

    public void setViestit(List<Viesti> viestit) {
        this.viestit = viestit;
    }

    public Integer getViestien_lkm() {
        return viestien_lkm;
    }

    public Timestamp getViimeisin() {
        return viimeisin;
    }

    public void setViestien_lkm(Integer viestien_lkm) {
        this.viestien_lkm = viestien_lkm;
    }

    public void setViimeisin(Timestamp viimeisin) {
        this.viimeisin = viimeisin;
    }

}
