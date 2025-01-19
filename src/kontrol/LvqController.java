/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kontrol;
import entitas.Koneksi;
import entitas.User;
import entitas.LvqEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author unsri-ict
 */

public class LvqController {
    
    Koneksi kon = new Koneksi();
    //User us = new User();
    LvqEntity le = new LvqEntity();
    
    
    private int inputdimension = 7; //jumlah parameter
    private double[][] inputvector;
    
    private int maxepoh = 100; //Maksimum 
    private double alpha = 0.9; //Learning Rate
    private double decalpha = 0.96; //penurunan Learning Rate
    
    //Weight Matrix
    private  double[][]w = null;
    
    //Euclidean Distance
    private  double[]d = null;
    private int target;
    private int winningneuron;
    private int k;
    
    public void DefineParameters(){
        inputvector = new double[le.GetNumberofInput()][inputdimension];
        target = le.GetTarget();
        w = new double[le.GetNumberofCluster()][inputdimension];
        d = new double[le.GetNumberofCluster()];      
    }
    
    public void DefineInput(double [][]x){
        for(int i=0; i<le.GetNumberofInput();i++){
            for(int j=0; j<inputdimension;j++){
                inputvector[i][j]=x[i][j];
                //System.out.println("InputVector["+i+"]["+j+"]:"+inputvector[i][j]);
            }
            //System.out.println("");
        }
    }
      
    private double RandomNumberGenerator(){
        java.util.Random rnd = new java.util.Random();
        return rnd.nextDouble();
    }
    
    private double LearningRateDecay(double currentlearningrate){
        
        double result = 0;
        result = decalpha*currentlearningrate;
        return result;
    }
   
   public void InitializeWeight(){//bobot diLVQ -> jumlah baris = jumlah kelas dan jumlah kolom = jumlah paramater     
       for(int i=0; i<le.GetNumberofCluster(); i++){
            for(int j=0; j<inputdimension; j++){
                w[i][j] = RandomNumberGenerator();
                //System.out.println("weight["+i+"]["+j+"]:"+w[i][j]);
            }
            System.out.println("");
        }
      
    }
   
    public void SetDataLvq(String[]s){
        le.SetNumberofInput(Integer.parseInt(s[0]));
        le.SetTarget(Integer.parseInt(s[1]));
        le.SetNumberofCluster(Integer.parseInt(s[2]));
    }
    
   private double ComputeEuclideanDistance(double[]vector1, double []vector2){
        double result;
        double distance = 0;
        for(int j = 0; j<inputdimension; j++){
            distance+= Math.pow((vector1[j]-vector2[j]),2);
        }
        result = distance;
        return Math.sqrt(result);
    }
    
   public void TrainLVQ(){
        System.out.println("Training LVQ");
        for(int epoh=0;epoh<maxepoh;epoh++){
            for(int k=0;k<le.GetNumberofInput();k++){
                winningneuron = 0;
                for(int i=0;i<le.GetNumberofCluster();i++){
                    d[i] = ComputeEuclideanDistance(w[i], inputvector[k]);
                    if(i!=0){
                        if(d[i]<d[winningneuron]){
                            winningneuron = i;
                        }   
                    }
                    System.out.println("euclideandistance["+i+"]:"+d[i]);
                    System.out.println("");
                }
          
        if(target==winningneuron){
                //System.out.println("target:"+target+" =="+winningneuron+"");
                    for(int i=0;i<inputdimension;i++){
                        w[winningneuron][i] = w[winningneuron][i] + alpha*(inputvector[k][i]-w[winningneuron][i]);
                        //System.out.println("weight["+winningneuron+"]["+i+"]:"+w[winningneuron][i]);
                        }
                    }
                    else{
                //System.out.println("target:"+target+"!="+winningneuron+"");
                     for(int i=0;i<inputdimension;i++){
                        w[winningneuron][i] = w[winningneuron][i] - alpha*(inputvector[k][i]-w[winningneuron][i]);
                       // System.out.println("weight["+winningneuron+"]["+i+"]:"+w[winningneuron][i]);
                     }
                }
                //System.out.println("winner:"+winningneuron);
                //System.out.println("");
                //update the winning neuron
            }
            alpha = LearningRateDecay(alpha);
            //System.out.print(".");
            //System.out.println("Learn Rate:"+alpha);
            //System.out.println("");   
        } 
       
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
                //if(d[i]==winningneuron){
                //System.out.println("d["+i+"]:"+d[i]);       
            }
        //} 
            if(target==winningneuron){
                    System.out.println("Input["+k+"]->Diterima");    
            }
            else{
            System.out.println("Input["+k+"]->Ditolak");
            //System.out.println("Input["+k+"]->Cluster No:"+winningneuron);
           
            }
            }
  } 
  
    public void RunLVQ(){
        //DefineParameters();
        InitializeWeight();
        TrainLVQ();
	//MappingInputVector();
        
    }
     
    public DefaultTableModel ShowTableUser() {
          DefaultTableModel TbModel;
          
          String sql = "select * from user";
          String ColName[] = {"Id Nama", "Nama", "Target"};
          TbModel = new DefaultTableModel(ColName,0);
          try{
              Statement st = kon.GetConnection().createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    TbModel.addRow(new Object[]{
                        rs.getString("idname"),
                        rs.getString("name"),
                        rs.getString("target")});
                };
                st.close();
              }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Ambil Data User Gagal: " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
                
              }
          return TbModel;
            } 
    
    public void InsertDataBobot(String idname, double w){
        int hasil = 0;
        String sql = "Insert Into lvq(idname,w) values ('" + idname + "','" + w + "')";
        try{
            Statement st = kon.GetConnection().createStatement();
            hasil = st.executeUpdate(sql);
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, "Insert Data Lvq Gagal: " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void SaveDataBobot(String idname){
        DeleteBobot(idname);
        for(int i=0; i<le.GetNumberofCluster(); i++){
            for(int j=0; j<inputdimension; j++){
                InsertDataBobot(idname,w[i][j]);
            }
            System.out.println("");
        }
    }
    
    public boolean DeleteBobot(String idname){
        int hasil = 0;
        String sql = "Delete From lvq where idname='" + idname + "'";
        try{
            Statement st = kon.GetConnection().createStatement();
            hasil = st.executeUpdate(sql);
        }catch (SQLException ex){
            //System.out.println("Insert User Failed " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Delete Data Lvq Gagal : " + ex.getMessage(),"Pesan Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
public static void main(String[] args){
		LvqController lvq = new LvqController();
		lvq.RunLVQ();
	}

  }
