package parsers;

import controller.MasterController;
import java.util.List;

public class ParserProducto
{
    public String codigo;
    public String nombre;
    public String unidades;
    public String precioBulto;
    public String minimo;
    public String precioLuana;
    public String categoria;
    public String urlImagen;
    public String descripcion;
    public String subtitulo;
    
    public ParserProducto()
    {
        this.subtitulo = "";
    }

    public ParserProducto(String codigo, String nombre, String unidades, String precioBulto, String minimo, String precioLuana, String categoria, String urlImagen, String descripcion, String subtitulo)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidades = unidades;
        this.precioBulto = precioBulto;
        this.minimo = minimo;
        this.precioLuana = precioLuana;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
        this.descripcion = descripcion;
        this.subtitulo = subtitulo;
    }

    
    
    
    public String getCodigo()
    {
        return codigo;
    }

    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
    }

    public String getNombre()
    {
        List<String> arrPalabras = MasterController.separarPalabrasPorEspacio(nombre);
        
        if(arrPalabras != null)
        {
            if(arrPalabras.size() > 3)
            {
                this.nombre =  arrPalabras.get(0) + " " + arrPalabras.get(1) + " " + arrPalabras.get(2);
            }
            
            if(subtitulo.equalsIgnoreCase("xxx"))
            {
                subtitulo = "";
            }
            if(descripcion.equalsIgnoreCase("xxx"))
            {
                descripcion = "";
            }
            for(int i = 3 ; i < arrPalabras.size(); i++)
            {
                subtitulo += arrPalabras.get(i) + " ";
            }
        }
        else
        {
            this.nombre = nombre;
        }
        
        return nombre;
    }

    public String getUnidades()
    {
        return unidades.trim();
    }

    public void setUnidades(String unidades)
    {
        this.unidades = unidades;
    }

    public String getPrecioBulto()
    {
        return precioBulto.trim();
    }

    public void setPrecioBulto(String precioBulto)
    {
        this.precioBulto = precioBulto;
    }

    public String getMinimo()
    {
        return minimo.trim();
    }

    public void setMinimo(String minimo)
    {
        this.minimo = minimo;
    }

    public String getPrecioLuana()
    {
        return precioLuana.trim();
    }

    public void setPrecioLuana(String precioLuana)
    {
        this.precioLuana = precioLuana;
    }

    public String getCategoria()
    {
        return categoria.trim();
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public String getDescripcion()
    {
        return descripcion.trim();
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public String getSubtitulo()
    {
        return subtitulo.trim();
    }

    public void setSubtitulo(String subtitulo)
    {
        this.subtitulo = subtitulo;
    }

    public String getUrlImagen()
    {
        return urlImagen.trim();
    }

    public void setUrlImagen(String urlImagen)
    {
        this.urlImagen = urlImagen;
    }
    

    
    @Override
    public String toString()
    {
        return "ParserProducto{" + "codigo=" + codigo + ", nombre=" + getNombre() + ", unidades=" + unidades + ", precioBulto=" + precioBulto +
                ", minimo=" + minimo + ", precioLuana=" + precioLuana + ", categoria=" + categoria + ", descripcion=" + descripcion + ", subtitulo=" + subtitulo + '}';
    }

    public String toJSON()
    {
        String json = "{\n";
        
        json+= "\"codigo\":\"" + codigo +"\",\n";
        json+= "\"nombre\":\"" + getNombre() +"\",\n";
        json+= "\"unidades\":\"" + unidades +"\",\n";
        json+= "\"precioBulto\":\"" + precioBulto +"\",\n";
        json+= "\"minimo\":\"" + minimo +"\",\n";
        json+= "\"precioLuana\":\"" + precioLuana +"\",\n";
        json+= "\"categoria\":\"" + categoria +"\",\n";
        json+= "\"urlImagen\":\"" + urlImagen +"\",\n";
        json+= "\"descripcion\":\"" + descripcion +"\",\n";
        json+= "\"subtitulo\":\"" + subtitulo +"\"\n";
        
        json+= "}\n";
        return json;
    }

    
    
}
