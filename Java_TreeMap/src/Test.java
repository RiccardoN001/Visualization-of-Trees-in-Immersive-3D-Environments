import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.JComponent;
import javax.swing.JFrame;

class MyCanvas extends JComponent {
  //RandomTreeGenerator rand = new RandomTreeGenerator(3, 8);
  String nomeFile = "C:\\Users\\Riccardo\\Desktop\\Università\\Tesi\\Java_TreeMap\\inputTree.txt";
  
//================================================FILE READER============================================
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
//================================================FILE READER============================================

  
//=======================================FILE WRITER========================================================
public static void fileGenerator(Tree tree, Node node, String nomeFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeFile))) {
            writeCordinates(writer, tree);
            writeRelations(writer, node);
        }
  }

private static void writeCordinates(PrintWriter writer, Tree tree){
  writer.println((1)+":  " + tree.getRoot().getCoordinates().getX()+","+tree.getRoot().getCoordinates().getY()+","+tree.getRoot().getCoordinates().getZ()+ " : "+tree.getRoot().getBound().getWidth()+","+ tree.getRoot().getBound().getHeight());
  for(Node node : tree.getNodes()){
    writer.println((node.getId())+":  " + node.getCoordinates().getX()+","+node.getCoordinates().getY()+","+node.getCoordinates().getZ()+ " : "+node.getBound().getWidth()+","+ node.getBound().getHeight());
  }
}
private static void writeRelations(PrintWriter writer, Node node) {
        if (node == null) return;

        for (Node n : node.getChildren()) {
            // Scrivi la relazione genitore-figlio nel file
            writer.println("(" + node.getId() + " " + n.getId() + ")");
            // Ricorsione sui figli
            writeRelations(writer, n);
        }
}
//=======================================FILE WRITER========================================================
  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    Tree tree = new Tree();
        

        //tree = rand.getTree();
        try {
          tree = treeReader(nomeFile);
        } catch (IOException e) {
          e.printStackTrace();
        }
        
        Node root = tree.getRoot();
        tree.calculateSubtreeSizeRecursive(root);
        tree.calculateLeafDepthRecursive(root, 0);
        tree.setTreeParentsRecursive(root);
        

        Color color[] = {Color.BLUE, Color.yellow, Color.red, Color.green, Color.CYAN, Color.GRAY, Color.ORANGE, Color.MAGENTA, Color.PINK};

//==================================SLICE N DICE======================================================================================
        Rectangle availableSpace = new Rectangle(new Point(0, 0), 500, 500);
        TreeMap.treeMapBoundsSliceNDice(tree, availableSpace);
        System.out.println(TreeMap.getCount());
        Font font = new Font("arial", Font.BOLD, 16);
        g.setFont(font);
        DecimalFormat df = new DecimalFormat("###.##");
        g2.drawString("L'Aspect Ratio medio pr lo Slice And Dice è: "+df.format(TreeMap.mediumAspectRatio(tree)), 100, 600);

        Rectangle2D border = new Rectangle2D.Double(100, 50, 500, 500);
        g2.draw(border);
        for(int i=0;i<tree.getNodes().size();i++){
             Rectangle2D rect = new Rectangle2D.Double(tree.getNodes().get(i).getBound().getVertex().getX()+100,
                                                        tree.getNodes().get(i).getBound().getVertex().getY()+50,
                                                         tree.getNodes().get(i).getBound().getWidth(),
                                                          tree.getNodes().get(i).getBound().getHeight());
             g2.setColor(color[i%color.length]);
             g2.fill(rect);
             g2.setColor(Color.BLACK);
             g2.draw(rect);
        }
        /*for(int i=0;i<tree.getNodes().size();i++){
             Rectangle2D rect = new Rectangle2D.Double(tree.getNodes().get(i).getBound().getVertex().getX()+100+((tree.getNodes().get(i).getDepth()*2)+20),
                                                        tree.getNodes().get(i).getBound().getVertex().getY()+100+((tree.getNodes().get(i).getDepth()*2)+20),
                                                         tree.getNodes().get(i).getBound().getWidth()-((tree.getNodes().get(i).getDepth()*2)),
                                                          tree.getNodes().get(i).getBound().getHeight()-((tree.getNodes().get(i).getDepth()*2)));
             g2.setColor(color[i%color.length]);
             g2.fill(rect);
             g2.setColor(Color.BLACK);
             g2.draw(rect);
        } */

