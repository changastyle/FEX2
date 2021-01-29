package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "hashs")
public class HashTag implements Comparable<HashTag>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String nombreUnidadAnt;
    private String urlAwesom;
    private double valorFloat;
    private double aumento;
    private double multiplicadorUnidadAnt;
    private double minimo;
    private boolean esDeColor;
    private boolean esDeUnidad;
    private boolean activo;
    
    
    //CONTRUCTOR VACIO:
    public HashTag() 
    {
    }

    public HashTag(String nombre, String nombreUnidadAnt, double valorFloat, double aumento, double minimo, boolean esDeUnidad, boolean activo)
    {
        this.nombre = nombre;
        this.nombreUnidadAnt = nombreUnidadAnt;
        this.valorFloat = valorFloat;
        this.aumento = aumento;
        this.minimo = minimo;
        this.esDeUnidad = esDeUnidad;
        this.activo = activo;
    }
    
    


    //<editor-fold desc="GETTERS Y SETTERS:">
    public int getId() 
    {
        return id;
    }
    public boolean getActivo() 
    {
        return activo;
    }
    public boolean getEsDeColor() 
    {
        return esDeColor;
    }
    public boolean getEsDeUnidad() 
    {
        return esDeUnidad;
    }
    public String getNombre() 
    {
        return nombre;
    }
    public String getUrlAwesom() 
    {
        return urlAwesom;
    }
    public double getValorFloat() 
    {
        return valorFloat;
    }

    //SET
    public void setId( int id ) 
    {
        this.id = id;
    }
    public void setActivo( boolean activo ) 
    {
        this.activo = activo;
    }
    public void setEsDeColor( boolean esDeColor ) 
    {
        this.esDeColor = esDeColor;
    }
    public void setEsDeUnidad( boolean esDeUnidad ) 
    {
        this.esDeUnidad = esDeUnidad;
    }
    public void setNombre( String nombre ) 
    {
        this.nombre = nombre;
    }
    public void setUrlAwesom( String urlAwesom ) 
    {
        this.urlAwesom = urlAwesom;
    }
    public void setValorFloat( double valorFloat ) 
    {
        this.valorFloat = valorFloat;
    }

    public double getAumento()
    {
        return aumento;
    }

    public void setAumento(double aumento)
    {
        this.aumento = aumento;
    }

    public double getMinimo()
    {
        return minimo;
    }

    public void setMinimo(double minimo)
    {
        this.minimo = minimo;
    }
    public String getNombreUnidadAnt()
    {
        return nombreUnidadAnt;
    }

    public void setNombreUnidadAnt(String nombreUnidadAnt)
    {
        this.nombreUnidadAnt = nombreUnidadAnt;
    }

    public double getMultiplicadorUnidadAnt()
    {
        return multiplicadorUnidadAnt;
    }

    //</editor-fold>
    public void setMultiplicadorUnidadAnt(double multiplicadorUnidadAnt)    
    {
        this.multiplicadorUnidadAnt = multiplicadorUnidadAnt;
    }

    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "esDeColor:" + esDeColor + ", ";
        str += "esDeUnidad:" + esDeUnidad + ", ";
        str += "nombre:" + nombre + ", ";
        str += "urlAwesom:" + urlAwesom + ", ";
        str += "valorFloat:" + valorFloat + ", ";
        str += "aumento:" + aumento + ", ";
        str += "minimo:" + minimo + ", ";
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:

    
    public int compareTo(HashTag otro)
    {
        return nombre.compareTo(otro.nombre);
    }
    
}
