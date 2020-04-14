package Entity.GerarchiaClassi;

public class GerarchiaClassi {

    private int idClasseBase;
    private int idSottoClasse;
    private int idSuperClasse;

    public int getIdClasseBase() {
        return idClasseBase;
    }

    public void setIdClasseBase(int idClasseBase) {
        this.idClasseBase = idClasseBase;
    }

    public int getIdSottoClasse() {
        return idSottoClasse;
    }

    public void setIdSottoClasse(int idSottoClasse) {
        this.idSottoClasse = idSottoClasse;
    }

    public int getIdSuperClasse() {
        return idSuperClasse;
    }

    public void setIdSuperClasse(int idSuperClasse) {
        this.idSuperClasse = idSuperClasse;
    }

    public GerarchiaClassi(int idClasseBase, int idSottoClasse, int idSuperClasse) {
        this.idClasseBase = idClasseBase;
        this.idSottoClasse = idSottoClasse;
        this.idSuperClasse = idSuperClasse;
    }

    @Override
    public String toString() {
        return "GerarchiaClassi{" +
                "idClasseBase=" + idClasseBase +
                ", idSottoClasse=" + idSottoClasse +
                ", idSuperClasse=" + idSuperClasse +
                '}';
    }
}