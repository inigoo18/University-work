library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.std_logic_unsigned.all;
use IEEE.numeric_std.ALL;

entity teclado is
    port (clk, rst: in std_logic;
          data: in std_logic;
          result: out std_logic_vector(1 downto 0));
end teclado;

architecture tec of teclado is

    component Reg_despl
        port (PS2Clk, PS2Data, rst: in STD_LOGIC;
          datos: out std_logic_vector(7 downto 0);
          leds: out std_logic_vector(7 downto 0));
    end component;
    component mod_comb
        port (inData: in std_logic_vector(7 downto 0);
          nuevo: out std_logic);
    end component;
    component traductorteclado
        port (inTecla: in std_logic_vector(7 downto 0);
          finTecla: in std_logic;
          direccion: out std_logic_vector(1 downto 0));
    end component;
    
    -- despl
    signal desp_clk, desp_data, desp_rst: std_logic;
    signal desp_datos, desp_leds : std_logic_vector(7 downto 0);
    
    -- comb
    signal comb_nuevo: std_logic;
    signal comb_data: std_logic_vector(7 downto 0);
    
    -- teclado
    signal tecl_inTecla: std_logic_vector(7 downto 0);
    signal tecl_finTecla: std_logic;
    signal tecl_direccion: std_logic_vector(1 downto 0);
    
    
begin
    
    despl: Reg_despl port map (desp_clk, desp_data, desp_rst, desp_datos, desp_leds);
    comb: mod_comb port map (comb_data, comb_nuevo);
    trad: traductorTeclado port map (tecl_inTecla, tecl_finTecla, tecl_direccion);
    
    desp_clk <= CLK;
    desp_rst <= RST;
    desp_data <= DATA;
    comb_data <= desp_datos;
    tecl_finTecla <= comb_nuevo;
    tecl_inTecla <= desp_leds;
    result <= tecl_direccion;
    

end tec;