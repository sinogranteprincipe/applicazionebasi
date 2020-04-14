package Entity.Classe;

import Entity.ClassDiagram.ClassDiagram;
import Entity.MyOracleConnection;
import Entity.RuoloAssociazione;
import Entity.TipoDiClasse;
import Entity.TipoDiVisibilita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    /*La connection sharedDatabase serve a connettersi al db, il costruttore se la piglia dalla classe statica MyOracleConnection con il metodo
    * getConnection, è automatico e sta dapertutto quindi non ti devi preoccupare di modificarla*/
    Connection sharedDatabase;

    /*Queste sono le query che vanno lanciate (so importanti i \"NOME_ATTRIBUTO\", altrimenti ti da eccezione). I punti interrogativi sono i parametri da settare.*/
    private static final String READ_ALL_IN_CLASS_DIAGRAM ="SELECT \"ID_CLASSE\",\"NOME\", \"VISIBILITA\", \"STEREOTIPO\", \"RAPPRESENTA\", \"ID_TIPO_DEFINITO\", \"ID_ASSOCIAZIONE\", \"ID_CLASS_DIAGRAM\", \"TIPO_CLASSE\" FROM \"CLASSE\"WHERE \"ID_CLASS_DIAGRAM\" = ?";
    private static final String ADD_CLASS_TO_DIAGRAM ="INSERT INTO \"CLASSE\"(\"NOME\",\"VISIBILITA\",\"STEREOTIPO\",\"RAPPRESENTA\",\"ID_ASSOCIAZIONE\",\"ID_CLASS_DIAGRAM\",\"TIPO_CLASSE\")VALUES(?,?,?,?,?,?,?) ";

    public ClasseDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    /*Il metodo prende un classdiagram e restituisce una lista delle classi in quel class diagram*/
    public List<Classe> readAllInCLassDIagram(ClassDiagram cd) throws SQLException {
        /*Inizializza la lista di classi*/
        List<Classe> classes = new ArrayList<>();
        Classe c; //temporaneo per salvarti i valori
        PreparedStatement preparedStatement = null; //questa qua è la classe che contiene la query da lanciare
        ResultSet result = null; //il risultato della query

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASS_DIAGRAM);
        preparedStatement.setInt(1, cd.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            TipoDiVisibilita vis = TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(3));
            RuoloAssociazione role = RuoloAssociazione.getRuoloAssociazioneByName(result.getString(5));
            TipoDiClasse tipoDiClasse = TipoDiClasse.getTipoDiClasseByName(result.getString(9));
            c = new Classe(result.getInt(1), result.getString(2),vis , result.getString(4), role , result.getInt(6), result.getInt(7), result.getInt(8), tipoDiClasse );
            classes.add(c);
        }
        //chiudo il risultato
        if(result != null){
            result.close();
        }
        //chiudo il preparedStatement
        if(preparedStatement != null){
            preparedStatement.close();
        }

        return classes;
    }

    public boolean addClassToDiagram(Classe c) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = sharedDatabase.prepareStatement(ADD_CLASS_TO_DIAGRAM);
        preparedStatement.setString(1, c.getNome());
        preparedStatement.setString(2, c.getVisibilita().name());
        preparedStatement.setString(3, c.getStereotipo());
        if(c.getRappresenta().name()=="MISSING"){
            preparedStatement.setNull(4,Types.VARCHAR);
        }else {
            preparedStatement.setString(4, c.getRappresenta().name());
        }
        if(c.getIdAssociazione() > 0) {
            preparedStatement.setInt(5, c.getIdAssociazione());
        }else {
            preparedStatement.setNull(5, Types.INTEGER);
        }
        preparedStatement.setInt(6, c.getIdClassDiagram());
        preparedStatement.setString(7,c.getTipoClasse().name());
        preparedStatement.execute();
        return true;

    }

}
