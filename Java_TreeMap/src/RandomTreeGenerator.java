import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Random;

public class RandomTreeGenerator {
    public static void treeGenerator(int n, String name) throws IOException {
        LinkedList<Integer> availableNodes = new LinkedList<>();
        LinkedList<Integer> connectedNodes = new LinkedList<>();
        Random rand = new Random();
        
        // Inizializzazione
        for (int i = 1; i <= n; i++) {
            if (i != 1) { // Escludi la radice
                availableNodes.add(i);
            }
        }
        
        connectedNodes.add(1); // La radice è il primo nodo connesso

        try (PrintWriter writer = new PrintWriter(new FileWriter(name))) {
            // Assicura che ogni nodo sia connesso
            while (!availableNodes.isEmpty()) {
                int parentIndex = rand.nextInt(connectedNodes.size());
                int parentNode = connectedNodes.get(parentIndex);
                int childNode = availableNodes.removeFirst(); // Rimuove e ritorna il primo nodo disponibile
                
                writer.println("(" + parentNode + " " + childNode + ")");
                connectedNodes.add(childNode); // Aggiunge il nodo connesso alla lista
                
                // Ulteriori assegnazioni casuali possono essere aggiunte qui se necessario
            }
        }
    }
}
