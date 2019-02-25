package caso1_d.montoya_mj.moya;

public class Mensaje {

	private int respuesta ;
	private int envio;
	
	public Mensaje(int pEnvio)
	{
		envio = pEnvio;
		respuesta = -1 ;
	}
	//TODO Preguntar si este metodo debe ser sincronizado y porque?
	public synchronized void recibeRespuesta(int pRespuesta)
	{
		respuesta = pRespuesta;
		notify();
	}
	
	//TODO Preguntar si este metodo debe ser sincronizado y porque?
	public synchronized int getRespuesta()
	{
		if(respuesta == -1)
		{
			try
			{
				wait();
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		return respuesta;
	}
	
	public int getMensaje()
	{
		return envio;
	}
}
