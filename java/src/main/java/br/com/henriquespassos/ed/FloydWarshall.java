package br.com.henriquespassos.ed;

public class FloydWarshall {

    public static void main(String[] args) {
        long[][] a = {
                {0, 4, 2, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {4, 0, 1, 5, Integer.MAX_VALUE, Integer.MAX_VALUE},
                {2, 1, 0, 8, 10, Integer.MAX_VALUE},
                {Integer.MAX_VALUE, 5, 8, 0, 2, 6},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, 10, 2, 0, 2},
                {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 6, 2, 0}
        };

        floydWarshall(a);

        System.out.println(FloydWarshall.class.getName());
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                System.out.printf("%-11s", a[i][j]);
            }
            System.out.println();
        }
    }

    public static void floydWarshall(long[][] a) {
        for (int x = 0; x < a.length; x++) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a.length; j++) {
                    if (a[i][j] > a[i][x] + a[x][j] && a[i][x] != Integer.MAX_VALUE && a[x][j] != Integer.MAX_VALUE) {
                        a[i][j] = a[i][x] + a[x][j];
                    }
                }
            }
        }
    }
}