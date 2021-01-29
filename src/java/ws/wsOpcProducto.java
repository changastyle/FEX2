package ws;
import com.google.gson.Gson;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import modelo.*;
import modelo.OpcProducto;
import org.springframework.web.bind.annotation.*;

@RestController
public class wsOpcProducto
{
    @RequestMapping(value = "findOpcProductos")
    public static List<OpcProducto> findOpcProductos()
    {
        List<OpcProducto> opcProductosList = new ArrayList<OpcProducto>();
        
        String jpql = "SELECT o FROM OpcProducto o";
        opcProductosList = dao.DAOEclipse.findAllByJPQL(jpql);
        
//        Collections.sort(opcProductosList);
        
        return opcProductosList;
    }
    @RequestMapping(value = "migrarOpcProductos")
    public static List<OpcProducto> migrarOpcProductos(HttpServletRequest request)
    {
        List<OpcProducto> opcProductosList = new ArrayList<OpcProducto>();
        
        String jpql = "SELECT o FROM OpcProducto o";
        opcProductosList = dao.DAOEclipse.findAllByJPQL(jpql);
        
        
        for(OpcProducto opcLoop: opcProductosList)
        {
            if(opcLoop.getHashtag() != null)
            {
                double valorFloat = opcLoop.getHashtag().getValorFloat();
                opcLoop.setValorFloat(valorFloat);
                
                
                // UNIFICO HASH EN SOLO 6 POSIBILIDADES: (grs , KG , HORMA , UNIDADES, CAJA , PACK)
                String nombreHashAnterior = opcLoop.getHashtag().getNombre();
                HashTag nvoHash = null;
                Unidad nvoUnidad = null;
                if(nombreHashAnterior.contains("grs"))
                {
                    nvoHash = wsHashTag.getHashTag(23);
                    nvoUnidad = wsUnidad.getUnidad(11 , request);
                }
                else if(nombreHashAnterior.contains("Horma"))
                {
                    nvoHash = wsHashTag.getHashTag(9);
                    nvoUnidad = wsUnidad.getUnidad(3 , request);
                }
                else if(nombreHashAnterior.contains("Pack"))
                {
                    nvoHash = wsHashTag.getHashTag(22);
                    nvoUnidad = wsUnidad.getUnidad(5 , request);
                }
                else if(nombreHashAnterior.contains("Caja"))
                {
                    nvoHash = wsHashTag.getHashTag(6);
                    nvoUnidad = wsUnidad.getUnidad(2 , request);
                }
                else if(nombreHashAnterior.contains("Bolsa"))
                {
                    nvoHash = wsHashTag.getHashTag(38);
                    nvoUnidad = wsUnidad.getUnidad(1 , request);
                }
                else if(nombreHashAnterior.contains("Kg"))
                {
                    nvoHash = wsHashTag.getHashTag(3);
                    nvoUnidad = wsUnidad.getUnidad(4 , request);
                }
                else if(nombreHashAnterior.contains("Unidades") || nombreHashAnterior.contains("Unidad"))
                {
                    nvoHash = wsHashTag.getHashTag(5);
                    nvoUnidad = wsUnidad.getUnidad(6 , request);
                }
                
                if(nvoHash != null)
                {
                    opcLoop.setHashtag(nvoHash);
                    opcLoop.setUnidad(nvoUnidad);
                }
                
                dao.DAOEclipse.update(opcLoop);
            }
        }
//        Collections.sort(opcProductosList);
        
        return opcProductosList;
    }
    
