package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Foto;
import modelo.GrupoOperador;
import modelo.Instalacion;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-29T20:36:00")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, Date> fechaAlta;
    public static volatile SingularAttribute<Cliente, String> pass;
    public static volatile SingularAttribute<Cliente, GrupoOperador> grupo;
    public static volatile SingularAttribute<Cliente, String> nombre;
    public static volatile SingularAttribute<Cliente, String> token;
    public static volatile SingularAttribute<Cliente, String> direccionEntrega;
    public static volatile SingularAttribute<Cliente, Foto> foto;
    public static volatile SingularAttribute<Cliente, String> observaciones;
    public static volatile SingularAttribute<Cliente, Instalacion> instalacion;
    public static volatile SingularAttribute<Cliente, Integer> id;
    public static volatile SingularAttribute<Cliente, String> telefono;
    public static volatile SingularAttribute<Cliente, String> email;
    public static volatile SingularAttribute<Cliente, Boolean> activo;

}