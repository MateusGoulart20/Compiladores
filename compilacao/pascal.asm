 section .data
	pula db 10, 0
	formato db "%d", 0
	text1 db "", 10, 0
	text2 db "", 10, 0

 section .bss
	base	resd 2
	i	resd 2
	v	resd 2
	fi	resd 2
	aux	resd 2
	cont	resd 2
	fv	resd 2
	fiv	resd 2
	result	resd 2
	aux1	resd 2
	aux2	resd 2
	while1e	resd 2
	while1d	resd 2
	while2e	resd 2
	while2d	resd 2
	while3e	resd 2
	while3d	resd 2
	while4e	resd 2
	while4d	resd 2
	while5e	resd 2
	while5d	resd 2

 section .text
	global _main
	extern _printf
	extern _scanf

_main:
	push base
	push formato
	call _scanf

	mov eax, 0; de
	mov [i], eax; para  ATRIBUICAO

	while_in_1:; ROTULO

	mov eax, [i]; de
	mov [while1e], eax; para  ATRIBUICAO

	mov eax, [base]; de
	mov [while1d], eax; para  ATRIBUICAO

	mov ebx, [while1e]
	mov ecx, [while1d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae while_out_1;salto se condicao atendida

	mov eax, 0; de
	mov [v], eax; para  ATRIBUICAO

	while_in_2:; ROTULO

	mov eax, [v]; de
	mov [while2e], eax; para  ATRIBUICAO

	mov ebx, [i]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [while2d], eax; para  ATRIBUICAO

	mov ebx, [while2e]
	mov ecx, [while2d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae while_out_2;salto se condicao atendida

	mov eax, 1; de
	mov [fi], eax; para  ATRIBUICAO

	mov ebx, [i]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [aux], eax; para  ATRIBUICAO

	mov eax, 1; de
	mov [cont], eax; para  ATRIBUICAO

	while_in_3:; ROTULO

	mov eax, [cont]; de
	mov [while3e], eax; para  ATRIBUICAO

	mov eax, [aux]; de
	mov [while3d], eax; para  ATRIBUICAO

	mov ebx, [while3e]
	mov ecx, [while3d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae while_out_3;salto se condicao atendida

	mov ax, [fi]
	mov bx, [cont]
	mul bx
	mov [aux1], eax; MULT

	mov eax, [aux1]; de
	mov [fi], eax; para  ATRIBUICAO

	mov ebx, [cont]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [cont], eax; para  ATRIBUICAO

	jmp while_in_3 ; JMP

	while_out_3:; ROTULO

	mov eax, 1; de
	mov [fv], eax; para  ATRIBUICAO

	mov ebx, [v]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [aux], eax; para  ATRIBUICAO

	mov eax, 1; de
	mov [cont], eax; para  ATRIBUICAO

	while_in_4:; ROTULO

	mov eax, [cont]; de
	mov [while4e], eax; para  ATRIBUICAO

	mov eax, [aux]; de
	mov [while4d], eax; para  ATRIBUICAO

	mov ebx, [while4e]
	mov ecx, [while4d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae while_out_4;salto se condicao atendida

	mov ax, [fv]
	mov bx, [cont]
	mul bx
	mov [aux1], eax; MULT

	mov eax, [aux1]; de
	mov [fv], eax; para  ATRIBUICAO

	mov ebx, [cont]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [cont], eax; para  ATRIBUICAO

	jmp while_in_4 ; JMP

	while_out_4:; ROTULO

	mov eax, 1; de
	mov [fiv], eax; para  ATRIBUICAO

	mov ebx, [i]
	mov ecx, [v]
	sub ebx, ecx
	mov [aux1], ebx; SUBT 

	mov ebx, [aux1]
	mov ecx, 1
	add ebx, ecx
	mov [aux2], ebx; SOMA

	mov eax, [aux2]; de
	mov [aux], eax; para  ATRIBUICAO

	mov eax, 1; de
	mov [cont], eax; para  ATRIBUICAO

	while_in_5:; ROTULO

	mov eax, [cont]; de
	mov [while5e], eax; para  ATRIBUICAO

	mov eax, [aux]; de
	mov [while5d], eax; para  ATRIBUICAO

	mov ebx, [while5e]
	mov ecx, [while5d]; posicionamento condicao
	cmp ebx, ecx ;comparacao
	jae while_out_5;salto se condicao atendida

	mov ax, [fv]
	mov bx, [cont]
	mul bx
	mov [aux1], eax; MULT

	mov eax, [aux1]; de
	mov [fv], eax; para  ATRIBUICAO

	mov ebx, [cont]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [cont], eax; para  ATRIBUICAO

	jmp while_in_5 ; JMP

	while_out_5:; ROTULO

	mov ax, [fv]
	mov bx, [fiv]
	mul bx
	mov [aux1], eax; MULT

	mov eax, [fi]
	mov edx, 0
	mov ecx, [aux1]
	div ecx
	mov [aux2], eax; DIVD 

	mov eax, [aux2]; de
	mov [result], eax; para  ATRIBUICAO

	mov eax, [result]
	push eax
	push formato
	call _printf; WRITV escreve variavel

	push text1
	call _printf; WRITS escreve string

	mov ebx, [v]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [v], eax; para  ATRIBUICAO

	jmp while_in_2 ; JMP

	while_out_2:; ROTULO

	push text2
	call _printf; WRITS escreve string

	mov ebx, [i]
	mov ecx, 1
	add ebx, ecx
	mov [aux1], ebx; SOMA

	mov eax, [aux1]; de
	mov [i], eax; para  ATRIBUICAO

	jmp while_in_1 ; JMP

	while_out_1:; ROTULO

