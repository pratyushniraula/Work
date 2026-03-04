import threading
import random
import time

empty = threading.Semaphore(1)
full = threading.Semaphore(0)

shared_number = None

def producer_sem():
    global shared_number
    for _ in range(10):
        num = random.randint(1, 100)
        empty.acquire()  # Wait until buffer is "empty"
        shared_number = num
        print(f"[Producer] Wrote: {num}")
        full.release()   # Signal that buffer is "full"
        time.sleep(0.3)

def consumer_sem(consumer_id):
    global shared_number
    full.acquire()  # Wait until buffer is "full"
    val = shared_number
    print(f"[Consumer {consumer_id}] Read: {val}")
    empty.release() # Signal that buffer is "empty"

if __name__ == "__main__":
    producer_thread = threading.Thread(target=producer_sem)
    consumer_threads = [
        threading.Thread(target=consumer_sem, args=(i,))
        for i in range(1, 11)
    ]

    producer_thread.start()
    for ct in consumer_threads:
        ct.start()

    producer_thread.join()
    for ct in consumer_threads:
        ct.join()

    print("Done (Semaphores).")
