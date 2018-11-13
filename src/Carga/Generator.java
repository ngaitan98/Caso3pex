package Carga;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import Cliente.ClienteConSeguridad;
import Cliente.ClienteSinSeguridad;
import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;

/**
 * GLoad Core Class - Task
 * @author Victor Guana at University of Los Andes (vm.guana26@uniandes.edu.co)
 * Systems and Computing Engineering Department - Engineering Faculty
 * Licensed with Academic Free License version 2.1
 * 
 * ------------------------------------------------------------
 * Example Class Client Server:
 * This is Generator Controller - Setup and Launch the Load 
 * Over the Server Using a Task
 * ------------------------------------------------------------
 * 
 */
public class Generator
{
	/**
	 * Load Generator Service (From GLoad 1.0)
	 */
	private LoadGenerator generator;
	
	/**
	 * Constructs a new Generator
	 */
	public Generator ()
	{
		Task work = createTask();
		int numberOfTasks = 80;
		int gapBetweenTasks = 100;
		generator = new LoadGenerator("Client - Server Load Test", numberOfTasks, work, gapBetweenTasks);
		generator.generate();
		try {
			print();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper that Constructs a Task
	 */
	private Task createTask()
	{
		return new ClienteTask();
	}
	
	public void print() throws Exception
	{
		Writer output;
		output = new BufferedWriter(new FileWriter("./Resultados/SSeguridad/8PT-80T-100ms.txt", true));  
		output.append("\nNumero de Fallas:" + ClienteSinSeguridad.fallas);
		output.close();
	}
	
	/**
	 * Starts the Application
	 * @param args
	 */
	public static void main (String ... args)
	{
		@SuppressWarnings("unused")
		Generator gen = new Generator();
	}
}

