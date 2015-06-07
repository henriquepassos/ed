package br.com.henriquespassos.ed;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GrafoConexo {

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

        System.out.println(GrafoConexo.class.getName());
        System.out.println(contarGrupoConexo(g));
        System.out.println(compConexoRecInd(g));
        System.out.println(compConexoItInd(g));
        System.out.println(compConexoRecArr(g));
        System.out.println(compConexoItArr(g));
    }

    // calcular quantidade de componentes conexos recursiva com arranjo
    public static List<Integer> contarGrupoConexo(Grafo g) {
        // vou utilizar uma lista para adicionar
        // o tamanho de cada componente conexo (tamanho == quantidade de NOS)
        List<Integer> list = new LinkedList<Integer>();
        // vou marcar todos os NOS como nao VISITADO
        for (int i = 0; i < g.no.length; i++) {
            g.visitado[i] = false;
        }
        // vou visitar todos os NOS
        for (int i = 0; i < g.adjacente.length; i++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!g.no[i].visitado) {
                // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                int c = contarGrupoConexoAux(g, g.no[i]);
                // adiciono a quantidade de componentes para esse componente conexo
                list.add(c);
            }
        }
        return list;
    }

    public static int contarGrupoConexoAux(Grafo g, No no) {
        int cont = 1;
        no.visitado = true;
        // vou visitar todos os NOS
        for (int i = 0; i < no.vizinho.length; i++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!no.vizinho[i].visitado) {
                // marcar como VISITADO
                no.vizinho[i].visitado = true;
                // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                cont += contarGrupoConexoAux(g, no.vizinho[i]);
            }
        }
        return cont;
    }

    // calcular componentes conexos recursiva com indice
    public static int compConexoRecInd(Grafo g) {
        int comp = 0;
        // vou marcar todos os NOS como nao VISITADO
        for (int i = 0; i < g.no.length; i++) {
            g.visitado[i] = false;
        }
        // vou visitar todos os NOS
        for (int atual = 0; atual < g.no.length; atual++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!g.visitado[atual]) {
                // marcar como VISITADO
                g.visitado[atual] = true;
                // contar a quantidade de componentes conexos
                comp++;
                // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                visitaVizinhoRecInd(g, atual);
            }
        }
        return comp;
    }

    public static void visitaVizinhoRecInd(Grafo g, int atual) {
        // vou visitar todos os NOS
        for (int viz = 0; viz < g.no.length; viz++) {
            // todos nao so os NOS nao VISITADOS ainda
            // e que sejam adjacente
            if (!g.visitado[viz] && g.adjacente[atual][viz]) {
                // marcar como VISITADO
                g.visitado[viz] = true;
                // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                visitaVizinhoRecInd(g, viz);
            }
        }
    }

    // calcular componentes conexos iterativo com indice
    public static int compConexoItInd(Grafo g) {
        int comp = 0;
        // vou utilizar uma lista para adicionar
        // os VIZINHOS dos VIZINHOS
        // para fazer uma busca em largura
        Deque<Integer> lista = new LinkedList<Integer>();
        // vou marcar todos os NOS como nao VISITADO
        for (int i = 0; i < g.no.length; i++) {
            g.visitado[i] = false;
        }
        // vou visitar todos os NOS
        for (int atual = 0; atual < g.no.length; atual++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!g.visitado[atual]) {
                // marcar como VISITADO
                g.visitado[atual] = true;
                // contar a quantidade de componentes conexos
                comp++;
                lista.add(atual);
                while (!lista.isEmpty()) {
                    Integer aux = lista.remove();
                    for (int viz = 0; viz < g.no.length; viz++) {
                        if (!g.visitado[viz] && g.adjacente[aux][viz]) {
                            g.visitado[viz] = true;
                            lista.add(viz);
                        }
                    }
                }
            }
        }
        return comp;
    }

    // calcular componentes conexos recursiva com arranjo
    public static int compConexoRecArr(Grafo g) {
        int comp = 0;
        // vou marcar todos os NOS como nao VISITADO
        for (int i = 0; i < g.no.length; i++) {
            g.no[i].visitado = false;
        }
        // vou visitar todos os NOS
        for (int atual = 0; atual < g.no.length; atual++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!g.no[atual].visitado) {
                // marcar como VISITADO
                g.no[atual].visitado = true;
                // contar a quantidade de componentes conexos
                comp++;
                // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                visitaVizinhoRecArr(g.no[atual]);
            }
        }
        return comp;
    }

    public static void visitaVizinhoRecArr(No no) {
        // vou visitar todos os NOS
        for (int viz = 0; viz < no.vizinho.length; viz++) {
            // todos nao so os NOS nao VISITADOS ainda
            if (!no.vizinho[viz].visitado) {
                // marcar como VISITADO
                no.vizinho[viz].visitado = true;
                // vou visitar recursivamente todos os possiveis VIZINHOS dos VIZINHOS
                visitaVizinhoRecArr(no.vizinho[viz]);
            }
        }
    }

    // calcular componentes conexos iterativo com arranjo
    public static int compConexoItArr(Grafo g) {
        int comp = 0;
        Deque<No> lista = new LinkedList<No>();
        for (int i = 0; i < g.no.length; i++) {
            g.no[i].visitado = false;
        }
        for (int atual = 0; atual < g.no.length; atual++) {
            if (!g.no[atual].visitado) {
                g.no[atual].visitado = true;
                comp++;
                lista.add(g.no[atual]);
                while (!lista.isEmpty()) {
                    No aux = lista.remove();
                    for (int viz = 0; viz < aux.vizinho.length; viz++) {
                        if (!aux.vizinho[viz].visitado) {
                            aux.vizinho[viz].visitado = true;
                            lista.add(aux.vizinho[viz]);
                        }
                    }
                }
            }
        }
        return comp;
    }
}