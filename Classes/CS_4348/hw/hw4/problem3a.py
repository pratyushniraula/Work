import threading
import random
import time

class SingleBufferMonitor:
    def __init__(self):
        self.buffer = None
        self.full = False
        self.cond = threading.Condition()

    def deposit(self, value):
        with self.cond:
            # Wait while buffer is already full
            while self.full:
                self.cond.wait()
            # Place the random integer
            self.buffer = value
            self.full = True
            print(f"[Producer] Stored: {value}")
            self.cond.notify_all()  # Signal that buffer is now full

    def fetch(self, consumer_id):
        with self.cond:
            # Wait while buffer is empty
            while not self.full:
                self.cond.wait()
            val = self.buffer
            self.full = False
            print(f"[Consumer {consumer_id}] Used: {val}")
            # Signal that buffer is empty again
            self.cond.notify_all()
            return val

# The single buffer
monitor_buffer = SingleBufferMonitor()

def producer_monitor():
    # Produce 10 random integers for 10 consumers
    for _ in range(10):
        num = random.randint(1, 100)
        monitor_buffer.deposit(num)
        time.sleep(0.3)

def consumer_monitor(consumer_id):
    # Each consumer fetches exactly 1 number
    val = monitor_buffer.fetch(consumer_id)
    # Possibly "use" the number
    time.sleep(0.1)

if __name__ == "__main__":
    prod_thread = threading.Thread(target=producer_monitor)
    cons_threads = [
        threading.Thread(target=consumer_monitor, args=(i,))
        for i in range(1, 11)
    ]

    prod_thread.start()
    for ct in cons_threads:
        ct.start()

    prod_thread.join()
    for ct in cons_threads:
        ct.join()

    print("Done (Monitor).")
