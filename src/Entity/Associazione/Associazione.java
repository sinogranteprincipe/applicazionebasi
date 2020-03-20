package Entity.Associazione;

import Entity.ClassDiagram.ClassDiagram;
import Entity.Classe.Classe;
import Entity.TipoDiAssociazione;
import Entity.TipoDiVisibilita;

public class Associazione {

    private int id;
    private String nome;
    private TipoDiAssociazione raffigura;
    private int numeroMembri;
    private TipoDiVisibilita visibilita;
    private String commento = null;
    private int idClasseDiAssociazione;
    private int idClassDiagram;

    public Associazione(int id, String nome, TipoDiAssociazione raffigura, int numeroMembri, TipoDiVisibilita visibilita, String commento, int idClasseDiAssociazione, int idClassDiagram) {
        this.id = id;
        this.nome = nome;
        this.raffigura = raffigura;
        this.numeroMembri = numeroMembri;
        this.visibilita = visibilita;
        this.commento = commento;
        this.idClasseDiAssociazione = idClasseDiAssociazione;
        this.idClassDiagram = idClassDiagram;
    }

    public int getIdClasseDiAssociazione() {
        return idClasseDiAssociazione;
    }

    public void setIdClasseDiAssociazione(int idClasseDiAssociazione) {
        this.idClasseDiAssociazione = idClasseDiAssociazione;
    }

    public int getIdClassDiagram() {
        return idClassDiagram;
    }

    public void setIdClassDiagram(int idClassDiagram) {
        this.idClassDiagram = idClassDiagram;
    }

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

    public TipoDiAssociazione getRaffigura() {
        return raffigura;
    }

    public void setRaffigura(TipoDiAssociazione raffigura) {
        this.raffigura = raffigura;
    }

    public int getNumeroMembri() {
        return numeroMembri;
    }

    public void setNumeroMembri(int numeroMembri) {
        this.numeroMembri = numeroMembri;
    }

    public TipoDiVisibilita getVisibilita() {
        return visibilita;
    }

    public void setVisibilita(TipoDiVisibilita visibilita) {
        this.visibilita = visibilita;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    @Override
    public String toString() {
        return nome;
    }
}