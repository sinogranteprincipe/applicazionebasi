package Entity.Parametro;

import Entity.Metodo.Metodo;
import Entity.MyOracleConnection;
import Entity.Package.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class ParametroDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_METHOD ="SELECT \"ID_PARAMETRO\", \"NOME\", \"ID_TIPO\", \"ID_METODO\", \"POSIZIONE\" FROM \"PARAMETRO\" WHERE \"ID_METODO\" = ?";

    public ParametroDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Parametro> readAllParametersInMethod(Metodo m) throws SQLException {
        List<Parametro> parameters = new ArrayList<>();
        Parametro p;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_METHOD);
        preparedStatement.setInt(1, m.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            p = new Parametro(result.getInt(1), result.getString(2), result.getInt(3), result.getInt(4), result.getInt(5));
            parameters.add(p);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return parameters;
    }
}
