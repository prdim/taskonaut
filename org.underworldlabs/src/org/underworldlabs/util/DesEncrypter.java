/*
 * DesEncrypter.java
 *
 * Copyright (C) 2002-2007 Takis Diakoumis
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.underworldlabs.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/* ----------------------------------------------------------
 * CVS NOTE: Changes to the CVS repository prior to the 
 *           release of version 3.0.0beta1 has meant a 
 *           resetting of CVS revision numbers.
 * ----------------------------------------------------------
 */

/**
 *
 * @author   Takis Diakoumis
 * @version  $Revision: 636 $
 * @date     $Date: 2007-01-03 19:01:11 +1100 (Wed, 03 Jan 2007) $
 */
public class DesEncrypter {
    
    private static char[] pwdChars = 
            "abcdefghijklmnopqrstuvqxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
    
    public DesEncrypter() {}

    public static String encrypt(String key, String value) {
        try {            
            SecretKey secretKey = getSecretKey(key);
            
            Cipher ecipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] cleartext = value.getBytes("UTF8");
			byte[] ciphertext = ecipher.doFinal(cleartext);

			sun.misc.BASE64Encoder base64encoder = new sun.misc.BASE64Encoder();
			return base64encoder.encode(ciphertext);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static SecretKey getSecretKey(String key) throws Exception {
        byte[] keyAsBytes = key.getBytes("UTF8");
        KeySpec keySpec = new DESKeySpec(keyAsBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(keySpec);
    }
    
    public static String decrypt(String key, String value) {
        try {
            SecretKey secretKey = getSecretKey(key);

            Cipher dcipher = Cipher.getInstance("DES");
			dcipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(value);
            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);
            
            // Decode using utf-8
            return new String(utf8, "UTF8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateKey(int length) {        
        try {
            StringBuffer key = new StringBuffer(length);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");        
            byte[] intbytes = new byte[4];      

            for (int i = 0; i < length; i++) {
                random.nextBytes(intbytes);
                key.append(pwdChars[Math.abs(getIntFromByte(intbytes) % pwdChars.length)]);
            }

            return key.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static int getIntFromByte(byte[] bytes) {
        int returnNumber = 0;
        int pos = 0;
        returnNumber += byteToInt(bytes[pos++]) << 24;
        returnNumber += byteToInt(bytes[pos++]) << 16;
        returnNumber += byteToInt(bytes[pos++]) << 8;
        returnNumber += byteToInt(bytes[pos++]) << 0;
        return returnNumber;
    }

    private static int byteToInt(byte b) {
        return (int) b & 0xFF;
    }

}




