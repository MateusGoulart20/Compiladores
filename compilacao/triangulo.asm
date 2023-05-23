 section .data
	pula db 10, 0
	formato db "%d", 0
	text1 db "primeiro lado ", 10, 0
	text2 db "segundo lado ", 10, 0
	text3 db "terceiro lado ", 10, 0
	text4 db "n eh triangulo ", 10, 0
	text5 db "eh triangulo ", 10, 0
	text6 db "escaleno ", 10, 0
	text7 db "isolceles ", 10, 0
	text8 db "equilatero ", 10, 0

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

 section .text
	global _main
	extern _printf
	extern _scanf

_main:
	push text1
	call _printf; WRITS escreve string

	push e1
	push formato
	call _scanf

	push text2
	call _printf; WRITS escreve string

	push e2
	push formato
	call _scanf

	push text3
	call _printf; WRITS escreve string

	push e3
	push formato
	call _scanf

	mov eax, 3; de
	mov [r], eax; para  ATRIBUICAO

	mov eax, [e1]; de
	mov [if1e], eax; para  ATRIBUICAO

	mov ebx, [e3]
	mov ecx, [e2]
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [if1d], eax; para  ATRIBUICAO

	mov ebx, [if1e]
	mov ecx, [if1d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae if_out_false_1;salto se condicao atendida

	mov ebx, [r]
	mov ecx, 1
	sub ebx, ecx
	mov [aux1], ebx; SUBT 

	mov eax, [aux1]; de
	mov [r], eax; para  ATRIBUICAO

	if_out_false_1:; ROTULO

	mov eax, [e3]; de
	mov [if2e], eax; para  ATRIBUICAO

	mov ebx, [e1]
	mov ecx, [e2]
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [if2d], eax; para  ATRIBUICAO

	mov ebx, [if2e]
	mov ecx, [if2d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae if_out_false_2;salto se condicao atendida

	mov ebx, [r]
	mov ecx, 1
	sub ebx, ecx
	mov [aux1], ebx; SUBT 

	mov eax, [aux1]; de
	mov [r], eax; para  ATRIBUICAO

	if_out_false_2:; ROTULO

	mov eax, [e2]; de
	mov [if3e], eax; para  ATRIBUICAO

	mov ebx, [e3]
	mov ecx, [e1]
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [if3d], eax; para  ATRIBUICAO

	mov ebx, [if3e]
	mov ecx, [if3d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae if_out_false_3;salto se condicao atendida

	mov ebx, [r]
	mov ecx, 1
	sub ebx, ecx
	mov [aux1], ebx; SUBT 

	mov eax, [aux1]; de
	mov [r], eax; para  ATRIBUICAO

	if_out_false_3:; ROTULO

	mov eax, 0; de
	mov [if4e], eax; para  ATRIBUICAO

	mov eax, [r]; de
	mov [if4d], eax; para  ATRIBUICAO

	mov ebx, [if4e]
	mov ecx, [if4d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae if_out_false_4;salto se condicao atendida

	push text4
	call _printf; WRITS escreve string

	jmp if_out_3 ; JMP

	if_out_false_4:; ROTULO

	push text5
	call _printf; WRITS escreve string

	mov eax, [e2]; de
	mov [if5e], eax; para  ATRIBUICAO

	mov eax, [e3]; de
	mov [if5d], eax; para  ATRIBUICAO

	mov ebx, [if5e]
	mov ecx, [if5d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jne if_out_false_5;salto se condicao atendida

	mov ebx, [r]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [r], eax; para  ATRIBUICAO

	if_out_false_5:; ROTULO

	mov eax, [e1]; de
	mov [if6e], eax; para  ATRIBUICAO

	mov eax, [e3]; de
	mov [if6d], eax; para  ATRIBUICAO

	mov ebx, [if6e]
	mov ecx, [if6d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jne if_out_false_6;salto se condicao atendida

	mov ebx, [r]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [r], eax; para  ATRIBUICAO

	if_out_false_6:; ROTULO

	mov eax, [e2]; de
	mov [if7e], eax; para  ATRIBUICAO

	mov eax, [e1]; de
	mov [if7d], eax; para  ATRIBUICAO

	mov ebx, [if7e]
	mov ecx, [if7d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jne if_out_false_7;salto se condicao atendida

	mov ebx, [r]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [r], eax; para  ATRIBUICAO

	if_out_false_7:; ROTULO

	mov eax, [r]; de
	mov [if8e], eax; para  ATRIBUICAO

	mov eax, 1; de
	mov [if8d], eax; para  ATRIBUICAO

	mov ebx, [if8e]
	mov ecx, [if8d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae if_out_false_8;salto se condicao atendida

	push text6
	call _printf; WRITS escreve string

	if_out_false_8:; ROTULO

	mov eax, [r]; de
	mov [if9e], eax; para  ATRIBUICAO

	mov eax, 1; de
	mov [if9d], eax; para  ATRIBUICAO

	mov ebx, [if9e]
	mov ecx, [if9d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jne if_out_false_9;salto se condicao atendida

	push text7
	call _printf; WRITS escreve string

	if_out_false_9:; ROTULO

	mov eax, 1; de
	mov [if10e], eax; para  ATRIBUICAO

	mov eax, [r]; de
	mov [if10d], eax; para  ATRIBUICAO

	mov ebx, [if10e]
	mov ecx, [if10d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae if_out_false_10;salto se condicao atendida

	push text8
	call _printf; WRITS escreve string

	if_out_false_10:; ROTULO

	if_out_3:; ROTULO

