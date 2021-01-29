<%
    String url = request.getRequestURL().toString();
    out.print("URL:" + url);
    
    
    response.sendRedirect("vistas/home/home.jsp");
    
//    if(url.contains("turnosmelipal.com.ar"))
//    {
//        //out.print("VOY A MELIPAL");
//        response.sendRedirect("/ClinicApp/vistas/turnos-web/turnos-web.jsp");
//    }
//    else
//    {
//        //out.print("VOY A ADENTRO");
//        response.sendRedirect("/ClinicApp/vistas/agenda2/agenda2.jsp");
//    }
%>