
import archivos.Axel;
import archivos.FilaExcelNico;
import java.util.List;
import parsers.ParserImportExcelOpcProd;

public class Main
{
    public static void main (String args[])
    {
        String ruta = "C:\\xampp\\htdocs\\upload\\ecommerce\\saludable-cosecha\\excel\\27-1-2021--12-494324.xlsx";
        
        System.out.println("LEO EXCEL:"  + ruta);
        
        List<String> arrLineas = Axel.leerXLSXComoLineas(ruta , 0);
        
        System.out.println("LINEAS: " + arrLineas.size());
        
        FilaExcelNico headers =  Axel.dameArrayDeHeaders(ruta, 0, 0 );
        for(String celdaLoop : headers.getArrCeldas())
        {
            System.out.print(celdaLoop + "|");
        }
        
        System.out.println("");
        System.out.println("--");
        
        
        
        System.out.println("LISTADO DE VALORES");
        
        List<ParserImportExcelOpcProd> arrValores = Axel.parsearXLSX(ruta, 0, 0, "parsers.ParserImportExcelOpcProd", false);
        
        System.out.println("SIZE VALORES:" + arrValores.size());
        
        for(ParserImportExcelOpcProd lineaXls : arrValores)
        {
            System.out.println(lineaXls.getDESCRIPCION());
        }
    }
}
