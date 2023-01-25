library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;

entity SegmentDisplay is
    port (input: in std_logic_vector(2 downto 0);
          output: out std_logic_vector(6 downto 0));
end SegmentDisplay;

architecture codif_prio of SegmentDisplay is

begin
    output <=          "1000000" when (input = "000") else
                       "1001111" when (input = "001") else
                       "0100100" when (input = "010") else
                       "0110000" when (input = "011") else
                       "0011001" when (input = "100") else
                       "0010010" when (input = "101") else
                       "0000010" when (input = "110") else
                       "1111000" when (input = "111") else
                       "1111111";
end codif_prio;