library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;
use IEEE.numeric_std.ALL;

entity traductorTeclado is
    port (inTecla: in std_logic_vector(7 downto 0);
          finTecla: in std_logic;
          direccion: out std_logic_vector(1 downto 0));
end traductorTeclado;

architecture tecl of traductorTeclado is
begin
	direccion <= "01" when inTecla = "10101110" and finTecla = '1' else -- arriba
	             "10" when inTecla = "01001110" and finTecla = '1' else -- abajo
	             "00";
end tecl;