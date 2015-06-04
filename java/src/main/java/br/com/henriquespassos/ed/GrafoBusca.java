package br.com.henriquespassos.ed;

import java.util.Deque;
import java.util.LinkedList;

public class GrafoBusca {

    public static void main(String[] args) {
        Grafo g = new Grafo();
        g.adjacente = new boolean[][]{
                {false, true, false, true, false, false},
                {false, false, true, true, false, false},
                {true, false, true, false, false, false},
                {true, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, true, false}
        };
        g.visitado = new boolean[g.adjacente.length];
        g.no = new No[g.adjacente.length];

        int[] total = new int[g.adjacente.length];

        for (int i = 0; i < g.adjacente.length; i++) {
            g.no[i] = new No();
            for (int j = 0; j < g.adjacente.length; j++) {
                if (g.adjacente[i][j]) {
                    total[i]++;
                }
            }
        }

        for (int i = 0; i < g.adjacente.length; i++) {
            g.no[i].vizinho = new No[total[i]];
            total[i] = 0;
            for (int j = 0; j < g.adjacente.length; j++) {
                if (g.adjacente[i][j]) {
                    g.no[i].vizinho[total[i]++] = g.no[j];
                }
            }
        }

        No inicio = g.no[0];
        No fim = g.no[2];

        System.out.println(GrafoBusca.class.getName());
        zera(g);
        System.out.println(buscaLargura(inicio, fim));
        zera(g);
        System.out.println(buscaProfundidade(inicio, fim, 0));
        zera(g);
        System.out.println(buscaProfundidade(inicio, fim));
    }

    public static void zera(Grafo g) {
        for (int i = 0; i < g.no.length; i++) {
            g.no[i].visitado = false;
            g.no[i].distancia = 0;
        }
    }

    public static int buscaLargura(No inicio, No fim) {
        if (inicio == fim) {
            return 0;
        }
        Deque<No> lista = new LinkedList<No>();
        inicio.visitado = true;
        inicio.distancia = 0;
        lista.add(inicio);
        while (!lista.isEmpty()) {
            No aux = lista.remove();
            for (int i = 0; i < aux.vizinho.length; i++) {
                if (!aux.vizinho[i].visitado) {
                    if (aux.vizinho[i] == fim) {
                        return aux.distancia + 1;
                    }
                    aux.vizinho[i].visitado = true;
                    aux.vizinho[i].distancia = aux.distancia + 1;
                    lista.add(aux.vizinho[i]);
                }
            }
        }
        return -1;
    }

    public static int buscaProfundidade(No atual, No fim, int visitado) {
        atual.visitado = true;
        if (atual == fim) {
            return visitado;
        }
        for (int i = 0; i < atual.vizinho.length; i++) {
            if (!atual.vizinho[i].visitado) {
                int res = buscaProfundidade(atual.vizinho[i], fim, visitado + 1);
                if (res != -1) {
                    return res;
                }
            }
        }
        return -1;
    }

    public static int buscaProfundidade(No atual, No fim) {
        atual.visitado = true;
        if (atual == fim) {
            return 0;
        }
        for (int i = 0; i < atual.vizinho.length; i++) {
            if (!atual.vizinho[i].visitado) {
                int res = buscaProfundidade(atual.vizinho[i], fim) + 1;
                if (res != 0) {
                    return res;
                }
            }
        }
        return -1;
    }
}