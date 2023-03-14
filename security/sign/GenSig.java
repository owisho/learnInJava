package sign;

import java.io.*;
import java.security.*;

public class GenSig {
    public static void main(String[] args) {
        /* Generate a DSA signature */
        if (args.length != 1) {
            System.out.println("Usage: GenSig nameOfFileToSign");
        } else {
            try {
                //生成秘钥对
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA","SUN");
//                SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
                SecureRandom random = SecureRandom.getInstanceStrong();
                keyGen.initialize(1024,random);
                KeyPair pair = keyGen.generateKeyPair();
                PrivateKey priv = pair.getPrivate();
                PublicKey pub = pair.getPublic();

                //初始化signature对象
                Signature dsa = Signature.getInstance("SHA1withDSA","SUN");
                dsa.initSign(priv);

                //获取文件对象，并将数据写入signature对象
                FileInputStream fis = new FileInputStream(args[0]);
                BufferedInputStream bufin = new BufferedInputStream(fis);
                byte[] buffer = new byte[1024];
                int len;
                while((len=bufin.read(buffer))>0){
                    dsa.update(buffer,0,len);
                }
                bufin.close();

                //获取签名byte[]
                byte[] realSig = dsa.sign();

                /*save the signature in a file*/
                FileOutputStream sigfos = new FileOutputStream("sig");
                sigfos.write(realSig);
                sigfos.close();

                /*save the pubkey in a file*/
                byte[] pubkey = pub.getEncoded();
                FileOutputStream keyfos = new FileOutputStream("suepk");
                keyfos.write(pubkey);
                keyfos.close();
            } catch (Exception e) {
                System.err.println("Caught exception " + e.toString());
            }
        }

    }

}
