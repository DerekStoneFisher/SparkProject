//package Backend;
//
//import org.apache.http.impl.client.SystemDefaultHttpClient;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.client.solrj.SolrServerException;
//import org.apache.solr.client.solrj.impl.HttpSolrClient;
//import org.apache.solr.client.solrj.response.QueryResponse;
//import org.apache.solr.client.solrj.response.UpdateResponse;
//import org.apache.solr.common.SolrDocument;
//import org.apache.solr.common.SolrDocumentList;
//import org.apache.solr.common.SolrInputDocument;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * Created by Admin on 3/1/2017.
// */
//public class SolrModule {
//    String solrCoreUrl;
//    HttpSolrClient solrClient;
//
//    public SolrModule(String solrCoreUrl) {
//        this.solrCoreUrl = solrCoreUrl;
//        this.solrClient = new HttpSolrClient(solrCoreUrl, new SystemDefaultHttpClient());
//    }
//
//    public Boolean uploadTextBook(Map<String, String> uploadParams) throws IOException, SolrServerException {
//        if (entryExists(uploadParams)) return false;
//        SolrInputDocument document = new SolrInputDocument();
//        Map<String, String> validUploadParams = RequestUtils.getValidUploadParams(uploadParams);
//        for (String paramKey : validUploadParams.keySet()) {
//            String paramValue = validUploadParams.get(paramKey);
//            document.addField(paramKey, paramValue);
//        }
//        document.addField("id", RequestUtils.generateSolrIdFromUploadParams(uploadParams));
//        UpdateResponse response = solrClient.add(document);
//        solrClient.commit();
//        System.out.println(response.toString());
//        return true;
//    }
//
//    public String executeTextbookSearch(Map<String, String> searchParams) throws IOException, SolrServerException {
//        SolrDocumentList solrDocuments = getSolrDocumentsFromSearch(searchParams);
//        JSONArray returnResults = new JSONArray();
//        for (Map singleDoc : solrDocuments) {
//            JSONObject jsonObject = new JSONObject(singleDoc);
//            returnResults.put(jsonObject);
//        }
//        System.out.println(returnResults.toString());
//        return returnResults.toString();
//    }
//
//    private SolrDocumentList getSolrDocumentsFromSearch(Map<String, String> searchParams) throws IOException, SolrServerException {
//        SolrQuery solrQuery = new SolrQuery();
//        String solrQueryString = "";
//        Map<String, String> validSearchParams = RequestUtils.getValidSearchParams(searchParams);
//
//        for (String paramKey : validSearchParams.keySet()) {
//            String paramValue = validSearchParams.get(paramKey);
//            if (solrQueryString.length() > 0) {
//                solrQueryString += " AND ";
//            }
//            solrQueryString += paramKey + ":" + "\"" + paramValue + "\"";
//        }
//        System.out.println("executing search query: " + solrQueryString);
//
//        solrQuery.setQuery(solrQueryString);
//        QueryResponse response = this.solrClient.query(solrQuery);
//        SolrDocumentList solrDocuments = response.getResults();
//        return solrDocuments;
//    }
//
//    private boolean entryExists(Map<String, String> uploadParams) throws IOException, SolrServerException {
//        String id = RequestUtils.generateSolrIdFromUploadParams(uploadParams);
//        SolrQuery solrQuery = new SolrQuery();
//        String solrQueryString = "id:\"" + id + "\"";
//        solrQuery.setQuery(solrQueryString);
//        System.out.println("executing Solr Query: " + solrQueryString);
//        QueryResponse response = this.solrClient.query(solrQuery);
//        SolrDocumentList solrDocuments = response.getResults();
//        return (solrDocuments.size() != 0);
//    }
//
//    public boolean verifyUserCredentials(Map<String, String> userCredentialParams) throws IOException, SolrServerException {
//        String user = userCredentialParams.getOrDefault("user", "");
//        String password = userCredentialParams.getOrDefault("password", "");
//
//        String solrQueryString = String.format("user:\"%s\" AND password:\"%s\"", user, password);
//        SolrQuery solrQuery = new SolrQuery();
//        solrQuery.setQuery(solrQueryString);
//        System.out.println("executing Solr Query: " + solrQueryString);
//        QueryResponse response = this.solrClient.query(solrQuery);
//        SolrDocumentList solrDocuments = response.getResults();
//        return (solrDocuments.size() != 0);
//    }
//
//    public boolean createNewUser(Map<String, String> userUploadParams) throws IOException, SolrServerException {
//        if (verifyUserCredentials(userUploadParams)) return false;
//        SolrInputDocument document = new SolrInputDocument();
//        Map<String, String> validUserParams = RequestUtils.getValidUserParams(userUploadParams);
//        validUserParams.put("resultType", "user");
//        for (String paramKey : validUserParams.keySet()) {
//            String paramValue = validUserParams.get(paramKey);
//            document.addField(paramKey, paramValue);
//        }
//        document.addField("id", RequestUtils.generateSolrIdFromUserParams(validUserParams));
//        System.out.println("creating and uploading new user: " + document.toString());
//        UpdateResponse response = solrClient.add(document);
//        System.out.println("response = " + response.toString());
//        solrClient.commit();
//        return true;
//    }
//
//    public Boolean executeDelete(Map<String, String> deleteParams) throws IOException, SolrServerException {
//        if (verifyUserCredentials(deleteParams)){
//            SolrDocumentList solrDocuments = getSolrDocumentsFromSearch(deleteParams);
//            List<String> idList = new ArrayList<>();
//            for (SolrDocument solrDocument : solrDocuments){
//                idList.add((String) solrDocument.getFieldValue("id"));
//            }
//            System.out.println("deleting entries: " + idList.toString());
//            solrClient.deleteById(idList);
//            solrClient.commit();
//            return true;
//        } else {
//            System.out.println("user credentials incorrect - cannot delete entries");
//            return false;
//        }
//    }
//
//
//
//
//}