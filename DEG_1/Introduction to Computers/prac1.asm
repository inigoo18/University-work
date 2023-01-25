// IÑIGO SANZ AND ALBA GORRINDO

/* Exercise 21
int Px, Py; //coordinates x and y of the fixed point P
int V[2N]; //Vector of N points V=[x0,y0,x1,y1,...]
int D[N]; //Vector of distances
*/
.global start
.extern _stack
@ #define N,5
.equ N, 5
.data
@int Px, Py; //coordinates x and y of the fixed point P
@int V[2N]; //Vector of N points V=[x0,y0,x1,y1,...]
V: .word 1,2,-3,4,5,9,17,-15,20,12 @ 5 points for test purposes
Px: .word 1 @ x coordinate of the fixed point
Py: .word 2 @ y coordinate of the fixed point
.bss

D: 			.space N*4 @ Results vector
.text
start: 		ldr sp,=_stack
			mov fp,#0
@ for (i=0; i < N; i++)
			mov r5, #0 @ r5 = loop counter i
loop1:	 	cmp r5, #N
			bge endloop1 @ if loop exhausted, go to end
@ D[i] = chebyshev(Px, Py, V[2*i], V[2*i + 1]);
			ldr r6,=Px
			ldr r0,[r6] @ 1st parameter for the call
			ldr r6,=Py
			ldr r1,[r6] @ 2nd parameter for the call
			ldr r6,=V @ r6= initial address of V
			mov r7,r5,lsl#1 @ r7 = 2*r5 (= 2*i)
			add r6,r6,r7,lsl#2 @ r6= address of V[2*i]
			// Alternative implementation
			// add r6,r6,r5,lsl#3 @ r6= address of V[2*i]
			ldr r2,[r6] @ 3rd parameter for the call
			add r6,r6,#4 @ r6= address of V[2*i + 1]
			ldr r3,[r6] @ 4th parameter for the call
			bl Cheby
			ldr r1,=D
			str r0,[r1,r5,lsl#2] @store D[i]
			add r5,r5,#1 @ i++
			b loop1
endloop1:	b .


// Subroutine Cheby (non-leaf)
Cheby: 		push {r4-r9,fp,lr} @ PROLOG
			add fp, sp, #4*7
			sub r0, r0, r2
			bl Abs
			mov r8, r0
			sub r0, r1, r3
			bl Abs
			mov r9, r0
if:			cmp r9,r8
			bls else
			mov r8, r9
else:		mov r0, r8
			sub sp, fp, #4*7 @ EPILOG
			pop {r4-r9,fp,lr}
			mov pc, lr
// Subroutine Abs (leaf). Not required as a part of the exercise, but needed to check
// the result in Eclipse
Abs: 		push {fp}
			mov fp, sp
			cmp r0, #0
			bge return3 @ if r0 positive, do nothing
			mov r1,#0
			sub r0,r1,r0 @ if r0 negative, then r0=0-r0
return3:	mov sp, fp
			pop {fp}
			mov pc, lr
			.end
