	.intel_syntax noprefix

	.section	.data

	enter:
	.octa	0
	.byte	10

	.section	.rodata

	.hex:
	.string	"0123456789ABCDEF"

	.section	.text

	.globl	exit
exit:
	xor rdi,rdi	# 0
	mov rax,60	# exit (60)
	syscall
	ret

	.globl	printhn
printhn:
	mov rcx, 0
döngü:
	rol rax, 4
	mov rdx, rax
	and rdx, 0x0F
	mov dil, [.hex+rdx]
	mov [enter+rcx], dil
	inc rcx
	cmp rcx, 16
	jnz döngü

	mov rdx, 17		# 
	lea	rsi, enter	# 
	mov rdi, 1		# file descriptor : 1 - standard output
	mov rax, 1		# system call : write (1)
	syscall			# Call the kernel
	ret

	.globl	_start
_start:
	push rbp
	mov rbp, rsp
	sub rsp, 24

	# Cümle_01DeğişkenYeni[değer1 i64 > 1]

	# Cümle_05SabitAtama[11 i64 > 1]
	mov r15,11

	# Cümle_01DeğişkenYeni[değer2 i64 > 2]

	# Cümle_05SabitAtama[22 i64 > 2]
	mov r14,22

	# Cümle_01DeğişkenYeni[değer3 i64 > 3]

	# Cümle_05SabitAtama[33 i64 > 3]
	mov r13,33

	# Cümle_01DeğişkenYeni[değer4 i64 > 4]

	# Cümle_05SabitAtama[44 i64 > 4]
	mov r12,44

	# Saklaç-Yığıt : 1 < r12
	mov QWORD PTR -24[rbp], r12

	# Cümle_01DeğişkenYeni[değer5 i64 > 5]

	# Cümle_05SabitAtama[55 i64 > 5]
	mov r12,55

	# Saklaç-Yığıt : 2 < r12
	mov QWORD PTR -16[rbp], r12

	# Cümle_01DeğişkenYeni[sayı1 i64 > 6]

	# Saklaç-Yığıt : 3 < r13
	mov QWORD PTR -8[rbp], r13

	# Cümle_01DeğişkenYeni[sayı2 i64 > 7]

	# Cümle_05SabitAtama[0 i64 > 7]
	mov r13,0

	# Cümle_03Operatörİşlemi[2 + 1 > 6]
	mov r12, r14
	add r12, r15

	# Cümle_04FonksiyonÇağrısı[printhn(6) > -1]
	mov rax,r12
	call printhn

	# Cümle_03Operatörİşlemi[2 - 1 > 6]
	mov r12, r14
	sub r12, r15

	# Cümle_04FonksiyonÇağrısı[printhn(6) > -2]
	mov rax,r12
	call printhn

	# Cümle_03Operatörİşlemi[2 * 1 > 6]
	mov rax, r14
	mul r15
	mov r12, rax

	# Cümle_04FonksiyonÇağrısı[printhn(6) > -3]
	mov rax,r12
	call printhn

	# Cümle_03Operatörİşlemi[2 // 1 > 6]
	mov rdx, 0
	mov rax, r14
	div r15
	mov r12, rax

	# Cümle_04FonksiyonÇağrısı[printhn(6) > -4]
	mov rax,r12
	call printhn

	# Cümle_02DeğişkenSil[6]

	# Cümle_03Operatörİşlemi[7 + 1 > 7]
	mov r13, r13
	add r13, r15

	# Cümle_02DeğişkenSil[1]

	# Cümle_03Operatörİşlemi[7 + 2 > 7]
	mov r13, r13
	add r13, r14

	# Cümle_02DeğişkenSil[2]

	# Yığıt-Saklaç : r14 < 3
	mov r14, QWORD PTR -8[rbp]

	# Cümle_03Operatörİşlemi[7 + 3 > 7]
	mov r13, r13
	add r13, r14

	# Cümle_02DeğişkenSil[3]

	# Yığıt-Saklaç : r14 < 1
	mov r14, QWORD PTR -24[rbp]

	# Cümle_03Operatörİşlemi[7 + 4 > 7]
	mov r13, r13
	add r13, r14

	# Cümle_02DeğişkenSil[4]

	# Yığıt-Saklaç : r14 < 2
	mov r14, QWORD PTR -16[rbp]

	# Cümle_03Operatörİşlemi[7 + 5 > 7]
	mov r13, r13
	add r13, r14

	# Cümle_02DeğişkenSil[5]

	# Cümle_04FonksiyonÇağrısı[printhn(7) > -5]
	mov rax,r13
	call printhn

	# Cümle_02DeğişkenSil[7]
	leave

	call exit
	ret
