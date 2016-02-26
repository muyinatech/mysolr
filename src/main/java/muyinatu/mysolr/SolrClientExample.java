package muyinatu.mysolr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import java.io.IOException;

public class SolrClientExample {

    private static final String SOLR_URL = "http://localhost:8983/solr/goodsentries";

    public static void main(String[] args) {
        try (SolrClient solr = new HttpSolrClient(SOLR_URL)) {
            SolrQuery query = new SolrQuery();

            query.setHighlight(true);
            // query.setHighlightRequireFieldMatch(true);
            query.setHighlightSimplePre("<em>");
            query.setHighlightSimplePost("</em>");
            query.set("q", "compound"); //single phrase
//            query.set("q", "gram module"); // multiple phrases
//            query.set("q", "+fabric +module"); // match both phrases
            query.set("hl.fl", "description");
            query.set("start", 0);
            query.set("facet", "true");
            query.set("facet.field", "controlEntry");
            query.set("mlt", "true");
            query.set("mlt.fl", "description");
            QueryResponse queryResponse = solr.query(query);
            SolrDocumentList results = queryResponse.getResults();
            for (SolrDocument document : results){
                System.out.println(document.get("description"));
            }

        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

}
