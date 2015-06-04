package br.com.henriquespassos.ed;

public class CaixeiroViajante {

    private final static int INF = Integer.MAX_VALUE / 4;

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

        CaixeiroViajanteBean c = new CaixeiroViajanteBean(g.adjacente.length);

        System.out.println(CaixeiroViajante.class.getName());
        System.out.println(caixeiroViajante(g, c, 0));
    }

    private static int caixeiroViajante(Grafo g, CaixeiroViajanteBean c, int i) {
        g.visitado[i] = true;
        c.tempSolucao[0] = i;
        caixeiroViajanteAux(g, c, 1);
        if (c.valorMelhorSolucao < INF) {
            return c.valorMelhorSolucao;
        }
        return -1;
    }

    private static void caixeiroViajanteAux(Grafo g, CaixeiroViajanteBean c, int i) {
        if (c.valorSolucaoAtual > c.valorMelhorSolucao) {
            return;
        }
        if (i == g.adjacente.length) {
            if (c.matrizDistancia[c.tempSolucao[i - 1]][c.tempSolucao[0]] < INF) {
                System.out.println("\t" + c.valorMelhorSolucao + " -> " + c.valorSolucaoAtual);
                c.valorMelhorSolucao = c.valorSolucaoAtual;
                c.melhorSolucao = c.tempSolucao.clone();
            }
            return;
        }
        int ultimo = c.tempSolucao[i - 1];
        for (int t = 0; t < c.matrizDistancia.length; t++) {
            if (!g.visitado[t] && c.matrizDistancia[ultimo][t] < INF) {
                g.visitado[t] = true;
                c.tempSolucao[i] = t;
                c.valorSolucaoAtual += c.matrizDistancia[ultimo][t];
                caixeiroViajanteAux(g, c, i + 1);
                c.valorSolucaoAtual -= c.matrizDistancia[ultimo][t];
                g.visitado[t] = false;
            }
        }
    }

    private static class CaixeiroViajanteBean {

        public int[] tempSolucao;
        public int[] melhorSolucao;
        public int valorMelhorSolucao;
        public int valorSolucaoAtual;
        public int[][] matrizDistancia;

        public CaixeiroViajanteBean(int n) {
            tempSolucao = new int[n];
            melhorSolucao = new int[n];
            valorMelhorSolucao = INF;
            valorSolucaoAtual = 0;
            matrizDistancia = new int[n][n];
        }
    }
}