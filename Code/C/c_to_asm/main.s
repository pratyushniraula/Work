	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 13, 1	sdk_version 13, 1
	.globl	_main                           ; -- Begin function main
	.p2align	2
_main:                                  ; @main
	.cfi_startproc
; %bb.0:
	sub	sp, sp, #144
	stp	x29, x30, [sp, #128]            ; 16-byte Folded Spill
	add	x29, sp, #128
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	adrp	x8, ___stack_chk_guard@GOTPAGE
	ldr	x8, [x8, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x8, [x8]
	stur	x8, [x29, #-8]
	str	wzr, [sp, #16]
	mov	w0, #25565
	bl	_openUdpListenerPort
	mov	w8, #1
	and	w8, w0, w8
	strb	w8, [sp, #15]
	ldrb	w8, [sp, #15]
	tbz	w8, #0, LBB0_6
	b	LBB0_1
LBB0_1:
	mov	x9, sp
	mov	x8, #25565
	str	x8, [x9]
	adrp	x0, l_.str@PAGE
	add	x0, x0, l_.str@PAGEOFF
	bl	_printf
	b	LBB0_2
LBB0_2:                                 ; =>This Inner Loop Header: Depth=1
	add	x0, sp, #20
	mov	w1, #100
	mov	w2, #16960
	movk	w2, #15, lsl #16
	bl	_receiveUdpData
	mov	w8, #1
	and	w8, w0, w8
	strb	w8, [sp, #15]
	ldrb	w8, [sp, #15]
	tbz	w8, #0, LBB0_4
	b	LBB0_3
LBB0_3:                                 ;   in Loop: Header=BB0_2 Depth=1
	mov	x9, sp
	add	x8, sp, #20
	str	x8, [x9]
	adrp	x0, l_.str.1@PAGE
	add	x0, x0, l_.str.1@PAGEOFF
	bl	_printf
	b	LBB0_5
LBB0_4:                                 ;   in Loop: Header=BB0_2 Depth=1
	adrp	x0, l_.str.2@PAGE
	add	x0, x0, l_.str.2@PAGEOFF
	bl	_printf
	b	LBB0_5
LBB0_5:                                 ;   in Loop: Header=BB0_2 Depth=1
	b	LBB0_2
LBB0_6:
	mov	x9, sp
	mov	x8, #25565
	str	x8, [x9]
	adrp	x0, l_.str.3@PAGE
	add	x0, x0, l_.str.3@PAGEOFF
	bl	_printf
	b	LBB0_7
LBB0_7:
	ldur	x9, [x29, #-8]
	adrp	x8, ___stack_chk_guard@GOTPAGE
	ldr	x8, [x8, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x8, [x8]
	subs	x8, x8, x9
	b.eq	LBB0_9
	b	LBB0_8
LBB0_8:
	bl	___stack_chk_fail
LBB0_9:
	mov	w0, #0
	ldp	x29, x30, [sp, #128]            ; 16-byte Folded Reload
	add	sp, sp, #144
	ret
	.cfi_endproc
                                        ; -- End function
	.section	__TEXT,__cstring,cstring_literals
l_.str:                                 ; @.str
	.asciz	"Listening on port %d\n"

l_.str.1:                               ; @.str.1
	.asciz	"Received: %s\n"

l_.str.2:                               ; @.str.2
	.asciz	"Receive timeout\n"

l_.str.3:                               ; @.str.3
	.asciz	"Unable to open port %d\n"

.subsections_via_symbols
