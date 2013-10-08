import javax.swing.*;

/**
 * User: Sigurd
 * Date: 04.10.13
 * Time: 21:11
 */
public class LinearCheckers {

    public static void main(String[] args) {
        try {
            int numberOfPieces = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of checkers"));
            int searchType = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter search type (0 = BestFirst, 1 = DepthFirst, 2 = BreadthFirst)"));
            LinearCheckersAStar linearCheckersAStar = new LinearCheckersAStar(searchType, numberOfPieces);
            Node<LinearCheckersBoard> node = linearCheckersAStar.search();
            System.out.println(linearCheckersAStar.getResultString(node));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
