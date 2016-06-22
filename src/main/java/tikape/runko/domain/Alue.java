package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;

public class Alue {
    
    private Integer id;
    private String nimi;
    private List<keskustelu> keskustelut;

    public Alue(int id, String nimi) {
        this.nimi = nimi;
        this.id = id;
        this.keskustelut = new ArrayList<>();
    }

    public String getNimi() {
        return nimi;
    }

    public int getId() {
        return id;
    }
   
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

}
