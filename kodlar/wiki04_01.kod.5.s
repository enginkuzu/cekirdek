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

	# Cümle_01DeğişkenYeni[değer2 i64 > 2]

	# Cümle_05SabitAtama[222 i64 > 2]
	mov r14,222

	# Cümle_01DeğişkenYeni[sayı1 i64 > 3]

	# Cümle_06DeğişkenAtama[2 > 3]
	mov r13,r14

	# Cümle_02DeğişkenSil[2]

	# Cümle_04FonksiyonÇağrısı[printhn(3) > -1]
	mov rax,r13
	call printhn

	# Cümle_02DeğişkenSil[3]

	call exit
	ret
