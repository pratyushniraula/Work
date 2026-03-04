import threading
import queue
import random
import time

mailbox = queue.Queue()

def producer_mp():
    for _ in range(10):
        num = random.randint(1, 100)
        print(f"[Producer] Sending: {num}")
        mailbox.put(num)  # Send message
        time.sleep(0.3)

def consumer_mp(consumer_id):
    val = mailbox.get()  # Receive message (blocks if empty)
    print(f"[Consumer {consumer_id}] Received: {val}")
    mailbox.task_done()

if __name__ == "__main__":
    producer_thread = threading.Thread(target=producer_mp)
    consumer_threads = [
        threading.Thread(target=consumer_mp, args=(i,))
        for i in range(1, 11)
    ]

    producer_thread.start()
    for ct in consumer_threads:
        ct.start()

    producer_thread.join()
    for ct in consumer_threads:
        ct.join()

    print("Done (Message Passing).")
