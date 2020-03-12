package Entity;

public class Attributo {

    private String nome;
    private TipoDiVisibilita visibilita;
    private String valoreDefault = NULL;
    private String stereotipo = NULL;
    private String range = NULL;
    private int posizione=1;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoDiVisibilita getVisibilita() {
        return visibilita;
    }

    public void setVisibilita(TipoDiVisibilita visibilita) {
        this.visibilita = visibilita;
    }

    public String getValoreDefault() {
        return valoreDefault;
    }

    public void setValoreDefault(String valoreDefault) {
        this.valoreDefault = valoreDefault;
    }

    public String getStereotipo() {
        return stereotipo;
    }

    public void setStereotipo(String stereotipo) {
        this.stereotipo = stereotipo;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public Attributo(String nome, TipoDiVisibilita visibilita, String valoreDefault, String stereotipo, String range, int posizione) {
        this.nome = nome;
        this.visibilita = visibilita;
        this.valoreDefault = valoreDefault;
        this.stereotipo = stereotipo;
        this.range = range;
        this.posizione = posizione;
    }

}