package ws;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import controller.MasterController;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import modelo.Configuracion;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import modelo.URLNico;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import modelo.Foto;
import modelo.Instalacion;
import net.coobird.thumbnailator.Thumbnails;

@RestController
public class wsUpload
{
    
//    REPARAR @RequestMapping(value="/uploadFotoPersona",method = RequestMethod.POST)
//    public static Foto uploadFotoPersona(@RequestParam(value="foto") MultipartFile file)
//    {
////        REPARAR return uploadOne(file, MasterController.rutaFotosPersonas);
//    }
//    @RequestMapping(value="/uploadFotoGrupo",method = RequestMethod.POST)
//    public static URLNico uploadFotoGrupo(@RequestParam(value="foto") MultipartFile file)
//    {
//        return uploadOne(file, MasterController.subCarpetaGrupos);
//    }
//    @RequestMapping(value="/uploadAdjunto",method = RequestMethod.POST)
//    public static URLNico uploadAdjunto(@RequestParam(value="foto") MultipartFile file)
//    {
//        return uploadOne(file, MasterController.subCarpetaAdjuntos);
//    }
    
    
    
    @RequestMapping(value="/uploadOne",method = RequestMethod.POST)
    public static Foto uploadOne
    (
        @RequestParam(value="foto") MultipartFile file,
        @RequestParam(value="subCarpeta", required = false ,defaultValue = "backgrounds") String subCarpeta,
        HttpServletRequest request
    )
    {
        System.out.println("UPLOAD " +  subCarpeta);
        Foto fotoCargada = new Foto();
//        URLNico urlSalida = null;
//        URLNico urlSalida = null;
        
        if(file != null)
        {
            if(!file.isEmpty() )
            {
                if(file.getName()!= null)
                {
                    if(file.getName().length() > 0)
                    {
                        System.out.println("subiendo archivo: " + file.getName());

                        //1 - SUBO EL ARCHIVO Y OBTENGO LA URL:
//                        urlSalida = subirArchi(file,subCarpeta);
                        
                        fotoCargada  = subirArchi(file,subCarpeta,request);
                    }
                }
            }
        }
        
        return fotoCargada;
    }
    @RequestMapping(value="/uploadMany",method = RequestMethod.POST)
    public static List<Foto> uploadMany
    (
        @RequestParam(value="fotos") MultipartFile[] files,
        @RequestParam(value="subCarpeta", required = false ,defaultValue = "fotos") String subCarpeta,
        HttpServletRequest request
    )
    {
        List<Foto> listadoUrls = new ArrayList<Foto>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            subCarpeta = instalacionDB.getCarpetaWeb();
        }
        
        System.out.println("recibí (" + files.length + ") archivos para subir: " + files.length);
        
        // 1 - POR CADA MULTIPART FILE, LO SUBO Y GUARDO SU URL:
        for(MultipartFile multipartFile : files)
        {
            Foto fotoLoop  = subirArchi(multipartFile , subCarpeta , request);
            listadoUrls.add(fotoLoop);
        }
        
        
        
