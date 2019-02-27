package caso1_d.montoya_mj.moya;

public class Mensaje {

	private int respuesta ;
	private int envio;
	
	/**
	 * Constructor
	 * @param pEnvio
	 */
	public Mensaje(int pEnvio)
	{
		envio = pEnvio;
		respuesta = -1 ;
	}
	
	/**
	 * Agrega la respuesta y notifica al cliente que ya puede pedir la respuesta
	 */
	public synchronized void recibeRespuesta(int pRespuesta)
	{
		respuesta = pRespuesta;
		notify();
	}
	
	
	/**
	 * Retorna la respuesta si el thread servidor ya proceso el mensaje y por lo tanto es diferente de -1.
	 * Si la respuesta no esta lista pone al thread de cliente en espera pasiva. 
	 * @return Respuesta del servidor
	 */
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
