%{
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
%}

%token LPAREN RPAREN SEMICOLON ASSIGN LEQ OR AND NOT PLUS MINUS TIMES EOF
%token SKIP IF THEN ELSE WHILE DO TRUE FALSE
%token<int> NUM
%token<string> VAR

%right SEMICOLON
%nonassoc SKIP ASSIGN IF THEN ELSE WHILE DO

%left OR
%left AND
%right NOT
%nonassoc TRUE FALSE LEQ

%left PLUS MINUS
%left TIMES
%nonassoc NUM VAR

%nonassoc LPAREN RPAREN EOF

%start parse_cmd
%type<Simpltypes.icmd> parse_cmd

%%

parse_cmd: cmd { $1 }
         | cmd SEMICOLON parse_cmd { Seq ($1,$3) }
         | cmd SEMICOLON { invalid 2 "superfluous semicolon" }
         | cmd simplecmd { missing 1 }
;

cmd: simplecmd { $1 }
   | LPAREN parse_cmd RPAREN { $2 }
   | LPAREN parse_cmd error { unmatched "(" 1 ")" }
;

simplecmd: SKIP { Skip }
         | VAR ASSIGN parse_arith { Assign ($1,$3) }
         | IF parse_bool THEN cmd ELSE cmd { Cond ($2,$4,$6) }
         | IF parse_bool THEN cmd error { unmatched "if" 1 "else" }
         | IF parse_bool error { unmatched "if" 1 "then" }
         | WHILE parse_bool DO cmd { While ($2,$4) }
         | WHILE parse_bool error { unmatched "while" 1 "do" }
;

parse_bool: TRUE { True }
          | FALSE { False }
          | parse_arith LEQ parse_arith { Leq ($1,$3) }
          | parse_bool AND parse_bool { Conj ($1,$3) }
          | parse_bool OR parse_bool { Disj ($1,$3) }
          | NOT parse_bool { Neg $2 }
          | LPAREN parse_bool RPAREN { $2 }
          | LPAREN parse_bool error { unmatched "(" 1 ")" }
;

parse_arith: NUM { Const $1 }
           | MINUS NUM { Const (-$2) }
           | VAR { Var $1 }
           | parse_arith PLUS parse_arith { Plus ($1,$3) }
           | parse_arith MINUS parse_arith { Minus ($1,$3) }
           | parse_arith TIMES parse_arith { Times ($1,$3) }
           | LPAREN parse_arith RPAREN { $2 }
           | LPAREN parse_arith error { unmatched "(" 1 ")" }
;

