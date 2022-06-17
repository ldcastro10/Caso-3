package Main;

public class Sincronizar {
	
	private boolean terminar;
	
	private int terminados;
	
	public Sincronizar()
	{
		this.terminar = false;
		this.terminados = 0;
	}
	
	public boolean darTerminar()
	{
		return terminar;
	}
	
	public void cambiarTerminar()
	{
		terminar = true;
	}

	public void sumar()
	{
		terminados++;
	}
	
	public int darTerminados() {
		return terminados;
	}
}
