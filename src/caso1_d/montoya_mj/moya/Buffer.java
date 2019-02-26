package caso1_d.montoya_mj.moya;

import java.util.ArrayList;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class Buffer {

	int capacidad;
	ArrayList<Mensaje> colaMensajes;
	int numeroClientes;

	public Buffer(int pCapacidad, int pNumeroClientes)
	{
		capacidad = pCapacidad;
		numeroClientes = pNumeroClientes;
		colaMensajes = new ArrayList<>();
	}
	//TODO cual es la diferencia entre sincronizar todo el metodo y sincronizar todas las partes
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
				//TODO Preguntar si este notify es necesario, si se hace el thread del cliente o del servidor
				//Como notificamos a los thread clientes (notifica solo al pool de threads dormidos que solo van a ser clientes)
			}
		}
		return mensaje;
	}

	public synchronized boolean hayClientes()
	{
		if( numeroClientes > 0 )
		{
			return true;
		}
		return false;
	}

	public synchronized void retiroCliente()
	{
		numeroClientes--;
	}
}
