package Entity.Associazione;

import Entity.ClassDiagram.ClassDiagram;
import Entity.MyOracleConnection;
import Entity.TipoDiAssociazione;
import Entity.TipoDiVisibilita;
import oracle.jdbc.AdditionalDatabaseMetaData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssociazioneDAO {
    Connection sharedDatabase;

    private static final String READ_BY_ID= "SELECT \"ID_ASSOCIAZIONE\", \"NOME\",\"RAFFIGURA\", \"NUMERO_MEMBRI\", \"VISIBILITA\", \"COMMENTO\", \"ID_CLASSE_ASSOCIAZIONE\", \"ID_CLASS_DIAGRAM\" FROM \"ASSOCIAZIONE\" WHERE \"ID_ASSOCIAZIONE\"=?";
    private static final String READ_ALL_IN_CLASS_DIAGRAM = "SELECT \"ID_ASSOCIAZIONE\", \"NOME\",\"RAFFIGURA\", \"NUMERO_MEMBRI\", \"VISIBILITA\", \"COMMENTO\", \"ID_CLASSE_ASSOCIAZIONE\", \"ID_CLASS_DIAGRAM\" FROM \"ASSOCIAZIONE\" WHERE \"ID_CLASS_DIAGRAM\"=?";
    private static final String CREATE_ASSOCIAZIONE ="INSERT INTO \"ASSOCIAZIONE\"(\"NOME\",\"RAFFIGURA\",\"NUMERO_MEMBRI\",\"VISIBILITA\",\"COMMENTO\",\"ID_CLASSE_ASSOCIAZIONE\", \"ID_CLASS_DIAGRAM\")VALUES(?,?,?,?,?,?,?)";
    public AssociazioneDAO() throws SQLException{
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Associazione> readAllInClassDiagram(ClassDiagram cd) throws SQLException {
        List<Associazione> associazioni = new ArrayList<>();
        Associazione a;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASS_DIAGRAM);
        preparedStatement.setInt(1, cd.getId());
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


    public Associazione readById(int anId) throws SQLException {
        Associazione a;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_BY_ID);
        preparedStatement.setInt(1, anId);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        result.next();
        a = new Associazione(result.getInt(1), result.getString(2), TipoDiAssociazione.getTipoDiAssociazioneByName(result.getString(3)), result.getInt(4), TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(5)),result.getString(6), result.getInt(7), result.getInt(8));

        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return a;
    }


    public boolean createAssociazione(Associazione a) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = sharedDatabase.prepareStatement(CREATE_ASSOCIAZIONE);
        preparedStatement.setString(1, a.getNome());
        preparedStatement.setString(2, (a.getRaffigura().name()));
        preparedStatement.setInt(3,a.getNumeroMembri());
        preparedStatement.setString(4,a.getVisibilita().name());
        preparedStatement.setString(5,a.getCommento());
        if(a.getIdClasseDiAssociazione() < 0){
            preparedStatement.setNull(6, Types.INTEGER);
        }else{
            preparedStatement.setInt(6, a.getIdClasseDiAssociazione());
        }
        preparedStatement.setInt(7, a.getIdClassDiagram());
        return preparedStatement.execute();
    }
}
