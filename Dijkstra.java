import java.util.*;

public class Dijkstra {
    
    static class Edge {
        int destination, weight;
        Edge(int destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        private final int vertices;
        private final List<List<Edge>> adjList;

        Graph(int vertices) {
            this.vertices = vertices;
            adjList = new ArrayList<>(vertices);
            for (int i = 0; i < vertices; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        void addEdge(int source, int destination, int weight) {
            adjList.get(source).add(new Edge(destination, weight));
            adjList.get(destination).add(new Edge(source, weight)); 
        }

        void dijkstra(int startVertex) {
            PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
            int[] distances = new int[vertices];
            boolean[] visited = new boolean[vertices];

            Arrays.fill(distances, Integer.MAX_VALUE);
            pq.add(new Edge(startVertex, 0));
            distances[startVertex] = 0;

            while (!pq.isEmpty()) {
                Edge edge = pq.poll();
                int vertex = edge.destination;

                if (visited[vertex]) continue;
                visited[vertex] = true;

                for (Edge neighbor : adjList.get(vertex)) {
                    int newDist = distances[vertex] + neighbor.weight;
                    if (newDist < distances[neighbor.destination]) {
                        distances[neighbor.destination] = newDist;
                        pq.add(new Edge(neighbor.destination, newDist));
                    }
                }
            }
            
            System.out.println("-------------------------------");
            System.out.println("Distâncias a partir de Goiânia:");
            for (int i = 0; i < vertices; i++) {
                System.out.println(cities.get(i) + " : " + distances[i]);
            }
        }
    }

    private static List<String> cities;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> cityIndexMap = new HashMap<>();
        cities = new ArrayList<>();

        System.out.println("Informe o nome de 10 cidades, começando por Goiânia:");
        for (int i = 0; i < 10; i++) {
            String city = scanner.nextLine();

            if (i == 0 && !city.equals("Goiânia")) {
                System.out.println("Cidade inválida! A primeira cidade deve ser Goiânia!");
                i--;
                continue;
            }

            cityIndexMap.put(city, i);
            cities.add(city);
        }

        Graph graph = new Graph(10);

        System.out.println("Informe as conexões entre as cidades no formato 'Cidade1 Cidade2 Distância'. Digite 'done' para finalizar:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                System.out.println("Formato inválido! Use o formato 'Cidade1 Cidade2 Distância'.");
                continue;
            }

            String city1 = parts[0];
            String city2 = parts[1];
            int distance;

            try {
                distance = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Distância inválida! A distância deve ser um número inteiro.");
                continue;
            }

            if (!cityIndexMap.containsKey(city1) || !cityIndexMap.containsKey(city2)) {
                System.out.println("Cidades inválidas! Certifique-se de que ambas as cidades foram informadas na lista inicial.");
                continue;
            }

            int index1 = cityIndexMap.get(city1);
            int index2 = cityIndexMap.get(city2);

            graph.addEdge(index1, index2, distance);
        }

        int startVertex = cityIndexMap.get("Goiânia");
        graph.dijkstra(startVertex);
    }
}