    @RequestMapping(value = "guardarOpcProducto")
    public static boolean guardarOpcProducto(@RequestParam(value = "strOpcProducto" , defaultValue = "") String strOpcProducto)
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        OpcProducto opcProductoDB = null;
        if(strOpcProducto != null)
        {
            try
            {
                OpcProducto opcProductoRecibido = new Gson().fromJson(strOpcProducto, OpcProducto.class);
                
                if(opcProductoRecibido != null)
                {
                    // MODO EDIT:
                    if(opcProductoRecibido.getId() != -1)
                    {
                        opcProductoDB = (OpcProducto) dao.DAOEclipse.get(OpcProducto.class, opcProductoRecibido.getId());
                        
                        if(opcProductoDB != null)
                        {
                            // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                            opcProductoDB.setHashtag(opcProductoRecibido.getHashtag());
                            opcProductoDB.setProducto(opcProductoRecibido.getProducto());
                            opcProductoDB.setPrecio(opcProductoRecibido.getPrecio());
                            opcProductoDB.setActivo(opcProductoRecibido.getActivo());
                            guarde = dao.DAOEclipse.update(opcProductoDB);
                        }
                    }
                    else
                    {
                        // 3 - MODO ADD:
                        
                        guarde = dao.DAOEclipse.update(opcProductoRecibido);
                        
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
    
//    @RequestMapping(value = "rmOpcProducto")
//    public static boolean rmOpcProducto(@RequestParam(value = "idOpcProducto" , defaultValue = "-1") int idOpcProducto)
//    {
//        boolean ok = false;
//        
//        OpcProducto opcProductoDB = null;
//        
//        if(idOpcProducto != -1)
//        {
//            opcProductoDB = (OpcProducto) dao.DAOEclipse.get(OpcProducto.class, idOpcProducto);
//            
//            
//            if(opcProductoDB != null)
//            {
//                ok = dao.DAOEclipse.remove(opcProductoDB);
//            }
//            
//        }
//        
//        return ok;
//    }
    @RequestMapping(value = "getOpcProducto")
    public static OpcProducto getOpcProducto(@RequestParam(value = "idOpcProducto" , defaultValue = "-1") int idOpcProducto)
    {
        OpcProducto opcProductoDB = null;
        
        if(idOpcProducto != -1)
        {
            opcProductoDB = (OpcProducto) dao.DAOEclipse.get(OpcProducto.class, idOpcProducto);
        }
        
        return opcProductoDB;
    }

    public static OpcProducto getOpcProductoDefault(HttpServletRequest request)
    {
        Unidad unidadDefault = wsUnidad.getUnidadDefault(request);
        
        OpcProducto opcProductoDefault = new OpcProducto(true, unidadDefault, 0 ,0, 0, 1, null);
        
        return opcProductoDefault;
    }

    public static OpcProducto crearNuevaOpcByUnidad(Unidad unidadDB , Producto producto , double precio , double costo, double porcentaje , double valorFloat)
    {
        OpcProducto nvoOpcProd = new OpcProducto(true, unidadDB, precio,costo, porcentaje, valorFloat, producto);
        
        return (OpcProducto) dao.DAOEclipse.updateReturnObj(nvoOpcProd);
    }
    @RequestMapping(value = "getOpcProductoEmpty")
    public OpcProducto getOpcProductoEmpty
    (
        @RequestParam(value = "fkUnidad", required = true) int fkUnidad,
        @RequestParam(value = "fkProducto" , required = false) int fkProducto,
        HttpServletRequest request
    )
    {
       OpcProducto opcProductoEmpty = new OpcProducto();
       int random = (int) Math.random() * 100;
       opcProductoEmpty.setId(-random);
       
       
        if(fkUnidad != -1)
        {
            Unidad unidadDB = wsUnidad.getUnidad(fkUnidad, request);
            
            
            if(unidadDB != null)
            {
                opcProductoEmpty.setUnidad(unidadDB);
            }
            if(fkProducto != -1)
            {
                Producto productoDB = wsProducto.getProducto(fkProducto);
                if(productoDB != null)
                {
                    opcProductoEmpty.setProducto(productoDB);
                }
            }
            
            opcProductoEmpty.setId(-1);
            opcProductoEmpty.setActivo(true);
            opcProductoEmpty.setActivarEdicion(false);
        }
        
        
        return opcProductoEmpty;
    }
    
    @RequestMapping(value = "toggleRelActivo")
    public static boolean toggleRelActivo
    (
        @RequestParam(value = "fkRelOpc", required = true) int fkRelOpc,
        HttpServletRequest request
    )
    {
        boolean ok = false;
        
        if(fkRelOpc != -1)
        {
            OpcProducto opcProdDB = getOpcProducto(fkRelOpc);
            
            if(opcProdDB != null)
            {
                boolean estadoAnt = opcProdDB.getActivo();
                
                opcProdDB.setActivo(!estadoAnt);
                
                ok = dao.DAOEclipse.update(opcProdDB);
            }
        }
        
        return ok;
    }
}
