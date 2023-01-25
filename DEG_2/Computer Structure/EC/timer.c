#include "44b.h"
#include "timer.h"

int tmr_set_prescaler(int p, int  value)
{
	int offset = p*8;
	value &= 0xFF;

	if (p < 0 | p > 3)
		return -1;

	// Write in register rTCFG0 the value "value" from position "offset" on
	// for establishing the pre-scaling value
	rTCFG0 &= ~(0XFF << offset);
	rTCFG0 |= (value << offset);
	return 0;
}

int tmr_set_divider(int d, enum tmr_div div)
{
	int ret = 0;
	int pos = d*4;

	if ((d < 0 || d > 5) ||
			(div == D1_32 && d > 3) ||
			(div == EXTCLK && d != 5) ||
			(div == TCLK && d != 4))
		return -1;

	if (div == EXTCLK || div == TCLK)
		div = 4;

	// Write in register rTCFG1 the value "div" from position "pos" on
	// for establishing the division factor
	rTCFG1 &= ~(0XF << pos);
	rTCFG1 |= (div << pos);

	return 0;
}

int tmr_set_count(enum tmr_timer t, int count, int cmp)
{
	int err = 0;
	switch (t) {
		case TIMER0:
			// Establish the value for count to "count" and the value
			// for comparison "cmp" in registers rTCNTB0 and rTCMPB0
			rTCNTB0 = count;
			rTCMPB0 = cmp;
			 break;
		case TIMER1:
			// Establish the value for count to "count" and the value
			// for comparison "cmp" in registers rTCNTB1 and rTCMPB1
			rTCNTB1 = count;
			rTCMPB1 = cmp;
			 break;
		case TIMER2:
			// Establish the value for count to "count" and the value
			// for comparison "cmp" in registers rTCNTB2 and rTCMPB2
			rTCNTB2 = count;
			rTCMPB2 = cmp;
			 break;
		case TIMER3:
			// Establish the value for count to "count" and the value
			// for comparison "cmp" in registers rTCNTB3 and rTCMPB3
			rTCNTB3 = count;
			rTCMPB3 = cmp;
			 break;
		case TIMER4:
			// Establish the value for count to "count" and the value
			// for comparison "cmp" in registers rTCNTB4 and rTCMPB4
			rTCNTB4 = count;
			rTCMPB4 = cmp;
			 break;
		case TIMER5:
			// Establish the value for count to "count" and the value
			// for comparison "cmp" in registers rTCNTB5 and rTCMPB5
			rTCNTB5 = count;
			 break;
		default:
			err = -1;
	}

	return err;
}

int tmr_update(enum tmr_timer t)
{
	int pos = t*4;
	if (t > 0)
		pos += 4;

	if (t < 0 || t > 5)
		return -1;

	// Set to 1 the bit "pos" of register rTCON and after that set it to 0
	rTCON |= (0x1 << (pos + 1));
	rTCON &= ~(0X1 << (pos + 1));
	return 0;
}

int tmr_set_mode(enum tmr_timer t, enum tmr_mode mode)
{
	int err = 0;
	int pos = t*4;
	if (t > 0)
		pos += 4;

	if (t < 0 || t > 5)
		return -1;

	if (mode == ONE_SHOT)
		// Set to 0 bit autoreload from position pos
		rTCON &= ~(0X1 << (pos + 3));
	else if (mode == RELOAD)
		// Set to 1 bit autoreload from position pos
		rTCON |= (0X1 << (pos + 3));
	else
		err = -1;

	return err;
}

int tmr_start(enum tmr_timer t)
{
	int pos = t*4;
	if (t > 0)
		pos += 4;

	if (t < 0 || t > 5)
		return -1;

	// Set to 1 bit of start from position pos in register rTCON
	rTCON |= (0x1 << pos);

	return 0;
}

int tmr_stop(enum tmr_timer t)
{
	int pos = t*4;
	if (t > 0)
		pos += 4;

	if (t < 0 || t > 5)
		return -1;

	// Set to 0 bit of start from position pos in register rTCON
	rTCON &= ~(0x1 << pos);

	return 0;
}

int tmr_isrunning(enum tmr_timer t)
{
	int ret = 0;
	int pos = t*4;
	if (t > 0)
		pos += 4;

	if ((t >= 0) && (t <= 5) 
			&& (rTCON & (0x1 << pos)))
		ret = 1;

	return ret;
}
