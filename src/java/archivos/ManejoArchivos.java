package archivos;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManejoArchivos
{
    public static String read3(String ruta)
    {
        String linea = "";
        List<String> arrLineas = read2(ruta);
        
        for(String strLoop : arrLineas)
        {
            linea += "" + strLoop;
        }
        
        return linea;
    }
    public static List<String> read2(String ruta)
    {
        List<String> lineas = new ArrayList<>();
        BufferedReader reader;
        try 
        {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(ruta),"UTF-8"));
            
            String linea = reader.readLine();
            while (linea != null) 
            {
                //System.out.println(linea);
                lineas.add(linea);
                
                // read next line
                linea = reader.readLine();
            }
            reader.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return lineas;
    }
    
    public static String dameValorLineaTSV(String lineaCSV , int columna)
    {
        String valorBuscado = "";
        
        int contadorPuntoYComa = 0;

        for(int i  = 0 ; i < lineaCSV.length() ; i++ )
        {
            char actual = lineaCSV.charAt(i);
            
            if(actual == '|' )
            {
                contadorPuntoYComa++;
            }
            else
            {
                if(contadorPuntoYComa == columna)
                {
                    valorBuscado += actual;
                }
            }
            
        }
        
        if(valorBuscado.startsWith("\""))
        {
            valorBuscado = valorBuscado.substring(1 , valorBuscado.length());
        }
        if(valorBuscado.endsWith("\""))
        {
            valorBuscado = valorBuscado.substring(0 , (valorBuscado.length() - 1) );
        }
        
        valorBuscado = valorBuscado.trim();
        
        return valorBuscado;
    }
    public static String dameValorLineaCSV(String lineaCSV , int columna)
    {
        String valorBuscado = "";
        
        int contadorPuntoYComa = 0;

        for(int i  = 0 ; i < lineaCSV.length() ; i++ )
        {
            char actual = lineaCSV.charAt(i);
            
            if(actual == ';' )
            {
                contadorPuntoYComa++;
            }
            else
            {
                if(contadorPuntoYComa == columna)
                {
                    valorBuscado += actual;
                }
            }
            
        }
        
        if(valorBuscado.startsWith("\""))
        {
            valorBuscado = valorBuscado.substring(1 , valorBuscado.length());
        }
        if(valorBuscado.endsWith("\""))
        {
            valorBuscado = valorBuscado.substring(0 , (valorBuscado.length() - 1) );
        }
        
        valorBuscado = valorBuscado.trim();
        
        return valorBuscado;
    }
    
    public static List<String> read(String ruta)
    {
        List<String> lineas = new ArrayList<>();
        if(ruta != null)
        {
            File file = new File(ruta);
            if(file != null)
            {
                if(file.exists() && file.isFile() && file.canRead())
                {
                    try 
                    {
                        Scanner sc = new Scanner(file);
                        String linea = "";
                        
                        while (linea != null)
                        { 
                            linea = sc.nextLine();
                            lineas.add(linea);
                            System.out.println(linea);
                        }
                    }
                    catch (Exception e) 
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return lineas;
    }
    
    public static void write(String rutaArchivo, String valor)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo));
            writer.write(valor);
     
            writer.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
  
}
