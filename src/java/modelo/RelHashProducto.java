package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "relshashproducto")
public class RelHashProducto implements Comparable<RelHashProducto>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean activo;
    @OneToOne() @JoinColumn(name = "fkHashtag")
    private HashTag hashtag;
    @OneToOne() @JoinColumn(name = "fkProducto")
    private Producto producto;
    
    
    //CONTRUCTOR VACIO:
    public RelHashProducto() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public RelHashProducto(boolean activo,HashTag hashtag,Producto producto)
    {
        this.activo = activo;
        this.hashtag = hashtag;
        this.producto = producto;
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
    public HashTag getHashtag() 
    {
        return hashtag;
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
    public void setActivo( boolean activo ) 
    {
        this.activo = activo;
    }
    public void setHashtag( HashTag hashtag ) 
    {
        this.hashtag = hashtag;
    }
    public void setProducto( Producto producto ) 
    {
        this.producto = producto;
    }
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "hashtag:" + hashtag + ", ";
        str += "producto:" + producto + ", ";
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:

    
    public int compareTo(RelHashProducto otro)
    {
        return 1;
    }
    
}
