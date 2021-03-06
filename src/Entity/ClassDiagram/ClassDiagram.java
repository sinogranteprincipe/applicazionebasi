package Entity.ClassDiagram;

public class ClassDiagram {

    private int id;
    private String nome;
    private String commento = null;

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

    public ClassDiagram (int id, String nome, String commento) {
        this.id = id;
        this.commento = commento;
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}