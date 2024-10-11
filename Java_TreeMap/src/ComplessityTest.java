import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class ComplessityTest {
    //public static String nomeFile = "C:\\Users\\Riccardo\\Desktop\\Università\\Tesi\\Java_TreeMap\\Test\\100Elements\\randomTree1.txt";
    public static int[] dim = {100, 500, 1000, 2000, 3000, 5000, 8000, 10000, 20000, 30000, 40000, 50000, 60000, 80000, 100000};

   public static void main(String[] args) {
    /*for(int j=1;j<3;j++){
       for(int i=1;i<11;i++){
        String nomeFile = "C:\\Users\\Riccardo\\Desktop\\Università\\Tesi\\Java_TreeMap\\Test\\"+dim[j]+"Nodes\\randomTree"+i+".txt";
        try {
          RandomTreeGenerator.treeGenerator(dim[j], nomeFile);
          } catch (IOException e) {
          System.err.println("Errore durante la scrittura del file: " + e.getMessage());
          }
          /*Tree tree = new Tree();
          try {
              tree = treeReader(nomeFile);
            } catch (IOException e) {
              e.printStackTrace();
            }
          Node root = tree.getRoot();
          tree.setTreeParentsRecursive(root);
          tree.calculateSubtreeSizeRecursive(root);
          tree.calculateLeafDepthRecursive(root, 0);
          }
      }
    }*/
    System.out.println("Inizio Test:");
      for(int j=0;j<12;j++){
        double media=0;
        double mediaAR = 0;
        double mediaVolume = 0;
        for(int i=5;i<11;i++){
            Rectangle availableSpace = new Rectangle(new Point(0, 0), 5000, 5000);
            String nomeFile = "C:\\Users\\Riccardo\\Desktop\\Università\\Tesi\\Java_TreeMap\\Test\\"+dim[j]+"Nodes\\randomTree"+i+".txt";
            Tree tree = new Tree();
            try {
                tree = treeReader(nomeFile);
              } catch (IOException e) {
                e.printStackTrace();
              }
            Node root = tree.getRoot();
              //tree.calculateLeafDepthRecursive(root, 0);
              
            long startTime = System.currentTimeMillis();
            //TreeMap.calculated3DCordinates(tree);
            tree.setTreeParentsRecursive(root);
            tree.calculateSubtreeSizeRecursive(root);
            tree.calculateLeafDepthRecursive(root, 0);
            //TreeMap.treeMapBoundsSplit(tree, availableSpace);
            TreeMap.treeMapBoundsSliceNDice(tree, availableSpace);
            //LayerTreeDraw3D.layoutTree(root, 0, 0);
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            media+=duration;
            //System.out.println(mediaAR);
            mediaAR+=TreeMap.mediumAspectRatio(tree);

            //Volume
            /*int maxDepth = 0;
            for(int p=0; p<tree.getNodes().size(); p++){
              if(tree.getNodes().get(p).getDepth()>maxDepth){
                  maxDepth = tree.getNodes().get(p).getDepth();
              }
            }*/
            //mediaVolume+=(dim[j]*maxDepth);
            //System.out.println(maxDepth);
            //System.out.println(mediaVolume);
            //mediaVolume+=((tree.getRoot().getBound().getWidth()*tree.getRoot().getBound().getHeight()));

        }
        media/=10;
        mediaAR/=10;
        mediaVolume/=10;
        //System.out.println("Il tempo medio impiegato per "+dim[j]+" nodi è: "+media+" ms e l'AR medio è: "+ mediaAR+" e il volume medio è: "+mediaVolume);
        System.out.println(mediaAR);
      }
    }


    public static Tree treeReader(String nomeFile) throws IOException {
        Tree tree = new Tree();
        LinkedList<Integer> buffer = new LinkedList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.replaceAll("[()]", ""); // Rimuove le parentesi
                String[] parts = linea.trim().split("\\s+");
                int idPadre = Integer.parseInt(parts[0]);
                int idFiglio = Integer.parseInt(parts[1]);
                

                if(!buffer.contains(idPadre)){
                  buffer.add(idPadre);
                  Node p = new Node();
                  p.setId(idPadre);
                  tree.getNodes().add(p);
                }
                if(!buffer.contains(idFiglio)){
                  buffer.add(idFiglio);
                  Node c = new Node();
                  c.setId(idFiglio);
                  tree.getNodes().add(c);
                }
                Node parent = null;
                Node child = null;
                for(Node n : tree.getNodes()){
                  if(n.getId()==idPadre){
                    parent = n;
                  }
                  if(n.getId()==idFiglio){
                    child = n;
                  }
                }
                parent.addChild(child);

                if (tree.getRoot() == null || tree.getRoot().getId() > idPadre) {
                    tree.setRoot(parent); // Assume il nodo con l'ID minimo come radice
                }
            }
        }
        return tree;
    }
}
