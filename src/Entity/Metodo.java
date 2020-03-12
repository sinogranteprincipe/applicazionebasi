package Entity;

public enum TipoDiVisibilita {
    PUBLIC, PRIVATE, PROTECTED, PACKAGE;
}

public class Metodo {

    private int id;
    private String nome;
    private TipoDiVisibilita visibilita;
    private int posizione=1;

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

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public Metodo(int id, String nome, TipoDiVisibilita visibilita, int posizione) {
        this.id = id;
        this.nome = nome;
        this.visibilita = visibilita;
        this.posizione = posizione;
    }

}