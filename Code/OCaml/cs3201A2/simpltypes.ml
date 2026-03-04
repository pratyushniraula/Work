(* SIMPL variable names are represented as strings *)
type varname = string

(* SIMPL arithmetic expressions *)
type iarith =
| Const of int
| Var of varname
| Plus of (iarith * iarith)
| Minus of (iarith * iarith)
| Times of (iarith * iarith)

(* SIMPL boolean expressions *)
type ibool =
| True
| False
| Leq of (iarith * iarith)
| Conj of (ibool * ibool)
| Disj of (ibool * ibool)
| Neg of ibool

(* SIMPL commands *)
type icmd =
| Skip
| Seq of (icmd * icmd)
| Assign of (varname * iarith)
| Cond of (ibool * icmd * icmd)
| While of (ibool * icmd)

