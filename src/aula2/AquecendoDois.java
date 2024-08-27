package aula2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AquecendoDois {

    /**
     * Classe Grafo que representa um grafo ou dígrafo utilizando uma matriz de adjacência.
     */
    public static class Grafo {

        private final boolean[][] matrizAdjacente;
        private final int numNodos;

        public Grafo(int numNodos) {
            this.numNodos = numNodos;
            this.matrizAdjacente = new boolean[numNodos][numNodos];
        }

        /**
         * Adiciona uma aresta ao grafo ou dígrafo.
         *
         * @param i           índice do primeiro nodo
         * @param j           índice do segundo nodo
         * @param tipoDeGrafo tipo de grafo (grafo ou dígrafo)
         */
        public void adicionaAresta(int i, int j, String tipoDeGrafo) {
            if (i < 0 || j < 0 || i >= numNodos || j >= numNodos) {
                throw new IndexOutOfBoundsException("Índices de nodos fora do intervalo válido.");
            }

            matrizAdjacente[i][j] = true;

            if (!"digrafo".equalsIgnoreCase(tipoDeGrafo) && !"dígrafo".equalsIgnoreCase(tipoDeGrafo)) {
                matrizAdjacente[j][i] = true;
            }
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

        /**
         * Calcula e imprime o grau de cada nodo em um grafo não direcionado.
         */
        public void calculaGrauGrafo() {
            for (int i = 0; i < numNodos; i++) {
                int grau = 0;
                for (int j = 0; j < numNodos; j++) {
                    if (matrizAdjacente[i][j]) {
                        grau++;
                    }
                }
                System.out.println("Grau do nodo " + i + ": " + grau);
            }
        }

        /**
         * Calcula e imprime o grau de entrada e de saída de cada nodo em um dígrafo.
         */
        public void calculaGrauDigrafo() {
            for (int i = 0; i < numNodos; i++) {
                int grauEntrada = 0;
                int grauSaida = 0;
                for (int j = 0; j < numNodos; j++) {
                    if (matrizAdjacente[i][j]) {
                        grauSaida++;
                    }
                    if (matrizAdjacente[j][i]) {
                        grauEntrada++;
                    }
                }
                System.out.println("Nodo " + i + ": Grau de Entrada = " + grauEntrada + ", Grau de Saída = " + grauSaida);
            }
        }

    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Você deseja criar um dígrafo ou um grafo? ");
            String tipoDeGrafo = scanner.nextLine().trim();

            if (!"digrafo".equalsIgnoreCase(tipoDeGrafo) && !"dígrafo".equalsIgnoreCase(tipoDeGrafo)
                    && !"grafo".equalsIgnoreCase(tipoDeGrafo)) {
                throw new IllegalArgumentException("Tipo de grafo inválido. Escolha 'dígrafo' ou 'grafo'.");
            }

            System.out.print("Digite a quantidade de nodos: ");
            int quantidadeDeNodos = scanner.nextInt();
            if (quantidadeDeNodos <= 0) {
                throw new IllegalArgumentException("O número de nodos deve ser maior que zero.");
            }

            System.out.print("Digite quantas arestas terá o " + tipoDeGrafo + ": ");
            int quantidadeDeArestas = scanner.nextInt();

            Grafo grafo = new Grafo(quantidadeDeNodos);

            for (int index = 0; index < quantidadeDeArestas; index++) {
                System.out.println("Digite os nodos conectados pela aresta " + (index + 1) + ":");
                int primeiroNodo = scanner.nextInt();
                int segundoNodo = scanner.nextInt();

                try {
                    grafo.adicionaAresta(primeiroNodo, segundoNodo, tipoDeGrafo);
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Erro: " + e.getMessage() + " Aresta ignorada.");
                }
            }

            System.out.println("Imprimindo matriz adjacente do " + tipoDeGrafo + ":");
            grafo.imprimeGrafo();

            if ("digrafo".equalsIgnoreCase(tipoDeGrafo) || "dígrafo".equalsIgnoreCase(tipoDeGrafo)) {
                grafo.calculaGrauDigrafo();
            } else {
                grafo.calculaGrauGrafo();
            }

        } catch (InputMismatchException e) {
            System.out.println("Erro: Entrada inválida. Certifique-se de digitar números inteiros.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
