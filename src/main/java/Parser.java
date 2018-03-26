import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Parser {

    private ArrayList<String> rootLinks = new ArrayList<String>();
    private int matrix [][];
    private int matrixRunnable [][];

    // for coordinate method
    private ArrayList<Integer> values = new ArrayList<Integer>();
    private ArrayList<Integer> iIndex = new ArrayList<Integer>();
    private ArrayList<Integer> jIndex = new ArrayList<Integer>();

    // for coordinate method
    private ArrayList<Integer> valuesRunnable = new ArrayList<Integer>();
    private ArrayList<Integer> iIndexRunnable = new ArrayList<Integer>();
    private ArrayList<Integer> jIndexRunnable = new ArrayList<Integer>();

    public ArrayList<String> getRootLinks() {
        return rootLinks;
    }

    public int[][] getMatrix(String rootLink, int limit ){
        this.matrix = new int [limit][limit];
        this.matrixRunnable = new int [limit] [limit];

        createRootLinks(rootLink,limit);
//        createMatrix();
        createMatrixRunnable();

//        for(int i =0; i<matrix.length;i++){
//            for(int j =0; j<matrix.length;j++){
//                System.out.print(matrix[i][j]);
//            }
//            System.out.println();
//        }

        for(int i =0; i<matrixRunnable.length;i++){
            for(int j =0; j<matrixRunnable.length;j++){
                System.out.print(matrixRunnable[i][j]);
            }
            System.out.println();
        }

        return this.matrix;
    }

    private void createRootLinks(String rootLink, int limit) {
        ArrayList<String> links;
        int i = 0;
        boolean isUnique = true;
        this.rootLinks =  parseLink(rootLink,limit);
        while (rootLinks.size()<limit){
            links = parseLink(this.rootLinks.get(i),0);
            i++;
            for(int j=0; j<links.size() && this.rootLinks.size()<limit;j++){
               for(int k=0;k<this.rootLinks.size()&& this.rootLinks.size()<limit;k++){
                   if(links.get(j).equals(this.rootLinks.get(k))){
                      isUnique = false;
                   }
               }
               if(isUnique) {
                   this.rootLinks.add(links.get(j));
               }
               isUnique = true;
            }
        }
    }


    public ArrayList<String> parseLink(String rootLink, int limit) {
        ArrayList<String> links = new ArrayList<String>();

        try {
            Document doc = Jsoup.connect(rootLink).get();
            Element body = doc.body();

            Elements urls = body.getElementsByTag("a");

            for(Element url : urls) {
                String link = url.attr("href");

                if(limit!=0){
                    if (limit == links.size()) {
                        break;
                    } else {
                        if(links.size()<limit && link.length()>4 && link.substring(0,4).equals("http")){
                            if(links.size() == 0){
                                links.add(link);
                            } else {
                                if(!checkDuplication(links,link)){
                                    links.add(link);
                                }
                            }
                        }
                    }
                } else {
                    if(link.length()>4 && link.substring(0,4).equals("http")){
                        if(links.size() == 0){
                            links.add(link);
                        } else {
                            if(!checkDuplication(links, link)){
                                links.add(link);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {}
        return links;
    }

    public boolean checkDuplication(ArrayList<String> links,String link){
        for(int i=0; i<links.size();i++){
            if(link.equals(links.get(i))){
               return true;
            }
        }
        return false;
    }

    private void createMatrix() {
        ArrayList<String> links;
        for (int i=0; i<rootLinks.size(); i++){
            links=this.parseLink(rootLinks.get(i),0);
            for (int j=0; j<rootLinks.size(); j++){
                matrix[i][j] = 0;
                for(int k=0; k<links.size();k++){
                    if(rootLinks.get(j).equals(links.get(k))){
                        matrix[i][j] = 1;
                        this.values.add(1);
                        this.iIndex.add(i);
                        this.jIndex.add(j);
                        break;
                    }
                }
            }
        }
    }

    private void createMatrixRunnable() {
        ArrayList<String> links;
        ExecutorService executor = Executors.newCachedThreadPool();

        for (int i=0; i<rootLinks.size(); i++){
            Future<ArrayList<Integer>> future = executor.submit(new IterativeRun(rootLinks, i));

            try {
                for(int k=0; k<rootLinks.size();k++){
                    matrixRunnable[i][k] = future.get().get(k);
                    this.valuesRunnable.add(1);
                    this.iIndexRunnable.add(i);
                    this.jIndexRunnable.add(k);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
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
