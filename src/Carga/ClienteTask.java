package Carga;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;

import Cliente.ClienteConSeguridad;
import Cliente.ClienteSinSeguridad;
import uniandes.gload.core.Task;

/**
 * GLoad Core Class - Task
 * @author Victor Guana at University of Los Andes (vm.guana26@uniandes.edu.co)
 * Systems and Computing Engineering Department - Engineering Faculty
 * Licensed with Academic Free License version 2.1
 * 
 * ------------------------------------------------------------
 * Example Class Client Server:
 * This Class Represents the task that we want to generate in a concurrent way
 * ------------------------------------------------------------
 * 
 */
public class ClienteTask extends Task
{
	ClienteSinSeguridad c;
	@Override
	public void execute() 
	{
		Socket s;
		try {
			s = new Socket("localhost", 8080);
			PrintWriter pw = new PrintWriter( s.getOutputStream( ), true );
			BufferedReader br = new BufferedReader( new InputStreamReader( s.getInputStream( ) ) );
			c = new ClienteSinSeguridad(s, pw, br);
			c.start();
		} catch (IOException e) {
		}
		
	}

	@Override
	public void fail() 
	{
		// TODO Auto-generated method stub
		System.out.println(Task.MENSAJE_FAIL);
		
	}

	@Override
	public void success() 
	{
		
	}
	

}
