package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Foto;
import modelo.RelHashCategoria;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-27T20:52:29")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile SingularAttribute<Categoria, String> icono;
    public static volatile ListAttribute<Categoria, RelHashCategoria> arrRelsByCategoria;
    public static volatile SingularAttribute<Categoria, Foto> foto;
    public static volatile SingularAttribute<Categoria, Integer> id;
    public static volatile SingularAttribute<Categoria, String> nombre;
    public static volatile SingularAttribute<Categoria, Boolean> activo;

}