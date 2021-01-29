package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Categoria;
import modelo.FotoProducto;
import modelo.Instalacion;
import modelo.OpcProducto;
import modelo.Proveedor;
import modelo.RelHashProducto;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-29T20:36:00")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile SingularAttribute<Producto, String> codigo;
    public static volatile SingularAttribute<Producto, String> subtitulo;
    public static volatile SingularAttribute<Producto, Categoria> categoria;
    public static volatile ListAttribute<Producto, RelHashProducto> arrRelsHashs;
    public static volatile SingularAttribute<Producto, Boolean> soyFavorito;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile SingularAttribute<Producto, Boolean> eliminado;
    public static volatile ListAttribute<Producto, OpcProducto> arrOpcProductos;
    public static volatile SingularAttribute<Producto, Instalacion> instalacion;
    public static volatile SingularAttribute<Producto, Proveedor> proveedor;
    public static volatile SingularAttribute<Producto, Integer> id;
    public static volatile SingularAttribute<Producto, Integer> orden;
    public static volatile ListAttribute<Producto, FotoProducto> arrFotosProducto;
    public static volatile SingularAttribute<Producto, Boolean> activo;

}