
#include "44b.h"
#include "intcontroller.h"

void ic_init(void)
{
	/* Default configuration of the interrupt controller:
	 *    Activate IRQ and FIQ lines
	 *    IRQ line in non-vectorized mode
	 *    Everything through IRQ line
	 *    All interruptions masked
	 **/
	rINTMOD = 0x0; // Configuration of lines as IRQ
	rINTCON = 0x7; // IRQ and FIQ masked, IRQ in non-vectorized mode
	rINTMSK = ~(0x0); // Mask all the lines
}

int ic_conf_irq(enum enable st, enum int_vec vec)
{
	int conf = rINTCON;

	if (st != ENABLE && st != DISABLE)
		return -1;

	if (vec == VEC)
		// Set bit 2 of conf to 0, for establishing IRQ line in vectorized mode
		conf &= ~(0x1 << 2);
	else
		// Set bit 2 of conf to 1, for establishing IRQ line in non-vectorized mode
		conf |= (0x1 << 2);
	if (st == ENABLE)
		// Set bit 1 of conf to 0, for enabling IRQ line
		conf &= ~(0x1 << 1);

	else
		// Set bit 1 of conf to 1, for masking IRQ line
		conf |= (0x1 << 1);

	rINTCON = conf;
	return 0;
}

int ic_conf_fiq(enum enable st)
{
	int ret = 0;

	if (st == ENABLE)
		// Set bit 0 of rINTCON to 0, for enabling FIQ line
		rTCON &= ~(0x1);
	else if (st == DISABLE)
		// Set bit 0 of rINTCON to 1, for masking FIQ line
		rTCON |= (0x1);
	else
		ret = -1;

	return ret;
}

int ic_conf_line(enum int_line line, enum int_mode mode)
{
	unsigned int bit = INT_BIT(line);

	if (line < 0 || line > 26)
		return -1;

	if (mode != IRQ || mode != FIQ)
		return -1;

	if (mode == IRQ)
		// Set bit "bit" of rINTMOD to 0, for establishing line "line" in IRQ mode
		rINTMOD &= ~(bit);
	else
		// Set bit "bit" of rINTMOD to 1, for establishing line "line" in FIQ mode
		rINTMOD |= (bit);
	return 0;
}

int ic_enable(enum int_line line)
{
	if (line < 0 || line > 26)
		return -1;

	// Set bit of rINTMSK corresponding to line "line" to 0, for enabling interruptions through that line
	rINTMSK &= ~(0x1 << 26);
	rINTMSK &= ~(INT_BIT(line));
	return 0;
}

int ic_disable(enum int_line line)
{
	if (line < 0 || line > 26)
		return -1;

	// Set bit of rINTMSK corresponding to line "line" to 1, for masking interruptions through that line
	rINTMSK |= (INT_BIT(line));
	return 0;
}

int ic_cleanflag(enum int_line line)
{
	int bit;

	if (line < 0 || line > 26)
		return -1;

	bit = INT_BIT(line);

	if (rINTMOD & line)
		// Erase interruption flag corresponding to line "line" with line configured through FIQ
		rF_ISPC |= bit;
	else
		// Erase interruption flag corresponding to line "line" with line configured through IRQ
		rI_ISPC |= bit;
	return 0;
}
