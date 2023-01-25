library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;

-- INFIERE RAM DISTRIBUIDA

entity MemRAM is
    port (clkFPGA, rst, we: in std_logic;
          data: in std_logic_vector(11 downto 0);
          addr: in integer range 0 to 7;
          dOut: out std_logic_vector(11 downto 0));
end MemRAM;

architecture memoria of MemRAM is
    type memo is array(0 to 7) of std_logic_vector(11 downto 0);
    --signal ram_block: memo := (others=> (others => '0'));
    signal ram_block: memo := ("010110100000", "001100000000", "000011001100", "000000110010", "110010001000", "010000000011", "010010110100", "000000000000"); 
begin
    process(clkFPGA, RST, ram_block, addr)
    begin
        if (RST = '1') then
            ram_block <= (others=> (others => '0'));
        elsif (clkFPGA'event and clkFPGA = '1') then
            if (we = '1') then
                ram_block(addr) <= data;
            end if;
        end if;
        dOut <= ram_block(addr);
    end process;
end memoria;