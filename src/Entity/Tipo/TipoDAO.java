package Entity.Tipo;

import Entity.MyOracleConnection;
import Entity.Package.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_CLASS_DIAGRAM = "SELECT \"ID_TIPO\",\"NOME\", \"RANGE_VALORI\", \"ID_CLASSE_DEFINENTE\", \"ID_CLASS_DIAGRAM\", \"DIMENSIONE_ARRAY\", \"E_ARRAY\",\"E_PRIMITIVO\" FROM \"TIPO\" WHERE \"ID_CLASS_DIAGRAM\"=?";
    private static final String READ_ALL_PRIMITIVES = "SELECT \"ID_TIPO\",\"NOME\", \"RANGE_VALORI\", \"ID_CLASSE_DEFINENTE\", \"ID_CLASS_DIAGRAM\", \"DIMENSIONE_ARRAY\", \"E_ARRAY\",\"E_PRIMITIVO\" FROM \"TIPO\" WHERE \"E_PRIMITIVO\"=1";

    public TipoDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Tipo> readAllInClassDiagram(int aClassDiagram) throws SQLException{
        List<Tipo> types = new ArrayList<>();
        Tipo t;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASS_DIAGRAM);
        preparedStatement.setInt(1, aClassDiagram);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            t = new Tipo(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4),result.getInt(5), result.getInt(6),(result.getInt(7)==1),(result.getInt(8)==1));
            types.add(t);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return types;
    }

    public List<Tipo> readAllPrimitives() throws SQLException{
        List<Tipo> types = new ArrayList<>();
        Tipo t;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_PRIMITIVES);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            t = new Tipo(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4),result.getInt(5), result.getInt(6),(result.getInt(7)==1),(result.getInt(8)==1));
            types.add(t);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return types;
    }
}
