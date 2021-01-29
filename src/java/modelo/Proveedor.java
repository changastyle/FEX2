package modelo;

import controller.MasterController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "proveedores")
public class Proveedor implements Comparable<Proveedor>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String cuit;
    private String email;
    @ManyToOne() @JoinColumn(name = "fkInstalacion") @JsonIgnore
    private Instalacion instalacion;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "proveedor") @JsonIgnore
    private List<Producto> arrProductos;
    private int orden;
    private boolean activo;
    private boolean esDefault;
    
    
    //CONTRUCTOR VACIO:
    public Proveedor() 
    {
        arrProductos = new ArrayList<>();
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public Proveedor(String nombre,String cuit,String email,Instalacion instalacion,int orden,boolean activo)
    {
        this.nombre = nombre;
        this.cuit = cuit;
        this.email = email;
        this.instalacion = instalacion;
        this.arrProductos = new ArrayList<>();
        this.orden = orden;
        this.activo = activo;
    }
    
    //CONTRUCTOR PARAMETROS CON LISTAS:
    public Proveedor(String nombre,String cuit,String email,Instalacion instalacion,List<Producto> arrProductos,int orden,boolean activo)
    {
        this.nombre = nombre;
        this.cuit = cuit;
        this.email = email;
        this.instalacion = instalacion;
        this.arrProductos = new ArrayList<>();
        this.orden = orden;
        this.activo = activo;
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
    public String getCuit() 
    {
        return cuit;
    }
    public String getEmail() 
    {
        return email;
    }
    public Instalacion getInstalacion() 
    {
        return instalacion;
    }
    public List<Producto> getArrProductos() 
    {
        return arrProductos;
    }
    public int getOrden() 
    {
        return orden;
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
    public void setNombre( String nombre ) 
    {
        this.nombre = nombre;
    }
    public void setCuit( String cuit ) 
    {
        this.cuit = cuit;
    }
    public void setEmail( String email ) 
    {
        this.email = email;
    }
    public void setInstalacion( Instalacion instalacion ) 
    {
        this.instalacion = instalacion;
    }
    public void setArrProductos( List<Producto> arrProductos ) 
    {
        this.arrProductos = arrProductos;
        for(Producto productoLoop : arrProductos)
        {
            productoLoop.setProveedor(this);
        }
    }
   public boolean addProducto(Producto producto)
   {
       boolean agregue = false;
       
       producto.setProveedor(this);
       this.arrProductos.add(producto);
    
       return agregue;
   }
    public void setOrden( int orden ) 
    {
        this.orden = orden;
    }
    public void setActivo( boolean activo ) 
    {
        this.activo = activo;
    }
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{\n";
        str += "id:" + id + ", \n";
        str += "nombre:" + nombre + ", \n";
        str += "cuit:" + cuit + ", \n";
        str += "email:" + email + ", \n";
        str += "instalacion:" + instalacion + ", \n";
        
        if( arrProductos != null) 
        {
            str += "arrProductos:" + arrProductos.size() + ", \n";
        }
        str += "orden:" + orden + ", \n";
        str += "activo:" + activo + ", \n";
        
        str += "}\n";
        
        return str;
    }

    public String toJSON()
    {
        String json = "{";
        json += "id:" + id + ",";
        json += "nombre:" + nombre + ",";
        json += "cuit:" + cuit + ",";
        json += "email:" + email + ",";
        
        if( instalacion != null) 
        {
            json += "fkInstalacion:" + instalacion.getId() + ",";
        }
        
        if( arrProductos != null) 
        {
            json += "arrProductos:" + arrProductos.size() + ",";
        }
        json += "orden:" + orden + ",";
        json += "activo:" + activo + ",";
        
        json += "}";
        
        return json;
    }

 
        
    
    //DYN:

    
    public int compareTo(Proveedor otro)
    {
        return 1;
    }

    public boolean isEsDefault()
    {
        return esDefault;
    }

    public void setEsDefault(boolean esDefault)
    {
        this.esDefault = esDefault;
    }
    
}
