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
        // INICIO e igual a FIM
        // essa foi facil de mais
        // manda uma problema mais dificil
        if (inicio == fim) {
            return 0;
        }
        // vou utilizar uma lista para adicionar
        // os VIZINHOS dos VIZINHOS
        // para fazer uma busca em largura
        Deque<No> lista = new LinkedList<No>();
        // marcar NO inicial como VISITADO
        inicio.visitado = true;
        // a DISTANCIA atual e 0
        inicio.distancia = 0;
        // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
        lista.add(inicio);
        // enquanto ouver VIZINHOS dos VIZINHOS
        while (!lista.isEmpty()) {
            No aux = lista.remove();
            // vou visitar todos os NOS
            for (int i = 0; i < aux.vizinho.length; i++) {
                // todos nao so os NOS nao VISITADOS ainda
                if (!aux.vizinho[i].visitado) {
                    // achei nos VIZINHOS dos VIZINHOS o FIM
                    if (aux.vizinho[i] == fim) {
                        // distancia do AUX + 1
                        // (lembrando que AUX.VIZINHO[i].DISTANCIA nao foi setado ainda)
                        return aux.distancia + 1;
                    }
                    // marcar como VISITADO
                    aux.vizinho[i].visitado = true;
                    // atribuir a DISTANCIA percorrida ao NO VIZINHO
                    aux.vizinho[i].distancia = aux.distancia + 1;
                    // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                    lista.add(aux.vizinho[i]);
                }
            }
        }
        return -1;
    }

    public static int buscaProfundidade(No atual, No fim, int visitado) {
        // marcar como visitado
        atual.visitado = true;
        // achei o NO que eu estava procurando
        if (atual == fim) {
            return visitado;
        }
        // vou visitar todos os NOS
        for (int i = 0; i < atual.vizinho.length; i++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!atual.vizinho[i].visitado) {
                // vou visitar todos os NOS VIZINHOS do NO atual
                int res = buscaProfundidade(atual.vizinho[i], fim, visitado + 1);
                // se achei retorno o tempo gasto percorrido
                if (res != -1) {
                    return res;
                }
            }
        }
        return -1;
    }

    public static int buscaProfundidade(No atual, No fim) {
        // marcar como visitado
        atual.visitado = true;
        // achei o NO que eu estava procurando
        if (atual == fim) {
            return 0;
        }
        // vou visitar todos os NOS
        for (int i = 0; i < atual.vizinho.length; i++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!atual.vizinho[i].visitado) {
                // vou visitar todos os NOS VIZINHOS do NO atual
                int res = buscaProfundidade(atual.vizinho[i], fim) + 1;
                // se achei retorno o tempo gasto percorrido
                if (res != 0) {
                    return res;
                }
            }
        }
        return -1;
    }
}