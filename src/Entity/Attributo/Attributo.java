package Entity.Attributo;

import Entity.TipoDiVisibilita;

public class Attributo {

    private int id;
    private String nome;
    private int idTipo;
    private TipoDiVisibilita visibilita;
    private String valoreDefault = null;
    private String stereotipo = null;
    private String range;
    private int posizione=1;
    private int idClasse;

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
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

    public Attributo(int id, String nome,int idTipo, TipoDiVisibilita visibilita,  String range, String valoreDefault, String stereotipo, int idClasse, int posizione) {
        this.id = id;
        this.idTipo= idTipo;
        this.nome = nome;
        this.visibilita = visibilita;
        this.valoreDefault = valoreDefault;
        this.stereotipo = stereotipo;
        this.range = range;
        this.posizione = posizione;
        this.idClasse = idClasse;
    }

    @Override
    public String toString() {
        return "Attributo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idTipo=" + idTipo +
                ", visibilita=" + visibilita +
                ", valoreDefault='" + valoreDefault + '\'' +
                ", stereotipo='" + stereotipo + '\'' +
                ", range='" + range + '\'' +
                ", posizione=" + posizione +
                ", idClasse=" + idClasse +
                '}';
    }
}