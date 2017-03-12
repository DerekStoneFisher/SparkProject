//package Backend;
//
//import org.apache.solr.client.solrj.SolrServerException;
//import spark.ModelAndView;
//import spark.Request;
//import spark.Response;
//import spark.Spark;
//import spark.template.freemarker.FreeMarkerEngine;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Admin on 3/1/2017.
// */
//public class ServiceMain {
//
//    public static void main(String args[]) throws IOException, SolrServerException {
//        try {
//            System.out.println("ServiceMain() start");
//            Spark.externalStaticFileLocation("static");
//            SolrModule solrModule = new SolrModule("http://ec2-52-40-24-42.us-west-2.compute.amazonaws.com:8983/solr/TEXTBOOK_DB");
//
//            Spark.get("/", (request, response) -> {
//                Map<String, Object> viewObjects = new HashMap<String, Object>();
//                viewObjects.put("title", "Welcome to Spark Project");
//                viewObjects.put("templateName", "home.ftl");
//                return new ModelAndView(viewObjects, "main.ftl");
//            }, new FreeMarkerEngine());
//
//            Spark.get("/TextBookApp/ExecuteSearch", (request, response) -> {
//                Map<String, String> paramMap = RequestUtils.getParamMapFromRequest(request);
//                String textBookResultsAsJson = solrModule.executeTextbookSearch(paramMap);
//                return textBookResultsAsJson;
//            });
//
//            Spark.get("/TextBookApp/ExecuteUpload", (request, response) -> {
//                Map<String, String> paramMap = RequestUtils.getParamMapFromRequest(request);
//                return solrModule.uploadTextBook(paramMap).toString();
//            });
//
//            Spark.get("/TextBookApp/ExecuteLogin", (request, response) -> executeLogin(request, response, solrModule));
//            Spark.post("/TextBookApp/ExecuteLogin", (request, response) -> executeLogin(request, response, solrModule));
//
//            Spark.get("/TextBookApp/CreateNewUser", (request, response) -> createNewUser(request, response, solrModule));
//            Spark.post("/TextBookApp/CreateNewUser", (request, response) -> createNewUser(request, response, solrModule));
//
//            // you can only delete entries uploaded by a user. The user must give his username and password in his delete request
//            Spark.get("/TextBookApp/ExecuteDelete", (request, response) -> executeDelete(request, response, solrModule));
//            Spark.post("/TextBookApp/ExecuteDelete", (request, response) -> executeDelete(request, response, solrModule));
//
//
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public static String executeLogin(Request request, Response response, SolrModule solrModule) throws IOException, SolrServerException {
//        Map<String, String> paramMap = RequestUtils.getParamMapFromRequest(request);
//        Boolean userCredentialsAreValid = solrModule.verifyUserCredentials(paramMap);
//        return userCredentialsAreValid.toString();
//    }
//
//    public static String createNewUser(Request request, Response response, SolrModule solrModule) throws IOException, SolrServerException {
//        Map<String, String> paramMap = RequestUtils.getParamMapFromRequest(request);
//        Boolean userSuccessfullyCreated = solrModule.createNewUser(paramMap);
//        return userSuccessfullyCreated.toString();
//    }
//
//    public static String executeDelete(Request request, Response response, SolrModule solrModule) throws IOException, SolrServerException {
//        Map<String, String> paramMap = RequestUtils.getParamMapFromRequest(request);
//        return solrModule.executeDelete(paramMap).toString();
//    }
//}
