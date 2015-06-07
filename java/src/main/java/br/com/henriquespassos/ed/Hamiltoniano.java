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
        // marca o primeiro NO como VISITADO
        g.no[0].visitado = true;
        // adicionar o primeiro NO na SOLUCAO
        solucao[0] = g.no[0];
        // achar um ciclo iniciando na SOLUCAO[0]
        boolean result = cicloHamiltonianoAux(g, solucao, 1);
        return result;
    }

    private static boolean cicloHamiltonianoAux(Grafo g, No[] solucao, int i) {
        // 9) so entro nesse if se minha solucao estiver completa
        if (i == g.adjacente.length) {
            // 10) aqui nesse exato momento eu tenho um caminho hamiltoniano
            //     onde NO SOLUCAO[i] e VIZINHO do NO SOLUCAO[i + 1]
            //     e nao existe repeticao
            //     agora para eu saber se isso e um ciclo hamiltoniano
            //     preciso ver se o ultimo NO SOLUCAO[i - 1] e vizinho do
            //     NO SOLUCAO[0]
            for (No t : solucao[i - 1].vizinho) {
                // 11) deixa eu ver se ha um NO VIZINHO do ultimo
                //     NO da SOLUCAO[i - 1] que e o NO inicial SOLUCAO[0]
                if (t == solucao[0]) {
                    // 12) achei corre para o abraco
                    return true;
                }
            }
            // 13) foi por pouco, achei uma caminho hamiltoniano
            //     que nao forma um ciclo hamiltoniano
            return false;
        }
        // 1) deixa eu acessar todos os NOS VIZINHOS
        //    do ultimo NO que eu adicioneu na SOLUCAO
        //    atraves da sua lista de adjacencias
        for (No t : solucao[i - 1].vizinho) {
            // 2) vou apenas confirmar se esse NO VIZINHO
            //    ja nao foi VISITADO
            if (!t.visitado) {
                // 3) se esse NO VIZINHO nao foi VISITADO
                //    marco como VISITADO
                t.visitado = true;
                // 4) e adiciono da SOLUCAO
                solucao[i] = t;
                // 5) vou tentar encontrar um NO VIZINHO
                //    do ultimo NO adicionado na solucao do passo "4)"
                //    que faca um caminho nao VISITADO
                if (cicloHamiltonianoAux(g, solucao, i + 1))
                    // 6) se recursivamente eu achei esse caminho
                    //    digo que existem um caminho que passe por
                    //    todos os NOS do grafo sem repeticao
                    return true;
                // 7) caso contrario esse caminho nao foi uma boa
                //    vamos marcar como nao VISITADO e tentar o proximo
                //    NO "No t" do laco for
                t.visitado = false;
            }
        }

        // 8) ja visitei todos os vizinhos do NO SOLUCAO[i - 1]
        //    por aqui o caminho nao deu certo
        return false;
    }
}