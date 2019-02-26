package caso1_d.montoya_mj.moya;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Servidor {

	//TODO preguntar si el buffer debe ser estático. 
	private Buffer buffer;
	private ServidorThread[] servidores;
	private static final String RUTA = "parametros.txt";
	
	public Servidor(Buffer pBuffer, int numServidores)
	{
		buffer = pBuffer;
		servidores = new ServidorThread[numServidores];
		for(int i = 0; i < numServidores; i++)
		{
			servidores[i] = new ServidorThread(buffer);
			servidores[i].start();
		}
	}
	
	private void terminoEjecucion() 
	{
		System.out.println("Empieza termino "+ servidores.length);
		for(int i = 0; i<servidores.length; i++)
		{
			try {
				//TODO Preguntar bien para que sirve el join.
				System.out.println("Antes del join" + i);
				servidores[i].join();
				System.out.println("Entro!!!!" + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("Error esperando a los demas");
			}
		}
		System.out.println("Terminaron todos los threads");
	}
	
	public static void main(String[] args) {

		int capacidadBuffer = 0;
		int numeroServ = 0;
		int numeroClientes = 0;
		int [] cantMensajes = null;
		
		//Leer archivo
		BufferedReader lector;
		try {
			lector = new BufferedReader(new FileReader(RUTA));
			//lector.readLine();
			String info = lector.readLine();
			lector.close();
			
			//Procesar lectura
			//Preguntar si al especificar el formato del archivo no se deben poner los nombres. 
			
			String[] valores = info.split(",");
			
			//Lee la capacidad del buffer
			capacidadBuffer = Integer.parseInt(valores[0]);
			System.out.println("Capacidad buffer : " + capacidadBuffer);
			
			//Lee el numero de servidores
			numeroServ=Integer.parseInt(valores[1]);
			System.out.println("Numero servidores: " + numeroServ);
			
			//Lee el numero de clientes
			numeroClientes = Integer.parseInt(valores[2]);
			System.out.println("Numero Clientes: " + numeroClientes);
			
			//Cantidad de mensajes por cliente
			String[] cantidadMensajes = valores[3].split(";");
			cantMensajes = new int[numeroClientes];
			
			for(int i = 0; i< cantidadMensajes.length; i++)
			{
				cantMensajes[i] = Integer.parseInt(cantidadMensajes[i]);
				System.out.println("Cantidad mensajes del cliente " + (i+1) + " = " + cantMensajes[i]);
			}
			
			//Comprobación datos correctos
			if( numeroClientes < 0 || numeroServ < 0 || capacidadBuffer<0 || cantMensajes.length < numeroClientes){
				throw new Exception("Los datos recibidos no son válidos");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Ejecución del programa
		Buffer pBuffer = new Buffer(capacidadBuffer, numeroClientes);
		
		for(int i = 0; i< numeroClientes; i++){
			new Cliente(i,pBuffer, cantMensajes[i]).start();
		}
		Servidor ser = new Servidor(pBuffer, numeroServ);
		ser.terminoEjecucion();
		
	}

}
