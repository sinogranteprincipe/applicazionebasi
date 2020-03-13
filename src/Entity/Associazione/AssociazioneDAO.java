package Entity.Associazione;

import Entity.MyOracleConnection;
import Entity.TipoDiAssociazione;
import Entity.TipoDiVisibilita;
import oracle.jdbc.AdditionalDatabaseMetaData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssociazioneDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_CLASS_DIAGRAM = "SELECT \"ID_ASSOCIAZIONE\", \"NOME\",\"RAFFIGURA\", \"NUMERO_MEMBRI\", \"VISIBILITA\", \"COMMENTO\", \"ID_CLASSE_DI_ASSOCIAZIONE\", \"ID_CLASS_DIAGRAM\" FROM \"ASSOCIAZIONE\" WHERE \"ID_CLASS_DIAGRAM\"=?";

    public AssociazioneDAO() throws SQLException{
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Associazione> readAllInClassDiagram(int aClassDiagramId) throws SQLException {
        List<Associazione> associazioni = new ArrayList<>();
        Associazione a;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASS_DIAGRAM);
        preparedStatement.setInt(1, aClassDiagramId);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            a = new Associazione(result.getInt(1), result.getString(2), TipoDiAssociazione.getTipoDiAssociazioneByName(result.getString(3)), result.getInt(4), TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(5)),result.getString(6), result.getInt(7), result.getInt(8));
            associazioni.add(a);
        }
        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return associazioni;
    }


}
