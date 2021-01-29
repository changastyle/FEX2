package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Instalacion;

@Generated(value="EclipseLink-2.6.1.v20150605-rNA", date="2021-01-27T20:52:29")
@StaticMetamodel(ZonaEnvio.class)
public class ZonaEnvio_ { 

    public static volatile SingularAttribute<ZonaEnvio, String> texto;
    public static volatile SingularAttribute<ZonaEnvio, Double> minimoDeCompra;
    public static volatile SingularAttribute<ZonaEnvio, Double> precioEnvio;
    public static volatile SingularAttribute<ZonaEnvio, Integer> distanciaMax;
    public static volatile SingularAttribute<ZonaEnvio, Instalacion> instalacion;
    public static volatile SingularAttribute<ZonaEnvio, Integer> id;
    public static volatile SingularAttribute<ZonaEnvio, Integer> orden;
    public static volatile SingularAttribute<ZonaEnvio, Integer> distanciaMin;
    public static volatile SingularAttribute<ZonaEnvio, String> nombre;
    public static volatile SingularAttribute<ZonaEnvio, Boolean> porDefault;
    public static volatile SingularAttribute<ZonaEnvio, Boolean> activo;

}