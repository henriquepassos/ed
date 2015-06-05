package br.com.henriquespassos.ed;

import java.util.LinkedList;

public class GrafoConexoArranjo {

    public static void main(String[] args) {

        GrafoConexoArranjo g = new GrafoConexoArranjo();

        No no1 = new No();
        No no2 = new No();
        No no3 = new No();
        No no4 = new No();
        No no5 = new No();
        No no6 = new No();
        No no7 = new No();

        no1.vizinho = new No[1];
        no2.vizinho = new No[2];
        no3.vizinho = new No[1];
        no4.vizinho = new No[0];
        no5.vizinho = new No[1];
        no6.vizinho = new No[1];
        no7.vizinho = new No[0];

        no1.vizinho[0] = no2;

        no2.vizinho[0] = no3;
        no2.vizinho[1] = no5;

        no3.vizinho[0] = no2;
        no3.vizinho[0] = no4;

        no5.vizinho[0] = no1;

        no6.vizinho[0] = no7;

        No[] nos = new No[7];

        nos[0] = no1;
        nos[1] = no2;
        nos[2] = no3;
        nos[3] = no4;
        nos[4] = no5;
        nos[5] = no6;
        nos[6] = no7;

        LinkedList<Integer> list = g.visitarViz(nos);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%d -> %d\n", i + 1, list.get(i));
        }
    }

    public LinkedList<Integer> visitarViz(No[] nos) {
        LinkedList<Integer> list = new LinkedList<Integer>();
        for (No t : nos) {
            if (!t.visitado) {
                t.visitado = true;
                int v = visitadoVizinhos(t);
                list.add(v);
            }
        }
        return list;
    }

    public int visitadoVizinhos(No atual) {
        int cont = 1;
        atual.visitado = true;
        for (No t : atual.vizinho) {
            if (!t.visitado) {
                cont += visitadoVizinhos(t);
            }
        }
        return cont;
    }
}