package Entity.Classe;

import Entity.RuoloAssociazione;
import Entity.TipoDiClasse;
import Entity.TipoDiVisibilita;

import java.util.Objects;

public class Classe {

    private int id;
    private String nome;
    private TipoDiVisibilita visibilita;
    private String stereotipo = null;
    private RuoloAssociazione rappresenta;
    private int idTipoDefinito;
    private int idAssociazione;
    private int idClassDiagram;
    private TipoDiClasse tipoClasse;

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

    public TipoDiVisibilita getVisibilita() {
        return visibilita;
    }

    public void setVisibilita(TipoDiVisibilita visibilita) {
        this.visibilita = visibilita;
    }

    public String getStereotipo() {
        return stereotipo;
    }

    public void setStereotipo(String stereotipo) {
        this.stereotipo = stereotipo;
    }

    public RuoloAssociazione getRappresenta() {
        return rappresenta;
    }

    public void setRappresenta(RuoloAssociazione rappresenta) {
        this.rappresenta = rappresenta;
    }

    public int getIdTipoDefinito() {
        return idTipoDefinito;
    }

    public void setIdTipoDefinito(int idTipoDefinito) {
        this.idTipoDefinito = idTipoDefinito;
    }

    public int getIdAssociazione() {
        return idAssociazione;
    }

    public void setIdAssociazione(int idAssociazione) {
        this.idAssociazione = idAssociazione;
    }

    public int getIdClassDiagram() {
        return idClassDiagram;
    }

    public void setIdClassDiagram(int idClassDiagram) {
        this.idClassDiagram = idClassDiagram;
    }

    public TipoDiClasse getTipoClasse() {
        return tipoClasse;
    }

    public void setTipoClasse(TipoDiClasse tipoClasse) {
        this.tipoClasse = tipoClasse;
    }

    @Override
    public String toString() {
        return nome;
    }


    public String toString(int i) {
        return "Classe{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", visibilita=" + visibilita +
                ", stereotipo='" + stereotipo + '\'' +
                ", rappresenta=" + rappresenta +
                ", idTipoDefinito=" + idTipoDefinito +
                ", idAssociazione=" + idAssociazione +
                ", idClassDiagram=" + idClassDiagram +
                ", tipoClasse=" + tipoClasse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Classe classe = (Classe) o;
        return getId() == classe.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Classe(int id, String nome, TipoDiVisibilita visibilita, String stereotipo, RuoloAssociazione rappresenta, int idTipoDefinito, int idAssociazione, int idClassDiagram, TipoDiClasse tipoClasse) {
        this.id = id;
        this.nome = nome;
        this.visibilita = visibilita;
        this.stereotipo = stereotipo;
        this.rappresenta = rappresenta;
        this.idTipoDefinito = idTipoDefinito;
        this.idAssociazione = idAssociazione;
        this.idClassDiagram = idClassDiagram;
        this.tipoClasse = tipoClasse;
    }

    public Classe(){
        return;
    }
}