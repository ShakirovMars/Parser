import java.io.*;
import java.util.ArrayList;

public class ReadMatrixCoord {
    private ArrayList<Integer> values = new ArrayList<Integer>();
    private ArrayList<Integer> iIndex = new ArrayList<Integer>();
    private ArrayList<Integer> jIndex = new ArrayList<Integer>();
    private ArrayList<String> rootLinks = new ArrayList<String>();
    private ArrayList<String> matrixLines = new ArrayList<String>();

    public Boolean readMatrix(String fileName) {
        boolean isFile;
        try {
            isFile = true;
            int index;
            boolean endMatrix = false;

            File file = new File("results/coord/"+fileName);
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
            isFile = false;
            return isFile;
        } catch (IOException e) {
            isFile = false;
            return isFile;
        }
        return isFile;
    }

    public ArrayList<String> getRootLinks() {
        return rootLinks;
    }

    private void createMatrix() {
        int size = matrixLines.size();
        String currentLine;
        int index;

        for(int i=0;i<size;i++) {
            if(i == 0) {
                currentLine = matrixLines.get(i);
                while ( currentLine.length() != 0){
                    index = currentLine.indexOf("-");
                    this.values.add(Integer.parseInt(currentLine.substring(0,index)));
                    currentLine = currentLine.substring(index+1,currentLine.length());
                }
            } else {
                if(i == 1) {
                    currentLine = matrixLines.get(i);
                    while (currentLine.length() != 0){
                        index = currentLine.indexOf('-');
                        this.iIndex.add(Integer.parseInt(currentLine.substring(0,index)));
                        currentLine = currentLine.substring(index+1,currentLine.length());
                    }
                } else {
                    if(i == 2) {
                        currentLine = matrixLines.get(i);
                        while ( currentLine.length() != 0){
                            index = currentLine.indexOf('-');
                            this.jIndex.add(Integer.parseInt(currentLine.substring(0,index)));
                            currentLine = currentLine.substring(index+1,currentLine.length());
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Integer> getValues() {
        return values;
    }

    public ArrayList<Integer> getiIndex() {
        return iIndex;
    }

    public ArrayList<Integer> getjIndex() {
        return jIndex;
    }
}

