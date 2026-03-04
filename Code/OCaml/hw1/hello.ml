(*
  Name: YOUR NAME
  Email: YOUR EMAIL
*)

(* (1a) Type for first-order logical sentences (FOS) *)
type fos =
  | True
  | False
  | Var of string
  | Or of fos * fos
  | Not of fos
  | Forall of string * fos
  | Exists of string * fos

(* (1b) mysentence = ∀x.((∀x.x) ∨ ¬x) *)
let mysentence : fos =
  Forall ("x",
    Or (
      Forall ("x", Var "x"),
      Not (Var "x")
    )
  )

(* (1c) freevars : fos -> string list *)
let freevars (s : fos) : string list =
  let rec helper (bound : string list) (t : fos) : string list =
    match t with
    | True | False -> []
    | Var v -> if List.mem v bound then [] else [v]
    | Or (t1, t2) -> helper bound t1 @ helper bound t2
    | Not t1 -> helper bound t1
    | Forall (v, t1) -> helper (v :: bound) t1
    | Exists (v, t1) -> helper (v :: bound) t1
  in
  helper [] s

(* (1d) istrue helper and tautology : fos -> bool *)
let rec istrue (tfv : string list) (s : fos) : bool =
  match s with
  | True -> true
  | False -> false
  | Var v -> List.mem v tfv
  | Or (s1, s2) -> istrue tfv s1 || istrue tfv s2
  | Not s1 -> not (istrue tfv s1)
  | Forall (v, s1) ->
      (* v=true branch AND v=false branch *)
      istrue (v :: tfv) s1 && istrue tfv s1
  | Exists (v, s1) ->
      (* v=true branch OR v=false branch *)
      istrue (v :: tfv) s1 || istrue tfv s1

let tautology (s : fos) : bool =
  freevars s = [] && istrue [] s

(* (1e) string_of_fos : fos -> string *)
let rec string_of_fos (s : fos) : string =
  match s with
  | True -> "T"
  | False -> "F"
  | Var v -> v
  | Or (s1, s2) ->
      "(" ^ string_of_fos s1 ^ "\\/" ^ string_of_fos s2 ^ ")"
  | Not s1 ->
      "(~" ^ string_of_fos s1 ^ ")"
  | Forall (v, s1) ->
      "A" ^ v ^ ".(" ^ string_of_fos s1 ^ ")"
  | Exists (v, s1) ->
      "E" ^ v ^ ".(" ^ string_of_fos s1 ^ ")"

(* (2) update : ('a -> 'b) -> 'a -> 'b -> ('a -> 'b) *)
let update (f : 'a -> 'b) (x : 'a) (y : 'b) : 'a -> 'b =
  fun z -> if z = x then y else f z

(* (3) compose_n : ('a -> 'a) -> int -> ('a -> 'a) *)
let compose_n (f : 'a -> 'a) (n : int) : 'a -> 'a =
  if n <= 0 then (fun x -> x)
  else
    let rec loop (acc : 'a -> 'a) (k : int) : 'a -> 'a =
      if k = 0 then acc
      else loop (fun x -> f (acc x)) (k - 1)
    in
    loop (fun x -> x) n

(* (4) selectall : (int -> bool) -> 'a list -> 'a list *)
let selectall (f : int -> bool) (l : 'a list) : 'a list =
  let (_, acc) =
    List.fold_left
      (fun (i, chosen) x ->
         if f i then (i + 1, x :: chosen) else (i + 1, chosen))
      (1, [])
      l
  in
  acc

