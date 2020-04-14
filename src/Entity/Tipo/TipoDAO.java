package Entity.Tipo;

import Entity.ClassDiagram.ClassDiagram;
import Entity.Classe.Classe;
import Entity.Classe.ClasseDAO;
import Entity.MyOracleConnection;
import Entity.Package.Package;
import Entity.RuoloAssociazione;
import Entity.TipoDiClasse;
import Entity.TipoDiVisibilita;
import oracle.ucp.proxy.annotation.Pre;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDAO{
    Connection sharedDatabase;

    private static final String READ_BY_ID = "SELECT \"ID_TIPO\",\"NOME\", \"RANGE_VALORI\", \"ID_CLASSE_DEFINENTE\", \"ID_CLASS_DIAGRAM\", \"DIMENSIONE_ARRAY\", \"E_ARRAY\",\"E_PRIMITIVO\" FROM \"TIPO\" WHERE \"ID_TIPO\"=?";
    private static final String READ_ALL_IN_CLASS_DIAGRAM = "SELECT \"ID_TIPO\",\"NOME\", \"RANGE_VALORI\", \"ID_CLASSE_DEFINENTE\", \"ID_CLASS_DIAGRAM\", \"DIMENSIONE_ARRAY\", \"E_ARRAY\",\"E_PRIMITIVO\" FROM \"TIPO\" WHERE \"ID_CLASS_DIAGRAM\"=?";
    private static final String READ_ALL_PRIMITIVES = "SELECT \"ID_TIPO\",\"NOME\", \"RANGE_VALORI\", \"ID_CLASSE_DEFINENTE\", \"ID_CLASS_DIAGRAM\", \"DIMENSIONE_ARRAY\", \"E_ARRAY\",\"E_PRIMITIVO\" FROM \"TIPO\" WHERE \"E_PRIMITIVO\"=1";
    private static final String ADD_USER_DEFINED_PRIMITIVE_TYPE= "INSERT INTO TIPO(\"NOME\", \"RANGE_VALORI\", \"ID_CLASSE_DEFINENTE\", \"ID_CLASS_DIAGRAM\", \"DIMENSIONE_ARRAY\", \"E_ARRAY\", \"E_PRIMITIVO\") VALUES (?,?,?,?,?,?,?)";

    public TipoDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Tipo> readAllInClassDiagram(ClassDiagram cd) throws SQLException{
        List<Tipo> types = new ArrayList<>();
        Tipo t;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_CLASS_DIAGRAM);
        preparedStatement.setInt(1, cd.getId());
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

    public boolean addUserDefinedTipoPrimitivo(Tipo t) throws SQLException, InvalidParameterException {
        PreparedStatement preparedStatement = null;
        preparedStatement = sharedDatabase.prepareStatement(ADD_USER_DEFINED_PRIMITIVE_TYPE);
        if(!(t.isePrimitivo())){
            throw new InvalidParameterException("ERRORE: il tipo passato non è primitivo");
        }else if(t.getIdClassDiagram()!=-1){
            throw new InvalidParameterException("ERRORE: il tipo passato appartiene ad uno specifico class diagram");
        }else if(t.getIdClasseDefinente()!=-1){
            throw  new InvalidParameterException("ERRORE: un tipo primitivo non può essere associato ad una classe");
        }else if(t.iseArray() && t.getDimensioneArray()<=0){
            throw new InvalidParameterException("ERRORE: un tipo array deve avere una dimensione positiva");
        }else{
            preparedStatement.setString(1, t.getNome());
            preparedStatement.setString(2, t.getRange());
            preparedStatement.setNull(3, Types.INTEGER);
            preparedStatement.setNull(4, Types.INTEGER);
            preparedStatement.setInt(5, t.getDimensioneArray());
            if(t.iseArray()) {
                preparedStatement.setInt(6, 1);
            }else{
                preparedStatement.setInt(6, 0);
            }
            preparedStatement.setInt(7, 1);
        }
        return preparedStatement.execute();
    }

    public Tipo readTipoById(int anId) throws SQLException{
        Tipo t;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_BY_ID);
        preparedStatement.setInt(1, anId);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        result.next();
        t = new Tipo(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4),result.getInt(5), result.getInt(6),(result.getInt(7)==1),(result.getInt(8)==1));
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return t;
    }
}
