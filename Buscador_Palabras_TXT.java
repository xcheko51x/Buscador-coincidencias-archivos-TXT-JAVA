package buscador_palabras_txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Buscador_Palabras_TXT {

    static File archivo_palabras = new File("palabras.txt"); // Archivo con palabras a buscar
    static File archivo_documento = new File("documento.txt"); // Documento donde se buscaran las palabras

    static String ruta = "respuesta.txt"; // Archivo donde guardara los resultados
    static File file = new File(ruta); // Se abre el archivo

    static int coincidencia = 0; // Bandera para saber si hay coincidencia o no

    public static void main(String[] args) {

        borrarArchivo(); // Metodo que borra el archivo si ya existe para generar uno nuevo
        leerArchivoPalabras(archivo_palabras); // Metodo para leer las palabras a buscar

    }

    public static void borrarArchivo() {
        file.delete();
    }

    public static void leerArchivoPalabras(File palabras) {
        try {
            
            if (palabras.exists()) {

                BufferedReader archivoLeer = new BufferedReader(new FileReader(palabras));
                String lineaLeida;

                while ((lineaLeida = archivoLeer.readLine()) != null) {
                    String[] partes = lineaLeida.split(",");

                    for (int i = 0; i < partes.length; i++) {
                        //System.out.println("PALABRA: " + partes[i]);
                        leerArchivoDocumentos(archivo_documento, partes[i]);
                    }
                    //System.out.println("");
                }

                archivoLeer.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void leerArchivoDocumentos(File documento, String palabra) {

        try {
            file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            if (documento.exists()) {
                BufferedReader archivoDocumento = new BufferedReader(new FileReader(documento));
                String lineaLeidaDoc;

                while ((lineaLeidaDoc = archivoDocumento.readLine()) != null) {

                    String[] partes = lineaLeidaDoc.split("\\,|\\.|\\ ");
                    for (int i = 0; i < partes.length; i++) {
                        //System.out.println(partes[i]);
                        if (partes[i].trim().equals(palabra.trim())) {
                            coincidencia = 1;
                        }
                    }
                }
            }

            if (coincidencia == 1) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(palabra + " - (SI)\n");
                bw.close();
                coincidencia = 0;
            } else {
                FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(palabra + " - (NO)\n");
                bw.close();
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
