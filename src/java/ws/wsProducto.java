package ws;
import archivos.FilaExcelNico;
import com.google.gson.Gson;
import controller.MasterController;
import java.io.File;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.EncodeException;
import modelo.*;
import modelo.FotoProducto;
import modelo.OpcProducto;
import modelo.Producto;
import modelo.RelHashProducto;
import org.springframework.web.bind.annotation.*;
import parsers.ParserImportExcelOpcProd;

@RestController
public class wsProducto
{
    @RequestMapping(value = "findProductosByInstalacion")
    public static List<Producto> findProductosByInstalacion
    (
        @RequestParam(value = "busqueda" , required = false) String busqueda,
        @RequestParam(value = "fkCategoria" , defaultValue = "-1") int fkCategoria,
        @RequestParam(value = "soloActivos" , defaultValue = "false") boolean soloActivos,
        HttpServletRequest request
    )
    {
        List<Producto> productosList = new ArrayList<Producto>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);

        if(instalacionDB != null)
        {
            String jpql = "SELECT p FROM Producto p WHERE p.instalacion.id = " + instalacionDB.getId() + " AND p.eliminado = false";
            
            
            if(busqueda != null && busqueda.trim().length() > 0)
            {
                busqueda = busqueda.toLowerCase();
                
                jpql += " AND (LOWER(p.nombre) LIKE \"%" + busqueda + "%\" OR LOWER(p.subtitulo) LIKE \"%" + busqueda + "%\")";
            }
            if(fkCategoria != -1 && fkCategoria != 0 && fkCategoria != 999)
            {
                jpql +=  " AND p.categoria.id = " + fkCategoria;
            }
            if(fkCategoria == 0)
            {
                jpql +=  " AND p.soyFavorito = true ";
            }
            if(soloActivos)
            {
                jpql +=  " AND p.activo = true";
            }
            
            jpql += " order by p.orden";
            
            
            System.out.println("JPQL = " + jpql);
            productosList = dao.DAOEclipse.findAllByJPQL(jpql);
        }
       
        
        return productosList;
    }
    @RequestMapping(value = "findProductosByInstalacionLimit")
    public static List<Producto> findProductosByInstalacionLimit
    (
        @RequestParam(value = "busqueda" , required = false , defaultValue = "-1") String busqueda,
        @RequestParam(value = "fkCategoria" , defaultValue = "-1") int fkCategoria,
        @RequestParam(value = "soloActivos" , defaultValue = "false") boolean soloActivos,
        @RequestParam(value = "offset" , defaultValue = "0") int offset,
        HttpServletRequest request
    )
    {
        int limite = 50;
        List<Producto> arrProductos = new ArrayList<Producto>();
        
        if(busqueda.equals("-1") || busqueda.trim().length() == 0)
        {
            busqueda = null;
        }
        if(fkCategoria == -1 || fkCategoria == 999)
        {
            fkCategoria = -1;
        }
        
        List<Producto> arrProductosAux = findProductosByInstalacion(busqueda, fkCategoria, soloActivos, request);
        
        int cantProductos = arrProductosAux.size();
        System.out.println("CANTIDAD DE PRODUCTOS: " + cantProductos);
        
        int indice = limite * offset;
        int indiceFinal = limite * (offset + 1);

        if(cantProductos == 0)
        {
            System.out.println("ENTRE 1");
            indice = 0;
            indiceFinal = 0;
            
        }
        else if( cantProductos < limite && offset == 0)
        {
            System.out.println("ENTRE 2");
            indice = 0;
            indiceFinal = indice + cantProductos;
            
            arrProductos = arrProductosAux.subList(indice, indiceFinal);
        }
        else if(cantProductos < limite && offset > 0)
        {
            // NO HAGO NADA, CORTO ACA, TENGO PRODUCTOS REPETIDOS:
            System.out.println("ENTRE 4");
            
        }
        else if(indice < cantProductos -1)
        {
            System.out.println("ENTRE 3");
            if(indiceFinal > cantProductos)
            {
                indiceFinal = cantProductos - 1;
            }
            
            arrProductos = arrProductosAux.subList(indice, indiceFinal);
        }
        
        System.out.println("cantProductos:" + cantProductos);
        System.out.println("indice:" + indice);
        System.out.println("indiceFinal:" + indiceFinal);
//        arrProductos = arrProductosAux.subList(indice, indiceFinal);
        
        return arrProductos;
    }
    @RequestMapping(value = "findProductosFavoritos")
    public static List<Producto> findProductosFavoritos
    (
        HttpServletRequest request
    )
    {
        List<Producto> productosList = new ArrayList<Producto>();
        
        int cantProductosAleatorios = 18;
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);

        if(instalacionDB != null)
        {
            String jpql = "SELECT p FROM Producto p WHERE p.instalacion.id = " + instalacionDB.getId() +  " AND p.soyFavorito = true AND p.activo = true AND p.eliminado = false order by p.orden";
            
            productosList = dao.DAOEclipse.findAllByJPQLLimitado(jpql, 0 , 50);

        }
        
        
        // COMPLETANDO CON PRODUCTOS ALEATORIOS:
        List<Producto> arrAllProductos = findProductosByInstalacion(null, -1, true, request);
        int reintentos = 0;
        if(arrAllProductos != null && arrAllProductos.size() > 0)
        {
            for(int i = 0 ; (i < cantProductosAleatorios  || reintentos > 100) ;i++)
            {
                if(productosList.size() < (i + 1))
                {
                    int random = (int) (Math.random() * arrAllProductos.size());
                    
                    Producto productoAleatorio = arrAllProductos.get(random);
                    productosList.add(productoAleatorio);
                }
                reintentos++;
            }
        }
        
        
        //PONGO LAS CANTIDADES QUE TENGO EN EL CARRITO DE LOS PRODUCTOS QUE ESTOY MOSTRANDO:
