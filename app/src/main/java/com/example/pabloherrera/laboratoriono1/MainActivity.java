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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnComprimirHuf;
    Button btnExaminar;
    TextView textoArchivo;
    String texto;
    static Node node;
    static Node newRoot;
    static String codedString = "";
    TextView txtComprimido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnComprimirHuf = (Button) findViewById(R.id.btnComprimirHuf);
        btnExaminar = (Button) findViewById(R.id.btnExaminar);
        textoArchivo = (TextView) findViewById(R.id.textoArchivo);
        txtComprimido = (TextView) findViewById(R.id.txtComprimido);
        btnComprimirHuf.setOnClickListener(this);
        btnExaminar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btnComprimirHuf):
                texto = textoArchivo.getText().toString();
                char[] msgChar = texto.toCharArray();
                ArrayList<Character> characters = new ArrayList<Character>();
                for (int i = 0; i < msgChar.length; i++) {
                    if (!(characters.contains(msgChar[i]))) {
                        characters.add(msgChar[i]);
                    }
                }
                int[] countOfChar = new int[characters.size()];
                for (int x = 0; x < countOfChar.length; x++) {
                    countOfChar[x] = 0;
                }
                for (int i = 0; i < characters.size(); i++) {
                    char checker = characters.get(i);
                    for (int x = 0; x < msgChar.length; x++) {
                        if (checker == msgChar[x]) {
                            countOfChar[i]++;
                        }
                    }
                }
                for (int i = 0; i < countOfChar.length - 1; i++) {
                    for (int j = 0; j < countOfChar.length - 1; j++) {
                        if (countOfChar[j] < countOfChar[j + 1]) {
                            int temp = countOfChar[j];
                            countOfChar[j] = countOfChar[j + 1];
                            countOfChar[j + 1] = temp;

                            char tempChar = characters.get(j);
                            characters.set(j, characters.get(j + 1));
                            characters.set(j + 1, tempChar);
                        }
                    }
                }
                Node root = null;
                Node current = null;
                Node end = null;

                for (int i = 0; i < countOfChar.length; i++) {
                    Node node = new Node(characters.get(i).toString(), countOfChar[i]);
                    if (root == null) {
                        root = node;
                        end = node;
                    } else {
                        current = root;
                        while (current.linker != null) {
                            current = current.linker;
                        }
                        current.linker = node;
                        current.linker.linkerBack = current;
                        end = node;
                    }
                }
                TreeMaker(root, end);

                char[] messageArray = texto.toCharArray();
                char checker;

                for (int i = 0; i < messageArray.length; i++) {
                    current = node;
                    checker = messageArray[i];
                    String code = "";
                    while (true) {
                        if (current.left.value.toCharArray()[0] == checker) {
                            code += "0";
                            break;
                        } else {
                            code += "1";
                            if (current.right != null) {
                                if (current.right.value.toCharArray()[0] == characters
                                        .get(countOfChar.length - 1)) {
                                    break;
                                }
                                current = current.right;
                            } else {
                                break;
                            }
                        }
                    }
                    codedString += code;
                }
                txtComprimido.setText(codedString);
                break;

            case (R.id.btnExaminar):
                Intent intent = new Intent().addCategory(Intent.CATEGORY_OPENABLE).setType("*/*").setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "select a file"), 123);

                break;
        }
    }

    private String readTextFromUri(Uri uri) throws IOException {
        String salida = "";
        InputStream inputStream = getContentResolver().openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));

        int cha;
        cha = reader.read();
        while (cha != -1) {
            salida = salida + ((char) cha);
            cha = reader.read();
        }
        inputStream.close();
        reader.close();
        return salida;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData();
            Toast.makeText(this, selectedfile.toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, selectedfile.getPath(), Toast.LENGTH_LONG).show();

            try {
                textoArchivo.setText(readTextFromUri(selectedfile));

            } catch (IOException e) {
                Toast.makeText(this, "Hubo un error al leer el archivo", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static void TreeMaker(Node root, Node end) {
        node = new Node(end.linkerBack.value + end.value, end.linkerBack.count
                + end.count);
        node.left = end.linkerBack;
        node.right = end;
        end.linkerBack.linkerBack.linker = node;
        node.linkerBack = end.linkerBack.linkerBack;
        end = node;
        end.linker = null;
        Node current = root;

        while (current.linker != null) {
            System.out.print(current.value + "->");
            current = current.linker;
        }

        System.out.println(current.value);

        if (root.linker == end) {
            node = new Node(root.value + end.value, root.count + end.count);
            node.left = root;
            node.right = end;
            node.linker = null;
            node.linkerBack = null;
            System.out.println(node.value);
            newRoot = node;
        } else {
            TreeMaker(root, end);
        }
    }

}
