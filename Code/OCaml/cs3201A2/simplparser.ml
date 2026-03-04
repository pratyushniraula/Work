type token =
  | LPAREN
  | RPAREN
  | SEMICOLON
  | ASSIGN
  | LEQ
  | OR
  | AND
  | NOT
  | PLUS
  | MINUS
  | TIMES
  | EOF
  | SKIP
  | IF
  | THEN
  | ELSE
  | WHILE
  | DO
  | TRUE
  | FALSE
  | NUM of (
# 24 "simplparser.mly"
       int
# 26 "simplparser.ml"
)
  | VAR of (
# 25 "simplparser.mly"
       string
# 31 "simplparser.ml"
)

open Parsing
let _ = parse_error;;
# 2 "simplparser.mly"
  open Simpltypes
  open Parsing
  open Lexing
  let report_error pos1 pos2 msg =
    failwith ("Line "^(string_of_int pos1.pos_lnum)^", char"^
      (if pos1.pos_cnum = pos2.pos_cnum then
         " "^(string_of_int (pos1.pos_cnum - pos1.pos_bol + 1))
       else
         ("s "^(string_of_int (pos1.pos_cnum - pos1.pos_bol + 1))^
          "-"^(string_of_int (pos2.pos_cnum - pos1.pos_bol + 1))))^": "^msg);;
  let unmatched opening_name opening_num closing_name =
    report_error (rhs_start_pos opening_num) (rhs_end_pos opening_num)
      ("Found "^opening_name^" with no matching "^closing_name^".");;
  let invalid num = report_error (rhs_start_pos num) (rhs_end_pos num)
  let parse_error msg =
    report_error (symbol_start_pos ()) (symbol_end_pos ()) msg;;
  let missing num =
    report_error (rhs_end_pos num) (rhs_end_pos num) "missing semicolon";;
# 55 "simplparser.ml"
let yytransl_const = [|
  257 (* LPAREN *);
  258 (* RPAREN *);
  259 (* SEMICOLON *);
  260 (* ASSIGN *);
  261 (* LEQ *);
  262 (* OR *);
  263 (* AND *);
  264 (* NOT *);
  265 (* PLUS *);
  266 (* MINUS *);
  267 (* TIMES *);
    0 (* EOF *);
  268 (* SKIP *);
  269 (* IF *);
  270 (* THEN *);
  271 (* ELSE *);
  272 (* WHILE *);
  273 (* DO *);
  274 (* TRUE *);
  275 (* FALSE *);
    0|]

let yytransl_block = [|
  276 (* NUM *);
  277 (* VAR *);
    0|]

let yylhs = "\255\255\
\001\000\001\000\001\000\001\000\002\000\002\000\002\000\003\000\
\003\000\003\000\003\000\003\000\003\000\003\000\005\000\005\000\
\005\000\005\000\005\000\005\000\005\000\005\000\004\000\004\000\
\004\000\004\000\004\000\004\000\004\000\004\000\000\000"

let yylen = "\002\000\
\001\000\003\000\002\000\002\000\001\000\003\000\003\000\001\000\
\003\000\006\000\005\000\003\000\004\000\003\000\001\000\001\000\
\003\000\003\000\003\000\002\000\003\000\003\000\001\000\002\000\
\001\000\003\000\003\000\003\000\003\000\003\000\002\000"

let yydefred = "\000\000\
\000\000\000\000\000\000\008\000\000\000\000\000\000\000\031\000\
\000\000\005\000\000\000\000\000\000\000\000\000\015\000\016\000\
\023\000\025\000\000\000\000\000\000\000\000\000\000\000\004\000\
\007\000\006\000\000\000\000\000\020\000\024\000\000\000\000\000\
\000\000\000\000\012\000\000\000\000\000\000\000\014\000\000\000\
\000\000\000\000\002\000\030\000\029\000\022\000\021\000\000\000\
\000\000\000\000\028\000\000\000\018\000\000\000\013\000\000\000\
\011\000\000\000\010\000"

let yydgoto = "\002\000\
\008\000\009\000\010\000\019\000\020\000"