//        Pedido pedido = wsPedido.damePedidoByCodSesion(request);
//        
//        if(pedido != null)
//        {
//            for(DetPedido detLoop : pedido.getArrDetsPedido())
//            {
//                OpcProducto opc = detLoop.getOpcProducto();
//                
//                if(opc != null)
//                {
//                    Producto productoCarritoLoop = opc.getProducto();
//                    
//                    if(productoCarritoLoop != null)
//                    {
//                        for(Producto productoLoop  : productosList)
//                        {
//                            if(productoCarritoLoop.getId() == productoLoop.getId())
//                            {
//                                productoLoop.setEstoyLlevando(true);
//                                productoLoop.setCantidad(detLoop.getCantidad());
//                                productoLoop.setUltimaUnidadSeleccionada(opc);
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//        }
        
//        Collections.sort(productosList);
        
        return productosList;
    }
    @RequestMapping(value = "findProductosAsociados")
    public static List<Producto> findProductosAsociados
    (
        @RequestParam(value = "fkProducto" , defaultValue = "-1") int fkProducto,
        HttpServletRequest request
    )
    {
        List<Producto> productosList = new ArrayList<Producto>();
        
        int cantProductosAleatorios =  6;
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);

        if(instalacionDB != null)
        {
            String jpql = "SELECT p FROM Producto p WHERE p.instalacion.id = " + instalacionDB.getId() +  " AND p.soyFavorito = true AND p.activo = true AND p.eliminado = false order by p.orden";
            
            productosList = dao.DAOEclipse.findAllByJPQL(jpql);

        }
        
        
        // COMPLETANDO CON PRODUCTOS ALEATORIOS:
        List<Producto> arrAllProductos = findProductosByInstalacion(null, -1, true, request);
        
        if(arrAllProductos != null && arrAllProductos.size() > 0)
        {
            for(int i = 0 ; i < cantProductosAleatorios ;i++)
            {
                if(productosList.size() < (i + 1))
                {
                    int random = (int) (Math.random() * arrAllProductos.size());
                    
                    Producto productoAleatorio = arrAllProductos.get(random);
                    productosList.add(productoAleatorio);
                }
            }
        }
        
        
