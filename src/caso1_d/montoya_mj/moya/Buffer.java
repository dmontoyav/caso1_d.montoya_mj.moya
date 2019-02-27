package caso1_d.montoya_mj.moya;

import java.util.ArrayList;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class Buffer {

	int capacidad;
	ArrayList<Mensaje> colaMensajes;
	int numeroClientes;

	/**
	 * Constructos de la clase
	 * @param pCapacidad : cantidad de mensajes que soporta el buffer
	 * @param pNumeroClientes
	 */
	
	public Buffer(int pCapacidad, int pNumeroClientes)
	{
		capacidad = pCapacidad;
		numeroClientes = pNumeroClientes;
		colaMensajes = new ArrayList<>();
	}
	
	/**
	 * Agrega el mensaje a la lista de mensajes en caso en que el buffer no esté lleno o de lo contrario duerme el thread cliente
	 * @param mensaje Mensaje que envia el cliente
	 */
	
	public synchronized void addMensaje(Mensaje mensaje)
	{
		boolean lleno = false;

		if(colaMensajes.size() >= capacidad)
		{
			lleno = true;
		}
		while(lleno)
		{
			try 
			{
				wait();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
		colaMensajes.add(mensaje);
		System.out.println("Cant mensajes buffer " + colaMensajes.size());
	}

	/**
	 * Metodo que retorna el primer mensaje de la lista en el buffer a un thread servidor para ser procesado
	 * @return Si hay mensajes en la lista retorna el primero sino retorna null
	 */
	
	public Mensaje darMensaje()
	{
		Mensaje mensaje = null;
		while(mensaje == null && hayClientes())
		{
			Thread.yield(); //Hace la espera activa del servidor para recibir mensajes del buffer
			synchronized (this) {
				if(!colaMensajes.isEmpty()){
					mensaje = colaMensajes.remove(0);
					notify();
				}
			}
		}
		return mensaje;
	}
	
	/**
	 * Verifica si aun hay clientes por procesar
	 * @return
	 */
	public synchronized boolean hayClientes()
	{
		if( numeroClientes > 0 )
		{
			return true;
		}
		return false;
	}

	/**
	 * Metodo que notifica que un cliente ya completo el proceso de mensajería y por lo tanto el thread acabó la ejecución. 
	 */
	
	public synchronized void retiroCliente()
	{
		numeroClientes--;
	}
}
