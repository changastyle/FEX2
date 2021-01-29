package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "zonasenvios")
public class ZonaEnvio implements Comparable<ZonaEnvio>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean activo;
    private String nombre;
    private int distanciaMin;
    private int distanciaMax;
    private double precioEnvio;
    private double minimoDeCompra;
    private String texto;
    @OneToOne() @JoinColumn(name = "fkInstalacion") @JsonIgnore
    private Instalacion instalacion;
    private boolean porDefault;
    
    private int orden;
    @Transient public boolean selected;
    
    
    //CONTRUCTOR VACIO:
    public ZonaEnvio() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public ZonaEnvio(boolean activo,String nombre,double precioEnvio, double minimoDeCompra,String texto,Instalacion instalacion)
    {
        this.activo = activo;
        this.nombre = nombre;
        this.precioEnvio = precioEnvio;
        this.minimoDeCompra = minimoDeCompra;
        this.texto = texto;
        this.instalacion = instalacion;
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
    public String getNombre() 
    {
        return nombre;
    }
    public double getPrecioEnvio() 
    {
        return precioEnvio;
    }
    public String getTexto() 
    {
        return texto;
    }
    public Instalacion getInstalacion() 
    {
        return instalacion;
    }

    public double getMinimoDeCompra()
    {
        return minimoDeCompra;
    }

    public int getDistanciaMin()
    {
        return distanciaMin;
    }

    public void setDistanciaMin(int distanciaMin)
    {
        this.distanciaMin = distanciaMin;
    }

    public int getDistanciaMax()
    {
        return distanciaMax;
    }

    public void setDistanciaMax(int distanciaMax)
    {
        this.distanciaMax = distanciaMax;
    }

    public boolean isPorDefault()
    {
        return porDefault;
    }

    public void setPorDefault(boolean porDefault)
    {
        this.porDefault = porDefault;
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
    public void setNombre( String nombre ) 
    {
        this.nombre = nombre;
    }
    public void setPrecioEnvio( double precioEnvio ) 
    {
        this.precioEnvio = precioEnvio;
    }
    public void setTexto( String texto ) 
    {
        this.texto = texto;
    }
    public void setInstalacion( Instalacion instalacion ) 
    {
        this.instalacion = instalacion;
    }

    public void setMinimoDeCompra(double minimoDeCompra)
    {
        this.minimoDeCompra = minimoDeCompra;
    }

    public int getOrden()
    {
        return orden;
    }

    public void setOrden(int orden)
    {
        this.orden = orden;
    }
    
    
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "nombre:" + nombre + ", ";
        str += "precioEnvio:" + precioEnvio + ", ";
        str += "minimoDeCompra:" + minimoDeCompra + ", ";
        str += "texto:" + texto + ", ";
        str += "instalacion:" + instalacion + ", ";
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:

    
    public int compareTo(ZonaEnvio otro)
    {
        if(this.orden > otro.getOrden())
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
}
