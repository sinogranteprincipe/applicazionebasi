package Entity.GerarchiaClassi;

import Entity.Classe.Classe;
import Entity.MyOracleConnection;
import Entity.RuoloAssociazione;
import Entity.TipoDiClasse;
import Entity.TipoDiVisibilita;

import java.security.InvalidParameterException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GerarchiaClassiDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_FOR_CLASSE="SELECT \"ID_CLASSE_BASE\",\"ID_SUPERCLASSE\", \"ID_SOTTOCLASSE\" FROM \"GERARCHIA_CLASSI\"WHERE \"ID_CLASSE_BASE\" = ?";
    private static final String ADD_GERARCHIA_CLASSI ="INSERT INTO \"GERARCHIA_CLASSI\"(\"ID_CLASSE_BASE\",\"ID_SUPERCLASSE\",\"ID_SOTTOCLASSE\")VALUES(?,?,?)";

    public GerarchiaClassiDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }
    public List<GerarchiaClassi> readAllForClasse(Classe c) throws SQLException {
        List<GerarchiaClassi> classes = new ArrayList<>();
        GerarchiaClassi gc;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_FOR_CLASSE);
        preparedStatement.setInt(1, c.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            gc = new GerarchiaClassi(result.getInt(1),result.getInt(2),result.getInt(3));
            classes.add(gc);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return classes;
    }

    public boolean addGerarchiaClassi(Classe classeBase, Classe superclasse, Classe sottoclasse) throws InvalidParameterException, SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = sharedDatabase.prepareStatement(ADD_GERARCHIA_CLASSI);
        if(classeBase == null) {
            throw new InvalidParameterException("ERRORE: classe base deve essere not null");
        }else if(superclasse == null && sottoclasse == null){
            throw new InvalidParameterException("ERRORE: inserisci almeno una superclasse o una sottoclasse");
        }else if(superclasse.equals(sottoclasse)){
            throw new InvalidParameterException("ERRORE: una classe non può contemporaneamente estendere o essere estesa");
        }else{
            preparedStatement.setInt(1,classeBase.getId());

            if(superclasse==null){
                preparedStatement.setNull(2, Types.INTEGER);
            }else{
                System.out.println(superclasse.getId());
                preparedStatement.setInt(2, superclasse.getId());
            }
            if(sottoclasse==null){
                preparedStatement.setNull(3, Types.INTEGER);
            }else{
                System.out.println(sottoclasse.getId());
                preparedStatement.setInt(3, sottoclasse.getId());
            }
        }
        return preparedStatement.execute();
    }
}
