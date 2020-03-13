package Entity.Classe;

import Entity.ClassDiagram.ClassDiagram;
import Entity.MyOracleConnection;
import Entity.RuoloAssociazione;
import Entity.TipoDiClasse;
import Entity.TipoDiVisibilita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClasseDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_CLASS_DIAGRAM ="SELECT \"ID_CLASSE\",\"NOME\", \"VISIBILITA\", \"STEREOTIPO\", \"RAPPRESENTA\", \"ID_TIPO_DEFINITO\", \"ID_ASSOCIAZIONE\", \"ID_CLASS_DIAGRAM\", \"TIPO_CLASSE\" FROM \"CLASSE\"WHERE \"ID_CLASS_DIAGRAM\" = ?";

    public ClasseDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }
    public List<Classe> readAllInCLassDIagram(int aCdId) throws SQLException {
        List<Classe> classes = new ArrayList<>();
        Classe c;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASS_DIAGRAM);
        preparedStatement.setInt(1, aCdId);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            c = new Classe(result.getInt(1), result.getString(2), TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(3)), result.getString(4), RuoloAssociazione.getRuoloAssociazioneByName(result.getString(5)), result.getInt(6), result.getInt(7), result.getInt(8), TipoDiClasse.getTipoDiClasseByName(result.getString(9)));
            classes.add(c);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return classes;
    }
}
