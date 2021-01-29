package archivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

public class ExcelReader
{
    
//    public static List parearXLS2(String ruta, int hojaSeleccionada, int numeroFilaConHeaders , Class clase)
//    {
//        List arr = new ArrayList();
//        
//        String salida = null;
//        try 
//        {
//            System.out.println("Leyendo " + ruta);
//            File archivo = new File(ruta);
////            BufferedReader  bf = new BufferedReader (new InputStreamReader(new FileInputStream(archivo),"UTF8"));
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(archivo));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            
//            List<String> arrHeaders = dameArrayDeHeaders(ruta, hojaSeleccionada,numeroFilaConHeaders);
//            
//            HSSFSheet sheet = wb.getSheetAt(hojaSeleccionada);
//            HSSFRow row;
//            HSSFCell celda;
//            
//            int columnas = 0; 
//            int tmp = 0;
//            
//            System.out.println("NUMERO DE SHEETS: " + wb.getNumberOfSheets());
//            int filas = sheet.getPhysicalNumberOfRows();
//            System.out.println("NUMERO DE ROWS EN SHEET(" + (hojaSeleccionada + 1) + "): " + filas);
//            
//            // 1 - AVERIGUAR CUANTAS COLUMNAS (CUANTO ME VOY DE ANCHO):
//            for(int i = 0; i < 10 || i < filas; i++) 
//            {
//                
//                row = sheet.getRow(i);
//                
//                if(row != null)
//                {
//                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//                    if(tmp > columnas)
//                    {
//                        columnas = tmp;
//                    }
//                }
//            }
//            System.out.println("NUMERO DE COLUMNAS " + columnas);
//            
//            
//            
//            // 2- RECORRO LAS FILAS Y COLUMNAS IMPRIMIENDO DATOS:
//            for( int f = (numeroFilaConHeaders+1) ; f < filas ; f++)
//            {
//                Object objetito = clase.newInstance();
//                
//                //COLUMNAS:
//                for( int c = 0 ; c < columnas ; c++)
//                {
//                    
//                    row = sheet.getRow(f);
//                    if(row != null)
//                    {
//                        celda = row.getCell((short)c);
//                        DataFormatter formatter = new DataFormatter();
//                        String valor = formatter.formatCellValue(celda);
//    //                    String valor = celda.toString().trim();
//
//                        //CAMPOS DE LA CLASE:
//                        for (Field fieldLoop : clase.getDeclaredFields())
//                        {
//                            String nombreCampo = fieldLoop.getName();
//                            //System.out.println("campo: "+ fieldLoop.getName());
//
//                            if(nombreCampo.equalsIgnoreCase("idx"))
//                            {
//                                fieldLoop.setAccessible(true);
//
//                                fieldLoop.setInt(objetito, f);
//                            }
//                            else
//                            {
//                                if(c < arrHeaders.size())
//                                {
//                                    String header = arrHeaders.get(c);
//                                    if(nombreCampo.equalsIgnoreCase(header))
//                                    {
//                                        fieldLoop.setAccessible(true);
//
//                                        fieldLoop.set(objetito, valor);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
////                System.out.println("objetito: "+ objetito);
//                arr.add(objetito);
//            }
//            
//            
////            System.out.println("HEADERS:");
//            for(String headerLoop : arrHeaders)
//            {
////                System.out.println(headerLoop);
//            }
//        }
//        catch(Exception ioe) 
//        {
//            ioe.printStackTrace();
//        }
//        
//        return arr;
//    }
//    
//    
//    public static String leerXLS(String ruta, int numeroFilaConHeaders)
//    {
//        String salida = null;
//        try 
//        {
//            System.out.println("Leyendo " + ruta);
//            File archivo = new File(ruta);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(archivo));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            
//            List<String> arrHeaders = new ArrayList<>();
//            
//            int hojaSeleccionada = 0;
//            HSSFSheet sheet = wb.getSheetAt(hojaSeleccionada);
//            HSSFRow row;
//            HSSFCell celda;
//            
//            int columnas = 0; 
//            int tmp = 0;
//            
//            System.out.println("NUMERO DE SHEETS: " + wb.getNumberOfSheets());
//            int filas = sheet.getPhysicalNumberOfRows();
//            System.out.println("NUMERO DE ROWS EN SHEET(" + (hojaSeleccionada + 1) + "): " + filas);
//            
//            // 1 - AVERIGUAR CUANTAS COLUMNAS (CUANTO ME VOY DE ANCHO):
//            for(int i = 0; i < 10 || i < filas; i++) 
//            {
//                
//                row = sheet.getRow(i);
//                
//                if(row != null)
//                {
//                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//                    if(tmp > columnas)
//                    {
//                        columnas = tmp;
//                    }
//                }
//            }
//            
//            System.out.println("Tengo "+ columnas +" columnas y " + filas + " filas");
//            
//            
//            // 2- RECORRO LAS FILAS Y COLUMNAS IMPRIMIENDO DATOS:
//            for( int f = (numeroFilaConHeaders) ; f < filas ; f++)
//            {
////                System.out.print("["+ f +"]");
//                for( int c = 0 ; c < columnas ; c++)
//                {
//                    row = sheet.getRow(f);
//                    celda = row.getCell((short)c);
//                    
//                    //FILA DE HEADERS:
//                    if((f) == numeroFilaConHeaders)
//                    {
//                        String valor = celda.getStringCellValue();
//                        //System.out.println("FILA HEADERS: " + f + " - " + c + " - " + celda);
//                        arrHeaders.add(valor);
////                        System.out.print(  celda + ",");
//                    }
//                    else
//                    {
//                        // SI NO ES HEADER IMPIMO:
////                        System.out.print(  celda + ",");
//                    }
//                }
////                System.out.println("");
//            }
//            
//            
//////            System.out.println("HEADERS:");
////            for(String headerLoop : arrHeaders)
////            {
////                System.out.println(headerLoop);
////            }
//            
////            int rows; // No of rows
////            rows = sheet.getPhysicalNumberOfRows();
////
////            int cols = 0; // No of columns
////            int tmp = 0;
////
////            // This trick ensures that we get the data properly even if it doesn't start from first few rows
////            for(int i = 0; i < 10 || i < rows; i++) 
////            {
////                row = sheet.getRow(i);
////                if(row != null)
////                {
////                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
////                    if(tmp > cols)
////                    {
////                        cols = tmp;
////                    }
////                }
////            }
////
////            for(int r = 0; r < rows; r++) 
////            {
////                row = sheet.getRow(r);
////                if(row != null) 
////                {
////                    for(int c = 0; c < cols; c++) 
////                    {
////                        cell = row.getCell((short)c);
////                        if(cell != null) 
////                        {
////                            salida = cell.getStringCellValue();
////                            System.out.println("cell: " + cell.getStringCellValue());
////                        }
////                    }
////                }
////            }
//        }
//        catch(Exception ioe) 
//        {
//            ioe.printStackTrace();
//        }
//        
//        return salida;
//    }
//    public static List parearXLS(String ruta, int numeroFilaConHeaders, Class clase)
//    {
//        List arr = new ArrayList();
//        
//        String salida = null;
//        try 
//        {
//            System.out.println("Leyendo " + ruta);
//            File archivo = new File(ruta);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(archivo));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            
//            List<String> arrHeaders = dameArrayDeHeaders(ruta, numeroFilaConHeaders,numeroFilaConHeaders);
//            
//            int hojaSeleccionada = 0;
//            HSSFSheet sheet = wb.getSheetAt(hojaSeleccionada);
//            HSSFRow row;
//            HSSFCell celda;
//            
//            int columnas = 0; 
//            int tmp = 0;
//            
//            System.out.println("NUMERO DE SHEETS: " + wb.getNumberOfSheets());
//            int filas = sheet.getPhysicalNumberOfRows();
//            System.out.println("NUMERO DE ROWS EN SHEET(" + (hojaSeleccionada + 1) + "): " + filas);
//            
//            // 1 - AVERIGUAR CUANTAS COLUMNAS (CUANTO ME VOY DE ANCHO):
//            for(int i = 0; i < 10 || i < filas; i++) 
//            {
//                
//                row = sheet.getRow(i);
//                
//                if(row != null)
//                {
//                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//                    if(tmp > columnas)
//                    {
//                        columnas = tmp;
//                    }
//                }
//            }
//            System.out.println("NUMERO DE COLUMNAS " + columnas);
//            
//            
//            
//            // 2- RECORRO LAS FILAS Y COLUMNAS IMPRIMIENDO DATOS:
//            for( int f = (numeroFilaConHeaders+1) ; f < filas ; f++)
//            {
//                Object objetito = clase.newInstance();
//                
//                //COLUMNAS:
//                for( int c = 0 ; c < columnas ; c++)
//                {
//                    
//                    row = sheet.getRow(f);
//                    celda = row.getCell((short)c);
//                    DataFormatter formatter = new DataFormatter();
//                    String valor = formatter.formatCellValue(celda);
////                    String valor = celda.toString().trim();
//
//                    //CAMPOS DE LA CLASE:
//                    for (Field fieldLoop : clase.getDeclaredFields())
//                    {
//                        String nombreCampo = fieldLoop.getName();
//                        //System.out.println("campo: "+ fieldLoop.getName());
//                        
//                        if(nombreCampo.equalsIgnoreCase("id"))
//                        {
//                            fieldLoop.setAccessible(true);
//
//                            fieldLoop.setInt(objetito, f);
//                        }
//                        else
//                        {
//                            if(c < arrHeaders.size())
//                            {
//                                String header = arrHeaders.get(c);
//                                if(nombreCampo.equalsIgnoreCase(header))
//                                {
//                                    fieldLoop.setAccessible(true);
//
//                                    fieldLoop.set(objetito, valor);
//                                }
//                            }
//                        }
//                    }
//                }
//                System.out.println("objetito: "+ objetito);
//                arr.add(objetito);
//            }
//            
//            
////            System.out.println("HEADERS:");
//            for(String headerLoop : arrHeaders)
//            {
////                System.out.println(headerLoop);
//            }
//        }
//        catch(Exception ioe) 
//        {
//            ioe.printStackTrace();
//        }
//        
//        return arr;
//    }
//    public static List<String> dameArrayDeHeaders(String ruta ,int hojaSeleccionada, int numeroFilaConHeaders )
//    {
//        List<String> arrHeaders = new ArrayList<>();
//        
//        try 
//        {
//            System.out.println("Leyendo " + ruta);
//            File archivo = new File(ruta);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(archivo));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//
//            HSSFSheet sheet = wb.getSheetAt(hojaSeleccionada);
//            HSSFRow row;
//            HSSFCell celda;
//            
//            int columnas = 0; 
//            int tmp = 0;
//            
//            System.out.println("NUMERO DE SHEETS: " + wb.getNumberOfSheets());
//            int filas = sheet.getPhysicalNumberOfRows();
////            System.out.println("NUMERO DE ROWS EN SHEET(" + (hojaSeleccionada + 1) + "): " + filas);
//            
//            // 1 - AVERIGUAR CUANTAS COLUMNAS (CUANTO ME VOY DE ANCHO):
//            for(int i = 0; i < 10 || i < filas; i++) 
//            {
//                row = sheet.getRow(i);
//                
//                if(row != null)
//                {
//                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//                    if(tmp > columnas)
//                    {
//                        columnas = tmp;
//                    }
//                }
//            }
////            System.out.println("NUMERO DE COLUMNAS " + columnas);
//
//            // 2- RECORRO LAS FILAS Y COLUMNAS IMPRIMIENDO DATOS:
//            for( int f = (numeroFilaConHeaders) ; f < filas ; f++)
//            {
//                //System.out.print("["+ f +"]");
//                for( int c = 0 ; c < columnas ; c++)
//                {
//                    
//                    row = sheet.getRow(f);
//                    if(row != null)
//                    {
//                        celda = row.getCell((short)c);
//
//                        //FILA DE HEADERS:
//                        if((f) == numeroFilaConHeaders)
//                        {
//    //                        System.out.println("f:"+f + "|c:" +c);
//                            if(celda != null)
//                            {
//                                DataFormatter formatter = new DataFormatter();
//                                String val = formatter.formatCellValue(celda);
//    //                            String valor = celda.getStringCellValue();
//                                arrHeaders.add(val);
//                            }
//                            //System.out.println("FILA HEADERS: " + f + " - " + c + " - " + celda);
//    //                        System.out.print(  celda + ",");
//                        }
//                    }
//                }
//                ///System.out.println("");
//            }
//            
//            
////            System.out.println("HEADERS:");
////            for(String headerLoop : arrHeaders)
////            {
////                System.out.println(headerLoop);
////            }
//        }
//        catch(Exception ioe) 
//        {
//            ioe.printStackTrace();
//        }
//        
//        return arrHeaders;
//    }
//    
//    public static int dameUltimoSheet(String ruta)
//    {
//        int ultimaSheet = -1;
//        try 
//        {
//            System.out.println("Leyendo " + ruta);
//            File archivo = new File(ruta);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(archivo));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            
//            List<String> arrHeaders = new ArrayList<>();
//            
//            int hojaSeleccionada = 0;
//            HSSFSheet sheet = wb.getSheetAt(hojaSeleccionada);
//            HSSFRow row;
//            HSSFCell celda;
//            
//            int columnas = 0; 
//            
//            ultimaSheet = wb.getNumberOfSheets();
////            System.out.println("NUMERO DE SHEETS: " + wb.getNumberOfSheets());
////            int filas = sheet.getPhysicalNumberOfRows();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//        return ultimaSheet - 1;
//    }
//    public static int dameCantidadFilasDeSheet(String ruta , int sheetALeer)
//    {
//        int cantidadFilas = -1;
//        try 
//        {
//            System.out.println("Leyendo " + ruta);
//            File archivo = new File(ruta);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(archivo));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            
//            List<String> arrHeaders = new ArrayList<>();
//            
//            HSSFSheet sheet = wb.getSheetAt(sheetALeer);
//            HSSFRow row;
//            HSSFCell celda;
//            
//            int columnas = 0; 
//            
//            cantidadFilas = sheet.getPhysicalNumberOfRows();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        
//        return cantidadFilas;
//    }
//    
//    public static String dameValorCelda(String ruta, int sheetALeer , int filaSeleccionada, int columnaSeleccionada)
//    {
//        filaSeleccionada = filaSeleccionada - 1;
//        columnaSeleccionada = columnaSeleccionada - 1;
//        String salida = null;
//        try 
//        {
//            File archivo = new File(ruta);
//            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(archivo));
//            HSSFWorkbook wb = new HSSFWorkbook(fs);
//            
//            
//            HSSFSheet sheet = wb.getSheetAt(sheetALeer);
//            HSSFRow row;
//            HSSFCell celda;
//            
//            int columnas = 0; 
//            int tmp = 0;
//            
//            int filas = sheet.getPhysicalNumberOfRows();
//            
//            // 1 - AVERIGUAR CUANTAS COLUMNAS (CUANTO ME VOY DE ANCHO):
//            for(int i = 0; i < 10 || i < filas; i++) 
//            {
//                
//                row = sheet.getRow(i);
//                
//                if(row != null)
//                {
//                    tmp = sheet.getRow(i).getPhysicalNumberOfCells();
//                    if(tmp > columnas)
//                    {
//                        columnas = tmp;
//                    }
//                }
//            }
//            
//            
//            
//            // 2- RECORRO LAS FILAS Y COLUMNAS IMPRIMIENDO DATOS:
//            for( int f = 0 ; f < filas ; f++)
//            {
//                for( int c = 0 ; c < columnas ; c++)
//                {
//                    row = sheet.getRow(f);
//                    if(row != null)
//                    {
//                        celda = row.getCell((short)c);
//
//                        if(f == filaSeleccionada && columnaSeleccionada == c)
//                        {
//                            if(celda != null)
//                            {
//                                int tipoCelda = celda.getCellType();
//                                //System.out.println("typo:" + tipoCelda);
//
//                                String valor = "";
//                                if(tipoCelda == 0)
//                                {
//                                    valor = "" + ((int) celda.getNumericCellValue());
//                                }
//                                else
//                                {
//                                    valor = celda.getStringCellValue();
//                                }
//                                salida = "" + valor;
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        catch(Exception ioe) 
//        {
//            ioe.printStackTrace();
//        }
//        
//        return salida;
//    }
//    public static boolean set(Object object, String fieldName, Object fieldValue) 
//    {
//        Class<?> clazz = object.getClass();
//        while (clazz != null) {
//            try 
//            {
//                Field field = clazz.getDeclaredField(fieldName);
//                field.setAccessible(true);
//                field.set(object, fieldValue);
//                return true;
//            }
//            catch (NoSuchFieldException e) 
//            {
//                clazz = clazz.getSuperclass();
//            }
//            catch (Exception e) 
//            {
//                throw new IllegalStateException(e);
//            }
//        }
//        return false;
//    }
}
