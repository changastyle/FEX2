package ws;

import com.google.gson.Gson;
import controller.MasterController;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import modelo.Categoria;
import modelo.Configuracion;
import modelo.Instalacion;
import modelo.OpcProducto;
import modelo.Producto;
import modelo.Proveedor;
import modelo.URLNico;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import parsers.ParserImportExcelOpcProd;

@RestController
public class wsExcel2
{
    @RequestMapping(value = "procesarExcelProductos", method = {RequestMethod.GET,RequestMethod.POST})
    public static boolean procesarExcelProductos
    (
        @RequestParam(value = "url") String strUrl,
        HttpServletRequest request
    )
    {
        String subCarpeta = "";
        String carpetaInternaDelProceso = "excel";
        boolean ok = false;
        List<Producto> arrProductos = new ArrayList<>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            subCarpeta = instalacionDB.getCarpetaWeb();
            URLNico url = new Gson().fromJson(strUrl, URLNico.class);
            
            String rutaCarpetaEnDisco = "";
            Configuracion config = MasterController.dameConfigMaster();
            if( config != null)
            {
                rutaCarpetaEnDisco = config.getRutaFileSystem() + File.separator + subCarpeta + File.separator + carpetaInternaDelProceso;
                
                String rutaArchiDisco = rutaCarpetaEnDisco + File.separator + url.getUrlProvisoria();
                
                System.out.println("URL:" + rutaArchiDisco);
                
                List<ParserImportExcelOpcProd> arrOpcLeidos = archivos.Axel.parsearXLSX(rutaArchiDisco, 0 , 0 , "parsers.ParserImportExcelOpcProd", false);
                System.out.println("LEI:" +  arrOpcLeidos.size() + "PARSER DE IMPORT EXCEL");
                
                
                int i = 1; 
                for(ParserImportExcelOpcProd parserLoop : arrOpcLeidos)
                {
                    if(i != 1)
                    {
                        System.out.println(i + " | " + parserLoop.toString());
                        
                        
                        OpcProducto opcProdDB = null;
                        
                        // 1 - ANALIZAR EL OPC PROD:
                        int IDopcProd = (int) Double.parseDouble(parserLoop.getOPCPROD());
                        if(IDopcProd != -1)
                        {
                            opcProdDB = wsOpcProducto.getOpcProducto(IDopcProd);
                            
                            if(opcProdDB != null)
                            {
                                // 2 - PRECIO COSTO:
                                String strPrecioCosto = parserLoop.getPRECIO_COSTO();
                                if(strPrecioCosto != null && MasterController.esNumeric2(strPrecioCosto))
                                {
                                    double precioCosto = Double.parseDouble(strPrecioCosto);

                                    if(precioCosto != -1)
                                    {
                                        opcProdDB.setPrecioCosto(precioCosto);
                                    }
                                }
                                
                                
                                // 3 - PORCENTAJE:
                                String strPorcentaje = parserLoop.getPORCENTAJE();
                                boolean esNumericPorcentaje = MasterController.esNumeric2(strPorcentaje);
                                if(strPorcentaje != null && esNumericPorcentaje)
                                {
                                    double porcentaje = Double.parseDouble(strPorcentaje);

                                    if(porcentaje != -1)
                                    {
                                        opcProdDB.setPorcentajeGanancia(porcentaje);
                                    }
                                }
                                
                                // 3 - PRECIO FIJO:
                                String strPrecioFijo = parserLoop.getPRECIO_FIJO();
                                boolean esNumericPrecioFijo = MasterController.esNumeric2(strPrecioFijo);
                                if(strPrecioFijo != null && esNumericPrecioFijo)
                                {
                                    double precioFijo = Double.parseDouble(strPrecioFijo);

                                    if(precioFijo != -1)
                                    {
                                        opcProdDB.setPrecio(precioFijo);
                                    }
                                }
                                
                                // 4 - CANT X PACK:
                                String strCantXPack = parserLoop.getCANT_X_PACK();
                                if(strCantXPack != null && MasterController.esNumeric2(strCantXPack))
                                {
                                    double cantXPack = Double.parseDouble(strCantXPack);

                                    if(cantXPack != -1)
                                    {
                                        opcProdDB.setValorFloat(cantXPack);
                                    }
                                }
                                
                                // 5 - DESCRIPCION:
                                String descripcion = parserLoop.getDESCRIPCION();
                                if(descripcion != null )
                                {
                                    opcProdDB.getProducto().setDescripcion(descripcion);
                                }
                                
                                // 6 - CATEGORIA:
                                String strCategoria = parserLoop.getCATEGORIA();
                                Categoria categoriaDB = wsCategoria.getCategoriaByNombre(strCategoria);
                                if(categoriaDB == null)
                                {
                                    categoriaDB = wsCategoria.crearCategoria(strCategoria , request);
                                    
                                }
                                if(categoriaDB != null )
                                {
                                    opcProdDB.getProducto().setCategoria(categoriaDB);
                                }
                                
                                // 7 - PROVEEDOR:
                                String strProveedor = parserLoop.getPROVEEDOR();
                                Proveedor proveedorDB = wsProveedor.getProveedorByNombre(strProveedor, request);
                                if(proveedorDB == null)
                                {
                                    proveedorDB = wsProveedor.crearProveedor(strProveedor , request);
                                }
                                if(proveedorDB != null )
                                {
                                    opcProdDB.getProducto().setProveedor(proveedorDB);
                                }
                                
                                
                                dao.DAOEclipse.update(opcProdDB);
                                ok = true;
                            }
                        }
                        
                        
                        // 1 - ANALIZAR LA CATEGORIA
                    }
                    i++;
                }
            }
            
        }
        
        
        return ok;
    }
}