//        Collections.sort(productosList);
        
        return productosList;
    }
    @RequestMapping(value = "findProductosByNombre")
    public static List<Producto> findProductosByNombre(@RequestParam(value = "nombre" , defaultValue = "") String nombre , HttpServletRequest request)
    {
        List<Producto> productosList = new ArrayList<Producto>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT p FROM Producto p WHERE (p.nombre LIKE '%" + nombre +"%' OR p.subtitulo LIKE '%" + nombre +"%') AND p.instalacion.id = "  + instalacionDB.getId() +  " AND p.eliminado = false AND p.activo =  true order by p.orden";
            productosList = dao.DAOEclipse.findAllByJPQL(jpql);
        }
        
        return productosList;
    }
    
    @RequestMapping(value = "guardarProducto", method = {RequestMethod.POST,RequestMethod.GET})
    public static boolean guardarProducto
    (
        @RequestParam(value = "strProducto" , defaultValue = "") String strProducto,
        HttpServletRequest request
    )
    {
        boolean guarde = false;
        boolean modoEdit = false;
    
        Producto productoDB = null;
        Categoria categoriaDB = null;
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        strProducto = archivos.Encoding.fix(strProducto);
        
        if(strProducto != null)
        {
            try
            {
                Producto productoRecibido = new Gson().fromJson(strProducto, Producto.class);

                if(productoRecibido != null)
                {
                    if(productoRecibido.fkCategoria != -1)
                    {
                        categoriaDB = wsCategoria.getCategoria(productoRecibido.fkCategoria , request);
                    }

                    if(instalacionDB != null && categoriaDB != null)
                    {
                        // MODO EDIT:
                        if(productoRecibido.getId() != -1)
                        {
                            productoDB = (Producto) dao.DAOEclipse.get(Producto.class, productoRecibido.getId());

                            if(productoDB != null)
                            {
                                // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                                productoDB.setCodigo(productoRecibido.getCodigo());
                                productoDB.setNombre(productoRecibido.getNombre());
                                productoDB.setSubtitulo(productoRecibido.getSubtitulo());
                                productoDB.setDescripcion(productoRecibido.getDescripcion());
                                productoDB.setInstalacion(productoRecibido.getInstalacion());
                                productoDB.setInstalacion(instalacionDB);
                                productoDB.setCategoria(categoriaDB);
                                productoDB.setOrden(productoRecibido.getOrden());

                                // ---- ARRRELSHASHSS ----
                                // 1 - BORRO TODAS/OS LAS/LOS ARRRELSHASHSS DE ANTES:
                                boolean borreTodos1 = true;
                                for(RelHashProducto arrRelsHashsLoop: productoDB.getArrRelsHashs())
                                {
                                    arrRelsHashsLoop.setActivo(false);
                                    if(!dao.DAOEclipse.update(arrRelsHashsLoop))
                                    {
                                        borreTodos1 = false;
                                    }
                                }

                                // 2 - ASOCIO LAS/LOS NUEVAS/OS ARRRELSHASHSS:
                                productoDB.setArrRelsHashs(productoRecibido.getArrRelsHashs());


                                // ---- ARRFOTOSPRODUCTOS ----
                                // 2 - BORRO TODAS/OS LAS/LOS ARRFOTOSPRODUCTOS DE ANTES:
                                boolean borreTodos2 = true;
                                for(FotoProducto arrFotosProductoLoop: productoDB.getArrFotosProducto())
                                {
                                    arrFotosProductoLoop.setActivo(false);
                                    if(!dao.DAOEclipse.update(arrFotosProductoLoop))
                                    {
                                        borreTodos2 = false;
                                    }
                                }

                                // 3 - ASOCIO LAS/LOS NUEVAS/OS ARRFOTOSPRODUCTOS:
                                productoDB.setArrFotosProducto(productoRecibido.getArrFotosProducto());


                                // ---- ARROPCPRODUCTOSS ----
                                // 3 - BORRO TODAS/OS LAS/LOS ARROPCPRODUCTOSS DE ANTES:
                                boolean borreTodos3 = true;
                                for(OpcProducto arrOpcProductosLoop: productoDB.getArrOpcProductos())
                                {
                                    arrOpcProductosLoop.setActivo(false);
                                    if(!dao.DAOEclipse.update(arrOpcProductosLoop))
                                    {
                                        borreTodos3 = false;
                                    }
                                }

                                // 4 - ASOCIO LAS/LOS NUEVAS/OS ARROPCPRODUCTOSS:
                                productoDB.setArrOpcProductos(productoRecibido.getArrOpcProductos());

                                int fkProveedor = productoRecibido.getFkProveedor();
                            
                                if(fkProveedor != -1)
                                {
                                    Proveedor proveedorDB = wsProveedor.getProveedor(0, request);

                                    if(proveedorDB != null)
                                    {
                                        productoDB.setProveedor(proveedorDB);
                                    }
                                }
                                
                                productoDB.setActivo(productoRecibido.getActivo());
                                
                                if(borreTodos1 && borreTodos2 && borreTodos3)
                                {
                                    guarde = dao.DAOEclipse.update(productoDB);
                                }
                            }
                        }
                        else
                        {
                            // 3 - MODO ADD:

                            // ---- ASOCIO RELHASHPRODUCTOS ----
                            for(RelHashProducto relHashProductoLoop: productoRecibido.getArrRelsHashs())
                            {
                                relHashProductoLoop.setProducto(productoRecibido);
                            }

                            // ---- ASOCIO FOTOPRODUCTOS ----
                            for(FotoProducto fotoProductoLoop: productoRecibido.getArrFotosProducto())
                            {
                                if(fotoProductoLoop != null)
                                {
                                    if(fotoProductoLoop.getFoto() != null)
                                    {
                                        fotoProductoLoop.setProducto(productoRecibido);
                                        fotoProductoLoop.getFoto().setInstalacion(instalacionDB);
                                    }
                                }
                            }

                            // ---- ASOCIO OPCPRODUCTOS ----
                            for(OpcProducto opcProductoLoop: productoRecibido.getArrOpcProductos())
                            {
                                opcProductoLoop.setProducto(productoRecibido);
                            }
                            
                            productoRecibido.setCategoria(categoriaDB);
                           
                            int fkProveedor = productoRecibido.fkProveedor;
                            if(fkProveedor != -1)
                            {
                                Proveedor proveedorDB = wsProveedor.getProveedor(fkProveedor, request);

                                if(proveedorDB != null)
                                {
                                    productoRecibido.setProveedor(proveedorDB);
                                }
                            }
                            productoRecibido.setInstalacion(instalacionDB);
                            Producto productoGuardado = (Producto) dao.DAOEclipse.updateReturnObj(productoRecibido);
                            
                            if(productoGuardado != null)
                            {
                                int fk = productoGuardado.getId();
                                wsProducto.guardarCodigoFast(fk,"" + fk , request);
                                guarde = true;
                            }

                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
            Thread t = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    actualizarCategoriasActivas(request);
                }
            });
            t.start();

        }
        
        return guarde;
    }
    @RequestMapping(value = "guardarPrecioFast")
    public static double guardarPrecioFast
    (
        @RequestParam(value = "fkOpcProd" ,defaultValue = "-1", required = false) int fkOpcProd,
        @RequestParam(value = "fkUnidad" , defaultValue = "-1", required = false) int fkUnidad,
        @RequestParam(value = "fkProducto" , defaultValue = "-1", required = false) int fkProducto,
        @RequestParam(value = "nvoPrecio" , required = true) double nvoPrecio,
        @RequestParam(value = "costo" , required = true) double costo,
        @RequestParam(value = "valorFloat", defaultValue = "1", required = false) double valorFloat,
        @RequestParam(value = "porcentaje" , required = true) double porcentaje,
        @RequestParam(value = "tildado" , defaultValue = "false") boolean tildado,
        HttpServletRequest request
    )
    {
        double precioRta = 0;
        
        OpcProducto opcProducto = null;

        if(fkOpcProd == -1 && fkUnidad != -1 && fkProducto != -1)
        {
            Unidad unidadDB = wsUnidad.getUnidad(fkUnidad, request);
            Producto productoDB = wsProducto.getProducto(fkProducto);
            
            if(productoDB != null)
            {
                opcProducto = wsOpcProducto.crearNuevaOpcByUnidad(unidadDB , productoDB, nvoPrecio , costo,porcentaje , valorFloat);
            }
        }
        else
        {
            if(fkOpcProd != -1 )
            {
                opcProducto = wsOpcProducto.getOpcProducto(fkOpcProd);
            }
        }
        
        if(opcProducto != null)
        {
            // VERIFICO, SI VIENE TILDADO, ENTONCES ES AUTOMATICO:
            if(tildado)
            {
                opcProducto.setPrecio(0);
                opcProducto.setPrecioCosto(costo);
                opcProducto.setPorcentajeGanancia(porcentaje);
                opcProducto.setValorFloat(valorFloat);
            }
            else
            {
                opcProducto.setPrecio(nvoPrecio);
                opcProducto.setPrecioCosto(0);
                opcProducto.setPorcentajeGanancia(0);
                opcProducto.setValorFloat(valorFloat);
            }

            if(dao.DAOEclipse.update(opcProducto))
            {
                precioRta = opcProducto.getPrecio();
            }
        }
        
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                actualizarCategoriasActivas(request);
            }
        });
        t.start();
        
        return precioRta;
    }
    @RequestMapping(value = "guardarOrdenFast")
    public static boolean guardarOrdenFast
    (
        @RequestParam(value = "fkProducto" , required = true) int fkProducto,
        @RequestParam(value = "orden" , required = true) int orden,
        HttpServletRequest request
    )
    {
        boolean guarde = false;
    
//        Instalacion instalacionDB = MasterController.dameInstalacionSegunURL(request);
        
        if(fkProducto != -1)
        {
            Producto productoDB = getProducto(fkProducto);
            
            if(productoDB != null)
            {
                productoDB.setOrden(orden);
                guarde = dao.DAOEclipse.update(productoDB);
            }
        }

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                actualizarCategoriasActivas(request);
            }
        });
        t.start();
        
        
        return guarde;
    }
    @RequestMapping(value = "guardarCodigoFast")
    public static boolean guardarCodigoFast
    (
        @RequestParam(value = "fkProducto" , required = true) int fkProducto,
        @RequestParam(value = "codigo" , required = true) String codigo,
        HttpServletRequest request
    )
    {
        boolean guarde = false;
    
//        Instalacion instalacionDB = MasterController.dameInstalacionSegunURL(request);
        
        if(fkProducto != -1)
        {
            Producto productoDB = getProducto(fkProducto);
            
            if(productoDB != null)
            {
                productoDB.setCodigo(codigo);
                guarde = dao.DAOEclipse.update(productoDB);
            }
        }

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                actualizarCategoriasActivas(request);
            }
        });
        t.start();
        
        
        return guarde;
    }
    @RequestMapping(value = "guardarCategoriaFast", method = {RequestMethod.POST,RequestMethod.GET})
    public static boolean guardarCategoriaFast
    (
        @RequestParam(value = "fkProducto" , required = true) int fkProducto,
        @RequestParam(value = "fkCategoria" , required = true) int fkCategoria,
        HttpServletRequest request
    )
    {
        boolean guarde = false;
    
//        Instalacion instalacionDB = MasterController.dameInstalacionSegunURL(request);
        
        if(fkProducto != -1)
        {
            Producto productoDB = getProducto(fkProducto);
            
            if(productoDB != null && fkCategoria != -1)
            {
                Categoria categoriaDB = wsCategoria.getCategoria(fkCategoria, request);
                
                if(categoriaDB != null)
                {
                    productoDB.setCategoria(categoriaDB);
                    guarde = dao.DAOEclipse.update(productoDB);
                }
            }
        }

        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                actualizarCategoriasActivas(request);
            }
        });
        t.start();
        
        
        return guarde;
    }
    
    @RequestMapping(value = "getProducto")
    public static Producto getProducto(@RequestParam(value = "idProducto" , defaultValue = "-1") int idProducto)
    {
        Producto productoDB = null;
        
        
        
        if(idProducto != -1)
        {
            productoDB = (Producto) dao.DAOEclipse.get(Producto.class, idProducto);
        }
        
        
        
        return productoDB;
    }
    @RequestMapping(value = "getProductoEmpty")
    public static Producto getProductoEmpty(@RequestParam(value = "fkCategoria" , defaultValue = "-1") int fkCategoria , HttpServletRequest request)
    {
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        Producto productoEmpty = new Producto();
        productoEmpty.setId(-1);
        productoEmpty.setCodigo("-1");
        productoEmpty.setOrden(999);
        productoEmpty.setActivo(true);
    
        OpcProducto opcProductoDefault = wsOpcProducto.getOpcProductoDefault(request);
        
        if(fkCategoria != -1)
        {
            Categoria categoriaDB = wsCategoria.getCategoria(fkCategoria, request);
            
            if(categoriaDB != null)
            {
                productoEmpty.setCategoria(categoriaDB);
            }
        }
        
        if(instalacionDB != null)
        {
            productoEmpty.setInstalacion(instalacionDB);
//            Foto fotoDefault = new Foto("default.png", instalacionDB, true);
//            productoEmpty.addFotoProducto(new FotoProducto(fotoDefault, productoEmpty, 1, true));
        }
        
        productoEmpty.addOpcProducto(opcProductoDefault);
       
       
       return productoEmpty;
    }
    @RequestMapping(value = "cambiarVisibilidadProducto")
    public static boolean cambiarVisibilidadProducto(@RequestParam(value = "fkProducto" ,required = true)int fkProducto, HttpServletRequest request)
    {
        boolean ok = false;
        
        Producto productoDB = getProducto(fkProducto);

        if(productoDB != null)
        {
            boolean visibilidad = productoDB.getActivo();
            
            visibilidad = !visibilidad;
            
            productoDB.setActivo(visibilidad);
            
            ok = dao.DAOEclipse.update(productoDB);
        }
        
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                actualizarCategoriasActivas(request);
            }
        });
                
        t.start();
