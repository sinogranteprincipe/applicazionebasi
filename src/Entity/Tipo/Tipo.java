package Entity.Tipo;

public class Tipo {

    private int id;
    private String nome;
    private String range = null;
    private int idClasseDefinente;
    private int idClassDiagram;
    private int dimensioneArray = -1;
    private boolean eArray = false;
    private boolean ePrimitivo = false;

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

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getIdClasseDefinente() {
        return idClasseDefinente;
    }

    public void setIdClasseDefinente(int idClasseDefinente) {
        this.idClasseDefinente = idClasseDefinente;
    }

    public int getIdClassDiagram() {
        return idClassDiagram;
    }

    public void setIdClassDiagram(int idClassDiagram) {
        this.idClassDiagram = idClassDiagram;
    }

    public int getDimensioneArray() {
        return dimensioneArray;
    }

    public void setDimensioneArray(int dimensioneArray) {
        this.dimensioneArray = dimensioneArray;
    }

    public boolean iseArray() {
        return eArray;
    }

    public void seteArray(boolean eArray) {
        this.eArray = eArray;
    }

    public boolean isePrimitivo() {
        return ePrimitivo;
    }

    public void setePrimitivo(boolean ePrimitivo) {
        this.ePrimitivo = ePrimitivo;
    }

    @Override
    public String toString() {
        return "Tipo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", range='" + range + '\'' +
                ", idClasseDefinente=" + idClasseDefinente +
                ", idClassDiagram=" + idClassDiagram +
                ", dimensioneArray=" + dimensioneArray +
                ", eArray=" + eArray +
                ", ePrimitivo=" + ePrimitivo +
                '}';
    }

    public Tipo(int id, String nome, String range, int idClasseDefinente, int idClassDiagram, int dimensioneArray, boolean eArray, boolean ePrimitivo) {
        this.id = id;
        this.nome = nome;
        this.range = range;
        this.idClasseDefinente = idClasseDefinente;
        this.idClassDiagram = idClassDiagram;
        this.dimensioneArray = dimensioneArray;
        this.eArray = eArray;
        this.ePrimitivo = ePrimitivo;
    }

}