package com.example.pabloherrera.laboratoriono1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button btnComprimirHuf;
    Button btnExaminar;
    TextView textoArchivo;
    String texto;
    private String resultado;
    private Lista camino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            btnComprimirHuf = (Button) findViewById(R.id.btnComprimirHuf);
            btnExaminar = (Button) findViewById(R.id.btnExaminar);
            textoArchivo = (TextView) findViewById(R.id.textoArchivo);

            btnComprimirHuf.setOnClickListener(this);
            btnExaminar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case(R.id.btnComprimirHuf):
                texto = textoArchivo.getText().toString();
                Lista l = ToNodos();
                ListaArboles la = cuentaLetras(l);
                ListaArboles l2 = juntaNodo(la);
                encuentraCamino(l2.getInicio().getDato().getRaiz(), "");
                Lista listaCaminos = regresaCaminos();
                String x = convierte(listaCaminos);
                System.out.println(x);
                encripta(x);

                break;

            case(R.id.btnExaminar):
                Intent intent = new Intent().addCategory(Intent.CATEGORY_OPENABLE).setType("*/*").setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "select a file"), 123);

                break;
        }
    }

    private String readTextFromUri(Uri uri) throws IOException{
        String salida ="";
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));

        int cha;
                cha = reader.read();
        while(cha != -1){
            salida = salida + ((char)cha);
            cha = reader.read();
        }
        inputStream.close();
        reader.close();
        return salida;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);
        if(requestCode == 123 && resultCode == RESULT_OK){
            Uri selectedfile = data.getData();
            Toast.makeText(this, selectedfile.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, selectedfile.getPath(), Toast.LENGTH_LONG).show();

            try{
                textoArchivo.setText(readTextFromUri(selectedfile));

            } catch(IOException e){
                Toast.makeText(this, "Hubo un error al leer el archivo", Toast.LENGTH_LONG).show();
            }
        }
    }
    public Lista ToNodos() {
        Lista l = new Lista();
        for (int i = 0; i < texto.length(); i++) {
            l.insertarFinal((texto.charAt(i)));
        }
        return l;

    }

    public ListaArboles cuentaLetras(Lista l) {
        ListaArboles la = new ListaArboles();
        Nodo aux = l.getInicio();
        while (aux != null) {
            Integer x = l.eliminarValor(aux.getDato());
            NodoArbol nuevo = new NodoArbol(aux.getDato(), x);
            Arbol a = new Arbol(nuevo);
            la.insertarOrdenado(a);
            aux = l.getInicio();
        }
        return la;
    }

    public NodoArbol uneNodos(NodoListaArbol a, NodoListaArbol b) {
        NodoArbol c = new NodoArbol('\u0000',
                ((a.getDato().getRaiz().getDato()) + (b.getDato().getRaiz()
                        .getDato())));
        c.setIzq(a.getDato().getRaiz());
        c.setDer(b.getDato().getRaiz());
        return c;
    }

    public ListaArboles juntaNodo(ListaArboles l) {
        NodoListaArbol aux = l.getInicio();
        NodoListaArbol aux2 = null;
        while ((aux != null) && (aux.getSiguiente() != null)) {
            l.setInicio(aux.getSiguiente());
            aux.setSiguiente(null);
            aux2 = l.getInicio();
            l.setInicio(aux2.getSiguiente());
            aux2.setSiguiente(null);
            NodoArbol nuevo = uneNodos(aux, aux2);
            Arbol a = new Arbol(nuevo);
            l.insertarOrdenado(a);
            aux = l.getInicio();
        }
        // Arbol a=l.getInicio().getDato();
        // a.enOrder(a.getRaiz());
        // a.encuentraCamino(a.getRaiz(), "");
        // System.out.println("dato: "+l.getInicio().getDato().getRaiz().getDato());
        // System.out.println("letra: "+l.getInicio().getDato().getRaiz().getLetra());
        return l;
    }

    public void recorrer2(Lista l) {
        Nodo n = l.getInicio();
        while (n != null) {
            System.out.print(n.getDato() + n.getCamino() + "->");
            n = n.getSiguiente();
        }
    }

    public String eliminarUltimo(String re) {
        String cadena = "";
        for (int i = 0; i < re.length() - 1; i++) {
            cadena += re.charAt(i);
        }
        return cadena;
    }

    public void encuentraCamino(NodoArbol r, String c) {
        // Lista camino = new Lista();
        resultado += c;
        if (r != null) {
            if (r.getLetra() != '\u0000') { // si es una letra
                camino.insertarFinal(r.getLetra(), resultado);
            }
            encuentraCamino(r.getIzq(), "0");
            if (r.getIzq() != null) {
                resultado = eliminarUltimo(resultado);
            }
            encuentraCamino(r.getDer(), "1");
            if (r.getDer() != null) { // si no es un nodo hoja
                resultado = eliminarUltimo(resultado);
            }
        } else {
            resultado = eliminarUltimo(resultado);
        }
    }

    public Lista regresaCaminos() { // retornamos la lista que contiene los
        // caminos de cada caracter
        return camino;
    }

    public String busca(char s, Lista l) {
        String camino = "";
        Nodo aux = l.getInicio();
        while (aux != null) {
            if (aux.getDato() == s) {
                camino = aux.getCamino();
            }
            aux = aux.getSiguiente();
        }
        return camino;
    }

    public String convierte(Lista camino) {
        String c = "";
        for (int i = 0; i < texto.length(); i++) {
            c = c + busca(texto.charAt(i), camino);
        }
        return c;
    }

    public char toAscii(String s) {// obtiene un codigo de 8 o menor
        String ascii = "";
        int numero = 0;
        int contador = 0;
        for (int i = 0; i < s.length(); i++) {// binario es string
            if (contador <= 8) {
                ascii = ascii + s.charAt(i);
                numero = Integer.parseInt(ascii, 2);
                if (contador == 8) {
                    ascii = "";
                    contador = 0;
                }
            }
        }
        return (char) numero;
    }

    public void encripta(String codigoHuffman) {
        String bites = "";
        String codigo = "";
        char c;
        int contador = 0;
        if (codigoHuffman.length() >= 8) {
            for (int i = 0; i < codigoHuffman.length(); i++) {
                if (contador <= 8) {
                    bites = bites + codigoHuffman.charAt(i);
                    contador++;
                    if (contador == 8) {
                        c = toAscii(bites);
                        codigo = codigo + c;
                        contador = 0;
                        bites = "";
                    }
                }
            }
        } else {
            c = toAscii(codigoHuffman);
            codigo = codigo + c;
        }
        System.out.println("Codigo Encriptado " + codigo);
    }

    public void encripta2(String codigoHuffman) {
        String bites = "";
        String codigo = "";
        char c;
        int contador = 0;
        for (int i = 0; i < codigoHuffman.length(); i++) {
            bites = bites + codigoHuffman.charAt(i);
            contador++;
            if (contador == 8) {
                c = toAscii(bites);
                codigo = codigo + c;
                contador = 0;
                bites = "";
            }
        }
        System.out.println("Codigo Encriptado " + codigo);
    }

    public void desencripta() {

    }
}
