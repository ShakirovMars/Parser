import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteMatrixCoord {
    public void writeMatrixCoord(ArrayList<Integer> values, ArrayList<Integer> iIndex, ArrayList<Integer> jIndex, ArrayList<String> rootLinks, String fileName) {
        String info="";

        for(int i = 0; i < values.size(); i++) {
            info = info.concat(values.get(i)+"-");
        }

        info =  info.concat("\n");

        for(int i = 0; i < iIndex.size(); i++) {
            info = info.concat(iIndex.get(i)+"-");
        }

        info =  info.concat("\n");

        for(int i = 0; i < jIndex.size(); i++) {
            info = info.concat(jIndex.get(i)+"-");
        }

        info =  info.concat("\n");

        for(int i =0; i<rootLinks.size(); i++){
            info =  info.concat("\n");
            info = info.concat(i+") "+rootLinks.get(i));
        }

        File file = new File("results/coord/"+fileName);

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

