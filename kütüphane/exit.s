	.intel_syntax noprefix
	.text
	.globl	exit
	.type	exit, @function
exit:
	xor rdi,rdi	# 0
	mov rax,60	# exit (60)
	syscall
	ret

