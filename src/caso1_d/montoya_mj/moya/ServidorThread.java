package caso1_d.montoya_mj.moya;

public class ServidorThread extends Thread{

	private Buffer buffer;
	
	public ServidorThread(Buffer pBuffer)
	{
		buffer = pBuffer;
	}
	
	public void run()
	{
		while(buffer.hayClientes())
		{
			Mensaje mensaje = buffer.darMensaje();
			mensaje.recibeRespuesta(mensaje.getMensaje()+1);
		}
	}
}
