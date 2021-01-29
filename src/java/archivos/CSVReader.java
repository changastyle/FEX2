package archivos;

import java.util.List;

public class CSVReader
{
    public static List<String> onlyRead(String ruta)
    {
        List<String> lineasCSV = ManejoArchivos.read2(ruta);
        
        return lineasCSV;
    }
}
