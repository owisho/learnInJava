package sign;

import java.io.*;
import java.security.*;
import java.security.spec.*;

public class VerSig {
    /*Verify a DSA signature*/
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: VerSig publickeyfile signaturefile datafile");
        } else {
            try {
                //get public key
                FileInputStream keyfis = new FileInputStream(args[0]);
                byte[] encKey = new byte[keyfis.available()];
                keyfis.read(encKey);
                keyfis.close();
                //注意每个KeyPairGenerator生成的pubkey的keySpec，需要使用相应的实现去将从pubkey.getEncoded()方法获取到的内容
                //重新转化为keySpec，进而重新生成pubKey用于之后的签名校验
                X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
                KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
                PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);

                //get signature bytes
                FileInputStream sigfis = new FileInputStream(args[1]);
                byte[] sigToVerify = new byte[sigfis.available()];
                sigfis.read(sigToVerify);
                sigfis.close();

                //verify
                Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
                sig.initVerify(pubKey);
                FileInputStream datafis = new FileInputStream(args[2]);
                BufferedInputStream bufin = new BufferedInputStream(datafis);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = bufin.read(buffer)) > 0) {
                    sig.update(buffer, 0, len);
                }
                bufin.close();

                boolean verifies = sig.verify(sigToVerify);
                System.out.println("signature verifies: " + verifies);
            } catch (Exception e) {
                System.err.println("Caught exception " + e.toString());
            }
        }
    }
}
