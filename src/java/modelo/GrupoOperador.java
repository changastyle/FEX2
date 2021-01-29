package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "grupooperador")
public class GrupoOperador implements Comparable<GrupoOperador>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int jerarquia;
    
    
    //CONTRUCTOR VACIO:
    public GrupoOperador() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public GrupoOperador(String nombre,int jerarquia)
    {
        this.nombre = nombre;
        this.jerarquia = jerarquia;
    }


    //<editor-fold desc="GETTERS Y SETTERS:">
    public int getId() 
    {
        return id;
    }
    public String getNombre() 
    {
        return nombre;
    }
    public int getJerarquia() 
    {
        return jerarquia;
    }

    //SET
    public void setId( int id ) 
    {
        this.id = id;
    }
    public void setNombre( String nombre ) 
    {
        this.nombre = nombre;
    }
    public void setJerarquia( int jerarquia ) 
    {
        this.jerarquia = jerarquia;
    }
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{\n";
        str += "id:" + id + ", \n";
        str += "nombre:" + nombre + ", \n";
        str += "jerarquia:" + jerarquia + ", \n";
        
        str += "}\n";
        
        return str;
    }

    public String toJSON()
    {
        String json = "{";
        
        json += "\"id\":" + id + ",";
        json += "\"nombre\":\"" + nombre + "\",";
        json += "\"jerarquia\":\"" + jerarquia + "\"";
        
        json += "}";
        
        return json;
    }

 
        
    
    //DYN:

    
    public int compareTo(GrupoOperador otro)
    {
        return 1;
    }
    
}