let yysindex = "\004\000\
\035\255\000\000\035\255\000\000\008\255\008\255\254\254\000\000\
\046\255\000\000\038\255\008\255\008\255\244\254\000\000\000\000\
\000\000\000\000\098\255\076\255\037\255\032\255\035\255\000\000\
\000\000\000\000\091\255\092\255\000\000\000\000\032\255\032\255\
\032\255\032\255\000\000\008\255\008\255\035\255\000\000\035\255\
\032\255\001\255\000\000\000\000\000\000\000\000\000\000\001\255\
\009\255\009\255\000\000\043\255\000\000\004\255\000\000\095\255\
\000\000\035\255\000\000"

let yyrindex = "\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\045\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\046\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\041\000\000\000\000\000\000\000\000\000\000\000\063\255\
\001\000\021\000\000\000\072\255\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000"

let yygindex = "\000\000\
\011\000\233\255\030\000\247\255\250\255"

let yytablesize = 318
let yytable = "\021\000\
\026\000\022\000\027\000\057\000\001\000\028\000\029\000\030\000\
\012\000\032\000\033\000\034\000\042\000\011\000\054\000\013\000\
\055\000\014\000\058\000\034\000\027\000\048\000\049\000\050\000\
\051\000\015\000\016\000\017\000\018\000\052\000\053\000\056\000\
\041\000\043\000\059\000\003\000\039\000\025\000\024\000\026\000\
\009\000\014\000\036\000\037\000\001\000\003\000\004\000\005\000\
\023\000\037\000\006\000\017\000\018\000\040\000\000\000\007\000\
\000\000\004\000\005\000\000\000\000\000\006\000\017\000\000\000\
\017\000\000\000\007\000\000\000\017\000\017\000\000\000\019\000\
\000\000\019\000\000\000\035\000\017\000\019\000\000\000\017\000\
\000\000\036\000\037\000\000\000\000\000\019\000\000\000\000\000\
\019\000\038\000\044\000\046\000\045\000\047\000\044\000\031\000\
\045\000\036\000\037\000\032\000\033\000\034\000\031\000\032\000\
\033\000\034\000\032\000\033\000\034\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\026\000\000\000\026\000\026\000\000\000\026\000\026\000\026\000\
\000\000\026\000\026\000\000\000\026\000\026\000\026\000\026\000\
\026\000\026\000\000\000\000\000\027\000\026\000\027\000\027\000\
\000\000\027\000\027\000\027\000\000\000\027\000\027\000\000\000\
\027\000\027\000\027\000\027\000\027\000\027\000\000\000\000\000\
\009\000\027\000\009\000\009\000\001\000\003\000\001\000\003\000\
\000\000\000\000\000\000\000\000\009\000\009\000\000\000\009\000\
\009\000\000\000\000\000\000\000\000\000\009\000"

let yycheck = "\006\000\
\000\000\004\001\012\000\000\001\001\000\012\000\013\000\020\001\
\001\001\009\001\010\001\011\001\022\000\003\000\038\000\008\001\
\040\000\010\001\015\001\011\001\000\000\031\000\032\000\033\000\
\034\000\018\001\019\001\020\001\021\001\036\000\037\000\041\000\
\001\001\023\000\058\000\001\001\000\001\000\001\009\000\002\001\
\000\000\010\001\006\001\007\001\000\000\000\000\012\001\013\001\
\003\001\007\001\016\001\020\001\021\001\017\001\255\255\021\001\
\255\255\012\001\013\001\255\255\255\255\016\001\000\001\255\255\
\002\001\255\255\021\001\255\255\006\001\007\001\255\255\000\001\
\255\255\002\001\255\255\000\001\014\001\006\001\255\255\017\001\
\255\255\006\001\007\001\255\255\255\255\014\001\255\255\255\255\
\017\001\014\001\000\001\000\001\002\001\002\001\000\001\005\001\
\002\001\006\001\007\001\009\001\010\001\011\001\005\001\009\001\
\010\001\011\001\009\001\010\001\011\001\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\255\
\000\001\255\255\002\001\003\001\255\255\005\001\006\001\007\001\
\255\255\009\001\010\001\255\255\012\001\013\001\014\001\015\001\
\016\001\017\001\255\255\255\255\000\001\021\001\002\001\003\001\
\255\255\005\001\006\001\007\001\255\255\009\001\010\001\255\255\
\012\001\013\001\014\001\015\001\016\001\017\001\255\255\255\255\
\000\001\021\001\002\001\003\001\000\001\000\001\002\001\002\001\
\255\255\255\255\255\255\255\255\012\001\013\001\255\255\015\001\
\016\001\255\255\255\255\255\255\255\255\021\001"

