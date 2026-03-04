#lang racket

;; Define the student structure
(struct student (name studentID gpa grade age next))

;; Function to add a student (linked list style)
(define (add-student head name id gpa grade age)
  (if (null? head)
      (student name id gpa grade age null)  ;; New head of list
      (student name id gpa grade age head))) ;; Insert at head

;; Function to delete a student by ID
(define (delete-student head id)
  (cond
    [(null? head) null] ;; List is empty
    [(equal? (student-studentID head) id) (student-next head)] ;; Match found, skip this node
    [else (student (student-name head)
                   (student-studentID head)
                   (student-gpa head)
                   (student-grade head)
                   (student-age head)
                   (delete-student (student-next head) id))])) ;; Recur for next node

;; Function to edit a student
(define (edit-student head name old-id new-id gpa grade age)
  (cond
    [(null? head) null] ;; List is empty
    [(equal? (student-studentID head) old-id) ;; Match found, update
     (student name new-id gpa grade age (student-next head))]
    [else (student (student-name head)
                   (student-studentID head)
                   (student-gpa head)
                   (student-grade head)
                   (student-age head)
                   (edit-student (student-next head) name old-id new-id gpa grade age))])) ;; Recur for next node

;; Function to print all students
(define (print-students head)
  (when (not (null? head))
    (printf "Name: ~a, ID: ~a, GPA: ~a, Grade: ~a, Age: ~a\n"
            (student-name head)
            (student-studentID head)
            (student-gpa head)
            (student-grade head)
            (student-age head))
    (print-students (student-next head)))) ;; Recur for next node

;; Main program
(define (main)
  (let loop ((head null)) ;; Start with empty list
    (displayln "Select an option:\n1: Add Student\n2: Delete Student\n3: Edit Student\n4: Print Students\n5: Exit")
    (define selection (read))
    (cond
      [(= selection 1) ;; Add student
       (display "Enter name: ") (define name (read))
       (display "Enter ID: ") (define id (read))
       (display "Enter GPA: ") (define gpa (read))
       (display "Enter grade: ") (define grade (read))
       (display "Enter age: ") (define age (read))
       (loop (add-student head name id gpa grade age))]
      [(= selection 2) ;; Delete student
       (display "Enter ID of student to delete: ") (define id (read))
       (loop (delete-student head id))]
      [(= selection 3) ;; Edit student
       (display "Enter old ID of student to edit: ") (define old-id (read))
       (display "Enter new name: ") (define name (read))
       (display "Enter new ID: ") (define new-id (read))
       (display "Enter new GPA: ") (define gpa (read))
       (display "Enter new grade: ") (define grade (read))
       (display "Enter new age: ") (define age (read))
       (loop (edit-student head name old-id new-id gpa grade age))]
      [(= selection 4) ;; Print all students
       (print-students head)
       (loop head)]
      [(= selection 5) (displayln "Exiting...") (void)] ;; Exit
      [else (displayln "Invalid selection, try again.") (loop head)])))

(main) ;; Run the program
