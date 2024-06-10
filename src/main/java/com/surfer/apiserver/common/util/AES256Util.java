package com.surfer.apiserver.common.util;

import com.surfer.apiserver.common.exception.BusinessException;
import com.surfer.apiserver.common.response.ApiResponseCode;
import org.springframework.http.HttpStatus;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES256Util {
    public static final String ENCRYPT_ALG = "AES/CBC/PKCS5Padding";
    public static final String ENCRYPT_KEY = "01234567899254321111012345678901";
    public static final String ENCRYPT_IV = ENCRYPT_KEY.substring(0, 16);

    public static String encrypt(String text){
        try{
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
            SecretKeySpec keySpec = new SecretKeySpec(ENCRYPT_IV.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(ENCRYPT_IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e){
            throw new BusinessException(ApiResponseCode.FAILED_SIGN_IN_USER, HttpStatus.BAD_REQUEST);
        }
    }

    public static String decrypt(String cipherText) {
        try{
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALG);
            SecretKeySpec keySpec = new SecretKeySpec(ENCRYPT_IV.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(ENCRYPT_IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, "UTF-8");
        } catch (Exception e){
            throw new BusinessException(ApiResponseCode.FAILED_SIGN_IN_USER, HttpStatus.BAD_REQUEST);

        }
    }
}