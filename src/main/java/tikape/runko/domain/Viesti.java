
package tikape.runko.domain;

import java.sql.Timestamp;


public class Viesti {
    
    private Integer id;
    private int keskustelu;
    private Timestamp aika;
    private String sisalto;

    public Viesti(Integer id, Timestamp aika, String sisalto, int keskustelu) {
        this.id = id;
        this.keskustelu = keskustelu;
        this.aika = aika;
        this.sisalto = sisalto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getKeskustelu() {
        return keskustelu;
    }

    public Timestamp getAika() {
        return aika;
    }

    public String getSisalto() {
        return sisalto;
    }

    public void setKeskustelu(int keskustelu) {
        this.keskustelu = keskustelu;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

}
