package GrafosEDD;

import java.util.*;

public class Nodo {
	public String nombre;
	public List <Camino>caminos;
	public double distancia;
	public Nodo anterior;
	public int scratch;
	
	public Nodo (String nombre)
	{
		this.nombre=nombre;
		this.caminos = new LinkedList<Camino>();
		Reiniciar();
	}
	public void Reiniciar()
	{
		this.distancia= Double.MAX_VALUE;
		anterior=null;
	}
}
