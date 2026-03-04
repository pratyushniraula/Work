#lang racket

(define name 30)

(define (bruh x y)
  (+ x y))

(define (op x y z)
  (x y z))
(define (λ p d f)
  (bruh p d))

(define bruh2 (lambda (x y) (+ x y)))
;λ
(define thing (lambda (t [a 2]) (t a)))