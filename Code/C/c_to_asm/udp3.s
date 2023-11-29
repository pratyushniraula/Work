	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 13, 1	sdk_version 13, 1
	.globl	_sendUdpData                    ; -- Begin function sendUdpData
	.p2align	2
_sendUdpData:                           ; @sendUdpData
	.cfi_startproc
; %bb.0:
	sub	sp, sp, #112
	stp	x29, x30, [sp, #96]             ; 16-byte Folded Spill
	add	x29, sp, #96
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	adrp	x8, ___stack_chk_guard@GOTPAGE
	ldr	x8, [x8, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x8, [x8]
	stur	x8, [x29, #-8]
	stur	x0, [x29, #-32]
	stur	w1, [x29, #-36]
	str	x2, [sp, #48]
	mov	w1, #2
	mov	x0, x1
	mov	w2, #17
	bl	_socket
	str	w0, [sp, #40]
	adds	w8, w0, #1
	cset	w8, ne
	mov	w9, #1
	and	w8, w8, w9
	strb	w8, [sp, #35]
	ldrb	w8, [sp, #35]
	tbz	w8, #0, LBB0_4
	b	LBB0_1
LBB0_1:
	ldur	x0, [x29, #-32]
	add	x1, sp, #44
	bl	_inet_aton
	mov	w8, #2
	sturb	w8, [x29, #-23]
	b	LBB0_2
LBB0_2:
	ldur	w8, [x29, #-36]
	and	w0, w8, #0xffff
	bl	__OSSwapInt16
	and	w8, w0, #0xffff
	str	w8, [sp, #28]                   ; 4-byte Folded Spill
	b	LBB0_3
LBB0_3:
	ldr	w8, [sp, #28]                   ; 4-byte Folded Reload
	sub	x9, x29, #24
	str	x9, [sp, #16]                   ; 8-byte Folded Spill
	sturh	w8, [x29, #-22]
	ldr	w8, [sp, #44]
	stur	w8, [x29, #-20]
	stur	xzr, [x29, #-16]
	ldr	w8, [sp, #40]
	str	w8, [sp, #24]                   ; 4-byte Folded Spill
	ldr	x8, [sp, #48]
	str	x8, [sp, #8]                    ; 8-byte Folded Spill
	ldr	x0, [sp, #48]
	bl	_strlen
	ldr	x1, [sp, #8]                    ; 8-byte Folded Reload
	ldr	x4, [sp, #16]                   ; 8-byte Folded Reload
	mov	x8, x0
	ldr	w0, [sp, #24]                   ; 4-byte Folded Reload
	add	x2, x8, #1
	mov	w3, #0
	mov	w5, #16
	bl	_sendto
	mov	x8, x0
	str	w8, [sp, #36]
	ldr	w8, [sp, #36]
	subs	w8, w8, #0
	cset	w8, gt
	and	w8, w8, #0x1
	strb	w8, [sp, #35]
	ldr	w0, [sp, #40]
	bl	_close
	b	LBB0_4
LBB0_4:
	ldrb	w8, [sp, #35]
	str	w8, [sp, #4]                    ; 4-byte Folded Spill
	ldur	x9, [x29, #-8]
	adrp	x8, ___stack_chk_guard@GOTPAGE
	ldr	x8, [x8, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x8, [x8]
	subs	x8, x8, x9
	b.eq	LBB0_6
	b	LBB0_5
LBB0_5:
	bl	___stack_chk_fail
LBB0_6:
	ldr	w8, [sp, #4]                    ; 4-byte Folded Reload
	and	w0, w8, #0x1
	ldp	x29, x30, [sp, #96]             ; 16-byte Folded Reload
	add	sp, sp, #112
	ret
	.cfi_endproc
                                        ; -- End function
	.p2align	2                               ; -- Begin function _OSSwapInt16
__OSSwapInt16:                          ; @_OSSwapInt16
	.cfi_startproc
; %bb.0:
	sub	sp, sp, #16
	.cfi_def_cfa_offset 16
	strh	w0, [sp, #14]
	ldrh	w9, [sp, #14]
	ldrh	w8, [sp, #14]
	asr	w8, w8, #8
	orr	w8, w8, w9, lsl #8
	and	w0, w8, #0xffff
	add	sp, sp, #16
	ret
	.cfi_endproc
                                        ; -- End function
	.globl	_openUdpListenerPort            ; -- Begin function openUdpListenerPort
	.p2align	2
_openUdpListenerPort:                   ; @openUdpListenerPort
	.cfi_startproc
; %bb.0:
	sub	sp, sp, #80
	stp	x29, x30, [sp, #64]             ; 16-byte Folded Spill
	add	x29, sp, #64
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	adrp	x8, _listen_sockfd@GOTPAGE
	ldr	x8, [x8, _listen_sockfd@GOTPAGEOFF]
	str	x8, [sp, #16]                   ; 8-byte Folded Spill
	adrp	x8, ___stack_chk_guard@GOTPAGE
	ldr	x8, [x8, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x8, [x8]
	stur	x8, [x29, #-8]
	stur	w0, [x29, #-28]
	mov	w8, #1
	str	w8, [sp, #32]
	mov	w8, #1
	strb	w8, [sp, #31]
	mov	w8, #2
	sturb	w8, [x29, #-23]
	b	LBB2_1
LBB2_1:
	ldur	w8, [x29, #-28]
	and	w0, w8, #0xffff
	bl	__OSSwapInt16
	and	w8, w0, #0xffff
	str	w8, [sp, #12]                   ; 4-byte Folded Spill
	b	LBB2_2
LBB2_2:
	ldr	w8, [sp, #12]                   ; 4-byte Folded Reload
	sturh	w8, [x29, #-22]
	stur	wzr, [x29, #-20]
	stur	xzr, [x29, #-16]
	mov	w1, #2
	mov	x0, x1
	mov	w2, #17
	bl	_socket
	ldr	x8, [sp, #16]                   ; 8-byte Folded Reload
	str	w0, [x8]
	adds	w8, w0, #1
	cset	w8, ne
	mov	w9, #1
	and	w8, w8, w9
	strb	w8, [sp, #31]
	ldrb	w8, [sp, #31]
	tbz	w8, #0, LBB2_6
	b	LBB2_3
LBB2_3:
	ldr	x8, [sp, #16]                   ; 8-byte Folded Reload
	ldr	w0, [x8]
	mov	w1, #65535
	mov	w2, #516
	add	x3, sp, #32
	mov	w4, #4
	bl	_setsockopt
	adds	w8, w0, #1
	cset	w8, ne
	mov	w9, #1
	and	w8, w8, w9
	strb	w8, [sp, #31]
	ldrb	w8, [sp, #31]
	tbz	w8, #0, LBB2_5
	b	LBB2_4
LBB2_4:
	ldr	x8, [sp, #16]                   ; 8-byte Folded Reload
	ldr	w0, [x8]
	sub	x1, x29, #24
	mov	w2, #16
	bl	_bind
	adds	w8, w0, #1
	cset	w8, ne
	and	w8, w8, #0x1
	strb	w8, [sp, #31]
	b	LBB2_5
LBB2_5:
	b	LBB2_6
LBB2_6:
	ldrb	w8, [sp, #31]
	str	w8, [sp, #8]                    ; 4-byte Folded Spill
	ldur	x9, [x29, #-8]
	adrp	x8, ___stack_chk_guard@GOTPAGE
	ldr	x8, [x8, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x8, [x8]
	subs	x8, x8, x9
	b.eq	LBB2_8
	b	LBB2_7
LBB2_7:
	bl	___stack_chk_fail
LBB2_8:
	ldr	w8, [sp, #8]                    ; 4-byte Folded Reload
	and	w0, w8, #0x1
	ldp	x29, x30, [sp, #64]             ; 16-byte Folded Reload
	add	sp, sp, #80
	ret
	.cfi_endproc
                                        ; -- End function
	.globl	_receiveUdpData                 ; -- Begin function receiveUdpData
	.p2align	2
_receiveUdpData:                        ; @receiveUdpData
	.cfi_startproc
; %bb.0:
	sub	sp, sp, #96
	stp	x29, x30, [sp, #80]             ; 16-byte Folded Spill
	add	x29, sp, #80
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	adrp	x8, _listen_sockfd@GOTPAGE
	ldr	x8, [x8, _listen_sockfd@GOTPAGEOFF]
	str	x8, [sp, #16]                   ; 8-byte Folded Spill
	adrp	x9, ___stack_chk_guard@GOTPAGE
	ldr	x9, [x9, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x9, [x9]
	stur	x9, [x29, #-8]
	stur	x0, [x29, #-32]
	stur	w1, [x29, #-36]
	str	w2, [sp, #40]
	mov	w9, #16
	str	w9, [sp, #32]
	ldr	w9, [x8]
	add	x0, sp, #24
	str	w9, [sp, #24]
	mov	w9, #1
	strh	w9, [sp, #28]
	ldr	w1, [x8]
	ldr	w2, [sp, #40]
	bl	_poll
	ldrsh	w8, [sp, #30]
	mov	w9, #1
	subs	w8, w8, #1
	cset	w8, eq
	and	w8, w8, w9
	strb	w8, [sp, #39]
	ldrb	w8, [sp, #39]
	tbz	w8, #0, LBB3_2
	b	LBB3_1
LBB3_1:
	ldr	x8, [sp, #16]                   ; 8-byte Folded Reload
	ldr	w0, [x8]
	ldur	x1, [x29, #-32]
	ldursw	x2, [x29, #-36]
	mov	w3, #0
	sub	x4, x29, #24
	add	x5, sp, #32
	bl	_recvfrom
	b	LBB3_2
LBB3_2:
	ldrb	w8, [sp, #39]
	str	w8, [sp, #12]                   ; 4-byte Folded Spill
	ldur	x9, [x29, #-8]
	adrp	x8, ___stack_chk_guard@GOTPAGE
	ldr	x8, [x8, ___stack_chk_guard@GOTPAGEOFF]
	ldr	x8, [x8]
	subs	x8, x8, x9
	b.eq	LBB3_4
	b	LBB3_3
LBB3_3:
	bl	___stack_chk_fail
LBB3_4:
	ldr	w8, [sp, #12]                   ; 4-byte Folded Reload
	and	w0, w8, #0x1
	ldp	x29, x30, [sp, #80]             ; 16-byte Folded Reload
	add	sp, sp, #96
	ret
	.cfi_endproc
                                        ; -- End function
	.globl	_closeUdpListenerPort           ; -- Begin function closeUdpListenerPort
	.p2align	2
_closeUdpListenerPort:                  ; @closeUdpListenerPort
	.cfi_startproc
; %bb.0:
	stp	x29, x30, [sp, #-16]!           ; 16-byte Folded Spill
	mov	x29, sp
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	adrp	x8, _listen_sockfd@GOTPAGE
	ldr	x8, [x8, _listen_sockfd@GOTPAGEOFF]
	ldr	w0, [x8]
	bl	_close
	ldp	x29, x30, [sp], #16             ; 16-byte Folded Reload
	ret
	.cfi_endproc
                                        ; -- End function
	.comm	_listen_sockfd,4,2              ; @listen_sockfd
	.comm	_listen_remote_ip,4,2           ; @listen_remote_ip
.subsections_via_symbols
