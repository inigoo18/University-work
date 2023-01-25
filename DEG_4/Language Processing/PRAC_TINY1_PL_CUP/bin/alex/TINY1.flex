package alex;

import errors.GestionErroresTiny;

%%
%cup
%line
%column
%class AnalizadorLexicoTiny
%type UnidadLexica
%unicode
%public

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}

letra  = ([A-Z]|[a-z])
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
subrayado = \_
decimal = {digito}* {digitoPositivo}
exp = [e | E]
exponencial = exp entero

separador = [ \t\r\b\n]
comentario = #[^\n]* 

identificador = {letra}({letra}|{digito}|{subrayado})*
litEnt = [\+,\-]?({digitoPositivo}{digito}* | 0)
litReal = litEnt((\.{decimal}{exponencial}?)|{exponencial})
litString = \"[^\b\r\n]*\"
mas = \+
menos = \-
ampDoble = \&\&
mul = \*
div = \/
porcentaje = \%
lt = \<
gt = \>
lte = \<\=
gte = \>\=
same = \=\=
diff = \!\=
pap = \(
pcie = \)
pc = \;
asig = \=
ccap = \[
cccie = \]
llap = \{
llcie = \}
punto = \.
flecha = \-\>
coma = \,
amp = \&
int = int
real = real
bool = bool
string = string
and = and
or = or
not = not
null = null
true = true
false = false
proc = proc
if = if
then = then
else = else
endif = endif
while = while
do = do
endwhile = endwhile
call = call
record = record
array = array
of = of
pointer = pointer
new = new
delete = delete
read = read
write = write
nl = nl
var = var
type = type
EOF = \$


%%
{separador}               {}
{comentario}              {}
{EOF}           {return ops.unidadEof();}
{litEnt}            {return ops.unidadLitEnt();}
{litReal}              {return ops.unidadLitReal();}
{litString}              {return ops.unidadLitString();}
{mas}            {return ops.unidadMas();}
{menos}           {return ops.unidadMenos();}
{ampDoble}              {return ops.unidadAmpDoble();}
{mul}  			{return ops.unidadMul();}
{div}        	{return ops.unidadDiv();}
{porcentaje}              {return ops.unidadPorcentaje();}
{lt}              {return ops.unidadLT();}
{gt}              {return ops.unidadGT();}
{lte}              {return ops.unidadLTE();}
{gte}              {return ops.unidadGTE();}
{same}              {return ops.unidadSame();}
{diff}              {return ops.unidadDiff();}
{pap}              {return ops.unidadPap();}
{pcie}              {return ops.unidadPcie();}
{pc}              {return ops.unidadPC();}
{asig}              {return ops.unidadAsig();}
{ccap}              {return ops.unidadCcap();}
{cccie}              {return ops.unidadCccie();}
{llap}              {return ops.unidadLlap();}
{llcie}              {return ops.unidadLlcie();}
{punto}              {return ops.unidadPunto();}
{flecha}              {return ops.unidadFlecha();}
{coma}              {return ops.unidadComa();}
{amp}              {return ops.unidadAmp();}
{int}              {return ops.unidadInt();}
{real}              {return ops.unidadReal();}
{bool}              {return ops.unidadBool();}
{string}              {return ops.unidadString();}
{and}              {return ops.unidadAnd();}
{or}              {return ops.unidadOr();}
{not}              {return ops.unidadNot();}
{null}              {return ops.unidadNull();}
{true}              {return ops.unidadTrue();}
{false}              {return ops.unidadFalse();}
{proc}              {return ops.unidadProc();}
{if}              {return ops.unidadIf();}
{then}              {return ops.unidadThen();}
{else}              {return ops.unidadElse();}
{endif}              {return ops.unidadEndIf();}
{while}              {return ops.unidadWhile();}
{do}              {return ops.unidadDo();}
{endwhile}              {return ops.unidadEndWhile();}
{call}              {return ops.unidadCall();}
{record}              {return ops.unidadRecord();}
{array}              {return ops.unidadArray();}
{of}              {return ops.unidadOf();}
{pointer}              {return ops.unidadPointer();}
{new}              {return ops.unidadNew();}
{delete}              {return ops.unidadDelete();}
{read}              {return ops.unidadRead();}
{write}              {return ops.unidadWrite();}
{nl}              {return ops.unidadNl();}
{var}              {return ops.unidadVar();}
{type}              {return ops.unidadType();}
{identificador}           {return ops.unidadId();}
[^]                       {errores.errorLexico(fila(), columna(),lexema());}  