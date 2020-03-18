package Entity.Package;

import Entity.MyOracleConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {
    Connection sharedDatabase;

    private static final String READ_ALL ="SELECT \"ID_PACKAGE\",\"NOME\", \"COMMENTO\" FROM \"PACKAGE\"";
    private static final String CREATE_PACKAGE="INSERT INTO \"PACKAGE\"(\"NOME\", \"COMMENTO\") VALUES(?,?)";
    private static final String GET_PACKAGE_BY_NAME = "SELECT \"ID_PACKAGE\",\"NOME\", \"COMMENTO\" FROM \"PACKAGE\" WHERE \"NOME\"=?";
    private static final String GET_PACKAGE_BY_ID = "SELECT \"ID_PACKAGE\",\"NOME\", \"COMMENTO\" FROM \"PACKAGE\" WHERE \"ID_PACKAGE\"=?";

    public PackageDAO() throws SQLException {
        sharedDatabase = MyOracleConnection.getInstance().getConnection();
    }

    public List<Package> readAll() throws SQLException{
        List<Package> packages = new ArrayList<>();
        Package p;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(READ_ALL);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        while(result.next()){
            p = new Package(result.getInt(1), result.getString(2), result.getString(3));
            packages.add(p);
        }
        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }
        return packages;
    }

    public Package readPackageByName(String name) throws SQLException{
        Package p;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(GET_PACKAGE_BY_NAME);
        preparedStatement.setString(1, name);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        result.next();
        p = new Package(result.getInt(1), result.getString(2), result.getString(3));

        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }

        return p;
    }


    public Package readPackageById(int id) throws SQLException{
        Package p;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        preparedStatement = sharedDatabase.prepareStatement(GET_PACKAGE_BY_ID);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        result = preparedStatement.getResultSet();
        result.next();
        p = new Package(result.getInt(1), result.getString(2), result.getString(3));

        if(result != null){
            result.close();
        }
        if(preparedStatement != null){
            preparedStatement.close();
        }

        return p;
    }


    public boolean createPackage(Package p) throws SQLException {
            PreparedStatement preparedStatement = null;
            preparedStatement = sharedDatabase.prepareStatement(CREATE_PACKAGE);
            preparedStatement.setString(1, p.getNome());
            if(p.getCommento() != null)
                preparedStatement.setString(2, p.getCommento());
            else
                preparedStatement.setNull(2, Types.VARCHAR);
            return preparedStatement.execute();
        }
}
