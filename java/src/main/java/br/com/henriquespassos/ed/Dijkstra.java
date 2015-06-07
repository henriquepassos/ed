package br.com.henriquespassos.ed;

public class Dijkstra {

    public static void main(String[] args) {
        long[][] a = {
                {0, 4, 2, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {4, 0, 1, 5, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {2, 1, 0, 8, 10, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 5, 8, 0, 2, 6},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, 10, 2, 0, 2},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 6, 2, 0}
        };

        dijkstra(a);

        System.out.println(Dijkstra.class.getName());
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.printf("%-11s", a[i][j]);
            }
            System.out.println();
        }
    }

    public static void dijkstra(long[][] a) {
        boolean[] visitado = new boolean[a.length];
        for (int i = 1; i < a.length; i++) {
            // indice da menor distancia aos adjacentes
            int min = -1;
            // valor da menor distancia aos adjacentes
            long minVal = Integer.MAX_VALUE;
            // vou visitar todos os NOS
            for (int j = 1; j < a.length; j++) {
                // todos nao so os NOS nao VISITADOS ainda
                // que tenha distancia menor do que a menor distancia aos adjacentes
                if (!visitado[j] && a[j][0] < minVal) {
                    // atribuir indice da menor distancia aos adjacentes
                    min = j;
                    // atribuir valor da menor distancia aos adjacentes
                    minVal = a[j][0];
                }
            }
            // marcar o valor da menor distancia como visitado
            visitado[min] = true;
            // vou visitar todos os NOS
            for (int j = 1; j < a.length; j++) {
                // a[min][0] e o NO selecionado de menor distancia
                // a[min][j] e o distancia de sair do NO selecionado de menor distancia
                //           e chegar ao seus adjacentes
                // a[j][0]   estou recalculando as distancias ao seus adjacentes
                if (a[j][0] > a[min][0] + a[min][j]) {
                    a[j][0] = a[min][0] + a[min][j];
                }
            }
        }
    }
}