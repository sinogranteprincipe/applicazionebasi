package Entity.ClassDiagram;

import Entity.MyOracleConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

public class ClassDiagramDAO  {
    Connection sharedDatabase;

    private static final String READ_ALL_IN_PACKAGE ="SELECT \"CLASS_DIAGRAM.ID_CLASS_DIAGRAM\", \"CLASS_DIAGRAM.NOME\", \"CLASS_DIAGRAM.COMMENTO\" FROM \"CD_DI_RIFERIMENTO\" INNER JOIN \"CLASS_DIAGRAM\" ON \"CD_DI_RIFERIMENTO.ID_PACKAGE\" = ?";

    public ClassDiagramDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }
    public List<ClassDiagram> readAllInPackage(int aPackageId) throws SQLException {
        List<ClassDiagram> classdiagrams = new ArrayList<>();
        ClassDiagram c;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL_IN_PACKAGE);
        preparedStatement.setInt(1,aPackageId);
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
}
