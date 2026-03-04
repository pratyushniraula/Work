import threading
import time
import random

# Shared data
buffer_item = None
buffer_full = False

# Peterson’s variables (for 2 threads: 0 = producer, 1 = consumer)
flag = [False, False]
turn = 0

def peterson_enter_critical_section(i):
    """ i is either 0 or 1 """
    global flag, turn
    j = 1 - i
    flag[i] = True
    turn = j
    # Spin while the other thread is interested and has the turn
    while flag[j] and turn == j:
        pass

def peterson_exit_critical_section(i):
    global flag
    flag[i] = False

def producer_peterson():
    global buffer_item, buffer_full
    # Produce N items, for example 5
    for _ in range(5):
        item = random.randint(1, 100)
        # Enter critical section
        peterson_enter_critical_section(0)
        # Critical section
        buffer_item = item
        buffer_full = True
        print(f"[Producer] Placed item {item} in buffer.")
        # Exit critical section
        peterson_exit_critical_section(0)
        # Simulate some delay
        time.sleep(0.5)

def consumer_peterson():
    global buffer_item, buffer_full
    for _ in range(5):
        # Enter critical section
        peterson_enter_critical_section(1)
        # Critical section
        if buffer_full:
            val = buffer_item
            buffer_full = False
            print(f"[Consumer] Retrieved item {val} from buffer.")
        else:
            print("[Consumer] Buffer was empty!")
        # Exit critical section
        peterson_exit_critical_section(1)
        time.sleep(0.5)

if __name__ == "__main__":
    prod_thread = threading.Thread(target=producer_peterson)
    cons_thread = threading.Thread(target=consumer_peterson)

    prod_thread.start()
    cons_thread.start()

    prod_thread.join()
    cons_thread.join()

    print("Done.")
