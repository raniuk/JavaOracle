package main;
import java.sql.*;

public class ConnectionMySql {
   static String bd = "guia";
   static String user = "root";
   static String password = "mysql";
   static String url = "jdbc:mysql://localhost/"+bd;
 
   Connection connection = null;
 
   /** Constructor de Connection */
   public ConnectionMySql() {
      try{
         Class.forName("com.mysql.jdbc.Driver");//obtenemos el driver para mysql
         //Class.forName("org.gjt.mm.mysql.Driver");//obtenemos el driver para mysql prepare
         connection = DriverManager.getConnection(url,user,password);//obtenemos la conexión
         if (connection!=null){
            System.out.println("Conexión a base de datos "+bd+" OK\n");
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }
   }
   /**Permite retornar la conexión*/
   public Connection getConnection(){
      return connection;
   }
 
   public void desconectar(){
      connection = null;
   }
}