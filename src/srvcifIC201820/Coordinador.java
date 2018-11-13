package srvcifIC201820;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.Security;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import srvIC201820.Delegado4;

public class Coordinador {

	private static ServerSocket ss;	
	private static final String MAESTRO = "MAESTRO: ";
	static java.security.cert.X509Certificate certSer; /* acceso default */
	static KeyPair keyPairServidor; /* acceso default */

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		System.out.println(MAESTRO + "Establezca puerto de conexion:");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		int ip = Integer.parseInt(br.readLine());
		System.out.println(MAESTRO + "Empezando servidor maestro en puerto " + ip);
		// Adiciona la libreria como un proveedor de seguridad.
		// Necesario para crear certificados.
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());		
		keyPairServidor = Seg.grsa();
		certSer = Seg.gc(keyPairServidor);

		crearPool(8080);
	}
	private static void crearPool(int puerto)
	{
		//Crea el pool

		int numT = 1;

		ExecutorService exec = Executors.newFixedThreadPool(numT);

		System.out.println(MAESTRO + "Creado pool de tamanio "+ numT);


		int idThread = 0;

		// Crea el socket que escucha en el puerto seleccionado.

		try {
			ss = new ServerSocket(puerto);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(MAESTRO + "Error en el puerto");
			return;
		}

		System.out.println(MAESTRO + "Socket creado.");

		while (true) {

			try { 

				Socket sc = ss.accept();

				System.out.println(MAESTRO + "Cliente " + idThread + " aceptado.");

				//Delegado3 d3 = new Delegado3(sc,idThread);

				exec.execute(new Delegado3(sc,idThread));

				idThread++;

				//d3.start();

			} catch (IOException e) {

				System.out.println(MAESTRO + "Error creando el socket cliente.");

				e.printStackTrace();

			}

		}
	}
}
