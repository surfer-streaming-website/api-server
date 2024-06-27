package com.surfer.apiserver.common.constant;


public class Constant {
    public static final String[] permitGetMethodUrl = {
            //swagger
            "/v3/api-docs", "/v3/api-docs/swagger-config",
            // common
            "/favicon.ico",
            // song
            "/api/song/rank", "/api/song/all",
            //
            "/favicon.ico",
            //album
//            "/api/album/status"
            "/api/album/latest"
    };
    public static final String[] permitGetMethodUrlAntPattern = {
            //auth
            "/api/v1/auth/hi/*/bye",
            //song
            "/api/song/detail/*","/api/search/*","/api/song/download/*","/api/song/*", "/api/song/genre/*",
            //album
            "/api/album/detail/*",
//            ,"/album/*","/api/album/status/*","/api/album/*","/api/album/image/*"
    };
    public static final String[] permitPostMethodUrl = {
            //auth
            "/api/v1/auth/sign-up", "/api/v1/auth/sign-in"
            //test
//            ,"/file/upload","/song/*"
            //file upload
    };
    public static final String[] permitPostMethodUrlAntPattern = {
    };
    public static final String[] permitDeleteMethodUrl = {
            "/api/album/delete/*"
    };
    public static final String[] permitDeleteMethodUrlAntPattern = {
            "/api/album/delete/*"
    };
    public static final String[] permitPutMethodUrl = {};
    public static final String[] permitPutMethodUrlAntPattern = {};
    public static final String[] permitHeadMethodUrl = {};
    public static final String[] permitHeadMethodUrlAntPattern = {};
    public static final String[] permitPatchMethodUrl = {};
    public static final String[] permitPatchMethodUrlAntPattern = {};
    public static final String[] permitOptionsMethodUrl = {};
    public static final String[] permitOptionsMethodUrlAntPattern = {};

}