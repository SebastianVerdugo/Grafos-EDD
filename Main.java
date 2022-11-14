package GrafosEDD;

import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		Grafo g = new Grafo();
		try
		{
			FileReader fin = new FileReader("GrafoArchivo.txt");
			Scanner archivoGrafo = new Scanner (fin);
			String linea;
			while(archivoGrafo.hasNextLine())
			{
				linea = archivoGrafo.nextLine();
				StringTokenizer st = new StringTokenizer(linea);
				try
				{
					if(st.countTokens()!=3)
					{
						System.err.println("Saltando linea incorrecta");
						continue;
					}
					String origen = st.nextToken();
					String destino = st.nextToken();
					int costo = Integer.parseInt(st.nextToken());
					g.agregarCamino(origen, destino, costo);
				}
				catch(NumberFormatException e)
				{
					System.err.println("Saltando linea incorrecta "+linea);
				}
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
		System.out.println("Archivo leido...");
		Scanner in = new Scanner (System.in);
		while(procesarSolicitud(in, g));
	}
	public static boolean procesarSolicitud(Scanner in, Grafo g)
	{
		try
		{
			System.out.print("Ingrese el nodo de inicio:");
			String nodoInicio=in.nextLine();
			
			System.out.print("Ingrese el nodo al cual quiere llegar:");
			String nodoDestino= in.nextLine();
			
			System.out.print("Ingrese el algoritmo(u: no ponderado, d: dijkstra, n: negativo, a: aciclico):");
			String alg= in.nextLine();
			
			if(alg.equals("u"))
				g.noPonderado(nodoInicio);
			else if(alg.equals("d"))
				g.dijkstra(nodoInicio);
			else if(alg.equals("n"))
				g.negativo(nodoInicio);
			else if(alg.equals("a"))
				g.aciclico(nodoInicio);
			
			g.imprimirCamino(nodoDestino);	
		}
		catch(NoSuchElementException e)
		{
			return false;
		}
		catch(ExcepcionesGrafo e)
		{
			System.err.println(e);
		}
		return true;
	}
}
