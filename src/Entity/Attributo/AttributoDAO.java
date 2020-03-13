package Entity.Attributo;

import Entity.Associazione.Associazione;
import Entity.MyOracleConnection;
import Entity.TipoDiAssociazione;
import Entity.TipoDiVisibilita;
import org.w3c.dom.Attr;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttributoDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_CLASSE ="SELECT \"ID_ATTRIBUTO\",\"NOME\", \"ID_TIPO\", \"VISIBILITA\",\"RANGE_VALORI\", \"VALORE_DEFAULT\", \"STEREOTIPO\",\"ID_CLASSE\",\"POSIZIONE\" FROM \"ATTRIBUTO\" WHERE \"ID_CLASSE=?\"";

    public AttributoDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Attributo> getAllAttributesInClass(int aClassId) throws SQLException {
        List<Attributo> attributes = new ArrayList<>();
        Attributo  a;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASSE);
        preparedStatement.setInt(1, aClassId);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            a = new Attributo(result.getInt(1), result.getString(2), result.getInt(3), TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(4)), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9));
            attributes.add(a);
        }
        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return attributes;
    }
}
