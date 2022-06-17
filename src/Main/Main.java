
package Main;

import java.util.Scanner;

import Algoritmos.Algorimo;

public class Main {


	public static boolean encontrado = false;
	public static char[] charset = "abcdefghijklmnopqrstuvwxyz".toCharArray();	
	
	public static void main(String[] args){

		Scanner lector = new Scanner(System.in);
		System.out.println("Ingrese un algoritmo para la generaci�n del c�digo criptogr�fico:");
		System.out.println("(SHA-256 o SHA-512)");
		String algoritmo = lector.nextLine();
		System.out.println("Ingrese los datos de la transacci�n:");
		String datosTransaccion = lector.nextLine();
		System.out.println("Ingrese la cantidad de 0's buscada:");
		int ceros = Integer.parseInt(lector.nextLine());
		Sincronizar sinc = new Sincronizar();
		boolean fin = false;
		
		Algorimo[] hash = new Algorimo[26];
		
		if( algoritmo.equals("SHA-256") )
		{			
			long tiempoInicial= System.nanoTime();
			for (int i = 0; i < charset.length; i++) {
				
				hash[i] = new Algorimo(algoritmo, datosTransaccion, ceros, false, charset[i] + "", charset, sinc);
				hash[i].start();							
			}
			long tiempoFinal= System.nanoTime();
			long tiempo= tiempoFinal-tiempoInicial;
			while(!fin)
			{
				if(sinc.darTerminados() == 26 && !sinc.darTerminar()) {
					System.out.println("El tiempo dedicado y no encontrarlo fue:" + tiempo/1_000_000_000 + "segundos");
					fin = true;
				}
				else if(sinc.darTerminados() == 26)
				{
					fin = true;
				}				
			}
		}
		else if( algoritmo.equals("SHA-512") )
		{ 
			long tiempoInicial= System.nanoTime();
			int a = 0;
			for (int i = 0; i < charset.length; i++) {
				
				hash[i] = new Algorimo(algoritmo, datosTransaccion, ceros, false, charset[i] + "", charset, sinc);
				hash[i].start();
				a++;
				
			}
			System.out.println(a);
			long tiempoFinal= System.nanoTime();
			long tiempo= tiempoFinal-tiempoInicial;
			while(!fin)
			{
				if(sinc.darTerminados() == 26 && !sinc.darTerminar()) {
					System.out.println("El tiempo dedicado y no encontrarlo fue:" + tiempo/1_000_000_000 + "segundos");
					fin = true;
				}
				else if(sinc.darTerminados() == 26 && sinc.darTerminar()) 
				{
					fin = true;
				}				
			}		
		}
	}
}
