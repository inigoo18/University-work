package mensajes;

public class MENSAJE_CERRAR_CONEXION extends Mensaje {
	
	public MENSAJE_CERRAR_CONEXION(String o, String d) {
		super(o, d);
		tipo = "MENSAJE_CERRAR_CONEXION";
	}
}
