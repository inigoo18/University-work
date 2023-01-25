package alex;

import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import errors.GestionErroresTiny;

public class AnalizadorLexicoTiny {

   private Reader input;
   private StringBuffer lex;
   private int sigCar;
   private int filaInicio;
   private int columnaInicio;
   private int filaActual;
   private int columnaActual;
   private GestionErroresTiny errores;
   private static String NL = System.getProperty("line.separator");
   
   private static enum Estado {
    INIT, RECVAR, RECENT, RECMAS, RECMENOS, REC0, RECPUNT, RECPRD, RECE, RECDEC, RECEXP, RECSIGNOEXP, REC0EXP,
    RECPC, RECDIV, RECMUL, RECPCIE, RECPAP, RECEOF,
    RECGT, RECGTE, RECLT, RECLTE, RECASIG, RECSAME, RECEXCLAM, RECDIFF, RECAMP1, RECAMP, RECCOM
   }

   private Estado estado;

   public AnalizadorLexicoTiny(Reader input) throws IOException {
    this.input = input;
    lex = new StringBuffer();
    sigCar = input.read();
    filaActual=1;
    columnaActual=1;
   }
   
   public UnidadLexica sigToken() throws IOException {
     estado = Estado.INIT;
     filaInicio = filaActual;
     columnaInicio = columnaActual;
     lex.delete(0,lex.length());
     while(true) {
         switch(estado) {
           case INIT: 
              if(hayLetra())  transita(Estado.RECVAR);
              else if (hayDigitoPos()) transita(Estado.RECENT);
              else if (hayCero()) transita(Estado.REC0);
              else if (haySuma()) transita(Estado.RECMAS);
              else if (hayResta()) transita(Estado.RECMENOS);
              else if (hayMul()) transita(Estado.RECMUL);
              else if (hayDiv()) transita(Estado.RECDIV);
              else if (hayPAp()) transita(Estado.RECPAP);
              else if (hayPCierre()) transita(Estado.RECPCIE);
              else if (hayIgual()) transita(Estado.RECASIG);
              else if (hayExclamacion()) transita(Estado.RECEXCLAM);
              else if (hayMenor()) transita(Estado.RECLT);
              else if (hayMayor()) transita(Estado.RECGT);
              else if (hayPuntoComa()) transita(Estado.RECPC);
              else if (hayAmp()) transita(Estado.RECAMP1);
              else if (hayAlmohadilla()) transita(Estado.RECEOF);
              else if (hayNL()) transitaIgnorando(Estado.INIT);
              else if (hayEOF()) transita(Estado.RECEOF);
              else if (haySep()) transitaIgnorando(Estado.INIT);
              else error();
 
              break;
           case RECVAR: 
              if (hayLetra() || hayDigito() || haySubrayado()) transita(Estado.RECVAR);
              else return unidadId();               
              break;
           case RECENT:
               if (hayDigito()) transita(Estado.RECENT);
               else if (hayPunto()) transita(Estado.RECPUNT);
               else if (hayExp()) transita(Estado.RECE);
               else return unidadEnt();
               break;
           case RECE:
        	   if (hayDigitoPos()) transita(Estado.RECEXP);
        	   else if (haySuma() || hayResta()) transita(Estado.RECSIGNOEXP);
        	   else if (hayCero()) transita(Estado.REC0EXP);
        	   else error();
        	   break;
           case REC0EXP:
        	   return unidadReal();
           case RECEXP:
               if (hayDigito()) transita(Estado.RECEXP);
               else return unidadReal();
               break;
           case RECSIGNOEXP:
        	   if (hayCero()) transita(Estado.REC0EXP);
        	   else if(hayDigitoPos()) transita(Estado.RECEXP);
        	   else error();
        	   break;
           case RECPUNT:
        	   if (hayDigito()) transita(Estado.RECDEC);
        	   else error();
        	   break;
           case RECPRD:
        	   if (hayDigitoPos()) transita(Estado.RECDEC);
        	   else if (hayCero()) transita(Estado.RECPRD);
        	   else error();
        	   break;
           case RECDEC:
        	   if (hayExp()) transita(Estado.RECE);
        	   else if (hayDigitoPos()) transita(Estado.RECDEC);
        	   else if (hayCero()) transita(Estado.RECPRD);
        	   else return unidadReal();   
        	   break;
           case REC0:
        	   if (hayPunto()) transita(Estado.RECPUNT);
        	   else if (hayExp()) transita(Estado.RECE);
        	   else return unidadEnt();
        	   break;        	   
           case RECMAS:
               if (hayDigitoPos()) transita(Estado.RECENT);
               else if(hayCero()) transita(Estado.REC0);
               else return unidadMas();
               break;
           case RECMENOS: 
               if (hayDigitoPos()) transita(Estado.RECENT);
               else if(hayCero()) transita(Estado.REC0);
               else return unidadMenos();
               break;
           case RECMUL: return unidadPor();
           case RECDIV: return unidadDiv();              
           case RECPAP: return unidadPAp();
           case RECPCIE: return unidadPCierre();
           case RECASIG:
        	   if (hayIgual()) transita(Estado.RECSAME);
        	   else return unidadIgual();
           case RECSAME:
        	   return unidadSame();
           case RECCOM: 
               if (hayNL()) transitaIgnorando(Estado.INIT);
               else if (hayEOF()) transita(Estado.RECEOF);
               else transitaIgnorando(Estado.RECCOM);
               break;
           case RECEOF: return unidadEof();
           case RECLT: 
        	   if (hayIgual()) transita(Estado.RECLTE);
        	   else return unidadMenor();
           case RECGT: 
        	   if (hayIgual()) transita(Estado.RECGTE);
        	   else return unidadMayor();
           case RECLTE:
        	   return unidadMenorIgual();
           case RECGTE:
        	   return unidadMayorIgual();
           case RECEXCLAM:
        	   if (hayIgual()) transita(Estado.RECDIFF);
        	   else error();
           case RECDIFF:
        	   return unidadDiff();
           case RECAMP1:
        	   if (hayAmp()) transita(Estado.RECAMP);
        	   else error();
           case RECAMP:
        	   return unidadAmp();
           case RECPC:
        	   return unidadPuntoComa();
         }
     }    
   }
   private void transita(Estado sig) throws IOException {
     lex.append((char)sigCar);
     sigCar();         
     estado = sig;
   }
   private void transitaIgnorando(Estado sig) throws IOException {
     sigCar();         
     filaInicio = filaActual;
     columnaInicio = columnaActual;
     estado = sig;
   }
   private void sigCar() throws IOException {
     sigCar = input.read();
     if (sigCar == NL.charAt(0)) saltaFinDeLinea();
     if (sigCar == '\n') {
        filaActual++;
        columnaActual=0;
     }
     else {
       columnaActual++;  
     }
   }
   private void saltaFinDeLinea() throws IOException {
      for (int i=1; i < NL.length(); i++) {
          sigCar = input.read();
          if (sigCar != NL.charAt(i)) error();
      }
      sigCar = '\n';
   }
   
