package modelo;

import controller.MasterController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "unidades")
public class Unidad implements Comparable<Unidad>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombrePre;
    private String nombrePos;
    private String aclaracion;
    private String nombreSiEsMenorAUno;
    private String nombreUnidadSingular;
    private String nombreUnidadSingularS;
    private String apb;
    private String apbs;
    private double valorInicial;
    private double minimo;
    private double aumento;
    private int orden;
    private boolean activo;
    
    
    //CONTRUCTOR VACIO:
    public Unidad() 
    {
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public Unidad(String nombrePre,String nombrePos, String aclaracion, double valorInicial,double minimo,double aumento,boolean activo)
    {
        this.nombrePre = nombrePre;
        this.nombrePos = nombrePos;
        this.valorInicial = valorInicial;
        this.minimo = minimo;
        this.aumento = aumento;
        this.activo = activo;
    }


    //<editor-fold desc="GETTERS Y SETTERS:">
    public int getId() 
    {
        return id;
    }
    public String getNombrePre() 
    {
        return nombrePre;
    }
    public String getNombrePos() 
    {
        return nombrePos;
    }
    public double getValorInicial() 
    {
        return valorInicial;
    }
    public double getMinimo() 
    {
        return minimo;
    }
    public double getAumento() 
    {
        return aumento;
    }
    public boolean getActivo() 
    {
        return activo;
    }

    public int getOrden()
    {
        return orden;
    }

    public String getAclaracion()
    {
        return aclaracion;
    }

    public void setAclaracion(String aclaracion)
    {
        this.aclaracion = aclaracion;
    }

    public String getNombreSiEsMenorAUno()
    {
        return nombreSiEsMenorAUno;
    }

    public void setNombreSiEsMenorAUno(String nombreSiEsMenorAUno)
    {
        this.nombreSiEsMenorAUno = nombreSiEsMenorAUno;
    }

    public String getApb()
    {
        return apb;
    }

    public void setApb(String apb)
    {
        this.apb = apb;
    }

    public String getApbs()
    {
        return apbs;
    }

    public void setApbs(String apbs)
    {
        this.apbs = apbs;
    }

    public String getNombreUnidadSingular()
    {
        return nombreUnidadSingular;
    }

    public void setNombreUnidadSingular(String nombreUnidadSingular)
    {
        this.nombreUnidadSingular = nombreUnidadSingular;
    }

    public String getNombreUnidadSingularS() {
        return nombreUnidadSingularS;
    }

    public void setNombreUnidadSingularS(String nombreUnidadSingularS) {
        this.nombreUnidadSingularS = nombreUnidadSingularS;
    }
    
    
    
    
    

    //SET
    public void setId( int id ) 
    {
        this.id = id;
    }
    public void setNombrePre( String nombrePre ) 
    {
        this.nombrePre = nombrePre;
    }
    public void setNombrePos( String nombrePos ) 
    {
        this.nombrePos = nombrePos;
    }
    public void setValorInicial( double valorInicial ) 
    {
        this.valorInicial = valorInicial;
    }
    public void setMinimo( double minimo ) 
    {
        this.minimo = minimo;
    }
    public void setAumento( double aumento ) 
    {
        this.aumento = aumento;
    }
    public void setActivo( boolean activo ) 
    {
        this.activo = activo;
    }

    public void setOrden(int orden)
    {
        this.orden = orden;
    }
    
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{\n";
        str += "id:" + id + ", \n";
        str += "nombrePre:" + nombrePre + ", \n";
        str += "nombrePos:" + nombrePos + ", \n";
        str += "valorInicial:" + valorInicial + ", \n";
        str += "minimo:" + minimo + ", \n";
        str += "aumento:" + aumento + ", \n";
        str += "activo:" + activo + ", \n";
        str += "orden:" + orden + ", \n";
        
        str += "}\n";
        
        return str;
    }

    public String toJSON()
    {
        String json = "{";
        json += "id:" + id + ",";
        json += "nombrePre:" + nombrePre + ",";
        json += "nombrePos:" + nombrePos + ",";
        json += "valorInicial:" + valorInicial + ",";
        json += "minimo:" + minimo + ",";
        json += "aumento:" + aumento + ",";
        json += "activo:" + activo + ",";
        
        json += "}";
        
        return json;
    }

 
        
    
    //DYN:

    
    public int compareTo(Unidad otro)
    {
        return 1;
    }
    
}
