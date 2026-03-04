(*Pratyush Niraula*)
(*https://dev.realworldocaml.org/variables-and-functions.html*)
(*https://ocaml.org/docs/lists*)
(*1.a*)
type fos = True | False | V of string 
| Or of fos * fos 
| Not of fos
| Forall of string * fos 
| ThereExists of string * fos;;

(*1.b*)
let mysentence : fos = Forall ("x" , Or ( Forall ("x", V "x"), Not(V "x")));;

(*1.c*)
let freevars s =
        let rec recurseFunc notFree s =
                match s with
                | True -> []
                | False -> []
                | V x -> if List.mem x notFree then [] else [x]
                | Or (q, r) -> recurseFunc notFree q @ recurseFunc notFree r
                | Not t -> recurseFunc notFree t
                | Forall (u, v) -> recurseFunc (u::notFree) v
                | ThereExists (w, y) -> recurseFunc (w::notFree) y
        in recurseFunc [] s;;

(*1.d*)

let tautology s = 
        let rec istrue tfv s =
                match s with 
                | True -> true
                | False -> false
                | V v -> if List.mem v tfv then true else false
                | Or (w, x) -> istrue tfv w || istrue tfv x
                | Not s1 -> if istrue tfv s1 then false else true
                | Forall (y, z) -> istrue (y::tfv) z && istrue tfv z
                | ThereExists (a, b) -> istrue(a::tfv) b || istrue tfv b
        in
        freevars s = [] && istrue [] s;;

(*1.e*)
let rec string_of_fos s =
        match s with
        | True -> "T"
        | False -> "F"
        | V x -> x
        | Or (y, z) -> "(" ^ string_of_fos y ^ "\\/" ^ string_of_fos z ^ ")"
        | Not a -> "(" ^ "~" ^ string_of_fos a ^ ")"
        | Forall (b, c) -> "A" ^ b ^ "." ^ "(" ^ string_of_fos c ^ ")"
        | ThereExists (d, e) -> "E" ^ d ^ ".(" ^ string_of_fos e ^ ")";;

(*2*)
let update (f : 'a -> 'b) (x : 'a) (y : 'b) : 'a -> 'b = 
        fun z -> if z = x then y else f z;;

(*3*)
let compose_n (f : 'a -> 'a) (n: int) : 'a -> 'a = 
        if n <= 0 then (fun x -> x) else 
                let rec recurseAgain (soFar : 'a -> 'a) (loopLeft : int) : 'a-> 'a =
                        if loopLeft = 0 then soFar else
                                recurseAgain (fun x -> f (soFar x)) (loopLeft - 1)
                in
                recurseAgain (fun x -> x) n;;

