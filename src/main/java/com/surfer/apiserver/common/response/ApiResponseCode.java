package com.surfer.apiserver.common.response;

public enum ApiResponseCode {
    SUCCESS("OK", "SUCCESS"),
    CREATED("OK", "CREATED"),
    ACCEPTED("OK", "ACCEPTED"),
    UNKNWON("UNKNWON", "occured unknown error"),
    INVALID_API_ACCESS_TOKEN("INVALID_API_ACCESS_TOKEN", "Invalid access_token"),
    INVALID_API_REFRESH_TOKEN("INVALID_API_REFRESH_TOKEN", "Invalid refresh_token"),
    ACCESS_TOKEN_EXPIRED("ACCESS_TOKEN_EXPIRED", "access token expired"),
    INVALID_CLIENT_ID_OR_CLIENT_SECRET("INVALID_CLIENT_ID_OR_CLIENT_SECRET", "invalid client-id or client-secret"),
    INVALID_PARAMETER_ERR("INVALID_PARAMETER_ERR", "invalid parameter error"),
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", "not authorized user"),
    //auth
    UNIQUE_PARAMETER_VIOLATION_EMAIL("UNIQUE_PARAMETER_VIOLATION_EMAIL", "unique parameter email is violation"),
    UNIQUE_PARAMETER_VIOLATION_NICKNAME("UNIQUE_PARAMETER_VIOLATION_NICKNAME", "unique parameter nickname is violation"),
    REGISTER_SUCCESS("REGISTER_SUCCESS", "register success"),
    UNIQUE_CONSTRAINT_VIOLATED("UNIQUE_CONSTRAINT_VIOLATED", "unique value is duplicated"),
    //COMMON
    FAILED_SIGN_UP_USER("ERR_SIGN_UP", "failed to add new user"),
    FAILED_SIGN_IN_USER("ERR_SIGN_IN", "failed to login because userid not exist"),
    INVALID_USER_ID("INVALID_USER_ID", "failed to load user because email is not exist"),
    //song
    INVALID_SONG_ID("INVALID_SONG_ID","failed to load song because seq is not exist"),
    INVALID_ALBUM_ID("INVALID_ALBUM_ID", "failed to load album because seq is not exist"),
    INVALID_ALBUM_STATE("INVALID_ALBUM_STATE","invalid album state"),
    INVALID_REPLY_ID("INVALID_REPLY_ID","failed to load reply because seq is not exist"),
    FAILED_UPDATE_REPLY("ERR_UPDATE_REPLY","failed to update reply because user is not authorized"),
    //reply_like
    FAILED_INSERT_LIKE("ERR_INSERT_LIKE","failed to insert like"),
    FAILED_DELETE_LIKE("ERR_DELETE_LIKE","failed to delete like"),
    //playlist
    FAILED_LOAD_PLAYLIST("ERR_LOAD_PLAYLIST", "failed to load playlist because playlist does not exist"), // 존재하는 플레이리스트가 없음
    INVALID_PLAYLIST_ID("FAILED_ACCESS_PLAYLIST", "failed to access playlist because playlist does not exist"), // 해당하는 플레이리스트가 없음
    FAILED_LOAD_PLAYLIST_LIKE("FAILED_LOAD_PLAYLIST_LIKE", "failed to load playlistLike because playlistLike does not exist"), // 존재하는 좋아요가 없음
    INVALID_PLAYLIST_LIKE_ID("INVALID_ACCESS_PLAYLIST_LIKE", "failed to access playlistLike because playlistLike does not exist"), // 해당하는 좋아요가 없음
    INVALID_TAG_ID("INVALID_TAG_ID","failed to access tag because tag does not exist"), // 해당하는 태그가 없음
    ;


    private String code;
    private String message;


    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    ApiResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
