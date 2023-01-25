library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;
use IEEE.numeric_std.ALL;

entity Reg_despl is
    port (PS2Clk, PS2Data, rst: in STD_LOGIC;
          datos: out std_logic_vector(7 downto 0);
          leds: out std_logic_vector(7 downto 0));
end Reg_despl;

architecture reg of Reg_despl is
    signal tmp : std_logic_vector(21 downto 0);
begin
    process(PS2Clk, rst)
    begin
        if (rst ='1') then
            tmp <= (others => '0');
            --tmp <= "0000011110000000000000";
            
        elsif (PS2Clk'event and PS2Clk = '0') then
            -- shift left datos
            tmp(21 downto 0) <= tmp(20 downto 0) & PS2Data;
            
          
        end if;
    end process;
    leds <= tmp(9 downto 2);
    datos <= tmp(20 downto 13);
end reg;