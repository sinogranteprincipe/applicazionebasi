package Entity;

public class Tipo {

    private int id;
    private String nome;
    private String range = null;
    private int dimensioneArray = -1;
    private boolean Array = false;
    private boolean Primitivo = false;

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

    public int getDimensioneArray() {
        return dimensioneArray;
    }

    public void setDimensioneArray(int dimensioneArray) {
        this.dimensioneArray = dimensioneArray;
    }

    public boolean isArray() {
        return Array;
    }

    public void setArray(boolean array) {
        Array = array;
    }

    public boolean isPrimitivo() {
        return Primitivo;
    }

    public void setPrimitivo(boolean primitivo) {
        Primitivo = primitivo;
    }

    public Tipo(int id, String nome, String range, int dimensioneArray, boolean Array, boolean Primitivo) {
        this.id = id;
        this.nome = nome;
        this.range = range;
        this.dimensioneArray = dimensioneArray;
        this.Array = Array;
        this.Primitivo = Primitivo;
    }

}