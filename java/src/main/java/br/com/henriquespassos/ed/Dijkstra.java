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
            int min = -1;
            long minVal = Integer.MAX_VALUE;
            for (int j = 1; j < a.length; j++) {
                if (!visitado[j] && a[j][0] < minVal) {
                    min = j;
                    minVal = a[j][0];
                }
            }
            visitado[min] = true;
            for (int j = 1; j < a.length; j++) {
                if (a[j][0] > a[min][0] + a[min][j]) {
                    a[j][0] = a[min][0] + a[min][j];
                }
            }
        }
    }
}