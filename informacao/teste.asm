 section	.data
   pula db 10, 0
	formato db "%d", 0
	text1 db "n eh triangulo ", 10, 0
	text2 db "eh triangulo ", 10, 0
	text3 db "escaleno ", 10, 0
	text4 db "isolceles ", 10, 0
	text5 db "equilatero ", 10, 0

 section .bss
   e1	resd 2
	e2	resd 2
	e3	resd 2
	r	resd 2
	aux1	resd 2
	if1e	resd 2
	if1d	resd 2
	if2e	resd 2
	if2d	resd 2
	if3e	resd 2
	if3d	resd 2
	if4e	resd 2
	if4d	resd 2
	if5e	resd 2
	if5d	resd 2
	if6e	resd 2
	if6d	resd 2
	if7e	resd 2
	if7d	resd 2
	if8e	resd 2
	if8d	resd 2
	if9e	resd 2
	if9d	resd 2
	if10e	resd 2
	if10d	resd 2

 section	.text
   global _main     ;must be declared for linker (ld)
	extern _printf
   extern _scanf

_main:	            ;tells linker entry point
   
    push pula
	call _printf; WRITV escreve variavel

    push pula
	call _printf; WRITV escreve variavel

    push pula
	call _printf; WRITV escreve variavel

    push pula
	call _printf; WRITV escreve variavel

    push pula
	call _printf; WRITV escreve variavel

    mov eax, 2; de
	mov [r], eax; para  ATRIBUICAO
  
    mov eax, [r]
	push eax
	push formato
	call _printf; WRITV escreve variavel

    mov eax, [r]
	push eax
	push formato
	call _printf; WRITV escreve variavel

    mov eax, [r]
	push eax
	push formato
	call _printf; WRITV escreve variavel

    mov eax, [r]
	push eax
	push formato
	call _printf; WRITV escreve variavel

    mov eax, [r]
	push eax
	push formato
	call _printf; WRITV escreve variavel

    mov eax, [r]
	push eax
	push formato
	call _printf; WRITV escreve variavel