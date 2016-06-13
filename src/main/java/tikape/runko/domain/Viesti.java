
package tikape.runko.domain;

import java.sql.Timestamp;


public class Viesti {
    
    private Keskustelu keskustelu;
    private Timestamp aika;
    private String sisalto;

    public Viesti(Timestamp aika, String sisalto) {
        this.aika = aika;
        this.sisalto = sisalto;
    }

    public Viesti(Integer keskustelu, Timestamp aika, String sisalto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public void setKeskustelu(Keskustelu keskustelu) {
        this.keskustelu = keskustelu;
    }

    public void setAika(Timestamp aika) {
        this.aika = aika;
    }

    public void setSisalto(String sisalto) {
        this.sisalto = sisalto;
    }

}
