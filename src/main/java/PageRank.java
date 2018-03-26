import java.math.RoundingMode;
import java.text.DecimalFormat;

public class PageRank {
    private static final double DIFFERENCE = 1.0e-4;
    private static final double ATTENUATION_COEF = 0.85;
    private int matrix [] [];
    private int sumLinks [];
    private int length;
    DecimalFormat df = new DecimalFormat("#.###");

    public double[] getPageRank(int [][] matrix) {
        this.length = matrix.length;
        this.matrix = matrix;
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
                    if (this.matrix[i][j] > 0 && this.sumLinks[i]!=0) {
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
                this.sumLinks[i]+= this.matrix[i][j];
            }
        }
    }
}
