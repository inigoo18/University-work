library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;
use IEEE.numeric_std.ALL;


entity VGA is
    port
    (
        RST: in std_logic; -- reset FPGA
        CLK: in std_logic; -- reloj FPGA
        HSYNCB: buffer std_logic; -- horizontal (line) sync
        VSYNCB: out std_logic; -- vertical (frame) sync
        RGB: out std_logic_vector(11 downto 0); -- 4 red, 4 green,4 blue colors
        
        INTEC_CLK: in std_logic; -- clk del teclado
        INTEC_DATA: in std_logic; -- data del teclado
        
        an: out std_logic_vector(3 downto 0); -- para el 7-segment
        
        seg: out std_logic_vector (6 downto 0) -- número de rebotes contra la barra
        );
end VGA;

architecture vgacore_arch of VGA is

    signal hcnt: std_logic_vector(11 downto 0);
    signal vcnt: std_logic_vector(11 downto 0);
    signal frontera : std_logic; -- señal para la frontera de la pantalla
    signal inside : std_logic; -- el rectangulo de dentro que va cambiando de color
    signal barra, clkBarra, pelota, clkPelota: std_logic; -- señales para dibujar barra/pelota + sus relojes
    
    type BARRA_ESTADOS is (ESTATICO, ARRIBA, ABAJO);
    signal BARRA_ESTADO, SIG_BARRA_ESTADO: BARRA_ESTADOS; -- maquina de estados de la barra
    
    signal posBarraYTOP, posBarraYBOT, posBarraXLEFT, posBarraXRIGHT: std_logic_vector(11 downto 0); -- posiciones de la barra
    
    type PELOTA_ESTADOS is (ARRI, ARRD, ABAI, ABAD);
    signal PELOTA_ESTADO, SIG_PELOTA_ESTADO: PELOTA_ESTADOS; -- maquina de estados de la pelota
    
    signal posPelotaYTOP, posPelotaYBOT, posPelotaXLEFT, posPelotaXRIGHT: std_logic_vector(11 downto 0); -- posiciones de la pelota
    
    signal movTeclado: std_logic_vector(1 downto 0); -- movimiento del teclado: 00 estático, 01 arriba, 10 abajo
    signal tec_clk, tec_rst, tec_data : std_logic; -- señales para el teclado
    
    signal div_clk, div_rst: std_logic; -- señales para el divisor de freq
    
    signal cont_clk, cont_clk2, cont_rst, cont_rst2, cont_enable, cont_enable2: std_logic; -- señales para los contadores
    signal cont_output, cont_output2: std_logic_vector(2 downto 0); -- señales para los contadores
    
    signal mem_clk, mem_we, mem_rst: std_logic; -- señales para la memoria
    signal mem_data, mem_out: std_logic_vector(11 downto 0);
    signal mem_addr: integer range 0 to 7;
    signal mem_cntr: std_logic_vector(2 downto 0); -- contador indicando que color mostrar
    
    component teclado -- COMPONENTE DE TECLADO
        port (clk, rst: in std_logic;
          data: in std_logic;
          result: out std_logic_vector(1 downto 0));
    end component;
    
    component DivisorFreq -- DIVISOR DE FREC. para la pelota
        port (clkFPGA, rst: in std_logic;
          clkOut: out std_logic);
    end component;
    
    component contador_mod8 -- CONTADOR MOD8 para el contador de rebotes y color de fondo
    port (clk, reset, E: in STD_LOGIC;
          B: out std_logic_vector(2 downto 0));
    end component;
    
    component SegmentDisplay -- SEGMENTDISPLAY es un modulo combinacional sencillo para definir lo que tiene que mostrar el segment display en funcion del número de rebotes
    port (input: in std_logic_vector(2 downto 0);
          output: out std_logic_vector(6 downto 0));
    end component;
    
    component MemRAM --MEMRAM es una memoria distribuida para el color de fondo
    port (clkFPGA, rst, we: in std_logic;
          data: in std_logic_vector(11 downto 0);
          addr: in integer range 0 to 7;
          dOut: out std_logic_vector(11 downto 0));
    end component;

