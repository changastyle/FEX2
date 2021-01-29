package ws;
import archivos.Encoding;
import com.google.gson.Gson;
import controller.MasterController;
import java.util.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.*;
import modelo.Cliente;
import modelo.Cliente;
import modelo.Instalacion;
import org.springframework.web.bind.annotation.*;
import static ws.wsCliente.getClienteByEmail;

@RestController
public class wsLogin
{
//    @RequestMapping(value = "setCookie")
//    public static String setCookie
//    (
//        @RequestParam(value = "nombreCookie" , required = true) String nombreCookie ,
//        @RequestParam(value = "valorCookie" , required = true)  String valorCookie,
//        HttpServletRequest request, HttpServletResponse response
//    )
//    {
//        Cookie myCookie = new Cookie(nombreCookie, valorCookie);
////        long ahora = new Date().getTime();
////        long treintaDias = 30 * 24 * 60 *60 *1000;
////        long ahoraMasTreintaDias = ahora + treintaDias;
//        
////        Date fechaExpira = new Date(ahoraMasTreintaDias);
////        System.out.println("FECHA EXPIRA:" + MasterController.formatearFechaAAlgoBonito2(fechaExpira, true, true));
////        myCookie.setMaxAge((int)treintaDias);
//        response.addCookie(myCookie);
//        
////        Date expdate = new Date ();
////        expdate.setTime (expdate.getTime() + (3600 * 1000));
////        String cookieExpire = "expires=" + expdate.toGMTString();
//        
//        return valorCookie;
//    }
    @RequestMapping(value = "dameCookie")
    public static String dameCookie(@RequestParam(value = "nombreCookie" , required = true) String nombreCookie , HttpServletRequest request)
    {
        String valorCookie = null;
        
        Cookie[] cookies = request.getCookies();

        for ( int i=0; i<cookies.length; i++)
        {
            Cookie cookieLoop = cookies[i];

            if (nombreCookie.equals(cookieLoop.getName()))
            {
                valorCookie = cookieLoop.getValue();
            }
        }
        
        return valorCookie;
    }
    @RequestMapping(value = "dameClienteLogeado")
    public static Cliente dameClienteLogeado(HttpServletRequest request)
    {
        Cliente clienteLogeado = wsCliente.getClienteEmpty();
        
        clienteLogeado = MasterController.dameClienteLogeadoFromDB(request);
        
        if(clienteLogeado == null)
        {
            clienteLogeado = wsCliente.getClienteEmpty();
        }
        
        return clienteLogeado;
    }
    @RequestMapping(value = "sesionarCliente")
    public static String sesionarCliente
    (
        @RequestParam(value = "email" , required = true) String email ,
        @RequestParam(value = "pass" ,required = false) String pass ,
        @RequestParam(value = "token" , required = false) String token ,
        @RequestParam(value = "nombre" , required = false) String nombre ,
        HttpServletRequest request,
        HttpServletResponse response
    )
    {
        String tokenRta = "";
        boolean logeado = false;
        
        if(nombre != null && nombre.trim().length() > 0)
        {
            nombre = Encoding.fix(nombre);
        }
        
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        Cliente clienteDB = wsCliente.getClienteByEmailPassOrToken(email, pass , token , request);
        
        if(clienteDB != null && pass != null)
        {
            logeado = MasterController.sesionarCliente(clienteDB, request , response);
            
            if(logeado)
            {
                tokenRta = clienteDB.getToken();
            }
        }
        else
        {
            if(instalacionDB != null && token != null)
            {
                // SI NO EXISTE NADIE CON ESE EMAIL, LO CREO:
                Cliente clienteNvo = wsCliente.crearClienteNvo(nombre, email, token, request);
                if(clienteNvo != null && clienteNvo.getId() != -1)
                {
                    logeado = MasterController.sesionarCliente(clienteNvo , request , response);
                    
                    if(logeado)
                    {
                        tokenRta = token;
                    }
                }
            }
        }
        
        return tokenRta;
    }
    @RequestMapping(value = "dameValorSesion")
    public static String dameValorSesion(HttpServletRequest request)
    {
        String valor = "";
                
        String nombreVarSesion = MasterController.dameConfigMaster().getVarSessionHTTP();
        valor = (String) request.getSession().getAttribute(nombreVarSesion);

        return valor;
    }
    @RequestMapping(value = "exit")
    public static boolean exit(HttpServletRequest request , HttpServletResponse response)
    {
        boolean exit = MasterController.exit(request , response);
        
        return exit;
    }
    @RequestMapping(value = "olvideMiClave")
    public static boolean olvideMiClave(@RequestParam(value = "email" , required = true) String email ,HttpServletRequest request , HttpServletResponse response)
    {
        boolean exit = MasterController.exit(request , response);
        
//        Cliente clienteDB = wsCliente.dameClienteByEmailPassOrTokenOrCreateNewClienteMagicMadnessReturnLogeadoCreoPasswordAleatorio(false, email, null, null, email,request, response);
        Cliente clienteDB = wsCliente.getClienteByEmail(email , request);
        
        if(clienteDB != null)
        {
            System.out.println("ESTAMOS ENVIANDO EMAIL CON CLAVE: " + clienteDB.getPass() );
//            wsEmail.enviarEmailBienvenida(clienteDB, true, request);
        }
        
        return exit;
    }
}
