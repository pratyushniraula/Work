	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 13, 1	sdk_version 13, 1
	.globl	_main                           ; -- Begin function main
	.p2align	2
_main:                                  ; @main
Lfunc_begin0:
	.cfi_startproc
	.cfi_personality 155, ___gxx_personality_v0
	.cfi_lsda 16, Lexception0
; %bb.0:
	sub	sp, sp, #368
	stp	x28, x27, [sp, #272]            ; 16-byte Folded Spill
	stp	x26, x25, [sp, #288]            ; 16-byte Folded Spill
	stp	x24, x23, [sp, #304]            ; 16-byte Folded Spill
	stp	x22, x21, [sp, #320]            ; 16-byte Folded Spill
	stp	x20, x19, [sp, #336]            ; 16-byte Folded Spill
	stp	x29, x30, [sp, #352]            ; 16-byte Folded Spill
	add	x29, sp, #352
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	.cfi_offset w23, -56
	.cfi_offset w24, -64
	.cfi_offset w25, -72
	.cfi_offset w26, -80
	.cfi_offset w27, -88
	.cfi_offset w28, -96
	stur	wzr, [x29, #-100]
	stp	xzr, xzr, [x29, #-128]
	stur	xzr, [x29, #-112]
	stp	xzr, xzr, [x29, #-152]
	stur	xzr, [x29, #-136]
	stp	xzr, xzr, [sp, #176]
	str	xzr, [sp, #192]
Lloh0:
	adrp	x8, __ZNSt3__14coutE@GOTPAGE
Lloh1:
	ldr	x8, [x8, __ZNSt3__14coutE@GOTPAGEOFF]
	ldr	x9, [x8]
	ldur	x10, [x9, #-24]
	add	x10, x8, x10
	mov	w11, #2
	str	x11, [x10, #16]
	ldur	x10, [x9, #-24]
	add	x10, x8, x10
	ldr	w11, [x10, #8]
	and	w11, w11, #0xfffffeff
	orr	w11, w11, #0x4
	str	w11, [x10, #8]
	ldur	x10, [x9, #-24]
	add	x10, x8, x10
	ldr	w11, [x10, #8]
	orr	w11, w11, #0x400
	str	w11, [x10, #8]
	ldur	x9, [x9, #-24]
	add	x0, x8, x9
Ltmp0:
	sub	x8, x29, #96
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp1:
; %bb.1:
Ltmp2:
Lloh2:
	adrp	x1, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh3:
	ldr	x1, [x1, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
	sub	x0, x29, #96
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp3:
; %bb.2:
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp4:
	mov	w1, #10
	blr	x8
Ltmp5:
; %bb.3:
	mov	x19, x0
Ltmp10:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp11:
; %bb.4:
Ltmp12:
Lloh4:
	adrp	x0, __ZNSt3__14coutE@GOTPAGE
Lloh5:
	ldr	x0, [x0, __ZNSt3__14coutE@GOTPAGEOFF]
	mov	x1, x19
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
Ltmp13:
; %bb.5:
Ltmp14:
Lloh6:
	adrp	x0, __ZNSt3__14coutE@GOTPAGE
Lloh7:
	ldr	x0, [x0, __ZNSt3__14coutE@GOTPAGEOFF]
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
Ltmp15:
; %bb.6:
Ltmp17:
Lloh8:
	adrp	x0, __ZNSt3__14coutE@GOTPAGE
Lloh9:
	ldr	x0, [x0, __ZNSt3__14coutE@GOTPAGEOFF]
Lloh10:
	adrp	x1, l_.str@PAGE
Lloh11:
	add	x1, x1, l_.str@PAGEOFF
	mov	w2, #31
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp18:
; %bb.7:
	mov	x19, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
Ltmp19:
	sub	x8, x29, #96
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp20:
; %bb.8:
Ltmp21:
Lloh12:
	adrp	x1, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh13:
	ldr	x1, [x1, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
	sub	x0, x29, #96
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp22:
; %bb.9:
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp23:
	mov	w1, #10
	blr	x8
Ltmp24:
; %bb.10:
	mov	x20, x0
Ltmp29:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp30:
; %bb.11:
Ltmp31:
	mov	x0, x19
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
Ltmp32:
; %bb.12:
Ltmp33:
	mov	x0, x19
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
Ltmp34:
; %bb.13:
Ltmp35:
Lloh14:
	adrp	x0, __ZNSt3__14coutE@GOTPAGE
Lloh15:
	ldr	x0, [x0, __ZNSt3__14coutE@GOTPAGEOFF]
Lloh16:
	adrp	x1, l_.str.1@PAGE
Lloh17:
	add	x1, x1, l_.str.1@PAGEOFF
	mov	w2, #47
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp36:
; %bb.14:
	mov	x19, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
Ltmp37:
	sub	x8, x29, #96
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp38:
; %bb.15:
Ltmp39:
Lloh18:
	adrp	x1, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh19:
	ldr	x1, [x1, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
	sub	x0, x29, #96
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp40:
; %bb.16:
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp41:
	mov	w1, #10
	blr	x8
Ltmp42:
; %bb.17:
	mov	x20, x0
Ltmp47:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp48:
; %bb.18:
Ltmp49:
	mov	x0, x19
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
Ltmp50:
; %bb.19:
Ltmp51:
	mov	x0, x19
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
Ltmp52:
; %bb.20:
Ltmp53:
Lloh20:
	adrp	x0, __ZNSt3__13cinE@GOTPAGE
Lloh21:
	ldr	x0, [x0, __ZNSt3__13cinE@GOTPAGEOFF]
	sub	x1, x29, #104
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERi
Ltmp54:
; %bb.21:
	ldur	w8, [x29, #-104]
	cmp	w8, #1
	b.ne	LBB0_122
; %bb.22:
Ltmp74:
	mov	w0, #72
	bl	__Znwm
Ltmp75:
; %bb.23:
	mov	x26, x0
	movi.2d	v0, #0000000000000000
	stp	q0, q0, [x0, #32]
	stp	q0, q0, [x0]
	stp	x0, x0, [sp, #144]
	str	xzr, [x0, #64]
	ldur	w8, [x29, #-100]
	cmp	w8, #5
	b.eq	LBB0_129
; %bb.24:
	mov	w25, #0
Lloh22:
	adrp	x19, __ZNSt3__14coutE@GOTPAGE
Lloh23:
	ldr	x19, [x19, __ZNSt3__14coutE@GOTPAGEOFF]
Lloh24:
	adrp	x20, l_.str.2@PAGE
Lloh25:
	add	x20, x20, l_.str.2@PAGEOFF
Lloh26:
	adrp	x21, l_.str.3@PAGE
Lloh27:
	add	x21, x21, l_.str.3@PAGEOFF
Lloh28:
	adrp	x22, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh29:
	ldr	x22, [x22, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
Lloh30:
	adrp	x23, __ZNSt3__13cinE@GOTPAGE
Lloh31:
	ldr	x23, [x23, __ZNSt3__13cinE@GOTPAGEOFF]
	mov	x24, x26
LBB0_25:                                ; =>This Loop Header: Depth=1
                                        ;     Child Loop BB0_101 Depth 2
Ltmp77:
	mov	x0, x19
	mov	x1, x20
	mov	w2, #36
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp78:
; %bb.26:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp79:
	mov	x1, x21
	mov	w2, #94
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp80:
; %bb.27:                               ;   in Loop: Header=BB0_25 Depth=1
	mov	x27, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
Ltmp81:
	sub	x8, x29, #96
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp82:
; %bb.28:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp83:
	sub	x0, x29, #96
	mov	x1, x22
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp84:
; %bb.29:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp85:
	mov	w1, #10
	blr	x8
Ltmp86:
; %bb.30:                               ;   in Loop: Header=BB0_25 Depth=1
	mov	x28, x0
Ltmp91:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp92:
; %bb.31:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp93:
	mov	x0, x27
	mov	x1, x28
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
Ltmp94:
; %bb.32:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp95:
	mov	x0, x27
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
Ltmp96:
; %bb.33:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp97:
	sub	x1, x29, #100
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERi
Ltmp98:
; %bb.34:                               ;   in Loop: Header=BB0_25 Depth=1
	ldur	w8, [x29, #-100]
	sub	w8, w8, #1
	cmp	w8, #4
	b.hi	LBB0_56
; %bb.35:                               ;   in Loop: Header=BB0_25 Depth=1
Lloh32:
	adrp	x11, lJTI0_0@PAGE
Lloh33:
	add	x11, x11, lJTI0_0@PAGEOFF
	adr	x9, LBB0_36
	ldrb	w10, [x11, x8]
	add	x9, x9, x10, lsl #2
	br	x9
LBB0_36:                                ;   in Loop: Header=BB0_25 Depth=1
	tbz	w25, #0, LBB0_107
; %bb.37:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp189:
	mov	x0, x19
Lloh34:
	adrp	x1, l_.str.4@PAGE
Lloh35:
	add	x1, x1, l_.str.4@PAGEOFF
	mov	w2, #34
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp190:
; %bb.38:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp191:
Lloh36:
	adrp	x1, l_.str.5@PAGE
Lloh37:
	add	x1, x1, l_.str.5@PAGEOFF
	mov	w2, #21
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp192:
; %bb.39:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp193:
	sub	x1, x29, #128
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp194:
; %bb.40:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp195:
	mov	x0, x19
Lloh38:
	adrp	x1, l_.str.6@PAGE
Lloh39:
	add	x1, x1, l_.str.6@PAGEOFF
	mov	w2, #27
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp196:
; %bb.41:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp197:
	sub	x1, x29, #152
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp198:
; %bb.42:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp199:
	mov	x0, x19
Lloh40:
	adrp	x1, l_.str.7@PAGE
Lloh41:
	add	x1, x1, l_.str.7@PAGEOFF
	mov	w2, #20
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp200:
; %bb.43:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp201:
	add	x1, sp, #168
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERd
Ltmp202:
; %bb.44:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp203:
	mov	x0, x19
Lloh42:
	adrp	x1, l_.str.8@PAGE
Lloh43:
	add	x1, x1, l_.str.8@PAGEOFF
	mov	w2, #27
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp204:
; %bb.45:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp205:
	add	x1, sp, #164
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERj
Ltmp206:
; %bb.46:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp207:
	mov	x0, x19
Lloh44:
	adrp	x1, l_.str.9@PAGE
Lloh45:
	add	x1, x1, l_.str.9@PAGEOFF
	mov	w2, #20
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp208:
; %bb.47:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp209:
	add	x1, sp, #160
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERj
Ltmp210:
; %bb.48:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp211:
	add	x0, sp, #120
	sub	x1, x29, #128
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEC1ERKS5_
Ltmp212:
; %bb.49:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp213:
	add	x0, sp, #96
	sub	x1, x29, #152
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEC1ERKS5_
Ltmp214:
; %bb.50:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	d0, [sp, #168]
	ldp	w4, w3, [sp, #160]
Ltmp216:
	add	x0, sp, #144
	add	x1, sp, #120
	add	x2, sp, #96
	bl	__Z10addStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_djj
Ltmp217:
; %bb.51:                               ;   in Loop: Header=BB0_25 Depth=1
	ldrsb	w8, [sp, #119]
	tbz	w8, #31, LBB0_53
; %bb.52:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x0, [sp, #96]
	bl	__ZdlPv
LBB0_53:                                ;   in Loop: Header=BB0_25 Depth=1
	ldrsb	w8, [sp, #143]
	tbz	w8, #31, LBB0_55
; %bb.54:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x0, [sp, #120]
	bl	__ZdlPv
LBB0_55:                                ;   in Loop: Header=BB0_25 Depth=1
	mov	w25, #1
	b	LBB0_121
LBB0_56:                                ;   in Loop: Header=BB0_25 Depth=1
Ltmp219:
	mov	x0, x19
Lloh46:
	adrp	x1, l_.str.17@PAGE
Lloh47:
	add	x1, x1, l_.str.17@PAGEOFF
	mov	w2, #23
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp220:
; %bb.57:                               ;   in Loop: Header=BB0_25 Depth=1
	mov	x27, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
Ltmp221:
	sub	x8, x29, #96
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp222:
; %bb.58:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp223:
	sub	x0, x29, #96
	mov	x1, x22
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp224:
; %bb.59:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp225:
	mov	w1, #10
	blr	x8
Ltmp226:
; %bb.60:                               ;   in Loop: Header=BB0_25 Depth=1
	mov	x28, x0
Ltmp231:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp232:
; %bb.61:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp233:
	mov	x0, x27
	mov	x1, x28
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
Ltmp234:
; %bb.62:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp235:
	mov	x0, x27
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
Ltmp236:
	b	LBB0_121
LBB0_63:                                ;   in Loop: Header=BB0_25 Depth=1
Ltmp154:
	mov	x0, x19
Lloh48:
	adrp	x1, l_.str.10@PAGE
Lloh49:
	add	x1, x1, l_.str.10@PAGEOFF
	mov	w2, #31
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp155:
; %bb.64:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp156:
	sub	x1, x29, #152
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp157:
; %bb.65:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp158:
	add	x0, sp, #72
	sub	x1, x29, #152
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEC1ERKS5_
Ltmp159:
; %bb.66:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp160:
	add	x0, sp, #152
	add	x1, sp, #72
	bl	__Z13deleteStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEE
Ltmp161:
; %bb.67:                               ;   in Loop: Header=BB0_25 Depth=1
	ldrsb	w8, [sp, #95]
	tbz	w8, #31, LBB0_121
; %bb.68:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x0, [sp, #72]
	b	LBB0_91
LBB0_69:                                ;   in Loop: Header=BB0_25 Depth=1
Ltmp119:
	mov	x0, x19
Lloh50:
	adrp	x1, l_.str.11@PAGE
Lloh51:
	add	x1, x1, l_.str.11@PAGEOFF
	mov	w2, #46
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp120:
; %bb.70:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp121:
	sub	x1, x29, #152
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp122:
; %bb.71:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp123:
	mov	x0, x19
Lloh52:
	adrp	x1, l_.str.12@PAGE
Lloh53:
	add	x1, x1, l_.str.12@PAGEOFF
	mov	w2, #21
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp124:
; %bb.72:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp125:
	sub	x1, x29, #128
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp126:
; %bb.73:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp127:
	mov	x0, x19
Lloh54:
	adrp	x1, l_.str.13@PAGE
Lloh55:
	add	x1, x1, l_.str.13@PAGEOFF
	mov	w2, #26
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp128:
; %bb.74:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp129:
	add	x1, sp, #176
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp130:
; %bb.75:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp131:
	mov	x0, x19
Lloh56:
	adrp	x1, l_.str.14@PAGE
Lloh57:
	add	x1, x1, l_.str.14@PAGEOFF
	mov	w2, #28
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp132:
; %bb.76:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp133:
	add	x1, sp, #168
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERd
Ltmp134:
; %bb.77:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp135:
	mov	x0, x19
Lloh58:
	adrp	x1, l_.str.15@PAGE
Lloh59:
	add	x1, x1, l_.str.15@PAGEOFF
	mov	w2, #25
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp136:
; %bb.78:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp137:
	add	x1, sp, #164
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERj
Ltmp138:
; %bb.79:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp139:
	mov	x0, x19
Lloh60:
	adrp	x1, l_.str.9@PAGE
Lloh61:
	add	x1, x1, l_.str.9@PAGEOFF
	mov	w2, #20
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp140:
; %bb.80:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp141:
	add	x1, sp, #160
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERj
Ltmp142:
; %bb.81:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp143:
	add	x0, sp, #48
	sub	x1, x29, #128
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEC1ERKS5_
Ltmp144:
; %bb.82:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp145:
	add	x0, sp, #24
	sub	x1, x29, #152
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEC1ERKS5_
Ltmp146:
; %bb.83:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp148:
	mov	x0, sp
	add	x1, sp, #176
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEC1ERKS5_
Ltmp149:
; %bb.84:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	d0, [sp, #168]
	ldp	w5, w4, [sp, #160]
Ltmp151:
	add	x0, sp, #152
	add	x1, sp, #48
	add	x2, sp, #24
	mov	x3, sp
	bl	__Z11editStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_S8_djj
Ltmp152:
; %bb.85:                               ;   in Loop: Header=BB0_25 Depth=1
	ldrsb	w8, [sp, #23]
	tbz	w8, #31, LBB0_87
; %bb.86:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x0, [sp]
	bl	__ZdlPv
LBB0_87:                                ;   in Loop: Header=BB0_25 Depth=1
	ldrsb	w8, [sp, #47]
	tbz	w8, #31, LBB0_89
; %bb.88:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x0, [sp, #24]
	bl	__ZdlPv
LBB0_89:                                ;   in Loop: Header=BB0_25 Depth=1
	ldrsb	w8, [sp, #71]
	tbz	w8, #31, LBB0_121
; %bb.90:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x0, [sp, #48]
LBB0_91:                                ;   in Loop: Header=BB0_25 Depth=1
	bl	__ZdlPv
	b	LBB0_121
LBB0_92:                                ;   in Loop: Header=BB0_25 Depth=1
Ltmp117:
	add	x0, sp, #152
	bl	__Z13PrintStudentsRP7student
Ltmp118:
	b	LBB0_121
LBB0_93:                                ;   in Loop: Header=BB0_25 Depth=1
Ltmp99:
	mov	x0, x19
Lloh62:
	adrp	x1, l_.str.16@PAGE
Lloh63:
	add	x1, x1, l_.str.16@PAGEOFF
	mov	w2, #29
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp100:
; %bb.94:                               ;   in Loop: Header=BB0_25 Depth=1
	mov	x26, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
Ltmp101:
	sub	x8, x29, #96
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp102:
; %bb.95:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp103:
	sub	x0, x29, #96
	mov	x1, x22
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp104:
; %bb.96:                               ;   in Loop: Header=BB0_25 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp105:
	mov	w1, #10
	blr	x8
Ltmp106:
; %bb.97:                               ;   in Loop: Header=BB0_25 Depth=1
	mov	x27, x0
Ltmp111:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp112:
; %bb.98:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp113:
	mov	x0, x26
	mov	x1, x27
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
Ltmp114:
; %bb.99:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp115:
	mov	x0, x26
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
Ltmp116:
; %bb.100:                              ;   in Loop: Header=BB0_25 Depth=1
	cbz	x24, LBB0_106
LBB0_101:                               ;   Parent Loop BB0_25 Depth=1
                                        ; =>  This Inner Loop Header: Depth=2
	ldr	x26, [x24, #64]
	ldrsb	w8, [x24, #47]
	tbz	w8, #31, LBB0_103
; %bb.102:                              ;   in Loop: Header=BB0_101 Depth=2
	ldr	x0, [x24, #24]
	bl	__ZdlPv
LBB0_103:                               ;   in Loop: Header=BB0_101 Depth=2
	ldrsb	w8, [x24, #23]
	tbz	w8, #31, LBB0_105
; %bb.104:                              ;   in Loop: Header=BB0_101 Depth=2
	ldr	x0, [x24]
	bl	__ZdlPv
LBB0_105:                               ;   in Loop: Header=BB0_101 Depth=2
	mov	x0, x24
	bl	__ZdlPv
	mov	x24, x26
	cbnz	x26, LBB0_101
LBB0_106:                               ;   in Loop: Header=BB0_25 Depth=1
	mov	x26, #0
	mov	x24, #0
	str	xzr, [sp, #152]
	b	LBB0_121
LBB0_107:                               ;   in Loop: Header=BB0_25 Depth=1
Ltmp163:
	mov	x0, x19
Lloh64:
	adrp	x1, l_.str.4@PAGE
Lloh65:
	add	x1, x1, l_.str.4@PAGEOFF
	mov	w2, #34
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp164:
; %bb.108:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp165:
Lloh66:
	adrp	x1, l_.str.5@PAGE
Lloh67:
	add	x1, x1, l_.str.5@PAGEOFF
	mov	w2, #21
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp166:
; %bb.109:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp167:
	sub	x1, x29, #128
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp168:
; %bb.110:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp169:
	mov	x0, x19
Lloh68:
	adrp	x1, l_.str.6@PAGE
Lloh69:
	add	x1, x1, l_.str.6@PAGEOFF
	mov	w2, #27
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp170:
; %bb.111:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp171:
	sub	x1, x29, #152
	mov	x0, x23
	bl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Ltmp172:
; %bb.112:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp173:
	mov	x0, x19
Lloh70:
	adrp	x1, l_.str.7@PAGE
Lloh71:
	add	x1, x1, l_.str.7@PAGEOFF
	mov	w2, #20
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp174:
; %bb.113:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp175:
	add	x1, sp, #168
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERd
Ltmp176:
; %bb.114:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp177:
	mov	x0, x19
Lloh72:
	adrp	x1, l_.str.8@PAGE
Lloh73:
	add	x1, x1, l_.str.8@PAGEOFF
	mov	w2, #27
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp178:
; %bb.115:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp179:
	add	x1, sp, #164
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERj
Ltmp180:
; %bb.116:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp181:
	mov	x0, x19
Lloh74:
	adrp	x1, l_.str.9@PAGE
Lloh75:
	add	x1, x1, l_.str.9@PAGEOFF
	mov	w2, #20
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp182:
; %bb.117:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp183:
	add	x1, sp, #160
	mov	x0, x23
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEErsERj
Ltmp184:
; %bb.118:                              ;   in Loop: Header=BB0_25 Depth=1
Ltmp185:
	sub	x1, x29, #128
	mov	x0, x26
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEaSERKS5_
Ltmp186:
; %bb.119:                              ;   in Loop: Header=BB0_25 Depth=1
	add	x0, x26, #24
Ltmp187:
	sub	x1, x29, #152
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEaSERKS5_
Ltmp188:
; %bb.120:                              ;   in Loop: Header=BB0_25 Depth=1
	ldr	d0, [sp, #168]
	str	d0, [x26, #48]
	ldp	w9, w8, [sp, #160]
	stp	w8, w9, [x26, #56]
	mov	w25, #1
	mov	x24, x26
LBB0_121:                               ;   in Loop: Header=BB0_25 Depth=1
	ldur	w8, [x29, #-100]
	cmp	w8, #5
	b.ne	LBB0_25
	b	LBB0_129
LBB0_122:
Ltmp55:
Lloh76:
	adrp	x0, __ZNSt3__14coutE@GOTPAGE
Lloh77:
	ldr	x0, [x0, __ZNSt3__14coutE@GOTPAGEOFF]
Lloh78:
	adrp	x1, l_.str.16@PAGE
Lloh79:
	add	x1, x1, l_.str.16@PAGEOFF
	mov	w2, #29
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Ltmp56:
; %bb.123:
	mov	x19, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
Ltmp57:
	sub	x8, x29, #96
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp58:
; %bb.124:
Ltmp59:
Lloh80:
	adrp	x1, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh81:
	ldr	x1, [x1, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
	sub	x0, x29, #96
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp60:
; %bb.125:
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp61:
	mov	w1, #10
	blr	x8
Ltmp62:
; %bb.126:
	mov	x20, x0
Ltmp67:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp68:
; %bb.127:
Ltmp69:
	mov	x0, x19
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
Ltmp70:
; %bb.128:
Ltmp71:
	mov	x0, x19
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
Ltmp72:
LBB0_129:
	ldrsb	w8, [sp, #199]
	tbz	w8, #31, LBB0_131
; %bb.130:
	ldr	x0, [sp, #176]
	bl	__ZdlPv
LBB0_131:
	ldursb	w8, [x29, #-129]
	tbz	w8, #31, LBB0_133
; %bb.132:
	ldur	x0, [x29, #-152]
	bl	__ZdlPv
LBB0_133:
	ldursb	w8, [x29, #-105]
	tbz	w8, #31, LBB0_135
; %bb.134:
	ldur	x0, [x29, #-128]
	bl	__ZdlPv
LBB0_135:
	mov	w0, #0
	ldp	x29, x30, [sp, #352]            ; 16-byte Folded Reload
	ldp	x20, x19, [sp, #336]            ; 16-byte Folded Reload
	ldp	x22, x21, [sp, #320]            ; 16-byte Folded Reload
	ldp	x24, x23, [sp, #304]            ; 16-byte Folded Reload
	ldp	x26, x25, [sp, #288]            ; 16-byte Folded Reload
	ldp	x28, x27, [sp, #272]            ; 16-byte Folded Reload
	add	sp, sp, #368
	ret
LBB0_136:
Ltmp76:
	b	LBB0_170
LBB0_137:
Ltmp218:
	mov	x19, x0
	ldrsb	w8, [sp, #119]
	tbz	w8, #31, LBB0_140
; %bb.138:
	ldr	x0, [sp, #96]
	bl	__ZdlPv
	b	LBB0_140
LBB0_139:
Ltmp215:
	mov	x19, x0
LBB0_140:
	ldrsb	w8, [sp, #143]
	tbz	w8, #31, LBB0_171
; %bb.141:
	ldr	x0, [sp, #120]
	b	LBB0_154
LBB0_142:
Ltmp63:
	mov	x19, x0
Ltmp64:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp65:
	b	LBB0_171
LBB0_143:
Ltmp66:
	bl	___clang_call_terminate
LBB0_144:
Ltmp153:
	mov	x19, x0
	ldrsb	w8, [sp, #23]
	tbz	w8, #31, LBB0_147
; %bb.145:
	ldr	x0, [sp]
	bl	__ZdlPv
	b	LBB0_147
LBB0_146:
Ltmp150:
	mov	x19, x0
LBB0_147:
	ldrsb	w8, [sp, #47]
	tbz	w8, #31, LBB0_150
; %bb.148:
	ldr	x0, [sp, #24]
	bl	__ZdlPv
	b	LBB0_150
LBB0_149:
Ltmp147:
	mov	x19, x0
LBB0_150:
	ldrsb	w8, [sp, #71]
	tbz	w8, #31, LBB0_171
; %bb.151:
	ldr	x0, [sp, #48]
	b	LBB0_154
LBB0_152:
Ltmp162:
	mov	x19, x0
	ldrsb	w8, [sp, #95]
	tbz	w8, #31, LBB0_171
; %bb.153:
	ldr	x0, [sp, #72]
LBB0_154:
	bl	__ZdlPv
	b	LBB0_171
LBB0_155:
Ltmp43:
	mov	x19, x0
Ltmp44:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp45:
	b	LBB0_171
LBB0_156:
Ltmp46:
	bl	___clang_call_terminate
LBB0_157:
Ltmp25:
	mov	x19, x0
Ltmp26:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp27:
	b	LBB0_171
LBB0_158:
Ltmp28:
	bl	___clang_call_terminate
LBB0_159:
Ltmp6:
	mov	x19, x0
Ltmp7:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp8:
	b	LBB0_171
LBB0_160:
Ltmp9:
	bl	___clang_call_terminate
LBB0_161:
Ltmp107:
	mov	x19, x0
Ltmp108:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp109:
	b	LBB0_171
LBB0_162:
Ltmp110:
	bl	___clang_call_terminate
LBB0_163:
Ltmp227:
	mov	x19, x0
Ltmp228:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp229:
	b	LBB0_171
LBB0_164:
Ltmp230:
	bl	___clang_call_terminate
LBB0_165:
Ltmp16:
	b	LBB0_170
LBB0_166:
Ltmp73:
	b	LBB0_170
LBB0_167:
Ltmp87:
	mov	x19, x0
Ltmp88:
	sub	x0, x29, #96
	bl	__ZNSt3__16localeD1Ev
Ltmp89:
	b	LBB0_171
LBB0_168:
Ltmp90:
	bl	___clang_call_terminate
LBB0_169:
Ltmp237:
LBB0_170:
	mov	x19, x0
LBB0_171:
	ldrsb	w8, [sp, #199]
	tbz	w8, #31, LBB0_173
; %bb.172:
	ldr	x0, [sp, #176]
	bl	__ZdlPv
LBB0_173:
	ldursb	w8, [x29, #-129]
	tbz	w8, #31, LBB0_175
; %bb.174:
	ldur	x0, [x29, #-152]
	bl	__ZdlPv
LBB0_175:
	ldursb	w8, [x29, #-105]
	tbz	w8, #31, LBB0_177
; %bb.176:
	ldur	x0, [x29, #-128]
	bl	__ZdlPv
LBB0_177:
	mov	x0, x19
	bl	__Unwind_Resume
	.loh AdrpLdrGot	Lloh0, Lloh1
	.loh AdrpLdrGot	Lloh2, Lloh3
	.loh AdrpLdrGot	Lloh4, Lloh5
	.loh AdrpLdrGot	Lloh6, Lloh7
	.loh AdrpAdd	Lloh10, Lloh11
	.loh AdrpLdrGot	Lloh8, Lloh9
	.loh AdrpLdrGot	Lloh12, Lloh13
	.loh AdrpAdd	Lloh16, Lloh17
	.loh AdrpLdrGot	Lloh14, Lloh15
	.loh AdrpLdrGot	Lloh18, Lloh19
	.loh AdrpLdrGot	Lloh20, Lloh21
	.loh AdrpLdrGot	Lloh30, Lloh31
	.loh AdrpLdrGot	Lloh28, Lloh29
	.loh AdrpAdd	Lloh26, Lloh27
	.loh AdrpAdd	Lloh24, Lloh25
	.loh AdrpLdrGot	Lloh22, Lloh23
	.loh AdrpAdd	Lloh32, Lloh33
	.loh AdrpAdd	Lloh34, Lloh35
	.loh AdrpAdd	Lloh36, Lloh37
	.loh AdrpAdd	Lloh38, Lloh39
	.loh AdrpAdd	Lloh40, Lloh41
	.loh AdrpAdd	Lloh42, Lloh43
	.loh AdrpAdd	Lloh44, Lloh45
	.loh AdrpAdd	Lloh46, Lloh47
	.loh AdrpAdd	Lloh48, Lloh49
	.loh AdrpAdd	Lloh50, Lloh51
	.loh AdrpAdd	Lloh52, Lloh53
	.loh AdrpAdd	Lloh54, Lloh55
	.loh AdrpAdd	Lloh56, Lloh57
	.loh AdrpAdd	Lloh58, Lloh59
	.loh AdrpAdd	Lloh60, Lloh61
	.loh AdrpAdd	Lloh62, Lloh63
	.loh AdrpAdd	Lloh64, Lloh65
	.loh AdrpAdd	Lloh66, Lloh67
	.loh AdrpAdd	Lloh68, Lloh69
	.loh AdrpAdd	Lloh70, Lloh71
	.loh AdrpAdd	Lloh72, Lloh73
	.loh AdrpAdd	Lloh74, Lloh75
	.loh AdrpAdd	Lloh78, Lloh79
	.loh AdrpLdrGot	Lloh76, Lloh77
	.loh AdrpLdrGot	Lloh80, Lloh81
Lfunc_end0:
	.cfi_endproc
	.section	__TEXT,__const
lJTI0_0:
	.byte	(LBB0_36-LBB0_36)>>2
	.byte	(LBB0_63-LBB0_36)>>2
	.byte	(LBB0_69-LBB0_36)>>2
	.byte	(LBB0_92-LBB0_36)>>2
	.byte	(LBB0_93-LBB0_36)>>2
	.section	__TEXT,__gcc_except_tab
	.p2align	2
GCC_except_table0:
Lexception0:
	.byte	255                             ; @LPStart Encoding = omit
	.byte	155                             ; @TType Encoding = indirect pcrel sdata4
	.uleb128 Lttbase0-Lttbaseref0
Lttbaseref0:
	.byte	1                               ; Call site Encoding = uleb128
	.uleb128 Lcst_end0-Lcst_begin0
Lcst_begin0:
	.uleb128 Ltmp0-Lfunc_begin0             ; >> Call Site 1 <<
	.uleb128 Ltmp1-Ltmp0                    ;   Call between Ltmp0 and Ltmp1
	.uleb128 Ltmp16-Lfunc_begin0            ;     jumps to Ltmp16
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp2-Lfunc_begin0             ; >> Call Site 2 <<
	.uleb128 Ltmp5-Ltmp2                    ;   Call between Ltmp2 and Ltmp5
	.uleb128 Ltmp6-Lfunc_begin0             ;     jumps to Ltmp6
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp10-Lfunc_begin0            ; >> Call Site 3 <<
	.uleb128 Ltmp15-Ltmp10                  ;   Call between Ltmp10 and Ltmp15
	.uleb128 Ltmp16-Lfunc_begin0            ;     jumps to Ltmp16
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp17-Lfunc_begin0            ; >> Call Site 4 <<
	.uleb128 Ltmp20-Ltmp17                  ;   Call between Ltmp17 and Ltmp20
	.uleb128 Ltmp73-Lfunc_begin0            ;     jumps to Ltmp73
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp21-Lfunc_begin0            ; >> Call Site 5 <<
	.uleb128 Ltmp24-Ltmp21                  ;   Call between Ltmp21 and Ltmp24
	.uleb128 Ltmp25-Lfunc_begin0            ;     jumps to Ltmp25
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp29-Lfunc_begin0            ; >> Call Site 6 <<
	.uleb128 Ltmp38-Ltmp29                  ;   Call between Ltmp29 and Ltmp38
	.uleb128 Ltmp73-Lfunc_begin0            ;     jumps to Ltmp73
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp39-Lfunc_begin0            ; >> Call Site 7 <<
	.uleb128 Ltmp42-Ltmp39                  ;   Call between Ltmp39 and Ltmp42
	.uleb128 Ltmp43-Lfunc_begin0            ;     jumps to Ltmp43
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp47-Lfunc_begin0            ; >> Call Site 8 <<
	.uleb128 Ltmp54-Ltmp47                  ;   Call between Ltmp47 and Ltmp54
	.uleb128 Ltmp73-Lfunc_begin0            ;     jumps to Ltmp73
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp74-Lfunc_begin0            ; >> Call Site 9 <<
	.uleb128 Ltmp75-Ltmp74                  ;   Call between Ltmp74 and Ltmp75
	.uleb128 Ltmp76-Lfunc_begin0            ;     jumps to Ltmp76
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp77-Lfunc_begin0            ; >> Call Site 10 <<
	.uleb128 Ltmp82-Ltmp77                  ;   Call between Ltmp77 and Ltmp82
	.uleb128 Ltmp237-Lfunc_begin0           ;     jumps to Ltmp237
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp83-Lfunc_begin0            ; >> Call Site 11 <<
	.uleb128 Ltmp86-Ltmp83                  ;   Call between Ltmp83 and Ltmp86
	.uleb128 Ltmp87-Lfunc_begin0            ;     jumps to Ltmp87
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp91-Lfunc_begin0            ; >> Call Site 12 <<
	.uleb128 Ltmp212-Ltmp91                 ;   Call between Ltmp91 and Ltmp212
	.uleb128 Ltmp237-Lfunc_begin0           ;     jumps to Ltmp237
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp213-Lfunc_begin0           ; >> Call Site 13 <<
	.uleb128 Ltmp214-Ltmp213                ;   Call between Ltmp213 and Ltmp214
	.uleb128 Ltmp215-Lfunc_begin0           ;     jumps to Ltmp215
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp216-Lfunc_begin0           ; >> Call Site 14 <<
	.uleb128 Ltmp217-Ltmp216                ;   Call between Ltmp216 and Ltmp217
	.uleb128 Ltmp218-Lfunc_begin0           ;     jumps to Ltmp218
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp219-Lfunc_begin0           ; >> Call Site 15 <<
	.uleb128 Ltmp222-Ltmp219                ;   Call between Ltmp219 and Ltmp222
	.uleb128 Ltmp237-Lfunc_begin0           ;     jumps to Ltmp237
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp223-Lfunc_begin0           ; >> Call Site 16 <<
	.uleb128 Ltmp226-Ltmp223                ;   Call between Ltmp223 and Ltmp226
	.uleb128 Ltmp227-Lfunc_begin0           ;     jumps to Ltmp227
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp231-Lfunc_begin0           ; >> Call Site 17 <<
	.uleb128 Ltmp159-Ltmp231                ;   Call between Ltmp231 and Ltmp159
	.uleb128 Ltmp237-Lfunc_begin0           ;     jumps to Ltmp237
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp160-Lfunc_begin0           ; >> Call Site 18 <<
	.uleb128 Ltmp161-Ltmp160                ;   Call between Ltmp160 and Ltmp161
	.uleb128 Ltmp162-Lfunc_begin0           ;     jumps to Ltmp162
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp119-Lfunc_begin0           ; >> Call Site 19 <<
	.uleb128 Ltmp144-Ltmp119                ;   Call between Ltmp119 and Ltmp144
	.uleb128 Ltmp237-Lfunc_begin0           ;     jumps to Ltmp237
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp145-Lfunc_begin0           ; >> Call Site 20 <<
	.uleb128 Ltmp146-Ltmp145                ;   Call between Ltmp145 and Ltmp146
	.uleb128 Ltmp147-Lfunc_begin0           ;     jumps to Ltmp147
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp148-Lfunc_begin0           ; >> Call Site 21 <<
	.uleb128 Ltmp149-Ltmp148                ;   Call between Ltmp148 and Ltmp149
	.uleb128 Ltmp150-Lfunc_begin0           ;     jumps to Ltmp150
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp151-Lfunc_begin0           ; >> Call Site 22 <<
	.uleb128 Ltmp152-Ltmp151                ;   Call between Ltmp151 and Ltmp152
	.uleb128 Ltmp153-Lfunc_begin0           ;     jumps to Ltmp153
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp117-Lfunc_begin0           ; >> Call Site 23 <<
	.uleb128 Ltmp102-Ltmp117                ;   Call between Ltmp117 and Ltmp102
	.uleb128 Ltmp237-Lfunc_begin0           ;     jumps to Ltmp237
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp103-Lfunc_begin0           ; >> Call Site 24 <<
	.uleb128 Ltmp106-Ltmp103                ;   Call between Ltmp103 and Ltmp106
	.uleb128 Ltmp107-Lfunc_begin0           ;     jumps to Ltmp107
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp111-Lfunc_begin0           ; >> Call Site 25 <<
	.uleb128 Ltmp188-Ltmp111                ;   Call between Ltmp111 and Ltmp188
	.uleb128 Ltmp237-Lfunc_begin0           ;     jumps to Ltmp237
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp55-Lfunc_begin0            ; >> Call Site 26 <<
	.uleb128 Ltmp58-Ltmp55                  ;   Call between Ltmp55 and Ltmp58
	.uleb128 Ltmp73-Lfunc_begin0            ;     jumps to Ltmp73
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp59-Lfunc_begin0            ; >> Call Site 27 <<
	.uleb128 Ltmp62-Ltmp59                  ;   Call between Ltmp59 and Ltmp62
	.uleb128 Ltmp63-Lfunc_begin0            ;     jumps to Ltmp63
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp67-Lfunc_begin0            ; >> Call Site 28 <<
	.uleb128 Ltmp72-Ltmp67                  ;   Call between Ltmp67 and Ltmp72
	.uleb128 Ltmp73-Lfunc_begin0            ;     jumps to Ltmp73
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp64-Lfunc_begin0            ; >> Call Site 29 <<
	.uleb128 Ltmp65-Ltmp64                  ;   Call between Ltmp64 and Ltmp65
	.uleb128 Ltmp66-Lfunc_begin0            ;     jumps to Ltmp66
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp44-Lfunc_begin0            ; >> Call Site 30 <<
	.uleb128 Ltmp45-Ltmp44                  ;   Call between Ltmp44 and Ltmp45
	.uleb128 Ltmp46-Lfunc_begin0            ;     jumps to Ltmp46
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp26-Lfunc_begin0            ; >> Call Site 31 <<
	.uleb128 Ltmp27-Ltmp26                  ;   Call between Ltmp26 and Ltmp27
	.uleb128 Ltmp28-Lfunc_begin0            ;     jumps to Ltmp28
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp7-Lfunc_begin0             ; >> Call Site 32 <<
	.uleb128 Ltmp8-Ltmp7                    ;   Call between Ltmp7 and Ltmp8
	.uleb128 Ltmp9-Lfunc_begin0             ;     jumps to Ltmp9
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp108-Lfunc_begin0           ; >> Call Site 33 <<
	.uleb128 Ltmp109-Ltmp108                ;   Call between Ltmp108 and Ltmp109
	.uleb128 Ltmp110-Lfunc_begin0           ;     jumps to Ltmp110
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp228-Lfunc_begin0           ; >> Call Site 34 <<
	.uleb128 Ltmp229-Ltmp228                ;   Call between Ltmp228 and Ltmp229
	.uleb128 Ltmp230-Lfunc_begin0           ;     jumps to Ltmp230
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp88-Lfunc_begin0            ; >> Call Site 35 <<
	.uleb128 Ltmp89-Ltmp88                  ;   Call between Ltmp88 and Ltmp89
	.uleb128 Ltmp90-Lfunc_begin0            ;     jumps to Ltmp90
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp89-Lfunc_begin0            ; >> Call Site 36 <<
	.uleb128 Lfunc_end0-Ltmp89              ;   Call between Ltmp89 and Lfunc_end0
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
Lcst_end0:
	.byte	1                               ; >> Action Record 1 <<
                                        ;   Catch TypeInfo 1
	.byte	0                               ;   No further actions
	.p2align	2
                                        ; >> Catch TypeInfos <<
	.long	0                               ; TypeInfo 1
Lttbase0:
	.p2align	2
                                        ; -- End function
	.section	__TEXT,__text,regular,pure_instructions
	.globl	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE ; -- Begin function _ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
	.weak_def_can_be_hidden	__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
	.p2align	2
__ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE: ; @_ZNSt3__1rsIcNS_11char_traitsIcEENS_9allocatorIcEEEERNS_13basic_istreamIT_T0_EES9_RNS_12basic_stringIS6_S7_T1_EE
Lfunc_begin1:
	.cfi_startproc
	.cfi_personality 155, ___gxx_personality_v0
	.cfi_lsda 16, Lexception1
; %bb.0:
	sub	sp, sp, #80
	stp	x24, x23, [sp, #16]             ; 16-byte Folded Spill
	stp	x22, x21, [sp, #32]             ; 16-byte Folded Spill
	stp	x20, x19, [sp, #48]             ; 16-byte Folded Spill
	stp	x29, x30, [sp, #64]             ; 16-byte Folded Spill
	add	x29, sp, #64
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	.cfi_offset w23, -56
	.cfi_offset w24, -64
	mov	x20, x1
	mov	x19, x0
	add	x0, sp, #8
	mov	x1, x19
	mov	w2, #0
	bl	__ZNSt3__113basic_istreamIcNS_11char_traitsIcEEE6sentryC1ERS3_b
	ldrb	w8, [sp, #8]
	cbz	w8, LBB1_24
; %bb.1:
	ldrsb	w8, [x20, #23]
	tbnz	w8, #31, LBB1_3
; %bb.2:
	strb	wzr, [x20]
	strb	wzr, [x20, #23]
	b	LBB1_4
LBB1_3:
	ldr	x8, [x20]
	strb	wzr, [x8]
	str	xzr, [x20, #8]
LBB1_4:
	ldr	x8, [x19]
	ldur	x8, [x8, #-24]
	add	x0, x19, x8
	ldr	x8, [x0, #24]
	mov	x9, #9223372036854775807
	cmp	x8, #1
	csel	x22, x9, x8, lt
Ltmp238:
	mov	x8, sp
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp239:
; %bb.5:
Ltmp240:
Lloh82:
	adrp	x1, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh83:
	ldr	x1, [x1, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
	mov	x0, sp
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp241:
; %bb.6:
	mov	x21, x0
Ltmp245:
	mov	x0, sp
	bl	__ZNSt3__16localeD1Ev
Ltmp246:
; %bb.7:
	mov	x23, #0
	add	x24, x19, #40
LBB1_8:                                 ; =>This Inner Loop Header: Depth=1
	ldr	x8, [x19]
	ldur	x8, [x8, #-24]
	ldr	x0, [x24, x8]
	ldp	x8, x9, [x0, #24]
	cmp	x8, x9
	b.eq	LBB1_10
; %bb.9:                                ;   in Loop: Header=BB1_8 Depth=1
	ldrb	w0, [x8]
	b	LBB1_12
LBB1_10:                                ;   in Loop: Header=BB1_8 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #72]
Ltmp248:
	blr	x8
Ltmp249:
; %bb.11:                               ;   in Loop: Header=BB1_8 Depth=1
	cmn	w0, #1
	b.eq	LBB1_21
LBB1_12:                                ;   in Loop: Header=BB1_8 Depth=1
	tbnz	w0, #7, LBB1_14
; %bb.13:                               ;   in Loop: Header=BB1_8 Depth=1
	ldr	x8, [x21, #16]
	and	w9, w0, #0xff
	ldr	w8, [x8, w9, uxtw #2]
	tbnz	w8, #14, LBB1_20
LBB1_14:                                ;   in Loop: Header=BB1_8 Depth=1
Ltmp251:
	sxtb	w1, w0
	mov	x0, x20
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEE9push_backEc
Ltmp252:
; %bb.15:                               ;   in Loop: Header=BB1_8 Depth=1
	ldr	x8, [x19]
	ldur	x8, [x8, #-24]
	ldr	x0, [x24, x8]
	ldp	x8, x9, [x0, #24]
	cmp	x8, x9
	b.eq	LBB1_17
; %bb.16:                               ;   in Loop: Header=BB1_8 Depth=1
	add	x8, x8, #1
	str	x8, [x0, #24]
	b	LBB1_18
LBB1_17:                                ;   in Loop: Header=BB1_8 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #80]
Ltmp253:
	blr	x8
Ltmp254:
LBB1_18:                                ;   in Loop: Header=BB1_8 Depth=1
	add	x23, x23, #1
	cmp	x22, x23
	b.ne	LBB1_8
; %bb.19:
	mov	w9, #0
	ldr	x8, [x19]
	ldur	x10, [x8, #-24]
	add	x10, x19, x10
	str	xzr, [x10, #24]
	b	LBB1_23
LBB1_20:
	mov	w9, #0
	b	LBB1_22
LBB1_21:
	mov	w9, #2
LBB1_22:
	ldr	x8, [x19]
	ldur	x10, [x8, #-24]
	add	x10, x19, x10
	str	xzr, [x10, #24]
	orr	w10, w9, #0x4
	cmp	x23, #0
	csel	w9, w10, w9, eq
LBB1_23:
	ldur	x8, [x8, #-24]
	add	x0, x19, x8
	ldr	w8, [x0, #32]
	orr	w1, w8, w9
	bl	__ZNSt3__18ios_base5clearEj
LBB1_24:
	mov	x0, x19
	ldp	x29, x30, [sp, #64]             ; 16-byte Folded Reload
	ldp	x20, x19, [sp, #48]             ; 16-byte Folded Reload
	ldp	x22, x21, [sp, #32]             ; 16-byte Folded Reload
	ldp	x24, x23, [sp, #16]             ; 16-byte Folded Reload
	add	sp, sp, #80
	ret
LBB1_25:
Ltmp242:
	mov	x20, x0
Ltmp243:
	mov	x0, sp
	bl	__ZNSt3__16localeD1Ev
Ltmp244:
	b	LBB1_30
LBB1_26:
Ltmp247:
	b	LBB1_29
LBB1_27:
Ltmp250:
	b	LBB1_29
LBB1_28:
Ltmp255:
LBB1_29:
	mov	x20, x0
LBB1_30:
	mov	x0, x20
	bl	___cxa_begin_catch
	ldr	x8, [x19]
	ldur	x9, [x8, #-24]
	add	x9, x19, x9
	ldr	w10, [x9, #32]
	orr	w10, w10, #0x1
	str	w10, [x9, #32]
	ldur	x8, [x8, #-24]
	add	x8, x19, x8
	ldrb	w8, [x8, #36]
	tbnz	w8, #0, LBB1_32
; %bb.31:
	bl	___cxa_end_catch
	ldr	x8, [x19]
	mov	w9, #1
	b	LBB1_23
LBB1_32:
Ltmp256:
	bl	___cxa_rethrow
Ltmp257:
; %bb.33:
	brk	#0x1
LBB1_34:
Ltmp258:
	mov	x19, x0
Ltmp259:
	bl	___cxa_end_catch
Ltmp260:
; %bb.35:
	mov	x0, x19
	bl	__Unwind_Resume
LBB1_36:
Ltmp261:
	bl	___clang_call_terminate
	.loh AdrpLdrGot	Lloh82, Lloh83
Lfunc_end1:
	.cfi_endproc
	.section	__TEXT,__gcc_except_tab
	.p2align	2
GCC_except_table1:
Lexception1:
	.byte	255                             ; @LPStart Encoding = omit
	.byte	155                             ; @TType Encoding = indirect pcrel sdata4
	.uleb128 Lttbase1-Lttbaseref1
Lttbaseref1:
	.byte	1                               ; Call site Encoding = uleb128
	.uleb128 Lcst_end1-Lcst_begin1
Lcst_begin1:
	.uleb128 Lfunc_begin1-Lfunc_begin1      ; >> Call Site 1 <<
	.uleb128 Ltmp238-Lfunc_begin1           ;   Call between Lfunc_begin1 and Ltmp238
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp238-Lfunc_begin1           ; >> Call Site 2 <<
	.uleb128 Ltmp239-Ltmp238                ;   Call between Ltmp238 and Ltmp239
	.uleb128 Ltmp247-Lfunc_begin1           ;     jumps to Ltmp247
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp240-Lfunc_begin1           ; >> Call Site 3 <<
	.uleb128 Ltmp241-Ltmp240                ;   Call between Ltmp240 and Ltmp241
	.uleb128 Ltmp242-Lfunc_begin1           ;     jumps to Ltmp242
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp245-Lfunc_begin1           ; >> Call Site 4 <<
	.uleb128 Ltmp246-Ltmp245                ;   Call between Ltmp245 and Ltmp246
	.uleb128 Ltmp247-Lfunc_begin1           ;     jumps to Ltmp247
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp248-Lfunc_begin1           ; >> Call Site 5 <<
	.uleb128 Ltmp249-Ltmp248                ;   Call between Ltmp248 and Ltmp249
	.uleb128 Ltmp250-Lfunc_begin1           ;     jumps to Ltmp250
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp251-Lfunc_begin1           ; >> Call Site 6 <<
	.uleb128 Ltmp254-Ltmp251                ;   Call between Ltmp251 and Ltmp254
	.uleb128 Ltmp255-Lfunc_begin1           ;     jumps to Ltmp255
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp254-Lfunc_begin1           ; >> Call Site 7 <<
	.uleb128 Ltmp243-Ltmp254                ;   Call between Ltmp254 and Ltmp243
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp243-Lfunc_begin1           ; >> Call Site 8 <<
	.uleb128 Ltmp244-Ltmp243                ;   Call between Ltmp243 and Ltmp244
	.uleb128 Ltmp261-Lfunc_begin1           ;     jumps to Ltmp261
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp244-Lfunc_begin1           ; >> Call Site 9 <<
	.uleb128 Ltmp256-Ltmp244                ;   Call between Ltmp244 and Ltmp256
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp256-Lfunc_begin1           ; >> Call Site 10 <<
	.uleb128 Ltmp257-Ltmp256                ;   Call between Ltmp256 and Ltmp257
	.uleb128 Ltmp258-Lfunc_begin1           ;     jumps to Ltmp258
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp259-Lfunc_begin1           ; >> Call Site 11 <<
	.uleb128 Ltmp260-Ltmp259                ;   Call between Ltmp259 and Ltmp260
	.uleb128 Ltmp261-Lfunc_begin1           ;     jumps to Ltmp261
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp260-Lfunc_begin1           ; >> Call Site 12 <<
	.uleb128 Lfunc_end1-Ltmp260             ;   Call between Ltmp260 and Lfunc_end1
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
Lcst_end1:
	.byte	1                               ; >> Action Record 1 <<
                                        ;   Catch TypeInfo 1
	.byte	0                               ;   No further actions
	.p2align	2
                                        ; >> Catch TypeInfos <<
	.long	0                               ; TypeInfo 1
Lttbase1:
	.p2align	2
                                        ; -- End function
	.section	__TEXT,__text,regular,pure_instructions
	.globl	__Z10addStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_djj ; -- Begin function _Z10addStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_djj
	.p2align	2
__Z10addStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_djj: ; @_Z10addStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_djj
	.cfi_startproc
; %bb.0:
	stp	d9, d8, [sp, #-80]!             ; 16-byte Folded Spill
	stp	x24, x23, [sp, #16]             ; 16-byte Folded Spill
	stp	x22, x21, [sp, #32]             ; 16-byte Folded Spill
	stp	x20, x19, [sp, #48]             ; 16-byte Folded Spill
	stp	x29, x30, [sp, #64]             ; 16-byte Folded Spill
	add	x29, sp, #64
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	.cfi_offset w23, -56
	.cfi_offset w24, -64
	.cfi_offset b8, -72
	.cfi_offset b9, -80
	mov	x19, x4
	mov	x20, x3
	fmov	d8, d0
	mov	x21, x2
	mov	x22, x1
	mov	x23, x0
	mov	w0, #72
	bl	__Znwm
	mov	x24, x0
	movi.2d	v0, #0000000000000000
	stp	q0, q0, [x0]
	stp	q0, q0, [x0, #32]
	str	xzr, [x0, #64]
	mov	x1, x22
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEaSERKS5_
	add	x0, x24, #24
	mov	x1, x21
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEaSERKS5_
	str	d8, [x24, #48]
	stp	w20, w19, [x24, #56]
	ldr	x8, [x23]
	str	x24, [x8, #64]
	str	x24, [x23]
	str	xzr, [x24, #64]
	ldp	x29, x30, [sp, #64]             ; 16-byte Folded Reload
	ldp	x20, x19, [sp, #48]             ; 16-byte Folded Reload
	ldp	x22, x21, [sp, #32]             ; 16-byte Folded Reload
	ldp	x24, x23, [sp, #16]             ; 16-byte Folded Reload
	ldp	d9, d8, [sp], #80               ; 16-byte Folded Reload
	ret
	.cfi_endproc
                                        ; -- End function
	.private_extern	___clang_call_terminate ; -- Begin function __clang_call_terminate
	.globl	___clang_call_terminate
	.weak_def_can_be_hidden	___clang_call_terminate
	.p2align	2
___clang_call_terminate:                ; @__clang_call_terminate
; %bb.0:
	stp	x29, x30, [sp, #-16]!           ; 16-byte Folded Spill
	bl	___cxa_begin_catch
	bl	__ZSt9terminatev
                                        ; -- End function
	.globl	__Z13deleteStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEE ; -- Begin function _Z13deleteStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEE
	.p2align	2
__Z13deleteStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEE: ; @_Z13deleteStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEE
	.cfi_startproc
; %bb.0:
	stp	x22, x21, [sp, #-48]!           ; 16-byte Folded Spill
	stp	x20, x19, [sp, #16]             ; 16-byte Folded Spill
	stp	x29, x30, [sp, #32]             ; 16-byte Folded Spill
	add	x29, sp, #32
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	ldr	x20, [x0]
	cbz	x20, LBB4_17
; %bb.1:
	mov	x19, x1
	mov	x21, x20
LBB4_2:                                 ; =>This Loop Header: Depth=1
                                        ;     Child Loop BB4_7 Depth 2
	ldrb	w8, [x20, #47]
	sxtb	w9, w8
	ldr	x10, [x20, #32]
	cmp	w9, #0
	csel	x2, x10, x8, lt
	ldrb	w10, [x19, #23]
	sxtb	w9, w10
	ldr	x11, [x19, #8]
	cmp	w9, #0
	csel	x10, x11, x10, lt
	cmp	x2, x10
	b.ne	LBB4_11
; %bb.3:                                ;   in Loop: Header=BB4_2 Depth=1
	add	x22, x20, #24
	sxtb	w11, w8
	ldr	x10, [x22]
	cmp	w11, #0
	csel	x0, x10, x22, lt
	ldr	x11, [x19]
	cmp	w9, #0
	csel	x1, x11, x19, lt
	tbnz	w8, #7, LBB4_9
; %bb.4:                                ;   in Loop: Header=BB4_2 Depth=1
	cbz	x2, LBB4_12
; %bb.5:                                ;   in Loop: Header=BB4_2 Depth=1
	ldrb	w9, [x1]
	cmp	w9, w10, uxtb
	b.ne	LBB4_11
; %bb.6:                                ;   in Loop: Header=BB4_2 Depth=1
	sub	x8, x8, #1
	add	x9, x20, #25
	add	x10, x1, #1
LBB4_7:                                 ;   Parent Loop BB4_2 Depth=1
                                        ; =>  This Inner Loop Header: Depth=2
	cbz	x8, LBB4_12
; %bb.8:                                ;   in Loop: Header=BB4_7 Depth=2
	bl	_OUTLINED_FUNCTION_0
	b.eq	LBB4_7
	b	LBB4_11
LBB4_9:                                 ;   in Loop: Header=BB4_2 Depth=1
	cbz	x2, LBB4_12
; %bb.10:                               ;   in Loop: Header=BB4_2 Depth=1
	bl	_memcmp
	cbz	w0, LBB4_12
LBB4_11:                                ;   in Loop: Header=BB4_2 Depth=1
	mov	x21, x20
	ldr	x20, [x20, #64]
	cbnz	x20, LBB4_2
	b	LBB4_17
LBB4_12:                                ;   in Loop: Header=BB4_2 Depth=1
	ldr	x8, [x20, #64]
	str	x8, [x21, #64]
	ldrsb	w8, [x20, #47]
	tbz	w8, #31, LBB4_14
; %bb.13:                               ;   in Loop: Header=BB4_2 Depth=1
	ldr	x0, [x22]
	bl	__ZdlPv
LBB4_14:                                ;   in Loop: Header=BB4_2 Depth=1
	ldrsb	w8, [x20, #23]
	tbz	w8, #31, LBB4_16
; %bb.15:                               ;   in Loop: Header=BB4_2 Depth=1
	ldr	x0, [x20]
	bl	__ZdlPv
LBB4_16:                                ;   in Loop: Header=BB4_2 Depth=1
	mov	x0, x20
	bl	__ZdlPv
	cbnz	x20, LBB4_2
LBB4_17:
Lloh84:
	adrp	x0, __ZNSt3__14coutE@GOTPAGE
Lloh85:
	ldr	x0, [x0, __ZNSt3__14coutE@GOTPAGEOFF]
Lloh86:
	adrp	x1, l_.str.18@PAGE
Lloh87:
	add	x1, x1, l_.str.18@PAGEOFF
	mov	w2, #8
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	brk	#0x1
	.loh AdrpAdd	Lloh86, Lloh87
	.loh AdrpLdrGot	Lloh84, Lloh85
	.cfi_endproc
                                        ; -- End function
	.globl	__Z11editStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_S8_djj ; -- Begin function _Z11editStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_S8_djj
	.p2align	2
__Z11editStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_S8_djj: ; @_Z11editStudentRP7studentNSt3__112basic_stringIcNS2_11char_traitsIcEENS2_9allocatorIcEEEES8_S8_djj
	.cfi_startproc
; %bb.0:
	stp	d9, d8, [sp, #-96]!             ; 16-byte Folded Spill
	stp	x26, x25, [sp, #16]             ; 16-byte Folded Spill
	stp	x24, x23, [sp, #32]             ; 16-byte Folded Spill
	stp	x22, x21, [sp, #48]             ; 16-byte Folded Spill
	stp	x20, x19, [sp, #64]             ; 16-byte Folded Spill
	stp	x29, x30, [sp, #80]             ; 16-byte Folded Spill
	add	x29, sp, #80
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	.cfi_offset w23, -56
	.cfi_offset w24, -64
	.cfi_offset w25, -72
	.cfi_offset w26, -80
	.cfi_offset b8, -88
	.cfi_offset b9, -96
	ldr	x24, [x0]
	cbz	x24, LBB5_13
; %bb.1:
	mov	x19, x5
	mov	x20, x4
	fmov	d8, d0
	mov	x21, x3
	mov	x22, x2
	mov	x23, x1
LBB5_2:                                 ; =>This Loop Header: Depth=1
                                        ;     Child Loop BB5_7 Depth 2
	ldrb	w8, [x24, #47]
	sxtb	w9, w8
	ldr	x10, [x24, #32]
	cmp	w9, #0
	csel	x2, x10, x8, lt
	ldrb	w10, [x22, #23]
	sxtb	w9, w10
	ldr	x11, [x22, #8]
	cmp	w9, #0
	csel	x10, x11, x10, lt
	cmp	x2, x10
	b.ne	LBB5_11
; %bb.3:                                ;   in Loop: Header=BB5_2 Depth=1
	add	x25, x24, #24
	sxtb	w11, w8
	ldr	x10, [x25]
	cmp	w11, #0
	csel	x0, x10, x25, lt
	ldr	x11, [x22]
	cmp	w9, #0
	csel	x1, x11, x22, lt
	tbnz	w8, #7, LBB5_9
; %bb.4:                                ;   in Loop: Header=BB5_2 Depth=1
	cbz	x2, LBB5_12
; %bb.5:                                ;   in Loop: Header=BB5_2 Depth=1
	ldrb	w9, [x1]
	cmp	w9, w10, uxtb
	b.ne	LBB5_11
; %bb.6:                                ;   in Loop: Header=BB5_2 Depth=1
	sub	x8, x8, #1
	add	x9, x24, #25
	add	x10, x1, #1
LBB5_7:                                 ;   Parent Loop BB5_2 Depth=1
                                        ; =>  This Inner Loop Header: Depth=2
	cbz	x8, LBB5_12
; %bb.8:                                ;   in Loop: Header=BB5_7 Depth=2
	bl	_OUTLINED_FUNCTION_0
	b.eq	LBB5_7
	b	LBB5_11
LBB5_9:                                 ;   in Loop: Header=BB5_2 Depth=1
	cbz	x2, LBB5_12
; %bb.10:                               ;   in Loop: Header=BB5_2 Depth=1
	bl	_memcmp
	cbz	w0, LBB5_12
LBB5_11:                                ;   in Loop: Header=BB5_2 Depth=1
	ldr	x24, [x24, #64]
	cbnz	x24, LBB5_2
	b	LBB5_13
LBB5_12:                                ;   in Loop: Header=BB5_2 Depth=1
	mov	x0, x24
	mov	x1, x23
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEaSERKS5_
	mov	x0, x25
	mov	x1, x21
	bl	__ZNSt3__112basic_stringIcNS_11char_traitsIcEENS_9allocatorIcEEEaSERKS5_
	str	d8, [x24, #48]
	stp	w20, w19, [x24, #56]
	cbnz	x24, LBB5_2
LBB5_13:
Lloh88:
	adrp	x0, __ZNSt3__14coutE@GOTPAGE
Lloh89:
	ldr	x0, [x0, __ZNSt3__14coutE@GOTPAGEOFF]
Lloh90:
	adrp	x1, l_.str.18@PAGE
Lloh91:
	add	x1, x1, l_.str.18@PAGEOFF
	mov	w2, #8
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	brk	#0x1
	.loh AdrpAdd	Lloh90, Lloh91
	.loh AdrpLdrGot	Lloh88, Lloh89
	.cfi_endproc
                                        ; -- End function
	.globl	__Z13PrintStudentsRP7student    ; -- Begin function _Z13PrintStudentsRP7student
	.p2align	2
__Z13PrintStudentsRP7student:           ; @_Z13PrintStudentsRP7student
Lfunc_begin2:
	.cfi_startproc
	.cfi_personality 155, ___gxx_personality_v0
	.cfi_lsda 16, Lexception2
; %bb.0:
	sub	sp, sp, #112
	stp	x28, x27, [sp, #16]             ; 16-byte Folded Spill
	stp	x26, x25, [sp, #32]             ; 16-byte Folded Spill
	stp	x24, x23, [sp, #48]             ; 16-byte Folded Spill
	stp	x22, x21, [sp, #64]             ; 16-byte Folded Spill
	stp	x20, x19, [sp, #80]             ; 16-byte Folded Spill
	stp	x29, x30, [sp, #96]             ; 16-byte Folded Spill
	add	x29, sp, #96
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	.cfi_offset w23, -56
	.cfi_offset w24, -64
	.cfi_offset w25, -72
	.cfi_offset w26, -80
	.cfi_offset w27, -88
	.cfi_offset w28, -96
	ldr	x21, [x0]
	cbz	x21, LBB6_13
; %bb.1:
Lloh92:
	adrp	x19, __ZNSt3__14coutE@GOTPAGE
Lloh93:
	ldr	x19, [x19, __ZNSt3__14coutE@GOTPAGEOFF]
Lloh94:
	adrp	x22, l_.str.23@PAGE
Lloh95:
	add	x22, x22, l_.str.23@PAGEOFF
Lloh96:
	adrp	x23, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh97:
	ldr	x23, [x23, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
Lloh98:
	adrp	x24, l_.str.24@PAGE
Lloh99:
	add	x24, x24, l_.str.24@PAGEOFF
Lloh100:
	adrp	x25, l_.str.25@PAGE
Lloh101:
	add	x25, x25, l_.str.25@PAGEOFF
Lloh102:
	adrp	x26, l_.str.26@PAGE
Lloh103:
	add	x26, x26, l_.str.26@PAGEOFF
Lloh104:
	adrp	x27, l_.str.27@PAGE
Lloh105:
	add	x27, x27, l_.str.27@PAGEOFF
LBB6_2:                                 ; =>This Inner Loop Header: Depth=1
	mov	x0, x19
Lloh106:
	adrp	x1, l_.str.21@PAGE
Lloh107:
	add	x1, x1, l_.str.21@PAGEOFF
	mov	w2, #1
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x0, x19
Lloh108:
	adrp	x1, l_.str.22@PAGE
Lloh109:
	add	x1, x1, l_.str.22@PAGEOFF
	mov	w2, #6
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	ldrb	w8, [x21, #23]
	sxtb	w9, w8
	ldp	x10, x11, [x21]
	cmp	w9, #0
	csel	x1, x10, x21, lt
	csel	x2, x11, x8, lt
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x1, x22
	mov	w2, #1
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x28, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
	add	x8, sp, #8
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp262:
	add	x0, sp, #8
	mov	x1, x23
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp263:
; %bb.3:                                ;   in Loop: Header=BB6_2 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp264:
	mov	w1, #10
	blr	x8
Ltmp265:
; %bb.4:                                ;   in Loop: Header=BB6_2 Depth=1
	mov	x20, x0
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
	mov	x0, x28
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
	mov	x0, x28
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
	mov	x0, x19
	mov	x1, x24
	mov	w2, #12
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x8, x21
	ldr	x9, [x8, #24]!
	ldrb	w10, [x8, #23]
	sxtb	w11, w10
	ldr	x12, [x8, #8]
	cmp	w11, #0
	csel	x1, x9, x8, lt
	csel	x2, x12, x10, lt
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x1, x22
	mov	w2, #1
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x28, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
	add	x8, sp, #8
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp270:
	add	x0, sp, #8
	mov	x1, x23
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp271:
; %bb.5:                                ;   in Loop: Header=BB6_2 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp272:
	mov	w1, #10
	blr	x8
Ltmp273:
; %bb.6:                                ;   in Loop: Header=BB6_2 Depth=1
	mov	x20, x0
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
	mov	x0, x28
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
	mov	x0, x28
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
	mov	x0, x19
	mov	x1, x25
	mov	w2, #5
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	ldr	d0, [x21, #48]
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEElsEd
	mov	x1, x22
	mov	w2, #1
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x28, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
	add	x8, sp, #8
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp278:
	add	x0, sp, #8
	mov	x1, x23
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp279:
; %bb.7:                                ;   in Loop: Header=BB6_2 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp280:
	mov	w1, #10
	blr	x8
Ltmp281:
; %bb.8:                                ;   in Loop: Header=BB6_2 Depth=1
	mov	x20, x0
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
	mov	x0, x28
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
	mov	x0, x28
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
	mov	x0, x19
	mov	x1, x26
	mov	w2, #7
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	ldr	w1, [x21, #56]
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEElsEj
	mov	x1, x22
	mov	w2, #1
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x28, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
	add	x8, sp, #8
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp286:
	add	x0, sp, #8
	mov	x1, x23
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp287:
; %bb.9:                                ;   in Loop: Header=BB6_2 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp288:
	mov	w1, #10
	blr	x8
Ltmp289:
; %bb.10:                               ;   in Loop: Header=BB6_2 Depth=1
	mov	x20, x0
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
	mov	x0, x28
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
	mov	x0, x28
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
	mov	x0, x19
	mov	x1, x27
	mov	w2, #5
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	ldr	w1, [x21, #60]
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEElsEj
	mov	x1, x22
	mov	w2, #1
	bl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	mov	x28, x0
	ldr	x8, [x0]
	ldur	x8, [x8, #-24]
	add	x0, x0, x8
	add	x8, sp, #8
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp294:
	add	x0, sp, #8
	mov	x1, x23
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp295:
; %bb.11:                               ;   in Loop: Header=BB6_2 Depth=1
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp296:
	mov	w1, #10
	blr	x8
Ltmp297:
; %bb.12:                               ;   in Loop: Header=BB6_2 Depth=1
	mov	x20, x0
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
	mov	x0, x28
	mov	x1, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
	mov	x0, x28
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
	ldr	x21, [x21, #64]
	cbnz	x21, LBB6_2
LBB6_13:
Lloh110:
	adrp	x8, __ZNSt3__14coutE@GOTPAGE
Lloh111:
	ldr	x8, [x8, __ZNSt3__14coutE@GOTPAGEOFF]
	ldr	x9, [x8]
	ldur	x9, [x9, #-24]
	add	x0, x8, x9
	add	x8, sp, #8
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp302:
Lloh112:
	adrp	x1, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh113:
	ldr	x1, [x1, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
	add	x0, sp, #8
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp303:
; %bb.14:
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp304:
	mov	w1, #10
	blr	x8
Ltmp305:
; %bb.15:
	mov	x19, x0
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
Lloh114:
	adrp	x20, __ZNSt3__14coutE@GOTPAGE
Lloh115:
	ldr	x20, [x20, __ZNSt3__14coutE@GOTPAGEOFF]
	mov	x0, x20
	mov	x1, x19
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE3putEc
	mov	x0, x20
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE5flushEv
	ldp	x29, x30, [sp, #96]             ; 16-byte Folded Reload
	ldp	x20, x19, [sp, #80]             ; 16-byte Folded Reload
	ldp	x22, x21, [sp, #64]             ; 16-byte Folded Reload
	ldp	x24, x23, [sp, #48]             ; 16-byte Folded Reload
	ldp	x26, x25, [sp, #32]             ; 16-byte Folded Reload
	ldp	x28, x27, [sp, #16]             ; 16-byte Folded Reload
	add	sp, sp, #112
	ret
LBB6_16:
Ltmp306:
	mov	x19, x0
Ltmp307:
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
Ltmp308:
	b	LBB6_27
LBB6_17:
Ltmp309:
	bl	___clang_call_terminate
LBB6_18:
Ltmp298:
	mov	x19, x0
Ltmp299:
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
Ltmp300:
	b	LBB6_27
LBB6_19:
Ltmp301:
	bl	___clang_call_terminate
LBB6_20:
Ltmp290:
	mov	x19, x0
Ltmp291:
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
Ltmp292:
	b	LBB6_27
LBB6_21:
Ltmp293:
	bl	___clang_call_terminate
LBB6_22:
Ltmp282:
	mov	x19, x0
Ltmp283:
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
Ltmp284:
	b	LBB6_27
LBB6_23:
Ltmp285:
	bl	___clang_call_terminate
LBB6_24:
Ltmp274:
	mov	x19, x0
Ltmp275:
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
Ltmp276:
	b	LBB6_27
LBB6_25:
Ltmp277:
	bl	___clang_call_terminate
LBB6_26:
Ltmp266:
	mov	x19, x0
Ltmp267:
	add	x0, sp, #8
	bl	__ZNSt3__16localeD1Ev
Ltmp268:
LBB6_27:
	mov	x0, x19
	bl	__Unwind_Resume
LBB6_28:
Ltmp269:
	bl	___clang_call_terminate
	.loh AdrpAdd	Lloh104, Lloh105
	.loh AdrpAdd	Lloh102, Lloh103
	.loh AdrpAdd	Lloh100, Lloh101
	.loh AdrpAdd	Lloh98, Lloh99
	.loh AdrpLdrGot	Lloh96, Lloh97
	.loh AdrpAdd	Lloh94, Lloh95
	.loh AdrpLdrGot	Lloh92, Lloh93
	.loh AdrpAdd	Lloh108, Lloh109
	.loh AdrpAdd	Lloh106, Lloh107
	.loh AdrpLdrGot	Lloh112, Lloh113
	.loh AdrpLdrGot	Lloh110, Lloh111
	.loh AdrpLdrGot	Lloh114, Lloh115
Lfunc_end2:
	.cfi_endproc
	.section	__TEXT,__gcc_except_tab
	.p2align	2
GCC_except_table6:
Lexception2:
	.byte	255                             ; @LPStart Encoding = omit
	.byte	155                             ; @TType Encoding = indirect pcrel sdata4
	.uleb128 Lttbase2-Lttbaseref2
Lttbaseref2:
	.byte	1                               ; Call site Encoding = uleb128
	.uleb128 Lcst_end2-Lcst_begin2
Lcst_begin2:
	.uleb128 Lfunc_begin2-Lfunc_begin2      ; >> Call Site 1 <<
	.uleb128 Ltmp262-Lfunc_begin2           ;   Call between Lfunc_begin2 and Ltmp262
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp262-Lfunc_begin2           ; >> Call Site 2 <<
	.uleb128 Ltmp265-Ltmp262                ;   Call between Ltmp262 and Ltmp265
	.uleb128 Ltmp266-Lfunc_begin2           ;     jumps to Ltmp266
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp265-Lfunc_begin2           ; >> Call Site 3 <<
	.uleb128 Ltmp270-Ltmp265                ;   Call between Ltmp265 and Ltmp270
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp270-Lfunc_begin2           ; >> Call Site 4 <<
	.uleb128 Ltmp273-Ltmp270                ;   Call between Ltmp270 and Ltmp273
	.uleb128 Ltmp274-Lfunc_begin2           ;     jumps to Ltmp274
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp273-Lfunc_begin2           ; >> Call Site 5 <<
	.uleb128 Ltmp278-Ltmp273                ;   Call between Ltmp273 and Ltmp278
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp278-Lfunc_begin2           ; >> Call Site 6 <<
	.uleb128 Ltmp281-Ltmp278                ;   Call between Ltmp278 and Ltmp281
	.uleb128 Ltmp282-Lfunc_begin2           ;     jumps to Ltmp282
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp281-Lfunc_begin2           ; >> Call Site 7 <<
	.uleb128 Ltmp286-Ltmp281                ;   Call between Ltmp281 and Ltmp286
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp286-Lfunc_begin2           ; >> Call Site 8 <<
	.uleb128 Ltmp289-Ltmp286                ;   Call between Ltmp286 and Ltmp289
	.uleb128 Ltmp290-Lfunc_begin2           ;     jumps to Ltmp290
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp289-Lfunc_begin2           ; >> Call Site 9 <<
	.uleb128 Ltmp294-Ltmp289                ;   Call between Ltmp289 and Ltmp294
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp294-Lfunc_begin2           ; >> Call Site 10 <<
	.uleb128 Ltmp297-Ltmp294                ;   Call between Ltmp294 and Ltmp297
	.uleb128 Ltmp298-Lfunc_begin2           ;     jumps to Ltmp298
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp297-Lfunc_begin2           ; >> Call Site 11 <<
	.uleb128 Ltmp302-Ltmp297                ;   Call between Ltmp297 and Ltmp302
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp302-Lfunc_begin2           ; >> Call Site 12 <<
	.uleb128 Ltmp305-Ltmp302                ;   Call between Ltmp302 and Ltmp305
	.uleb128 Ltmp306-Lfunc_begin2           ;     jumps to Ltmp306
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp305-Lfunc_begin2           ; >> Call Site 13 <<
	.uleb128 Ltmp307-Ltmp305                ;   Call between Ltmp305 and Ltmp307
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp307-Lfunc_begin2           ; >> Call Site 14 <<
	.uleb128 Ltmp308-Ltmp307                ;   Call between Ltmp307 and Ltmp308
	.uleb128 Ltmp309-Lfunc_begin2           ;     jumps to Ltmp309
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp299-Lfunc_begin2           ; >> Call Site 15 <<
	.uleb128 Ltmp300-Ltmp299                ;   Call between Ltmp299 and Ltmp300
	.uleb128 Ltmp301-Lfunc_begin2           ;     jumps to Ltmp301
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp291-Lfunc_begin2           ; >> Call Site 16 <<
	.uleb128 Ltmp292-Ltmp291                ;   Call between Ltmp291 and Ltmp292
	.uleb128 Ltmp293-Lfunc_begin2           ;     jumps to Ltmp293
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp283-Lfunc_begin2           ; >> Call Site 17 <<
	.uleb128 Ltmp284-Ltmp283                ;   Call between Ltmp283 and Ltmp284
	.uleb128 Ltmp285-Lfunc_begin2           ;     jumps to Ltmp285
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp275-Lfunc_begin2           ; >> Call Site 18 <<
	.uleb128 Ltmp276-Ltmp275                ;   Call between Ltmp275 and Ltmp276
	.uleb128 Ltmp277-Lfunc_begin2           ;     jumps to Ltmp277
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp267-Lfunc_begin2           ; >> Call Site 19 <<
	.uleb128 Ltmp268-Ltmp267                ;   Call between Ltmp267 and Ltmp268
	.uleb128 Ltmp269-Lfunc_begin2           ;     jumps to Ltmp269
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp268-Lfunc_begin2           ; >> Call Site 20 <<
	.uleb128 Lfunc_end2-Ltmp268             ;   Call between Ltmp268 and Lfunc_end2
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
Lcst_end2:
	.byte	1                               ; >> Action Record 1 <<
                                        ;   Catch TypeInfo 1
	.byte	0                               ;   No further actions
	.p2align	2
                                        ; >> Catch TypeInfos <<
	.long	0                               ; TypeInfo 1
Lttbase2:
	.p2align	2
                                        ; -- End function
	.section	__TEXT,__text,regular,pure_instructions
	.globl	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m ; -- Begin function _ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	.weak_def_can_be_hidden	__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
	.p2align	2
__ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m: ; @_ZNSt3__124__put_character_sequenceIcNS_11char_traitsIcEEEERNS_13basic_ostreamIT_T0_EES7_PKS4_m
Lfunc_begin3:
	.cfi_startproc
	.cfi_personality 155, ___gxx_personality_v0
	.cfi_lsda 16, Lexception3
; %bb.0:
	sub	sp, sp, #112
	stp	x26, x25, [sp, #32]             ; 16-byte Folded Spill
	stp	x24, x23, [sp, #48]             ; 16-byte Folded Spill
	stp	x22, x21, [sp, #64]             ; 16-byte Folded Spill
	stp	x20, x19, [sp, #80]             ; 16-byte Folded Spill
	stp	x29, x30, [sp, #96]             ; 16-byte Folded Spill
	add	x29, sp, #96
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	.cfi_offset w23, -56
	.cfi_offset w24, -64
	.cfi_offset w25, -72
	.cfi_offset w26, -80
	mov	x21, x2
	mov	x20, x1
	mov	x19, x0
Ltmp310:
	add	x0, sp, #8
	mov	x1, x19
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE6sentryC1ERS3_
Ltmp311:
; %bb.1:
	ldrb	w8, [sp, #8]
	cbz	w8, LBB7_11
; %bb.2:
	ldr	x8, [x19]
	ldur	x8, [x8, #-24]
	add	x22, x19, x8
	ldr	x23, [x22, #40]
	ldr	w25, [x22, #8]
	ldr	w24, [x22, #144]
	cmn	w24, #1
	b.ne	LBB7_8
; %bb.3:
Ltmp312:
	add	x8, sp, #24
	mov	x0, x22
	bl	__ZNKSt3__18ios_base6getlocEv
Ltmp313:
; %bb.4:
Ltmp314:
Lloh116:
	adrp	x1, __ZNSt3__15ctypeIcE2idE@GOTPAGE
Lloh117:
	ldr	x1, [x1, __ZNSt3__15ctypeIcE2idE@GOTPAGEOFF]
	add	x0, sp, #24
	bl	__ZNKSt3__16locale9use_facetERNS0_2idE
Ltmp315:
; %bb.5:
	ldr	x8, [x0]
	ldr	x8, [x8, #56]
Ltmp316:
	mov	w1, #32
	blr	x8
Ltmp317:
; %bb.6:
	mov	x24, x0
Ltmp322:
	add	x0, sp, #24
	bl	__ZNSt3__16localeD1Ev
Ltmp323:
; %bb.7:
	str	w24, [x22, #144]
LBB7_8:
	add	x3, x20, x21
	mov	w8, #176
	and	w8, w25, w8
	cmp	w8, #32
	csel	x2, x3, x20, eq
Ltmp324:
	sxtb	w5, w24
	mov	x0, x23
	mov	x1, x20
	mov	x4, x22
	bl	__ZNSt3__116__pad_and_outputIcNS_11char_traitsIcEEEENS_19ostreambuf_iteratorIT_T0_EES6_PKS4_S8_S8_RNS_8ios_baseES4_
Ltmp325:
; %bb.9:
	cbnz	x0, LBB7_11
; %bb.10:
	ldr	x8, [x19]
	ldur	x8, [x8, #-24]
	add	x0, x19, x8
	ldr	w8, [x0, #32]
	mov	w9, #5
	orr	w1, w8, w9
Ltmp327:
	bl	__ZNSt3__18ios_base5clearEj
Ltmp328:
LBB7_11:
Ltmp332:
	add	x0, sp, #8
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE6sentryD1Ev
Ltmp333:
LBB7_12:
	mov	x0, x19
	ldp	x29, x30, [sp, #96]             ; 16-byte Folded Reload
	ldp	x20, x19, [sp, #80]             ; 16-byte Folded Reload
	ldp	x22, x21, [sp, #64]             ; 16-byte Folded Reload
	ldp	x24, x23, [sp, #48]             ; 16-byte Folded Reload
	ldp	x26, x25, [sp, #32]             ; 16-byte Folded Reload
	add	sp, sp, #112
	ret
LBB7_13:
Ltmp329:
	b	LBB7_17
LBB7_14:
Ltmp318:
	mov	x20, x0
Ltmp319:
	add	x0, sp, #24
	bl	__ZNSt3__16localeD1Ev
Ltmp320:
	b	LBB7_18
LBB7_15:
Ltmp321:
	bl	___clang_call_terminate
LBB7_16:
Ltmp326:
LBB7_17:
	mov	x20, x0
LBB7_18:
Ltmp330:
	add	x0, sp, #8
	bl	__ZNSt3__113basic_ostreamIcNS_11char_traitsIcEEE6sentryD1Ev
Ltmp331:
	b	LBB7_20
LBB7_19:
Ltmp334:
	mov	x20, x0
LBB7_20:
	mov	x0, x20
	bl	___cxa_begin_catch
	ldr	x8, [x19]
	ldur	x8, [x8, #-24]
	add	x0, x19, x8
Ltmp335:
	bl	__ZNSt3__18ios_base33__set_badbit_and_consider_rethrowEv
Ltmp336:
; %bb.21:
	bl	___cxa_end_catch
	b	LBB7_12
LBB7_22:
Ltmp337:
	mov	x19, x0
Ltmp338:
	bl	___cxa_end_catch
Ltmp339:
; %bb.23:
	mov	x0, x19
	bl	__Unwind_Resume
LBB7_24:
Ltmp340:
	bl	___clang_call_terminate
	.loh AdrpLdrGot	Lloh116, Lloh117
Lfunc_end3:
	.cfi_endproc
	.section	__TEXT,__gcc_except_tab
	.p2align	2
GCC_except_table7:
Lexception3:
	.byte	255                             ; @LPStart Encoding = omit
	.byte	155                             ; @TType Encoding = indirect pcrel sdata4
	.uleb128 Lttbase3-Lttbaseref3
Lttbaseref3:
	.byte	1                               ; Call site Encoding = uleb128
	.uleb128 Lcst_end3-Lcst_begin3
Lcst_begin3:
	.uleb128 Ltmp310-Lfunc_begin3           ; >> Call Site 1 <<
	.uleb128 Ltmp311-Ltmp310                ;   Call between Ltmp310 and Ltmp311
	.uleb128 Ltmp334-Lfunc_begin3           ;     jumps to Ltmp334
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp312-Lfunc_begin3           ; >> Call Site 2 <<
	.uleb128 Ltmp313-Ltmp312                ;   Call between Ltmp312 and Ltmp313
	.uleb128 Ltmp326-Lfunc_begin3           ;     jumps to Ltmp326
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp314-Lfunc_begin3           ; >> Call Site 3 <<
	.uleb128 Ltmp317-Ltmp314                ;   Call between Ltmp314 and Ltmp317
	.uleb128 Ltmp318-Lfunc_begin3           ;     jumps to Ltmp318
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp322-Lfunc_begin3           ; >> Call Site 4 <<
	.uleb128 Ltmp325-Ltmp322                ;   Call between Ltmp322 and Ltmp325
	.uleb128 Ltmp326-Lfunc_begin3           ;     jumps to Ltmp326
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp327-Lfunc_begin3           ; >> Call Site 5 <<
	.uleb128 Ltmp328-Ltmp327                ;   Call between Ltmp327 and Ltmp328
	.uleb128 Ltmp329-Lfunc_begin3           ;     jumps to Ltmp329
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp332-Lfunc_begin3           ; >> Call Site 6 <<
	.uleb128 Ltmp333-Ltmp332                ;   Call between Ltmp332 and Ltmp333
	.uleb128 Ltmp334-Lfunc_begin3           ;     jumps to Ltmp334
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp319-Lfunc_begin3           ; >> Call Site 7 <<
	.uleb128 Ltmp320-Ltmp319                ;   Call between Ltmp319 and Ltmp320
	.uleb128 Ltmp321-Lfunc_begin3           ;     jumps to Ltmp321
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp330-Lfunc_begin3           ; >> Call Site 8 <<
	.uleb128 Ltmp331-Ltmp330                ;   Call between Ltmp330 and Ltmp331
	.uleb128 Ltmp340-Lfunc_begin3           ;     jumps to Ltmp340
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp331-Lfunc_begin3           ; >> Call Site 9 <<
	.uleb128 Ltmp335-Ltmp331                ;   Call between Ltmp331 and Ltmp335
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp335-Lfunc_begin3           ; >> Call Site 10 <<
	.uleb128 Ltmp336-Ltmp335                ;   Call between Ltmp335 and Ltmp336
	.uleb128 Ltmp337-Lfunc_begin3           ;     jumps to Ltmp337
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp336-Lfunc_begin3           ; >> Call Site 11 <<
	.uleb128 Ltmp338-Ltmp336                ;   Call between Ltmp336 and Ltmp338
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp338-Lfunc_begin3           ; >> Call Site 12 <<
	.uleb128 Ltmp339-Ltmp338                ;   Call between Ltmp338 and Ltmp339
	.uleb128 Ltmp340-Lfunc_begin3           ;     jumps to Ltmp340
	.byte	1                               ;   On action: 1
	.uleb128 Ltmp339-Lfunc_begin3           ; >> Call Site 13 <<
	.uleb128 Lfunc_end3-Ltmp339             ;   Call between Ltmp339 and Lfunc_end3
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
Lcst_end3:
	.byte	1                               ; >> Action Record 1 <<
                                        ;   Catch TypeInfo 1
	.byte	0                               ;   No further actions
	.p2align	2
                                        ; >> Catch TypeInfos <<
	.long	0                               ; TypeInfo 1
Lttbase3:
	.p2align	2
                                        ; -- End function
	.section	__TEXT,__text,regular,pure_instructions
	.private_extern	__ZNSt3__116__pad_and_outputIcNS_11char_traitsIcEEEENS_19ostreambuf_iteratorIT_T0_EES6_PKS4_S8_S8_RNS_8ios_baseES4_ ; -- Begin function _ZNSt3__116__pad_and_outputIcNS_11char_traitsIcEEEENS_19ostreambuf_iteratorIT_T0_EES6_PKS4_S8_S8_RNS_8ios_baseES4_
	.globl	__ZNSt3__116__pad_and_outputIcNS_11char_traitsIcEEEENS_19ostreambuf_iteratorIT_T0_EES6_PKS4_S8_S8_RNS_8ios_baseES4_
	.weak_def_can_be_hidden	__ZNSt3__116__pad_and_outputIcNS_11char_traitsIcEEEENS_19ostreambuf_iteratorIT_T0_EES6_PKS4_S8_S8_RNS_8ios_baseES4_
	.p2align	2
__ZNSt3__116__pad_and_outputIcNS_11char_traitsIcEEEENS_19ostreambuf_iteratorIT_T0_EES6_PKS4_S8_S8_RNS_8ios_baseES4_: ; @_ZNSt3__116__pad_and_outputIcNS_11char_traitsIcEEEENS_19ostreambuf_iteratorIT_T0_EES6_PKS4_S8_S8_RNS_8ios_baseES4_
Lfunc_begin4:
	.cfi_startproc
	.cfi_personality 155, ___gxx_personality_v0
	.cfi_lsda 16, Lexception4
; %bb.0:
	sub	sp, sp, #112
	stp	x26, x25, [sp, #32]             ; 16-byte Folded Spill
	stp	x24, x23, [sp, #48]             ; 16-byte Folded Spill
	stp	x22, x21, [sp, #64]             ; 16-byte Folded Spill
	stp	x20, x19, [sp, #80]             ; 16-byte Folded Spill
	stp	x29, x30, [sp, #96]             ; 16-byte Folded Spill
	add	x29, sp, #96
	.cfi_def_cfa w29, 16
	.cfi_offset w30, -8
	.cfi_offset w29, -16
	.cfi_offset w19, -24
	.cfi_offset w20, -32
	.cfi_offset w21, -40
	.cfi_offset w22, -48
	.cfi_offset w23, -56
	.cfi_offset w24, -64
	.cfi_offset w25, -72
	.cfi_offset w26, -80
	mov	x19, x0
	cbz	x0, LBB8_15
; %bb.1:
	mov	x24, x5
	mov	x20, x4
	mov	x22, x3
	mov	x21, x2
	ldr	x8, [x4, #24]
	sub	x9, x3, x1
	subs	x8, x8, x9
	csel	x23, x8, xzr, gt
	sub	x25, x2, x1
	cmp	x25, #1
	b.lt	LBB8_3
; %bb.2:
	ldr	x8, [x19]
	ldr	x8, [x8, #96]
	mov	x0, x19
	mov	x2, x25
	blr	x8
	cmp	x0, x25
	b.ne	LBB8_14
LBB8_3:
	cmp	x23, #1
	b.lt	LBB8_11
; %bb.4:
	cmp	x23, #23
	b.hs	LBB8_6
; %bb.5:
	add	x25, sp, #8
	strb	w23, [sp, #31]
	b	LBB8_7
LBB8_6:
	add	x8, x23, #16
	and	x26, x8, #0xfffffffffffffff0
	mov	x0, x26
	bl	__Znwm
	mov	x25, x0
	orr	x8, x26, #0x8000000000000000
	stp	x23, x8, [sp, #16]
	str	x0, [sp, #8]
LBB8_7:
	mov	x0, x25
	mov	x1, x24
	mov	x2, x23
	bl	_memset
	strb	wzr, [x25, x23]
	ldrsb	w8, [sp, #31]
	ldr	x9, [sp, #8]
	cmp	w8, #0
	add	x8, sp, #8
	csel	x1, x9, x8, lt
	ldr	x8, [x19]
	ldr	x8, [x8, #96]
Ltmp341:
	mov	x0, x19
	mov	x2, x23
	blr	x8
Ltmp342:
; %bb.8:
	mov	x24, x0
	ldrsb	w8, [sp, #31]
	tbz	w8, #31, LBB8_10
; %bb.9:
	ldr	x0, [sp, #8]
	bl	__ZdlPv
LBB8_10:
	cmp	x24, x23
	b.ne	LBB8_14
LBB8_11:
	sub	x22, x22, x21
	cmp	x22, #1
	b.lt	LBB8_13
; %bb.12:
	ldr	x8, [x19]
	ldr	x8, [x8, #96]
	mov	x0, x19
	mov	x1, x21
	mov	x2, x22
	blr	x8
	cmp	x0, x22
	b.ne	LBB8_14
LBB8_13:
	str	xzr, [x20, #24]
	b	LBB8_15
LBB8_14:
	mov	x19, #0
LBB8_15:
	mov	x0, x19
	ldp	x29, x30, [sp, #96]             ; 16-byte Folded Reload
	ldp	x20, x19, [sp, #80]             ; 16-byte Folded Reload
	ldp	x22, x21, [sp, #64]             ; 16-byte Folded Reload
	ldp	x24, x23, [sp, #48]             ; 16-byte Folded Reload
	ldp	x26, x25, [sp, #32]             ; 16-byte Folded Reload
	add	sp, sp, #112
	ret
LBB8_16:
Ltmp343:
	mov	x19, x0
	ldrsb	w8, [sp, #31]
	tbz	w8, #31, LBB8_18
; %bb.17:
	ldr	x0, [sp, #8]
	bl	__ZdlPv
LBB8_18:
	mov	x0, x19
	bl	__Unwind_Resume
Lfunc_end4:
	.cfi_endproc
	.section	__TEXT,__gcc_except_tab
	.p2align	2
GCC_except_table8:
Lexception4:
	.byte	255                             ; @LPStart Encoding = omit
	.byte	255                             ; @TType Encoding = omit
	.byte	1                               ; Call site Encoding = uleb128
	.uleb128 Lcst_end4-Lcst_begin4
Lcst_begin4:
	.uleb128 Lfunc_begin4-Lfunc_begin4      ; >> Call Site 1 <<
	.uleb128 Ltmp341-Lfunc_begin4           ;   Call between Lfunc_begin4 and Ltmp341
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp341-Lfunc_begin4           ; >> Call Site 2 <<
	.uleb128 Ltmp342-Ltmp341                ;   Call between Ltmp341 and Ltmp342
	.uleb128 Ltmp343-Lfunc_begin4           ;     jumps to Ltmp343
	.byte	0                               ;   On action: cleanup
	.uleb128 Ltmp342-Lfunc_begin4           ; >> Call Site 3 <<
	.uleb128 Lfunc_end4-Ltmp342             ;   Call between Ltmp342 and Lfunc_end4
	.byte	0                               ;     has no landing pad
	.byte	0                               ;   On action: cleanup
Lcst_end4:
	.p2align	2
                                        ; -- End function
	.section	__TEXT,__text,regular,pure_instructions
	.p2align	2                               ; -- Begin function OUTLINED_FUNCTION_0
_OUTLINED_FUNCTION_0:                   ; @OUTLINED_FUNCTION_0 Function
	.cfi_startproc
; %bb.0:
	ldrb	w11, [x9], #1
	ldrb	w12, [x10], #1
	sub	x8, x8, #1
	cmp	w11, w12
	ret
	.cfi_endproc
                                        ; -- End function
	.section	__TEXT,__cstring,cstring_literals
l_.str:                                 ; @.str
	.asciz	"Welcome to the Student Database"

l_.str.1:                               ; @.str.1
	.asciz	"Would You Like to Make a Database?\n1: Yes\n2: No"

l_.str.2:                               ; @.str.2
	.asciz	"\nSelect From the Following Options:\n"

l_.str.3:                               ; @.str.3
	.asciz	"1: Add a New Student \n2: Delete a Student \n3: Edit a Student \n4: Print All Students \n5: Exit\n\n"

l_.str.4:                               ; @.str.4
	.asciz	"Enter Information of the Student:\n"

l_.str.5:                               ; @.str.5
	.asciz	"What is Their Name?: "

l_.str.6:                               ; @.str.6
	.asciz	"What is Their Student ID?: "

l_.str.7:                               ; @.str.7
	.asciz	"What is Their GPA?: "

l_.str.8:                               ; @.str.8
	.asciz	"What is Their Grade level: "

l_.str.9:                               ; @.str.9
	.asciz	"What is their Age?: "

l_.str.10:                              ; @.str.10
	.asciz	"Please enter Their Student ID: "

l_.str.11:                              ; @.str.11
	.asciz	"Enter the ID of the Student You Want to Edit:\n"

l_.str.12:                              ; @.str.12
	.asciz	"What Is Their Name?: "

l_.str.13:                              ; @.str.13
	.asciz	"What Is Their Current ID: "

l_.str.14:                              ; @.str.14
	.asciz	"What Is Their Current GPA?: "

l_.str.15:                              ; @.str.15
	.asciz	"What is their New Grade: "

l_.str.16:                              ; @.str.16
	.asciz	"Thank You! Program Terminated"

l_.str.17:                              ; @.str.17
	.asciz	"Number is Out of Bounds"

l_.str.18:                              ; @.str.18
	.asciz	"Student "

l_.str.21:                              ; @.str.21
	.asciz	"\n"

l_.str.22:                              ; @.str.22
	.asciz	"Name: "

l_.str.23:                              ; @.str.23
	.asciz	" "

l_.str.24:                              ; @.str.24
	.asciz	"Student ID: "

l_.str.25:                              ; @.str.25
	.asciz	"GPA: "

l_.str.26:                              ; @.str.26
	.asciz	"Grade: "

l_.str.27:                              ; @.str.27
	.asciz	"Age: "

.subsections_via_symbols
