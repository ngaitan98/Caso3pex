package Cliente;

import java.io.BufferedInputStream;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Scanner;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ClienteSinSeguridad extends Thread
{
	public static int fallas = 0;
	public double tiempo1 = 0;
	public double tiempo2 = 0;
	private double carga;
	
	Socket s;
	PrintWriter pw;
	BufferedReader br;
	public ClienteSinSeguridad(Socket s, PrintWriter pw, BufferedReader br)
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
		pw.println("ALGORITMOS:"+"AES:RSA:HMACMD5");
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
		pw.println("Certificado del Cliente");
		try {
			String answer = br.readLine();
			if(answer.equals("ERROR"))
			{
				fallas++;
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		try {
			String answer = br.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fallas++;
			return;
		}
		pw.println("OK");
		double inicio = System.currentTimeMillis();
		try {
			String answer = br.readLine();
			if(answer.equals("ERROR"))
			{
				fallas++;
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
		pw.println("LS");
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
		tiempo1 = System.currentTimeMillis() - inicio;
		pw.println(3);
		pw.println(3);
		try {
			inicio = System.currentTimeMillis();
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
		tiempo2 = System.currentTimeMillis() - inicio;
		try {
			carga = getSystemCpuLoad();
		} catch (Exception e) {
			fallas++;
			return;
		}
		print();
	}
	public void print()
	{
		Writer output;
		try {
			output = new BufferedWriter(new FileWriter("./Resultados/SSeguridad/1PT-400T-20ms.txt", true));  
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

