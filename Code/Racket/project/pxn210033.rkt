#lang racket

;Pratyush Niraula
;pxn210033
;CS4337.001
;Project 1

; function 1
(define (process-9 b) (b 9)) ;does something to 9 depending on what that something is (b)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;function 2
(define (mymap funct lis)
  (define (mymap-helper funct lis) ;helper function to run recursion
    (cond
      [(null? lis) '()] ;if null return empty list
      [else (cons (funct (first lis)) (mymap-helper funct (rest lis)))])) ;run recursion for every element in the list
  (mymap-helper funct lis)) ;set helper function to the output, running it until it produces an answer

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 3
(define (second-min lis)
  (let ([compressed-lis (sort (remove-duplicates lis) <)]) ;sort list and remove duplicates
    (cond
      [(< (length compressed-lis) 2) ;if there are less than 2 unique elements make display error
        (error "please provide a list with more than 2 elements")] ;error
      [else (second compressed-lis)]))) ;else display the second element of the lsit

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 4
(define (zipper x y)
  (cond
   [(null? x) '()] ;if x is null return empty list (base case)
   [(null? y) '()] ;if y is null return empty list (base case)
   [else (cons (list (first x) (first y)) ;else construct a list based on the first elements on both lists
   (zipper (rest x) (rest y)))])) ;call function recursively

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 5
(define (classify funct lis)
  (cond
    [(null? lis) '(() ())] ;if null return two empty lists
    [else
     (let ([res (classify funct (rest lis))] ;Recursively classify the rest of the list
            [first-elem (first lis)]) ;Get the first element
        (if (funct first-elem) ;apply predicate
            (list (cons first-elem (first res)) (second res))  ;if true then put in first list
            (list (first res) (cons first-elem (second res)))))])) ;else put in second list

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 6
(define (member-n-times? e i l)
  (define (member-n-times-helper e i l counter) ;helper function to do recursion
    (cond
      [(null? l) counter]  ;if list is null return the counter
      [(= e (first l)) (member-n-times-helper e i (rest l) (+ counter 1))] ;if there is a match add one to counter and recurse
      [else (member-n-times-helper e i (rest l) counter)])) ;else just recurse without adding one to counter

  (let ([count (member-n-times-helper e i l 0)]) ;call the function

    (cond
      [(= count i) #t] ;if the integer that holds total count is the same as count return true
      [else #f]))) ;else return false

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 7 (using wikipedia's interpretation of the luhn algorithm)
(define(luhn n)                          
;helper function converts digits to list for processing - operates recursively on each digit
(define (to-lst x)                    
 (cond [(zero? x) '()]               ;base case - if num is 0 return empty list to start building
   [else (append(to-lst(quotient x 10))        ;recursively process remaining digits by dividing
          (list(remainder x 10)))]))      ;get last digit with remainder and add to growing list

;processes digits according to luhn rules - doubles every other digit from right and handles > 9
(define(process xs)                   
 (define(transform lst i)               ;recursive helper that handles doubling logic
   (if(null? lst) '()                        ;base case - empty list means we're done
      (let([digit(car lst)])          ;get current digit we're processing
        (if(odd? i) (let([doubled (* 2 digit)])    ;if odd index, double the digit per luhn rules 
             (cons(if(> doubled 9)(- doubled 9) doubled)           ;if > 9 subtract 9, else keep doubled value
                 (transform(cdr lst)(add1 i))))
           (cons digit (transform(cdr lst)(add1 i)))))))     ;even index - keep original digit and continue
 (reverse(transform(reverse xs)0)))          ;reverse list to process from right, then put back in order

;adds up all digits in the processed list - used to get final sum for validation
(define(sum-all sequence)                 
 (if(null? sequence) 0                              ;base case - empty sequence sums to 0
    (+  (car sequence)(sum-all(cdr sequence)))))    ;add current digit and recursively sum rest

;checks if final sum is valid according to luhn algorithm - must be divisible by 10
(define(valid? digits)                    
 (zero?(remainder(sum-all(process digits)) 10)))   ;if remainder of sum divided by 10 is 0, number is valid

(valid?(to-lst n)))                    ;convert input to list and run validation check


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 8
(define (my-sorted? comp lis)
  (cond
    [(null? lis) #t] ;if list is null return true
    [(null? (rest lis)) #t] ;if there is only one element return true
    [(comp (first lis) (second lis)) (my-sorted? comp (cdr lis))] ;compare first and second element and recurse to next
    [else #f]) ;if the recursion reaches the end it will return true, else it will be false
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 9
(define (my-flatten lis)
  (cond
    [(null? lis) '()] ; if list is null return empty list
    [(list? (first lis)) (append (my-flatten (first lis)) (my-flatten (rest lis)))] ;recursively dig deep into any possible list layers
    [else (cons (first lis) (my-flatten (rest lis)))] ;or just add element to the new list if it is not a list
    )

  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; function 10
(define (my-list-ref lis i)
  (cond
    [(< (- (length lis) 1) i) (error "ERROR: Index out of bounds\n")] ;error message if out of bounds
    [(= i 0) (first lis)] ;if i is 0 return first element
    [else (my-list-ref (rest lis) (- i 1))])) ;any other case recursively go down the list until i is 0
