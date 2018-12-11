	.intel_syntax noprefix
	.text
	.globl	_start
	.type	_start, @function
_start:

	#Cümle_01DeğişkenYeni[sayı1 i32 > 1]

	#Cümle_05SabitTanımlama[255 i32 > -1]
	mov r14,255

	#Cümle_06DeğişkenAtama[-1 > 1]
	mov r15,r14

	#Cümle_02DeğişkenSil[-1]

	#Cümle_04FonksiyonÇağrısı[println(1) > -2]
	mov rax,r15
	call println

	#Cümle_01DeğişkenYeni[sayı2 i32 > 2]

	#Cümle_05SabitTanımlama[15 i32 > -3]
	mov r13,15

	#Cümle_06DeğişkenAtama[-3 > 2]
	mov r14,r13

	#Cümle_02DeğişkenSil[-3]

	#Cümle_04FonksiyonÇağrısı[println(2) > -4]
	mov rax,r14
	call println

	#Cümle_04FonksiyonÇağrısı[println(2) > -5]
	mov rax,r14
	call println

	#Cümle_01DeğişkenYeni[sayı3 i32 > 3]

	#Cümle_05SabitTanımlama[255 i32 > -6]
	mov r12,255

	#Cümle_06DeğişkenAtama[-6 > 3]
	mov r13,r12

	#Cümle_02DeğişkenSil[-6]

	#Cümle_04FonksiyonÇağrısı[println(3) > -7]
	mov rax,r13
	call println

	#Cümle_01DeğişkenYeni[sayı4 i32 > 4]

	#Cümle_05SabitTanımlama[15 i32 > -8]
	mov rbx,15

	#Cümle_06DeğişkenAtama[-8 > 4]
	mov r12,rbx

	#Cümle_02DeğişkenSil[-8]

	#Cümle_04FonksiyonÇağrısı[println(4) > -9]
	mov rax,r12
	call println

	#Cümle_06DeğişkenAtama[1 > 2]
	mov r14,r15

	#Cümle_06DeğişkenAtama[4 > 2]
	mov r14,r12

	#Cümle_02DeğişkenSil[4]

	#Cümle_03Operatörİşlemi[1 + 2 > -10]
	mov r12,r15
	add r12,r14

	#Cümle_06DeğişkenAtama[-10 > 3]
	mov r13,r12

	#Cümle_02DeğişkenSil[-10]

	#Cümle_04FonksiyonÇağrısı[println(3) > -11]
	mov rax,r13
	call println

	#Cümle_03Operatörİşlemi[1 - 2 > -12]
	mov r12,r15
	sub r12,r14

	#Cümle_06DeğişkenAtama[-12 > 3]
	mov r13,r12

	#Cümle_02DeğişkenSil[-12]

	#Cümle_04FonksiyonÇağrısı[println(3) > -13]
	mov rax,r13
	call println

	#Cümle_03Operatörİşlemi[1 * 2 > -14]
	mov rax,r15
	mul r14
	mov r12,rax

	#Cümle_06DeğişkenAtama[-14 > 3]
	mov r13,r12

	#Cümle_02DeğişkenSil[-14]

	#Cümle_04FonksiyonÇağrısı[println(3) > -15]
	mov rax,r13
	call println

	#Cümle_04FonksiyonÇağrısı[println(1) > -16]
	mov rax,r15
	call println

	#Cümle_04FonksiyonÇağrısı[println(2) > -17]
	mov rax,r14
	call println

	#Cümle_03Operatörİşlemi[1 // 2 > -18]
	mov rdx,0
	mov rax,r15
	div r14
	mov r12,rax

	#Cümle_02DeğişkenSil[2]

	#Cümle_02DeğişkenSil[1]

	#Cümle_06DeğişkenAtama[-18 > 3]
	mov r13,r12

	#Cümle_02DeğişkenSil[-18]

	#Cümle_04FonksiyonÇağrısı[println(3) > -19]
	mov rax,r13
	call println

	#Cümle_02DeğişkenSil[3]

	call exit
	ret
