package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class keskustelu {

    private Integer id;
    private Alue alue;
    private String aihe;
    private List<Viesti> viestit;

    public keskustelu(Integer id, String aihe) {
        this.id = id;
        this.aihe = aihe;
        this.viestit = new ArrayList<>();
    }

    public keskustelu(Integer id, Alue alue, String aihe) {
       this.id = id;
       this.alue = alue;
       this.aihe = aihe;
       this.viestit = new ArrayList<>();
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

}
