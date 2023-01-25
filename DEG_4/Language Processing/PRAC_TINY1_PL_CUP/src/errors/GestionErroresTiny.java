package errors;

import alex.UnidadLexica;

public class GestionErroresTiny {
   public void errorLexico(int fila, int col, String string) {
     System.out.println("ERROR fila "+fila+","+col+": Caracter inexperado: "+string); 
     System.exit(1);
   }  
   public void errorSintactico(UnidadLexica lex) {
     System.out.print("ERROR fila "+lex.fila()+","+lex.columna()+": Encontrado " + lex.lexema());
     System.out.println();
     System.exit(1);
   }
   public void errorFatal(Exception e) {
     System.out.println(e);
     e.printStackTrace();
     System.exit(1);
   }
}
