package Entity.Partecipa;

public class Partecipa {
    private int idPartecipa;
    private int idAssociazione;
    private int idClasse;
    private int molteplicita;
    private String ruoloClasse;

    public int getIdPartecipa() {
        return idPartecipa;
    }

    public void setIdPartecipa(int idPartecipa) {
        this.idPartecipa = idPartecipa;
    }

    public int getIdAssociazione() {
        return idAssociazione;
    }

    public void setIdAssociazione(int idAssociazione) {
        this.idAssociazione = idAssociazione;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public int getMolteplicita() {
        return molteplicita;
    }

    public void setMolteplicita(int molteplicita) {
        this.molteplicita = molteplicita;
    }

    public String getRuoloClasse() {
        return ruoloClasse;
    }

    public void setRuoloClasse(String ruoloClasse) {
        this.ruoloClasse = ruoloClasse;
    }

    @Override
    public String toString() {
        return "Partecipa{" +
                "idPartecipa=" + idPartecipa +
                ", idAssociazione=" + idAssociazione +
                ", idClasse=" + idClasse +
                ", molteplicita=" + molteplicita +
                ", ruoloClasse='" + ruoloClasse + '\'' +
                '}';
    }

    public Partecipa(int idPartecipa, int idAssociazione, int idClasse, int molteplicita, String ruoloClasse) {
        this.idPartecipa = idPartecipa;
        this.idAssociazione = idAssociazione;
        this.idClasse = idClasse;
        this.molteplicita = molteplicita;
        this.ruoloClasse = ruoloClasse;
    }
}