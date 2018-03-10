import java.io.*;
import java.util.ArrayList;

public class ReadMatrix {
    private int [][] matrix;
    private ArrayList<String> rootLinks = new ArrayList<String>();
    private ArrayList<String> matrixLines = new ArrayList<String>();

    public Boolean readMatrix() {
        boolean isFile;
        try {
            isFile = true;
            int index;
            boolean endMatrix = false;

            File file = new File("result.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();

            while (line != null) {
                if(line.equals("")){
                   endMatrix = true;
                    line = reader.readLine();
                }
                if(!endMatrix) {
                    matrixLines.add(line);
                } else {
                    index = line.indexOf(')');
                    rootLinks.add(line.substring(index+1));
                }

                line = reader.readLine();
            }
            this.createMatrix();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            isFile = false;
        } catch (IOException e) {
            e.printStackTrace();
            isFile = false;
        }
        return isFile;
    }

    private void createMatrix() {
        int size = matrixLines.size();
        this.matrix = new int  [size][size];
        for(int i=0;i<size;i++) {
            for(int j=0;j<size;j++) {
                if(j%2 == 0) {
                    matrix[i][j] = Integer.parseInt(matrixLines.get(i).substring(j,j+1));
                } else {
                    matrix[i][j] = Integer.parseInt(matrixLines.get(i).substring(j+1,j+2));
                }
            }
        }

    }
}


