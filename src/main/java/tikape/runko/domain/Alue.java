package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Alue {

    private String nimi;
    private List<Keskustelu> keskustelut;

    public Alue(String nimi) {
        this.nimi = nimi;
        this.keskustelut = new ArrayList<>();
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
