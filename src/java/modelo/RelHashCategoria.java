package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "relshashcategoria")
public class RelHashCategoria implements Comparable<RelHashCategoria>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean activo;
    @OneToOne() @JoinColumn(name = "fkCategoria") @JsonIgnore
    private Categoria categoria;
    @OneToOne() @JoinColumn(name = "fkHashtag")
    private HashTag hashtag;
    
    
    //CONTRUCTOR VACIO:
    public RelHashCategoria() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public RelHashCategoria(boolean activo,Categoria categoria,HashTag hashtag)
    {
        this.activo = activo;
        this.categoria = categoria;
        this.hashtag = hashtag;
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
    public Categoria getCategoria() 
    {
        return categoria;
    }
    public HashTag getHashtag() 
    {
        return hashtag;
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
    public void setCategoria( Categoria categoria ) 
    {
        this.categoria = categoria;
    }
    public void setHashtag( HashTag hashtag ) 
    {
        this.hashtag = hashtag;
    }
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "categoria:" + categoria + ", ";
        str += "hashtag:" + hashtag + ", ";
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:

    
    public int compareTo(RelHashCategoria otro)
    {
        return 1;
    }
    
}