//                actualizarCategoriasActivas(request);
       
       
       return ok;
    }
    
    @RequestMapping(value = "addRmFavorito")
    public static boolean addRmFavorito(@RequestParam(value = "fkProducto" ,required = true)int fkProducto, HttpServletRequest request)
    {
        boolean ok = false;
        
        Producto productoDB = getProducto(fkProducto);

        if(productoDB != null)
        {
            boolean favorito = productoDB.isSoyFavorito();
            
            favorito = !favorito;
            
            productoDB.setSoyFavorito(favorito);
            
            ok = dao.DAOEclipse.update(productoDB);
        }
       
       
       return ok;
    }
    @RequestMapping(value = "ordenTodos999")
    public static boolean ordenTodos999
    (
        HttpServletRequest request
    )
    {
        List<Producto> arrProductos = findProductosByInstalacion(null, -1, false, request);
        
        for(Producto productoLoop : arrProductos)
        {
            productoLoop.setOrden(999);
            dao.DAOEclipse.update(productoLoop);
        }
        
        return true;
    }
//    @RequestMapping(value = "codigoTodosId")
//    public static boolean codigoTodosId
//    (
//        HttpServletRequest request
//    )
//    {
//        List<Producto> arrProductos = findProductosByInstalacion(-1, false, request);
//        
//        for(Producto productoLoop : arrProductos)
//        {
//            int id = productoLoop.getId();
//            productoLoop.setCodigo(id);
//            dao.DAOEclipse.update(productoLoop);
//        }
//        
//        return true;
//    }
    
    public static boolean actualizarCategoriasActivas(HttpServletRequest request)
    {
        boolean actualizeCategorias = true;
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            String jpql = "SELECT p FROM Producto p WHERE p.instalacion.id = " + instalacionDB.getId();
            List<Producto> arrAllProductosInstalacion =  dao.DAOEclipse.findAllByJPQL(jpql);
            List<Categoria> arrAllCategorias =  wsCategoria.findAllCategorias();
            
            if(arrAllProductosInstalacion != null && arrAllCategorias != null)
            {
                for(Categoria categoriaLoop : arrAllCategorias)
                {
                    boolean tengoAlgunoEnEstaCategoria = false;
                    
                    for(Producto productoLoop : arrAllProductosInstalacion)
                    {
                        if(productoLoop.getCategoria().getId() == categoriaLoop.getId())
                        {
                            tengoAlgunoEnEstaCategoria = true;
                            break;
                        }
                    }
                    
//                    RelCategoriaInstalacion relDB = null;
//                    if(tengoAlgunoEnEstaCategoria)
//                    {
//                        relDB = wsRelCategoriaInstalacion.getRelCategoriaInstalacion(categoriaLoop.getId(), instalacionDB.getId() , request);
//                        
//                        if(relDB != null)
//                        {
//                            relDB.setActivo(true);
//                        }
////                        categoriaLoop.setActivo(true);
//                    }
//                    else
//                    {
//                        relDB = wsRelCategoriaInstalacion.getRelCategoriaInstalacion(categoriaLoop.getId(), instalacionDB.getId() ,request);
//                        if(relDB != null)
//                        {
//                            relDB.setActivo(false);
//                        }
//                    }
                    
//                    if(relDB != null)
//                    {
//                        if(!dao.DAOEclipse.update(relDB))
//                        {
//                            actualizeCategorias = false;
//                        }
//                    }
                }
            }
        }
        
        return actualizeCategorias;
    }
    
    
     @RequestMapping(value = "eliminarProdDefinitivo")
    public static boolean eliminarProdDefinitivo(@RequestParam(value = "fkProducto" ,required = true)int fkProducto, HttpServletRequest request)
    {
        boolean ok = false;
        
        Producto productoDB = getProducto(fkProducto);

        if(productoDB != null)
        {
            productoDB.setEliminado(true);
            
            ok = dao.DAOEclipse.update(productoDB);
        }
        
        Thread t = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                actualizarCategoriasActivas(request);
            }
        });
                
        t.start();
