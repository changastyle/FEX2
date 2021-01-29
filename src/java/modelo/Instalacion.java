package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "instalaciones")
public class Instalacion implements Comparable<Instalacion>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean activo;
    @JsonIgnore
    private String alias;
    @JsonIgnore
    private String firmaMail;
    private String calle;
    @JsonIgnore
    private String carpetaWeb;
    @JsonIgnore
    private String cc;
    private String ciudad;
    private String colorPri;
    private String colorSec;
    private String colorTer;
    private String email;
    @OneToOne() @JoinColumn(name = "fkFavicon")
    private Foto favicon;
    private double lat;
    private double longi;
    private String googleMaps;
    @JsonIgnore
    private String msg;
    private String nombre;
    private String pais;
    
    private String prefijo;
    private String telefonoFijo;
    private String telefonoWpp;
    private String urlDominio;
    private String urlDominioDos;
    
    @JsonIgnore
    private String pass;
    private String clientID;
    @JsonIgnore
    private String clientSecret;
    private String txtTiempoEnvio;
    
    @OneToOne() @JoinColumn(name = "fkLogo")
    private Foto logo;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "instalacion") @JsonIgnore
    private List<Foto> arrFotos;
    
    @OneToOne() @JoinColumn(name = "fkClienteVolatil")
    private Cliente clienteVolatil;
    
    //CONTRUCTOR VACIO:
    public Instalacion() 
    {
        arrFotos = new ArrayList<>();
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public Instalacion(boolean activo,String alias,String calle,String carpetaWeb,String cc,String ciudad,String colorPri,String colorSec,String colorTer,String email,Foto favicon,String googleMaps,String msg,String nombre,String pais,String pass,String prefijo,String telefonoFijo,String telefonoWpp,String urlDominio,Foto logo)
    {
        this.activo = activo;
        this.alias = alias;
        this.calle = calle;
        this.carpetaWeb = carpetaWeb;
        this.cc = cc;
        this.ciudad = ciudad;
        this.colorPri = colorPri;
        this.colorSec = colorSec;
        this.colorTer = colorTer;
        this.email = email;
        this.favicon = favicon;
        this.googleMaps = googleMaps;
        this.msg = msg;
        this.nombre = nombre;
        this.pais = pais;
        this.pass = pass;
        this.prefijo = prefijo;
        this.telefonoFijo = telefonoFijo;
        this.telefonoWpp = telefonoWpp;
        this.urlDominio = urlDominio;
        this.logo = logo;
        this.arrFotos = new ArrayList<>();
    }
    

    //<editor-fold desc="GETTERS Y SETTERS:">
    public int getId() 
    {
        return id;
    }
    public boolean getActivo() 
    {
        return activo;
    }
    public String getAlias() 
    {
        return alias;
    }
    public String getCalle() 
    {
        return calle;
    }
    public String getCarpetaWeb() 
    {
        return carpetaWeb;
    }
    public String getCc() 
    {
        return cc;
    }
    public String getCiudad() 
    {
        return ciudad;
    }
    public String getColorPri() 
    {
        return colorPri;
    }
    public String getColorSec() 
    {
        return colorSec;
    }
    public String getColorTer() 
    {
        return colorTer;
    }
    public String getEmail() 
    {
        return email;
    }
    public Foto getFavicon() 
    {
        return favicon;
    }
    public String getGoogleMaps() 
    {
        return googleMaps;
    }
    public String getMsg() 
    {
        return msg;
    }
    public String getNombre() 
    {
        return nombre;
    }
    public String getPais() 
    {
        return pais;
    }
    public String getPass() 
    {
        return pass;
    }
    public String getPrefijo() 
    {
        return prefijo;
    }
    public String getTelefonoFijo() 
    {
        return telefonoFijo;
    }
    public String getTelefonoWpp() 
    {
        return telefonoWpp;
    }
    public String getUrlDominio() 
    {
        return urlDominio;
    }
    public Foto getLogo() 
    {
        return logo;
    }

    public List<Foto> getArrFotos()
    {
        return arrFotos;
    }

    public String getFirmaMail()
    {
        return firmaMail;
    }

    public double getLat()
    {
        return lat;
    }

    public void setLat(double lat)
    {
        this.lat = lat;
    }

    public double getLongi()
    {
        return longi;
    }

    public void setLongi(double longi)
    {
        this.longi = longi;
    }

    public Cliente getClienteVolatil()
    {
        return clienteVolatil;
    }

    public void setClienteVolatil(Cliente clienteVolatil)
    {
        this.clienteVolatil = clienteVolatil;
    }

    public String getTxtTiempoEnvio() {
        return txtTiempoEnvio;
    }

    public void setTxtTiempoEnvio(String txtTiempoEnvio) {
        this.txtTiempoEnvio = txtTiempoEnvio;
    }
    
    
    
    

    //SET
    public void setFirmaMail(String firmaMail)
    {
        this.firmaMail = firmaMail;
    }
    public void setId( int id ) 
    {
        this.id = id;
    }
    public void setActivo( boolean activo ) 
    {
        this.activo = activo;
    }
    public void setAlias( String alias ) 
    {
        this.alias = alias;
    }
    public void setCalle( String calle ) 
    {
        this.calle = calle;
    }
    public void setCarpetaWeb( String carpetaWeb ) 
    {
        this.carpetaWeb = carpetaWeb;
    }
    public void setCc( String cc ) 
    {
        this.cc = cc;
    }
    public void setCiudad( String ciudad ) 
    {
        this.ciudad = ciudad;
    }
    public void setColorPri( String colorPri ) 
    {
        this.colorPri = colorPri;
    }
    public void setColorSec( String colorSec ) 
    {
        this.colorSec = colorSec;
    }
    public void setColorTer( String colorTer ) 
    {
        this.colorTer = colorTer;
    }
    public void setEmail( String email ) 
    {
        this.email = email;
    }
    public void setFavicon( Foto favicon ) 
    {
        this.favicon = favicon;
    }
    public void setGoogleMaps( String googleMaps ) 
    {
        this.googleMaps = googleMaps;
    }
    public void setMsg( String msg ) 
    {
        this.msg = msg;
    }
    public void setNombre( String nombre ) 
    {
        this.nombre = nombre;
    }
    public void setPais( String pais ) 
    {
        this.pais = pais;
    }
    public void setPass( String pass ) 
    {
        this.pass = pass;
    }
    public void setPrefijo( String prefijo ) 
    {
        this.prefijo = prefijo;
    }
    public void setTelefonoFijo( String telefonoFijo ) 
    {
        this.telefonoFijo = telefonoFijo;
    }
    public void setTelefonoWpp( String telefonoWpp ) 
    {
        this.telefonoWpp = telefonoWpp;
    }
    public void setUrlDominio( String urlDominio ) 
    {
        this.urlDominio = urlDominio;
    }
    public void setLogo( Foto logo ) 
    {
        this.logo = logo;
    }

    public void setArrFotos(List<Foto> arrFotos)
    {
        this.arrFotos = arrFotos;
    }
    
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "alias:" + alias + ", ";
        str += "calle:" + calle + ", ";
        str += "carpetaWeb:" + carpetaWeb + ", ";
        str += "cc:" + cc + ", ";
        str += "ciudad:" + ciudad + ", ";
        str += "colorPri:" + colorPri + ", ";
        str += "colorSec:" + colorSec + ", ";
        str += "colorTer:" + colorTer + ", ";
        str += "email:" + email + ", ";
        str += "googleMaps:" + googleMaps + ", ";
        str += "msg:" + msg + ", ";
        str += "nombre:" + nombre + ", ";
        str += "pais:" + pais + ", ";
        str += "pass:" + pass + ", ";
        str += "prefijo:" + prefijo + ", ";
        str += "telefonoFijo:" + telefonoFijo + ", ";
        str += "telefonoWpp:" + telefonoWpp + ", ";
        str += "urlDominio:" + urlDominio + ", ";
        str += "logo:" + logo + ", ";
        
        str += "}";
        
        return str;
    }

    public String getClientID()
    {
        return clientID;
    }

    public void setClientID(String clientID)
    {
        this.clientID = clientID;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
    }

    public String getUrlDominioDos() {
        return urlDominioDos;
    }

    public void setUrlDominioDos(String urlDominioDos) {
        this.urlDominioDos = urlDominioDos;
    }
    

 
        
    
    //DYN:

    
    public int compareTo(Instalacion otro)
    {
        return 1;
    }
    public String getDameNumeroParaWhats()
    {
        String numeroPosta = "5492944" + telefonoWpp;
        
        return numeroPosta;
    }

    public boolean tengoMP()
    {
        boolean tengoMP = false;
        
        
        if(clientID != null && clientSecret != null)
        {
            if( clientID.trim().length() > 0 && clientSecret.trim().length() > 0)
            {
                tengoMP = true;
            }
        }
        
        return tengoMP;
    }
}
