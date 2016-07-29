package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.driver.OracleDriver;

public class ConnectionOrcl {

    //Usuario de la base de datos
    private final String USUARIO = "hr";
    //Contraseña del usuario de la base de datos
    private final String PASS = "hr";
    //SID de la base de datos, este lo registramos en la instalacion
    private final String SID = "xe";
    //Host donde se encuentra la base de datos, para nuesto caso como es local
    //se indica que esta en localhost
    private final String HOST = "localhost";
    //El puerto 1521 es el estandar para este tipo de instalaciones a menos que
    //se indicque lo contrario
    private final int PUERTO = 1521;
    //Objeto donde se almacenara nuestra conexion
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    /*
     * Instanciamos un objeto de tipo OracleDriver para regitrarlo y posterior uso
     * este objeto lo provee el driver que agregamos al principio
     */
    public void registrarDriver() throws SQLException {
        OracleDriver oracleDriver = new oracle.jdbc.driver.OracleDriver();
        DriverManager.registerDriver(oracleDriver);
    }

    /*
     * Procedemos a realizar nuestra conexion a la base datos, para esto nos
     * aseguramos que el objeto este null o que este cerrada la conexion.
     * 
     * cadanaConexion: es un string que se contruye a partir de los atributos
     * definidos.
     * 
     * Finalmente obtenemos la conexion.  El metodo "getConnection"
     * lanza una excepcion la cual propagamos "throws SQLException".
     */
    public void conectar() throws SQLException {
        //System.out.println(connection);
        if (connection == null || connection.isClosed() == true) {
            String cadenaConexion = "jdbc:oracle:thin:@" + HOST + ":" + PUERTO + ":" + SID;
            registrarDriver();
            connection = DriverManager.getConnection(cadenaConexion, USUARIO, PASS);
        }
    }

    /*
     * Con este metodo cerramos la conexion una vez hayamos terminado de usar la
     * base de datos
     */
    public void cerrar() throws SQLException {
        if (connection != null && connection.isClosed() == false) {
            connection.close();
        }
    }

    /*
     * Main para comprobar que funciona, aqui hacemos un select a una tabla del
     * sistema para obtener la version.
     */
    public static void main(String args[]) {

        ConnectionOrcl conexionOracle = new ConnectionOrcl();
        try {
            conexionOracle.conectar();
            Connection conn = conexionOracle.getConnection();
            // driver@machineName:port:SID           ,  userid,  password
            Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery("select BANNER from SYS.V_$VERSION");
            while (rset.next()) {
                System.out.println(rset.getString(1));   // Print col 1
            }
            stmt.close();
            conexionOracle.cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionOrcl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}