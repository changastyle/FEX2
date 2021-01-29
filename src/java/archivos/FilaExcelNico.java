package archivos;

import java.util.ArrayList;
import java.util.List;

public class FilaExcelNico
{
    private String strInicial;
    private List<String> arrCeldas;

    public FilaExcelNico(String strInicial)
    {
        this.strInicial = strInicial;
        
        arrCeldas = new ArrayList<>();
        
        int ultimoDigito = strInicial.length()-1;
        if(strInicial.charAt(ultimoDigito) != '|')
        {
            strInicial += "|";
        }
        
        String acumulador = "";
        for(int i = 0 ; i < strInicial.length() ; i++)
        {
            char c  = strInicial.charAt(i);

            if(c == '|')
            {
                arrCeldas.add(acumulador);
                acumulador = "";
            }
            else
            {
                acumulador += "" + c;
            }
        }
        
    }

    public String getStrInicial()
    {
        return strInicial;
    }

    public void setStrInicial(String strInicial)
    {
        this.strInicial = strInicial;
    }

    public List<String> getArrCeldas()
    {
        return arrCeldas;
    }


    @Override
    public String toString()
    {
        return "FilaNico{" + "strInicial=" + strInicial + ", arrCeldas=" + getArrCeldas() + '}';
    }
    
    
    
}
