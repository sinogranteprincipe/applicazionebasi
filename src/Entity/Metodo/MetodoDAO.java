package Entity.Metodo;

import Entity.Attributo.Attributo;
import Entity.Classe.Classe;
import Entity.MyOracleConnection;
import Entity.TipoDiVisibilita;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MetodoDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_CLASSE ="SELECT \"ID_METODO\" , \"NOME\" ,\"HA_PARAMETRI\" ,\"ID_TIPO_RITORNO\" , \"VISIBILITA\" , \"ID_CLASSE\" , \"POSIZIONE\"  FROM \"METODO\" WHERE \"ID_CLASSE\" = ?";
    private static final String CREATE_METHOD = "INSERT INTO METODO( \"NOME\", \"HA_PARAMETRI\", \"ID_TIPO_RITORNO\", \"VISIBILITA\", \"ID_CLASSE\", \"POSIZIONE\") VALUES (?,?,?,?,?,?)";
    private static final String GET_LAST_INSERTED = "SELECT \"ID_METODO\" , \"NOME\" ,\"HA_PARAMETRI\" ,\"ID_TIPO_RITORNO\" , \"VISIBILITA\" , \"ID_CLASSE\" , \"POSIZIONE\"  FROM \"METODO\" WHERE \"ID_METODO\" = (SELECT MAX(\"ID_METODO\") FROM \"METODO\")";

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
        methods.sort(new Comparator<Metodo>() {
            @Override
            public int compare(Metodo metodo, Metodo t1) {
                if(metodo.getPosizione()>t1.getPosizione()){
                    return 1;
                }else if(metodo.getPosizione()<t1.getPosizione()){
                    return -1;
                }
                return 0;
            }
        });
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
        if(m.isHaParametri())
             preparedStatement.setInt(2, 1);
        else
            preparedStatement.setInt(2, 0);

        preparedStatement.setInt(3,m.getIdTipoDiRitorno());
        preparedStatement.setString(4,m.getVisibilita().name());
        preparedStatement.setInt(5,m.getIdClasse());
        preparedStatement.setInt(6,m.getPosizione());
        preparedStatement.execute();
        return true;
    }

    public Metodo readLastInserted() throws SQLException{
        Metodo m;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(GET_LAST_INSERTED);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

            m = new Metodo(result.getInt(1), result.getString(2), (result.getInt(3)==1),result.getInt(4), TipoDiVisibilita.getTipoDiVisibilitaByName(result.getString(5)), result.getInt(6), result.getInt(7));

        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return m;
    }

}
