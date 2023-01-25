#include <stdio.h>
#include "44b.h"
#include "button.h"
#include "leds.h"
#include "utils.h"
#include "D8Led.h"
#include "intcontroller.h"
#include "timer.h"
#include "gpio.h"

struct RLstat {
	int moving;
	int speed;
	int iter;
	int direction;
	int position;
};

static struct RLstat RL = {
	.moving = 0,
	.speed = 5,
	.iter = 0,
	.direction = 0,
	.position = 0,
};

void timer_ISR(void) __attribute__ ((interrupt ("IRQ")));
void leds_ISR(void) __attribute__ ((interrupt ("IRQ")));
void countdown_ISR(void) __attribute__ ((interrupt ("IRQ")));
void button_ISR(void) __attribute__ ((interrupt ("IRQ")));
void keyboard_ISR(void) __attribute__ ((interrupt ("IRQ")));

int counter = 0;
int counter2 = 0;

void leds_ISR(void)
{
	counter2++;
	if (counter2 % 2 == 0){
		led1_off();
		led2_off();
	}
	if (counter2 % 2 == 1){
		led1_on();
		led2_on();
	}
	ic_cleanflag(INT_TIMER2);
}

int countdown = 4;

void countdown_ISR(void){
	countdown--;
	if (countdown == 0){
		tmr_stop(TIMER0);
		tmr_stop(TIMER2);
		D8Led_digit(16);
		led1_on();
		led2_on();
	}
	ic_cleanflag(INT_TIMER4);
}

void timer_ISR(void)
{
	// Depending on the Timer:
	// The moving segment must advance 1 position in the direction indicated in the RL structure.
	// The current position is given in the position field.
	// We only want to go through the external segments. So, position must be in the range 0-5.
		counter++;
		D8Led_digit(counter % 16);

	// Clear interrupt flag in the interrupt controller
		ic_cleanflag(INT_TIMER0);
}

int button1Counter = 0;

void button_ISR(void)
{
	unsigned int whicheint = rEXTINTPND;
	unsigned int buttons = (whicheint >> 2) & 0x3;

	// Depending on the Buttons:
	if (buttons & BUT1) {
		button1Counter++;
		if (button1Counter % 2 == 0){
			tmr_stop(TIMER0);
			tmr_stop(TIMER2);
		}
		else{
			tmr_start(TIMER0);
			tmr_start(TIMER2);
		}
	}
	if (buttons & BUT2) {
		// Use led functions defined in leds.h
		// Invert LED2
		// Invert movement state of the moving segment (moving field in RL structure)
		// (note that if we are restarting the movement the speed field must be copied to the iter field)
		tmr_start(TIMER4);
	}


	// eliminamos rebotes
	Delay(2000);
	// Erase the corresponding flag in extintpnd
	rEXTINTPND |= (0x3 << 2); // Erase the interrupt requests in
		         // EXTINTPND writing 1 in the corresponding flags
				 // (the ones corresponding to the buttons that were pressed)
	// COMPLETE: Clear the interrupt flag in the interrupt controller
	ic_cleanflag(21);
}

void keyboard_ISR(void)
{
	int key;

	Delay(200);

	key = kb_scan();

	if (key != -1 && button1Counter % 2 != 0) {
		// COMPLETE: Represent the key pressed in the D8Led display
		if (counter % 16 == key){
			D8Led_digit(10);
		}
		else{
			D8Led_digit(15);
		}

		while ((rPDATG & (0x1 >> 1)) == 0x2);
	}

    Delay(200);

	//COMPLETE: Clear the interrupt for line EINT1 in the interrupt controller (rI_ISPC)
    ic_cleanflag(INT_EINT1);
}

int setup(void)
{
	leds_init();
	D8Led_init();
	D8Led_digit(0);

	/* Port G: Configuration of external interrupts */

	// Use the interface for the G Port defined at gpio.h
	// Configure pins 6 and 7 of Port G for generating external interrupts,
	// configure these interrupts as FALLING edge,
	// and activate the pull-up resistances.

	portG_conf(1, EINT);
	portG_conf(6, EINT);
	portG_conf(7, EINT);

	portG_conf_pup(1, ENABLE);
	portG_conf_pup(6, ENABLE);
	portG_conf_pup(7, ENABLE);

	portG_eint_trig(1, FALLING);
	portG_eint_trig(6, FALLING);
	portG_eint_trig(7, FALLING);

	/********************************************************************/

	/* Configuration of the timer */

	tmr_set_prescaler(0, 255);
	tmr_set_divider(0, D1_8);
	tmr_set_count(TIMER0, 31250, 1); // put something other than 0 (check out) !!!!
	tmr_set_mode(TIMER0, ONE_SHOT); // look it up
	tmr_update(TIMER0);
	tmr_set_mode(TIMER0, RELOAD);

	tmr_set_prescaler(1, 255);
	tmr_set_divider(2, D1_8);
	tmr_set_count(TIMER2, 15625, 1); // put something other than 0 (check out) !!!!
	tmr_set_mode(TIMER2, ONE_SHOT); // look it up
	tmr_update(TIMER2);
	tmr_set_mode(TIMER2, RELOAD);

	tmr_set_prescaler(2, 255);
	tmr_set_divider(4, D1_8);
	tmr_set_count(TIMER4, 31250, 1); // put something other than 0 (check out) !!!!
	tmr_set_mode(TIMER4, ONE_SHOT); // look it up
	tmr_update(TIMER4);
	tmr_set_mode(TIMER4, RELOAD);

	//Use the interface for the Timers defined at timer.h
	// Configure Timer0:
	//      P=255
	//      D=1/8
	//      Initial Count=62500 and any comparison value between 1 and 62499
	//      Update counter to those values
	//      Counter in RELOAD Mode
	//      Stop the counter

	//if (RL.moving)
	/***************************/

	// Register the ISRs for the timer and the push buttons
	pISR_TIMER0 = (int) timer_ISR;
	pISR_TIMER2 = (int) leds_ISR;
	pISR_TIMER4 = (int) countdown_ISR;
	pISR_EINT4567 = (int) button_ISR;
	pISR_EINT1 = (int) keyboard_ISR;

	ic_init();
	// Using the interface defined at intcontroller.h
	//	Enable line IRQ in vectorized mode
	//	Disable line FIQ
	//	Configure line INT_TIMER0 in IRQ mode
	//	Configure line INT_EINT4567 in IRQ mode
	//	Configure line INT_EINT1 in IRQ mode
	//	Enable line INT_TIMER0
	//	Enable line INT_EINT4567
	//	Enable line INT_EINT1
	ic_conf_irq(ENABLE, VEC);
	ic_conf_fiq(DISABLE);
	ic_conf_line(INT_TIMER0, IRQ);
	ic_conf_line(INT_TIMER2, IRQ);
	ic_conf_line(INT_TIMER4, IRQ);
	ic_conf_line(INT_EINT4567, IRQ);
	ic_conf_line(INT_EINT1, IRQ);
	ic_enable(INT_TIMER0);
	ic_enable(INT_TIMER2);
	ic_enable(INT_TIMER4);
	ic_enable(INT_EINT4567);
	ic_enable(INT_EINT1);

	tmr_stop(TIMER0);
	//tmr_stop(TIMER2);
	/***************************************************/

	// Callibrate the delay function
	Delay(0);
	return 0;
}

int loop(void)
{
	return 0;
}


int main(void)
{
	setup();

	while (1) {
		loop();
	}
}
