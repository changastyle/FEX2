package archivos;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder.In;
import modelo.Instalacion;

public class Main
{
//    public static void main(String args[])
//    {
//        String rutaCarpeta = "C:\\Users\\Nico\\Desktop\\novedades-vd\\POTATO\\PRODUCTOS\\";
//        String rutaArchivo = "listado-test.xlsx";
//        String rutaTotal  = rutaCarpeta + rutaArchivo;
//        System.out.println("LEO EXCEL POTATO : "  + rutaTotal);
//        
//        List<ParserProducto> arrProductosExcel = Axel.parsearXLSX(rutaTotal, 0, 0, "parsers.ParserProducto");
//        
//        int indice = 0;
//        for(ParserProducto parserLoop : arrProductosExcel)
//        {
//            System.out.println(parserLoop.toJSON());
//            
//            // DESCARGO LA IMAGEN:
////            String fileURL = "http://jdbc.postgresql.org/download/postgresql-9.2-1002.jdbc4.jar";
//
//            if(indice > 0)
//            {
//                int codigo = (int) Float.parseFloat(parserLoop.codigo);
//                String fileURL = parserLoop.getUrlImagen();
//                System.out.println("VOY A BAJAR: " + fileURL);
//                String saveDir = "C:\\Users\\Nico\\Desktop\\novedades-vd\\POTATO\\PRODUCTOS\\TEST\\" + codigo+".jpg";
//                try 
//                {
//                    bajar(parserLoop.getUrlImagen(), saveDir);
//                } 
//                catch (IOException ex)
//                {
//                    ex.printStackTrace();
//                }
//                System.out.println("IMG: " + parserLoop.getUrlImagen());
//                System.out.println("--------");
//            }
//
//            indice++;
//        }
//    }
//    public static void bajar(String search, String path) throws IOException
//    {
//
//    // This will get input data from the server
//    InputStream inputStream = null;
//
//    // This will read the data from the server;
//    OutputStream outputStream = null;
//
//    try {
//        // This will open a socket from client to server
//        URL url = new URL(search);
//
//        // This user agent is for if the server wants real humans to visit
//        String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";
//
//        // This socket type will allow to set user_agent
//        URLConnection con = url.openConnection();
//
//        // Setting the user agent
//        con.setRequestProperty("User-Agent", USER_AGENT);
//
//        //Getting content Length
//        int contentLength = con.getContentLength();
//        System.out.println("File contentLength = " + contentLength + " bytes");
//
//
//        // Requesting input data from server
//        inputStream = con.getInputStream();
//
//        // Open local file writer
//        outputStream = new FileOutputStream(path);
//
//        // Limiting byte written to file per loop
//        byte[] buffer = new byte[2048];
//
//        // Increments file size
//        int length;
//        int downloaded = 0; 
//
//        // Looping until server finishes
//        while ((length = inputStream.read(buffer)) != -1) 
//        {
//            // Writing data
//            outputStream.write(buffer, 0, length);
//            downloaded+=length;
//            //System.out.println("Downlad Status: " + (downloaded * 100) / (contentLength * 1.0) + "%");
//
//
//        }
//    } catch (Exception ex) {
//        //Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
//    }
//
//    // closing used resources
//    // The computer will not be able to use the image
//    // This is a must
//    if(outputStream != null)
//    {
//        outputStream.close();
//    }
//    if(inputStream != null)
//    {
//        inputStream.close();
//    }
//}
    
}
