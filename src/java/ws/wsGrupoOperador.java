package ws;
import com.google.gson.Gson;
import controller.MasterController;
import java.util.*;
import modelo.*;
import javax.servlet.http.HttpServletRequest;
import modelo.GrupoOperador;
import modelo.Instalacion;
import org.springframework.web.bind.annotation.*;

@RestController
public class wsGrupoOperador
{
    @RequestMapping(value = "findGrupoOperadors")
    public static List<GrupoOperador> findGrupoOperadors(HttpServletRequest request)
    {
        List<GrupoOperador> grupoOperadorsList = new ArrayList<GrupoOperador>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
             String jpql = "SELECT g FROM GrupoOperador g WHERE g.instalacion.id == " + instalacionDB.getId();
             grupoOperadorsList = dao.DAOEclipse.findAllByJPQL(jpql);
             
             Collections.sort(grupoOperadorsList);
        }
        
        return grupoOperadorsList;
    }
    
    @RequestMapping(value = "guardarGrupoOperador")
    public static boolean guardarGrupoOperador(@RequestParam(value = "strGrupoOperador" , defaultValue = "") String strGrupoOperador)
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        GrupoOperador grupoOperadorDB = null;
        if(strGrupoOperador != null)
        {
            try
            {
                GrupoOperador grupoOperadorRecibido = new Gson().fromJson(strGrupoOperador, GrupoOperador.class);
                
                if(grupoOperadorRecibido != null)
                {
                    // MODO EDIT:
                    if(grupoOperadorRecibido.getId() != -1)
                    {
                        grupoOperadorDB = (GrupoOperador) dao.DAOEclipse.get(GrupoOperador.class, grupoOperadorRecibido.getId());
                        
                        if(grupoOperadorDB != null)
                        {
                            // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                            grupoOperadorDB.setNombre(grupoOperadorRecibido.getNombre());
                            grupoOperadorDB.setJerarquia(grupoOperadorRecibido.getJerarquia());
                                guarde = dao.DAOEclipse.update(grupoOperadorDB);
                        }
                    }
                    else
                    {
                        // 3 - MODO ADD:
                        
                        guarde = dao.DAOEclipse.update(grupoOperadorRecibido);
                        
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
    
    @RequestMapping(value = "getGrupoOperador")
    public static GrupoOperador getGrupoOperador(@RequestParam(value = "idGrupoOperador" , defaultValue = "-1") int idGrupoOperador)
    {
        GrupoOperador grupoOperadorDB = null;
        
        if(idGrupoOperador != -1)
        {
            grupoOperadorDB = (GrupoOperador) dao.DAOEclipse.get(GrupoOperador.class, idGrupoOperador);
        }
        
        return grupoOperadorDB;
    }
    @RequestMapping(value = "getGrupoOperadorEmpty")
    public GrupoOperador getGrupoOperadorEmpty()
    {
       GrupoOperador grupoOperadorEmpty = new GrupoOperador();
       grupoOperadorEmpty.setId(-1);
       return grupoOperadorEmpty;
    }
}
