/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2018;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author oracle
 */
public class Examen2018 {

    /**
     * @param args the command line arguments
     */
    public static Connection conexion = null;

    public static Connection getConexion() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;

        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }

    public static void main(String[] args) throws FileNotFoundException, SQLException, IOException {
        File txtLect = new File("/home/oracle/Desktop/ProbaExer4/analisis.txt");
        FileInputStream acceso = new FileInputStream(txtLect);
        BufferedInputStream buffer = new BufferedInputStream(acceso);
        DataInputStream cargador = new DataInputStream(buffer);
        String texto;
        Connection conn = getConexion();
        while ((texto = cargador.readLine()) != null) {
            String[] separador = texto.split(",");
            String num=separador[0];
            String textoAcidez;
            String numeUva="(select * from uvas where tipo='"+separador[4]+"')";
            int total=(15*Integer.parseInt(separador[5]));
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(numeUva);
            rs.next();
            String nomeu = rs.getString("nomeu");
            int acidezmin = rs.getInt("acidezmin");
            int acidezmax = rs.getInt("acidezmax");
            if(Integer.parseInt(separador[1])<=acidezmin)
                   textoAcidez="subir acidez";
            else if(Integer.parseInt(separador[1])>=acidezmax)
                textoAcidez="bajar acidez";
            else
                textoAcidez="equilibrado";
            
            String sql = "insert into xerado (NUM,NOMEUVA,TRATACIDEZ,TOTAL) VALUES('"+num+"','"+nomeu+"','"+textoAcidez+"',"+total+")";
            ResultSet rsssss = st.executeQuery(sql);
            
            
        }
        conexion.close();
        

    }

}
