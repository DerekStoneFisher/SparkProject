//package Backend;
//
//import spark.Request;
//
//import java.util.*;
//
///**
// * Created by Admin on 3/5/2017.
// */
//public class RequestUtils {
//    public static LinkedHashSet<String> validSearchParams = new LinkedHashSet<>(Arrays.asList("title", "author", "subject", "resultType", "user"));
//    public static LinkedHashSet<String> validUploadParams = new LinkedHashSet<>(Arrays.asList("title", "author", "subject", "resultType", "user", "pdfLink", "id"));
//    public static LinkedHashSet<String> validUserParams  =  new LinkedHashSet<>(Arrays.asList("user", "password", "school"));
//
//    public static Map<String, String> getParamMapFromRequest(Request request){
//        Map<String, String> paramMap = new HashMap<>();
//        for (String paramKey : request.queryParams()){
//            String paramValue = request.queryParams(paramKey);
//            paramMap.put(paramKey, paramValue);
//        }
//        return paramMap;
//    }
//
//    public static Map<String, String> getValidUploadParams(Map<String, String> requestParams){
//        return getValidParams(requestParams, validUploadParams);
//    }
//
//    public static Map<String, String> getValidSearchParams(Map<String, String> requestParams){
//        return getValidParams(requestParams, validSearchParams);
//    }
//
//    public static Map<String, String> getValidUserParams(Map<String, String> requestParams){
//        return getValidParams(requestParams, validUserParams);
//    }
//
//
//
//    private static Map<String, String> getValidParams(Map<String, String> requestParams, Set<String> validParams){
//        Map<String, String> validParamMap = new HashMap<>();
//        for (String paramKey : requestParams.keySet()) {
//            if (validParams.contains(paramKey)) {
//                String paramValue = requestParams.get(paramKey);
//                if (paramValue != null) {
//                    validParamMap.put(paramKey, paramValue);
//                }
//            }
//        }
//        return validParamMap;
//    }
//
//    private static String generateSolrIdGivenValidParams(Map<String, String> params, Set<String> validParams){
//        String id = ""; // id will be title=book1,author=bob,subject=chemistry,resultType=free
//        for (String paramKey : validParams){
//                String paramValue = returnNullStringIfBlank(params.get(paramKey));
//                id += paramKey + "=" + paramValue + ",";
//            }
//        return id.substring(0, id.length()-1); // get rid of the comma at the end
//    }
//
//    public static String generateSolrIdFromUploadParams(Map<String, String> params){
//        return generateSolrIdGivenValidParams(params, validSearchParams); // validSearchParams is NOT a typo here. I don't want pdfLink or id to be part of the id...
//    }
//
//    public static String generateSolrIdFromUserParams(Map<String, String> params){
//        return generateSolrIdGivenValidParams(params, validUserParams);
//    }
//
//    private static String returnNullStringIfBlank(Map<String, String> map, String param){
//        if (map.get(param) == null)
//            return param + "=null";
//        else
//            return param + "=" + map.get(param);
//    }
//
//    private static String returnNullStringIfBlank(String paramValue){
//       if (paramValue == null) return "null";
//        else return paramValue;
//    }
//}
