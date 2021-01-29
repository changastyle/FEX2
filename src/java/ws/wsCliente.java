package ws;
import com.google.gson.Gson;
import controller.MasterController;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.*;
import modelo.Cliente;
import org.springframework.web.bind.annotation.*;

@RestController
public class wsCliente
{
    @RequestMapping(value = "findClientes")
    public static List<Cliente> findClientes()
    {
        List<Cliente> clientesList = new ArrayList<Cliente>();
        
        String jpql = "SELECT c FROM Cliente c";
        clientesList = dao.DAOEclipse.findAllByJPQL(jpql);
        
        Collections.sort(clientesList);
        
        return clientesList;
    }
    
    @RequestMapping(value = "guardarCliente")
    public static boolean guardarCliente(@RequestParam(value = "strCliente" , defaultValue = "") String strCliente)
    {
        boolean guarde = false;
        boolean modoEdit = false;
        
        Cliente clienteDB = null;
        if(strCliente != null)
        {
            try
            {
                Cliente clienteRecibido = new Gson().fromJson(strCliente, Cliente.class);
                
                if(clienteRecibido != null)
                {
                    // MODO EDIT:
                    if(clienteRecibido.getId() != -1)
                    {
                        clienteDB = (Cliente) dao.DAOEclipse.get(Cliente.class, clienteRecibido.getId());
                        
                        if(clienteDB != null)
                        {
                            // 0 - ACTUALIZO VALORES DEL OBJ.DB CON LOS DEL OBJ.RECIBIDOS:
                            clienteDB.setNombre(clienteRecibido.getNombre());
                            clienteDB.setEmail(clienteRecibido.getEmail());
                            clienteDB.setTelefono(clienteRecibido.getTelefono());
                            clienteDB.setPass(clienteRecibido.getPass());
                            clienteDB.setFoto(clienteRecibido.getFoto());
                            clienteDB.setInstalacion(clienteRecibido.getInstalacion());
                            
                            // ---- ARRFAVORITOSS ----
                            // 1 - BORRO TODAS/OS LAS/LOS ARRFAVORITOSS DE ANTES:
                            boolean borreTodos1 = true;
                            
                            // 2 - ASOCIO LAS/LOS NUEVAS/OS ARRFAVORITOSS:
                                                 
                            clienteDB.setDireccionEntrega(clienteRecibido.getDireccionEntrega());
                            clienteDB.setObservaciones(clienteRecibido.getObservaciones());
                            clienteDB.setActivo(clienteRecibido.getActivo());
                            if( borreTodos1 )
                            {
                                guarde = dao.DAOEclipse.update(clienteDB);
                            }
                        }
                    }
                    else
                    {
                        // 3 - MODO ADD:
                        clienteRecibido.setFechaAlta(new Date());
                        
                        guarde = dao.DAOEclipse.update(clienteRecibido);
                        
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
        
        return guarde;
    }
    
    @RequestMapping(value = "getCliente")
    public static Cliente getCliente(@RequestParam(value = "idCliente" , defaultValue = "-1") int idCliente , HttpServletRequest request)
    {
        Cliente clienteDB = null;
        
        // 2 - VERIFICO LA INSTALACION:
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null && idCliente != -1)
        {
            String jpql = "SELECT c FROM Cliente c WHERE c.id = " +  idCliente +"  AND c.instalacion.id = " + instalacionDB.getId();
            clienteDB = (Cliente) dao.DAOEclipse.getByJPQL(jpql);
        }
        
        return clienteDB;
    }
    
    @RequestMapping(value = "crearClienteNvo")
    public static Cliente crearClienteNvo(String nombre , String email , String token, HttpServletRequest request)
    {
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        // 1 - COMPRUEBO SI YA TENGO UN CLIENTE CON ESE MAIL:
        Cliente clienteNvo = getClienteByEmail(email, request);
        GrupoOperador grupoClientesDB = wsGrupoOperador.getGrupoOperador(1);
        
        // 2 - SI NO HAY NADIE CON ESE MAIL :
        if(clienteNvo == null && instalacionDB != null && grupoClientesDB != null) 
        {
            // 3 - SI ES NUEVO - LE GENERO UNA CLAVE DE 4 DIGITOS:
            String clave = MasterController.generarCodigoCuatroDigitosAleatorio(request);

            // 4 - TODO: DEBO MANDAR MAIL CON SU CLAVE:
            
            // 5 - LE TRAIGO LA FOTO POR DEFAULT:
//            Foto fotoDefault = wsFotoProducto.getFotoUsuarioDefault();
            Foto fotoDefault = new Foto("", instalacionDB, true);
            
            if(nombre == null)
            {
                if(email.contains("@"))
                {
                    int posArroba =email.lastIndexOf("@");
                    
                    if(posArroba != -1)
                    {
                        nombre = email.substring(0, posArroba) ;
                    }
                    else
                    {
                        nombre = email;
                    }
                }
                else
                {
                    nombre = email;
                }
            }
            
            // 6 - GUARDO EN DB:
            clienteNvo = new Cliente(true, "", email, nombre, "", clave, "", grupoClientesDB ,fotoDefault, instalacionDB);
            clienteNvo.setToken(token);
            clienteNvo.setFechaAlta(new Date());
            
            clienteNvo = (Cliente) dao.DAOEclipse.updateReturnObj(clienteNvo); 
            
            // 7 - ENVIO EL MAIL DE BIENVENIDA:
//            wsEmail.enviarEmailBienvenida(clienteNvo,false , request);
        }
        
        return clienteNvo;
    }
    
    
    public static Cliente dameClienteByEmailPassOrTokenOrCreateNewClienteMagicMadnessReturnLogeadoCreoPasswordAleatorio
    (
         boolean compraANombreDe,
         String email,
         String pass,
         String token,
         String nombre,
         HttpServletRequest request,
         HttpServletResponse response
    )
    {
        // 1 - BUSCO EL CLIENTE EN DB POR EMAIL Y PASS || EMAIL Y TOKEN AND OBVIAMENTE POR INSTALACION:
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        Cliente clienteDB = wsCliente.getClienteEmpty();
        Cliente clienteSesionado = wsLogin.dameClienteLogeado(request);
        
        if(!compraANombreDe && clienteSesionado != null && clienteSesionado.getId() != -1)
        {
            // 2 - YA TENGO UN CLIENTE EN VARIABLE DE SESION - LO DEVUELVO:
            int fkClienteSesionado = clienteSesionado.getId();
            clienteDB = wsCliente.getCliente(fkClienteSesionado, request);
        }
        else if(email != null && (pass != null || token != null) && email.length() > 0 && (pass.length() > 0 || token.length() > 0))
        {
            // 3 - SI NO TENGO NADA SESIONADO - BUSCO SI TENGO UN CLIENTE EN DB CON ESE MAIL Y PASS || MAIL Y TOKEN:
            clienteDB = wsCliente.getClienteByEmailPassOrToken(email, pass, token, request);
            
            if(clienteDB != null)
            {
                // 4 - MUESTRO EL ID CON EL QUE SE INSERTO EL NVO CLIENTE: 
                System.out.println("EL NVO CLIENTE TIENE ID:" + clienteDB.getId());
                
                // 5 - SI LO TENGO - LO SESIONO Y DEVUELVO:
                MasterController.sesionarCliente(clienteDB , request, response);
            }
        }
        else
        {
            // 5 - SI NO LO TENGO SESIONADO Y NO EXISTE EN DB - LO CREO - LO SESIONO - LO DEVUELVO:
            if(instalacionDB != null && email != null)
            {
                // 6 - SI NO EXISTE NADIE CON ESE EMAIL - LO CREO (SE GENERA PASS ACA ADENTRO):
                Cliente clienteNvo = wsCliente.crearClienteNvo(nombre, email, token, request);
                
                // 7 - SI SE LOGRO GUARDAR EN DB Y ME DEVOLVIO ID - LO SESIONO - LO DEVUELVO:
                if(clienteNvo != null && clienteNvo.getId() != -1)
                {
                    // 8 - LO SESIONO:
                    if(!compraANombreDe)
                    {
                        MasterController.sesionarCliente(clienteNvo , request , response);
                    }
                    
                    // 9 - LO DEVUELVO:
                    clienteDB = clienteNvo;
                }
            }
        }
        
        return clienteDB;
    }
    @RequestMapping(value = "getClienteByEmailPassOrToken")
    public static Cliente getClienteByEmailPassOrToken
    (
        @RequestParam(value = "email" , required = true) String email ,
        @RequestParam(value = "pass " , required = true) String pass ,
        @RequestParam(value = "token " , required = true) String token ,
        HttpServletRequest request
    )
    {
        Cliente clienteDB = null;
        
        // 2 - VERIFICO LA INSTALACION:
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null && email != null &&  (pass != null || token != null))
        {
            String jpql = "SELECT c FROM Cliente c WHERE c.instalacion.id = " + instalacionDB.getId() + " AND (c.email = \"" + email + "\" AND (c.pass = \"" + pass + "\" OR c.token = \"" + token + "\")) ";
            clienteDB = (Cliente) dao.DAOEclipse.getByJPQL(jpql);
        }
        
        
        return clienteDB;
    }
    
     @RequestMapping(value = "yaExisteCliente")
    public static boolean yaExisteCliente
    (
        @RequestParam(value = "email" , required = true) String email ,
        HttpServletRequest request
    )
    {
        boolean ok = false;
        
        if(getClienteByEmail(email, request) != null)
        {
            ok = true;
        }
        
        return ok;
    }
    public static Cliente getClienteByEmail
    (
        @RequestParam(value = "email" , required = true) String email ,
        HttpServletRequest request
    )
    {
        Cliente clienteDB = null;
        
        // 2 - VERIFICO LA INSTALACION:
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null && email != null)
        {
            String jpql = "SELECT c FROM Cliente c WHERE c.instalacion.id = " + instalacionDB.getId() +"  AND c.email = \"" + email + "\" ";
            clienteDB = (Cliente) dao.DAOEclipse.getByJPQL(jpql);
        }
        
        return clienteDB;
    }
    
    @RequestMapping(value = "getClienteEmpty")
    public static Cliente getClienteEmpty()
    {
       Cliente clienteEmpty = new Cliente();
       clienteEmpty.setId(-1);
       return clienteEmpty;
    }
}