//                actualizarCategoriasActivas(request);
       
       
       return ok;
    }
    @RequestMapping(value = "fixOpcProductoZero")
    public static boolean fixOpcProductoZero()
    {
        List<OpcProducto> arrOpcs = wsOpcProducto.findOpcProductos();
        
        for(OpcProducto opcLoop : arrOpcs)
        {
            if(opcLoop.getValorFloat() < 1)
            {
                opcLoop.setValorFloat(1);
                dao.DAOEclipse.update(opcLoop);
            }
        }
        
        return true;
    }
    
    
    @RequestMapping(value = "fixProveedorProducto")
    public static boolean fixProveedorProducto(HttpServletRequest request)
    {
        List<Producto> arrProductos = wsProducto.findProductosByInstalacion(null, -1, false, request);
        
        Proveedor proveedorDefault = wsProveedor.getProveedorDefault(request);
        
        for(Producto productoLoop : arrProductos)
        {
            productoLoop.setProveedor(proveedorDefault);
            dao.DAOEclipse.update(productoLoop);
        }
        
        return true;
    }
    
//    @RequestMapping(value = "exportarProductosExcel")
//    public static String exportarProductosExcel
//    (
//        @RequestParam(value = "fkCategoria" , defaultValue = "-1") int fkCategoria, 
//        HttpServletRequest request
//    )
//    {
//        String url = "";
//        
//        
//        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
//        
//        if(instalacionDB != null)
//        {
//            Date ahora = new Date();
//            String prefijoCarpeta = "excel";
//            String nombreArchivo = ahora.getDate() + "-" + (ahora.getMonth()+1) + "-" + (1900 + ahora.getYear()) + "--" + ahora.getHours() + "-"+ ahora.getMinutes() +".xlsx";
//            String nombreArchivoFS = MasterController.dameRutaFS(prefijoCarpeta + File.separator + nombreArchivo, instalacionDB);
//
//            List<Producto> arrProductos = findProductosByInstalacion(null, fkCategoria,  true, request);
//            List<FilaExcelNico> arrFilasExcel = new ArrayList<>();
//            
//            // ORDENO LOS PRODUCTOS POR COD.PROD:
//            arrProductos.sort(new Comparator<Producto>()
//            {
//                @Override
//                public int compare(Producto p, Producto p2)
//                {
//                    int orden = 0;
//                    
//                    if(p.getCodigoInt() <= p2.getCodigoInt())
//                    {
//                        orden = -1;
//                    }
//                    else
//                    {
//                        orden = 1;
//                    }
//                    
//                    return orden;
//                }
//            });
//
//            // EMPIEZO A AGREGAR FILAS AL EXCEL - EMPEZANDO POR EL ENCABEZADO:
//            arrFilasExcel.add(new FilaExcelNico("CODPROD|OPCPROD|DESCRIPCION|ENVASE|UNIDAD|CANT_X_PACK|PRECIO_COSTO|PORCENTAJE|PRECIO_AUTO|PRECIO_FIJO|CATEGORIA|PROVEEDOR|STOCK|"));
//            
//            if(arrProductos != null)
//            {
//                
//                int i = 2;
//                for(Producto productoLoop : arrProductos )
//                {
//                    if(productoLoop != null)
//                    {
//                        if(productoLoop.getCategoria() != null)
//                        {
//                            String nombreCat = productoLoop.getCategoria().getNombre();
//                            String codigoProducto = productoLoop.getCodigo();
//                            
//                            for(OpcProducto opcLoop : productoLoop.getArrOpcProductos())
//                            {
//                                Proveedor proveedor =  opcLoop.getProducto().getProveedor();
//                                
//                                if(opcLoop != null && proveedor != null)
//                                {
//                                    Unidad unidad = opcLoop.getUnidad();
//                                    
//                                    if(unidad != null)
//                                    {
//                                        String descripcion = productoLoop.getNombre();
//                                        String envase = + opcLoop.getValorFloat() + " " + opcLoop.getUnidad().getNombreUnidadSingular();
//                                        String formula = "=SUM(G"+ i +"+(G"+ i +"*(H" + i + "/100)))";
//                                        String agregadoPrecio = "";
//                                        
//                                        if(opcLoop.soyPrecioAutomatico())
//                                        {
//                                            agregadoPrecio = (int)(opcLoop.getPrecioCosto()) + "|" + (int)(opcLoop.getPorcentajeGanancia()) + "|" + formula + "||";
////                                            agregadoPrecio = "|" + opcLoop.getPrecioCosto() + "|" + opcLoop.getPorcentajeGanancia() + "|" + opcLoop.getPrecio() + "||";
//                                        }
//                                        else
//                                        {
//                                            agregadoPrecio = "0|0|" + formula + "|"+ (int)(opcLoop.getPrecio()) +"|";
////                                            agregadoPrecio = "||||" + opcLoop.getPrecio() + "|";
//                                        }
//                                        
//                                        
//                                        if(proveedor != null)
//                                        {
//                                            String nombreProveedor = proveedor.getNombre();
//                                            String strFila =  codigoProducto + "|" + opcLoop.getId() +"|" + descripcion +"|" + envase +"|" + unidad.getNombrePre() +"|"+ (int)(opcLoop.getValorFloat()) + "|"  + agregadoPrecio + nombreCat +"|" +  nombreProveedor  + "|1";
////                                            String strFila = nombreCat +"|1|" + codigoProducto + "|" + opcLoop.getId() +"|" + descripcion +"|" +  nombreProveedor + "|" + opcLoop.getValorFloat() + "|" + unidad.getNombrePre() + agregadoPrecio;
////                                            String strFila = nombreCat +"|1|" + codigoProducto + "|" + opcLoop.getId() +"|" + descripcion +"|" +  nombreProveedor + "|" + opcLoop.getValorFloat() + "|" + unidad.getNombrePre() + agregadoPrecio;
//                                            arrFilasExcel.add(new FilaExcelNico(strFila));
//                                        }
//                                    }
//                                }
//                                i++;
//                            }
//                        }
//                    }
//                    
//                }
//                
//                System.out.println("ESCRIBIENDO EXCEL");
//                archivos.Axel.escribirAxel(nombreArchivoFS, "productos", arrFilasExcel);
//            }
//            
//            url = MasterController.dameConfigMaster().getUrlVisualizacion() +"/" +  instalacionDB.getCarpetaWeb()  +"/" + prefijoCarpeta+"/" + nombreArchivo;
//        }
//        
//        
//        return url;
//    }
//    @RequestMapping(value = "procesarExcelProductos", method = {RequestMethod.GET,RequestMethod.POST})
//    public static boolean procesarExcelProductos
//    (
//        @RequestParam(value = "url") String strUrl,
//        HttpServletRequest request
//    )
//    {
//        String subCarpeta = "";
//        String carpetaInternaDelProceso = "excel";
//        boolean ok = false;
//        List<Producto> arrProductos = new ArrayList<>();
//        
//        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
//        
//        if(instalacionDB != null)
//        {
//            subCarpeta = instalacionDB.getCarpetaWeb();
//            URLNico url = new Gson().fromJson(strUrl, URLNico.class);
//            
//            String rutaCarpetaEnDisco = "";
//            Configuracion config = MasterController.dameConfigMaster();
//            if( config != null)
//            {
//                rutaCarpetaEnDisco = config.getRutaFileSystem() + File.separator + subCarpeta + File.separator + carpetaInternaDelProceso;
//                
//                String rutaArchiDisco = rutaCarpetaEnDisco + File.separator + url.getUrlProvisoria();
//                
//                System.out.println("URL:" + rutaArchiDisco);
//                
//                List<ParserImportExcelOpcProd> arrOpcLeidos = archivos.Axel.parsearXLSX(rutaArchiDisco, 0 , 0 , "parsers.ParserImportExcelOpcProd", false);
//                System.out.println("LEI:" +  arrOpcLeidos.size() + "PARSER DE IMPORT EXCEL");
//                
//                
//                int i = 1; 
//                for(ParserImportExcelOpcProd parserLoop : arrOpcLeidos)
//                {
//                    if(i != 1)
//                    {
//                        System.out.println(i + " | " + parserLoop.toString());
//                        
//                        
//                        OpcProducto opcProdDB = null;
//                        
//                        // 1 - ANALIZAR EL OPC PROD:
//                        int IDopcProd = (int) Double.parseDouble(parserLoop.getOPCPROD());
//                        if(IDopcProd != -1)
//                        {
//                            opcProdDB = wsOpcProducto.getOpcProducto(IDopcProd);
//                            
//                            if(opcProdDB != null)
//                            {
//                                // 2 - PRECIO COSTO:
//                                String strPrecioCosto = parserLoop.getPRECIO_COSTO();
//                                if(strPrecioCosto != null && MasterController.esNumeric2(strPrecioCosto))
//                                {
//                                    double precioCosto = Double.parseDouble(strPrecioCosto);
//
//                                    if(precioCosto != -1)
//                                    {
//                                        opcProdDB.setPrecioCosto(precioCosto);
//                                    }
//                                }
//                                
//                                
//                                // 3 - PORCENTAJE:
//                                String strPorcentaje = parserLoop.getPORCENTAJE();
//                                boolean esNumericPorcentaje = MasterController.esNumeric2(strPorcentaje);
//                                if(strPorcentaje != null && esNumericPorcentaje)
//                                {
//                                    double porcentaje = Double.parseDouble(strPorcentaje);
//
//                                    if(porcentaje != -1)
//                                    {
//                                        opcProdDB.setPorcentajeGanancia(porcentaje);
//                                    }
//                                }
//                                
//                                // 3 - PRECIO FIJO:
//                                String strPrecioFijo = parserLoop.getPRECIO_FIJO();
//                                boolean esNumericPrecioFijo = MasterController.esNumeric2(strPrecioFijo);
//                                if(strPrecioFijo != null && esNumericPrecioFijo)
//                                {
//                                    double precioFijo = Double.parseDouble(strPrecioFijo);
//
//                                    if(precioFijo != -1)
//                                    {
//                                        opcProdDB.setPrecio(precioFijo);
//                                    }
//                                }
//                                
//                                // 4 - CANT X PACK:
//                                String strCantXPack = parserLoop.getCANT_X_PACK();
//                                if(strCantXPack != null && MasterController.esNumeric2(strCantXPack))
//                                {
//                                    double cantXPack = Double.parseDouble(strCantXPack);
//
//                                    if(cantXPack != -1)
//                                    {
//                                        opcProdDB.setValorFloat(cantXPack);
//                                    }
//                                }
//                                
//                                // 5 - DESCRIPCION:
//                                String descripcion = parserLoop.getDESCRIPCION();
//                                if(descripcion != null )
//                                {
//                                    opcProdDB.getProducto().setDescripcion(descripcion);
//                                }
//                                
//                                // 6 - CATEGORIA:
//                                String strCategoria = parserLoop.getCATEGORIA();
//                                Categoria categoriaDB = wsCategoria.getCategoriaByNombre(strCategoria);
//                                if(categoriaDB == null)
//                                {
//                                    categoriaDB = wsCategoria.crearCategoria(strCategoria , request);
//                                    
//                                }
//                                if(categoriaDB != null )
//                                {
//                                    opcProdDB.getProducto().setCategoria(categoriaDB);
//                                }
//                                
//                                // 7 - PROVEEDOR:
//                                String strProveedor = parserLoop.getPROVEEDOR();
//                                Proveedor proveedorDB = wsProveedor.getProveedorByNombre(strProveedor, request);
//                                if(proveedorDB == null)
//                                {
//                                    proveedorDB = wsProveedor.crearProveedor(strProveedor , request);
//                                }
//                                if(proveedorDB != null )
//                                {
//                                    opcProdDB.getProducto().setProveedor(proveedorDB);
//                                }
//                                
//                                
//                                dao.DAOEclipse.update(opcProdDB);
//                                ok = true;
//                            }
//                        }
//                        
//                        
//                        // 1 - ANALIZAR LA CATEGORIA
//                    }
//                    i++;
//                }
//            }
//            
//        }
//        
//        
//        return ok;
//    }
}
