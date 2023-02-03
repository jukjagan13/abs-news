package com.abs.news.utilities;

import java.text.ParseException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class CommonUtilities {
    @Value("${app.client-salt:}")
    private String clientSalt;

    public static Date convertStringToDate(String dateStr) {
        Date date = null;
        try {
            date = CommonConstants.DATE_FORMATTER.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String convertStringToSqlDate(String dateStr) {
        String date = null;
        try {
            Date tempDate = CommonConstants.DATE_FORMATTER.parse(dateStr);
            date = CommonConstants.SQL_DATE_FORMATTER.format(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String hashStringSHA26(String str) {
        String finStr = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            BigInteger number = new BigInteger(1, hash);
            StringBuilder stringBuilder = new StringBuilder(number.toString(16));
            if(stringBuilder.length() < 32) {
                stringBuilder.insert(0 , '0');
            }
            finStr = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finStr;
    }

    public String hashStringBCrypt(String str) {
        String finStr = null;
        try {
            finStr = BCrypt.hashpw(str, clientSalt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finStr;
    }

    public boolean matchKeys(String privateKey, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            Base64.Decoder decoder = Base64.getDecoder();

            PublicKey decodedPublicKey = keyFactory.generatePublic(new X509EncodedKeySpec(decoder.decode(publicKey)));
            PrivateKey decodedPrivateKey =
                    keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decoder.decode(privateKey)));

            return matches(decodedPrivateKey, decodedPublicKey);
        }catch (Exception e){
//            log.error("Error validating the keys");
            return false;
        }
    }

    private static boolean matches(PrivateKey privateKey, PublicKey publicKey) {
        String file = "qwerty";
        byte[] fileBytes = file.getBytes();
        byte[] digitalSignature = signData(fileBytes, privateKey);
        boolean verified;
        verified = verifySig(fileBytes, publicKey, digitalSignature);
        return verified;
    }

    public static byte[] signData(byte[] data, PrivateKey key) {
        try {
            Signature signer = Signature.getInstance("SHA256withRSA");
            signer.initSign(key);
            signer.update(data);
            return (signer.sign());
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public static boolean verifySig(byte[] data, PublicKey key, byte[] sig) {
        try {
            Signature signer = Signature.getInstance("SHA256withRSA");
            signer.initVerify(key);
            signer.update(data);
            return (signer.verify(sig));
        }  catch (Exception e) {
            return false;
        }
    }
}
