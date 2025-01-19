/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entitas;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author unsri-ict
 */
public class Koneksi {
    
    private Connection conn;
    public Connection GetConnection() throws SQLException{
        if(conn==null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/skripsi","root","");
                //System.out.println("Connection Success");
            }catch(ClassNotFoundException ex){
                //System.out.println("Connection Error:\n"+ex.getMessage());
                JOptionPane.showMessageDialog(null, "Koneksi Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
            }catch(SQLException ex){
                //System.out.println("Connection Error:\n"+ex.getMessage());
                JOptionPane.showMessageDialog(null, "Koneksi Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
            
        }
        }
       
        return conn;
    }            

public static void main(String[]args) throws SQLException{
    Koneksi k=new Koneksi();
    k.GetConnection();
}
}