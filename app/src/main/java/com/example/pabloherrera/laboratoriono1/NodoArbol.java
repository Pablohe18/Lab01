package com.example.pabloherrera.laboratoriono1;

class NodoArbol {
    private Integer dato;
    private char letra;
    private NodoArbol izq;
    private NodoArbol der;

    public Integer getDato() {
        return dato;
    }

    public void setDato(Integer dato) {
        this.dato = dato;
    }

    public NodoArbol getIzq() {
        return izq;
    }

    public void setIzq(NodoArbol izq) {
        this.izq = izq;
    }

    public NodoArbol getDer() {
        return der;
    }

    public void setDer(NodoArbol der) {
        this.der = der;
    }

    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public NodoArbol(char l, Integer d) {
        letra = l;
        dato = d;
        izq = null;
        der = null;
    }

    @Override
    public String toString() {
        return letra + ":" + dato;
    }

}
