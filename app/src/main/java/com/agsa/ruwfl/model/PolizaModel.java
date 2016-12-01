package com.agsa.ruwfl.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Evillatoro on 16/11/2016.
 */

public class PolizaModel implements Serializable {

    private int codAseg;
    private int codSuc;
    private int codRamo;
    private String txtRamo;
    private int codTipoAgente;
    private int codAgente;
    private int idCaso;
    private Date fecProceso;
    private long nroPol;
    private int idPv;
    private String txtEndoso;
    private String txtNomSuc;
    private String txtAsegurado;
    private boolean marked;

    public PolizaModel() {
    }

    public PolizaModel(int codAseg, int codSuc, int codRamo, String txtRamo, int codTipoAgente, int codAgente, int idCaso, Date fecProceso, long nroPol, int idPv, String txtEndoso, String txtNomSuc, String txtAsegurado) {
        this.codAseg = codAseg;
        this.codSuc = codSuc;
        this.codRamo = codRamo;
        this.txtRamo = txtRamo;
        this.codTipoAgente = codTipoAgente;
        this.codAgente = codAgente;
        this.idCaso = idCaso;
        this.fecProceso = fecProceso;
        this.nroPol = nroPol;
        this.idPv = idPv;
        this.txtEndoso = txtEndoso;
        this.txtNomSuc = txtNomSuc;
        this.txtAsegurado = txtAsegurado;
    }

    public int getCodAseg() {
        return codAseg;
    }

    public void setCodAseg(int codAseg) {
        this.codAseg = codAseg;
    }

    public int getCodSuc() {
        return codSuc;
    }

    public void setCodSuc(int codSuc) {
        this.codSuc = codSuc;
    }

    public int getCodRamo() {
        return codRamo;
    }

    public void setCodRamo(int codRamo) {
        this.codRamo = codRamo;
    }

    public String getTxtRamo() {
        return txtRamo;
    }

    public void setTxtRamo(String txtRamo) {
        this.txtRamo = txtRamo;
    }

    public int getCodTipoAgente() {
        return codTipoAgente;
    }

    public void setCodTipoAgente(int codTipoAgente) {
        this.codTipoAgente = codTipoAgente;
    }

    public int getCodAgente() {
        return codAgente;
    }

    public void setCodAgente(int codAgente) {
        this.codAgente = codAgente;
    }

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public long getNroPol() {
        return nroPol;
    }

    public void setNroPol(long nroPol) {
        this.nroPol = nroPol;
    }

    public int getIdPv() {
        return idPv;
    }

    public void setIdPv(int idPv) {
        this.idPv = idPv;
    }

    public String getTxtEndoso() {
        return txtEndoso;
    }

    public void setTxtEndoso(String txtEndoso) {
        this.txtEndoso = txtEndoso;
    }

    public String getTxtNomSuc() {
        return txtNomSuc;
    }

    public void setTxtNomSuc(String txtNomSuc) {
        this.txtNomSuc = txtNomSuc;
    }

    public String getTxtAsegurado() {
        return txtAsegurado;
    }

    public void setTxtAsegurado(String txtAsegurado) {
        this.txtAsegurado = txtAsegurado;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
