package alex;


public class UnidadLexicaUnivaluada extends UnidadLexica {
   public String lexema() {return "";}   
   public UnidadLexicaUnivaluada(int fila, int columna, int clase) {
     super(fila,columna, clase, "");  
   }
  public String toString() {
    return "[clase:"+clase()+",fila:"+fila()+",col:"+columna()+"]";  
  }   
}
