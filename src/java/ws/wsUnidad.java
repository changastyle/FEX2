package ws;
import controller.MasterController;
import com.google.gson.Gson;
import java.util.*;
import modelo.*;
import javax.servlet.http.HttpServletRequest;
import modelo.Instalacion;
import modelo.Unidad;
import org.springframework.web.bind.annotation.*;

@RestController
public class wsUnidad
{
    @RequestMapping(value = "findUnidads")
    public static List<Unidad> findUnidads
    (
        @RequestParam(value = "busqueda" , defaultValue = "") String busqueda , 
        @RequestParam(value = "soloVisibles" , defaultValue = "true") boolean soloVisibles , 
        HttpServletRequest request
    )
    {
        List<Unidad> unidadsList = new ArrayList<Unidad>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT u FROM Unidad u WHERE u.activo = true";
//            String jpql = "SELECT u FROM Unidad u WHERE u.instalacion.id = " + instalacionDB.getId() ;
            
//            if(busqueda != null && busqueda.trim().length() > 0)
//            {
//                jpql += " AND u.nombre LIKE '%" + busqueda + "%' ";
//            }
//            
//            if(soloVisibles)
//            {
//                jpql += " AND u.activo = true";
//            }
            
            unidadsList = dao.DAOEclipse.findAllByJPQL(jpql);

            Collections.sort(unidadsList);
        }
        
        return unidadsList;
    }
    
    @RequestMapping(value = "guardarUnidad")
    public static boolean guardarUnidad
    (
         @RequestParam(value = "strUnidad" , defaultValue = "") String strUnidad,
         HttpServletRequest request
    )
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        Unidad unidadDB = null;
        if(strUnidad != null)
        {
            try
            {
                Unidad unidadRecibido = new Gson().fromJson(strUnidad, Unidad.class);
                
                if(unidadRecibido != null)
                {
                    // MODO EDIT:
                    if(unidadRecibido.getId() != -1)
                    {
                        unidadDB = (Unidad) dao.DAOEclipse.get(Unidad.class, unidadRecibido.getId());
                        
                        if(unidadDB != null)
                        {
                            // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                            unidadDB.setNombrePre(unidadRecibido.getNombrePre());
                            unidadDB.setNombrePos(unidadRecibido.getNombrePos());
                            unidadDB.setValorInicial(unidadRecibido.getValorInicial());
                            unidadDB.setMinimo(unidadRecibido.getMinimo());
                            unidadDB.setAumento(unidadRecibido.getAumento());
                            unidadDB.setActivo(unidadRecibido.getActivo());
                            guarde = dao.DAOEclipse.update(unidadDB);
                        }
                    }
                    else
                    {
                        // 3 - MODO ADD:
                        
                        guarde = dao.DAOEclipse.update(unidadRecibido);
                        
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

    public static Unidad getUnidadDefault(HttpServletRequest request)
    {
        Unidad unidadDefault = getUnidad(6, request);
        
        return unidadDefault;
    }
    
    @RequestMapping(value = "cambiarActivoUnidadFast")
    public boolean cambiarActivoUnidadFast
    (
        @RequestParam(value = "id") int id,
        @RequestParam(value = "activo") boolean activo,
        HttpServletRequest request
    )
    {
        boolean ok = false;
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        Cliente operadorLogeado = MasterController.dameClienteLogeadoFromDB(request);
        
        if(instalacionDB != null && operadorLogeado != null && id != -1)
        {
            Unidad unidadDB = getUnidad(id, request);
            
            if(unidadDB != null)
            {
                unidadDB.setActivo(activo);
            
                ok = dao.DAOEclipse.update(unidadDB);
            }
        }
        
        return ok;
    }
    @RequestMapping(value = "cambiarOrdenUnidadFast")
    public boolean cambiarOrdenUnidadFast
    (
        @RequestParam(value = "id") int id,
        @RequestParam(value = "orden") int orden,
        HttpServletRequest request
    )
    {
        boolean ok = false;
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        Cliente operadorLogeado = MasterController.dameClienteLogeadoFromDB(request);
        
        if(instalacionDB != null && operadorLogeado != null && id != -1)
        {
            Unidad unidadDB = getUnidad(id, request);
            
            if(unidadDB != null)
            {
                unidadDB.setOrden(orden);
            
                ok = dao.DAOEclipse.update(unidadDB);
            }
        }
        
        return ok;
    }
    @RequestMapping(value = "getUnidad")
    public static Unidad getUnidad
    (
         @RequestParam(value = "idUnidad" , defaultValue = "-1") int idUnidad,
         HttpServletRequest request
    )
    {
        Unidad unidadDB = null;
        
        if(idUnidad != -1)
        {
            unidadDB = (Unidad) dao.DAOEclipse.get(Unidad.class, idUnidad);
        }
        
        return unidadDB;
    }
    @RequestMapping(value = "getUnidadEmpty")
    public Unidad getUnidadEmpty()
    {
       Unidad unidadEmpty = new Unidad();
       unidadEmpty.setId(-1);
       unidadEmpty.setOrden(999);
       unidadEmpty.setActivo(true);
       return unidadEmpty;
    }
}
