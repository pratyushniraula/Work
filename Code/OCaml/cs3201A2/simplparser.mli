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
# 26 "simplparser.mli"
)
  | VAR of (
# 25 "simplparser.mly"
       string
# 31 "simplparser.mli"
)

val parse_cmd :
  (Lexing.lexbuf  -> token) -> Lexing.lexbuf -> Simpltypes.icmd
