package ws;
import controller.MasterController;
import com.google.gson.Gson;
import java.util.*;
import modelo.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class wsProveedor
{
    @RequestMapping(value = "findProveedors")
    public static List<Proveedor> findProveedors
    (
        @RequestParam(value = "busqueda" , defaultValue = "") String busqueda , 
        @RequestParam(value = "soloVisibles" , defaultValue = "true") boolean soloVisibles , 
        HttpServletRequest request
    )
    {
        List<Proveedor> proveedorsList = new ArrayList<Proveedor>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT p FROM Proveedor p WHERE p.instalacion.id = " + instalacionDB.getId() ;
            
            if(busqueda != null && busqueda.trim().length() > 0)
            {
                jpql += " AND p.nombre LIKE '%" + busqueda + "%' ";
            }
            
            if(soloVisibles)
            {
                jpql += " AND p.activo = true";
            }
            
            proveedorsList = dao.DAOEclipse.findAllByJPQL(jpql);

            Collections.sort(proveedorsList);
        }
        
        return proveedorsList;
    }
    
    @RequestMapping(value = "guardarProveedor")
    public static boolean guardarProveedor
    (
         @RequestParam(value = "strProveedor" , defaultValue = "") String strProveedor,
         HttpServletRequest request
    )
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        Proveedor proveedorDB = null;
        if(strProveedor != null)
        {
            try
            {
                Proveedor proveedorRecibido = new Gson().fromJson(strProveedor, Proveedor.class);
                
                if(proveedorRecibido != null)
                {
                    // MODO EDIT:
                    if(proveedorRecibido.getId() != -1)
                    {
                        proveedorDB = (Proveedor) dao.DAOEclipse.get(Proveedor.class, proveedorRecibido.getId());
                        
                        if(proveedorDB != null)
                        {
                            // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                            proveedorDB.setNombre(proveedorRecibido.getNombre());
                            proveedorDB.setCuit(proveedorRecibido.getCuit());
                            proveedorDB.setEmail(proveedorRecibido.getEmail());
                            proveedorDB.setInstalacion(proveedorRecibido.getInstalacion());
                            
                            // ---- ARRPRODUCTOSS ----
                            // 1 - BORRO TODAS/OS LAS/LOS ARRPRODUCTOSS DE ANTES:
                            boolean borreTodos1 = true;
                            for(Producto arrProductosLoop: proveedorDB.getArrProductos())
                            {
                                boolean borreEsta = dao.DAOEclipse.remove(arrProductosLoop);
                                if(!borreEsta)
                                {
                                    borreTodos1 = false;
                                }
                            }
                            
                            // 2 - ASOCIO LAS/LOS NUEVAS/OS ARRPRODUCTOSS:
                            proveedorDB.setArrProductos(proveedorRecibido.getArrProductos());
                                                 
                            proveedorDB.setOrden(proveedorRecibido.getOrden());
                            proveedorDB.setActivo(proveedorRecibido.getActivo());
                            if( borreTodos1 )
                            {
                                guarde = dao.DAOEclipse.update(proveedorDB);
                            }
                        }
                    }
                    else
                    {
                        // 3 - MODO ADD:
                        
                        // ---- ASOCIO PRODUCTOS ----
                        for(Producto productoLoop: proveedorRecibido.getArrProductos())
                        {
                            productoLoop.setProveedor(proveedorRecibido);
                        }
                        
                        guarde = dao.DAOEclipse.update(proveedorRecibido);
                        
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
    
//    @RequestMapping(value = "cambiarActivoProveedorFast")
//    public boolean cambiarActivoProveedorFast
//    (
//        @RequestParam(value = "id") int id,
//        @RequestParam(value = "activo") boolean activo,
//        HttpServletRequest request
//    )
//    {
//        boolean ok = false;
//        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
//        Operador operadorLogeado = MasterController.dameUsuarioLogeadoFromDB(request);
//        
//        if(instalacionDB != null && operadorLogeado != null && id != -1)
//        {
//            Proveedor proveedorDB = getProveedor(id, request);
//            
//            if(proveedorDB != null)
//            {
//                proveedorDB.setActivo(activo);
//            
//                ok = dao.DAOEclipse.update(proveedorDB);
//            }
//        }
//        
//        return ok;
//    }
//    @RequestMapping(value = "cambiarOrdenProveedorFast")
//    public boolean cambiarOrdenProveedorFast
//    (
//        @RequestParam(value = "id") int id,
//        @RequestParam(value = "orden") int orden,
//        HttpServletRequest request
//    )
//    {
//        boolean ok = false;
//        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
//        Operador operadorLogeado = MasterController.dameUsuarioLogeadoFromDB(request);
//        
//        if(instalacionDB != null && operadorLogeado != null && id != -1)
//        {
//            Proveedor proveedorDB = getProveedor(id, request);
//            
//            if(proveedorDB != null)
//            {
//                proveedorDB.setOrden(orden);
//            
//                ok = dao.DAOEclipse.update(proveedorDB);
//            }
//        }
//        
//        return ok;
//    }
    @RequestMapping(value = "getProveedor")
    public static Proveedor getProveedor
    (
         @RequestParam(value = "idProveedor" , defaultValue = "-1") int idProveedor,
         HttpServletRequest request
    )
    {
        Proveedor proveedorDB = null;
        
        if(idProveedor != -1)
        {
            proveedorDB = (Proveedor) dao.DAOEclipse.get(Proveedor.class, idProveedor);
        }
        
        return proveedorDB;
    }
    @RequestMapping(value = "getProveedorByNombre")
    public static Proveedor getProveedorByNombre(@RequestParam(value = "nombre", required =  true) String nombre ,HttpServletRequest request)
    {
        Proveedor proveedorDB = null;
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT p FROM Proveedor p WHERE lower(p.nombre) = lower(\"" + nombre + "\") AND p.instalacion.id = " + instalacionDB.getId();

            proveedorDB = (Proveedor) dao.DAOEclipse.getByJPQL(jpql);
        }
        
        return proveedorDB;
    }
    @RequestMapping(value = "getProveedorDefault")
    public static Proveedor getProveedorDefault
    (
         HttpServletRequest request
    )
    {
        Proveedor proveedorDB = null;
        
        for(Proveedor proveedorLoop : findProveedors(null, true, request))
        {
            if(proveedorLoop.isEsDefault() && proveedorLoop.getActivo())
            {
                proveedorDB  = proveedorLoop;
            }
        }
        
        return proveedorDB;
    }

    public static Proveedor crearProveedor(String nombreProveedor, HttpServletRequest request)
    {
        Proveedor proveedor = null;
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        if(instalacionDB != null)
        {
            proveedor = new Proveedor(nombreProveedor, "", "", instalacionDB, 999, true);
        }
        
        return (Proveedor) dao.DAOEclipse.updateReturnObj(proveedor);
    }
    @RequestMapping(value = "getProveedorEmpty")
    public Proveedor getProveedorEmpty()
    {
       Proveedor proveedorEmpty = new Proveedor();
       proveedorEmpty.setId(-1);
       proveedorEmpty.setOrden(999);
       proveedorEmpty.setActivo(true);
       return proveedorEmpty;
    }
}
