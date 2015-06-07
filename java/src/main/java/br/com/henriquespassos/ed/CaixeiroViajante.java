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
        // marca o primeiro NO como VISITADO
        g.visitado[i] = true;
        // adicionar o primeiro NO na SOLUCAO
        c.tempSolucao[0] = i;
        // achar um ciclo iniciando na SOLUCAO[0]
        caixeiroViajanteAux(g, c, 1);
        // se a SOLUCAO der DISTANCIA infinita
        // para ficar mais apresentavel retorna DISTANCIA -1
        // que significa uma solucao inalcancavel
        if (c.valorMelhorSolucao < INF) {
            return c.valorMelhorSolucao;
        }
        return -1;
    }

    private static void caixeiroViajanteAux(Grafo g, CaixeiroViajanteBean c, int i) {
        // 9) daqui so passo se a solucao atual ainda
        // for menor do que a melhor solucao
        if (c.valorSolucaoAtual > c.valorMelhorSolucao) {
            return;
        }
        // 10) so entro nesse if se minha solucao estiver completa
        if (i == g.adjacente.length) {
            // 11) aqui nesse exato momento eu tenho um caminho
            //     que passa por todas cidades
            //     sua DISTANCIA e menos do que a melhor solucao
            //     mas ainda nao calculamos o caminho de volta para cidade inicial

            // adicionar volta a cidade inicial
            int dist = c.matrizDistancia[c.tempSolucao[i - 1]][c.tempSolucao[0]];

            // 12) se a DISTANCIA for menor do que infinito temos um ciclo
            //     se a DISTANCIA desse ciclo e menos do que a da melhor solucao
            //     achamos uma rota para o carteiro andar menos pelas cidades
            if (dist < INF && c.valorSolucaoAtual + dist < c.valorMelhorSolucao) {
                System.out.println("\t" + c.valorMelhorSolucao + " -> " + (c.valorSolucaoAtual + dist));
                // mantenho essa DISTANCIA como a melhor DISTANCIA
                c.valorMelhorSolucao = c.valorSolucaoAtual + dist;
                // mantenho essa SOLUCAO como a melhor SOLUCAO
                c.melhorSolucao = c.tempSolucao.clone();

                // melhorSolucao == mapa que apresenta por onde o carteiro devera andar
                // valorMelhorSolucao == a propria DISTANCIA
            }
            return;
        }
        int ultimo = c.tempSolucao[i - 1];
        // 1) deixa eu acessar todos os NOS VIZINHOS
        //    do ultimo NO que eu adicioneu na SOLUCAO
        //    atraves da sua lista de adjacencias
        for (int t = 0; t < c.matrizDistancia.length; t++) {
            // 2) vou apenas confirmar se esse NO VIZINHO
            //    ja nao foi VISITADO
            //    e se existe um caminho (onde exista o caminho == caminho nao e infinito)
            if (!g.visitado[t] && c.matrizDistancia[ultimo][t] < INF) {
                // 3) se esse NO VIZINHO nao foi VISITADO
                //    marco como VISITADO
                g.visitado[t] = true;
                // 4) e adiciono da SOLUCAO
                c.tempSolucao[i] = t;
                // recalculo a DISTANCIA da SOLUCAO
                // adicionando a DISTANCIA da ultima SOLUCAO
                c.valorSolucaoAtual += c.matrizDistancia[ultimo][t];
                // 6) vou recursivamente achei o melhor caminho
                //    que passe por todos os NOS do grafo sem repeticao
                //    com o menor caminho
                caixeiroViajanteAux(g, c, i + 1);
                // recalculo a DISTANCIA da SOLUCAO
                // adicionando a DISTANCIA da ultima SOLUCAO
                c.valorSolucaoAtual -= c.matrizDistancia[ultimo][t];
                // 7) caso contrario esse caminho nao foi uma boa
                //    vamos marcar como nao VISITADO e tentar os proximos
                //    NOS do laco for
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