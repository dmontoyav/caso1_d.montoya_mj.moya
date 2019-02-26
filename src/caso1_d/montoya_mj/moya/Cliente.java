package caso1_d.montoya_mj.moya;

public class Cliente extends Thread{

	private Buffer buffer;
	private int cantMensajes;
	private int id;
	
	public Cliente(int pId,Buffer pBuffer,int pNumero)
	{
		buffer = pBuffer;
		cantMensajes = pNumero;
		id=pId;
	}
	
	public void run()
	{
		int i = 0;
		while(i != cantMensajes)
		{
			
			Mensaje mensaje = new Mensaje((int) (Math.random()*10));
			buffer.addMensaje(mensaje);
			System.out.println("El mensaje es: " + mensaje.getMensaje() + " del cliente "+ id);
			int resp = mensaje.getRespuesta();
			System.out.println("La respuesta del servidor es: " + resp + " del cliente " + id);
			i++;
		}
		
		buffer.retiroCliente();
		System.out.println("Se va el cliente "+ id);
	}
	
}
