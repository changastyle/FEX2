package ws;

import controller.MasterController;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modelo.Configuracion;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class wsConfiguraciones
{
    @RequestMapping(value = "findConfiguraciones")
    public static List<Configuracion> findConfiguraciones()
    {
        List<Configuracion> configuracionesList = new ArrayList<Configuracion>();
        
        String jpql = "SELECT c FROM Configuracion c";
        configuracionesList = dao.DAOEclipse.findAllByJPQL(jpql);
        
        //Collections.sort(configuracionesList);
        
        return configuracionesList;
    }
    @RequestMapping(value = "dameConfigActual")
    public static Configuracion dameConfigActual()
    {
        Configuracion configuracionActual = null;
        
        for(Configuracion configLoop : findConfiguraciones())
        {
            if(configLoop.isEnabled())
            {
                configuracionActual = configLoop;
                break;
            }
        }
        return configuracionActual;
    }
    @RequestMapping(value = "forzarConfig")
    public static Configuracion forzarConfig()
    {
        Configuracion config = null; 
        MasterController.timestampUltimaActualizacionConfiguracion = -1;
        config =  MasterController.dameConfigMaster();
        
        return config;
    }
    @RequestMapping(value = "pasarANafta")
    public static Configuracion pasarANafta()
    {
        Configuracion config = null; 
        
        try 
        {
            // 1 -  DAME MI IP DE LA RED (NO LOCALHOST):
            InetAddress ipOBJ = InetAddress.getLocalHost();
            String ip = ipOBJ.getHostAddress();
            System.out.println("LA IP ES: " + ip);
            
            
            
            config =  MasterController.dameConfigMaster();
            if(config.isEnabled())
            {
                if(ip != null)
                {
                    config.setIp(ip);
                }
            }
            
            if(config != null)
            {
                if(dao.DAOEclipse.update(config))
                {
                    forzarConfig();
                }
            }
            
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return config;
    }
}
