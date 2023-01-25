library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;
use IEEE.numeric_std.ALL;

entity mod_comb is
    port (inData: in std_logic_vector(7 downto 0);
          nuevo: out std_logic);
end mod_comb;

architecture modc of mod_comb is
begin
	nuevo <= '1' when inData = "00001111" else
	         '0';
end modc;