package com.lt.dom.serviceOtc;


import com.lt.dom.JwtUtils;
import com.lt.dom.crypto.AES;
import com.lt.dom.crypto.AES_ENCRYPTION;
import com.lt.dom.oct.Voucher;
import com.lt.dom.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.util.Base64;
import java.util.Optional;

@Service
public class QrcodeServiceImpl {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private AES_ENCRYPTION aes_encryption;


    public String _decode(String code) {
        return AES.decrypt(code);
/*
        try {

            String decryptedData = aes_encryption.decrypt(code);
            System.out.println("Decrypted Data : " + decryptedData);

            return decryptedData;

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }


        return null;*/
       // return jwtUtils.getUserNameFromJwtToken(code);
    }

    public String _encode(String code) {

        return AES.encrypt(code);
      /*  try {

            String encryptedData = aes_encryption.encrypt(code);
            System.out.println("Encrypted Data : " + encryptedData);

            return encryptedData;
        } catch (Exception ignored) {
        }


        return null;*/
     //   return jwtUtils.generateJwtToken(code);

    }






    public String encodeFromId(Long code) {
        return jwtUtils.generateJwtToken(Long.toString(code));
    }

    public Long encodeFromId(String code) {
        return Long.valueOf(jwtUtils.generateJwtToken(code));
    }


    public String decodeFromVoucher(Voucher code) {
        return jwtUtils.getUserNameFromJwtToken(code.getCode());
    }

    public Voucher encodeFromVoucher(String crypto_code) {
        String code = jwtUtils.generateJwtToken(crypto_code);
        Optional<Voucher> voucherOptional = voucherRepository.findByCode(code);
        return voucherOptional.get() ;

    }

    public static void main(String[] args) {
        try {
            AES_ENCRYPTION aes_encryption = new AES_ENCRYPTION();
            aes_encryption.init();
            String encryptedData = aes_encryption.encrypt("Hello, welcome to the encryption world");
            String decryptedData = aes_encryption.decrypt(encryptedData);

            System.out.println("Encrypted Data : " + encryptedData);
            System.out.println("Decrypted Data : " + decryptedData);
        } catch (Exception ignored) {
        }
    }

}


