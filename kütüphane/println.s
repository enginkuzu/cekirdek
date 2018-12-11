	.intel_syntax noprefix
	.data
	.globl	enter
	.type	enter, @object
	.size	enter, 17
enter:
	.octa	0
	.byte	10
	.section	.rodata
.hex:
	.string	"0123456789ABCDEF"
	.text
	.globl	println
	.type	println, @function
println:

	mov rcx,0
döngü:
	rol rax,4
	mov rdx,rax
	and rdx,0x0F
	mov dil,[.hex+rdx]
	mov [enter+rcx],dil
	inc rcx
	cmp rcx,16
	jnz döngü

	mov rdx,17		# 
	lea	rsi,enter	# 
	mov rdi,1		# file descriptor : 1 - standard output
	mov rax,1		# system call : write (1)
	syscall			# Call the kernel
	ret

