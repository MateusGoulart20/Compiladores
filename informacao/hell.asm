 section	.data
   msg1 db 'insira um num', 10,0  ;string to be printed
   sorvete db 10, 0
   pizza db "%d", 0
   lasanha db "%d", 0xA, 0x0
   zero db '0'

 section .bss
   banana resd 2
   multa resd 1
   multb resd 1
   multc resd 1

 section	.text
   global _main     ;must be declared for linker (ld)
	extern _printf
   extern _scanf

_main:	            ;tells linker entry point
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
   mov ebx, 9
   mov ecx, 8
   cmp ebx, ecx
   jl rodolfo

   push msg1
   call _printf

   rodolfo:

   push msg1
   call _printf
   
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
