/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entitas;

/**
 *
 * @author unsri-ict
 */
public class LvqEntity {
    private int numberofinput;
    private int numberofcluster;//jumlah target = 20
    private int target;
 
  
    public LvqEntity() {
 
    }
    
    public LvqEntity(int numberofinput, int numberofcluster, int target){
       this.numberofinput = numberofinput;
       this.numberofcluster = numberofcluster;
       this.target = target;   
    }
    
    public int GetNumberofInput(){
        return numberofinput;
    }
    
    public void SetNumberofInput(int numberofinput){
        this.numberofinput = numberofinput;
    }
    
    public int GetNumberofCluster(){
        return numberofcluster;
    }
    
    public void SetNumberofCluster(int numberofcluster){
        this.numberofcluster = numberofcluster;
    }
    
    public int GetTarget(){
        return target;
    }
    
    public void SetTarget(int target){
        this.target = target;
    }
   
}
