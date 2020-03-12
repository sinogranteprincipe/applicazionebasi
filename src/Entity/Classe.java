package Entity;

public class Classe {

    private int id;
    private String nome;
    private TipoDiVisibilita visibilita;
    private RuoloAssociazione rappresenta;
    private TipoDiClasse tipoClasse;
    private String stereotipo = NULL;
    private String commento = NULL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public Classe(int id, String nome, TipoDiVisibilita visibilita, RuoloAssociazione rappresenta, TipoDiClasse tipoClasse, String stereotipo, String commento) {
        this.id = id;
        this.nome = nome;
        this.visibilita = visibilita;
        this.rappresenta = rappresenta;
        this.tipoClasse = tipoClasse;
        this.stereotipo = stereotipo;
        this.commento = commento;
    }
}