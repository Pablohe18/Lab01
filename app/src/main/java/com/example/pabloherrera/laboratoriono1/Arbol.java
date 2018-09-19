package com.example.pabloherrera.laboratoriono1;

class Arbol {
    private NodoArbol raiz;

    public NodoArbol getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoArbol raiz) {
        this.raiz = raiz;
    }

    public Arbol() {
        raiz = null;
    }

    public Arbol(NodoArbol raizArbol) {
        raiz = raizArbol;
    }

    public void insertar(char letra, Integer d) {
        NodoArbol nuevo = new NodoArbol(letra, d);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            NodoArbol aux = raiz;
            NodoArbol ant = null;
            while (aux != null) {
                ant = aux;
                if (d <= aux.getDato()) {
                    aux = aux.getIzq();
                } else {
                    aux.getDer();
                }
            }
            if (d <= ant.getDato()) {
                ant.setIzq(nuevo);
            } else {
                ant.setDer(nuevo);
            }
        }
    }

    public String toString() {
        return raiz.toString();
    }

    public void enOrder(NodoArbol r) {
        if (r != null) {
            enOrder(r.getIzq());
            System.out.println(r.toString());
            enOrder(r.getDer());
        }
    }
}
