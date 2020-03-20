package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyOracleConnection {
    private static MyOracleConnection instance;
    private Connection connection;
    /*private String url ="jdbc:oracle:thin:@localhost:1521:XE";
    private String username ="sino";
    private String password = "progetto";
    */
    private String url ="jdbc:oracle:thin:@192.168.1.13:1521:XE";
    private String username ="system";
    private String password = "accro";

    private String host;
    private String port;
    private String database;
    //private String url = "jdbc:oracle:thin:@"+host+":"+"port"+":"+database;

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

/*
    private MyOracleConnection(String hostTextField, String usernameTextField, String passwordTextField, String portaTextField, String databaseTextField)throws SQLException{
        try{
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            //this.connection = DriverManager.getConnection(hostTextField, usernameTextField, passwordTextField, portaTextField, databaseTextField);
            //this.connection = DriverManager.getConnection(host username, password, port, database);
        }catch(Exception ex){
            System.out.println("Database Connection Creation Failed: " + ex.getMessage());
        }
    }

    public Connection getConnection(String hostTextField, String usernameTextField, String passwordTextField, String portaTextField, String databaseTextField) {
        this.username = usernameTextField;
        this.password = passwordTextField;
        this.host = hostTextField;
        this.port = portaTextField;
        this.database = databaseTextField;
        return connection;
    }
*/
    public static MyOracleConnection getInstance() throws SQLException{
        if(instance == null){
            instance = new MyOracleConnection();
        }else if(instance.getConnection().isClosed()){
            instance = new MyOracleConnection();
        }

        return instance;
    }
}
