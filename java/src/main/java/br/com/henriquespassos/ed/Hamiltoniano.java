package br.com.henriquespassos.ed;

public class Hamiltoniano {

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

        //No inicio = g.no[0];
        //No fim = g.no[2];

        System.out.println(Hamiltoniano.class.getName());
        System.out.println(cicloHamiltoniano(g));
    }

    private static boolean cicloHamiltoniano(Grafo g) {
        No[] solucao = new No[g.adjacente.length];
        g.no[0].visitado = true;
        solucao[0] = g.no[0];
        boolean result = cicloHamiltonianoAux(g, solucao, 1);
        return result;
    }

    private static boolean cicloHamiltonianoAux(Grafo g, No[] solucao, int i) {
        if (i == g.adjacente.length) {
            for (No t : solucao[i - 1].vizinho) {
                if (t == solucao[0]) {
                    return true;
                }
            }
            return false;
        }
        for (No t : solucao[i - 1].vizinho) {
            if (!t.visitado) {
                t.visitado = true;
                solucao[i] = t;
                if (cicloHamiltonianoAux(g, solucao, i + 1)) return true;
                t.visitado = false;
            }
        }
        return false;
    }
}