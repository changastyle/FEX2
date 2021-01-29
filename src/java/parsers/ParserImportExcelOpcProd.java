package parsers;

public class ParserImportExcelOpcProd
{
    public String CODPROD;
    public String OPCPROD;
    public String DESCRIPCION;
    public String ENVASE;
    public String UNIDAD;
    public String CANT_X_PACK;
    public String PRECIO_COSTO;
    public String PORCENTAJE;
    public String PRECIO_AUTO;
    public String PRECIO_FIJO;
    public String CATEGORIA;
    public String PROVEEDOR;
    public String STOCK;

    public ParserImportExcelOpcProd() 
    {
    }

    public ParserImportExcelOpcProd(String CODPROD, String OPCPROD, String DESCRIPCION, String ENVASE, String UNIDAD, String CANT_X_PACK, String PRECIO_COSTO, String PORCENTAJE, String PRECIO_AUTO, String PRECIO_FIJO, String CATEGORIA, String PROVEEDOR, String STOCK)
    {
        this.CODPROD = CODPROD;
        this.OPCPROD = OPCPROD;
        this.DESCRIPCION = DESCRIPCION;
        this.ENVASE = ENVASE;
        this.UNIDAD = UNIDAD;
        this.CANT_X_PACK = CANT_X_PACK;
        this.PRECIO_COSTO = PRECIO_COSTO;
        this.PORCENTAJE = PORCENTAJE;
        this.PRECIO_AUTO = PRECIO_AUTO;
        this.PRECIO_FIJO = PRECIO_FIJO;
        this.CATEGORIA = CATEGORIA;
        this.PROVEEDOR = PROVEEDOR;
        this.STOCK = STOCK;
    }

    public String getCODPROD()
    {
        return CODPROD;
    }

    public void setCODPROD(String CODPROD)
    {
        this.CODPROD = CODPROD;
    }

    public String getOPCPROD()
    {
        return OPCPROD;
    }

    public void setOPCPROD(String OPCPROD)
    {
        this.OPCPROD = OPCPROD;
    }

    public String getDESCRIPCION()
    {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION)
    {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getENVASE()
    {
        return ENVASE;
    }

    public void setENVASE(String ENVASE)
    {
        this.ENVASE = ENVASE;
    }

    public String getUNIDAD()
    {
        return UNIDAD;
    }

    public void setUNIDAD(String UNIDAD)
    {
        this.UNIDAD = UNIDAD;
    }

    public String getCANT_X_PACK()
    {
        return CANT_X_PACK;
    }

    public void setCANT_X_PACK(String CANT_X_PACK)
    {
        this.CANT_X_PACK = CANT_X_PACK;
    }

    public String getPRECIO_COSTO()
    {
        return PRECIO_COSTO;
    }

    public void setPRECIO_COSTO(String PRECIO_COSTO)
    {
        this.PRECIO_COSTO = PRECIO_COSTO;
    }

    public String getPORCENTAJE()
    {
        return PORCENTAJE;
    }

    public void setPORCENTAJE(String PORCENTAJE)
    {
        this.PORCENTAJE = PORCENTAJE;
    }

    public String getPRECIO_AUTO()
    {
        return PRECIO_AUTO;
    }

    public void setPRECIO_AUTO(String PRECIO_AUTO)
    {
        this.PRECIO_AUTO = PRECIO_AUTO;
    }

    public String getPRECIO_FIJO()
    {
        return PRECIO_FIJO;
    }

    public void setPRECIO_FIJO(String PRECIO_FIJO)
    {
        this.PRECIO_FIJO = PRECIO_FIJO;
    }

    public String getCATEGORIA()
    {
        return CATEGORIA;
    }

    public void setCATEGORIA(String CATEGORIA)
    {
        this.CATEGORIA = CATEGORIA;
    }

    public String getPROVEEDOR()
    {
        return PROVEEDOR;
    }

    public void setPROVEEDOR(String PROVEEDOR)
    {
        this.PROVEEDOR = PROVEEDOR;
    }

    public String getSTOCK()
    {
        return STOCK;
    }

    public void setSTOCK(String STOCK)
    {
        this.STOCK = STOCK;
    }

    @Override
    public String toString()
    {
        return "{" + "CODPROD=" + CODPROD + ", OPCPROD=" + OPCPROD + ", DESCRIPCION=" + DESCRIPCION + ", ENVASE=" + ENVASE + ", UNIDAD=" + UNIDAD + ", CANT_X_PACK=" + CANT_X_PACK + ", PRECIO_COSTO=" + PRECIO_COSTO + ", PORCENTAJE=" + PORCENTAJE + ", PRECIO_AUTO=" + PRECIO_AUTO + ", PRECIO_FIJO=" + PRECIO_FIJO + ", CATEGORIA=" + CATEGORIA + ", PROVEEDOR=" + PROVEEDOR + ", STOCK=" + STOCK + '}';
    }

    
}
