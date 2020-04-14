package Entity;

import javax.print.attribute.standard.ReferenceUriSchemesSupported;

public enum RuoloAssociazione {
    SEMPLICE, EPDIRETTO, AGGREGATA, COMPOSTA, MISSING;

    public static RuoloAssociazione getRuoloAssociazioneByName(String aRole){
        if(aRole == null){
            return  RuoloAssociazione.MISSING;
        }
        switch (aRole){
            case "SEMPLICE":
                return RuoloAssociazione.SEMPLICE;
            case  "EPDIRETTO":
                return RuoloAssociazione.EPDIRETTO;
            case "AGGREGATA":
                return RuoloAssociazione.AGGREGATA;
            case "COMPOSTA":
                return RuoloAssociazione.COMPOSTA;
            default:
                return RuoloAssociazione.MISSING;
        }
    }
}
