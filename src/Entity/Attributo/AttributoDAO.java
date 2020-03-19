package Entity.Attributo;

import Entity.Associazione.Associazione;
import Entity.Classe.Classe;
import Entity.Metodo.Metodo;
import Entity.MyOracleConnection;
import Entity.TipoDiAssociazione;
import Entity.TipoDiVisibilita;
import org.w3c.dom.Attr;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class AttributoDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_CLASSE ="SELECT  FROM \"ATTRIBUTO\" WHERE \"ID_CLASSE=?\"";
    private static final String CREATE_ATTRIBUTE = "INSERT INTO \"ATTRIBUTO\"(\"NOME\", \"ID_TIPO\", \"VISIBILITA\",\"RANGE_VALORI\", \"VALORE_DEFAULT\", \"STEREOTIPO\",\"ID_CLASSE\",\"POSIZIONE\")VALUES(?,?,?,?,?,?,?,?)";

    public AttributoDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Attributo> getAllAttributesInClass(Classe c) throws SQLException {
        List<Attributo> attributes = new ArrayList<>();
        Attributo  a;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASSE);
        preparedStatement.setInt(1, c.getId());
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

    public boolean createAttribute(Attributo a) throws SQLException{
        PreparedStatement preparedStatement = null;
        preparedStatement = sharedDatabase.prepareStatement(CREATE_ATTRIBUTE);
        preparedStatement.setString(1, a.getNome());
        preparedStatement.setInt(2,a.getIdTipo());
        preparedStatement.setString(3,a.getVisibilita().name());
        preparedStatement.setString(4,a.getRange());
        preparedStatement.setString(5,a.getValoreDefault());
        preparedStatement.setString(6,a.getStereotipo());
        preparedStatement.setInt(7,a.getIdClasse());
        preparedStatement.setInt(8,a.getPosizione());
        return preparedStatement.execute();
    }

    public List<Attributo> readAllInClasse(Classe c) throws SQLException{
        List<Attributo> attributes = new ArrayList<>();
        Attributo a;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASSE);
        preparedStatement.setInt(1, c.getId());
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

