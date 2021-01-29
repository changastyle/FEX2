package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controller.MasterController;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "opcionesproductos")
public class OpcProducto implements Comparable<OpcProducto>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne() @JoinColumn(name = "fkHashtag")
    private HashTag hashtag;
    @OneToOne() @JoinColumn(name = "fkUnidad")
    private Unidad unidad;
    private double precio;
    private double valorFloat;
    @ManyToOne() @JoinColumn(name = "fkProducto") @JsonIgnore
    private Producto producto;
    private boolean activo;
    private double precioCosto;
    private double porcentajeGanancia;
    
    @Transient boolean activarEdicion;
    @Transient boolean tildado;
    @Transient double cantidad;
    
    
    //CONTRUCTOR VACIO:
    public OpcProducto() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public OpcProducto(boolean activo,Unidad unidad,double precio, double precioCosto , double porcentajeGanancia , double valorFloat,Producto producto)
    {
        this.activo = activo;
        this.unidad = unidad;
        this.precio = precio;
        this.precioCosto = precioCosto;
        this.porcentajeGanancia = porcentajeGanancia;
        this.valorFloat = valorFloat;
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

    public double getValorFloat()
    {
//        //// LUEGO DE MIGRAR SACAR ESTO:
//        if(valorFloat == 0)
//        {
//            if(hashtag != null)
//            {
//                valorFloat = hashtag.getValorFloat();
//            }
//        }
//        
//        //// HASTA ACA
        
        return valorFloat;
    }

    public Unidad getUnidad()
    {
        return unidad;
    }

    public void setUnidad(Unidad unidad)
    {
        this.unidad = unidad;
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
    public void setPrecio( double precio ) 
    {
        this.precio = precio;
    }
    public void setProducto( Producto producto ) 
    {
        this.producto = producto;
    }
    public void setValorFloat(double valorFloat)
    {
        this.valorFloat = valorFloat;
    }

    public void setPrecioCosto(double precioCosto) {
        this.precioCosto = precioCosto;
    }

    public void setPorcentajeGanancia(double porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }
    
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "hashtag:" + hashtag + ", ";
        str += "precio:" + precio + ", ";
        str += "valorFloat:" + valorFloat + ", ";
        str += "producto:" + producto + ", ";
        
        str += "}";
        
        return str;
    }

    public boolean getActivarEdicion()
    {
        return activarEdicion;
    }

    public void setActivarEdicion(boolean activarEdicion)
    {
        this.activarEdicion = activarEdicion;
    }

    public boolean isTildado()
    {
        if(getPorcentajeGanancia() > 0)
        {
            tildado = true;
        }
        return tildado;
    }

    public void setTildado(boolean tildado)
    {
        this.tildado = tildado;
    }

    
 
        
    
    //DYN:

    
    public int compareTo(OpcProducto otro)
    {
        int orden = 1;
        
        if(this.getPrecioMkt() > otro.getPrecioMkt())
        {
            orden = 1;
        }
        else
        {
            orden = -1;
        }
        
        return orden;
    }
 
    public String getNombreProductoPadre()
    {
        String nombreProductoPadre = "";
        
        if(producto != null)
        {
            nombreProductoPadre = producto.getNombre();
        }
        
        return nombreProductoPadre;
    }
    public int getFKProductoPadre()
    {
        int fkProductoPadre = -1;
        
        if(producto != null)
        {
            fkProductoPadre = producto.getId();
        }
        
        return fkProductoPadre;
    }
    public String getSubtituloProductoPadre()
    {
        String subtituloProductoPadre = "";
        
        if(producto != null)
        {
            subtituloProductoPadre = producto.getSubtitulo();
        }
        
        return subtituloProductoPadre;
    }
    public Foto getFotoProductoPadre()
    {
        Foto foto = null;
        
        if(producto != null)
        {
            foto = producto.getFotoPrincipal();
        }
        
        return foto;
    }
    public double getPrecioMkt()
    {
        double preciomkt = getPrecio();
        
        if(unidad != null)
        {
            // OPC PRODUCTO != UNIDAD:
            if(unidad.getId() != 5)
            {
                if(getPrecio() != 0 && valorFloat != 0)
                {
                    preciomkt = getPrecio() / valorFloat;
                }
                else
                {
                    preciomkt = 0;
                }
            }
        }
        
        
        
        return MasterController.round(preciomkt, 3);
    }
    public String getCalcularNombreMkt()
    {
        String nombreMKT = "";
        
        if(unidad != null)
        {
            String nombre = unidad.getApb();

            nombreMKT = nombre;

            // OPC PRODUCTO != UNIDAD:
//            !unidad.getNombrePos().equalsIgnoreCase("kg") 
            if( !unidad.getNombrePre().equalsIgnoreCase("kg")&& !unidad.getNombrePre().equalsIgnoreCase("unidades"))
            {
//                nombreMKT += ((int)valorFloat) +" cada " +  unidad.getNombreUnidadSingular();
                nombreMKT += " cada " + unidad.getNombreUnidadSingular();
//                nombreMKT += " x " + ((int)valorFloat) + " "  +;
//                    
//                if( nombre.toLowerCase().contains("kg") )
//                {
//                    nombreMKT +=" C/KG";
//                    
//                    if(nombre.toLowerCase().equals("kg"))
//                    {
//                        nombreMKT =" KG";
//                    }
//                }
//                else if(nombre.toLowerCase().contains("unidad"))
//                {
//                    nombreMKT += " C/U";
//                }
            }
        }
        
        
        return nombreMKT;
    }
    public String getCalcularNombre()
    {
        String nombreMKT = "";
        
        if(unidad != null)
        {
            nombreMKT = unidad.getApb();
            
            if(valorFloat != 1)
            {
                if(valorFloat % 1 == 0 )
                {
                    nombreMKT += " x " + ((int)valorFloat) + " " +  unidad.getNombreUnidadSingular();
                }
                else
                {
                    nombreMKT += " x " + valorFloat + " " + unidad.getNombreUnidadSingular();
                }
            }
        }
        
        
        return nombreMKT;
    }
    
    
    
    
    
    
    
    
    
    
    
    public double getPrecioCosto() {
        return precioCosto;
    }

    public double getPorcentajeGanancia() {
        return porcentajeGanancia;
    }
    public double getPrecio() 
    {
        double precioX = 0;
        
        if( precio <= 0)
        {
            precioX = precioCosto + (precioCosto * porcentajeGanancia )/100;
        }
        else
        {
            // PRECIO FIJO CLAVADO:
            precioX = precio;
        }
        return precioX;
    }
    public boolean soyPrecioAutomatico()
    {
        boolean si = false;
        
        if( precio <= 0)
        {
            si = true;
        }
        
        return si;
    }

    public double getCantidad()
    {
        return cantidad;
    }

    public void setCantidad(double cantidad)
    {
        this.cantidad = cantidad;
    }
    
}
