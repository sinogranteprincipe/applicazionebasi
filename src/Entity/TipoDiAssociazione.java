package Entity;

import Entity.Tipo.Tipo;

public enum TipoDiAssociazione {
    DIRETTA, BIDIREZIONALE, COMPOSIZIONE, AGGREGAZIONE, MISSING;

    public static TipoDiAssociazione getTipoDiAssociazioneByName(String aType){
        switch (aType){
            case "DIRETTA":
                return TipoDiAssociazione.DIRETTA;
            case "BIDIREZIONALE":
                return TipoDiAssociazione.BIDIREZIONALE;
            case "COMPOSIZIONE":
                return TipoDiAssociazione.COMPOSIZIONE;
            case "AGGREGAZIONE":
                return TipoDiAssociazione.AGGREGAZIONE;
            default:
                return TipoDiAssociazione.MISSING;
        }
    }
}
