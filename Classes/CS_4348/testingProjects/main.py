import threading
import time
import random
import queue

# Global semaphores for shared resources.
door_semaphore = threading.Semaphore(2)       # Only 2 customers can enter the bank at once.
safe_semaphore = threading.Semaphore(2)       # Only 2 tellers can be in the safe at once.
manager_semaphore = threading.Semaphore(1)    # Only 1 teller can interact with the manager.

# Queue for customers waiting to be served.
customer_queue = queue.Queue()

# Total number of customers and tellers.
NUM_CUSTOMERS = 50
NUM_TELLERS = 3

# Lock to ensure that print statements don't interleave.
print_lock = threading.Lock()

def log(message):
    with print_lock:
        print(message)

def customer_thread(customer_id):
    # Decide transaction type randomly: Deposit or Withdrawal.
    transaction = random.choice(["Deposit", "Withdrawal"])
    
    # Wait between 0 and 100ms before trying to enter the bank.
    pre_entry_delay = random.uniform(0, 0.1)
    log(f"Customer {customer_id}: waiting {pre_entry_delay*1000:.1f}ms before entering bank")
    time.sleep(pre_entry_delay)
    
    # Acquire door semaphore to enter the bank.
    door_semaphore.acquire()
    log(f"Customer {customer_id}: enters bank")
    door_semaphore.release()  # Release the door semaphore immediately after entering.
    
    # Create two semaphores for this customer's interaction with a teller:
    # one for waiting for transaction completion and one for signaling that the customer is leaving.
    trans_done = threading.Semaphore(0)
    leave_sem = threading.Semaphore(0)
    
    # Put customer details in the queue.
    customer_obj = (customer_id, transaction, trans_done, leave_sem)
    customer_queue.put(customer_obj)
    log(f"Customer {customer_id}: waiting in line with transaction {transaction}")
    
    # Wait for teller to complete the transaction.
    trans_done.acquire()
    log(f"Customer {customer_id} [Teller ?]: transaction complete")
    
    # Signal the teller that customer is now leaving.
    leave_sem.release()
    log(f"Customer {customer_id}: leaves bank")
    
    # Release door semaphore upon leaving.
    door_semaphore.release()

def teller_thread(teller_id):
    log(f"Teller {teller_id}: ready to serve")
    while True:
        # Get the next customer from the queue.
        customer_obj = customer_queue.get()
        if customer_obj is None:
            # Sentinel received; time to exit.
            break
        customer_id, transaction, trans_done, leave_sem = customer_obj
        
        # Simulate the initial interaction.
        log(f"Teller {teller_id} [Customer {customer_id}]: customer approaches")
        log(f"Teller {teller_id} [Customer {customer_id}]: asks for transaction")
        time.sleep(0.01)  # Simulate a short delay waiting for customer's response.
        log(f"Customer {customer_id} [Teller {teller_id}]: states transaction: {transaction}")
        
        # For withdrawal transactions, interact with the manager.
        if transaction == "Withdrawal":
            log(f"Teller {teller_id}: going to manager for permission (start)")
            # Acquire manager semaphore to ensure only one teller interacts with the manager.
            manager_semaphore.acquire()
            log(f"Teller {teller_id}: interacting with manager (waiting 5-30ms)")
            delay = random.uniform(0.005, 0.03)
            time.sleep(delay)
            log(f"Teller {teller_id}: manager interaction complete")
            manager_semaphore.release()
        
        # Access the safe.
        log(f"Teller {teller_id}: waiting for safe")
        safe_semaphore.acquire()
        log(f"Teller {teller_id}: entered safe")
        log(f"Teller {teller_id}: processing transaction in safe (waiting 10-50ms)")
        delay = random.uniform(0.01, 0.05)
        time.sleep(delay)
        log(f"Teller {teller_id}: finished transaction in safe")
        safe_semaphore.release()
        
        # Inform the customer that the transaction is complete.
        log(f"Teller {teller_id} [Customer {customer_id}]: transaction complete")
        trans_done.release()
        
        # Wait for the customer to leave before proceeding.
        leave_sem.acquire()
        log(f"Teller {teller_id}: confirmed Customer {customer_id} has left")
        
        # Mark this customer as done in the queue.
        customer_queue.task_done()

def main():
    # Start teller threads.
    tellers = []
    for t in range(NUM_TELLERS):
        t_thread = threading.Thread(target=teller_thread, args=(t,))
        t_thread.start()
        tellers.append(t_thread)
    
    # Start customer threads.
    customers = []
    for c in range(NUM_CUSTOMERS):
        c_thread = threading.Thread(target=customer_thread, args=(c,))
        c_thread.start()
        customers.append(c_thread)
    
    # Wait for all customers to be processed.
    for c in customers:
        c_thread = c
        c_thread.join()
    
    # Wait until all queued tasks are done.
    customer_queue.join()
    
    # Send termination signal to teller threads by putting sentinels in the queue.
    for _ in range(NUM_TELLERS):
        customer_queue.put(None)
    
    # Wait for all teller threads to finish.
    for t in tellers:
        t.join()
    
    log("Bank simulation complete. All customers have been served and the bank is closed.")

if __name__ == '__main__':
    main()
