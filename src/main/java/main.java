import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        int[][] matrix = parser.getMatrix("http://kpfu.ru", 100);
        ArrayList<String> rootLinks = parser.getRootLinks();
        for(int i=0; i<matrix.length; i++){
            for (int j=0; j<matrix.length; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        for(int i=0; i<rootLinks.size();i++){
            System.out.println(i+") "+rootLinks.get(i));
        }
    }
}
