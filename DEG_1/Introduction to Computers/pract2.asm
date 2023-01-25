/*-----------------------------------------------------------------
**
**  File:
**    pract2b.asm  
**
**    Introduction to Computers
**    Dpto. de Arquitectura de Computadores y Automática
**    Facultad de Informática. Universidad Complutense de Madrid
**
**  Goal:
**    Sort a vector, A, of positive integers in decreasing order
**	  Give the the result in vector B
**
**  Design notes:
**
**---------------------------------------------------------------*/

.global start

.EQU N, 8

.data
A: .word 7,3,25,4,75,2,1,1

.bss
B:   .space N*4
max: .space 4
ind: .space 4
.text
start:			MOV R0, #0		@i = 0
				LDR R1, =A
				LDR R2, =B
				LDR R3, =max
				LDR R4, =ind
				MOV R5, #0		@j = 0
for1:			CMP R5, #N
				BGE FIN_FOR1
				MOV R6, #0		@ max = 0
for2:			CMP R0, #N
				BGE FIN_FOR2
				LDR R7, [R1, R0, LSL #2]		@R7 = A[i]
				CMP R7, R6
				BLE FIN_IF
				STR R7, R6		@max = A[i]
				STR R8, R0		@ind = i
								@R8 = ind
FIN_IF:			ADD R0, R0, #1
				B for2
FIN_FOR2:		LDR R9, [R2, R5, LSL #2]		@R9 = B[j]
				LDR R10, [R1, R8, LSL #2]		@A[ind]
				STR R9, R10
				MOV R7, #0		@A[ind] = 0
				ADD R5, R5, #1	@j++
				B for1
FIN_FOR1:		B.
				.end
