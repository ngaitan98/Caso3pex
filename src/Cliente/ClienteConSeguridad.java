package Cliente;

import java.io.BufferedInputStream;






import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.security.auth.x500.X500Principal;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.*;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
public class ClienteConSeguridad extends Thread
{	
	public static int fallas = 0;
	public double tiempo1 = 0;
	public double tiempo2 = 0;
	private double carga;
	Socket s;
	PrintWriter pw;
	BufferedReader br;
	public ClienteConSeguridad(Socket s, PrintWriter pw, BufferedReader br)
	{
		this.s = s;
		this.pw = pw;
		this.br = br;
	}
	public void run()
	{
		pw.println("HOLA");
		try {
			String answer = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fallas++;
			return;
		}
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Ingrese el algoritmo de cifrado simetrico:");
//		String alg = sc.nextLine() + ":";
//		System.out.println("Ingrese el algoritmo de cifrado asimetrico:");
//		alg +=  sc.nextLine() + ":";
//		System.out.println("Ingrese el algoritmo de cifrado por HMAC:");
//		alg += sc.nextLine();
		String alg = "AES:RSA:HMACMD5";
		pw.println("ALGORITMOS:"+ alg);
		try {
			String answer = br.readLine();
			if(answer.equals("ERROR"))
			{
				fallas++;
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		}
		KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance(alg.split(":")[1], new BouncyCastleProvider());
		} catch (NoSuchAlgorithmException e) {
			fallas++;
			return;
		}
		generator.initialize(1024, new SecureRandom());
		KeyPair keyPair = generator.generateKeyPair();
		PublicKey puk;
		try {
			X509Certificate cg = generateCertificate(keyPair);
			byte[] cgbytes = cg.getEncoded();
			String cgstring = DatatypeConverter.printHexBinary(cgbytes);
			pw.println(cgstring);
		} catch (CertificateEncodingException | InvalidKeyException | IllegalStateException | NoSuchAlgorithmException
				| SignatureException e1) {
			fallas++;
			return;
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			fallas++;
			return;
		}
		try {
			String answer = br.readLine();
			if(answer.equals("ERROR"))
			{
				fallas++;
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fallas++;
			return;
		}
		try {
			String answer = br.readLine();
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			byte[] serverCertificate = DatatypeConverter.parseHexBinary(answer);
			try (FileOutputStream fos = new FileOutputStream("./data/certificadoservidor.txt")) {
				fos.write(serverCertificate);
			}
			Certificate cert = cf.generateCertificate(new FileInputStream(new File("./data/certificadoservidor.txt")));
			puk = cert.getPublicKey();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		}
		pw.println("OK");
		double inicio = System.currentTimeMillis();
		SecretKey symmetricKey;
		try {
			String answer = br.readLine();
			byte[] ls = DatatypeConverter.parseHexBinary(answer);

			Cipher decrypt=Cipher.getInstance(alg.split(":")[1]);
			decrypt.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
			byte[] decryptedMessage=decrypt.doFinal(ls);

			symmetricKey = new SecretKeySpec(decryptedMessage, alg.split(":")[0]);

			Cipher encrypt=Cipher.getInstance(alg.split(":")[1]);
			encrypt.init(Cipher.ENCRYPT_MODE, puk);
			byte[] encryptedMessage=encrypt.doFinal(decryptedMessage);
			pw.println(DatatypeConverter.printHexBinary(encryptedMessage));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			fallas++;
			e.printStackTrace();
			return;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		} 
		try {
			String answer = br.readLine();
			if(answer.equals("ERROR"))
			{
				fallas++;
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		}
		tiempo1 = System.currentTimeMillis() - inicio;
		try {
//			System.out.println("Ingrese un número:");
//			String consulta = sc.nextLine();
			inicio = System.currentTimeMillis();
			Cipher encrypt=Cipher.getInstance(alg.split(":")[0]);
			encrypt.init(Cipher.ENCRYPT_MODE, symmetricKey);
			byte[] encryptedMessage1=encrypt.doFinal("3".getBytes());

			pw.println(DatatypeConverter.printHexBinary(encryptedMessage1));

			String digest;
			try {
				Mac mac = Mac.getInstance(alg.split(":")[2]);
				mac.init(symmetricKey);
				byte[] bytes = mac.doFinal("3".getBytes("ASCII"));
				StringBuffer hash = new StringBuffer();
				for (int i = 0; i < bytes.length; i++) {
					String hex = Integer.toHexString(0xFF & bytes[i]);
					if (hex.length() == 1) {
						hash.append('0');
					}
					hash.append(hex);
				}
				digest = hash.toString();
				pw.println(digest.toUpperCase());
			} catch (UnsupportedEncodingException e) {
				fallas++;
				e.printStackTrace();
				return;
			} catch (InvalidKeyException e) {
				fallas++;
				e.printStackTrace();
				return;
			} catch (NoSuchAlgorithmException e) {
				fallas++;
				e.printStackTrace();
				return;
			}
		} catch (IllegalBlockSizeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fallas++;
			return;
		} catch (BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fallas++;
			return;
		} catch (InvalidKeyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fallas++;
			return;
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fallas++;
			return;
		} catch (NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			fallas++;
			return;
		}
		try {
			String answer = br.readLine();
			if(answer.equals("ERROR"))
			{
				fallas++;
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fallas++;
			return;
		}
		tiempo2 = System.currentTimeMillis() - inicio;
		try {
			carga = getSystemCpuLoad();
		} catch (Exception e) {
			fallas++;
			return;
		}
		print();

	}
	public static byte[ ] sign( String fileName, String algorithm, PrivateKey privateKey )
	{
		Signature signature;
		try
		{
			signature = Signature.getInstance ( algorithm );
			signature.initSign ( privateKey );
			BufferedInputStream bin = new BufferedInputStream ( new FileInputStream ( fileName ) );
			byte [ ] buffer = new byte [ 1024 ];
			int length;
			while ( bin.available ( ) != 0 )
			{
				length = bin.read ( buffer );
				signature.update ( buffer, 0, length );
			}
			bin.close ( );

			return signature.sign ( );
		}
		catch ( Exception e ){
			e.printStackTrace ( );
		}

		return null;
	}
	@SuppressWarnings("deprecation")
	public X509Certificate generateCertificate(KeyPair llaves) throws CertificateEncodingException, InvalidKeyException, IllegalStateException, NoSuchAlgorithmException, SignatureException, NoSuchProviderException
	{
		Date sd = new Date(System.currentTimeMillis());
		//1 año
		Date ed = new Date(System.currentTimeMillis() + 365*24*60*60*1000);
		BigInteger sn = new BigInteger(generateSerial());
		//X509Certificate c = generateV1();
		X509V3CertificateGenerator gen = new X509V3CertificateGenerator();
		X500Principal subjectName = new X500Principal("CN=Test V3 Certificate");

		gen.setSerialNumber(sn);
		gen.setIssuerDN(subjectName);
		gen.setNotBefore(sd);
		gen.setNotAfter(ed);
		gen.setPublicKey(llaves.getPublic());
		gen.setSignatureAlgorithm("MD5withRSA");
		gen.setSubjectDN(subjectName);
		return gen.generate(llaves.getPrivate());
	}
	public String generateSerial()
	{
		String answer = "";
		for(int i = 0; i < 70; i++)
		{
			answer += (int)(Math.random()*9);
		}
		return answer;
	}
	public void print()
	{
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter("./Resultados/CSeguridad/8PT-400T-20ms.txt", true));  
			output.append("\n" + tiempo1 + " "+tiempo2 + " " +carga);
			output.close();
		} catch (IOException e) {
			fallas++;
			return;
		}
	}
	public double getSystemCpuLoad() throws Exception{
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = ObjectName.getInstance("java.lang:type=OperatingSystem");
		AttributeList list = mbs.getAttributes(name, new String[]{ "SystemCpuLoad" });
		if (list.isEmpty()) return Double.NaN;
		Attribute att = (Attribute)list.get(0);
		Double value = (Double)att.getValue();
		// usually takes a couple of seconds before we get real values
		if (value == -1.0) return Double.NaN;
		// returns a percentage value with 1 decimal point precision
		return ((int)(value * 1000) / 10.0);
	}

}

