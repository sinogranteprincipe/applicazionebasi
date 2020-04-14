package Entity.Package;

import java.util.Objects;

public class Package {

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

    public Package(int id, String nome, String commento) {
        this.id = id;
        this.commento = commento;
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return getId() == aPackage.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", commento='" + commento + '\'' +
                '}';
    }
}
