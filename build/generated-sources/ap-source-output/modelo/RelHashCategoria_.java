package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Categoria;
import modelo.HashTag;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-29T20:36:00")
@StaticMetamodel(RelHashCategoria.class)
public class RelHashCategoria_ { 

    public static volatile SingularAttribute<RelHashCategoria, Categoria> categoria;
    public static volatile SingularAttribute<RelHashCategoria, Integer> id;
    public static volatile SingularAttribute<RelHashCategoria, Boolean> activo;
    public static volatile SingularAttribute<RelHashCategoria, HashTag> hashtag;

}