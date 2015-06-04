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
        List<Integer> list = new LinkedList<Integer>();
        for (int i = 0; i < g.no.length; i++) {
            g.visitado[i] = false;
        }
        for (int i = 0; i < g.adjacente.length; i++) {
            if (!g.no[i].visitado) {
                int c = contarGrupoConexoAux(g, g.no[i]);
                list.add(c);
            }
        }
        return list;
    }

    public static int contarGrupoConexoAux(Grafo g, No no) {
        int cont = 1;
        no.visitado = true;
        for (int i = 0; i < no.vizinho.length; i++) {
            if (!no.vizinho[i].visitado) {
                cont += contarGrupoConexoAux(g, no.vizinho[i]);
            }
        }
        return cont;
    }

    // calcular componentes conexos recursiva com indice
    public static int compConexoRecInd(Grafo g) {
        int comp = 0;
        for (int i = 0; i < g.no.length; i++) {
            g.visitado[i] = false;
        }
        for (int atual = 0; atual < g.no.length; atual++) {
            if (!g.visitado[atual]) {
                g.visitado[atual] = true;
                comp++;
                visitaVizinhoRecInd(g, atual);
            }
        }
        return comp;
    }

    public static void visitaVizinhoRecInd(Grafo g, int atual) {
        for (int viz = 0; viz < g.no.length; viz++) {
            if (!g.visitado[viz] && g.adjacente[atual][viz]) {
                g.visitado[viz] = true;
                visitaVizinhoRecInd(g, viz);
            }
        }
    }

    // calcular componentes conexos iterativo com indice
    public static int compConexoItInd(Grafo g) {
        int comp = 0;
        Deque<Integer> lista = new LinkedList<Integer>();
        for (int i = 0; i < g.no.length; i++) {
            g.visitado[i] = false;
        }
        for (int atual = 0; atual < g.no.length; atual++) {
            if (!g.visitado[atual]) {
                g.visitado[atual] = true;
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
        for (int i = 0; i < g.no.length; i++) {
            g.no[i].visitado = false;
        }
        for (int atual = 0; atual < g.no.length; atual++) {
            if (!g.no[atual].visitado) {
                g.no[atual].visitado = true;
                comp++;
                visitaVizinhoRecArr(g.no[atual]);
            }
        }
        return comp;
    }

    public static void visitaVizinhoRecArr(No no) {
        for (int viz = 0; viz < no.vizinho.length; viz++) {
            if (!no.vizinho[viz].visitado) {
                no.vizinho[viz].visitado = true;
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