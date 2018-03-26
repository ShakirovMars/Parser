import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class PageRankCoord {
    private static final double DIFFERENCE = 1.0e-4;
    private static final double ATTENUATION_COEF = 0.85;
    private  ArrayList<Integer> values;
    ArrayList<Integer> iIndex;
    ArrayList<Integer> jIndex;
    private int sumLinks [];
    private int length;
    DecimalFormat df = new DecimalFormat("#.###");

    public double[] getPageRankCoord(ArrayList<Integer> values, ArrayList<Integer> iIndex, ArrayList<Integer> jIndex, int length) {
        this.length = length;
        this.values = values;
        this.iIndex = iIndex;
        this.jIndex = jIndex;
        double[] pageRankOld = new double[this.length];
        double[] pageRank = new double[this.length];
        boolean stop;
        this.df.setRoundingMode(RoundingMode.HALF_UP);
        double sum;

        this.getSumLinks();

        for (int i = 0; i < this.length; i++) {
            pageRankOld[i] = 1;
        }

        do {
            for (int j = 0; j < this.length; j++) {
                sum = 0;
                pageRank[j] = 1 - this.ATTENUATION_COEF;
                for (int i = 0 ; i < this.length; i++) {
                    if(checkIndex(i,j) && this.sumLinks[i]!=0){
                        sum+= pageRankOld[i] / this.sumLinks[i];
                    } else {
                        sum += 0;
                    }
                }
                pageRank[j]+= Double.valueOf(df.format(this.ATTENUATION_COEF * sum).replace(',','.'));
            }

            stop = this.checkStop(pageRankOld, pageRank);
            pageRankOld = pageRank;
        } while (!stop);

        return pageRank;
    }

    private boolean checkIndex(int indexI, int indexJ) {
        boolean result = false;
        for (int i=0; i<iIndex.size();i++){
            if(indexI == iIndex.get(i)){
                if(jIndex.get(i) == indexJ){
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    private  boolean checkStop(double[] pageRankOld, double[] pageRank) {
        boolean result = true;
        double range;
        for (int i =0; i < this.length; i++) {
            range = pageRankOld[i] - pageRank[i];
            if(range < 0) {
                range = -range;
            }
            if((range) > this.DIFFERENCE){
                result = false;
                break;
            }
        }

        return result;
    }

    private void getSumLinks() {
        this.sumLinks = new int[length];

        for(int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.length; j++) {
                if(checkIndex(i, j)){
                    this.sumLinks[i]++;
                }
            }
        }
    }
}
