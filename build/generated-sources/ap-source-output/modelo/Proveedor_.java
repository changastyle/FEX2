package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Instalacion;
import modelo.Producto;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-29T20:36:00")
@StaticMetamodel(Proveedor.class)
public class Proveedor_ { 

    public static volatile SingularAttribute<Proveedor, Boolean> esDefault;
    public static volatile SingularAttribute<Proveedor, String> cuit;
    public static volatile SingularAttribute<Proveedor, Instalacion> instalacion;
    public static volatile SingularAttribute<Proveedor, Integer> id;
    public static volatile SingularAttribute<Proveedor, Integer> orden;
    public static volatile SingularAttribute<Proveedor, String> nombre;
    public static volatile SingularAttribute<Proveedor, String> email;
    public static volatile ListAttribute<Proveedor, Producto> arrProductos;
    public static volatile SingularAttribute<Proveedor, Boolean> activo;

}