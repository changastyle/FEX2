package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "relscategoriasinstalacion")
public class RelCategoriaInstalacion implements Comparable<RelCategoriaInstalacion>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean activo;
    @OneToOne() @JoinColumn(name = "fkCategoria")
    private Categoria categoria;
    @ManyToOne() @JoinColumn(name = "fkInstalacion") @JsonIgnore
    private Instalacion instalacion;
    
    
    //CONTRUCTOR VACIO:
    public RelCategoriaInstalacion() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public RelCategoriaInstalacion(boolean activo,Categoria categoria,Instalacion instalacion)
    {
        this.activo = activo;
        this.categoria = categoria;
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
    public Categoria getCategoria() 
    {
        return categoria;
    }
    public Instalacion getInstalacion() 
    {
        return instalacion;
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
    public void setInstalacion( Instalacion instalacion ) 
    {
        this.instalacion = instalacion;
    }
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "categoria:" + categoria + ", ";
        str += "instalacion:" + instalacion + ", ";
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:

    
    public int compareTo(RelCategoriaInstalacion otro)
    {
        return 1;
    }
    
}
