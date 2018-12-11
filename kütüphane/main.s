	.intel_syntax noprefix
	.text
	.globl	_start
	.type	_start, @function
_start:
	mov rax,0xABCDEF0123456789
	call println
	call exit
	ret

