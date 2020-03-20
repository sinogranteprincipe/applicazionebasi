package Entity.Parametro;

public class Parametro {
    private int idParametro;
    private String nome;
    private int idTipo;
    private int idMetodo;
    private int posizione;

    public int getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(int idParametro) {
        this.idParametro = idParametro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdMetodo() {
        return idMetodo;
    }

    public void setIdMetodo(int idMetodo) {
        this.idMetodo = idMetodo;
    }

    public int getPosizione() {
        return posizione;
    }

    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }

    @Override
    public String toString() {
        return nome;
    }

    public Parametro(int idParametro, String nome, int idTipo, int idMetodo, int posizione) {
        this.idParametro = idParametro;
        this.nome = nome;
        this.idTipo = idTipo;
        this.idMetodo = idMetodo;
        this.posizione = posizione;
    }
}