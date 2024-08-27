package aula1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    /**
     * Classe Grafo que representa um grafo não direcionado usando uma matriz de adjacência.
     */
    public static class Grafo {

        private final boolean[][] matrizAdjacente;
        private final int numNodos;

        public Grafo(int numNodos) {
            this.numNodos = numNodos;
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
         * Imprime a matriz de adjacência do grafo.
         */
        public void imprimeGrafo() {
            for (int i = 0; i < numNodos; i++) {
                for (int j = 0; j < numNodos; j++) {
                    System.out.print(matrizAdjacente[i][j] ? "1 " : "0 ");
                }
                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Digite a quantidade de nodos: ");
            int quantidadeDeNodos = scanner.nextInt();

            if (quantidadeDeNodos <= 0) {
                throw new IllegalArgumentException("O número de nodos deve ser maior que zero.");
            }

            Grafo grafo = new Grafo(quantidadeDeNodos);

            System.out.print("Digite quantas arestas terá o grafo: ");
            int quantidadeDeArestas = scanner.nextInt();

            for (int index = 0; index < quantidadeDeArestas; index++) {
                System.out.println("Digite os nodos conectados pela aresta " + (index + 1) + ":");
                int primeiroNodo = scanner.nextInt();
                int segundoNodo = scanner.nextInt();

                try {
                    grafo.adicionaAresta(primeiroNodo, segundoNodo);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Erro: " + e.getMessage() + " Aresta ignorada.");
                }
            }

            System.out.println("Imprimindo matriz adjacente do grafo:");
            grafo.imprimeGrafo();

        } catch (InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. Certifique-se de digitar números inteiros.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
