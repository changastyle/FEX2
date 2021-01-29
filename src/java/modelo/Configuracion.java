package modelo;

import controller.MasterController;
import java.io.File;
import javax.persistence.*;

@Entity @Table( name = "configuraciones")
public class Configuracion implements Comparable<Configuracion>
{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private boolean enabled;
    private String protocolo;
    private String ip;
    private String puerto;
    private String nombreProyecto;
    private String subCarpetaImagenes;
    private String varSessionHTTP;
    @Transient
    private String urlVisualizacion;
    @Transient
    private String rutaFileSystem;

    public Configuracion()
    {
    }

    public Configuracion(int id, String nombre, boolean enabled, String protocolo, String ip, String puerto, String nombreProyecto, String subCarpetaImagenes, String varSessionHTTP, String urlVisualizacion, String rutaFileSystem)
    {
        this.id = id;
        this.nombre = nombre;
        this.enabled = enabled;
        this.protocolo = protocolo;
        this.ip = ip;
        this.puerto = puerto;
        this.nombreProyecto = nombreProyecto;
        this.subCarpetaImagenes = subCarpetaImagenes;
        this.varSessionHTTP = varSessionHTTP;
        this.urlVisualizacion = urlVisualizacion;
        this.rutaFileSystem = rutaFileSystem;
    }

    


    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getProtocolo()
    {
        return protocolo;
    }

    public void setProtocolo(String protocolo)
    {
        this.protocolo = protocolo;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getPuerto()
    {
        return puerto;
    }

    public void setPuerto(String puerto)
    {
        this.puerto = puerto;
    }

    public String getNombreProyecto()
    {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto)
    {
        this.nombreProyecto = nombreProyecto;
    }

    public String getVarSessionHTTP()
    {
        return varSessionHTTP;
    }

    //<editor-fold desc="GYS:">
    public void setVarSessionHTTP(String varSessionHTTP)
    {
        this.varSessionHTTP = varSessionHTTP;
    }

    public String getSubCarpetaImagenes()
    {
        return subCarpetaImagenes;
    }

    public void setSubCarpetaImagenes(String subCarpetaImagenes)
    {
        this.subCarpetaImagenes = subCarpetaImagenes;
    }
    

    //</editor-fold>
    @Override
    public int compareTo(Configuracion o)
    {
        if(this.enabled)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }
    
    // DYN:
    public String getUrlVisualizacion()
    {
        String urlCompleta = protocolo + "" + ip + "" + puerto + "/upload/" + nombreProyecto ;
        return urlCompleta;
    }
//    public String getUrlVisualizacionBackground()
//    {
//        String urlCompleta = protocolo + "" + ip + "" + puerto + "/upload/" + nombreProyecto + "/backgrounds" ;
//        return urlCompleta;
//    }
//    public String getUrlVisualizacionIconos()
//    {
//        String urlCompleta = protocolo + "" + ip + "" + puerto + "/upload/" + nombreProyecto ;
//        return urlCompleta;
//    }
    public String getRutaFileSystem()
    {
        String ruta = "";
        String os = System.getProperty("os.name");
        
        //+ File.separator + subCarpetaImagenes
        if(os.startsWith("Windows"))
        {
            //WINDOWS:
            ruta = "C:\\xampp\\htdocs\\upload\\" + nombreProyecto ;
        }
        else if(os.startsWith("Mac OS X"))
        {
            ruta = "/Applications/XAMPP/xamppfiles/htdocs/upload/" + nombreProyecto ;
        }
        else
        {
            //LINUX:
            ruta = "/var/www/upload/" + nombreProyecto ;
        }
        
        return ruta;
    }
}
