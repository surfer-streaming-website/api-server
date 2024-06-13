package com.surfer.apiserver.common.constant;


public class Constant {
    public static final String[] permitGetMethodUrl = {
            //swagger
            "/v3/api-docs", "/v3/api-docs/swagger-config"
    };
    public static final String[] permitGetMethodUrlAntPattern = {
            //auth
            "/api/v1/auth/hi/*/bye"
    };
    public static final String[] permitPostMethodUrl = {
            //auth
            "/api/v1/auth/sign-up", "/api/v1/auth/sign-in"
    };
    public static final String[] permitPostMethodUrlAntPattern = {
    };
    public static final String[] permitDeleteMethodUrl = {};
    public static final String[] permitDeleteMethodUrlAntPattern = {};
    public static final String[] permitPutMethodUrl = {};
    public static final String[] permitPutMethodUrlAntPattern = {};
    public static final String[] permitHeadMethodUrl = {};
    public static final String[] permitHeadMethodUrlAntPattern = {};
    public static final String[] permitPatchMethodUrl = {};
    public static final String[] permitPatchMethodUrlAntPattern = {};
    public static final String[] permitOptionsMethodUrl = {};
    public static final String[] permitOptionsMethodUrlAntPattern = {};

    public static final String[] permitGetMethodUrlPattern = convertAntToRegex(permitGetMethodUrlAntPattern);
    public static final String[] permitDeleteMethodUrlPattern = convertAntToRegex(permitDeleteMethodUrlAntPattern);
    public static final String[] permitPutMethodUrlPattern = convertAntToRegex(permitPutMethodUrlAntPattern);
    public static final String[] permitHeadMethodUrlPattern = convertAntToRegex(permitHeadMethodUrlAntPattern);
    public static final String[] permitPatchMethodUrlPattern = convertAntToRegex(permitPatchMethodUrlAntPattern);
    public static final String[] permitOptionsMethodUrlPattern = convertAntToRegex(permitOptionsMethodUrlAntPattern);
    public static final String[] permitPostMethodUrlPattern = convertAntToRegex(permitPostMethodUrlAntPattern);


    private static String[] convertAntToRegex(String[] antUrl){
        int antUrlSize = antUrl.length;
        String[] result = new String[antUrlSize];
        for(int i = 0; i < antUrlSize; i++){
            String regexStr = antUrl[i].replaceAll("/\\*", "/[^/]");
            regexStr = "^" + regexStr + "$";
            result[i] = regexStr;
        }
        return result;
    }
}