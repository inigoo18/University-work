package alex;
import asint.ClaseLexica;
public class ALexOperations {
	private AnalizadorLexicoTiny alex;
	
	public ALexOperations(AnalizadorLexicoTiny alex) {
		this.alex = alex;
	}

	public UnidadLexica unidadId() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.ID, alex.lexema());
	}

	public UnidadLexica unidadLitEnt() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LIT_ENT, alex.lexema());
	}
	
	public UnidadLexica unidadLitReal() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LIT_REAL, alex.lexema());
	}
	
	public UnidadLexica unidadLitString() {
		return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LIT_STRING, alex.lexema());
	}
	
	public UnidadLexica unidadMas() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAS);
	}
	
	public UnidadLexica unidadMenos() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENOS);
	}
	
	public UnidadLexica unidadAmpDoble() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.AMP_DOBLE);
	}
	
	public UnidadLexica unidadMul() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MUL);
	}
	
	public UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIV);
	}
	
	public UnidadLexica unidadPorcentaje() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PORCENTAJE);
	}
	
	public UnidadLexica unidadLT() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LT);
	}
	
	public UnidadLexica unidadGT() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.GT);
	}
	
	public UnidadLexica unidadLTE() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LTE);
	}
	
	public UnidadLexica unidadGTE() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.GTE);
	}
	
	public UnidadLexica unidadSame() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.SAME);
	}
	
	public UnidadLexica unidadDiff() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIFF);
	}
	
	public UnidadLexica unidadPap() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PAP);
	}
	
	public UnidadLexica unidadPcie() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PCIE);
	}
	
	public UnidadLexica unidadPC() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PC);
	}
	
	public UnidadLexica unidadAsig() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ASIG);
	}
	
	public UnidadLexica unidadCcap() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CCAP);
	}

	public UnidadLexica unidadCccie() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CCCIE);
	}
	
	public UnidadLexica unidadLlap() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LLAP);
	}
	
	public UnidadLexica unidadLlcie() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.LLCIE);
	}
	
	public UnidadLexica unidadPunto() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
	}
	
	public UnidadLexica unidadFlecha() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.FLECHA);
	}
	
	public UnidadLexica unidadComa() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.COMA);
	}
	
	public UnidadLexica unidadAmp() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.AMP);
	}
	
	public UnidadLexica unidadInt() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.INT);
	}
	
	public UnidadLexica unidadReal() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.REAL);
	}
	
	public UnidadLexica unidadBool() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BOOL);
	}
	
	public UnidadLexica unidadString() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.STRING);
	}
	
	public UnidadLexica unidadAnd() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.AND);
	}
	
	public UnidadLexica unidadOr() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.OR);
	}
	
	public UnidadLexica unidadNot() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NOT);
	}
	
	public UnidadLexica unidadNull() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NULL);
	}
	
	public UnidadLexica unidadTrue() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TRUE);
	}
	
	public UnidadLexica unidadFalse() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.FALSE);
	}
	
	public UnidadLexica unidadProc() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PROC);
	}
	
	public UnidadLexica unidadIf() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.IF);
	}
	
	public UnidadLexica unidadThen() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.THEN);
	}
	
	public UnidadLexica unidadElse() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ELSE);
	}
	
	public UnidadLexica unidadEndIf() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ENDIF);
	}
	
	public UnidadLexica unidadWhile() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.WHILE);
	}
	
	public UnidadLexica unidadDo() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DO);
	}
	
	public UnidadLexica unidadEndWhile() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ENDWHILE);
	}
	
	public UnidadLexica unidadCall() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CALL);
	}
	
	public UnidadLexica unidadRecord() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.RECORD);
	}
	
	public UnidadLexica unidadArray() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ARRAY);
	}

	public UnidadLexica unidadOf() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.OF);
	}
	
	public UnidadLexica unidadPointer() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.POINTER);
	}
	
	public UnidadLexica unidadNew() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NEW);
	}
	
	public UnidadLexica unidadDelete() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DELETE);
	}
	
	public UnidadLexica unidadRead() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.READ);
	}
	
	public UnidadLexica unidadWrite() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.WRITE);
	}
	
	public UnidadLexica unidadNl() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NL);
	}
	
	public UnidadLexica unidadVar() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.VAR);
	}
	
	public UnidadLexica unidadType() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TYPE);
	}
	
	public UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.EOF);
	}
}
