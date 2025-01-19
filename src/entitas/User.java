/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entitas;


/**
 *
 * @author unsri-ict
 */
public class User {
    
    private String idname;
    private String name;
    private String target;
    
    public User(){
        
    }
    
    public User (String idname, String name, String target){
        this.idname = idname;
        this.name = name;
        this.target = target;
    }
    
    public String GetIdName(){
        return idname;
    }
    
    public void SetIdName(String idname){
        this.idname = idname;
    }
    
    public String GetName(){
        return name;
    }
    
    public void SetName(String name){
        this.name = name;
    }
    
    public String GetTarget(){
        return target;
    }
    
    public void SetTarget(String target){
        this.target = target;
    }
    
}


