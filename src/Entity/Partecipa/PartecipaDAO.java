package Entity.Partecipa;

import Entity.Associazione.Associazione;
import Entity.Classe.Classe;
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

public class PartecipaDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_ASSOCIAZIONE ="SELECT \"ID_PARTECIPA\",\"ID_ASSOCIAZIONE\",\"ID_CLASSE\", \"MOLTEPLICITA\", \"RUOLOCLASSE\" FROM \"PARTECIPA\" WHERE \"ID_ASSOCIAZIONE\" =?";
    private static final String READ_ALL_OF_CLASSE = "SELECT \"ID_PARTECIPA\",\"ID_ASSOCIAZIONE\",\"ID_CLASSE\", \"MOLTEPLICITA\", \"RUOLOCLASSE\" FROM \"PARTECIPA\" WHERE \"ID_CLASSE\" =?";
    public PartecipaDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Partecipa> readAllInAssociazione(Associazione a) throws SQLException {
        List<Partecipa> members = new ArrayList<>();
        Partecipa p;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_ASSOCIAZIONE);
        preparedStatement.setInt(1, a.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            p = new Partecipa(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getString(5));
            members.add(p);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return members;
    }


    public List<Partecipa> readAllOfClasse(Classe c) throws SQLException {
        List<Partecipa> members = new ArrayList<>();
        Partecipa p;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_OF_CLASSE);
        preparedStatement.setInt(1, c.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            p = new Partecipa(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4), result.getString(5));
            members.add(p);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return members;
    }
}
