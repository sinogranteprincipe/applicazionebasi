package Entity.Metodo;

import Entity.TipoDiVisibilita;

public class Metodo {

    private int id;
    private String nome;
    private boolean haParametri;
    private int idTipoDiRitorno;
    private TipoDiVisibilita visibilita;
    private int idClasse;
    private int posizione;

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

    public boolean isHaParametri() {
        return haParametri;
    }

    public void setHaParametri(boolean haParametri) {
        this.haParametri = haParametri;
    }

    public int getIdTipoDiRitorno() {
        return idTipoDiRitorno;
    }

    public void setIdTipoDiRitorno(int idTipoDiRitorno) {
        this.idTipoDiRitorno = idTipoDiRitorno;
    }

    public TipoDiVisibilita getVisibilita() {
        return visibilita;
    }

    public void setVisibilita(TipoDiVisibilita visibilita) {
        this.visibilita = visibilita;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public Metodo(int id, String nome, boolean haParametri, int idTipoDiRitorno, TipoDiVisibilita visibilita, int idClasse, int posizione) {
        this.id = id;
        this.nome = nome;
        this.haParametri = haParametri;
        this.idTipoDiRitorno = idTipoDiRitorno;
        this.visibilita = visibilita;
        this.idClasse = idClasse;
        this.posizione = posizione;
    }

    @Override
    public String toString() {
        return "Metodo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", haParametri=" + haParametri +
                ", idTipoDiRitorno=" + idTipoDiRitorno +
                ", visibilita=" + visibilita +
                ", idClasse=" + idClasse +
                ", posizione=" + posizione +
                '}';
    }
}