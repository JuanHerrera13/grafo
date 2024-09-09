package aula2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AquecendoDois {

    /**
     * Classe Grafo que representa um grafo ou dígrafo utilizando uma matriz de adjacência.
     */
    public static class Grafo {

        private final boolean[][] matrizAdjacente; // Matriz de adjacência para armazenar as arestas
        private final int numNodos; // Número total de nodos

        public Grafo(int numNodos) {
            this.numNodos = numNodos;
            this.matrizAdjacente = new boolean[numNodos][numNodos]; // Inicializa a matriz de adjacência
        }

        /**
         * Adiciona uma aresta ao grafo ou dígrafo.
         *
         * @param i           índice do primeiro nodo (internamente começa em 0)
         * @param j           índice do segundo nodo (internamente começa em 0)
         * @param tipoDeGrafo tipo de grafo (grafo ou dígrafo)
         */
        public void adicionaAresta(int i, int j, String tipoDeGrafo) {
            if (i < 0 || j < 0 || i >= numNodos || j >= numNodos) {
                throw new IndexOutOfBoundsException("Índices de nodos fora do intervalo válido.");
            }

            if (matrizAdjacente[i][j]) {
                System.out.println("Aresta já existe entre os nodos " + (i + 1) + " e " + (j + 1));
                return; // Aresta já existe, não é necessário adicionar novamente
            }

            matrizAdjacente[i][j] = true; // Adiciona a aresta do primeiro nodo para o segundo

            // Se for um grafo (não direcionado), adiciona também a aresta na direção oposta
            if (!"digrafo".equalsIgnoreCase(tipoDeGrafo) && !"dígrafo".equalsIgnoreCase(tipoDeGrafo)) {
                matrizAdjacente[j][i] = true;
            }
        }

        /**
         * Imprime a matriz de adjacência do grafo ou dígrafo.
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
                System.out.println("Grau do nodo " + (i + 1) + ": " + grau); // Adiciona 1 ao índice para exibir corretamente ao usuário
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
                System.out.println("Nodo " + (i + 1) + ": Grau de Entrada = " + grauEntrada + ", Grau de Saída = " + grauSaida);
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Você deseja criar um dígrafo ou um grafo? ");
            String tipoDeGrafo = scanner.nextLine().trim();

            // Verifica se o tipo de grafo é válido (grafo ou dígrafo)
            if (!"digrafo".equalsIgnoreCase(tipoDeGrafo) && !"dígrafo".equalsIgnoreCase(tipoDeGrafo)
                    && !"grafo".equalsIgnoreCase(tipoDeGrafo)) {
                throw new IllegalArgumentException("Tipo de grafo inválido. Escolha 'dígrafo' ou 'grafo'.");
            }

            System.out.print("Digite a quantidade de nodos: ");
            int quantidadeDeNodos = scanner.nextInt();
            if (quantidadeDeNodos <= 0) {
                throw new IllegalArgumentException("O número de nodos deve ser maior que zero.");
            }

            Grafo grafo = new Grafo(quantidadeDeNodos); // Cria o grafo com o número de nodos especificado

            // Loop para adicionar arestas até o usuário optar por parar
            while (true) {
                System.out.println("Digite os nodos conectados pela aresta:");
                int primeiroNodo = scanner.nextInt() - 1; // Subtrai 1 para ajustar o índice (usuário insere de 1 a n)
                int segundoNodo = scanner.nextInt() - 1;  // Subtrai 1 para ajustar o índice

                // Verifica se os nodos estão dentro do intervalo válido
                if (primeiroNodo < 0 || primeiroNodo >= quantidadeDeNodos || segundoNodo < 0 || segundoNodo >= quantidadeDeNodos) {
                    System.out.println("Erro: Índices de nodos fora do intervalo válido. Aresta ignorada.");
                } else {
                    try {
                        grafo.adicionaAresta(primeiroNodo, segundoNodo, tipoDeGrafo);
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Erro: " + e.getMessage() + " Aresta ignorada.");
                    }
                }

                // Pergunta ao usuário se deseja adicionar mais arestas
                System.out.print("Deseja adicionar outra aresta? (s/n): ");
                String resposta = scanner.next().trim().toLowerCase();

                if (resposta.equals("n")) {
                    break; // Sai do loop se o usuário responder "não"
                }
            }

            // Imprime a matriz de adjacência
            System.out.println("Imprimindo matriz adjacente do " + tipoDeGrafo + ":");
            grafo.imprimeGrafo();

            // Calcula e imprime os graus dependendo do tipo de grafo
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
