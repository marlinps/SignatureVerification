/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kontrol;
import entitas.Koneksi;
import entitas.User;
import entitas.GmiEntity;
import java.sql.Statement;
import java.sql.SQLException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author unsri-ict
 */
public class GmiController {
    
    Koneksi kon = new Koneksi();
    User us = new User();
    GmiEntity ge = new GmiEntity();  
    
    private File file;
    private BufferedImage image;
    private int[][]pixels;
    
    private int m, n=0;
    private double m00, m01, m10;
    private double x0, y0;// x0= xbar, y0= ybar
    private double mu00, mu02, mu03, mu11, mu12, mu20, mu21, mu30;
    private double n02, n03, n11, n12, n20, n21, n30;
    
    public void setFile(File file){
        
        this.file = file;
        try{
        pixels = getAllPixelsValue(file);
         
    }catch(Exception ex){
        
        ex.printStackTrace();
        
    }
    }
    
    private int[][]getAllPixelsValue(File file){
        int[][] pixels = null;
        try{
            image = ImageIO.read(file);
            pixels = new int[image.getWidth()][];
            
            for(int x = 0;x<image.getWidth();x++){
               pixels[x] = new int[image.getHeight()];
               for(int y = 0;y<image.getHeight();y++){
                   pixels[x][y]=(int)(image.getRGB(x, y)==0xFFFFFFFF?0 : 1);
                   //System.out.println("pixels["+x+"]["+y+"]:"+pixels[x][y]);   
               }
               //System.out.println("\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return pixels;
    }
    
    private void getPixels(File file) 
    {
        try{
        image = ImageIO.read(file);
        m = image.getHeight();
        n = image.getWidth();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    private double MomenCitra(File file, int [][]pixel, int p, int q ){
        int x,y;
        double mpq = 0.0;
        getPixels(file);
        
        
        for(x = 0; x<n; x++){
           for(y = 0; y<m; y++){
               mpq+=Math.pow(x,p)*Math.pow(y,q)*pixel[x][y];
           }
        }
        return mpq;
    }
    
    private void  hitungMomenCitra(){
        
        m00 = MomenCitra(file, pixels, 0, 0);
        
        m10 = MomenCitra(file, pixels, 1, 0);
        
        m01 = MomenCitra(file, pixels, 0, 1);
    }
    
    private double MomenPusat(File file, int [][]pixel, double x0, double y0, int p, int q){
        int x,y;
        double mupq = 0.0;
        getPixels(file);
        for(x = 0; x<n; x++){
            for(y = 0; y<m; y++){
                mupq+=Math.pow(x-x0,p)*Math.pow(y-y0,q)*pixel[x][y];
            }
        }
        return mupq;
    }
    
    private void hitungMomenPusat(){
        hitungMomenCitra();
        
        x0=m10/m00;
        y0=m01/m00;
       
        mu00 = MomenPusat(file,pixels, x0, y0, 0, 0);
        mu02 = MomenPusat(file,pixels, x0, y0, 0, 2);
        mu03 = MomenPusat(file,pixels, x0, y0, 0, 3);
        mu11 = MomenPusat(file,pixels, x0, y0, 1, 1);
        mu12 = MomenPusat(file,pixels, x0, y0, 1, 2);
        mu20 = MomenPusat(file,pixels, x0, y0, 2, 0);
        mu21 = MomenPusat(file,pixels, x0, y0, 2, 1);
        mu30 = MomenPusat(file,pixels, x0, y0, 3, 0);
        
    }
    
    private void MomenPusatNormalisasi(){
        double gamma;
        hitungMomenPusat();
        
        gamma = ((0+2)/2)+1;
        n02 = mu02/Math.pow(mu00, gamma);
        
        gamma = ((0+3)/2)+1;
        n03 = mu03/Math.pow(mu00, gamma);
        
        gamma = ((1+1)/2)+1;
        n11 = mu11/Math.pow(mu00, gamma);
        
        gamma = ((1+2)/2)+1;
        n12 = mu12/Math.pow(mu00, gamma);
        
        gamma = ((2+0)/2)+1;
        n20 = mu20/Math.pow(mu00, gamma);
        
        gamma = ((2+1)/2)+1;
        n21 = mu21/Math.pow(mu00, gamma);
        
        gamma = ((3+0)/2)+1;
        n30 = mu30/Math.pow(mu00, gamma);
    }
    
    private double getmax(double[]x,int n){
       double maksimum=0.0;
       
       for(int i=0;i<n;i++){
           if(x[i]>maksimum){
               maksimum=x[i];
           }
       }
       return maksimum;
   }
    
    public double[]MomentInvarint(){
        MomenPusatNormalisasi();
        double[]M = new double[7];
        M[0] = (n20+n02);
        M[1] = Math.pow((n20-n02),2) + 4*(Math.pow(n11,2));
        M[2] = Math.pow(n30-(3*(n12)),2)+Math.pow((3*n21-n03),2);
        M[3] = Math.pow((n30+n12),2)+ Math.pow((n21+n03),2);
        M[4] =(n30-3*n12)*(n30+n12)*(Math.pow((n30+n12),2)- 3*Math.pow((n21+n03),2))+(3*n21-n03)*(n21+n03)*(3*Math.pow((n30+n12),2)-Math.pow((n21+n03),2));
        M[5] =(n20-n02)*(Math.pow((n30+n12),2)-Math.pow((n21+n03),2))+4*n11*(n30+n12)*(n21+n03);
        M[6] =(3*n21-n03)*(n30+n12)*(Math.pow((n30+n12),2)-3*Math.pow((n21+n03),2))+(3*n12-n30)*(n21+n03)*(3*Math.pow((n30+n12),2)-Math.pow((n21+n03),2)); 
        double max = getmax(M,7);
        for(int i=0;i<7;i++){
           M[i]=M[i]/max;
           System.out.println("M["+i+"]:"+M[i]++ );
        }
           System.out.println("\n");

        return M;
    }
    
    public void SetDataUser(String [] s){
        us.SetIdName(s[0]);
        us.SetName(s[1]); 
        us.SetTarget(s[2]);
    }
    
    public void InsertDataUser(){
        int hasil = 0;
        String sql = "Insert Into user(idname, name, target) values ('" + us.GetIdName() + "' , '" + us.GetName() + "'  , '" + us.GetTarget() + "')";
        try{
            Statement st = kon.GetConnection().createStatement();
            hasil = st.executeUpdate(sql);
        }catch (SQLException ex){
            //System.out.println("Insert User Failed " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Insert User Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void SetTarget(String target){
        us.SetTarget(target);   
    }
    
    public int UpdateTarget(String idname){
        int result = 0;
        String sql = "Update user set target='"+us.GetTarget()+"'where idname='"+idname+"'";
        try{
            Statement st = kon.GetConnection().createStatement();
            result= st.executeUpdate(sql);
            st.close();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Update Data Target Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }
    
    public void SetMomentInvariant(double[]M){
        ge.SetIdCiri(m);
        ge.SetM1(M[0]);
        ge.SetM2(M[1]);
        ge.SetM3(M[2]);
        ge.SetM4(M[3]);
        ge.SetM5(M[4]);
        ge.SetM6(M[5]);
        ge.SetM7(M[6]);
        
    }
    
      public void InsertMomentInvariant(String idname){
          int result = 0;
          String sql = "Insert Into momentinvariant(idname, m1, m2, m3, m4, m5, m6, m7 ) values ("
                + "'" + idname + "',"
                + "'" + ge.GetM1() + "',"
                + "'" + ge.GetM2() + "',"
                + "'" + ge.GetM3() + "',"
                + "'" + ge.GetM4() + "',"
                + "'" + ge.GetM5() + "',"
                + "'" + ge.GetM6() + "',"  
                + "'" + ge.GetM7() + "')";
        try{
            Statement st = kon.GetConnection().createStatement();
            result = st.executeUpdate(sql);
        }catch (SQLException ex){
            //System.out.println("Insert Moment Invariant Failed " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Insert Geometric Moment Invariant Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        
    }
      
      // public DefaultTableModel ShowTableFeatures(String sql) {
      public DefaultTableModel ShowTableFeatures(String sql) {    
      DefaultTableModel TbModel;
          String idname = null;
          //String sql ="select * from momentinvariant";
          String ColName[] = {"Id Name", "M1", "M2", "M3", "M4", "M5", "M6", "M7"};
          TbModel = new DefaultTableModel(ColName,0);
          try{
              Statement st = kon.GetConnection().createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    TbModel.addRow(new Object[]{
                        rs.getString("idname"),
                        rs.getString("m1"),
                        rs.getString("m2"),
                        rs.getString("m3"),
                        rs.getString("m4"),
                        rs.getString("m5"),
                        rs.getString("m6"),
                        rs.getString("m7")});
                };
                st.close();
              }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Data Ekstraksi Fitur Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
                
              }
          return TbModel;
            } 
       
       /*public static void main(String[] args){
		MomInvariantController mi = new MomInvariantController();
		mi.MomentInvarint();
	}*/
         
}
          
      
   

