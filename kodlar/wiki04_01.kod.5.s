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

	.globl	println
println:
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

	# Cümle_01DeğişkenYeni[sayı1 i64 > 1]

	# Cümle_05SabitTanımlama[255 i64 > 1]
	mov r14,255

	# Cümle_04FonksiyonÇağrısı[println(1) > -1]
	mov rax,r14
	call println

	# Cümle_01DeğişkenYeni[sayı2 i64 > 2]

	# Cümle_05SabitTanımlama[15 i64 > 2]
	mov r12,15

	# Cümle_04FonksiyonÇağrısı[println(2) > -2]
	mov rax,r12
	call println

	# Cümle_04FonksiyonÇağrısı[println(2) > -3]
	mov rax,r12
	call println

	# Cümle_01DeğişkenYeni[sayı3 i64 > 3]

	# Cümle_05SabitTanımlama[255 i64 > 3]
	mov r9,255

	# Cümle_04FonksiyonÇağrısı[println(3) > -4]
	mov rax,r9
	call println

	# Cümle_01DeğişkenYeni[sayı4 i64 > 4]

	# Cümle_05SabitTanımlama[15 i64 > 4]
	mov r10,15

	# Cümle_04FonksiyonÇağrısı[println(4) > -5]
	mov rax,r10
	call println

	# Cümle_06DeğişkenAtama[1 > 2]
	mov r12,r14

	# Cümle_06DeğişkenAtama[4 > 2]
	mov r12,r10

	# Cümle_02DeğişkenSil[4]

	# Cümle_03Operatörİşlemi[1 + 2 > 3]
	mov r9, r14
	add r9, r12

	# Cümle_04FonksiyonÇağrısı[println(3) > -6]
	mov rax,r9
	call println

	# Cümle_03Operatörİşlemi[1 - 2 > 3]
	mov r9, r14
	sub r9, r12

	# Cümle_04FonksiyonÇağrısı[println(3) > -7]
	mov rax,r9
	call println

	# Cümle_03Operatörİşlemi[1 * 2 > 3]
	mov rax, r14
	mul r12
	mov r9, rax

	# Cümle_04FonksiyonÇağrısı[println(3) > -8]
	mov rax,r9
	call println

	# Cümle_04FonksiyonÇağrısı[println(1) > -9]
	mov rax,r14
	call println

	# Cümle_04FonksiyonÇağrısı[println(2) > -10]
	mov rax,r12
	call println

	# Cümle_03Operatörİşlemi[1 // 2 > 3]
	mov rdx, 0
	mov rax, r14
	div r12
	mov r9, rax

	# Cümle_02DeğişkenSil[2]

	# Cümle_02DeğişkenSil[1]

	# Cümle_04FonksiyonÇağrısı[println(3) > -11]
	mov rax,r9
	call println

	# Cümle_02DeğişkenSil[3]

	call exit
	ret
