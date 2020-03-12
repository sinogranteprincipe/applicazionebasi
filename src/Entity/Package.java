package Entity;

public class Package {

    private int id;
    private String nome;
    private String commento;

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

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public Package(int id, String nome, String commento) {
        this.id = id;
        this.commento = commento;
        this.nome = nome;
    }

}
