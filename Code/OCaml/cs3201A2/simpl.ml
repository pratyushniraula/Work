open Simpltypes

(*Pratyush Niraula CS4301.004*)




(* The 'store' models the machine's memory as a mapping from
 * variable names to integers. *)
type store = varname -> int

let init_store (l : (varname*int) list) : store =
  fun v -> List.assoc v l;;
  (* YOUR CODE GOES HERE
   * Replace the following line with code that takes a list of
   * (variable,integer) pairs and returns a store that maps each
   * variable to each corresponding integer. If your store function
   * is applied to a variable not in the list, it may return any
   * integer or it may raise an exception. *)
  (*(fun _ -> 0);;*)  (* <-- delete this line *)

let rec eval_arith (s:store) (a:iarith) : int =
  (* YOUR CODE GOES HERE
   * Replace the following line with code that evaluates iarith
   * expression 'a' in memory state 's' and returns an integer.
   * The type 'iarith' is defined in simpltypes.ml. *)
  match a with
  | Const n -> n
  | Var v -> s v
  | Plus (a1, a2) -> eval_arith s a1 + eval_arith s a2
  | Minus (a1, a2) -> eval_arith s a1 - eval_arith s a2
  | Times (a1, a2) -> eval_arith s a1 * eval_arith s a2;;

let rec eval_bool (s:store) (b:ibool) : bool =
  (* YOUR CODE GOES HERE
   * Replace the following line with code that evaluates ibool
   * expression 'b' in memory state 's' and returns a bool.
   * The type 'ibool' is defined in simpltypes.ml. *)
  match b with
  | True -> true 
  | False -> false
  | Leq (b1, b2) -> eval_arith s b1 <= eval_arith s b2
  | Conj (b1, b2) -> eval_bool s b1 && eval_bool s b2
  | Disj (b1, b2) -> eval_bool s b1 || eval_bool s b2
  | Neg b1 -> not (eval_bool s b1);;

let update (s:store) (x:varname) (v:int) : store =
        fun y -> if y = x then v else s y;;

let rec exec_cmd (s:store) (c:icmd) : store =
  (* YOUR CODE GOES HERE
   * Replace the following line with code that executes icmd
   * 'c' in memory state 's' and returns the new memory state
   * that results. Type 'icmd' is defined in simpltypes.ml. *)
  match c with 
  | Skip -> s
  | Seq (c1, c2) -> let sPrime = exec_cmd s c1 in exec_cmd sPrime c2
  | Assign (v, c1) -> let n = eval_arith s c1 in update s v n
  | Cond (c1, c2, c3) -> if eval_bool s c1 then exec_cmd s c2 else exec_cmd s c3
  | While (c1, c2) -> if eval_bool s c1 then exec_cmd (exec_cmd s c2) c else s;;

(* This is the main entrypoint of the code. You don't need to understand
 * how it works to complete the assignment, but here's a short explanation
 * for those interested:
 *  * The first 'let' statement reads the .sim file and invokes an external
 *    parser (defined in simplparser.mly) to convert the file into an icmd
 *    structure.
 *  * The next 'let' statement calls your init_store code to create a
 *    store s from the rest of the command-line arguments. The first
 *    command-line argument gets assigned to variable "arg0", the next to
 *    "arg1", etc.
 *  * The third 'let' statement calls your exec_cmd code to execute the icmd
 *    starting in the initial store.
 *  * If the SIMPL program terminates (and your code is correct) then s2 will
 *    eventually hold the final store that results from executing the
 *    program. We then print out the value of variable "ret" as the result
 *    of the computation.
 *)
let main () =
  (match (Array.to_list Sys.argv) with
   | [] -> raise (Sys_error "invalid argument list")
   | [_] -> raise (Failure "please specify a program to interpret")
   | _::prog::args ->
      let c = (Simplparser.parse_cmd Simpllexer.token 
                (Lexing.from_channel (open_in prog))) in
      let s = init_store (Array.to_list (Array.mapi
                (fun i a -> ("arg"^(string_of_int i),
                  (try int_of_string a
                   with Failure _ -> raise (Failure "args must be ints"))))
                (Array.of_list args))) in
      let s2 = exec_cmd s c in
        (print_string (match (try Some (s2 "ret") with Not_found -> None) with
                       | None -> "<no value returned>"
                       | Some n -> string_of_int n);
         print_newline ()));;

main ();;

