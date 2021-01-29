package ws;
import com.google.gson.Gson;
import controller.MasterController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import modelo.*;
import modelo.Instalacion;
import org.springframework.web.bind.annotation.*;

@RestController
public class wsInstalacion
{
    @RequestMapping(value = "getInstalacionSegunURL")
    public Instalacion getInstalacionSegunURL(HttpServletRequest request)
    {
        return MasterController.dameInstalacionByRequest(request);
    }
    
    @RequestMapping(value = "findInstalacions")
    public static List<Instalacion> findInstalacions()
    {
        List<Instalacion> instalacionsList = new ArrayList<Instalacion>();
        
        String jpql = "SELECT i FROM Instalacion i";
        instalacionsList = dao.DAOEclipse.findAllByJPQL(jpql);
        
        Collections.sort(instalacionsList);
        
        return instalacionsList;
    }
    
    @RequestMapping(value = "guardarInstalacion")
    public static boolean guardarInstalacion(@RequestParam(value = "strInstalacion" , defaultValue = "") String strInstalacion)
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        Instalacion instalacionDB = null;
        if(strInstalacion != null)
        {
            try
            {
                Instalacion instalacionRecibido = new Gson().fromJson(strInstalacion, Instalacion.class);
                
                if(instalacionRecibido != null)
                {
                    // MODO EDIT:
                    if(instalacionRecibido.getId() != -1)
                    {
                        instalacionDB = (Instalacion) dao.DAOEclipse.get(Instalacion.class, instalacionRecibido.getId());
                        
                        if(instalacionDB != null)
                        {
                            // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                            instalacionDB.setNombre(instalacionRecibido.getNombre());
                            instalacionDB.setEmail(instalacionRecibido.getEmail());
                            instalacionDB.setAlias(instalacionRecibido.getAlias());
                            instalacionDB.setCc(instalacionRecibido.getCc());
                            instalacionDB.setPass(instalacionRecibido.getPass());
                            instalacionDB.setUrlDominio(instalacionRecibido.getUrlDominio());
                            instalacionDB.setMsg(instalacionRecibido.getMsg());
                            instalacionDB.setFavicon(instalacionRecibido.getFavicon());
                            instalacionDB.setLogo(instalacionRecibido.getLogo());
                            instalacionDB.setColorPri(instalacionRecibido.getColorPri());
                            instalacionDB.setColorSec(instalacionRecibido.getColorSec());
                            instalacionDB.setColorTer(instalacionRecibido.getColorTer());
                            instalacionDB.setGoogleMaps(instalacionRecibido.getGoogleMaps());
                            instalacionDB.setCalle(instalacionRecibido.getCalle());
                            instalacionDB.setCiudad(instalacionRecibido.getCiudad());
                            instalacionDB.setPais(instalacionRecibido.getPais());
                            instalacionDB.setPrefijo(instalacionRecibido.getPrefijo());
                            instalacionDB.setTelefonoWpp(instalacionRecibido.getTelefonoWpp());
                            instalacionDB.setTelefonoFijo(instalacionRecibido.getTelefonoFijo());
                            instalacionDB.setCarpetaWeb(instalacionRecibido.getCarpetaWeb());
                            
                            // ---- ARRRELSDIASTRABAJOS ----
                            // 1 - BORRO TODAS/OS LAS/LOS ARRRELSDIASTRABAJOS DE ANTES:
                            boolean borreTodos1 = true;
                            
                            // ---- ARRPROMOSS ----
                            // 3 - BORRO TODAS/OS LAS/LOS ARRPROMOSS DE ANTES:
                            boolean borreTodos3 = true;
                            
                            // 4 - ASOCIO LAS/LOS NUEVAS/OS ARRPROMOSS:
                                                 
                            
                            // ---- ARRRELCATEGORIASS ----
                            // 4 - BORRO TODAS/OS LAS/LOS ARRRELCATEGORIASS DE ANTES:
                            boolean borreTodos4 = true;
                            
                            // 5 - ASOCIO LAS/LOS NUEVAS/OS ARRRELCATEGORIASS:
                                                 
                            
                            // ---- ARRPRODUCTOSS ----
                            // 5 - BORRO TODAS/OS LAS/LOS ARRPRODUCTOSS DE ANTES:
                            boolean borreTodos5 = true;
                            
                            // 6 - ASOCIO LAS/LOS NUEVAS/OS ARRPRODUCTOSS:
                                                 
                            instalacionDB.setActivo(instalacionRecibido.getActivo());
                            if( borreTodos1 &&  borreTodos3 &&  borreTodos4 &&  borreTodos5 )
                            {
                                guarde = dao.DAOEclipse.update(instalacionDB);
                            }
                        }
                    }
                    else
                    {
                        // 3 - MODO ADD:
                        
                        guarde = dao.DAOEclipse.update(instalacionRecibido);
                        
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
        
        return guarde;
    }
    
    @RequestMapping(value = "getInstalacion")
    public static Instalacion getInstalacion(@RequestParam(value = "idInstalacion" , defaultValue = "-1") int idInstalacion)
    {
        Instalacion instalacionDB = null;
        
        if(idInstalacion != -1)
        {
            instalacionDB = (Instalacion) dao.DAOEclipse.get(Instalacion.class, idInstalacion);
        }
        
        return instalacionDB;
    }
    @RequestMapping(value = "getInstalacionEmpty")
    public Instalacion getInstalacionEmpty()
    {
       Instalacion instalacionEmpty = new Instalacion();
       instalacionEmpty.setId(-1);
       return instalacionEmpty;
    }
}
