package modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import javax.persistence.*;

@Entity @Table(name = "clientes")
public class Cliente implements Comparable<Cliente>
{
    //ATRIBUTOS:
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean activo;
    private String direccionEntrega;
    private String email;
    private String nombre;
    private String observaciones;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta;
    @JsonIgnore
    private String pass;
    @JsonIgnore
    private String token;
    private String telefono;
    @OneToOne() @JoinColumn(name = "fkGrupo")
    private GrupoOperador grupo;
    @OneToOne() @JoinColumn(name = "fkFoto")
    private Foto foto;
    @OneToOne() @JoinColumn(name = "fkInstalacion") @JsonIgnore
    private Instalacion instalacion;
    
    //CONTRUCTOR VACIO:
    public Cliente() 
    {
        fechaAlta = new Date();
    }
    
    //CONTRUCTOR PARAMETROS SIN LISTAS:
    public Cliente(boolean activo,String direccionEntrega,String email,String nombre,String observaciones,String pass,String telefono,GrupoOperador grupo,Foto foto,Instalacion instalacion)
    {
        this.activo = activo;
        this.direccionEntrega = direccionEntrega;
        this.email = email;
        this.nombre = nombre;
        this.observaciones = observaciones;
        this.pass = pass;
        this.telefono = telefono;
        this.grupo = grupo;
        this.foto = foto;
        this.instalacion = instalacion;
        fechaAlta = new Date();
    }
    


    //<editor-fold desc="GETTERS Y SETTERS:">
    public int getId() 
    {
        return id;
    }
    public boolean getActivo() 
    {
        return activo;
    }
    public String getDireccionEntrega() 
    {
        return direccionEntrega;
    }
    public String getEmail() 
    {
        return email;
    }
    public String getNombre() 
    {
        return nombre;
    }
    public String getObservaciones() 
    {
        return observaciones;
    }
    public String getPass() 
    {
        return pass;
    }
    public String getTelefono() 
    {
        return telefono;
    }
    public Foto getFoto() 
    {
        return foto;
    }
    public Instalacion getInstalacion() 
    {
        return instalacion;
    }

    public GrupoOperador getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoOperador grupo) {
        this.grupo = grupo;
    }
    

    //SET
    public void setId( int id ) 
    {
        this.id = id;
    }
    public void setActivo( boolean activo ) 
    {
        this.activo = activo;
    }
    public void setDireccionEntrega( String direccionEntrega ) 
    {
        this.direccionEntrega = direccionEntrega;
    }
    public void setEmail( String email ) 
    {
        this.email = email;
    }
    public void setNombre( String nombre ) 
    {
        this.nombre = nombre;
    }
    public void setObservaciones( String observaciones ) 
    {
        this.observaciones = observaciones;
    }
    public void setPass( String pass ) 
    {
        this.pass = pass;
    }
    public void setTelefono( String telefono ) 
    {
        this.telefono = telefono;
    }
    public void setFoto( Foto foto ) 
    {
        this.foto = foto;
    }
    public void setInstalacion( Instalacion instalacion ) 
    {
        this.instalacion = instalacion;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public Date getFechaAlta()
    {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta)
    {
        this.fechaAlta = fechaAlta;
    }
    
   
    //</editor-fold>
    
    //@Override
    public String toString()
    {
        String str = "{";
        str += "id:" + id + ", ";
        str += "activo:" + activo + ", ";
        str += "direccionEntrega:" + direccionEntrega + ", ";
        str += "email:" + email + ", ";
        str += "nombre:" + nombre + ", ";
        str += "observaciones:" + observaciones + ", ";
        str += "pass:" + pass + ", ";
        str += "token:" + token + ", ";
        str += "telefono:" + telefono + ", ";
        str += "foto:" + foto + ", ";
        str += "instalacion:" + instalacion + ", ";
        
        
        str += "}";
        
        return str;
    }

 
        
    
    //DYN:
    public String toJSON()
    {
        String json = "{";
        
        json += "\"id\":" + id + ",";
        json += "\"nombre\":\"" + nombre + "\",";
        json += "\"email\":\"" + email + "\",";
        json += "\"telefono\":\"" + telefono + "\",";
        
        if(foto != null)
        {
            json += "\"foto\":" + foto.toJSON() + ",";
        }
        
        if(grupo != null)
        {
            json += "\"grupo\":" + grupo.toJSON() + ",";
        }
        
        json += "\"direccionEntrega\":\"" + direccionEntrega + "\"";
        json += "}";
        
        return json;
    }
    
    public int compareTo(Cliente otro)
    {
        return 1;
    }
    
}
