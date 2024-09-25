package aula2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AquecendoDois {

    public static class Grafo {

        private final String[][] matrizAdjacente; // Agora é uma matriz de Strings para armazenar o tipo de relacionamento
        private final List<String> vertices;
        private final int larguraMaximaNome;

        public Grafo(Set<String> verticesUnicos) {
            int numNodos = verticesUnicos.size();
            this.matrizAdjacente = new String[numNodos][numNodos]; // Inicializa a matriz com Strings
            this.vertices = new ArrayList<>(verticesUnicos);
            this.larguraMaximaNome = vertices.stream().mapToInt(String::length).max().orElse(0);
        }

        private int getIndiceVertice(String vertice) {
            return vertices.indexOf(vertice);
        }

        public void adicionaAresta(String origem, String destino, String tipoDeGrafo, String relacao) {
            int i = getIndiceVertice(origem);
            int j = getIndiceVertice(destino);

            if (i < 0 || j < 0) {
                throw new IllegalArgumentException("Um ou ambos os vértices não existem.");
            }

            matrizAdjacente[i][j] = relacao; // Armazena o tipo da relação na matriz

            if ("grafo".equalsIgnoreCase(tipoDeGrafo)) {
                matrizAdjacente[j][i] = relacao; // Aresta bidirecional
            }
        }

        public void removeAresta(String origem, String destino, String tipoDeGrafo) {
            int i = getIndiceVertice(origem);
            int j = getIndiceVertice(destino);

            if (i < 0 || j < 0) {
                throw new IllegalArgumentException("Um ou ambos os vértices não existem.");
            }

            matrizAdjacente[i][j] = null; // Remove a relação da matriz

            if ("grafo".equalsIgnoreCase(tipoDeGrafo)) {
                matrizAdjacente[j][i] = null; // Remove a aresta bidirecional
            }

            System.out.println("Aresta entre " + origem + " e " + destino + " removida.");
        }

        public void imprimeGrafo() {
            System.out.printf("%-" + larguraMaximaNome + "s ", " ");
            for (String vertice : vertices) {
                System.out.printf("%-" + larguraMaximaNome + "s ", vertice);
            }
            System.out.println();

            for (int i = 0; i < vertices.size(); i++) {
                System.out.printf("%-" + larguraMaximaNome + "s ", vertices.get(i));
                for (int j = 0; j < vertices.size(); j++) {
                    String relacao = matrizAdjacente[i][j] != null ? matrizAdjacente[i][j] : "-";
                    System.out.printf("%-" + larguraMaximaNome + "s ", relacao);
                }
                System.out.println();
            }
        }

        public void imprimeMatrizAdjacente() {
            System.out.printf("%-" + larguraMaximaNome + "s ", " ");
            for (String vertice : vertices) {
                System.out.printf("%-" + larguraMaximaNome + "s ", vertice);
            }
            System.out.println();

            for (int i = 0; i < vertices.size(); i++) {
                System.out.printf("%-" + larguraMaximaNome + "s ", vertices.get(i));
                for (int j = 0; j < vertices.size(); j++) {
                    // Se houver uma relação, imprime 1, caso contrário, imprime 0
                    int valor = (matrizAdjacente[i][j] != null) ? 1 : 0;
                    System.out.printf("%-" + larguraMaximaNome + "d ", valor);
                }
                System.out.println();
            }
        }

        public void consultaAresta(String origem, String destino) {
            int i = getIndiceVertice(origem);
            int j = getIndiceVertice(destino);

            if (i < 0 || j < 0) {
                System.out.println("Um ou ambos os vértices não existem.");
                return;
            }

            String relacao = matrizAdjacente[i][j];
            if (relacao != null) {
                System.out.println("Aresta entre " + origem + " e " + destino + ": " + relacao);
            } else {
                System.out.println("Não existe uma aresta entre " + origem + " e " + destino);
            }
        }

        public void consultaVertice(String vertice) {
            int i = getIndiceVertice(vertice);
            if (i < 0) {
                System.out.println("Vértice não encontrado.");
                return;
            }

            System.out.println("Conexões de " + vertice + ":");
            for (int j = 0; j < vertices.size(); j++) {
                if (matrizAdjacente[i][j] != null) {
                    System.out.println(vertice + " -> " + vertices.get(j) + " : " + matrizAdjacente[i][j]);
                }
            }
        }

        public void grauVertice(String vertice, String tipoDeGrafo) {
            int i = getIndiceVertice(vertice);
            if (i < 0) {
                System.out.println("Vértice não encontrado.");
                return;
            }

            if ("grafo".equalsIgnoreCase(tipoDeGrafo)) {
                int grau = 0;
                for (int j = 0; j < vertices.size(); j++) {
                    if (matrizAdjacente[i][j] != null) {
                        grau++;
                    }
                }
                System.out.println("Grau do vértice " + vertice + ": " + grau);
            } else {
                int grauEntrada = 0;
                int grauSaida = 0;
                for (int j = 0; j < vertices.size(); j++) {
                    if (matrizAdjacente[i][j] != null) {
                        grauSaida++;
                    }
                    if (matrizAdjacente[j][i] != null) {
                        grauEntrada++;
                    }
                }
                System.out.println("Grau de Entrada de " + vertice + ": " + grauEntrada);
                System.out.println("Grau de Saída de " + vertice + ": " + grauSaida);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("src/aula2/tempo.txt"));

            Set<String> verticesUnicos = new HashSet<>();
            List<String[]> arestas = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                String[] partes = linha.split(" : ");

                if (partes.length >= 2) { // Adicionando a relação (ally, enemy, family)
                    String relacao = partes[0];
                    String tipoRelacao = partes[1].trim();
                    String[] vertices = relacao.split(" <-> | -> ");
                    if (vertices.length == 2) {
                        verticesUnicos.add(vertices[0].trim());
                        verticesUnicos.add(vertices[1].trim());
                        arestas.add(new String[]{vertices[0].trim(), vertices[1].trim(), relacao.contains("<->") ? "grafo" : "digrafo", tipoRelacao});
                    }
                }
            }

            Grafo grafo = new Grafo(verticesUnicos);

            for (String[] aresta : arestas) {
                grafo.adicionaAresta(aresta[0], aresta[1], aresta[2], aresta[3]);
            }

            // Menu para interagir com o grafo
            Scanner inputScanner = new Scanner(System.in);
            String tipoDeGrafo = arestas.get(0)[2]; // Usando o tipo de grafo da primeira aresta

            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Imprimir o grafo");
                System.out.println("2. Imprimir matriz adjacente");
                System.out.println("3. Consultar aresta");
                System.out.println("4. Consultar vértice");
                System.out.println("5. Informar grau do vértice");
                System.out.println("6. Remover aresta");
                System.out.println("7. Sair");
                System.out.print("Escolha uma opção: ");
                int opcao = inputScanner.nextInt();
                inputScanner.nextLine(); // Consome a quebra de linha

                switch (opcao) {
                    case 1:
                        grafo.imprimeGrafo();
                        break;
                    case 2:
                        grafo.imprimeMatrizAdjacente();
                        break;
                    case 3:
                        System.out.print("Digite o vértice de origem: ");
                        String origem = inputScanner.nextLine();
                        System.out.print("Digite o vértice de destino: ");
                        String destino = inputScanner.nextLine();
                        grafo.consultaAresta(origem, destino);
                        break;
                    case 4:
                        System.out.print("Digite o vértice para consulta: ");
                        String vertice = inputScanner.nextLine();
                        grafo.consultaVertice(vertice);
                        break;
                    case 5:
                        System.out.print("Digite o vértice para verificar o grau: ");
                        String verticeGrau = inputScanner.nextLine();
                        grafo.grauVertice(verticeGrau, tipoDeGrafo);
                        break;
                    case 6:
                        System.out.print("Digite o vértice de origem da aresta a remover: ");
                        String origemRemocao = inputScanner.nextLine();
                        System.out.print("Digite o vértice de destino da aresta a remover: ");
                        String destinoRemocao = inputScanner.nextLine();
                        grafo.removeAresta(origemRemocao, destinoRemocao, tipoDeGrafo);
                        break;
                    case 7:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
}
