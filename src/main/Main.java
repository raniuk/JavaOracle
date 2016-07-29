package main;
import java.sql.*;
import java.util.Calendar;

public class Main{
	//Connection con = null;
	ResultSet rs = null;
	Statement cmd = null;

	public void listData(){
		ConnectionMySql con= new ConnectionMySql();
		try {
		    //con = DriverManager.getConnection("jdbc:mysql://localhost/guia?" + "user=root&password=mysql");
		    // Otros y operaciones sobre la base de datos...
		    cmd = con.getConnection().createStatement();
		    rs = cmd.executeQuery("SELECT nombre, telefono, fecha FROM personal");
		    while (rs.next()) {
		        String nombre = rs.getString(1);
		        int telefono = rs.getInt(2);
		        String fecha = rs.getString(3);
		        System.out.println(nombre + " - " + telefono + " - " + fecha);
		    }

		    rs.close();
		    con.desconectar();
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		}
	}
	
	public void insertData(String nombre, int telefono, Date fecha){
		ConnectionMySql con= new ConnectionMySql();
		try {
		    cmd = con.getConnection().createStatement();
		    cmd.executeUpdate("INSERT INTO personal (nombre, telefono, fecha) VALUES ('"+nombre+"', '"
		      +telefono+"', '"+fecha+"')");
		    cmd.close();
		    con.desconectar();
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		}
	}
	
	public void deleteData(int ids){
		ConnectionMySql con= new ConnectionMySql();
		try {
//		    cmd = con.getConnection().createStatement();
//		    cmd.executeUpdate("DELETE personal WHERE id="+ids);
//		    cmd.close();
			String query = "delete from personal where id = ?";
			PreparedStatement deleteQ = con.getConnection().prepareStatement(query);
			deleteQ.setInt(1, ids);
			deleteQ.execute();
		    con.desconectar();
		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Main pri = new Main();
		Calendar calendar = Calendar.getInstance();
	    java.sql.Date fecha = new java.sql.Date(calendar.getTime().getTime());

		//pri.insertData("Juan Mendez",21727273,fecha);
		pri.listData();
		pri.deleteData(6);
		pri.listData();
	}

}