package br.com.henriquespassos.ed;

import java.util.LinkedList;

public class GrafoConexoMatriz {

    static int inf = Integer.MAX_VALUE / 4;
    static int[][] ma = {
            {0, 10, inf, inf, 2},
            {10, 0, inf, inf, inf},
            {inf, inf, 0, 2, inf},
            {inf, inf, 2, 0, inf},
            {2, inf, inf, inf, inf}};

    static boolean[] adjacente = new boolean[ma.length];

    public static void main(String[] args) {
        LinkedList<Integer> list = analisaCompMatrizAdjancia();
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d -> %d\n", i + 1, list.get(i));
        }
    }

    static LinkedList<Integer> analisaCompMatrizAdjancia() {
        // vou utilizar uma lista para adicionar
        // o tamanho de cada componente conexo (tamanho == quantidade de NOS)
        LinkedList<Integer> list = new LinkedList<Integer>();
        // vou visitar todos os NOS
        for (int i = 0; i < ma.length; i++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!adjacente[i]) {
                // marcar como VISITADO
                adjacente[i] = true;
                // adiciono a quantidade de componentes para esse componente conexo
                list.add(vizitaVizinhoMatrizAdjacente(i));
            }

        }
        return list;
    }

    static public int vizitaVizinhoMatrizAdjacente(int atual) {
        int cont = 1;
        // vou visitar todos os NOS
        for (int i = 0; i < ma.length; i++) {
            // todos nao so os NOS nao VISITADOS ainda
            // e que sejam adjacente
            if (!adjacente[i] && (ma[atual][i] < inf)) {
                // marcar como VISITADO
                adjacente[i] = true;
                // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                cont += vizitaVizinhoMatrizAdjacente(i);
            }
        }
        return cont;
    }
}