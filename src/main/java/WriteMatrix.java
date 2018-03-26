import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteMatrix {
    public void writeMatrix(int [][] matrix, ArrayList<String> rootLinks, String fileName) {
        String info="";

        for(int i = 0; i < matrix.length; i++) {
            for(int j=0; j<matrix.length; j++){
                info = info.concat(matrix[i][j]+"");
            }
            info = info.concat("\n");
        }

        for(int i =0; i<rootLinks.size(); i++){
            info =  info.concat("\n");
            info = info.concat(i+") "+rootLinks.get(i));
        }

        File file = new File("results/simple/"+fileName);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(info);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
