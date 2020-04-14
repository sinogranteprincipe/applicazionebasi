package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyOracleConnection {
    private static MyOracleConnection instance;
    private static Connection connection;
    private static String host;
    private static String port;
    private static String database;
    private static String username;
    private static String password;
    private static String url;


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


    public MyOracleConnection(String hostTextField, String usernameTextField, String passwordTextField, String portaTextField, String databaseTextField)throws SQLException{
            host = hostTextField;
            username = usernameTextField;
            password = passwordTextField;
            port = portaTextField;
            database = databaseTextField;

            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String tmp = "jdbc:oracle:thin:@"+host+":"+port+":"+database;
            this.url = tmp;
            System.out.println(tmp +"\n" +username + "\n" +password);
            this.connection = DriverManager.getConnection(url, username, password );

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
