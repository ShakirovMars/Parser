import java.text.DecimalFormat;
import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        String rootLink = "https://game2day.org";
        int limit = 100;
        boolean isReadMatrix = false;

        int[][] matrix = new int[0][0];
        ArrayList<String> rootLinks;
        double pageRankMatrix [] = new double[limit];
        double pageRankMatrixCoord [] = new double[limit];

        // for coordinate method
        ArrayList<Integer> values = new ArrayList<Integer>();
        ArrayList<Integer> iIndex = new ArrayList<Integer>();
        ArrayList<Integer> jIndex = new ArrayList<Integer>();

        int index = rootLink.lastIndexOf("//");
        String fileName = rootLink.substring(index+1)+".txt";
        fileName = fileName.substring(fileName.indexOf('/')+1);
        fileName=fileName.replace('/', '.');

        DecimalFormat df = new DecimalFormat("#.###");
        PageRank pageRank = new PageRank();
        PageRankCoord pageRankCoord = new PageRankCoord();

        if(isReadMatrix){

            ReadMatrixCoord readMatrixCoord  = new ReadMatrixCoord();
            ReadMatrix readMatrix  = new ReadMatrix();

            if (readMatrixCoord.readMatrix(fileName)) {
                   values = readMatrixCoord.getValues();
                   iIndex = readMatrixCoord.getiIndex();
                   jIndex = readMatrixCoord.getjIndex();
            }

            if (readMatrix.readMatrix(fileName)) {
                matrix = readMatrix.getMatrix();
                rootLinks = readMatrix.getRootLinks();
            }

        } else {
            Parser parser = new Parser();

            matrix=parser.getMatrix(rootLink, limit);
            rootLinks = parser.getRootLinks();
            values = parser.getValues();
            iIndex = parser.getiIndex();
            jIndex = parser.getjIndex();


            WriteMatrixCoord writeMatrixCoord = new WriteMatrixCoord();
            writeMatrixCoord.writeMatrixCoord(values,iIndex,jIndex,rootLinks,fileName);
            WriteMatrix writeMatrix = new WriteMatrix();
            writeMatrix.writeMatrix(matrix,rootLinks,fileName);

        }

        pageRankMatrix = pageRank.getPageRank(matrix);
        pageRankMatrixCoord = pageRankCoord.getPageRankCoord(values, iIndex, jIndex, limit);

//        for (int k = 0; k < pageRankMatrix.length; k++) {
//            System.out.print(df.format(pageRankMatrix[k])+" ");
//        }
//        System.out.println();
//        for (int k = 0; k < pageRankMatrixCoord.length; k++) {
//            System.out.print(df.format(pageRankMatrixCoord[k])+" ");
//        }
    }
}
