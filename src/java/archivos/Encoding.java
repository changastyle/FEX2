/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package archivos;

import java.nio.charset.StandardCharsets;

public class Encoding
{
    public static String fix(String entrada)
    {
        String salida = "";
        
        byte[] bytes = entrada.getBytes(StandardCharsets.ISO_8859_1);
        salida = new String(bytes, StandardCharsets.UTF_8);
        
        return salida;
    }
}
