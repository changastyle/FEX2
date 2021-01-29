package ws;
import com.google.gson.Gson;
import controller.MasterController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import modelo.*;
import modelo.HashTag;
import org.springframework.web.bind.annotation.*;
import static ws.wsCategoria.findAllCategorias;

@RestController
public class wsHashTag
{
    @RequestMapping(value = "findHashTags")
    public static List<HashTag> findHashTags()
    {
        List<HashTag> hashTagsList = new ArrayList<HashTag>();
        
        String jpql = "SELECT h FROM HashTag h";
        hashTagsList = dao.DAOEclipse.findAllByJPQL(jpql);
        
        Collections.sort(hashTagsList);
        
        return hashTagsList;
    }
    @RequestMapping(value = "findHashTagsDeUnidades")
    public static List<HashTag> findHashTagsDeUnidades()
    {
        List<HashTag> hashTagsList = new ArrayList<HashTag>();
        
        String jpql = "SELECT h FROM HashTag h WHERE h.esDeUnidad = true";
        hashTagsList = dao.DAOEclipse.findAllByJPQL(jpql);
        
        Collections.sort(hashTagsList);
        
        return hashTagsList;
    }
    
    @RequestMapping(value = "guardarHashTag")
    public static boolean guardarHashTag(@RequestParam(value = "strHashTag" , defaultValue = "") String strHashTag)
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        HashTag hashTagDB = null;
        if(strHashTag != null)
        {
            try
            {
                HashTag hashTagRecibido = new Gson().fromJson(strHashTag, HashTag.class);
                
                if(hashTagRecibido != null)
                {
                    // MODO EDIT:
                    if(hashTagRecibido.getId() != -1)
                    {
                        hashTagDB = (HashTag) dao.DAOEclipse.get(HashTag.class, hashTagRecibido.getId());
                        
                        if(hashTagDB != null)
                        {
                            // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                            hashTagDB.setNombre(hashTagRecibido.getNombre());
                            hashTagDB.setUrlAwesom(hashTagRecibido.getUrlAwesom());
                            hashTagDB.setEsDeColor(hashTagRecibido.getEsDeColor());
                            hashTagDB.setEsDeUnidad(hashTagRecibido.getEsDeUnidad());
                            hashTagDB.setValorFloat(hashTagRecibido.getValorFloat());
                            hashTagDB.setActivo(hashTagRecibido.getActivo());
                            guarde = dao.DAOEclipse.update(hashTagDB);
                        }
                    }
                    else
                    {
                        // 3 - MODO ADD:
                        
                        guarde = dao.DAOEclipse.update(hashTagRecibido);
                        
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
    @RequestMapping(value = "altaNvaUnidad")
    public static boolean altaNvaUnidad
    (
        @RequestParam(value = "nombreUnidad" , defaultValue = "") String nombreUnidad,
        @RequestParam(value = "aumento" , defaultValue = "-1") double aumento,
        @RequestParam(value = "minimo" , defaultValue = "-1") double minimo,
        @RequestParam(value = "valorFloat" , defaultValue = "-1") double valorFloat,
        HttpServletRequest request
    )
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        HashTag hashTagDB = null;
        
        Cliente clienteLogeado = MasterController.dameClienteLogeadoFromDB(request);
        
        if(clienteLogeado != null)
        {
            GrupoOperador grupo = clienteLogeado.getGrupo();
            if(grupo != null)
            {
                int jerarquia = grupo.getJerarquia();
                if(jerarquia > 3)
                {
                    if(nombreUnidad != null && nombreUnidad.trim().length() > 0  && aumento  != -1 && minimo != -1 && valorFloat != -1)
                    {
                        HashTag hashTagNvo = new HashTag(nombreUnidad, nombreUnidad, valorFloat, aumento, minimo, true, true);

                        if(hashTagNvo != null)
                        {
                            guarde = dao.DAOEclipse.update(hashTagNvo);
                        }

                    }
                    
                }
            }
        }
        
        
        return guarde;
    }
    
    @RequestMapping(value = "getHashTag")
    public static HashTag getHashTag(@RequestParam(value = "idHashTag" , defaultValue = "-1") int idHashTag)
    {
        HashTag hashTagDB = null;
        
        if(idHashTag != -1)
        {
            hashTagDB = (HashTag) dao.DAOEclipse.get(HashTag.class, idHashTag);
        }
        
        return hashTagDB;
    }
    @RequestMapping(value = "getHashTagEmpty")
    public HashTag getHashTagEmpty()
    {
       HashTag hashTagEmpty = new HashTag();
       hashTagEmpty.setId(-1);
       return hashTagEmpty;
    }
    
    
    public static List<HashTag> arrHashFast;
    public static HashTag getHashByNombreFast(@RequestParam(value = "nombre", required =  true) String nombre)
    {
        if(arrHashFast == null)
        {
            arrHashFast = findHashTagsDeUnidades();
        }
        
        HashTag hashRta = null;
        
        for(HashTag hashLoop : arrHashFast)
        {
            if(hashLoop.getNombre().equalsIgnoreCase(nombre))
            {
                hashRta = hashLoop;
                break;
            }
        }
        
        return hashRta;
    }
}
