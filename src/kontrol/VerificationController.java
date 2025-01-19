/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package kontrol;

import entitas.Koneksi;
import entitas.LvqEntity;
import entitas.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Uchiha Marlin
 */
public class VerificationController {
    
    Koneksi kon = new Koneksi();
    //User us = new User();
    LvqEntity le = new LvqEntity();
    
    private int inputdimension = 7; //jumlah parameter
    private double[][] inputvector;
    
    private double[]m;
    
    //Weight Matrix
    private static double[][]w = null;
    
    //Euclidean Distance
    private static double[]d = null;
    
    private int target;
    private int winningneuron;
    private int k;
    
    public void DefineParameters(){
        inputvector = new double[le.GetNumberofInput()][inputdimension];
        target = le.GetTarget();
        m = new double[le.GetNumberofCluster()*inputdimension];
        w = new double[le.GetNumberofCluster()][inputdimension];
        d = new double[le.GetNumberofCluster()];   
    
}
    public void SetDataUser(String [] s){
        le.SetNumberofInput(Integer.parseInt(s[0]));
        le.SetNumberofCluster(Integer.parseInt(s[1]));
        le.SetTarget(Integer.parseInt(s[2]));
    }
     
    public void SetInputVector(double[]x){
        for(int i=0;i<le.GetNumberofInput();i++){
        for(int j=0; j<inputdimension;j++){
            inputvector[i][j]=x[j];
            System.out.println("inputvector["+i+"]["+j+"]:"+inputvector[i][j]);
        }
        
        System.out.println("");
        System.out.println("target:"+target);
        }
    }
    
    public String GetTarget(String idname){
        String result = null;
        String sql = "select target From user where idname='" + idname + "'";
        try{
            Statement st = kon.GetConnection().createStatement();
            ResultSet rt = st.executeQuery(sql);
            rt.first();
            result = rt.getString("target");
            st.close();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Get Target Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        return result; 
    }
    
    public void SetWeights(double [][]m){
        for(int i=0;i<le.GetNumberofCluster();i++){
            for(int j=0;j<inputdimension;j++){
                w[i][j]=m[i][j];
                
                System.out.println("Weight["+i+"]["+j+"]:"+w[i][j]);  
            }
            System.out.println("");
        }
    }
    
    
   private double ComputeEuclideanDistance(double[]vector1, double []vector2){
        double result;
        double distance = 0;
        for(int j = 0; j<inputdimension; j++){
            distance+= Math.pow((vector1[j]-vector2[j]),2);
        }
        result = distance;
        return result;
    }
   
   public void MappingInputVector(){
        for(int k=0;k<le.GetNumberofInput();k++){
            winningneuron = 0;
            for(int i=0;i<le.GetNumberofCluster();i++){
                d[i] = ComputeEuclideanDistance(w[i],inputvector[k]);
                if(i!=0){
                    if(d[i]<d[winningneuron]){
                        winningneuron = i;
                    }
                }
                System.out.println("d["+i+"]:"+d[i]);  
               
            }
           
            if(target==winningneuron){
                    System.out.println("Input["+k+"]->Signature Accepted");    
            }
            else{
            System.out.println("Input["+k+"]->Signature Rejected");
            //System.out.println("Input["+k+"]->Cluster No:"+winningneuron);
           
            }
        
    }
         
  } 
    public String Verification(){
        if(target==winningneuron){
            return "Diterima";
        }
        else{
            return "Ditolak";
        }
        
    }
    
    public double[]GetWeights(String idname){
        int i=0;
        String sql = "select w  From lvq where idname='" + idname + "'";
        try{
            Statement st = kon.GetConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            m[i]=rs.getDouble("w");
            i++;
            }
            st.close();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Get Weights is Failed : " + ex.getMessage(),"Error Message", JOptionPane.ERROR_MESSAGE);
     
    }
               return m;

}
}