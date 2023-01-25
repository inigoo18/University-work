library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;
use IEEE.numeric_std.ALL;

entity contador_mod8 is
    port (clk, reset, E: in STD_LOGIC;
          B: out std_logic_vector(2 downto 0));
end contador_mod8;

architecture bitreg of contador_mod8 is
  
    signal A: std_logic_vector(2 downto 0); -- w(0) => z(0) etc.
begin
    process(clk, reset, A)
    begin
        if reset='1' then
            A <= (others => '0');
        elsif (clk'event and clk = '1') then
            if E = '1' then
                A <= A + 1;
            end if;
        end if;
        B <= A;
    end process;
end bitreg;