        return listadoUrls;
    }
    @RequestMapping(value="/uploadXLS",method = RequestMethod.POST)
    public static List<URLNico> uploadXLS
    (
        @RequestParam(value="archivos") MultipartFile[] files,
        @RequestParam(value="subCarpeta", required = false ,defaultValue = "archivos") String subCarpeta,
        HttpServletRequest request
    )
    {
        List<URLNico> listadoUrls = new ArrayList<URLNico>();
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            subCarpeta = instalacionDB.getCarpetaWeb();
        }
        
        System.out.println("recibí (" + files.length + ") archivos para subir: " + files.length);
        
        // 1 - POR CADA MULTIPART FILE, LO SUBO Y GUARDO SU URL:
        for(MultipartFile multipartFile : files)
        {
            URLNico urlArchivoSubido = subirFile(multipartFile, subCarpeta, request);
            System.out.println("SUBIR FILE: " + urlArchivoSubido.getUrlFinal());
            
            
            listadoUrls.add(urlArchivoSubido);
        }
        
        
        
        return listadoUrls;
    }
    private static Foto subirArchi(MultipartFile file , String subCarpeta , HttpServletRequest request )
    {
        //1 - URL DE RESPUESTA (PROVISORIA Y FINAL):
        Foto fotoCargada = new Foto();
//        URLNico urlSalida = new URLNico();
        boolean subio = false;
        
        
        // 2 - RUTA A GUARDAR DISCO C://
        String rutaCarpetaEnDisco = "";
        
        // 3 - URL DE VISUALIZACION MEDIANTE PUERTO 80:
        String urlCarpetaVisualizacion = "";
        
        // 4 - POPULO LAS VARIABLES DE CARPETA EN DISCO Y DE VISUALIZACION PUERTO 80:
        Configuracion config = MasterController.dameConfigMaster();
        if( config != null)
        {
            rutaCarpetaEnDisco = config.getRutaFileSystem() + File.separator + subCarpeta + File.separator + "subidas";
        }
        
        // 5 - COMPRUEBO QUE LA CARPETA EN DISCO EXISTA:
        File carpetaDondeLoGuardo = new File(rutaCarpetaEnDisco);
        if(!carpetaDondeLoGuardo.exists())
        {
            carpetaDondeLoGuardo.mkdir();
        }
        
        // 6 - CREO UN TIMESTAMP PARA PONERLE NOMBRE UNICO:
        Date ahora = new Date();
        
        
        // 7 - SI TENGO UN ARCHIVO VALIDO PROCESO SU NOMBRE Y LA EXTENSION:
        if(file != null)
        {
            String nombreArchivo = file.getOriginalFilename();
            if(nombreArchivo.contains("."))
            {
                int punto = nombreArchivo.lastIndexOf(".");
                String extensionArchivo = (String) file.getOriginalFilename().subSequence(punto, nombreArchivo.length());
                nombreArchivo = nombreArchivo.substring(0,punto);

                if(!file.isEmpty())
                {
                    // 8 - AGREGO ULTIMOS 4 DIGITOS DEL TIMESTAMP AL NOMBRE DEL ARCHIVO ASI ES UNICO:
                    String timestamp = "" + ahora.getTime();
                    timestamp = timestamp.substring((timestamp.length() - 4), timestamp.length());;

                    try 
                    {

                        // 9 - EL NOMBRE FULL DEL ARCHIVO EN DISCO:
                        String nombreProvisorioArchivoEnDisco = nombreArchivo.toLowerCase() + "" + timestamp + extensionArchivo;
                        String nombreFullArchivoEnDisco = rutaCarpetaEnDisco + File.separator + nombreProvisorioArchivoEnDisco;
                        

                        
                        // 10 - COPIO EL ARCHIVO DEL TMP A LA UBICACION EN DISCO:
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(nombreFullArchivoEnDisco)));
                        int sizeEnDisco = FileCopyUtils.copy(file.getInputStream(), stream);
                        
                        System.out.println("carpeta en disco: " + rutaCarpetaEnDisco);
                        System.out.println("nombreFullArchivoEnDisco: " + nombreFullArchivoEnDisco + " | " + sizeEnDisco + "KB");
                        
                        if(sizeEnDisco > 0)
                        {
                            
                            File fotoYaCargada = new File(nombreFullArchivoEnDisco);
                            if(fotoYaCargada != null)
                            {
                                String nuevoNombreDisco = achicarFoto(fotoYaCargada, 1920, 1080, nombreFullArchivoEnDisco, subCarpeta);
                                
                                if(nuevoNombreDisco != null)
                                {
                                    nuevoNombreDisco = nuevoNombreDisco.replace("\\", "/");
                                    int posUltimoSlash = nuevoNombreDisco.lastIndexOf("/");
                                    if(posUltimoSlash != -1)
                                    {
                                        String nombreOnlyArchivo = nuevoNombreDisco.substring((posUltimoSlash +1), nuevoNombreDisco.length());
                                        String nuevoNombreWeb = /* REPARAR config.getUrlVisualizacionIconos() + */ "/" + subCarpeta +"/subidas/" + nombreOnlyArchivo;
                                        
                                        // 11 - DEVUELVO UNA URL CON NOMBRE PROVISORIO Y NOMBRE FULL DE VISUALIZACION:
                                        
                                        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
                                        if(instalacionDB != null)
                                        {
                                            fotoCargada = new Foto("/subidas/" + nombreOnlyArchivo,instalacionDB, true);
                                            subio = true;
//                                            subCarpeta = instalacionDB.getCarpetaWeb();
                                        }
//                                        urlSalida = new URLNico( nuevoNombreWeb );
                                       
                                    }
                                }
                            }
                            
                            
                            System.out.println("SUBIDA CORRECTA2: " + nombreProvisorioArchivoEnDisco);
                            System.out.println("URL SALIDA: " + fotoCargada.toString());
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("ERROR: subiendo archivo " + nombreArchivo);
                        e.printStackTrace();
                    }
                }
            }

        }
        return fotoCargada;
    }
    private static URLNico subirFile(MultipartFile file , String subCarpeta , HttpServletRequest request )
    {
        String carpetaInternaDelProceso = "excel";
        
        //1 - URL DE RESPUESTA (PROVISORIA Y FINAL):
        URLNico urlFinal = new URLNico("" , carpetaInternaDelProceso , request);
        

        boolean subio = false;
        
        
        // 2 - RUTA A GUARDAR DISCO C://
        String rutaCarpetaEnDisco = "";
        
        // 3 - URL DE VISUALIZACION MEDIANTE PUERTO 80:
        String urlCarpetaVisualizacion = "";
        
        // 4 - POPULO LAS VARIABLES DE CARPETA EN DISCO Y DE VISUALIZACION PUERTO 80:
        Configuracion config = MasterController.dameConfigMaster();
        if( config != null)
        {
            rutaCarpetaEnDisco = config.getRutaFileSystem() + File.separator + subCarpeta + File.separator + carpetaInternaDelProceso;
        }
        
        // 5 - COMPRUEBO QUE LA CARPETA EN DISCO EXISTA:
        File carpetaDondeLoGuardo = new File(rutaCarpetaEnDisco);
        if(!carpetaDondeLoGuardo.exists())
        {
            carpetaDondeLoGuardo.mkdir();
        }
        
        // 6 - CREO UN TIMESTAMP PARA PONERLE NOMBRE UNICO:
        Date ahora = new Date();
        
        
        // 7 - SI TENGO UN ARCHIVO VALIDO PROCESO SU NOMBRE Y LA EXTENSION:
        if(file != null)
        {
            String nombreArchivo = file.getOriginalFilename();
            if(nombreArchivo.contains("."))
            {
                int punto = nombreArchivo.lastIndexOf(".");
                String extensionArchivo = (String) file.getOriginalFilename().subSequence(punto, nombreArchivo.length());
                nombreArchivo = nombreArchivo.substring(0,punto);

                if(!file.isEmpty())
                {
                    // 8 - AGREGO ULTIMOS 4 DIGITOS DEL TIMESTAMP AL NOMBRE DEL ARCHIVO ASI ES UNICO:
                    String timestamp = "" + ahora.getTime();
                    timestamp = timestamp.substring((timestamp.length() - 4), timestamp.length());;

                    try 
                    {

                        // 9 - EL NOMBRE FULL DEL ARCHIVO EN DISCO:
                        String nombreProvisorioArchivoEnDisco = nombreArchivo.toLowerCase() + "" + timestamp + extensionArchivo;
                        String nombreFullArchivoEnDisco = rutaCarpetaEnDisco + File.separator + nombreProvisorioArchivoEnDisco;
                        

                        
                        // 10 - COPIO EL ARCHIVO DEL TMP A LA UBICACION EN DISCO:
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(nombreFullArchivoEnDisco)));
                        int sizeEnDisco = FileCopyUtils.copy(file.getInputStream(), stream);
                        
                        System.out.println("carpeta en disco: " + rutaCarpetaEnDisco);
                        System.out.println("nombreFullArchivoEnDisco: " + nombreFullArchivoEnDisco + " | " + sizeEnDisco + "KB");
                        
                        if(sizeEnDisco > 0)
                        {
                            
                            File archivoYaCargado = new File(nombreFullArchivoEnDisco);
                                    
                            if(archivoYaCargado != null)
                            {
//                                String nombreOnlyArchivo = archivoYaCargado.substring((posUltimoSlash +1), nuevoNombreDisco.length());
//                                        String nuevoNombreWeb = /* REPARAR config.getUrlVisualizacionIconos() + */ "/" + subCarpeta +"/subidas/" + nombreOnlyArchivo;
//                                        
                                
                                String nombreOnlyFile = archivoYaCargado.getAbsolutePath();
                                
                                if(nombreOnlyFile != null)
                                {
                                    int posUltimoSlash = nombreOnlyFile.lastIndexOf("/");
                                    if(posUltimoSlash == -1)
                                    {
                                        posUltimoSlash = nombreOnlyFile.lastIndexOf(File.separator);
                                    }
                                    
                                    if(posUltimoSlash != -1)
                                    {
                                        nombreOnlyFile = nombreOnlyFile.substring((posUltimoSlash +1), nombreOnlyFile.length());
                                        urlFinal = new URLNico(nombreOnlyFile, carpetaInternaDelProceso, request);
                                    }
                                }
                                
                                
                                System.out.println("ESTOY ACA: " + urlFinal.getUrlFinal() + " | " + urlFinal.getUrlProvisoria());
                            }
                                    
//                                    carpetaInternaDelProceso
//                            if(fotoYaCargada != null)
//                            {
//                                String nuevoNombreDisco = achicarFoto(fotoYaCargada, 1920, 1080, nombreFullArchivoEnDisco, subCarpeta);
//                                
//                                if(nuevoNombreDisco != null)
//                                {
//                                    nuevoNombreDisco = nuevoNombreDisco.replace("\\", "/");
//                                    int posUltimoSlash = nuevoNombreDisco.lastIndexOf("/");
//                                    if(posUltimoSlash != -1)
//                                    {
//                                        String nombreOnlyArchivo = nuevoNombreDisco.substring((posUltimoSlash +1), nuevoNombreDisco.length());
//                                        String nuevoNombreWeb = /* REPARAR config.getUrlVisualizacionIconos() + */ "/" + subCarpeta +"/subidas/" + nombreOnlyArchivo;
//                                        
//                                        // 11 - DEVUELVO UNA URL CON NOMBRE PROVISORIO Y NOMBRE FULL DE VISUALIZACION:
//                                        
//                                        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
//        
//                                        if(instalacionDB != null)
//                                        {
//                                            fotoCargada = new Foto("/subidas/" + nombreOnlyArchivo,instalacionDB, true);
//                                            subio = true;
////                                            subCarpeta = instalacionDB.getCarpetaWeb();
//                                        }
////                                        urlSalida = new URLNico( nuevoNombreWeb );
//                                       
//                                    }
//                                }
//                            }
                            
                            
                            System.out.println("SUBIDA CORRECTA: " + nombreProvisorioArchivoEnDisco);
//                            System.out.println("URL SALIDA: " + fotoCargada.toString());
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println("ERROR: subiendo archivo " + nombreArchivo);
                        e.printStackTrace();
                    }
                }
            }

        }
        return urlFinal;
    }
    
    public static String  achicarFoto(File imagen,int ancho , int alto , String nombreArchivoSaliente, String rutaCarpeta)
    {
        String nuevoNombreDisco = "";
        try
        {
            // 1 - CREO LA BUFFERED IMAGE:
            BufferedImage image = ImageIO.read(imagen);
            
            
            //"comprimidas/"
            File carpetaFotosComprimidas = new File(rutaCarpeta);
            if(carpetaFotosComprimidas != null)
            {
                if(!carpetaFotosComprimidas.exists())
                { 
                    carpetaFotosComprimidas.mkdir();
                }
                
                if(image != null)
                {
                    Date timestamp = new Date();
//                    String nombreArchivo = carpetaFotosComprimidas.getAbsolutePath() + File.separator +  nombreArchivoSaliente;

                    int posUltimoPunto = nombreArchivoSaliente.lastIndexOf(".");

                    if(posUltimoPunto != -1)
                    {
                        nuevoNombreDisco = nombreArchivoSaliente.substring(0,posUltimoPunto) + ".jpg";
                    }
                    
                    Thumbnails.of(image)
                    .size(ancho, alto)
                    .outputQuality(0.5)
                    .outputFormat("jpg")
                    .toFile(new File(nuevoNombreDisco));
                    
                }
            }
            
            
        }
        catch(Exception e)
        {
            System.out.println("ERROR: " + e.toString() );
        }
        
        
        return nuevoNombreDisco;
    }
    
    public static String rotarFoto(String carpetaFS , String nombreFoto)
    {
        String rutaImagenEntrada = carpetaFS + File.separator + nombreFoto;
        String rutaImagenSalida = carpetaFS + File.separator +"rotada-" + nombreFoto ;
        
        try
        {
            BufferedImage image = ImageIO.read(new File(rutaImagenEntrada));

            double angle = 90;
            
            int wAux= image.getWidth();    
            int hAux = image.getHeight();
            
            int w = hAux;    
            int h = wAux;
            
            
            BufferedImage rotated = new BufferedImage(w, h, image.getType());  
            Graphics2D graphic = rotated.createGraphics();
            graphic.translate((hAux - wAux) / 2, (hAux - wAux) / 2);
            graphic.rotate(Math.PI / 2, hAux / 2, wAux / 2);
            graphic.drawImage(image, null, 0, 0);
            graphic.dispose();
            

            File outputfile = new File(rutaImagenSalida);
            ImageIO.write(rotated, "jpg", outputfile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return rutaImagenSalida;
    }
    /*
    @RequestMapping(value="/subirArchivoParaMensaje",method = {RequestMethod.GET,RequestMethod.POST})
    private static ArchivoMensaje subimeArchivoParaMensaje(@RequestParam(value="foto") MultipartFile file)
    {
        ArchivoMensaje archivo = null;
        URLNico urlNico = uploadOne(file);
        
        if(urlNico != null)
        {
            //archivo = new ArchivoMensaje(urlNico.getUrlFinal(), urlNico.getUrlProvisoria(), wsMensaje.getMensajeEmpty());
        }
        
        return archivo;
        
    }*/
}