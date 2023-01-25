/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny;
import alex.ClaseLexica;
import errors.GestionErroresTiny;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorSintacticoTiny {
	private UnidadLexica anticipo;
	private AnalizadorLexicoTiny alex;
	private GestionErroresTiny errores;

	public AnalizadorSintacticoTiny(Reader input) {
		errores = new GestionErroresTiny();
		try {
			alex = new AnalizadorLexicoTiny(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		alex.fijaGestionErrores(errores);
		sigToken();
	}
	
	public void TinyP() {
        switch(anticipo.clase()) {
        case INT:
        case BOOL:
        case REAL:
            Tiny0();
            empareja(ClaseLexica.EOF);
            break;
        case EOF:
            break;
        default:
            errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                    ClaseLexica.INT, ClaseLexica.BOOL, ClaseLexica.REAL, ClaseLexica.EOF);
        }
    }

	public void Tiny0() {
		switch(anticipo.clase()) {
		case INT:
		case BOOL:
		case REAL:
			Declaraciones();
			empareja(ClaseLexica.AMP);
			Instrucciones();
			
			break;
		case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                    ClaseLexica.INT, ClaseLexica.BOOL, ClaseLexica.REAL, ClaseLexica.EOF);   
		}
	}

	private void Declaraciones() {
		switch(anticipo.clase()) {
		case INT:
		case BOOL:
		case REAL:
			Declaracion();
			RDeclaraciones();
			break;
		default:
			errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                    ClaseLexica.INT, ClaseLexica.BOOL, ClaseLexica.REAL);
		}
	}
	
	private void Declaracion() {
		switch(anticipo.clase()) {
		case INT:
		case BOOL:
		case REAL:
			Tipo();
			empareja(ClaseLexica.VAR);
			break;
		default:
			errores.errorSintactico(anticipo.fila(),anticipo.columna(),anticipo.clase(),
                    ClaseLexica.INT, ClaseLexica.BOOL, ClaseLexica.REAL);
		}
	}
	
	private void Tipo() {
		switch(anticipo.clase()) {
		case INT:
			empareja(ClaseLexica.INT);
			break;
		case REAL:
			empareja(ClaseLexica.REAL);
			break;
		case BOOL:
			empareja(ClaseLexica.BOOL);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), 
					ClaseLexica.BOOL, ClaseLexica.INT, ClaseLexica.REAL);
		}
	}

	private void RDeclaraciones() {
		switch(anticipo.clase()) {
		case PC:
			empareja(ClaseLexica.PC);
			Declaracion();
			RDeclaraciones();
			break;
		case AMP:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), 
					ClaseLexica.PC, ClaseLexica.AMP);
		}
	}

	private void Instrucciones() {
		switch(anticipo.clase()) {
		case VAR:
			Instruccion();
			RInstrucciones();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), 
					ClaseLexica.VAR);
		}
	}
	
	private void Instruccion() {
		switch(anticipo.clase()) {
		case VAR:
			empareja(ClaseLexica.VAR);
			empareja(ClaseLexica.ASIG);
			E0();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), 
					ClaseLexica.VAR);
		}
	}

	private void RInstrucciones() {
		switch(anticipo.clase()) {
		case PC:
			empareja(ClaseLexica.PC);
			Instruccion();
			RInstrucciones();
			break;
		case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PC,
					ClaseLexica.EOF);
		}
	}
	
	private void E0() {
		switch(anticipo.clase()) {
		case PAP: case MENOS: case LIT_INT: case LIT_REAL: case VAR: case FALSE: case NOT: case TRUE:
			E1();
			RE0();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PAP,
					ClaseLexica.MENOS, ClaseLexica.LIT_INT, ClaseLexica.LIT_REAL,
					ClaseLexica.VAR, ClaseLexica.FALSE, ClaseLexica.NOT, ClaseLexica.TRUE);
		}
	}
	
	private void RE0() {
		switch(anticipo.clase()) {
		case MAS:
			empareja(ClaseLexica.MAS);
			E0();
			break;
		case MENOS:
			empareja(ClaseLexica.MENOS);
			E1();
			break;
		case PCIE: case PC: case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), 
					ClaseLexica.MAS, ClaseLexica.MENOS);
		}
	}

	private void E1() {
		switch(anticipo.clase()) {
		case PAP: case MENOS: case LIT_INT: case LIT_REAL: case VAR: case FALSE: case NOT: case TRUE:
			E2();
			RE1();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PAP,
					ClaseLexica.MENOS, ClaseLexica.MENOS, ClaseLexica.LIT_INT, ClaseLexica.LIT_REAL,
					ClaseLexica.VAR, ClaseLexica.FALSE, ClaseLexica.NOT, ClaseLexica.TRUE);
		}
	}
	
	private void RE1() {
		switch(anticipo.clase()) {
		case AND: case OR:
			OPBOOL();
			E2();
			RE1();
			break;
		case PCIE: case MAS: case MENOS: case PC: case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.OR,
					ClaseLexica.AND);
		}
	}

	private void E2() {
		switch(anticipo.clase()) {
		case PAP: case MENOS: case LIT_INT: case LIT_REAL: case VAR: case FALSE: case NOT: case TRUE:
			E3();
			RE2();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PAP,
					ClaseLexica.MENOS, ClaseLexica.LIT_INT, ClaseLexica.LIT_REAL,
					ClaseLexica.VAR, ClaseLexica.FALSE, ClaseLexica.NOT, ClaseLexica.TRUE);
		}
	}

	private void RE2() {
		switch(anticipo.clase()) {
		case LT: case ASIG: case GT: case DIFF: case GTE: case LTE: case SAME:
			OPREL();
			E3();
			RE2();
			break;
		case PCIE: case MAS: case MENOS: case PC: case AND: case OR: case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.SAME,
					ClaseLexica.DIFF, ClaseLexica.LTE, ClaseLexica.LT, ClaseLexica.ASIG, ClaseLexica.GT,
					ClaseLexica.GTE);
		}
	}

	private void E3() {
		switch(anticipo.clase()) {
		case PAP: case MENOS: case LIT_INT: case LIT_REAL: case VAR: case FALSE: case NOT: case TRUE:
			E4();
			RE3();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PAP,
					ClaseLexica.MENOS, ClaseLexica.LIT_INT, ClaseLexica.LIT_REAL,
					ClaseLexica.VAR, ClaseLexica.FALSE, ClaseLexica.NOT, ClaseLexica.TRUE);
		}
	}

	private void RE3() {
		switch(anticipo.clase()) {
		case MUL: case DIV:
			OP3();
			E4();
			break;
		case PCIE: case MAS: case MENOS: case PC: case LT: case ASIG: case GT: case DIFF:
		case GTE: case LTE: case SAME: case AND: case OR: case EOF:
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MUL,
					ClaseLexica.DIV);
		}
	}

	private void E4() {
		switch(anticipo.clase()) {
		case MENOS:
			empareja(ClaseLexica.MENOS);
			E5();
			break;
		case NOT:
			empareja(ClaseLexica.NOT);
			E4();
			break;
		case PAP: case LIT_INT: case LIT_REAL: case VAR: case FALSE: case TRUE:
			E5();
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.PAP,
					ClaseLexica.MENOS, ClaseLexica.LIT_INT, ClaseLexica.LIT_REAL,
					ClaseLexica.VAR, ClaseLexica.FALSE, ClaseLexica.NOT, ClaseLexica.TRUE);
		}
	}
	
	private void E5() {
		switch(anticipo.clase()) {
		case VAR:
			empareja(ClaseLexica.VAR);
			break;
		case LIT_INT:
			empareja(ClaseLexica.LIT_INT);
			break;
		case LIT_REAL:
			empareja(ClaseLexica.LIT_REAL);
			break;
		case TRUE:
			empareja(ClaseLexica.TRUE);
			break;
		case FALSE:
			empareja(ClaseLexica.FALSE);
			break;
		case PAP:
			empareja(ClaseLexica.PAP);
			E0();
			empareja(ClaseLexica.PCIE);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.VAR,
					ClaseLexica.LIT_INT, ClaseLexica.LIT_REAL, ClaseLexica.TRUE, ClaseLexica.FALSE,
					ClaseLexica.PAP);
		}
	}

	private void OPREL() {
		switch(anticipo.clase()) {
		case LT:
			empareja(ClaseLexica.LT);
			break;
		case GT:
			empareja(ClaseLexica.GT);
			break;
		case ASIG:
			empareja(ClaseLexica.ASIG);
			break;
		case GTE:
			empareja(ClaseLexica.GTE);
			break;
		case LTE:
			empareja(ClaseLexica.LTE);
			break;
		case SAME:
			empareja(ClaseLexica.SAME);
			break;
		case DIFF:
			empareja(ClaseLexica.DIFF);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.SAME,
					ClaseLexica.DIFF, ClaseLexica.LTE, ClaseLexica.LT, ClaseLexica.ASIG, ClaseLexica.GT,
					ClaseLexica.GTE);
		}
	}

	private void OPBOOL() {
		switch(anticipo.clase()) {
		case AND:
			empareja(ClaseLexica.AND);
			break;
		case OR:
			empareja(ClaseLexica.OR);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.AND,
					ClaseLexica.OR);
		}
	}

	private void OP3() {
		switch(anticipo.clase()) {
		case MUL:
			empareja(ClaseLexica.MUL);
			break;
		case DIV:
			empareja(ClaseLexica.DIV);
			break;
		default:
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), ClaseLexica.MUL,
					ClaseLexica.DIV);
		}
	}	

	private void empareja(ClaseLexica claseEsperada) {
		if (anticipo.clase() == claseEsperada)
			sigToken();
		else
			errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), claseEsperada);
	}

	private void sigToken() {
		try {
			anticipo = alex.sigToken();
		} catch (IOException e) {
			errores.errorFatal(e);
		}
	}

}