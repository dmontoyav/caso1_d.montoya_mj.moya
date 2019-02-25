package caso1_d.montoya_mj.moya;

public class Cliente extends Thread{

	private Buffer buffer;
	private int cantMensajes;
	
	public Cliente(Buffer pBuffer,int pNumero)
	{
		buffer = pBuffer;
		cantMensajes = pNumero;
	}
	
	public void run()
	{
		int i = 0;
		while(i != cantMensajes)
		{
			
			Mensaje mensaje = new Mensaje((int) (Math.random()*10));
			System.out.println("El mensaje es: " + mensaje.getMensaje());
			buffer.addMensaje(mensaje);
			int resp = mensaje.getRespuesta();
			System.out.println("La respuesta del servidor es: " + resp);
			i++;
		}
		
		buffer.retiroCliente();
	}
	
}
