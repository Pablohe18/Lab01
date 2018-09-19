package com.example.pabloherrera.laboratoriono1;

class ListaArboles {

    private NodoListaArbol inicio;

    public NodoListaArbol getInicio() {
        return inicio;
    }

    public void setInicio(NodoListaArbol inicio) {
        this.inicio = inicio;
    }

    public void insertarInicio(Arbol d) {
        NodoListaArbol nuevo = new NodoListaArbol(d);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        }
    }

    public void recorrer() {
        NodoListaArbol aux = inicio;
        while (aux != null) {
            System.out.print(aux.getDato() + " -> ");
            aux = aux.getSiguiente();
        }
    }

    public void insertarFinal(Arbol d) {
        NodoListaArbol nuevo = new NodoListaArbol(d);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoListaArbol aux = inicio;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(nuevo);
        }
    }

    public void insertarOrdenado(Arbol d) {
        NodoListaArbol nuevo = new NodoListaArbol(d);
        if (inicio == null) {
            inicio = nuevo;
        } else {
            NodoListaArbol aux = inicio;
            NodoListaArbol ant = null;
            while (aux != (null) && (aux.getDato().getRaiz().getDato() <= nuevo.getDato().getRaiz().getDato())) {
                ant = aux;
                aux = aux.getSiguiente();
            }
            if (ant == null) {
                nuevo.setSiguiente(inicio);
                inicio = nuevo;
            } else if (aux == null) {
                ant.setSiguiente(nuevo);
            } else {
                ant.setSiguiente(nuevo);
                nuevo.setSiguiente(aux);
            }
        }
    }
}
