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
	mov eax, 28796
	mov edx, 0
	mov ecx, 800
	div ecx
	mov ebx, eax
	mov [aux1], ebx
	mov eax, [aux1]
	push eax
	push formato
	call _printf
   
   ;add esp, 4
   ;push ebp
 	;mov ebp,esp
	;push msg
	;call _printf

   ;push banana
   ;call _printf
   ;mov eax, 48 ; 48 ou '0' para o registrador
   ;mov [banana], eax ; move o registrador para a variável
   ;push banana
   
   ; escrevendo msg na tela
;   push msg
;   call _printf ; escreve o resultado
   
   ; lendo banana na tela como inteiro (pizza)   
;   push banana
;   push pizza
;   call _scanf
   
;   jmp _L1
;_L1:
;   push sorvete 
;   call _printf
   
;   push banana
;   call _printf ; escreve o resultado
   
;   mov eax, 1; 1 para o registrador
;   add [banana], eax ; adicioba o registrador para a variável
   
;   mov eax, [banana]
;   cmp eax, 54
;   jl _L1
   
  ; push sorvete 
   ;call _printf
   ; MULTIPLICACAO
   ;mov eax, 9
   ;mov [multa], eax
   ;mov ecx , 9;mov eax, 9
   ;mov edx , 9;mov [multb], eax
   ;mov ebx , 9;mov [multc], eax
   ;jmp _mult_in_1
;_mult_in_1:
   ;mov eax, [multc]
   ;cmp ebx, 0 ;cmp eax, 0
   ;je _mult_out_1
   ; somar
   ;mov eax, [multb]
   ;add ecx , edx ;add [multa], eax
   ; decrementar
   ; mov eax, [multc]
   ;dec ebx ;dec eax
   ;mov [multc], eax
   ;jmp _mult_in_1
;_mult_out_1:
   ;mov eax,8
   ;mov [banana] , eax
   ;mov al, [banana]
   ;mov bl, [banana]
   ;mul bl
   ;mov [multa], ax
   ;mov eax , 4584
   ;mov [banana] , eax




   ;mov edx , [banana]
   ;mov bx , 1000
 ;  jmp _write_num_1
;_write_num_1:
   ;mov ax, [banana]
   ;div bx
   ;mov [multa], ax
   ;add ecx, 48
   
   ;mov eax, 48579
   ;mov [multa], eax
   ;push eax
   ;push pizza
   ;call _printf
   ;TESTE DE IDENTACAO DO ROTULO
   ;mov ebx, 9
   ;mov ecx, 8
   ;cmp ebx, ecx
   ;jl rodolfo

   ;push msg1
   ;call _printf

   ;rodolfo:

   ;push msg1
   ;call _printf
   
   

   ;push sorvete 
   ;call _printf
  
  ;push banana
   ;call _printf ; escreve o resultado
   
   ;mov eax, 1; 1 para o registrador
   ;add [banana], eax ; adicioba o registrador para a variável
   
   ;mov eax, [banana]
   ;cmp eax, 54
   ;jl rodolfo

   ;mov ax, [multa]
   ;sub eax, 6524
   ;push eax
   ;push pizza
   ;call _printf

   ; DIVISAO
   ;mov ax,60001 ;preparing the dividend
	;mov dx,0 ;zero extension
	;mov cx,10000 ;preparing the divisor
   ;div cx
   ;mov [banana], ax
   ;mov [multa], dx

   ;mov ax, [banana]
   ;add ax, 48
   ;mov [banana], ax
   ;mov ax, 6
   ;mov [multa], ax
   ; IMPRESSAO DE NUMERO
   ;mov eax, [multa]
   ;push eax
   ;push pizza
   ;call _printf

   ;push msg1
   ;call _printf

   ;push sorvete
   ;call _printf
   
   ;push multa
   ;push pizza
   ;call _printf
   
   
   ;push sorvete
   ;call _printf
   
   ;push multa
   ;push lasanha
   ;call _printf
   
   ;mov ax, [multa]
   ;sub ax, 40000
   ;mov [banana], ax
   
   ;push banana
   ;call _printf 

   
   ;call _printf
   ;mov [multa], ecx
   ;push multa
   ;call _printf
   ;call _printf
   
   


 
   ;add [banana], 33
   ;mov	edx,len     ;message length
   ;mov	ecx,msg     ;message to write
   ;mov	ebx,1       ;file descriptor (stdout)
   ;mov	eax,4       ;system call number (sys_write)
   ;int	0x80        ;call kernel
	
   ;mov	eax,1       ;system call number (sys_exit)
   ;int	0x80        ;call kernel
