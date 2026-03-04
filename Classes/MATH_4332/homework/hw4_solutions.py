"""
MATH 4332 - Assignment #4 (Intro to Python style solutions)
Problems 1–6 solved with simple, readable Python.
Save this file as 'hw4_solutions.py' and run with:  python hw4_solutions.py
"""

# ----------------------------
# Problem 1
# ----------------------------
def read_scores_and_report():
    """
    Reads test scores until a negative number is entered.
    Prints how many are A's (>= 90) and the average score.

    Input example during runtime:
    95 88 73 100 92 -1
    """
    scores = []
    print("Enter scores one by one (enter a negative number to finish):")
    while True:
        try:
            s = float(input("> "))
        except ValueError:
            print("Please enter a number.")
            continue
        if s < 0:
            break
        scores.append(s)

    if len(scores) == 0:
        print("No scores entered.")
        return

    a_count = sum(1 for x in scores if x >= 90)
    avg = sum(scores) / len(scores)
    print(f"Number of A's: {a_count}")
    print(f"Average: {avg:.2f}")


def count_as_and_average(scores):
    """Helper (non-interactive) for testing/demo."""
    if not scores:
        return 0, 0.0
    a_count = sum(1 for x in scores if x >= 90)
    avg = sum(scores) / len(scores)
    return a_count, avg


# ----------------------------
# Problem 2
# ----------------------------
def build_card_names():
    """Creates full deck names using format: '<Value> of <Suit>'."""
    suits = ['Hearts', 'Diamonds', 'Clubs', 'Spades']
    values = ['One', 'Two', 'Three', 'Four', 'Five', 'Six', 'Seven',
              'Eight', 'Nine', 'Ten', 'Jack', 'Queen', 'King', 'Ace']
    cards = ["{} of {}".format(value, suit) for suit in suits for value in values]
    return cards


# ----------------------------
# Problem 3
# ----------------------------
def digits_of(n):
    """Returns list of digits for a nonnegative integer n."""
    return [int(ch) for ch in str(n)]


def prod(nums):
    """Product of a list of numbers (no math.prod to keep it week-1–4 friendly)."""
    result = 1
    for x in nums:
        result *= x
    return result


def special_numbers_upto(limit=10000):
    """
    Finds all numbers less than 'limit' where:
    product_of_digits(number) + sum_of_digits(number) == number
    """
    found = []
    for n in range(1, limit):
        d = digits_of(n)
        if prod(d) + sum(d) == n:
            found.append(n)
    return found


# ----------------------------
# Problem 4
# ----------------------------
def build_course_dict(pairs):
    """
    Builds a dictionary from [(name, [courses...]), ...].
    Example:
        pairs = [
            ("Alice", ["CS101", "MATH140"]),
            ("Bob",   ["CS101", "HIST200"]),
            ...
        ]
    """
    d = {}
    for name, courses in pairs:
        d[name] = list(courses)  # copy to be safe
    return d


def students_in_course(course_dict, course_name):
    """Returns a list of names who took 'course_name'."""
    result = []
    for name, courses in course_dict.items():
        if course_name in courses:
            result.append(name)
    return result


def prompt_build_and_query_courses():
    """
    Interactive version: Asks for 5 people.
    Then asks for one course and prints who took it.
    """
    print("Enter 5 people's names and the courses they have taken.")
    pairs = []
    for i in range(1, 6):
        name = input(f"Person {i} - name: ").strip()
        raw = input("Enter course numbers separated by spaces (e.g., CS101 MATH140 ENGL1301): ").strip()
        course_list = [x for x in raw.split() if x]
        pairs.append((name, course_list))

    course_dict = build_course_dict(pairs)
    query = input("Enter a course to search for (e.g., CS101): ").strip()
    who = students_in_course(course_dict, query)

    if who:
        print("People who took", query + ":", ", ".join(who))
    else:
        print("No one took", query)


# ----------------------------
# Problem 5
# ----------------------------
def count_occurrences_5x5(grid):
    """
    Given a 5x5 list of numbers, return a dict {number: count}.
    """
    counts = {}
    for row in grid:
        for num in row:
            if num in counts:
                counts[num] += 1
            else:
                counts[num] = 1
    return counts


# ----------------------------
# Problem 6
# ----------------------------
def play_quiz(qa_dict):
    """
    Plays a quiz where questions are asked in random order.
    qa_dict keys are questions; values are the correct answers (strings).
    User types their answer, we report right/wrong and a final score.
    """
    import random
    questions = list(qa_dict.keys())
    random.shuffle(questions)

    print("Quiz time! Type your answers.\n")
    correct = 0
    for q in questions:
        print(q)
        user_ans = input("> ").strip()
        if user_ans.lower() == str(qa_dict[q]).lower():
            print("✅ Correct!\n")
            correct += 1
        else:
            print(f"❌ Incorrect. Correct answer: {qa_dict[q]}\n")
    print(f"Your score: {correct}/{len(questions)}")


# ----------------------------
# Demo (non-interactive showcase)
# ----------------------------
if __name__ == "__main__":
    # Problem 1 demo
    demo_scores = [95, 88, 73, 100, 92]
    a_count, avg = count_as_and_average(demo_scores)
    print("Problem 1 demo -> scores:", demo_scores)
    print(f"  A's: {a_count}, Average: {avg:.2f}\n")

    # Problem 2 demo
    cards = build_card_names()
    print("Problem 2 demo -> first 12 cards:\n ", cards[:12], "\n")

    # Problem 3 demo
    specials = special_numbers_upto(10000)
    print("Problem 3 demo -> special numbers < 10000:\n ", specials, "\n")
    print(f"  Count: {len(specials)}\n")

    # Problem 4 demo
    demo_pairs = [
        ("Alice", ["CS101", "MATH140", "HIST200"]),
        ("Bob",   ["CS101", "ENGL1301"]),
        ("Cara",  ["MATH140", "PHYS1301"]),
        ("Dan",   ["CS101", "PHIL2306"]),
        ("Eve",   ["ENGL1301", "MATH140"]),
    ]
    d = build_course_dict(demo_pairs)
    course_to_check = "CS101"
    who = students_in_course(d, course_to_check)
    print("Problem 4 demo -> course dict:", d)
    print(f"  Who took {course_to_check}? {who}\n")

    # Problem 5 demo
    grid_5x5 = [
        [1, 2, 3, 4, 5],
        [2, 3, 4, 5, 1],
        [3, 4, 5, 1, 2],
        [4, 5, 1, 2, 3],
        [5, 1, 2, 3, 4],
    ]
    counts = count_occurrences_5x5(grid_5x5)
    print("Problem 5 demo -> 5x5 grid:")
    for row in grid_5x5:
        print(" ", row)
    print("  Counts:", counts, "\n")

    # Problem 6 demo
    # (Uncomment to play interactively)
    # sample_quiz = {
    #     "What is 2 + 2?": "4",
    #     "What color is the sky on a clear day?": "blue",
    #     "What is the capital of Texas?": "Austin",
    # }
    # play_quiz(sample_quiz)

    print("Notes:")
    print("  • For Problems 1, 4, and 6, there are interactive versions (input prompts).")
    print("    Use those when running the script yourself.")
    print("  • Demos use simple examples so you can see how the functions work.")
