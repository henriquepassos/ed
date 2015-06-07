package br.com.henriquespassos.ed;

public class DijkstraArranjo {

    // TODO: no futuro :)
    public static void main(String[] args) {
        Grafo g = new Grafo();
        g.a = new long[][]{
                {0, 4, 2, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {4, 0, 1, 5, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {2, 1, 0, 8, 10, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 5, 8, 0, 2, 6},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, 10, 2, 0, 2},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 6, 2, 0}
        };
        g.visitado = new boolean[g.a.length];
        g.no = new No[g.a.length];

        int[] total = new int[g.a.length];

        for (int i = 0; i < g.a.length; i++) {
            g.no[i] = new No();
            for (int j = 0; j < g.a.length; j++) {
                if (g.a[i][j] != Integer.MAX_VALUE) {
                    total[i]++;
                }
            }
        }

        for (int i = 0; i < g.a.length; i++) {
            g.no[i].vizinho = new No[total[i]];
            total[i] = 0;
            for (int j = 0; j < g.a.length; j++) {
                if (g.a[i][j] != Integer.MAX_VALUE) {
                    g.no[i].vizinho[total[i]++] = g.no[j];
                }
            }
        }

        dijkstra(g);

        System.out.println(DijkstraArranjo.class.getName());
        for (int i = 0; i < g.a.length; i++) {
            for (int j = 0; j < g.a[i].length; j++) {
                System.out.printf("%-11s", g.a[i][j]);
            }
            System.out.println();
        }
    }

    public static void dijkstra(Grafo g) {
        for (int i = 1; i < g.a.length; i++) {
            // indice da menor distancia aos adjacentes
            int min = -1;
            // valor da menor distancia aos adjacentes
            long minVal = Integer.MAX_VALUE;
            // vou visitar todos os NOS
            for (int j = 1; j < g.a.length; j++) {
                // todos nao so os NOS nao VISITADOS ainda
                // que tenha distancia menor do que a menor distancia aos adjacentes
                if (!g.visitado[j] && g.a[j][0] < minVal) {
                    // atribuir indice da menor distancia aos adjacentes
                    min = j;
                    // atribuir valor da menor distancia aos adjacentes
                    minVal = g.a[j][0];
                }
            }
            // marcar o valor da menor distancia como visitado
            g.visitado[min] = true;
            // vou visitar todos os NOS
            for (int j = 1; j < g.a.length; j++) {
                // a[min][0] e o NO selecionado de menor distancia
                // a[min][j] e o distancia de sair do NO selecionado de menor distancia
                //           e chegar ao seus adjacentes
                // a[j][0]   estou recalculando as distancias ao seus adjacentes

                // e chamado de processo de relaxamento
                // eu percorri determinados NOS e a[min][0] e o NO selecionado de menor distancia
                // sera que eu posso melhorar a DISTANCIA de a[j][0] passando por a[min][j]
                if (g.a[j][0] > g.a[min][0] + g.a[min][j]) {
                    g.a[j][0] = g.a[min][0] + g.a[min][j];
                }
            }
        }
    }
}