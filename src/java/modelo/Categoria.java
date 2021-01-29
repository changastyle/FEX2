package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "categorias")
public class Categoria implements Comparable<Categoria>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean activo;
    private String nombre;
    private String icono;
    @OneToOne() @JoinColumn(name = "fkFoto")
    private Foto foto;
//    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "categoria")
//    private List<Producto> arrProductos;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "categoria")
    private List<RelHashCategoria> arrRelsByCategoria;
    
    
    //CONTRUCTOR VACIO:
    public Categoria() 
    {
//        arrProductos = new ArrayList<>();
        arrRelsByCategoria = new ArrayList<>();
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public Categoria(boolean activo,String nombre,Foto foto)
    {
        this.activo = activo;
        this.nombre = nombre;
        this.foto = foto;
//        this.arrProductos = new ArrayList<>();
        this.arrRelsByCategoria = new ArrayList<>();
    }
    
    //CONTRUCTOR PARAMETROS CON LISTAS:
    public Categoria(boolean activo,String nombre,Foto foto,List<Producto> arrProductos,List<RelHashCategoria> arrRelsByCategoria)
    {
        this.activo = activo;
        this.nombre = nombre;
        this.foto = foto;
//        this.arrProductos = new ArrayList<>();
        this.arrRelsByCategoria = new ArrayList<>();
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
    public Foto getFoto() 
    {
        return foto;
    }

    public String getIcono()
    {
        return icono;
    }
    
//    public List<Producto> getArrProductos() 
//    {
//        return arrProductos;
//    }
    public List<RelHashCategoria> getArrRelsByCategoria() 
    {
        return arrRelsByCategoria;
    }
    public List<RelHashCategoria> getArrRelsByCategoriaActivos() 
    {
        List<RelHashCategoria> arrAux = new ArrayList<>();
        for(RelHashCategoria relLoop : arrRelsByCategoria)
        {
            HashTag hashLoop = relLoop.getHashtag();
            if(hashLoop != null)
            {
                if(relLoop.getActivo() && hashLoop.getEsDeUnidad())
                {
                    arrAux.add(relLoop);
                }
            }
        }
        return arrAux;
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
    public void setFoto( Foto foto ) 
    {
        this.foto = foto;
    }

    public void setIcono(String icono)
    {
        this.icono = icono;
    }
    
//    public void setArrProductos( List<Producto> arrProductos ) 
//    {
//        this.arrProductos = arrProductos;
//        for(Producto productoLoop : arrProductos)
//        {
//            productoLoop.setCategoria(this);
//        }
//    }
//   public boolean addProducto(Producto producto)
//   {
//       boolean agregue = false;
//       
//       producto.setCategoria(this);
//       this.arrProductos.add(producto);
//    
//       return agregue;
//   }
    public void setArrRelsByCategoria( List<RelHashCategoria> arrRelsByCategoria ) 
    {
        this.arrRelsByCategoria = arrRelsByCategoria;
        for(RelHashCategoria relHashCategoriaLoop : arrRelsByCategoria)
        {
            relHashCategoriaLoop.setCategoria(this);
        }
    }
   public boolean addRelHashCategoria(RelHashCategoria relHashCategoria)
   {
       boolean agregue = false;
       
       relHashCategoria.setCategoria(this);
       this.arrRelsByCategoria.add(relHashCategoria);
    
       return agregue;
   }
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "nombre:" + nombre + ", ";
        str += "foto:" + foto + ", ";
        
//        if( arrProductos != null) 
//        {
//            str += "arrProductos:" + arrProductos.size() + ", ";
//        }
        
        if( arrRelsByCategoria != null) 
        {
            str += "arrRelsByCategoria:" + arrRelsByCategoria.size() + ", ";
        }
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:
    public int compareTo(Categoria otro)
    {
        return this.nombre.compareTo(otro.nombre);
    }

    public String getNombreParaMostar()
    {
        String nombreParaMostrar = nombre;
//        String nombreParaMostrar = nombre + " - " + arrProductos.size() + " productos";
        
        
        
        return nombreParaMostrar;
    }
}
