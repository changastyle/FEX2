package ws;
import com.google.gson.Gson;
import controller.MasterController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import modelo.*;
import modelo.Categoria;
import modelo.Producto;
import modelo.RelHashCategoria;
import org.springframework.web.bind.annotation.*;

@RestController
public class wsCategoria
{
    @RequestMapping(value = "dameTodasLasCategoriasComoRelsDeInstalacion")
    public static List<RelCategoriaInstalacion> dameTodasLasCategoriasComoRelsDeInstalacion
    (
        HttpServletRequest request
    )
    {
        List<RelCategoriaInstalacion> arrRels = new ArrayList<>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        List<Categoria> arrCategorias = wsCategoria.findAllCategorias();
        
        if(instalacionDB != null)
        {
            int fkInstalacion = instalacionDB.getId();
            
            String jpql = "SELECT r FROM RelCategoriaInstalacion r WHERE r.instalacion.id = " + fkInstalacion;
            arrRels = dao.DAOEclipse.findAllByJPQL(jpql);
            Collections.sort(arrRels);
            
            for(Categoria categoriaLoop : arrCategorias)
            {
                boolean existe = false;
                for(RelCategoriaInstalacion relLoop : arrRels)
                {
                    if(categoriaLoop.getId() == relLoop.getCategoria().getId())
                    {
                        existe = true;
                        break;
                    }
                }
                
                if(!existe)
                {
                    RelCategoriaInstalacion relNew = new RelCategoriaInstalacion(false, categoriaLoop, instalacionDB);
                    arrRels.add(relNew);
                }
            }
        }
        
        
        return arrRels;
    }
    @RequestMapping(value = "findAllCategorias")
    public static List<Categoria> findAllCategorias()
    {
        List<Categoria> categoriasList = new ArrayList<Categoria>();
        
        String jpql = "SELECT c FROM Categoria c";
        categoriasList = dao.DAOEclipse.findAllByJPQL(jpql);
        
        Collections.sort(categoriasList);
        
        return categoriasList;
    }
    
    @RequestMapping(value = "findCategoriasByInstalacion")
    public static List<Categoria> findCategoriasByInstalacion(HttpServletRequest request)
    {
        List<Categoria> arrCategorias = new ArrayList<Categoria>();
        List<RelCategoriaInstalacion> arrRelcategorias = new ArrayList<RelCategoriaInstalacion>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT rc FROM RelCategoriaInstalacion rc WHERE rc.instalacion.id = " + instalacionDB.getId() + " AND rc.activo = true";
            arrRelcategorias = dao.DAOEclipse.findAllByJPQL(jpql);

            for(RelCategoriaInstalacion relLoop : arrRelcategorias)
            {
                arrCategorias.add(relLoop.getCategoria());
            }
            
            Collections.sort(arrCategorias);
            
        }
        
