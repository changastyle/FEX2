package controller;

import ws.wsLogin;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import extra.DateJsonParser;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.*;
import java.io.File;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
//import org.apache.commons.lang3.StringUtils;
import ws.wsCliente;
import ws.wsConfiguraciones;
import ws.wsInstalacion;


public class MasterController
{
    public static long timestampUltimaActualizacionConfiguracion = -1; 
    private static Configuracion masterConfig;
    private final static String nombreCookieEmail = "ecommerceEmail" ;
    private final static String nombreCookieClave = "ecommerceClave" ;
    
    public static Configuracion dameConfigMaster()
    {
        Date ahora = new Date();
        long timestampActual = ahora.getTime();
        
        if(timestampUltimaActualizacionConfiguracion == -1 || timestampActual > (timestampUltimaActualizacionConfiguracion + ( 3600 * 1 * 1000)) )
        {
            masterConfig = wsConfiguraciones.dameConfigActual();
            timestampUltimaActualizacionConfiguracion = timestampActual;
        }
        
        return masterConfig;
    }

    public static Gson dameGson()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class,new DateJsonParser()).create();
        return gson;
    }
    
    public static Instalacion dameInstalacionByRequest(HttpServletRequest request)
    {
        Instalacion instalacionSegunURL = null;
        
        if(request != null)
        {
            String url = request.getRequestURL().toString();

            if(url != null)
            {
                for(Instalacion instalacionLoop: wsInstalacion.findInstalacions())
                {
                    String urlInstalacionLoop  = instalacionLoop.getUrlDominio();
                    String urlInstalacionDosLoop  = instalacionLoop.getUrlDominioDos();

                    // URL INSTALACION 1:
                    if(urlInstalacionLoop != null && urlInstalacionLoop.trim().length() > 0)
                    {
                        if(url.contains(urlInstalacionLoop))
                        {
                            instalacionSegunURL = instalacionLoop;
                        }
                    }
                    
                    // URL INSTALACION 2:
                    if(urlInstalacionDosLoop != null && urlInstalacionDosLoop.trim().length() > 0)
                    {
                        if(url.contains(urlInstalacionDosLoop))
                        {
                            instalacionSegunURL = instalacionLoop;
                        }
                    }
                }
            }
        }
        
        return instalacionSegunURL;
    }
    public static String dameRutaFS (String urlProvisoria , Instalacion instalacionDB)
    {
        String rta = null;
        
        Configuracion config = dameConfigMaster();
        if(config != null && instalacionDB != null)
        {
//            String rutaLogoDefaultFS = config.getRutaFileSystem() + File.separator + instalacionDB.getCarpetaWeb() + File.separator + instalacionDB.getLogo().getUrlProvisoria();
            String rutaLogoDefaultFS = config.getRutaFileSystem() + File.separator + instalacionDB.getCarpetaWeb() + File.separator + urlProvisoria;
            rta = rutaLogoDefaultFS.replaceAll("/", "" + File.separatorChar);
        }
        
        
        return rta;
    }
    
    
    
    
    // LOGIN DE USUARIOS Y OPERADORES:
    public static boolean sesionarCliente(Cliente cliente , HttpServletRequest request , HttpServletResponse response)
    {
        boolean sesionado = false;
        
        // 1 - DE UN CLIENTE JAVA LO COVIERTO A JSON - PARA PODER GUARDAR ESO COMO UN STRING EN VARIABLE DE SESION:
        String strClienteJSON = cliente.toJSON();
        
        if(strClienteJSON != null)
        {
            if(dameConfigMaster() != null)
            {
                String nombreVarSesion = masterConfig.getVarSessionHTTP();
                request.getSession().setAttribute(nombreVarSesion, strClienteJSON);
                sesionado = true;
                
                //LO HACE JS:
//                wsLogin.setCookie(nombreCookieEmail, cliente.getEmail(), request, response);
//                wsLogin.setCookie(nombreCookieClave, cliente.getToken(), request, response);
            }
        }
        
        return sesionado;
    }
    public static Cliente dameClienteLogeadoFromDB(HttpServletRequest request)
    {
        // 1 - INICIALIZO UN USUARIO VACIO: 
        Cliente clienteDB = null;
        Cliente clienteJSON = null;
        
        // 2 - VERIFICO LA INSTALACION:
        Instalacion instalacionDB = MasterController.dameInstalacionByRequest(request);
        
        if(instalacionDB != null)
        {
            // 3 - BUSCO EN LA VARIABLE DE SESION EL ATRIBUTO RESTAURANTE Y LO CONVIERTO A JSON:
            if(dameConfigMaster() != null)
            {
                String nombreVarSesion = masterConfig.getVarSessionHTTP();
                if(request != null && nombreVarSesion != null)
                {
                    HttpSession session = request.getSession();
                    if(session != null)
                    {
                        String valorCookieEmail = wsLogin.dameCookie(nombreCookieEmail, request);
                        String valorCookieClave = wsLogin.dameCookie(nombreCookieClave, request);
                        
                        if(session.getAttribute(nombreVarSesion) != null || (valorCookieEmail != null && valorCookieClave != null))
                        {
                            if(session.getAttribute(nombreVarSesion) != null)
                            {
                                String strClienteJSON = (String) request.getSession().getAttribute(nombreVarSesion);
                                System.out.println("EN LA VARIABLE DE SESION (" + nombreVarSesion + "):" + strClienteJSON);
                                
                                // 4 - CONVIERTO DE JSON A JAVA:
                                if(strClienteJSON != null)
                                {
                                    clienteJSON = new Gson().fromJson(strClienteJSON, Cliente.class);
                                    if(clienteJSON != null)
                                    {
                                        clienteDB = wsCliente.getCliente(clienteJSON.getId(), request);
                                    }
                                }
                            }
                            else
                            {
                                clienteDB = wsCliente.getClienteByEmailPassOrToken(valorCookieEmail, null, valorCookieClave, request);
                            }
                        }
                    }
                }

                // 5 - SI EL CLIENTE.ID ES DISTINTO DE -1 - LO BUSCO EN DB:
//                if(clienteRta != null)
//                {
//                }
            }
        }
        
        if(clienteDB == null)
        {
            clienteDB = wsCliente.getClienteEmpty();
        }
        
        
        return clienteDB;
    }
    public static boolean exit(HttpServletRequest request, HttpServletResponse response)
    {
        boolean deslogeado = false;
        
        if(dameConfigMaster() != null)
        {
            String nombreVarSesion = masterConfig.getVarSessionHTTP();
            String nombreVarOperador = "operador" + nombreVarSesion;
            
            request.getSession().setAttribute(nombreVarSesion, null);
            request.getSession().setAttribute(nombreVarOperador, null);

            // LO HACE JS:
//            wsLogin.setCookie(nombreCookieEmail, null, request, response);
//            wsLogin.setCookie(nombreCookieClave, null , request, response);
            
            deslogeado = true;
        }
        
        return deslogeado;
    }
    
     // SOLO GENERA UN NUMERO DE 4 DIGITOS, NO SESIONA NI NADA
    public static String generarCodigoCuatroDigitosAleatorio(HttpServletRequest request)
    {
        String codigoGenerado = "";
        boolean yaLoTengo = true;
        int reintentos = 0;
        
        codigoGenerado = "1234";
        
//        while(yaLoTengo )
//        {
//            // 1 - GENERO CODIGO:
//            codigoGenerado = "";
//            for(int i = 0  ; i < 6 ;i++)
//            {
//                int numeroAleatorio = (int) (Math.random() * 9 ) + 1;
//                codigoGenerado += numeroAleatorio;
//            }
//            
//            // 2 - COMPRUEBO SI YA EXISTE EN EL LISTADO:
//            Pedido pedidoVolatilDB = wsPedido.damePedidoVolatil(codigoGenerado, request);
//            if(pedidoVolatilDB == null)
//            {
//                yaLoTengo = false;
//            }
//            
//            reintentos++;
//        }
        
        
        return codigoGenerado;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static Date convertimeStrDateIntoRealDate(String strDate , boolean vaConHoras)
    {
        Date fecha = null;
        
        if(strDate != null)
        {
            if(strDate.trim().length() > 0)
            {
                if(strDate.equalsIgnoreCase("1000-01-01"))
                {
                    fecha = dameFechaTrucha();
                }
                else
                {
                    int posPrimerSlash = strDate.indexOf("/");
                    int posSegundoSlash = strDate.lastIndexOf("/");
                    int posPunto = strDate.indexOf(":");
                    boolean usoSlash = true;

                    if(posPrimerSlash == -1 || posSegundoSlash == -1)
                    {
                        posPrimerSlash = strDate.indexOf("-");
                        posSegundoSlash = strDate.lastIndexOf("-");
                        usoSlash = false;
                    }
                    
                    
                    if(posPrimerSlash != -1 && posSegundoSlash != -1)
                    {
                        String strD;
                        String strMM;
                        String strY;
                        if(usoSlash)
                        {
                            strMM = strDate.substring( 0 , posPrimerSlash);
                            strD = strDate.substring( (posPrimerSlash + 1) ,posSegundoSlash);
                            strY = ""  + (1900 +  Integer.parseInt(strDate.substring(posSegundoSlash + 1) ,(posSegundoSlash + 3)));
                        }
                        else
                        {
                            strD = strDate.substring( (posSegundoSlash+1) , (posSegundoSlash+3));
                            strMM = strDate.substring( (posPrimerSlash + 1) ,posSegundoSlash);
                            strY = strDate.substring( 0 , posPrimerSlash);
                        }
                        
                        String strH = "0";
                        String strM = "0";
                        if(vaConHoras)
                        {
                            if(usoSlash)
                            {
                                strH = strDate.substring( (posSegundoSlash + 4) ,posPunto);
                                strM = strDate.substring( (posPunto + 1) ,strDate.length());
                            }
                            else
                            {
                                strH = strDate.substring( (posSegundoSlash+4) , (posSegundoSlash+6));;
                                strM = strDate.substring( (posSegundoSlash+7) , (posSegundoSlash+9));
                            }
                        }

                        
        //                System.out.println("d = " + strD);
        //                System.out.println("MM = " + strMM);
        //                System.out.println("y = " + strY);
        //                System.out.println("h = " + strH);
        //                System.out.println("m = " + strM);

                        int d = Integer.parseInt(strD);
                        int mm = Integer.parseInt(strMM);
                        int y = Integer.parseInt(strY);
                        
                        int h = Integer.parseInt(strH);
                        int m = Integer.parseInt(strM);
                        
                        fecha = new Date((y-1900) , (mm - 1) , d , h , m , 0 );
                    }
                }
            }
        }
        
        return fecha;
    }
    public static String formatearFechaAAlgoBonito(Date fecha, boolean ponerHoras)
    {
        Date hoy = new Date();

        String fechaFormateada = "";

        int dia = fecha.getDate();
        int mes = fecha.getMonth() + 1 ;
        int year = fecha.getYear() + 1900;
        int hora = fecha.getHours();
        int minutos = fecha.getMinutes();

        String strDia;
        String strDiaSemana = "";
        String strMes;
        String strYear;
        String strH;
        String strM;


        //DIA:
        if(dia < 10)
        {
            strDia = "0" + dia;
        }
        else
        {
            strDia = "" + dia;
        }

        //MES:
        if(mes < 10)
        {
            strMes = "0" + mes;
        }
        else
        {
            strMes = "" + mes;
        }

        //YEAR:
        strYear = "" + year;

        //HORA:
        if(hora < 10)
        {
            strH = "0" + hora;
        }
        else
        {
            strH = "" + hora;
        }

        //MINUTOS:
        if(minutos < 10)
        {
            strM = "0" + minutos;
        }
        else
        {
            strM = "" + minutos;
        }
       
        strDiaSemana = resuelveDiaDeLaSemana(fecha.getDay(), true);

        
        if(dia == hoy.getDate() && (mes == hoy.getMonth() + 1))
        {
            if(ponerHoras)
            {
                fechaFormateada = strH + ":" + strM + " hs";
            }
            else
            {
                fechaFormateada = "Hoy";
            }
        }
        else if((mes == hoy.getMonth() + 1))
        {
            if(ponerHoras)
            {
                fechaFormateada = strDiaSemana + " " +  strDia + " (" + strH + ":" + strM + " hs)";
            }
            else
            {
                fechaFormateada = strDiaSemana + " " +  strDia;
            }
        }
        else
        {
            if(ponerHoras)
            {
                fechaFormateada =  strDia + "/" + strMes + "/" + strYear + " (" + strH + ":" + strM +" hs)";
            }
            else
            {
                fechaFormateada =  strDia + "/" + strMes + "/" + strYear;
            }
        }


        return  fechaFormateada;
    }
    public static String formatearFechaAAlgoBonito2(Date fecha, boolean ponerHoras , boolean formatoMesLargo)
    {
        Date hoy = new Date();

        String fechaFormateada = "";

        if(fecha != null)
        {

            int dia = fecha.getDate();
            int mes = fecha.getMonth() + 1 ;
            int year = fecha.getYear() + 1900;
            int hora = fecha.getHours();
            int minutos = fecha.getMinutes();

            String strDia;
            String strDiaSemana = "";
            String strMes = "";
            String strYear;
            String strH;
            String strM;


            //DIA:
            if(dia < 10)
            {
                strDia = "0" + dia;
            }
            else
            {
                strDia = "" + dia;
            }

            //MES:
            if(formatoMesLargo)
            {
                switch(mes)
                {
                    case 1 : strMes = "Ene";break;
                    case 2 : strMes = "Feb";break;
                    case 3 : strMes = "Mar";break;
                    case 4 : strMes = "Abr";break;
                    case 5 : strMes = "May";break;
                    case 6 : strMes = "Jun";break;
                    case 7 : strMes = "Jul";break;
                    case 8 : strMes = "Ago";break;
                    case 9 : strMes = "Sep";break;
                    case 10 : strMes = "Oct";break;
                    case 11 : strMes = "Nov";break;
                    case 12 : strMes = "Dic";break;
                    default: strMes = "Ene"; break;
                }
            }
            else
            {
                if(mes < 10)
                {
                    strMes = "0" + mes;
                }
                else
                {
                    strMes = "" + mes;
                }
            }

            //YEAR:
            strYear = "" + year;

            //HORA:
            if(hora < 10)
            {
                strH = "0" + hora;
            }
            else
            {
                strH = "" + hora;
            }

            //MINUTOS:
            if(minutos < 10)
            {
                strM = "0" + minutos;
            }
            else
            {
                strM = "" + minutos;
            }

            strDiaSemana = resuelveDiaDeLaSemana(fecha.getDay()  , true);


            String strFecha = "";
            if(formatoMesLargo)
            {
                strFecha = strDiaSemana + ", " + strDia + " " + strMes;
            }
            else
            {
                strFecha = strDiaSemana + " " + strDia + "/" + strMes + "/" + strYear;
            }

            if(ponerHoras)
            {
                fechaFormateada = strFecha + " (" + strH+ ":" + strM +")";
            }
            else
            {
                fechaFormateada = strFecha;
            }
        }

        return  fechaFormateada;
    }
    public static String formatearFechaAAlgoBonitoCompleta(Date fecha)
    {
        Date hoy = new Date();

        String fechaFormateada = "";

        int dia = fecha.getDate();
        int mes = fecha.getMonth() + 1 ;
        int year = fecha.getYear() + 1900;
        int hora = fecha.getHours();
        int minutos = fecha.getMinutes();

        String strDia;
        String strDiaSemana = "";
        String strMes;
        String strYear;
        String strH;
        String strM;


        //DIA:
        if(dia < 10)
        {
            strDia = "0" + dia;
        }
        else
        {
            strDia = "" + dia;
        }

        //MES:
        if(mes < 10)
        {
            strMes = "0" + mes;
        }
        else
        {
            strMes = "" + mes;
        }

        //YEAR:
        strYear = "" + year;

        //HORA:
        if(hora < 10)
        {
            strH = "0" + hora;
        }
        else
        {
            strH = "" + hora;
        }

        //MINUTOS:
        if(minutos < 10)
        {
            strM = "0" + minutos;
        }
        else
        {
            strM = "" + minutos;
        }
       
        strDiaSemana = resuelveDiaDeLaSemana(fecha.getDay(), true);
        
        fechaFormateada =  strDia + "/" + strMes + "/" + strYear + " (" + strH + ":" + strM +" hs)";


        return  fechaFormateada;
    }
    public static String formatearFechaKandas(Date fecha)
    {
        Date hoy = new Date();

        String fechaFormateada = "";

        if(fecha != null)
        {

            int dia = fecha.getDate();
            int mes = fecha.getMonth() + 1 ;
            int year = fecha.getYear() + 1900;
            int hora = fecha.getHours();
            int minutos = fecha.getMinutes();

            String strDia;
            String strDiaSemana = resuelveDiaDeLaSemana(fecha.getDay(), true);
            String strMes = "";
            String strYear;
            String strH;
            String strM;


            //DIA:
            if(dia < 10)
            {
                strDia = "0" + dia;
            }
            else
            {
                strDia = "" + dia;
            }

            //MES:
            switch(mes)
            {
                case 1 : strMes = "Enero";break;
                case 2 : strMes = "Febrero";break;
                case 3 : strMes = "Marzo";break;
                case 4 : strMes = "Abril";break;
                case 5 : strMes = "Mayo";break;
                case 6 : strMes = "Junio";break;
                case 7 : strMes = "Julio";break;
                case 8 : strMes = "Agosto";break;
                case 9 : strMes = "Septiembre";break;
                case 10 : strMes = "Octubre";break;
                case 11 : strMes = "Noviembre";break;
                case 12 : strMes = "Diciembre";break;
                default: strMes = "Enero"; break;
            }

            //YEAR:
            strYear = "" + year;

            //HORA:
            if(hora < 10)
            {
                strH = "0" + hora;
            }
            else
            {
                strH = "" + hora;
            }

            //MINUTOS:
            if(minutos < 10)
            {
                strM = "0" + minutos;
            }
            else
            {
                strM = "" + minutos;
            }

            strDiaSemana = resuelveDiaDeLaSemana(fecha.getDay(), true);


            String strFecha = "";
            fechaFormateada = strDiaSemana + " " + strDia + ", " + strMes + " " + strYear;

        }

        return  fechaFormateada;
    }
    public static String formatearFechaN(Date fecha)
    {
        String fechaFormateada = "";

        if(fecha != null)
        {

            int dia = fecha.getDate();
            int mes = fecha.getMonth() + 1 ;
            int year = fecha.getYear() + 1900;
            int hora = fecha.getHours();
            int minutos = fecha.getMinutes();

            String strDia;
            String strDiaSemana = resuelveDiaDeLaSemana(fecha.getDay() , false);
            String strMes = "";
            String strYear;
            String strH;
            String strM;


            //DIA:
            if(dia < 10)
            {
                strDia = "0" + dia;
            }
            else
            {
                strDia = "" + dia;
            }

            //MES:
            if(mes < 10)
            {
                strMes = "0" + mes;
            }
            else
            {
                strMes = "" + mes;
            }

            //YEAR:
            strYear = "" + year;

            //HORA:
            if(hora < 10)
            {
                strH = "0" + hora;
            }
            else
            {
                strH = "" + hora;
            }

            //MINUTOS:
            if(minutos < 10)
            {
                strM = "0" + minutos;
            }
            else
            {
                strM = "" + minutos;
            }


            String strFecha = "";
            fechaFormateada = strDiaSemana + " " + strDia + "/" + strMes + "/" + strYear;

        }

        return  fechaFormateada;
    }
    public static String resuelveDiaDeLaSemana(int dia  , boolean acronimo)
    {
        String strDiaSemana = "";
        if(acronimo)
        {
            switch (dia)
            {
                case 0: strDiaSemana = "Dom"; break;
                case 1: strDiaSemana = "Lun"; break;
                case 2: strDiaSemana = "Mar"; break;
                case 3: strDiaSemana = "Mie"; break;
                case 4: strDiaSemana = "Jue"; break;
                case 5: strDiaSemana = "Vie"; break;
                case 6: strDiaSemana = "Sab"; break;

            }
        }
        else
        {
            switch (dia)
            {
                case 0: strDiaSemana = "Domingo"; break;
                case 1: strDiaSemana = "Lunes"; break;
                case 2: strDiaSemana = "Martes"; break;
                case 3: strDiaSemana = "Miercoles"; break;
                case 4: strDiaSemana = "Jueves"; break;
                case 5: strDiaSemana = "Viernes"; break;
                case 6: strDiaSemana = "Sabado"; break;

            }
        }
        
        return strDiaSemana;
    }
    public static String resuelveStrMes(int mes)
    {
        String strMes = "";
        switch (mes)
        {
            case 1: strMes = "Enero"; break;
            case 2: strMes = "Febrero"; break;
            case 3: strMes = "Marzo"; break;
            case 4: strMes = "Abril"; break;
            case 5: strMes = "Mayo"; break;
            case 6: strMes = "Junio"; break;
            case 7: strMes = "Julio"; break;
            case 8: strMes = "Agosto"; break;
            case 9: strMes = "Septiembre"; break;
            case 10: strMes = "Octubre"; break;
            case 11: strMes = "Noviembre"; break;
            case 12: strMes = "Diciembre"; break;
        }
        return strMes;
    }
    public static String calcularTiempoTranscurrido(Date timestampACalcular)
    {
        long inicioCalculo = System.currentTimeMillis();
        String tiempoTranscurrido = "0 seg";
        
        Date ahoraRightNow = new Date();
        
        long segundo = 1000;
        long minuto = 60 * segundo;
        long hora = 60 * minuto;
        long dia = 24 * hora;
        long semana = 7 * dia;
        long mes = 30 * dia;
        long year = 12 * mes;
        
        int contadorYear = 0;
        int contadorMes = 0;
        int contadorDia = 0;
        int contadorSemana = 0;
        int contadorHora = 0;
        int contadorMinuto = 0;
        int contadorSegundo = 0;
        
        if(timestampACalcular != null)
        {
            long diferencia =  (long) (ahoraRightNow.getTime() - timestampACalcular.getTime());
            if(diferencia > 0)
            {
                if(diferencia > year)
                {
                    contadorYear = Math.round(diferencia / year);
                }
                else
                {
                    //DIA:
                    if(diferencia > dia)
                    {
                        contadorDia = Math.round(diferencia / dia);
                    }
                    else
                    {
                        //HORA:
                        if(diferencia > hora)
                        {
                            contadorHora = Math.round(diferencia / hora);
                        }
                        else
                        {
                            //MINUTO:
                            if(diferencia > minuto)
                            {
                                contadorMinuto = Math.round(diferencia / minuto);
                            }
                            else
                            {
                                //SEGUNDO:
                                if(diferencia > segundo)
                                {
                                    contadorSegundo = Math.round(diferencia / segundo);
                                }
                            }
                        }
                    }
                }
            }

            if(contadorYear  != 0 )
            {
                if(contadorYear > 1)
                {
                    tiempoTranscurrido = contadorYear + " años";
                }
                else
                {
                    tiempoTranscurrido = contadorYear + " año";
                }
            }
            if(contadorMes  != 0 )
            {
                if(contadorMes > 1)
                {
                    tiempoTranscurrido = contadorMes + " meses";
                }
                else
                {
                    tiempoTranscurrido = contadorMes + " mes";
                }
            }
            if(contadorSemana  != 0 )
            {
                if(contadorSemana > 1)
                {
                    tiempoTranscurrido = contadorSemana + " sem";
                }
                else
                {
                    tiempoTranscurrido = contadorSemana + " semana";
                }
            }
            if(contadorDia  != 0 )
            {
                if(contadorDia > 1)
                {
                    tiempoTranscurrido = contadorDia + " dias";
                }
                else
                {
                    tiempoTranscurrido = contadorDia + " dia";
                }
            }
            if(contadorHora  != 0 )
            {
                if(contadorHora > 1)
                {
                    tiempoTranscurrido = contadorHora + " horas";
                }
                else
                {
                    tiempoTranscurrido = contadorHora + " hora";
                }
            }
            if(contadorMinuto  != 0 )
            {
                if(contadorMinuto > 1)
                {
                    tiempoTranscurrido = contadorMinuto + " min";
                }
                else
                {
                    tiempoTranscurrido = contadorMinuto + " min";
                }
            }
            if(contadorSegundo  != 0 )
            {
                if(contadorSegundo > 1)
                {
                    tiempoTranscurrido = contadorSegundo + " seg";
                }
                else
                {
                    tiempoTranscurrido = contadorSegundo + " seg";
                }
            }
            long finCalculo = System.currentTimeMillis();
            //System.out.println("tarde "+ ((finCalculo - inicioCalculo)) + " ms en calcular la distancia entre " + timestampACalcular.getTime() + " y " + ahoraRightNow.getTime() + " = " + tiempoTranscurrido);
        }
        
        return tiempoTranscurrido;
    }
    public static String formatearHoraBonita(int hora , int minutos)
    {
        String strHora = "" + hora;
        String strMinutos = "" + minutos;
        
        if( hora < 10)
        {
            strHora = "0" + hora;
        }
        if( minutos < 10)
        {
            strMinutos = "0" + minutos;
        }
        
        return strHora + ":" + strMinutos;
    }
    
    public static double round(double value, int comas) 
    {
        if (comas < 0) 
        {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, comas);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
//    public static boolean esNumeric(String str) 
//    {
//        return StringUtils.isNumeric(str);
//    }
    public static boolean esNumeric(String str) 
    {
        try 
        {  
            Double.parseDouble(str);  
            return true;
        }
        catch(NumberFormatException e)
        {  
            return false;  
        }  
    }
    public static boolean esNumeric2(String str) 
    {
        boolean si = false;
        
        try 
        {  
            Double.parseDouble(str);  
            si = true;
        } 
        catch(NumberFormatException e)
        {  
            si = false;  
        }  
        
        return si;
    }
    
    
    
    
    
    
    //LETRAS:
    public static String primeraMayus(String entrada)
    {
        String salida = "";
        String strPrimerChar = "";
        String restoDeLaCadena = "";
        
        if(entrada.length() > 0)
        {
            char primerChar = entrada.charAt(0);
            strPrimerChar = "" + primerChar;
            strPrimerChar = strPrimerChar.toUpperCase();
            restoDeLaCadena = entrada.substring(1,entrada.length());
        }
        
        salida = strPrimerChar + restoDeLaCadena;
        
        return salida;
    }
    public static List<String> separarPalabrasPorEspacio(String palabras)
    {
        List<String> arrPalabras = new ArrayList<>();
        
        if(palabras != null)
        {
            String acumulador = "";
            for(int i = 0;  i < palabras.length() ; i++)
            {
                char c = palabras.charAt(i);
                if(Character.isWhitespace(c) || i == palabras.length() - 1)
                {
                    // SI ES EL ULTIMO CARACTER AGREGALO PAPA:
                    if(i == palabras.length() - 1)
                    {
                        acumulador += c;
                    }
                    
                    arrPalabras.add(acumulador);
                    acumulador = "";
                }
                else
                {
                    acumulador += c;
                }
            }
        }
        
        return arrPalabras;
    }
    public static boolean yaTengoStrEnArrStr(List<String> arrStr , String str)
    {
        boolean tengo = false;
        
        if(arrStr != null)
        {
            for(String strLoop : arrStr)
            {
                if(strLoop.equalsIgnoreCase(str))
                {
                    tengo = true;
                    break;
                }
            }
        }
        
        return tengo;
    }
    
    public static String remplazarTodo(String str , char caracterAReemplazar  ,char reemplazo)
    {
        String salida = "";
        if(str != null)
        {
            for(int i = 0 ; i < str.length() ; i ++)
            {
                char caracterActual = str.charAt(i);
                
                if(caracterActual == caracterAReemplazar)
                {
                    str += reemplazo;
                }
                else
                {
                    str += caracterActual;
                }
            }
        }
        
        return salida;
    }
    
    
    public static Date dameFechaTrucha()
    {
        int dia = 1;
        int mes = 1;
        int year = (1000 - 1900);
        Date fecha = new Date(year,mes , dia);
        
        
        return fecha;
    }
    /* */
    public static String agregarZerosSiTieneLargoMenorA(String str , int cantidadDigitos)
    {
        String zeros = "000000000000000000000000000000000000000000000000000000000000000";
        if(str != null)
        {
            int largo = str.length();
            
            if(largo < cantidadDigitos)
            {
                int cantidadZerosAAgregar = cantidadDigitos - largo ;
                
                str = zeros.substring(0,cantidadZerosAAgregar) + str;
            }
        }
        
        return str;
    }
    
    
    
    private static boolean soyWindows()
    {
        boolean soyWindows = false;
        String os = System.getProperty("os.name");
        System.out.println("os:" + System.getProperty("os.name"));
        
        // SISTEMA OPERATIVO (SO)
        if(os.startsWith("Windows"))
        {
            soyWindows = true;
        }
        return soyWindows;
    }
    
}
