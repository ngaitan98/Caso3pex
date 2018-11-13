package Carga;

import java.io.BufferedReader;
import java.io.*;

public class Promedio {

	public static void main(String[] args) {
		try {
			String srcFile = "8PT-80T-100ms.txt";
			BufferedReader br = new BufferedReader(new FileReader("./Resultados/CSeguridad/" + srcFile));
			String text = null;
			BufferedWriter bw = new BufferedWriter(new FileWriter("./Resultados/CSeguridadPromediado/" + srcFile, false));
			int fallas = 0;
			double tiempo1 = 0;
			double tiempo2 = 0;
			double carga = 0;
			int c = 0;
			while ((text = br.readLine()) != null) {
				if(text.contains("T1"))
				{
					System.out.println(text);
				}
				else if(text.contains("Prueba"))
				{
					System.out.println(text);
				}
				else if(text.contains("Numero"))
				{
					fallas = Integer.parseInt(text.split(":")[1]);
					tiempo1 = tiempo1/(80-fallas);
					tiempo2 = tiempo2/(80-fallas);
					carga = carga/(80-fallas-c);
					bw.append(tiempo1 + " " + tiempo2 + " " + " " + fallas + " " + carga + "\n");
					System.out.println(tiempo1 + " " + tiempo2 + " " + carga);
					System.out.println("Fallas: " + fallas);
				}
				else
				{
					tiempo1 += Double.parseDouble(text.split(" ")[0]);
					tiempo2 += Double.parseDouble(text.split(" ")[1]);
					if(text.split(" ")[2].equals("NaN")) 
						{carga += 0;c++;}
					else
						carga += Double.parseDouble(text.split(" ")[2]);
				}
			}
			bw.close();
			br.close();
		} catch (Exception e) {
		}
		
	}
}


