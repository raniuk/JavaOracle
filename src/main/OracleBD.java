package main;

import java.sql.*;

public class OracleBD {

    private Connection conexion;

    public OracleBD conectar() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String BaseDeDatos = "jdbc:oracle:thin:@localhost:1521:ESQUEMA";

            conexion = DriverManager.getConnection(BaseDeDatos, "USUARIO", "CONTRASEÑA");
            if (conexion != null) {
                System.out.println("Conexion exitosa!");
            } else {
                System.out.println("Conexion fallida!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public boolean ejecutar(String sql) {
        try {
            Statement sentencia;
            sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            sentencia.executeUpdate(sql);
            getConexion().commit();
            sentencia.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ResultSet consultar(String sql) {
        ResultSet resultado = null;
        try {
            Statement sentencia;
            sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            resultado = sentencia.executeQuery(sql);
            getConexion().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return resultado;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public static void main(String[] args) {
        OracleBD baseDatos = new OracleBD().conectar();
        if (baseDatos.ejecutar("INSERT INTO TEST(IDENTIFICADOR,DESCRIPCION) VALUES(4,'CUATRO')")) {
            System.out.println("Ejecucion correcta!");
        } else {
            System.out.println("Ocurrió un problema al ejecutar!");
        }
        ResultSet resultados = baseDatos.consultar("SELECT * FROM TEST");
        if (resultados != null) {
            try {
                System.out.println("IDENTIFICADOR       DESCRIPCION");
                System.out.println("--------------------------------");
                while (resultados.next()) {
                    System.out.println("" + resultados.getBigDecimal("IDENTIFICADOR") + "       " + resultados.getString("DESCRIPCION"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}