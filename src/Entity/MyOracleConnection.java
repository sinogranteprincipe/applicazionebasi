package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyOracleConnection {
    private static MyOracleConnection instance;
    private Connection connection;
    private String url ="jdbc:oracle:thin:@LAPTOP-KDJK5080:1521:XE";
    private String username ="sino";
    private String password = "progetto";

    private MyOracleConnection()throws SQLException{
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            this.connection = DriverManager.getConnection(url, username, password);
        }catch(Exception ex){
            System.out.println("Database Connection Creation Failed: " + ex.getMessage());
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public static MyOracleConnection getInstance() throws SQLException{
        if(instance == null){
            instance = new MyOracleConnection();
        }else if(instance.getConnection().isClosed()){
            instance = new MyOracleConnection();
        }

        return instance;
    }
}
