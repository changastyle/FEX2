package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cliente;
import modelo.Foto;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-27T20:52:29")
@StaticMetamodel(Instalacion.class)
public class Instalacion_ { 

    public static volatile SingularAttribute<Instalacion, String> carpetaWeb;
    public static volatile SingularAttribute<Instalacion, String> colorPri;
    public static volatile SingularAttribute<Instalacion, String> msg;
    public static volatile SingularAttribute<Instalacion, String> clientID;
    public static volatile SingularAttribute<Instalacion, Double> longi;
    public static volatile SingularAttribute<Instalacion, String> googleMaps;
    public static volatile SingularAttribute<Instalacion, String> nombre;
    public static volatile SingularAttribute<Instalacion, String> txtTiempoEnvio;
    public static volatile SingularAttribute<Instalacion, String> urlDominio;
    public static volatile SingularAttribute<Instalacion, String> telefonoWpp;
    public static volatile SingularAttribute<Instalacion, String> alias;
    public static volatile SingularAttribute<Instalacion, Foto> logo;
    public static volatile SingularAttribute<Instalacion, String> clientSecret;
    public static volatile SingularAttribute<Instalacion, Integer> id;
    public static volatile SingularAttribute<Instalacion, String> firmaMail;
    public static volatile SingularAttribute<Instalacion, String> email;
    public static volatile SingularAttribute<Instalacion, Double> lat;
    public static volatile SingularAttribute<Instalacion, String> cc;
    public static volatile ListAttribute<Instalacion, Foto> arrFotos;
    public static volatile SingularAttribute<Instalacion, Cliente> clienteVolatil;
    public static volatile SingularAttribute<Instalacion, Foto> favicon;
    public static volatile SingularAttribute<Instalacion, String> pass;
    public static volatile SingularAttribute<Instalacion, String> calle;
    public static volatile SingularAttribute<Instalacion, String> colorSec;
    public static volatile SingularAttribute<Instalacion, String> prefijo;
    public static volatile SingularAttribute<Instalacion, String> pais;
    public static volatile SingularAttribute<Instalacion, String> telefonoFijo;
    public static volatile SingularAttribute<Instalacion, String> urlDominioDos;
    public static volatile SingularAttribute<Instalacion, String> ciudad;
    public static volatile SingularAttribute<Instalacion, String> colorTer;
    public static volatile SingularAttribute<Instalacion, Boolean> activo;

}