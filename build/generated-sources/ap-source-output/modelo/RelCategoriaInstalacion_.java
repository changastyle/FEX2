package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Categoria;
import modelo.Instalacion;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-29T20:36:00")
@StaticMetamodel(RelCategoriaInstalacion.class)
public class RelCategoriaInstalacion_ { 

    public static volatile SingularAttribute<RelCategoriaInstalacion, Categoria> categoria;
    public static volatile SingularAttribute<RelCategoriaInstalacion, Instalacion> instalacion;
    public static volatile SingularAttribute<RelCategoriaInstalacion, Integer> id;
    public static volatile SingularAttribute<RelCategoriaInstalacion, Boolean> activo;

}