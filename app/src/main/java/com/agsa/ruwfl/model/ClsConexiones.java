package com.agsa.ruwfl.model;

/**
 * Created by Evillatoro on 05/12/2016.
 */

public class ClsConexiones {
    private int intCodAplicacion;
    private int intCodConexion;
    private String strDescConexion;
    private String strNombreConexion;

    public ClsConexiones() {
    }

    public ClsConexiones(int intCodAplicacion, int intCodConexion, String strDescConexion, String strNombreConexion) {
        this.intCodAplicacion = intCodAplicacion;
        this.intCodConexion = intCodConexion;
        this.strDescConexion = strDescConexion;
        this.strNombreConexion = strNombreConexion;
    }

    public int getIntCodAplicacion() {
        return intCodAplicacion;
    }

    public void setIntCodAplicacion(int intCodAplicacion) {
        this.intCodAplicacion = intCodAplicacion;
    }

    public int getIntCodConexion() {
        return intCodConexion;
    }

    public void setIntCodConexion(int intCodConexion) {
        this.intCodConexion = intCodConexion;
    }

    public String getStrDescConexion() {
        return strDescConexion;
    }

    public void setStrDescConexion(String strDescConexion) {
        this.strDescConexion = strDescConexion;
    }

    public String getStrNombreConexion() {
        return strNombreConexion;
    }

    public void setStrNombreConexion(String strNombreConexion) {
        this.strNombreConexion = strNombreConexion;
    }
}
