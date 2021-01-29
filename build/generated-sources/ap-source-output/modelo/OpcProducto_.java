package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HashTag;
import modelo.Producto;
import modelo.Unidad;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-29T20:36:00")
@StaticMetamodel(OpcProducto.class)
public class OpcProducto_ { 

    public static volatile SingularAttribute<OpcProducto, Unidad> unidad;
    public static volatile SingularAttribute<OpcProducto, Double> precio;
    public static volatile SingularAttribute<OpcProducto, Double> valorFloat;
    public static volatile SingularAttribute<OpcProducto, Integer> id;
    public static volatile SingularAttribute<OpcProducto, Producto> producto;
    public static volatile SingularAttribute<OpcProducto, Double> porcentajeGanancia;
    public static volatile SingularAttribute<OpcProducto, HashTag> hashtag;
    public static volatile SingularAttribute<OpcProducto, Boolean> activo;
    public static volatile SingularAttribute<OpcProducto, Double> precioCosto;

}