let yynames_const = "\
  LPAREN\000\
  RPAREN\000\
  SEMICOLON\000\
  ASSIGN\000\
  LEQ\000\
  OR\000\
  AND\000\
  NOT\000\
  PLUS\000\
  MINUS\000\
  TIMES\000\
  EOF\000\
  SKIP\000\
  IF\000\
  THEN\000\
  ELSE\000\
  WHILE\000\
  DO\000\
  TRUE\000\
  FALSE\000\
  "

let yynames_block = "\
  NUM\000\
  VAR\000\
  "

let yyact = [|
  (fun _ -> failwith "parser")
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : 'cmd) in
    Obj.repr(
# 46 "simplparser.mly"
               ( _1 )
# 252 "simplparser.ml"
               : Simpltypes.icmd))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'cmd) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : Simpltypes.icmd) in
    Obj.repr(
# 47 "simplparser.mly"
                                   ( Seq (_1,_3) )
# 260 "simplparser.ml"
               : Simpltypes.icmd))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 1 : 'cmd) in
    Obj.repr(
# 48 "simplparser.mly"
                         ( invalid 2 "superfluous semicolon" )
# 267 "simplparser.ml"
               : Simpltypes.icmd))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 1 : 'cmd) in
    let _2 = (Parsing.peek_val __caml_parser_env 0 : 'simplecmd) in
    Obj.repr(
# 49 "simplparser.mly"
                         ( missing 1 )
# 275 "simplparser.ml"
               : Simpltypes.icmd))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : 'simplecmd) in
    Obj.repr(
# 52 "simplparser.mly"
               ( _1 )
# 282 "simplparser.ml"
               : 'cmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : Simpltypes.icmd) in
    Obj.repr(
# 53 "simplparser.mly"
                             ( _2 )
# 289 "simplparser.ml"
               : 'cmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : Simpltypes.icmd) in
    Obj.repr(
# 54 "simplparser.mly"
                            ( unmatched "(" 1 ")" )
# 296 "simplparser.ml"
               : 'cmd))
; (fun __caml_parser_env ->
    Obj.repr(
# 57 "simplparser.mly"
                ( Skip )
# 302 "simplparser.ml"
               : 'simplecmd))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : string) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'parse_arith) in
    Obj.repr(
# 58 "simplparser.mly"
                                  ( Assign (_1,_3) )
# 310 "simplparser.ml"
               : 'simplecmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 4 : 'parse_bool) in
    let _4 = (Parsing.peek_val __caml_parser_env 2 : 'cmd) in
    let _6 = (Parsing.peek_val __caml_parser_env 0 : 'cmd) in
    Obj.repr(
# 59 "simplparser.mly"
                                           ( Cond (_2,_4,_6) )
# 319 "simplparser.ml"
               : 'simplecmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 3 : 'parse_bool) in
    let _4 = (Parsing.peek_val __caml_parser_env 1 : 'cmd) in
    Obj.repr(
# 60 "simplparser.mly"
                                        ( unmatched "if" 1 "else" )
# 327 "simplparser.ml"
               : 'simplecmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'parse_bool) in
    Obj.repr(
# 61 "simplparser.mly"
                               ( unmatched "if" 1 "then" )
# 334 "simplparser.ml"
               : 'simplecmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 2 : 'parse_bool) in
    let _4 = (Parsing.peek_val __caml_parser_env 0 : 'cmd) in
    Obj.repr(
# 62 "simplparser.mly"
                                   ( While (_2,_4) )
# 342 "simplparser.ml"
               : 'simplecmd))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'parse_bool) in
    Obj.repr(
# 63 "simplparser.mly"
                                  ( unmatched "while" 1 "do" )
# 349 "simplparser.ml"
               : 'simplecmd))
; (fun __caml_parser_env ->
    Obj.repr(
# 66 "simplparser.mly"
                 ( True )
# 355 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    Obj.repr(
# 67 "simplparser.mly"
                  ( False )
# 361 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'parse_arith) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'parse_arith) in
    Obj.repr(
# 68 "simplparser.mly"
                                        ( Leq (_1,_3) )
# 369 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'parse_bool) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'parse_bool) in
    Obj.repr(
# 69 "simplparser.mly"
                                      ( Conj (_1,_3) )
# 377 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'parse_bool) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'parse_bool) in
    Obj.repr(
# 70 "simplparser.mly"
                                     ( Disj (_1,_3) )
# 385 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 0 : 'parse_bool) in
    Obj.repr(
# 71 "simplparser.mly"
                           ( Neg _2 )
# 392 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'parse_bool) in
    Obj.repr(
# 72 "simplparser.mly"
                                     ( _2 )
# 399 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'parse_bool) in
    Obj.repr(
# 73 "simplparser.mly"
                                    ( unmatched "(" 1 ")" )
# 406 "simplparser.ml"
               : 'parse_bool))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : int) in
    Obj.repr(
# 76 "simplparser.mly"
                 ( Const _1 )
# 413 "simplparser.ml"
               : 'parse_arith))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 0 : int) in
    Obj.repr(
# 77 "simplparser.mly"
                       ( Const (-_2) )
# 420 "simplparser.ml"
               : 'parse_arith))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : string) in
    Obj.repr(
# 78 "simplparser.mly"
                 ( Var _1 )
# 427 "simplparser.ml"
               : 'parse_arith))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'parse_arith) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'parse_arith) in
    Obj.repr(
# 79 "simplparser.mly"
                                          ( Plus (_1,_3) )
# 435 "simplparser.ml"
               : 'parse_arith))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'parse_arith) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'parse_arith) in
    Obj.repr(
# 80 "simplparser.mly"
                                           ( Minus (_1,_3) )
# 443 "simplparser.ml"
               : 'parse_arith))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'parse_arith) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'parse_arith) in
    Obj.repr(
# 81 "simplparser.mly"
                                           ( Times (_1,_3) )
# 451 "simplparser.ml"
               : 'parse_arith))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'parse_arith) in
    Obj.repr(
# 82 "simplparser.mly"
                                       ( _2 )
# 458 "simplparser.ml"
               : 'parse_arith))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'parse_arith) in
    Obj.repr(
# 83 "simplparser.mly"
                                      ( unmatched "(" 1 ")" )
# 465 "simplparser.ml"
               : 'parse_arith))
(* Entry parse_cmd *)
; (fun __caml_parser_env -> raise (Parsing.YYexit (Parsing.peek_val __caml_parser_env 0)))
|]
let yytables =
  { Parsing.actions=yyact;
    Parsing.transl_const=yytransl_const;
    Parsing.transl_block=yytransl_block;
    Parsing.lhs=yylhs;
    Parsing.len=yylen;
    Parsing.defred=yydefred;
    Parsing.dgoto=yydgoto;
    Parsing.sindex=yysindex;
    Parsing.rindex=yyrindex;
    Parsing.gindex=yygindex;
    Parsing.tablesize=yytablesize;
    Parsing.table=yytable;
    Parsing.check=yycheck;
    Parsing.error_function=parse_error;
    Parsing.names_const=yynames_const;
    Parsing.names_block=yynames_block }
let parse_cmd (lexfun : Lexing.lexbuf -> token) (lexbuf : Lexing.lexbuf) =
   (Parsing.yyparse yytables 1 lexfun lexbuf : Simpltypes.icmd)
