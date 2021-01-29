package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HashTag;
import modelo.Producto;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-27T20:52:29")
@StaticMetamodel(RelHashProducto.class)
public class RelHashProducto_ { 

    public static volatile SingularAttribute<RelHashProducto, Integer> id;
    public static volatile SingularAttribute<RelHashProducto, Producto> producto;
    public static volatile SingularAttribute<RelHashProducto, Boolean> activo;
    public static volatile SingularAttribute<RelHashProducto, HashTag> hashtag;

}