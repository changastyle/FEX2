package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "fotoproductos")
public class FotoProducto implements Comparable<FotoProducto>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne() @JoinColumn(name = "fkFoto")
    private Foto foto;
    @OneToOne() @JoinColumn(name = "fkProducto") @JsonIgnore
    private Producto producto;
    
    private int orden;
    private boolean activo;
    
    
    //CONTRUCTOR VACIO:
    public FotoProducto() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public FotoProducto(Foto foto,Producto producto , int orden , boolean activo)
    {
        this.foto = foto;
        this.producto = producto;
        this.activo = activo;
    }


    //<editor-fold desc="GETTERS Y SETTERS:">
    public int getId() 
    {
        return id;
    }
    public Foto getFoto() 
    {
        return foto;
    }
    public Producto getProducto() 
    {
        return producto;
    }

    //SET
    public void setId( int id ) 
    {
        this.id = id;
    }
    public void setFoto( Foto foto ) 
    {
        this.foto = foto;
    }
    public void setProducto( Producto producto ) 
    {
        this.producto = producto;
    }

    public int getOrden()
    {
        return orden;
    }

    public void setOrden(int orden)
    {
        this.orden = orden;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "foto:" + foto + ", ";
        str += "orden:" + orden + ", ";
        str += "producto:" + producto + ", ";
        str += "activo:" + activo + ", ";
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:

    
    public int compareTo(FotoProducto otro)
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
    
    public int getFkProducto()
    {
        int fkProducto = -1;
        
        if(producto != null)
        {
            fkProducto = producto.getId();
        }
        
        return fkProducto;
    }
    
}
