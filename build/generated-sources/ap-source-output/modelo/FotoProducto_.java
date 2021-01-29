package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Foto;
import modelo.Producto;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-27T20:52:29")
@StaticMetamodel(FotoProducto.class)
public class FotoProducto_ { 

    public static volatile SingularAttribute<FotoProducto, Foto> foto;
    public static volatile SingularAttribute<FotoProducto, Integer> id;
    public static volatile SingularAttribute<FotoProducto, Producto> producto;
    public static volatile SingularAttribute<FotoProducto, Integer> orden;
    public static volatile SingularAttribute<FotoProducto, Boolean> activo;

}