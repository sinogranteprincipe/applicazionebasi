package Entity;

public class Parametro {

    private String nome;
    private int posizione;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    public Parametro(String nome, int posizione) {
        this.nome = nome;
        this.posizione = posizione;
    }

}