        return arrCategorias;
    }
    @RequestMapping(value = "findAllCategoriasByInstalacion")
    public static List<Categoria> findAllCategoriasByInstalacion(@RequestParam(value = "soloReales",defaultValue = "false") boolean soloReales,HttpServletRequest request)
    {
        List<Categoria> arrCategorias = new ArrayList<Categoria>();
        List<Categoria> arrCategoriasAux = new ArrayList<Categoria>();
        List<RelCategoriaInstalacion> arrRelcategorias = new ArrayList<RelCategoriaInstalacion>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT rc FROM RelCategoriaInstalacion rc WHERE rc.instalacion.id = " + instalacionDB.getId() + " AND rc.activo = true";
            arrRelcategorias = dao.DAOEclipse.findAllByJPQL(jpql);

            for(RelCategoriaInstalacion relLoop : arrRelcategorias)
            {
                arrCategoriasAux.add(relLoop.getCategoria());
            }
            
            Collections.sort(arrCategoriasAux);
         
            if(!soloReales)
            {
//                Categoria categoriaTodos = new Categoria(true, "Todos", wsFotoProducto.getFotoUsuarioDefault());
                Categoria categoriaTodos = new Categoria(true, "Todos", new Foto());
                categoriaTodos.setId(999);
                arrCategorias.add(categoriaTodos);
                arrCategorias.addAll(arrCategoriasAux);
            }
        }
        
        return arrCategorias;
    }
    
    @RequestMapping(value = "findCategoriasByInstalacionConProductos")
    public static List<Categoria> findCategoriasByInstalacionConProductos(HttpServletRequest request)
    {
        List<Categoria> arrCategorias = new ArrayList<Categoria>();
        List<RelCategoriaInstalacion> arrRelcategorias = new ArrayList<RelCategoriaInstalacion>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT rc FROM RelCategoriaInstalacion rc WHERE rc.instalacion.id = " + instalacionDB.getId() + " AND rc.activo = true";
            arrRelcategorias = dao.DAOEclipse.findAllByJPQL(jpql);

            
            for(RelCategoriaInstalacion relLoop : arrRelcategorias)
            {
                if(relLoop != null)
                {
                    Categoria categoria = relLoop.getCategoria();
                    
                    if(categoria != null)
                    {
                        if(categoria.getActivo() == true && relLoop.getActivo())
                        {
                            arrCategorias.add(categoria);
                        }
                    }
                }
            }
            
            
            Collections.sort(arrCategorias);
            
            arrCategorias.add(dameCategoriaOfertas(request));
        }
        
        return arrCategorias;
    }
    private static Categoria dameCategoriaOfertas(HttpServletRequest request)
    {
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        Categoria categoriaOfertas  = null;
        
        if(instalacionDB != null)
        {
            categoriaOfertas = new Categoria(true, "Ofertas", new Foto("oferta.jpg",instalacionDB,true));
            categoriaOfertas.setIcono("fas fa-percentage");
        }
        return categoriaOfertas;
    }
    
    @RequestMapping(value = "guardarCategoria")
    public static boolean guardarCategoria
    (
        @RequestParam(value = "id" , defaultValue = "-1") int id,
        @RequestParam(value = "nombre" , required = true) String nombre,
        @RequestParam(value = "icono" , defaultValue = ""   ,required = false) String icono,
        @RequestParam(value = "strFoto" , required = true) String strFoto,
        HttpServletRequest request
    )
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        Categoria categoriaDB = null;
        
        Foto fotoJSON = new Gson().fromJson(strFoto, Foto.class);
        if(fotoJSON != null && instalacionDB != null)
        {
            fotoJSON.setInstalacion(instalacionDB);
        }
        
        if(id != -1)
        {
            categoriaDB = wsCategoria.getCategoria(id, request);
        }
        nombre = MasterController.primeraMayus(nombre);
        
        if(categoriaDB != null)
        {
            // MODO EDIT:
            categoriaDB.setNombre(nombre);
            if(fotoJSON != null)
            {
                categoriaDB.setFoto(fotoJSON);
                
                if(icono != null)
                {
                    categoriaDB.setIcono(icono);
                }
            }

        }
        else
        {
            // 3 - MODO ADD:
            categoriaDB = new Categoria(true, nombre, fotoJSON);
            categoriaDB.setIcono(icono);
        }

        guarde = dao.DAOEclipse.update(categoriaDB);
        
            
        
        return guarde;
    }
    
    @RequestMapping(value = "getCategoria")
    public static Categoria getCategoria
    (
        @RequestParam(value = "fkCategoria" , defaultValue = "-1") int fkCategoria,
        HttpServletRequest request)
    {
        Categoria categoriaDB = null;
        
        if(fkCategoria != -1)
        {
            categoriaDB = (Categoria) dao.DAOEclipse.get(Categoria.class, fkCategoria);
        }
        if(fkCategoria == 0)
        {
            categoriaDB = dameCategoriaOfertas(request);
            
            
            // ASOCIO LOS PRODUCTOS FAVORITOS A OFERTAS:
//            categoriaDB.setArrProductos(wsProducto.findProductosFavoritos(request));
        }
        
//        wsClick.addClick(request, -1, fkCategoria, -1);
        
        return categoriaDB;
    }
    public static Categoria getCategoriaX
    (
        @RequestParam(value = "fkCategoria" , defaultValue = "-1") int fkCategoria)
    {
        Categoria categoriaDB = null;
        
        if(fkCategoria != -1)
        {
            categoriaDB = (Categoria) dao.DAOEclipse.get(Categoria.class, fkCategoria);
        }
        return categoriaDB;
    }
    @RequestMapping(value = "getCategoriaByNombre")
    public static Categoria getCategoriaByNombre(@RequestParam(value = "nombre", required =  true) String nombre)
    {
        String jpql = "SELECT c FROM Categoria c WHERE lower(c.nombre) = lower(\"" + nombre + "\")";
        
        Categoria categoriaDB = (Categoria) dao.DAOEclipse.getByJPQL(jpql);
        
        return categoriaDB;
    }
    public static List<Categoria> arrCategoriasFast;
    @RequestMapping(value = "getCategoriaByNombreFast")
    public static Categoria getCategoriaByNombreFast(@RequestParam(value = "nombre", required =  true) String nombre)
    {
        
        Categoria categoriaRta = null;
        
        nombre = nombre.toLowerCase();
        
        //INICIALIZO EL ARRAY DE CATEGORIAS FAST:
        if(arrCategoriasFast == null)
        {
            arrCategoriasFast = findAllCategorias();
        }
        
        for(Categoria categoriaLoop : arrCategoriasFast)
        {
            if(categoriaLoop.getNombre().toLowerCase().contains(nombre))
            {
                categoriaRta = categoriaLoop;
                break;
            }
        }
        
        return categoriaRta;
    }

    public static Categoria crearCategoria(String nombreCategoria , HttpServletRequest request)
    {
//        Foto fotoDefault = wsFotoProducto.getFotoUsuarioDefault();
        Foto fotoDefault = null;
        Categoria categoriaNva = new Categoria(true, nombreCategoria, fotoDefault);
        categoriaNva.setIcono("");
        
        return (Categoria) dao.DAOEclipse.updateReturnObj(categoriaNva);
    }
    @RequestMapping(value = "getCategoriaEmpty")
    public Categoria getCategoriaEmpty()
    {
       Categoria categoriaEmpty = new Categoria();
       categoriaEmpty.setId(-1);
       return categoriaEmpty;
    }
}
