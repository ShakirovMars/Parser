import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Parser {

    private ArrayList<String> rootLinks = new ArrayList<String>();
    private int matrix [][];

    public ArrayList<String> getRootLinks() {
        return rootLinks;
    }

    public int[][] getMatrix(String rootLink, int limit ){
        this.matrix = new int [limit][limit];
        this.rootLinks =  parseLink(rootLink,limit);
        createMatrix();

        return this.matrix;
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

    private boolean checkDuplication(ArrayList<String> links,String link){
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
                        break;
                    }
                }
            }
        }
    }
}
