package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Keskustelu {

    private Integer id;
    private Alue alue;
    private String aihe;
    private List<Viesti> viestit;

    public Keskustelu(Integer id, String aihe) {
        this.id = id;
        this.aihe = aihe;
        this.viestit = new ArrayList<>();
    }

    public Keskustelu(Integer id, String alue, String aihe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
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

}
