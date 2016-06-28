package tikape.runko.domain;

import java.sql.Timestamp;

public class Viesti {

    private Integer id;
    private Keskustelu keskustelu;
    private Timestamp aika;
    private String sisalto;
    private String nimimerkki;

    public Viesti(Integer id, Keskustelu keskustelu, Timestamp aika, String sisalto) {
        this.id = id;
        this.keskustelu = keskustelu;
        this.aika = aika;
        this.sisalto = sisalto;
    }

    public Viesti(Integer id, Keskustelu keskustelu, Timestamp aika, String sisalto, String nimimerkki) {
        this.id = id;
        this.keskustelu = keskustelu;
        this.aika = aika;
        this.sisalto = sisalto;
        this.nimimerkki = nimimerkki;
    }

    public Viesti(Timestamp aika, String sisalto) {
        this.aika = aika;
        this.sisalto = sisalto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Keskustelu getKeskustelu() {
        return keskustelu;
    }

    public Timestamp getAika() {
        return aika;
    }

    public String getSisalto() {
        return sisalto;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setKeskustelu(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

}
