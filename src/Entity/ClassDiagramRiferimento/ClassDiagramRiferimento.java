package Entity.ClassDiagramRiferimento;

public class ClassDiagramRiferimento {
    private String idPackage;
    private String idClassDiagram;

    public ClassDiagramRiferimento(String idPackage, String idClassDiagram) {
        this.idPackage = idPackage;
        this.idClassDiagram = idClassDiagram;
    }

    public String getIdPackage() {
        return idPackage;
    }

    public void setIdPackage(String idPackage) {
        this.idPackage = idPackage;
    }

    public String getIdClassDiagram() {
        return idClassDiagram;
    }

    public void setIdClassDiagram(String idClassDiagram) {
        this.idClassDiagram = idClassDiagram;
    }

}
