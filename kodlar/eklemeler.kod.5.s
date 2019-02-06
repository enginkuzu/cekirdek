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
	sub rsp, 0

	# Cümle_01DeğişkenYeni[sayı1 i64 > 1]

	# Cümle_10SabitAtama[255 i64 > 1]
	mov r15,255

	# Cümle_05FonksiyonÇağrısı[printhn(1) > -17]
	mov rax,r15
	call printhn

	# Cümle_03DeğişkenSil[1]

	# Cümle_02GeçiciDeğişkenYeni[i64 > -18]

	# Cümle_10SabitAtama[123 i64 > -18]
	mov r15,123

	# Cümle_05FonksiyonÇağrısı[printhn(-18) > -19]
	mov rax,r15
	call printhn

	# Cümle_03DeğişkenSil[-18]
	leave

	call exit
	ret
