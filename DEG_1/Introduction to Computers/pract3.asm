/*-----------------------------------------------------------------
**
**  File:
**    pract3b.asm
**
**    Introduction to Computers
**    Dpto. de Arquitectura de Computadores y Automática
**    Facultad de Informática. Universidad Complutense de Madrid
**
**  Goal:
**    Implement a program that sorts a vector, A, of positive integers, of length N
**    and stores the result in vector B
**	  The program must use the function "max" implemented in prac3a.
**
**  Design notes:
**
**---------------------------------------------------------------*/

.global start
.extern _stack

.EQU N, 8

.data
A:         .word 7,3,25,4,75,100,1,1	@ Vector to be sorted

.bss
B:         .space N*4			@ Sorted vector
ind:       .space 4				@ variable "ind" of the C code


.text
/* This program sorts a vector, A, of positive integers, of length N
   and stores the result in vector B */

start:	ldr sp, =_stack		@ initialize the SP with the address provided in the linker script
		mov	fp, #0			@ initialize the FP
		mov r8, #0			@ j value to execute N times.
		ldr r9, =A			@ Preparing the call to subroutine max
		mov r1,#N			@ 2nd parameter (length of vector)
for2:	cmp r8, r1
		bge end_for2
       	mov	r0,r9			@ 1st parameter (address of A)
       	mov r1,#N			@ 2nd parameter (length of vector)
       	bl  max
       	ldr r2,=ind
       	str r0,[r2]			@ store the return value
       	ldr r10, [r9, r0, lsl #2]	@ this is A[i].
       	ldr r4, =B			@ r4 takes address of B instead of i
       	str r10, [r4, r8, lsl #2]
       	mov r10, #0
       	str r10, [r9, r0, lsl #2]
       	add r8, r8, #1
       	b for2

end_for2:b	.



/* subroutine max (leaf subroutine)
-----------------------------------------------------------------------
Function:		Finds the position of the maximum value of a vector of postitive integers
Parameters IN	r0: starting address of the vector to be traversed
				r1: vector length
Parameters OUT	r0: position of the maximum element

--------------------------------------------------------------------
*/

		@ complete the rest of the code

max:	push {r4-r7, fp, lr}
		add fp, sp, #4*5
		mov r6, #0 // max = 0
		mov r7, #0 // ind = 0
		mov r4, #0
for:	cmp r4, r1 // for i = 0 to N
		bge end_for
		ldr r5, [r0, r4, lsl#2] // A[i]
		cmp r6, r5 // we compare max and A[i]
		bge noMax  // max isnt smaller than A[i]
		mov r6, r5 // max = A[i]
		mov r7, r4
noMax:	add r4, r4, #1
		b for
end_for: mov r0, r7
		sub fp, sp, #4*5
		pop {r4-r7, fp, lr}
		bx lr


.end
