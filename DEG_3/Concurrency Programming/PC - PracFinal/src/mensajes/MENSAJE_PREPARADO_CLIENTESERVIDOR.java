package mensajes;

public class MENSAJE_PREPARADO_CLIENTESERVIDOR extends Mensaje{
	public MENSAJE_PREPARADO_CLIENTESERVIDOR(String o, String d) {
		super(o, d);
		tipo = "MENSAJE_PREPARADO_CLIENTESERVIDOR";
	}
}
