library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;

entity DivisorFreq is
    port (clkFPGA, rst: in std_logic;
          clkOut: out std_logic);
end DivisorFreq;

architecture divfreq of DivisorFreq is

    signal cuentaMax: std_logic_vector(16 downto 0);
    signal cuenta: std_logic_vector(16 downto 0);
    signal clkState : std_logic;
begin

    cuentaMax <= (others => '1');
    process(clkFPGA, rst)
    begin
        if rst='1' then
            cuenta <= (others => '0');
            clkState <= '0';
        elsif (clkFPGA'event and clkFPGA = '1') then
            if cuenta = cuentaMax then
                cuenta <= (others => '0');
                clkState <= not clkState;
            else
                cuenta <= cuenta + '1';
            end if;
        end if;
    end process;
    clkOut <= clkState;
end divfreq;