package com.agsa.ruwfl.model;

/**
 * Created by Evillatoro on 15/11/2016.
 */

public class AgenteModel {
    private int codTipoAgente;
    private int codAgente;
    private String txtChequeANom;
    private String mKey;

    public AgenteModel() {
    }

    public AgenteModel(String txtChequeANom, int codTipoAgente, int codAgente) {
        this.txtChequeANom = txtChequeANom;
        this.codTipoAgente = codTipoAgente;
        this.codAgente = codAgente;
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

    public String getTxtChequeANom() {
        return txtChequeANom;
    }

    public void setTxtChequeANom(String txtChequeANom) {
        this.txtChequeANom = txtChequeANom;
    }

    public String getmKey() {
        return String.valueOf(codTipoAgente) + "-" + String.valueOf(codAgente);
    }
}
