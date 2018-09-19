package com.example.pabloherrera.laboratoriono1;

class NodoListaArbol {
    private Arbol dato;
    private NodoListaArbol siguiente;

    public Arbol getDato() {
        return dato;
    }

    public void setDato(Arbol dato) {
        this.dato = dato;
    }

    public NodoListaArbol getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoListaArbol siguiente) {
        this.siguiente = siguiente;
    }

    public NodoListaArbol(Arbol d) {
        this.setDato(d);
        this.setSiguiente(null);
    }

    @Override
    public String toString() {
        return dato.toString();
    }
}
