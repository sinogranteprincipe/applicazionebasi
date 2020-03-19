package Entity.Metodo;

import Entity.Attributo.Attributo;
import Entity.Classe.Classe;
import Entity.MyOracleConnection;
import Entity.TipoDiVisibilita;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

public class MetodoDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_CLASSE ="SELECT \"ID_METODO\" , \"NOME\" ,\"HA_PARAMETRI\" ,\"ID_TIPO_RITORNO\" , \"VISIBILITA\" , \"ID_CLASSE\" , \"POSIZIONE\"  FROM \"METODO\" WHERE \"ID_CLASSE\" = ?";

    public MetodoDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Metodo> readAllInClasse(Classe c) throws SQLException{
        List<Metodo> methods = new ArrayList<>();
        Metodo m;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASSE);
        preparedStatement.setInt(1, c.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            m = new Metodo(result.getInt(1), result.getString(2), (result.getInt(3)==1),result.getInt(4), TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(5)), result.getInt(6), result.getInt(7));
            methods.add(m);
        }
        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return methods;
    }

    public List<Metodo> getAllMethodsInClass(Classe c) throws SQLException {
        List<Metodo> methods = new ArrayList<>();
        Metodo  m;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASSE);
        preparedStatement.setInt(1, c.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            m = new Metodo(result.getInt(1), result.getString(2), (result.getInt(3)==1),result.getInt(4), TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(5)), result.getInt(6), result.getInt(7));
            methods.add(m);
        }
        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return methods;
    }

    public boolean createMethod(Metodo m) throws SQLException{
        PreparedStatement preparedStatement = null;
        preparedStatement = sharedDatabase.prepareStatement(CREATE_METHOD);
        preparedStatement.setString(1, m.getNome());
        preparedStatement.setInt(2,m.getIdTipoDiRitorno());
        preparedStatement.setString(3,m.getVisibilita().name());
        preparedStatement.setInt(4,m.getIdClasse());
        preparedStatement.setInt(5,m.getPosizione());
        return preparedStatement.execute();
    }
}