   private boolean hayLetra() {return sigCar >= 'a' && sigCar <= 'z' ||
                                      sigCar >= 'A' && sigCar <= 'z';}
   private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
   private boolean hayCero() {return sigCar == '0';}
   private boolean hayDigito() {return hayDigitoPos() || hayCero();}
   private boolean haySuma() {return sigCar == '+';}
   private boolean hayResta() {return sigCar == '-';}
   private boolean hayMul() {return sigCar == '*';}
   private boolean hayDiv() {return sigCar == '/';}
   private boolean hayPAp() {return sigCar == '(';}
   private boolean hayPCierre() {return sigCar == ')';}
   private boolean hayIgual() {return sigCar == '=';}
   private boolean hayPunto() {return sigCar == '.';}
   private boolean hayPuntoComa() {return sigCar == ';';}
   private boolean hayAmp() {return sigCar == '&';}
   private boolean hayMenor() {return sigCar == '<';}
   private boolean hayMayor() {return sigCar == '>';}
   private boolean hayExclamacion() {return sigCar == '!';}
   private boolean hayAlmohadilla() {return sigCar == '!';}
   private boolean haySubrayado() {return sigCar == '_';}
   private boolean hayExp() {return sigCar == 'E' || sigCar == 'e';}
   private boolean hayNL() {return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';}
   private boolean haySep() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n';}
   private boolean hayEOF() {return sigCar == -1;}
   private UnidadLexica unidadId() {
     switch(lex.toString()) {
         case "true":  
            return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.TRUE);
         case "false":    
            return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.FALSE);
         case "int":    
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.INT);
         case "real":    
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.REAL);
         case "bool":    
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.BOOL);
         case "and":    
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.AND);
         case "or":    
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.OR);
         case "not":    
             return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.NOT);
         default:    
            return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.VAR,lex.toString());     
      }
   }  
   private UnidadLexica unidadEnt() {
     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_INT,lex.toString());     
   }    
   private UnidadLexica unidadReal() {
     return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_REAL,lex.toString());     
   }    
   private UnidadLexica unidadMas() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAS);     
   }    
   private UnidadLexica unidadMenos() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOS);     
   }    
   private UnidadLexica unidadPor() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MUL);     
   }    
   private UnidadLexica unidadDiv() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DIV);     
   }    
   private UnidadLexica unidadPAp() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PAP);     
   }    
   private UnidadLexica unidadPCierre() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PCIE);     
   }    
   private UnidadLexica unidadMenor() {
	 return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LT);     
   }
   private UnidadLexica unidadMayor() {
	   return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.GT);     
   }
   private UnidadLexica unidadMayorIgual() {
	   return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.GTE);     
   }
   private UnidadLexica unidadMenorIgual() {
	   return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LTE);     
   }
   private UnidadLexica unidadIgual() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ASIG);     
   }    
   private UnidadLexica unidadEof() {
     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EOF);     
   }
   private UnidadLexica unidadDiff() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DIFF);     
   }
   private UnidadLexica unidadSame() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.SAME);     
   }
   private UnidadLexica unidadPuntoComa() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PC);     
   }
   private UnidadLexica unidadAmp() {
	     return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.AMP);     
   }
   private void error() {
     //System.err.println("("+filaActual+','+columnaActual+"):Caracter inexperado"); 
     errores.errorLexico(filaActual, columnaActual, (char)sigCar);
   }
   
   public void fijaGestionErrores(GestionErroresTiny errores) {
	   this.errores = errores;
   }

   public static void main(String arg[]) throws IOException {
     Reader input = new InputStreamReader(new FileInputStream("input.txt"));
     AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
     UnidadLexica unidad;
     do {
       unidad = al.sigToken();
       System.out.println(unidad);
     }
     while (unidad.clase() != ClaseLexica.EOF);
    } 
}