package Entity.ClassDiagramRiferimento;

import Entity.ClassDiagram.ClassDiagram;
import Entity.Metodo.Metodo;
import Entity.MyOracleConnection;
import Entity.TipoDiVisibilita;
import Entity.Package.Package;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassDiagramRiferimentoDAO {
    Connection sharedDatabase;

    private static final String READ_ALL_CLASS_DIAGRAM_IN_PACKAGE = "SELECT \"ID_CLASS_DIAGRAM\" FROM \"CD_DI_RIFERIMENTO\" WHERE \"ID_PACKAGE\"=?";
    private static final String READ_ALL_PACKAGES_OF_A_CLASS_DIAGRAM = "SELECT \"ID_PACKAGE\" FROM \"CD_DI_RIFERIMENTO\" WHERE \"ID_CLASS_DIAGRAM\"=?";
    private static final String ADD_CLASS_DIAGRAM_TO_PACKAGE = "INSERT INTO \"CD_DI_RIFERIMENTO\"(\"ID_CLASS_DIAGRAM\", \"ID_PACKAGE\") VALUES (?,?)";

    public ClassDiagramRiferimentoDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Integer> readAllDiagramsOfAPackage(Package pkg) throws SQLException{
        List<Integer> l = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_CLASS_DIAGRAM_IN_PACKAGE);
        preparedStatement.setInt(1, pkg.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            l.add(result.getInt(1));
        }
        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return l;
    }

    public List<Integer> readAllPackagesOfAClassDiagram(ClassDiagram cd) throws SQLException{
        List<Integer> l = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_PACKAGES_OF_A_CLASS_DIAGRAM);
        preparedStatement.setInt(1, cd.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            l.add(result.getInt(1));
        }
        if(result!=null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return l;
    }

    public boolean addClassDiagramToPackage(ClassDiagramRiferimento cdrif) throws SQLException {
        PreparedStatement preparedStatement;
        preparedStatement = sharedDatabase.prepareStatement(ADD_CLASS_DIAGRAM_TO_PACKAGE);
        preparedStatement.setInt(1,cdrif.getIdClassDiagram());
        preparedStatement.setInt(2,cdrif.getIdPackage());
        boolean result = preparedStatement.execute();
        if(preparedStatement!= null){
            preparedStatement.close();
        }
        return result;
    }
}
