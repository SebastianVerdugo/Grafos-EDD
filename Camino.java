package GrafosEDD;

public class Camino implements Comparable <Camino>{
	public Nodo destino;
	public double costo;
	
	public Camino (Nodo destino, double costo)
	{
		this.destino=destino;
		this.costo=costo;
	}
	
	public int compareTo(Camino rhs)
	{
		double otroCosto = rhs.costo;
		return costo < otroCosto ? -1 : costo > otroCosto ? 1 : 0;
	}


}
