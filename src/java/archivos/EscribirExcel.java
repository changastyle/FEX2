/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivos;

import controller.MasterController;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Nico
 */
public class EscribirExcel 
{
    public static void main(String args[])
    {
        String rutaXLS = "C:\\jtemp\\donato.xlsx";
        String nombreSheet = "DONATELO";
        
        List<FilaExcelNico> arrFilas = new ArrayList<>();
        
        // RELLENO :
        for(int i = 0 ; i < 25 ; i++)
        {
            arrFilas.add(new FilaExcelNico( (i+1) + "|NICO|2|"));
        }
        
        System.out.println("ESCRIBI: " + Axel.escribirAxel(rutaXLS, nombreSheet , arrFilas));
        
    }
    
   
}
