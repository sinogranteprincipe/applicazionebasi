package Entity;

import Entity.Tipo.Tipo;

public enum TipoDiVisibilita {
    PUBLIC, PRIVATE, PROTECTED, PACKAGE, MISSING;

    public static TipoDiVisibilita getTipoDiVisibilitaByName(String aName){
        if(aName==null){
            return TipoDiVisibilita.MISSING;
        }
        switch (aName){
            case "PUBLIC":
                return TipoDiVisibilita.PUBLIC;
            case "PRIVATE":
                return TipoDiVisibilita.PRIVATE;
            case "PROTECTED":
                return TipoDiVisibilita.PROTECTED;
            case "PACAKAGE":
                return TipoDiVisibilita.PACKAGE;
            default:
                return TipoDiVisibilita.MISSING;
        }
    }

}
