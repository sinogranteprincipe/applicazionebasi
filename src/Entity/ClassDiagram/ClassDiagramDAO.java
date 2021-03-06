package Entity.ClassDiagram;

import Entity.MyOracleConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.util.Map;

import Entity.Package.Package;

public class ClassDiagramDAO  {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_PACKAGE ="SELECT DISTINCT \"CLASS_DIAGRAM\".\"ID_CLASS_DIAGRAM\", \"CLASS_DIAGRAM\".\"NOME\", \"CLASS_DIAGRAM\".\"COMMENTO\" FROM (\"CLASS_DIAGRAM\" INNER JOIN \"CD_DI_RIFERIMENTO\" ON  \"CD_DI_RIFERIMENTO\".\"ID_PACKAGE\" = ?)";
    private static final String CREATE_DATABASE ="INSERT INTO \"CLASS_DIAGRAM\"(\"NOME\", \"COMMENTO\") VALUES (?,?)";
    private static final String GET_LAST_INSERTED_ID="SELECT \"ID_CLASS_DIAGRAM\" FROM \"CLASS_DIAGRAM\" WHERE \"ID_CLASS_DIAGRAM\" = (SELECT MAX(\"ID_CLASS_DIAGRAM\") FROM \"CLASS_DIAGRAM\")";
    private static final String READ_ALL_BY_NAME = "SELECT \"CLASS_DIAGRAM\".\"ID_CLASS_DIAGRAM\", \"CLASS_DIAGRAM\".\"NOME\", \"CLASS_DIAGRAM\".\"COMMENTO\" FROM \"CLASS_DIAGRAM\" WHERE \"CLASS_DIAGRAM\".\"NOME\" = ?";
    private static final String READ_ALL_NOT_IN_PACKAGE = "SELECT DISTINCT \"CLASS_DIAGRAM\".\"ID_CLASS_DIAGRAM\", \"CLASS_DIAGRAM\".\"NOME\", \"CLASS_DIAGRAM\".\"COMMENTO\" FROM (\"CLASS_DIAGRAM\" INNER JOIN \"CD_DI_RIFERIMENTO\" ON  \"CD_DI_RIFERIMENTO\".\"ID_PACKAGE\" <> ?)";
    private static final String READ_BY_ID = "SELECT \"CLASS_DIAGRAM\".\"ID_CLASS_DIAGRAM\", \"CLASS_DIAGRAM\".\"NOME\", \"CLASS_DIAGRAM\".\"COMMENTO\" FROM \"CLASS_DIAGRAM\" WHERE \"CLASS_DIAGRAM\".\"ID_CLASS_DIAGRAM\" = ?";


    public ClassDiagramDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }
    public List<ClassDiagram> readAllInPackage(Package pkg) throws SQLException {
        List<ClassDiagram> classdiagrams = new ArrayList<>();
        ClassDiagram c;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_PACKAGE);
        preparedStatement.setInt(1,pkg.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            c = new ClassDiagram(result.getInt(1), result.getString(2), result.getString(3));
            classdiagrams.add(c);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return classdiagrams;
    }


    public List<ClassDiagram> readAllNotInPackage(Package p) throws SQLException {
        List<ClassDiagram> classdiagrams = new ArrayList<>();
        ClassDiagram c;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_NOT_IN_PACKAGE);
        preparedStatement.setInt(1,p.getId());
        preparedStatement.execute();
        result = preparedStatement.getResultSet();

        while(result.next()){
            c = new ClassDiagram(result.getInt(1), result.getString(2), result.getString(3));
            classdiagrams.add(c);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return classdiagrams;
    }

    public boolean createClassDiagram(ClassDiagram cd) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement= sharedDatabase.prepareStatement(CREATE_DATABASE);
        preparedStatement.setString(1, cd.getNome());
        preparedStatement.setString(2,cd.getCommento());
        boolean res =  preparedStatement.execute();
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return res;
    }

    public List<ClassDiagram> readByName(String aName) throws SQLException {


        List<ClassDiagram> classdiagrams = new ArrayList<>();
        ClassDiagram c;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_BY_NAME);
        preparedStatement.setString(1, aName);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            c = new ClassDiagram(result.getInt(1), result.getString(2), result.getString(3));
            classdiagrams.add(c);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return classdiagrams;
    }

    public int getLastInsertedId() throws SQLException{
        PreparedStatement preparedStatement = null;
        preparedStatement= sharedDatabase.prepareStatement(GET_LAST_INSERTED_ID);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();
        resultSet.next();
        int i = resultSet.getInt(1);
        if(resultSet!= null) {
            resultSet.close();
        }
        if(preparedStatement!=null){
            preparedStatement.close();
        }

        return i;
    }

    public ClassDiagram readById(int idClassDiagram) throws SQLException {
        ClassDiagram c;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_BY_ID);
        preparedStatement.setInt(1,idClassDiagram);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        result.next();
            c = new ClassDiagram(result.getInt(1), result.getString(2), result.getString(3));
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return c;
    }
}
