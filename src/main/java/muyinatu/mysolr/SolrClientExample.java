package muyinatu.mysolr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SolrClientExample {

    private static final String SOLR_URL = "http://localhost:8983/solr/books";

    public static void main(String[] args) {
        try (SolrClient solr = new HttpSolrClient(SOLR_URL)) {
            SolrQuery query = new SolrQuery();

            query.setHighlight(true);
            // query.setHighlightRequireFieldMatch(true);
            query.setHighlightSimplePre("<em>");
            query.setHighlightSimplePost("</em>");
            query.set("q", "percy"); //single phrase
//            query.set("q", "Percy Jackson"); // multiple phrases
//            query.set("q", "+fabric +module"); // match both phrases
            query.set("hl.fl", "series_t");
//            query.set("start", 0);
//            query.set("facet", "true");
         //   query.set("facet.field", "controlEntry");
//            query.set("mlt", "true");
//            query.set("mlt.fl", "description");
            QueryResponse queryResponse = solr.query(query);
            SolrDocumentList results = queryResponse.getResults();
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

            for (SolrDocument document : results){
                System.out.println(document.get("name"));
                String id = (String) document.get("id");
                Map<String, List<String>> highlightMap = highlighting.get(id);
                List<String> highlightValues = highlightMap.get("series_t");
                highlightValues.stream().forEach(System.out::println);
            }

        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

}
