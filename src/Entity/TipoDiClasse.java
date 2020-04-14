package Entity;

import Entity.Tipo.Tipo;

public enum TipoDiClasse {
    STANDARD, DATATYPE, PRIMITIVETYPE, ENUMERATION, INTERFACE, MISSING;

    public static TipoDiClasse getTipoDiClasseByName(String aClassType){
        if(aClassType == null){
            return  TipoDiClasse.MISSING;
        }
        switch (aClassType){
            case "STANDARD":
                return TipoDiClasse.STANDARD;
            case "DATATYPE":
                return TipoDiClasse.DATATYPE;
            case "PRIMITIVETYPE":
                return TipoDiClasse.PRIMITIVETYPE;
            case "ENUMERATION":
                return TipoDiClasse.ENUMERATION;
            case "INTERFACE":
                return TipoDiClasse.INTERFACE;
            default:
                return TipoDiClasse.MISSING;
        }
    }
}
