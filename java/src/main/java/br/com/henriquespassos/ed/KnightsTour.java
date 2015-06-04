package br.com.henriquespassos.ed;

public class KnightsTour {

    final int[] dx = {2, 1, 1, 2, 2, 1, 1, 2};
    final int[] dy = {1, 2, 2, 1, 1, 2, 2, 1};
    final int num; // numero de posicoes do tabuleiro
    final int numSqr; // numero total de casas
    int[][] table;

    public KnightsTour(int num) {
        this.num = num;
        this.numSqr = num * num;
        this.table = new int[num][num];
    }

    boolean isAcceptable(int x, int y) {
        boolean result = (x >= 0 && x <= num - 1);
        result = result && (y >= 0 && y <= num - 1);
        result = result && (table[x][y] == 0);
        return result;
    }

    boolean tryMove(int i, int x, int y) {
        // verifica a quantidade de movimentos
        boolean done = (i > numSqr);
        int k = 0;
        int u, v;
        while (!done && k < 8) {
            u = x + dx[k];
            v = y + dy[k];
            if (isAcceptable(u, v)) {
                table[u][v] = i;
                done = tryMove(i + 1, u, v); // tenta outro movimento
                if (!done) {
                    table[u][v] = 0; // sem sucesso, descarta movimento
                }
            }
            k = k + 1; // passa ao proximo movimento possivel
        }
        return done;
    }

    void showTour(int x, int y) {
        table[x][y] = 1;
        boolean done = tryMove(2, x, y);
        if (done) {
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    System.out.print(table[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println("Nao ha passeio possivel");
        }
    }

    public static void main(String[] args) {
        new KnightsTour(8).showTour(0, 0);
    }
}