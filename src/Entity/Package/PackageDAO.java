package Entity.Package;

import Entity.MyOracleConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PackageDAO {
        Connection sharedDatabase;

        private static final String READ_ALL ="SELECT \"ID_PACKAGE\",\"NOME\", \"COMMENTO\" FROM \"PACKAGE\"";

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
}
