package Algoritmos;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Main.Sincronizar;

public class Algorimo extends Thread {

	private String algoritmo;
	private static String datosTransaccion;
	private int numeroCeros;
	private boolean encontrado; 
	private MessageDigest digest;
	private String letra;
	private char[] charset;
	private Sincronizar sincronizar;
	private boolean encontradoAca;

	public Algorimo(String pAlgoritmo, String pDatos, int pNumCeros, boolean pEncontrado, String pLetra, char[] pLetras, Sincronizar pSinc) {		
		this.datosTransaccion = pDatos;
		this.numeroCeros = pNumCeros;
		this.encontrado = pEncontrado;
		this.letra = pLetra;
		this.charset = pLetras;
		this.sincronizar = pSinc;
		this.encontradoAca = false;
		try {
			this.digest = MessageDigest.getInstance(pAlgoritmo);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public byte[] obtenerHash(String complemento) {

		String prueba = datosTransaccion + complemento;
		byte[] byteArray = prueba.getBytes();
		digest.update(byteArray);
		return digest.digest();
	}

	public void generate(String str, int pos, int length) {
		if (length == 0) {

			comprobar(str);

		} else {

			if (pos != 0) {
				pos = 0;
			}

			for (int i = pos; i < charset.length && !sincronizar.darTerminar(); i++) {
				generate(str + charset[i], i, length - 1);
			}
		}
	}

	public String imprimirHexa(byte[] byteArray) 
	{
		String out = "";
		for(int i=0;i<byteArray.length;i++) 
		{
			if((byteArray[i] & 0xff)<= 0xf)  
			{
				out+="0";
			}
			out+=Integer.toHexString(byteArray[i] & 0xff).toLowerCase();
		}

		return out;
	}


	public synchronized void comprobar(String str)
	{
		byte[] hash = obtenerHash(str);
		char[] hexa = imprimirHexa(hash).toCharArray();
		int cerosAct = 0;
		boolean condicion = true;

		while(cerosAct< numeroCeros/4 && condicion) {				
			if(hexa[cerosAct] == '0')
			{
				cerosAct++;
			}
			else
			{
				condicion = false;
			}				
		}				

		if(condicion && !sincronizar.darTerminar() && hexa[cerosAct+1] != '0')
		{
			sincronizar.cambiarTerminar();			
			encontradoAca = true;
			System.out.println("\n------------------");
			System.out.println("La cadena ingresada por el usuario es: " + datosTransaccion);
			System.out.println("El valor para V es: " + str);
			System.out.println("El valor del Hash es: " + imprimirHexa(hash));				
			System.out.println("Hash encontrado\n-----");	
		}					
	}


	public void run(){

		int length = 6;
		long tiempoInicial= System.nanoTime();
		while(!sincronizar.darTerminar() && length > 0)
		{

			generate(letra, 0, length);

			if(!sincronizar.darTerminar())
			{
				comprobar(letra);
			}

			length--;
		}

		sincronizar.sumar();
		long tiempoFinal= System.nanoTime();
		long tiempo= tiempoFinal-tiempoInicial;
		if(encontradoAca)
		{
			System.out.println("El tiempo de encontrarlo fue:" + tiempo/1_000_000_000 + "segundos");
		}
	}
}
