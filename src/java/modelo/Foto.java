package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controller.MasterController;
import java.io.File;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "fotos")
public class Foto implements Comparable<Foto>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String urlProvisoria;
    @ManyToOne() @JoinColumn(name = "fkInstalacion") @JsonIgnore
    private Instalacion instalacion;
    private boolean activo;
    
    
    //CONTRUCTOR VACIO:
    public Foto() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:

    public Foto(String urlProvisoria, Instalacion instalacion, boolean activo)
    {
        this.urlProvisoria = urlProvisoria;
        this.instalacion = instalacion;
        this.activo = activo;
    }
    

    //<editor-fold desc="GETTERS Y SETTERS:">
    public int getId() 
    {
        return id;
    }
    public String getUrlProvisoria() 
    {
        return urlProvisoria;
    }
    public boolean getActivo() 
    {
        return activo;
    }

    //SET
    public void setId( int id ) 
    {
        this.id = id;
    }
    public void setUrlProvisoria( String urlProvisoria ) 
    {
        this.urlProvisoria = urlProvisoria;
    }
    public void setActivo( boolean activo ) 
    {
        this.activo = activo;
    }

    public void setInstalacion(Instalacion instalacion)
    {
        this.instalacion = instalacion;
    }
    
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "urlProvisoria:" + urlProvisoria + ", ";
        str += "activo:" + activo + ", ";
        
        str += "}";
        
        return str;
    }
     public String toJSON() 
    {
        String fullFoto = getFullFoto();
        String json = "{";
        
        json += "\"id\":" + id + ",";
        json += "\"urlProvisoria\":\"" + urlProvisoria + "\",";
        
        if(fullFoto != null && fullFoto.length() > 0)
        {
            json += "\"fullFoto\":\"" + getFullFoto() + "\"";
        }
        json += "}";
        
        return json;
    }

 
        
    
    //DYN:

    public String getFullFoto()
    {
        
        String urlFull = MasterController.dameConfigMaster().getUrlVisualizacion();
        
        String carpetaWebInstalacion = instalacion.getCarpetaWeb();
        
        urlFull += "/" + carpetaWebInstalacion + "/" + urlProvisoria;
        
        return urlFull;
    }
    
    public int compareTo(Foto otro)
    {
        return 1;
    }
}
