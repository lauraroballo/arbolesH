package arbolesh;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

class NodoHuffman implements Comparable<NodoHuffman> {
    char dato;
    int frecuencia;
    NodoHuffman izquierda, derecha;

    public NodoHuffman(char dato, int frecuencia) {
        this.dato = dato;
        this.frecuencia = frecuencia;
    }

    @Override
    public int compareTo(NodoHuffman nodo) {
        return this.frecuencia - nodo.frecuencia;
    }
}

public class ArbolH {
    public static HashMap<Character, String> codificar(String datos) {
        HashMap<Character, Integer> mapaFrecuencia = new HashMap<>();
        for (char c : datos.toCharArray()) {
            mapaFrecuencia.put(c, mapaFrecuencia.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<NodoHuffman> colaPrioridad = new PriorityQueue<>();
        for (char c : mapaFrecuencia.keySet()) {
            colaPrioridad.offer(new NodoHuffman(c, mapaFrecuencia.get(c)));
        }

        while (colaPrioridad.size() > 1) {
            NodoHuffman izquierda = colaPrioridad.poll();
            NodoHuffman derecha = colaPrioridad.poll();
            NodoHuffman fusionado = new NodoHuffman('\0', izquierda.frecuencia + derecha.frecuencia);
            fusionado.izquierda = izquierda;
            fusionado.derecha = derecha;
            colaPrioridad.offer(fusionado);
        }

        HashMap<Character, String> codigos = new HashMap<>();
        if (!colaPrioridad.isEmpty()) {
            recorrer(colaPrioridad.peek(), "", codigos);
        }
        return codigos;
    }

    private static void recorrer(NodoHuffman nodo, String codigo, HashMap<Character, String> codigos) {
        if (nodo == null) return;
        if (nodo.izquierda == null && nodo.derecha == null) {
            codigos.put(nodo.dato, codigo);
        }
        recorrer(nodo.izquierda, codigo + "0", codigos);
        recorrer(nodo.derecha, codigo + "1", codigos);
    }

    public static String comprimir(String datos) {
        HashMap<Character, String> codigos = codificar(datos);
        StringBuilder datosComprimidos = new StringBuilder();
        for (char c : datos.toCharArray()) {
            datosComprimidos.append(codigos.get(c));
        }
        return datosComprimidos.toString();
    }

    public static String descomprimir(String datosComprimidos, HashMap<Character, String> codigos) {
        StringBuilder datosDescomprimidos = new StringBuilder();
        StringBuilder codigo = new StringBuilder();
        for (char c : datosComprimidos.toCharArray()) {
            codigo.append(c);
            if (codigos.containsValue(codigo.toString())) {
                for (char key : codigos.keySet()) {
                    if (codigos.get(key).equals(codigo.toString())) {
                        datosDescomprimidos.append(key);
                        codigo.setLength(0);
                        break;
                    }
                }
            }
        }
        return datosDescomprimidos.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese el mensaje a comprimir:");
        String mensaje = scanner.nextLine();

        String datosComprimidos = comprimir(mensaje);
        System.out.println("Datos comprimidos: " + datosComprimidos);

        HashMap<Character, String> codigos = codificar(mensaje);
        String datosDescomprimidos = descomprimir(datosComprimidos, codigos);
        System.out.println("Datos descomprimidos: " + datosDescomprimidos);
    }
}
