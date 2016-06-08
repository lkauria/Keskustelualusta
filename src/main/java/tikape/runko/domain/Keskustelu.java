package tikape.runko.domain;

public class Keskustelu {

    private Integer id;
    private Alue alue;
    private String aihe;

    public Keskustelu(Integer id, Alue alue, String aihe) {
        this.id = id;
        this.alue = alue;
        this.aihe = aihe;
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