//===================================================SPLIT======================================================================================
        Rectangle availableSpace2 = new Rectangle(new Point(0, 0), 500, 500);
        TreeMap.treeMapBoundsSplit(tree, availableSpace2);
        g2.drawString("L'Aspect Ratio medio per lo Split è: "+df.format(TreeMap.mediumAspectRatio(tree)), 800, 600);
        Rectangle2D border2 = new Rectangle2D.Double(800, 50, 500, 500);
        g2.draw(border2);
        for(int i=0;i<tree.getNodes().size();i++){
             Rectangle2D rect = new Rectangle2D.Double(tree.getNodes().get(i).getBound().getVertex().getX()+800,
                                                        tree.getNodes().get(i).getBound().getVertex().getY()+50,
                                                         tree.getNodes().get(i).getBound().getWidth(),
                                                          tree.getNodes().get(i).getBound().getHeight());
             g2.setColor(color[i%color.length]);
             g2.fill(rect);
             g2.setColor(Color.BLACK);
             g2.draw(rect);
        }
        g2.drawString("Il bound occupato dal TreeMap è: "+Constants.NODESNUMBER+" (n)", 100, 650);
        /*for(int i=0;i<tree.getNodes().size();i++){
             Rectangle2D rect = new Rectangle2D.Double(tree.getNodes().get(i).getBound().getVertex().getX()+800+((tree.getNodes().get(i).getDepth()*4)),
                                                        tree.getNodes().get(i).getBound().getVertex().getY()+100+((tree.getNodes().get(i).getDepth()*4)),
                                                         tree.getNodes().get(i).getBound().getWidth()-((tree.getNodes().get(i).getDepth()*8)),
                                                          tree.getNodes().get(i).getBound().getHeight()-((tree.getNodes().get(i).getDepth()*8)));
             g2.setColor(color[i%color.length]);
             g2.fill(rect);
             g2.setColor(Color.BLACK);
             g2.draw(rect);
        }*/

        LayerTreeDraw3D.layoutTree(root,0,0);
        //System.out.println(LayerTreeDraw3D.getCount());
        
        for(int i=0;i<tree.getNodes().size();i++){
            //System.out.println("ID: "+tree.getNodes().get(i).getId()+" :"+tree.getNodes().get(i).getBound().toString());
             Rectangle2D rect = new Rectangle2D.Double(tree.getNodes().get(i).getBound().getVertex().getX()+100,
                                                        tree.getNodes().get(i).getBound().getVertex().getY()+700,
                                                         tree.getNodes().get(i).getBound().getWidth(),
                                                          tree.getNodes().get(i).getBound().getHeight());
             g2.setColor(color[i%color.length]);
             g2.fill(rect);
             g2.setColor(Color.BLACK);
             g2.draw(rect);
        }
        //LayerTreeDraw3D.layout3D(root, 0, 0);
        //System.out.println(LayerTreeDraw3D.getCount());
        g2.drawString("Il bound occupato dal LayerTree è: ["+(tree.getRoot().getBound().getWidth()*tree.getRoot().getBound().getHeight())/100+"]", 600, 650);


//====================================3D COORDINATES CREATION=============================================
  int width = 0;
  int height = 0;
    if(Constants.NODESNUMBER<100){
      width = 70;
      height = 70;
    }
    else if(Constants.NODESNUMBER>=100 && Constants.NODESNUMBER<=500){
      width = 100;
      height = 100;
    }
    else{
      width = 200;
      height = 200;
    }
    Rectangle availableSpaceC = new Rectangle(new Point(0, 0), width, height);
    //TreeMap.treeMapBoundsSliceNDice(tree, availableSpaceC);
    TreeMap.treeMapBoundsSplit(tree, availableSpaceC);
      
    //Point3D[] coordinates = TreeMap.calculated3DCordinates(tree);
     
     try {
          String output = "outputTree.txt";
          //LayerTreeDraw3D.layoutTree(root, 0,0);
          TreeMap.calculated3DCordinates(tree);
          fileGenerator(tree, tree.getRoot(), output);
            
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
      }
}

public class Test {
  public static void main(String[] args) {;
    try {
            RandomTreeGenerator.treeGenerator(Constants.NODESNUMBER,"randomTree.txt");
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del file: " + e.getMessage());
        }
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setBounds(30, 30, 450, 450);
    window.getContentPane().add(new MyCanvas());
    window.setVisible(true);
  }
}