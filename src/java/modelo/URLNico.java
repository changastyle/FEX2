package modelo;

import controller.MasterController;
import java.io.File;
import javax.servlet.http.HttpServletRequest;

public class URLNico 
{
    
    private String urlProvisoria;
    private String urlFinal;

    public URLNico() 
    {
    }

    public URLNico(String url , String subCarpeta , HttpServletRequest request)
    {
        if(url != null)
        {
            int posUltimoSlash = url.lastIndexOf("/");
            if(posUltimoSlash == -1)
            {
                posUltimoSlash = url.lastIndexOf(File.separator);
            }
            
            if(posUltimoSlash != -1)
            {
                this.urlProvisoria = url.substring((posUltimoSlash +1), url.length());
                this.urlFinal = url;
            }
            else
            {
                this.urlProvisoria = url;
                
                Configuracion config = MasterController.dameConfigMaster();
                Instalacion instalacion = MasterController.dameInstalacionByRequest(request);
                String strInstalacion = instalacion.getCarpetaWeb();
                
                if(config != null)
                {
                    this.urlFinal = config.getProtocolo() + "" +config.getIp() + "" + config.getPuerto() + "/upload/" + config.getNombreProyecto() + "/" + strInstalacion + "/" + subCarpeta + "/"  + url; 
                }
            }
        }
    }

    public String getUrlProvisoria() {
        return urlProvisoria;
    }

    public void setUrlProvisoria(String urlProvisoria) {
        this.urlProvisoria = urlProvisoria;
    }

    public String getUrlFinal() {
        return urlFinal;
    }

    public void setUrlFinal(String urlFinal) {
        this.urlFinal = urlFinal;
    }

    @Override
    public String toString()
    {
        return "URLNico{" + "urlProvisoria=" + urlProvisoria + ", urlFinal=" + urlFinal + '}';
    }
    
}
