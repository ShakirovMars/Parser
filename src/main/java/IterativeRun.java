import java.util.ArrayList;
import java.util.concurrent.Callable;

public class IterativeRun implements Callable<ArrayList<Integer>> {

    private ArrayList<String> rootLinks;
    private int i;

    public IterativeRun(ArrayList<String> rootLinks, int i) {
        this.rootLinks = rootLinks;
        this.i = i;
    }

    @Override
    public ArrayList<Integer> call() throws InterruptedException {
        Parser parser = new Parser();
        ArrayList<Integer> result = new ArrayList<Integer>();
        int current;

        ArrayList<String> links = parser.parseLink(rootLinks.get(i),0);

        for (int j=0; j<rootLinks.size(); j++){
            current = 0;
            for(int k=0; k<links.size();k++){
                if(rootLinks.get(j).equals(links.get(k))){
                    current = 1;
                    break;
                }
            }
            result.add(j,current);
        }
        return result;
    }
}
