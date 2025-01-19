/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entitas;

/**
 *
 * @author unsri-ict
 */
public class GmiEntity {
    
    private int idciri;
    private double m1;
    private double m2;
    private double m3;    
    private double m4;
    private double m5;
    private double m6;
    private double m7;
    
    public GmiEntity(){
        
    }
    
    public GmiEntity(int idciri, double m1, double m2, double m3, double m4, double m5, double m6, double m7){
        this.idciri = idciri;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.m4 = m4;
        this.m5 = m5;
        this.m6 = m6;
        this.m7 = m7;
    }
    
    public int GetIdCiri(){
        return idciri;
    }
    
    public void SetIdCiri(int idciri){
        this.idciri = idciri;
    }
    
    public double GetM1(){
        return m1;
    }
    
    public void SetM1(double m1){
        this.m1 = m1;
    }
    
    public double GetM2(){
        return m2;
    }
    
    public void SetM2(double m2 ){
        this.m2 = m2;
    }
    
    public double GetM3(){
        return m3;
    }
    
    public void SetM3(double m3){
        this.m3 = m3;
    }
    
    public double GetM4(){
        return m4;
    }
    
    public void SetM4(double m4){
        this.m4 = m4;
    }
    
    public double GetM5(){
        return m5;
    }
    
    public void SetM5(double m5){
        this.m5 = m5;
    }
    
    public double GetM6(){
        return m6;
    }
    
    public void SetM6(double m6){
        this.m6 = m6;
    }

    public double GetM7(){
        return m7;
    }
    
    public void SetM7(double m7){
        this.m7 = m7;
    }


    
}
