package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import controller.MasterController;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "productos")
public class Producto  
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String codigo;
    private int orden;
    private boolean activo;
    private boolean eliminado;
    private boolean soyFavorito;
    private String descripcion;
    private String nombre;
    private String subtitulo;
    @ManyToOne() @JoinColumn(name = "fkCategoria") @JsonIgnore
    private Categoria categoria;
    @ManyToOne() @JoinColumn(name = "fkInstalacion") @JsonIgnore
    private Instalacion instalacion;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "producto")
    private List<FotoProducto> arrFotosProducto;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "producto")
    private List<OpcProducto> arrOpcProductos;
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "producto")
    private List<RelHashProducto> arrRelsHashs;
    
    @Transient public int fkCategoria;
    @Transient boolean estoyLlevando;
    @Transient double cantidad;
    @Transient double ultimoPrecioElegido;
    @Transient OpcProducto ultimaUnidadSeleccionada;
    @Transient boolean activarEdicion;
    
    @ManyToOne() @JoinColumn(name = "fkProveedor") @JsonIgnore
    private Proveedor proveedor;
    @Transient public int fkProveedor;
    
    
    //CONTRUCTOR VACIO:
    public Producto() 
    {
        arrFotosProducto = new ArrayList<>();
        arrOpcProductos = new ArrayList<>();
        arrRelsHashs = new ArrayList<>();
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public Producto(int orden, boolean activo,String descripcion,String nombre,String subtitulo,Categoria categoria,Instalacion instalacion, Proveedor proveedor)
    {
        this.orden = orden;
        this.activo = activo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.subtitulo = subtitulo;
        this.categoria = categoria;
        this.instalacion = instalacion;
        this.arrFotosProducto = new ArrayList<>();
        this.arrOpcProductos = new ArrayList<>();
        this.arrRelsHashs = new ArrayList<>();
        this.proveedor = proveedor;
    }
    
    //CONTRUCTOR PARAMETROS CON LISTAS:
    public Producto(int orden,boolean activo,String descripcion,String nombre,String subtitulo,Categoria categoria,Instalacion instalacion,List<FotoProducto> arrFotosProducto,List<OpcProducto> arrOpcProductos,List<RelHashProducto> arrRelsHashs, Proveedor proveedor)
    {
        this.orden = orden;
        this.activo = activo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.subtitulo = subtitulo;
        this.categoria = categoria;
        this.instalacion = instalacion;
        this.arrFotosProducto = new ArrayList<>();
        this.arrOpcProductos = new ArrayList<>();
        this.arrRelsHashs = new ArrayList<>();
        this.proveedor = proveedor;
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
    public String getDescripcion() 
    {
        return descripcion;
    }
    public String getNombre() 
    {
        String rta = "";
        
        if(nombre != null)
        {
            rta = nombre.toUpperCase();
        }
        
        return rta;
    }
    public String getSubtitulo() 
    {
        String rta = "";
        
        if(subtitulo != null)
        {
            rta = subtitulo.toUpperCase();
        }
        
        return rta;
    }
    public Categoria getCategoria() 
    {
        return categoria;
    }
    public Instalacion getInstalacion() 
    {
        return instalacion;
    }
    public List<FotoProducto> getArrFotosProducto() 
    {
        List<FotoProducto> arrAux = new ArrayList<>();
        
        for(FotoProducto fotoLoop : arrFotosProducto)
        {
            if(fotoLoop.getActivo())
            {
                arrAux.add(fotoLoop);
            }
        }
        
        arrFotosProducto =  arrAux;
        
//        if(arrFotosProducto == null || arrFotosProducto.size() == 0)
//        {
//            Foto fotoPrincipal = new Foto("empty.jpg", getInstalacion(), true);
//            FotoProducto fotoProducto = new FotoProducto(fotoPrincipal, this, 1, true);
//            arrFotosProducto.add(fotoProducto);
//        }
        
        if(arrFotosProducto != null)
        {
            Collections.sort(arrFotosProducto);
        }
        
        return arrFotosProducto;
    }
    public List<OpcProducto> getArrOpcProductos() 
    {
        List<OpcProducto> arrAux = new ArrayList<>();
        
        for(OpcProducto loop : arrOpcProductos)
        {
            if(loop.getActivo())
            {
                arrAux.add(loop);
            }
        }
        
        arrOpcProductos =  arrAux;
        
        Collections.sort(arrOpcProductos);
        
        
        return arrOpcProductos;
    }
    public List<RelHashProducto> getArrRelsHashs() 
    {
        List<RelHashProducto> arrAux = new ArrayList<>();
        
        for(RelHashProducto loop : arrRelsHashs)
        {
            if(loop.getActivo())
            {
                arrAux.add(loop);
            }
        }
        
        arrRelsHashs =  arrAux;
        
        Collections.sort(arrRelsHashs);
        
        return arrRelsHashs;
    }

    public boolean getEliminado()
    {
        return eliminado;
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
    public void setDescripcion( String descripcion ) 
    {
        this.descripcion = descripcion;
    }
    public void setNombre( String nombre ) 
    {
        this.nombre = nombre;
    }
    public void setSubtitulo( String subtitulo ) 
    {
        this.subtitulo = subtitulo;
    }
    public void setCategoria( Categoria categoria ) 
    {
        this.categoria = categoria;
    }
    public void setInstalacion( Instalacion instalacion ) 
    {
        this.instalacion = instalacion;
    }
    public void setArrFotosProducto( List<FotoProducto> arrFotosProducto ) 
    {
        this.arrFotosProducto = arrFotosProducto;
        for(FotoProducto fotoProductoLoop : arrFotosProducto)
        {
            fotoProductoLoop.setProducto(this);
            fotoProductoLoop.getFoto().setInstalacion(instalacion);
            fotoProductoLoop.setActivo(true);
        }
    }

    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }
    
   public boolean addFotoProducto(FotoProducto fotoProducto)
   {
       boolean agregue = false;
       
       fotoProducto.setProducto(this);
       this.arrFotosProducto.add(fotoProducto);
    
       return agregue;
   }
    public void setArrOpcProductos( List<OpcProducto> arrOpcProductos ) 
    {
        this.arrOpcProductos = arrOpcProductos;
        for(OpcProducto opcProductoLoop : arrOpcProductos)
        {
            if(opcProductoLoop != null)
            {
                if(opcProductoLoop.getValorFloat() < 1)
                {
                    opcProductoLoop.setValorFloat(1);
                }
            }
            opcProductoLoop.setProducto(this);
            opcProductoLoop.setActivo(true);
            
        }
    }
   public boolean addOpcProducto(OpcProducto opcProducto)
   {
       boolean agregue = false;
       
       opcProducto.setProducto(this);
       this.arrOpcProductos.add(opcProducto);
    
       return agregue;
   }
    public void setArrRelsHashs( List<RelHashProducto> arrRelsHashs ) 
    {
        this.arrRelsHashs = arrRelsHashs;
        for(RelHashProducto relHashProductoLoop : arrRelsHashs)
        {
            relHashProductoLoop.setProducto(this);
            relHashProductoLoop.setActivo(true);
        }
    }
   public boolean addRelHashProducto(RelHashProducto relHashProducto)
   {
       boolean agregue = false;
       
       relHashProducto.setProducto(this);
       this.arrRelsHashs.add(relHashProducto);
    
       return agregue;
   }
    public boolean isEstoyLlevando()   
    {
        return estoyLlevando;
    }

    public double getCantidad()
    {
        return MasterController.round(cantidad, 3);
    }

    public void setCantidad(double cantidad)
    {
        this.cantidad = cantidad;
    }

    public OpcProducto getUltimaUnidadSeleccionada()
    {
        return ultimaUnidadSeleccionada;
    }

    public void setUltimaUnidadSeleccionada(OpcProducto ultimaUnidadSeleccionada)
    {
        this.ultimaUnidadSeleccionada = ultimaUnidadSeleccionada;
    }

    public void setEliminado(boolean eliminado)
    {
        this.eliminado = eliminado;
    }
    
    

    //</editor-fold>
    public void setEstoyLlevando(boolean estoyLlevando)    
    {
        this.estoyLlevando = estoyLlevando;
    }

    public double getUltimoPrecioElegido()
    {
        return ultimoPrecioElegido;
    }

    public void setUltimoPrecioElegido(double ultimoPrecioElegido)
    {
        this.ultimoPrecioElegido = ultimoPrecioElegido;
    }

    public boolean isSoyFavorito()
    {
        return soyFavorito;
    }

    public void setSoyFavorito(boolean soyFavorito)
    {
        this.soyFavorito = soyFavorito;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    
    

    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "codigo:" + codigo + ", ";
        str += "activo:" + activo + ", ";
        str += "descripcion:" + descripcion + ", ";
        str += "nombre:" + nombre + ", ";
        str += "subtitulo:" + subtitulo + ", ";
        str += "categoria:" + categoria + ", ";
        str += "instalacion:" + instalacion + ", ";
        
        if( arrFotosProducto != null) 
        {
            str += "arrFotosProducto:" + arrFotosProducto.size() + ", ";
        }
        
        if( arrOpcProductos != null) 
        {
            str += "arrOpcProductos:" + arrOpcProductos.size() + ", ";
        }
        
        if( arrRelsHashs != null) 
        {
            str += "arrRelsHashs:" + arrRelsHashs.size() + ", ";
        }
        
        str += "}";
        
        return str;
    }
    public String toJson()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "codigo:" + codigo + ", ";
        str += "activo:" + activo + ", ";
        str += "descripcion:" + descripcion + ", ";
        str += "nombre:" + nombre + ", ";
        str += "subtitulo:" + subtitulo + ", ";
        str += "categoria:" + categoria + ", ";
        
        if( arrFotosProducto != null) 
        {
            str += "arrFotosProducto:" + arrFotosProducto.size() + ", ";
        }
        
        if( arrOpcProductos != null) 
        {
            str += "arrOpcProductos:" + arrOpcProductos.size() + ", ";
        }
        
        if( arrRelsHashs != null) 
        {
            str += "arrRelsHashs:" + arrRelsHashs.size() + ", ";
        }
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:

//    
//    public int compareTo(Producto otro)
//    {
//        int rta = 1;
//        int orden1 = -1;
//        int orden2 = -1;
//        try
//        {
//            if(this != null && otro != null)
//            {
//                orden1 = this.getOrden();
//                orden2 = otro.getOrden();
//                
//                if(orden1 != -1 && orden2 != -1)
//                {
//                    if(orden1 >= orden2)
//                    {
//                        rta =  1;
//                    }
//                    else
//                    {
//                        rta = -1;
//                    }
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("orden1: " + orden1);
//            System.out.println("orden2: " + orden2);
//            e.printStackTrace();
//        }
//        return rta;
//    }
    
    public Foto getFotoPrincipal()
    {
        Foto fotoPrincipal = null;
        
        
        
        // BUSCA SOLO LAS FOTOS ACTIVAS:
        List<FotoProducto> arrFotosAux = getArrFotosProducto();
        
        if(arrFotosAux != null)
        {
            if(arrFotosAux.size() > 0 )
            {
                Collections.sort(arrFotosAux);
                
                FotoProducto fotoAux = arrFotosAux.get(0);
                
                if(fotoAux != null)
                {
                    fotoPrincipal = fotoAux.getFoto();
                }
            }
        }
        
//        if(fotoPrincipal == null)
//        {
//            
//        }
        
        return fotoPrincipal;
    }
    
    public int getFkCategoria()
    {
        int fkCategoria = -1;
        
        if(categoria != null)
        {
            fkCategoria = categoria.getId();
        }
        
        return fkCategoria;
    }
    public String getNombreCategoria()
    {
        String nombreCategoria = "";
        
        if(categoria != null)
        {
            nombreCategoria = categoria.getNombre();
        }
        
        return nombreCategoria;
    }

    public String getMenorPrecio()
    {
        double menorPrecio = 99999999;
        
        List<OpcProducto> arr = getArrOpcProductos();
        if(arr != null)
        {
            if(arr.size() > 0)
            {
                OpcProducto opcMenorPrecio = arr.get(0);
                if(opcMenorPrecio != null)
                {
                    HashTag hash = opcMenorPrecio.getHashtag();
                    if(hash != null)
                    {
                        double valorFloat = opcMenorPrecio.getHashtag().getValorFloat();
                        menorPrecio = opcMenorPrecio.getPrecio() / valorFloat;
                    }
                }
            }
        }
        
        return String.valueOf(menorPrecio);
    }

    public boolean isActivarEdicion()
    {
        return activarEdicion;
    }
    
    public int getFKCategoria()
    {
        int fkCategoria = -1;
        
        if(categoria != null)
        {
            fkCategoria = categoria.getId();
        }
        
        return fkCategoria;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getFkProveedor()
    {
        int fkProveedor = -1;
        
        if(proveedor != null)
        {
            fkProveedor = proveedor.getId();
        }
        
        return fkProveedor;
    }
    
    public int getCodigoInt()
    {
        int codigo = 0;
        
        try
        {
            codigo = Integer.parseInt(this.codigo);
        }
        catch(Exception e)
        {
            
        }
        
        return codigo;
    }
}
