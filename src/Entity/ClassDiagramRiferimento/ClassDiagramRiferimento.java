package Entity.ClassDiagramRiferimento;

public class ClassDiagramRiferimento {
    private int idPackage;
    private int idClassDiagram;

    public int getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(int idPackage) {
        this.idPackage = idPackage;
    }

    public int getIdClassDiagram() {
        return idClassDiagram;
    }

    public void setIdClassDiagram(int idClassDiagram) {
        this.idClassDiagram = idClassDiagram;
    }

    @Override
    public String toString() {
        return "ClassDiagramRiferimento{" +
                "idPackage=" + idPackage +
                ", idClassDiagram=" + idClassDiagram +
                '}';
    }

    public ClassDiagramRiferimento(int idPackage, int idClassDiagram) {
        this.idPackage = idPackage;
        this.idClassDiagram = idClassDiagram;
    }
}

