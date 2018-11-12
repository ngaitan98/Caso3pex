package Cliente;

import java.io.BufferedInputStream;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Scanner;

public class ClienteSinSeguridad extends Thread
{
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
		System.out.println("Cliente HOLA");
		try {
			String answer = br.readLine();
			System.out.println("Servidor " + answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR en 1");
			return;
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Ingrese el algoritmo Simetrico:");
		String alg = sc.nextLine() + ":";
		System.out.println("Ingrese el algoritmo de Asimetrico:");
		alg +=  sc.nextLine() + ":";
		System.out.println("Ingrese el algoritmo de cifrado por HMAC:");
		alg += sc.nextLine();
		System.out.println("ALGORITMOS:"+alg);
		pw.println("ALGORITMOS:"+alg);
		try {
			String answer = br.readLine();
			System.out.println("Servidor " + answer);
			if(answer.equals("ERROR"))
			{
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR en 2");
			return;
		}
		pw.println("Certificado del Cliente");
		try {
			String answer = br.readLine();
			System.out.println("Servidor " + answer);
			if(answer.equals("ERROR"))
			{
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR en 3");
			return;
		}
		try {
			String answer = br.readLine();
			System.out.println("Servidor " + answer);
			if(answer.equals("ERROR"))
			{
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR en 4");
			return;
		}
		pw.println("OK");
		try {
			String answer = br.readLine();
			System.out.println("Servidor " + answer);
			if(answer.equals("ERROR"))
			{
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR en 4");
			return;
		}
		pw.println("LS");
		try {
			String answer = br.readLine();
			System.out.println("Servidor " + answer);
			if(answer.equals("ERROR"))
			{
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR en 4");
			return;
		}
		System.out.println("Ingrese la consulta:");
		String num1 = sc.nextLine();
		pw.println(num1);
		pw.println(num1);
		try {
			String answer = br.readLine();
			System.out.println("Servidor " + answer);
			if(answer.equals("ERROR"))
			{
				return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR en 4");
			return;
		}
	}
	public final static void main(String[] args)
	{
		try {
			Socket s = new Socket("localhost", 8080);
			PrintWriter pw = new PrintWriter( s.getOutputStream( ), true );
            BufferedReader br = new BufferedReader( new InputStreamReader( s.getInputStream( ) ) );
            ClienteSinSeguridad c = new ClienteSinSeguridad(s, pw, br);
            c.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return;
		}
	}
}

