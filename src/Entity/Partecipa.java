package Entity;

public class Partecipa {

    private String molteplicita;
    private String ruoloClasse = NULL;

    public String getMolteplicita() {
        return molteplicita;
    }

    public void setMolteplicita(String molteplicita) {
        this.molteplicita = molteplicita;
    }

    public String getRuoloClasse() {
        return ruoloClasse;
    }

    public void setRuoloClasse(String ruoloClasse) {
        this.ruoloClasse = ruoloClasse;
    }

    public Partecipa(String molteplicita, String ruoloClasse) {
        this.molteplicita = molteplicita;
        this.ruoloClasse = ruoloClasse;
    }
}