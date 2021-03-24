package example;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class RSADemo {
	private static String  src="infcn";
		private static RSAPublicKey rsaPublicKey;
		private static RSAPrivateKey rsaPrivateKey;
		
		static{
			KeyPairGenerator keyPairGenerator;
			try {
				keyPairGenerator=KeyPairGenerator.getInstance("RSA");
				keyPairGenerator.initialize(512);
				KeyPair keyPair=keyPairGenerator.generateKeyPair();
				rsaPublicKey=(RSAPublicKey) keyPair.getPublic();
				rsaPrivateKey=(RSAPrivateKey)keyPair.getPrivate();
				System.out.println("Public key:"+Base64.encode(rsaPublicKey.getEncoded()));
				System.out.println("Private key:"+Base64.encode(rsaPrivateKey.getEncoded()));
			} catch (NoSuchAlgorithmException e) {
				// TODO: handle exception
				e.printStackTrace();
			}  			
		}
		
		/**公钥加密，私钥解密
		 * @author 29284
		 * @throws NoSuchAlgorithmException 
		 * @throws InvalidKeySpecException 
		 * @throws NoSuchPaddingException 
		 * @throws InvalidKeyException 
		 * @throws BadPaddingException 
		 * @throws IllegalBlockSizeException 
		 */
		public static  void  pubEn2PriDe() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
			//公钥加密
			X509EncodedKeySpec x509EncodedKeySpec=new X509EncodedKeySpec(rsaPublicKey.getEncoded());
			KeyFactory keyFactory=KeyFactory.getInstance("RSA");
			PublicKey publicKey=keyFactory.generatePublic(x509EncodedKeySpec);
			Cipher cipher=Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] result=cipher.doFinal(src.getBytes());
			System.out.println("公钥加密，私钥解密--加密："+Base64.encode(result));
			//私钥解密
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec=new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
			keyFactory=KeyFactory.getInstance("RSA");
			PrivateKey privateKey=keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			result=cipher.doFinal(result);
			System.out.println("公钥加密，私钥解密--解密："+new String(result));
			
		}
		
		/**
	     * 私钥加密，公钥解密
	     * @author jijs
		 * @throws NoSuchAlgorithmException 
		 * @throws InvalidKeySpecException 
		 * @throws NoSuchPaddingException 
		 * @throws InvalidKeyException 
		 * @throws BadPaddingException 
		 * @throws IllegalBlockSizeException 
	     */
	    public static void priEn2PubDe() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

	        //私钥加密
	        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	        byte[] result = cipher.doFinal(src.getBytes());
	        System.out.println("私钥加密，公钥解密 --加密 : " + Base64.encode(result));

	        //公钥解密
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
	        keyFactory = KeyFactory.getInstance("RSA");
	        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
	        cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.DECRYPT_MODE, publicKey);
	        result = cipher.doFinal(result);
	        System.out.println("私钥加密，公钥解密   --解密: " + new String(result));
	        }
		public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
			pubEn2PriDe();
			priEn2PubDe();
		}
}