begin

    Tec: teclado port map (tec_clk, tec_rst, tec_data, movTeclado);
    Div: DivisorFreq port map (div_clk, div_rst, clkPelota);
    Cont: contador_mod8 port map (cont_clk, cont_rst, cont_enable, cont_output);
    Cont2: contador_mod8 port map (cont_clk2, cont_rst2, cont_enable2, cont_output2);
    Prio: SegmentDisplay port map (cont_output, seg);
    Mem: MemRAM port map (mem_clk, mem_rst, mem_we, mem_data, mem_addr, mem_out); 

    tec_clk <= INTEC_CLK;
    tec_data <= INTEC_DATA;
    div_clk <= CLK; -- reloj fpga
    div_rst <= RST; -- rst fpga
    cont_rst <= RST; -- rst fpga
    cont_enable <= '1'; -- enable de los contadores siempre a '1'. Los relojes son los que cuentan
    cont_enable2 <= '1';
    
    mem_clk <= CLK; -- reloj fpga
    mem_rst <= '0'; -- nunca reseteamos la memoria
    mem_we <= '0'; -- nunca escribimos la memoria
    
    an <= "1110"; -- solo mostramos el segment más a la derecha

    A: process(CLK,RST)
    begin
        -- reset asynchronously clears pixel counter
        if RST='1' then
            hcnt <= "000000000000";
        -- horiz. pixel counter increments on rising edge of dot clock
        elsif (CLK'event and CLK='1') then
            -- horiz. pixel counter rolls-over after 381 pixels
            if hcnt<1687 then
                hcnt <= hcnt + 1;
            else
                hcnt <= "000000000000";
            end if;
        end if;
    end process;
   
    B: process(hsyncb,RST)
    begin
        -- reset asynchronously clears line counter
        if RST='1' then
            vcnt <= "000000000000";
        -- vert. line counter increments after every horiz. line
        elsif (hsyncb'event and hsyncb='1') then
            -- vert. line counter rolls-over after 528 lines
            if vcnt<1065 then
                vcnt <= vcnt + 1;
            else
                vcnt <= "000000000000";
            end if;
        end if;
    end process;
   
    C: process(CLK,RST)
    begin
        -- reset asynchronously sets horizontal sync to inactive
        if RST='1' then
            hsyncb <= '1';
        -- horizontal sync is recomputed on the rising edge of every dot clock
        elsif (CLK'event and CLK='1') then
            -- horiz. sync is low in this interval to signal start of a new line
            if (hcnt>=1327 and hcnt<1439) then
                hsyncb <= '0';
            else
                hsyncb <= '1';
            end if;
        end if;
    end process;
   
    D: process(hsyncb,RST)
    begin
        -- reset asynchronously sets vertical sync to inactive
        if RST='1' then
            vsyncb <= '1';
        -- vertical sync is recomputed at the end of every line of pixels
        elsif (hsyncb'event and hsyncb='1') then
            -- vert. sync is low in this interval to signal start of a new frame
            if (vcnt>=1024 and vcnt<1027) then
                vsyncb <= '0';
            else
                vsyncb <= '1';
            end if;
        end if;
    end process;
    
    ------------------------------------------------
    ------------------- BARRA ----------------------
    
    barra_FSM_sincrono: process(CLK, RST, BARRA_ESTADO, SIG_BARRA_ESTADO) -- process para pasar al siguente estado de la maquina de estados de la barra
    begin
        if RST='1' then
            BARRA_ESTADO <= ESTATICO;
        elsif (CLK'event and CLK = '1') then
            BARRA_ESTADO <= SIG_BARRA_ESTADO;
        end if;    
    end process barra_FSM_sincrono;
    
    barra_FSM_cambioest: process(BARRA_ESTADO, SIG_BARRA_ESTADO, posBarraYTOP, posBarraYBOT) -- maquina de estados de la barra
    begin
        case BARRA_ESTADO is -- 3 estados:
            when ESTATICO => -- ESTATICO: no se mueve
                if (movTeclado = "01" and posBarraYTOP > 60) then -- si movTeclado = arriba y no estamos en el tope de la pantalla
                    tec_rst <= '0'; -- ponemos el rst del teclado a 0 para ponerlo luego a 1
                    clkBarra <= '0'; -- no necesitamos refrescar todavia
                    SIG_BARRA_ESTADO <= ARRIBA;
                elsif (movTeclado = "10" and posBarraYBOT < 980) then -- si movTeclado = abajo y no estamos al fondo de la pantalla
                    tec_rst <= '0';-- ponemos el rst del teclado a 0 para ponerlo luego a 1
                    clkBarra <= '0'; -- no necesitamos refrescar todavia
                    SIG_BARRA_ESTADO <= ABAJO;
                else
                    tec_rst <= '0'; -- si no, nos quedamos en estatico
                    clkBarra <= '0';
                    SIG_BARRA_ESTADO <= ESTATICO;
                end if;
            when ARRIBA | ABAJO => -- ARRIBA o ABAJO
                tec_rst <= '1'; -- reset al teclado para que no reciba más datos
                clkBarra <= '1'; -- ponemos el clk a 1 para refrescar
                SIG_BARRA_ESTADO <= ESTATICO; -- pasamos a estático
        end case;
    end process barra_FSM_cambioest;
    
       -------------------------------------------------
       ------------------- PELOTA ----------------------
   
   pelota_FSM_sincrono: process(CLK, RST, PELOTA_ESTADO, SIG_PELOTA_ESTADO) -- process para pasar al siguente estado de la maquina de estados de la pelota
    begin
        if RST='1' then
            PELOTA_ESTADO <= ABAI;
        elsif (CLK'event and CLK = '1') then
            PELOTA_ESTADO <= SIG_PELOTA_ESTADO;
        end if;    
    end process pelota_FSM_sincrono;
    
    pelota_FSM_cambioest: process(PELOTA_ESTADO, SIG_PELOTA_ESTADO, movTeclado, posBarraYTOP)
    begin
        case PELOTA_ESTADO is -- 4 estados:
            when ABAI => -- ABAJO A LA IZQ.
                if (posPelotaXLEFT <= 10) then -- si chocamos contra el lado izq
                    SIG_PELOTA_ESTADO <= ABAD; -- ABAJO A LA DER.
                    cont_clk <= '0'; -- no contamos (segment display)
                    cont_clk2 <= '0'; -- no contamos (color de fondo)
                elsif (posPelotaYBOT >= 1000) then -- si chocamos contra abajo del todo
                    SIG_PELOTA_ESTADO <= ARRI; -- ARRIBA A LA IZQ.
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                elsif (posBarraXLEFT = posPelotaXLEFT and posPelotaYTOP > posBarraYTOP and posPelotaYBOT < posBarraYBOT) then -- si golpeamos contra la barra
                    -- BARRA GOLPEA LA PELOTA
                    SIG_PELOTA_ESTADO <= ABAD; -- ABAJO A LA DER.
                    cont_clk <= '1'; -- +1 al contador de rebotes
                    cont_clk2 <= '1'; -- +1 al color de fondo
                else
                    SIG_PELOTA_ESTADO <= ABAI; -- si no nos quedamos y no hacemos nada
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                end if;
            when ABAD => -- ABAJO A LA DER.
                if (posPelotaXRIGHT >= 1270) then -- choque contra el lado derecho
                    SIG_PELOTA_ESTADO <= ABAI;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                elsif (posPelotaYBOT >= 1000) then -- choque contra el lado de abajo
                    SIG_PELOTA_ESTADO <= ARRD;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                else
                    SIG_PELOTA_ESTADO <= ABAD;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                end if;
            when ARRI => -- ARRIBA A LA IZQ
                if (posPelotaXLEFT <= 10) then -- choque conra el lado izq
                    SIG_PELOTA_ESTADO <= ARRD;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                elsif (posPelotaYTOP <= 15) then -- choque contra el lado de arriba
                    SIG_PELOTA_ESTADO <= ABAI;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                elsif (posBarraXLEFT = posPelotaXLEFT and posPelotaYTOP > posBarraYTOP and posPelotaYBOT < posBarraYBOT) then -- choque contra la barra
                    -- BARRA GOLPEA LA PELOTA
                    cont_clk <= '1';
                    cont_clk2 <= '1';
                    SIG_PELOTA_ESTADO <= ARRD;
                else
                    SIG_PELOTA_ESTADO <= ARRI;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                end if;
            when ARRD => -- ARRIBA A LA DER
                if (posPelotaXRIGHT >= 1270) then -- choque contra el lado derecho
                    SIG_PELOTA_ESTADO <= ARRI;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                elsif (posPelotaYTOP <= 15) then -- choque contra el lado de arriba
                    SIG_PELOTA_ESTADO <= ABAD;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                else
                    SIG_PELOTA_ESTADO <= ARRD;
                    cont_clk <= '0';
                    cont_clk2 <= '0';
                end if;
        end case;
    end process pelota_FSM_cambioest;
    
    
    ------------------------------- MEMORIA ---------------------------------
    -------------------------------------------------------------------------
    escribeMemoria: process(mem_clk, RST, mem_cntr) -- la memoria ya está escrita desde el componente MemRAM
    begin
        if (RST = '1') then -- si reset a 1, reinicializamos el contador de color de fondo
            cont_rst2 <= '1';
        elsif (mem_clk'event and mem_clk = '1') then -- para cada CLK de la fpga (= mem_clk)
            if (cont_output2 = "111") then -- si contador esta en 7 = negro
                cont_rst2 <= '1'; -- reseteamos el segundo contador para que no enseñe el negro
            else
                cont_rst2 <= '0'; -- si no seguimos
            end if;
            mem_addr <= to_integer(unsigned(cont_output2)); -- el color de fondo corresponde al contador
        end if;
    end process;
    
    --------------------------------------DIBUJO-------------------------------------------------
    ---------------------------------------------------------------------------------------------
    
    barra_REFRESCO: process(clkBarra, RST) -- proceso de refresco de la barra
    begin
        if (RST = '1') then -- si el rst está a 1
            posBarraYTOP <= std_logic_vector(to_unsigned(400, posBarraYTOP'length)); -- reinicializamos posiciones
            posBarraYBOT <= std_logic_vector(to_unsigned(650, posBarraYBOT'length));
            posBarraXLEFT <= std_logic_vector(to_unsigned(20, posBarraXLEFT'length));
            posBarraXRIGHT <= std_logic_vector(to_unsigned(25, posBarraXRIGHT'length));
        elsif (clkBarra'event and clkBarra = '1') then
            if BARRA_ESTADO = ABAJO then -- si nos movemos hacia abajo
                posBarraYTOP <= posBarraYTOP + 50;
                posBarraYBOT <= posBarraYBOT + 50;
           else -- si no nos movemos hacia arriba (podemos usar else porque clkBarra solo es = 1 cuando está en el estado de ABAJO o ARRIBA)
                posBarraYTOP <= posBarraYTOP - 50;
                posBarraYBOT <= posBarraYBOT - 50;
            end if;
        end if;
    end process barra_REFRESCO;
    
    
    pelota_REFRESCO: process(clkPelota, RST) -- proceso de refresco de memoria
    begin
        if (RST = '1') then
            posPelotaYTOP <= std_logic_vector(to_unsigned(20, posPelotaYTOP'length)); -- reposicionamos la pelota
            posPelotaYBOT <= std_logic_vector(to_unsigned(40, posPelotaYBOT'length));
            posPelotaXLEFT <= std_logic_vector(to_unsigned(700, posPelotaXLEFT'length));
            posPelotaXRIGHT <= std_logic_vector(to_unsigned(715, posPelotaXRIGHT'length));
        elsif (clkPelota'event and clkPelota = '1') then -- con el reloj de la pelota (= divisor de frecuencia)
            if PELOTA_ESTADO = ABAI then -- si el estado es abajo a la izquierda..
                posPelotaYTOP <= posPelotaYTOP + 1;
                posPelotaYBOT <= posPelotaYBOT + 1;
                posPelotaXLEFT <= posPelotaXLEFT - 1;
                posPelotaXRIGHT <= posPelotaXRIGHT - 1;
           elsif PELOTA_ESTADO = ABAD then -- si el estado es abajo a la der..
                posPelotaYTOP <= posPelotaYTOP + 1;
                posPelotaYBOT <= posPelotaYBOT + 1;
                posPelotaXLEFT <= posPelotaXLEFT + 1;
                posPelotaXRIGHT <= posPelotaXRIGHT + 1;
           elsif PELOTA_ESTADO = ARRI then-- si el estado es arriba a la izquierda..
                posPelotaYTOP <= posPelotaYTOP - 1;
                posPelotaYBOT <= posPelotaYBOT - 1;
                posPelotaXLEFT <= posPelotaXLEFT - 1;
                posPelotaXRIGHT <= posPelotaXRIGHT - 1;
           else -- si el estado es arriba a la der..
                posPelotaYTOP <= posPelotaYTOP - 1;
                posPelotaYBOT <= posPelotaYBOT - 1;
                posPelotaXLEFT <= posPelotaXLEFT + 1;
                posPelotaXRIGHT <= posPelotaXRIGHT + 1;
            end if;
        end if;
    end process pelota_REFRESCO;
    

    -- A partir de aqui escribir la parte de dibujar en la pantalla
    dibujaFrontera : process(hcnt,vcnt)
    begin
        if ((hcnt > 0) and (hcnt < 1280))then
            if((vcnt > 0) and (vcnt < 1020)) then
                frontera <='1'; -- ponemos señal a 1 para la pantalla entera
            else
                frontera <='0';
            end if;
        else
            frontera <='0';
        end if;
    end process dibujaFrontera;
   
    dibujaInterior : process(hcnt,vcnt)
    begin
        if ((hcnt > 10 ) and (hcnt < 1270)) then
            if ((vcnt > 15) and (vcnt < 1000)) then
                inside <='1'; -- ponemos señal a 1 para el rectangulo de fondo
            else
                inside <='0';
            end if;
        else
            inside <='0';
        end if;
    end process dibujaInterior;
   
    dibujaBarra : process(hcnt,vcnt, posBarraYTOP, posBarraYBOT, posBarraXRIGHT, posBarraXLEFT)
    begin
        if ((hcnt >= posBarraXLEFT ) and (hcnt < posBarraXRIGHT))then
            if ((vcnt >= posBarraYTOP) and (vcnt < posBarraYBOT)) then
                barra <='1'; -- ponemos señal a 1 para mostrar la barra
            else
                barra <='0';
            end if;
        else
            barra <='0';
        end if;
    end process dibujaBarra;
    
    dibujaPelota : process(hcnt,vcnt, posPelotaYTOP, posPelotaYBOT, posPelotaXRIGHT, posPelotaXLEFT)
    begin
        if ((hcnt >= posPelotaXLEFT ) and (hcnt < posPelotaXRIGHT))then
            if ((vcnt >= posPelotaYTOP) and (vcnt < posPelotaYBOT)) then
                pelota <='1'; -- ponemos señal a 1 para mostrar la pelota
            else
                pelota <='0';
            end if;
        else
            pelota <='0';
        end if;
    end process dibujaPelota;
   
    dibujarTodo : process(frontera, inside, barra)
    begin
         if barra = '1' then
            rgb <= "111100000000";
         elsif pelota = '1' then
            rgb <= "000000001111";
         elsif inside = '1' then
            rgb <= mem_out; -- el color de fondo depende de la salida de la memoria.
         elsif frontera = '1' then
            rgb <= "111111111111";
        else
            rgb <= "000000000000";
        end if;
    end process dibujarTodo;
end vgacore_arch;
