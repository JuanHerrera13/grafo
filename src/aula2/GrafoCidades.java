package aula2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GrafoCidades {

    /**
     * Classe Grafo que representa um grafo não direcionado de cidades usando uma matriz de adjacência.
     */
    public static class Grafo {

        private final boolean[][] matrizAdjacente;
        private final int numNodos;
        private final List<String> cidades;

        public Grafo(int numNodos, List<String> cidades) {
            this.numNodos = numNodos;
            this.cidades = new ArrayList<>(cidades);
            this.matrizAdjacente = new boolean[numNodos][numNodos];
        }

        /**
         * Adiciona uma aresta não direcionada entre os nodos i e j.
         *
         * @param i índice do primeiro nodo
         * @param j índice do segundo nodo
         */
        public void adicionaAresta(int i, int j) {
            if (i < 0 || j < 0 || i >= numNodos || j >= numNodos) {
                throw new IndexOutOfBoundsException("Índices de nodos fora do intervalo válido.");
            }
            matrizAdjacente[i][j] = true;
            matrizAdjacente[j][i] = true;
        }

        /**
         * Imprime a matriz de adjacência do grafo, associando os índices dos nodos às cidades.
         */
        public void imprimeGrafo() {
            System.out.print("    ");
            for (String cidade : cidades) {
                System.out.print(cidade + " ");
            }
            System.out.println();
            for (int i = 0; i < numNodos; i++) {
                System.out.print(cidades.get(i) + " ");
                for (int j = 0; j < numNodos; j++) {
                    System.out.print(" " + (matrizAdjacente[i][j] ? "1" : "0") + " ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        final List<String> cidades = Arrays.asList("PA", "PR", "SP", "RJ", "DF");
        final int numCidades = cidades.size();

        final Grafo grafo = new Grafo(numCidades, cidades);

        // Porto Alegre vai a Curitiba
        grafo.adicionaAresta(0, 1);
        // Porto Alegre vai a Rio de Janeiro
        grafo.adicionaAresta(0, 3);
        // Curitiba vai a São Paulo
        grafo.adicionaAresta(1, 2);
        // São Paulo vai a Rio de Janeiro
        grafo.adicionaAresta(2, 3);
        // São Paulo vai a Brasília
        grafo.adicionaAresta(2, 4);

        System.out.println("Imprimindo matriz adjacente do grafo:");
        grafo.imprimeGrafo();
    }
}
