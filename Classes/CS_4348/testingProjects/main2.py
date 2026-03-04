import threading
import time
import random
import queue

# Semaphores for shared resources.
door_semaphore = threading.Semaphore(2)     # At most 2 customers can enter the bank at once.
safe_semaphore = threading.Semaphore(2)     # At most 2 tellers may be in the safe at a time.
manager_semaphore = threading.Semaphore(1)  # Only 1 teller may interact with the manager at any moment.

# Queue for customers waiting to be served.
customer_queue = queue.Queue()

# Lock to prevent interleaved output.
print_lock = threading.Lock()

def log_msg(role, id, partner_role, partner_id, msg):
    """
    Prints a log message in the format:
      THREAD_TYPE ID [OtherType OTHER_ID]: MSG
    If no partner is provided, prints an empty pair of brackets.
    """
    with print_lock:
        if partner_role and partner_id is not None:
            print(f"{role} {id} [{partner_role} {partner_id}]: {msg}")
        else:
            print(f"{role} {id} []: {msg}")

def customer_thread(customer_id):
    """
    The customer thread simulates a single customer’s life cycle:
      - Deciding on a transaction (deposit or withdrawal)
      - Printing its intent
      - Waiting to enter the bank (door semaphore)
      - Printing its steps (going to bank, entering, getting in line, selecting a teller)
      - Getting into the waiting queue with several events for synchronization with a teller
      - Waiting for a teller and then, once signaled, printing:
          • select teller & introduce itself
          • asking for the transaction
      - Waiting for the teller to complete the transaction
      - Finally, printing that it leaves the teller, goes to the door, and leaves the bank.
    """
    # Randomly choose a transaction type.
    transaction = random.choice(["deposit", "withdrawal"])
    log_msg("Customer", customer_id, "", None, f"wants to perform a {transaction} transaction")

    # Wait a random time (0 - 100ms) before going to the bank.
    time.sleep(random.uniform(0, 0.1))
    log_msg("Customer", customer_id, "", None, "going to bank.")
    log_msg("Customer", customer_id, "", None, "entering bank.")

    # Limit entry to two customers at a time.
    door_semaphore.acquire()
    log_msg("Customer", customer_id, "", None, "getting in line.")
    log_msg("Customer", customer_id, "", None, "selecting a teller.")

    # Create a customer dictionary with events for synchronization.
    customer = {
        "id": customer_id,
        "transaction": transaction,
        "teller": None,  # Will be set by the teller thread.
        "ev_teller_asked": threading.Event(),
        "ev_transaction_given": threading.Event(),
        "ev_transaction_done": threading.Event(),
        "ev_customer_left": threading.Event()
    }

    # Put the customer into the shared waiting queue.
    customer_queue.put(customer)

    # Wait until a teller picks up this customer (the teller sets the teller ID).
    customer["ev_teller_asked"].wait()
    log_msg("Customer", customer_id, "Teller", customer["teller"], "selects teller")
    log_msg("Customer", customer_id, "Teller", customer["teller"], "introduces itself")
    
    # Brief pause before customer “states” the transaction.
    time.sleep(0.01)
    log_msg("Customer", customer_id, "Teller", customer["teller"], f"asks for {transaction} transaction")
    # Signal to the teller that the customer has given the transaction.
    customer["ev_transaction_given"].set()
    
    # Wait until the teller signals that the transaction is complete.
    customer["ev_transaction_done"].wait()
    log_msg("Customer", customer_id, "Teller", customer["teller"], "leaves teller")
    log_msg("Customer", customer_id, "", None, "goes to door")
    log_msg("Customer", customer_id, "", None, "leaves the bank")

    # Release the door for other customers.
    door_semaphore.release()
    # Signal to the teller that the customer has left.
    customer["ev_customer_left"].set()

def teller_thread(teller_id):
    """
    The teller thread runs in a loop and serves customers one-by-one.
    For each customer:
      - It sets its own teller id into the customer record and prints that it is serving them.
      - It prints that it “asks for transaction” and then signals the customer.
      - It waits for the customer to state their request.
      - If a withdrawal, it goes to the manager (with a simulated delay).
      - Then, it acquires the safe (only two may be inside), simulates the transaction (sleep),
        and finally leaves the safe.
      - Finally, the teller prints that it finished the transaction and waits for the customer to leave.
      - Once done, it prints that it is again ready and waiting for a new customer.
    When a sentinel (None) is received, the teller prints a “leaving for the day” message.
    """
    log_msg("Teller", teller_id, "", None, "ready to serve")
    log_msg("Teller", teller_id, "", None, "waiting for a customer")
    while True:
        customer = customer_queue.get()
        if customer is None:
            # Sentinel received—exit loop.
            break

        cust_id = customer["id"]
        # Assign this teller to the customer.
        customer["teller"] = teller_id

        log_msg("Teller", teller_id, "Customer", cust_id, "serving a customer")
        log_msg("Teller", teller_id, "Customer", cust_id, "asks for transaction")
        # Signal the customer that the teller is ready to receive the transaction.
        customer["ev_teller_asked"].set()
        # Wait until the customer signals that it has provided its transaction request.
        customer["ev_transaction_given"].wait()

        # Process the transaction.
        if customer["transaction"] == "deposit":
            log_msg("Teller", teller_id, "Customer", cust_id, "handling deposit transaction")
        else:
            log_msg("Teller", teller_id, "Customer", cust_id, "handling withdrawal transaction")
            log_msg("Teller", teller_id, "Customer", cust_id, "going to the manager")
            log_msg("Teller", teller_id, "Customer", cust_id, "getting manager's permission")
            # Acquire the manager semaphore to ensure only one teller interacts with the manager.
            manager_semaphore.acquire()
            ######log_msg("Teller", teller_id, "Customer", cust_id, "interacting with manager (waiting 5-30ms)")
            # Simulate manager delay (5 to 30ms).
            time.sleep(random.uniform(0.005, 0.03))
            log_msg("Teller", teller_id, "Customer", cust_id, "got manager's permission")
            manager_semaphore.release()

        # Both transaction types then go to the safe.
        log_msg("Teller", teller_id, "Customer", cust_id, "going to safe")
        safe_semaphore.acquire()
        log_msg("Teller", teller_id, "Customer", cust_id, "enter safe")
        # Simulate processing time in the safe (10 to 50ms).
        time.sleep(random.uniform(0.01, 0.05))
        log_msg("Teller", teller_id, "Customer", cust_id, "leaving safe")
        safe_semaphore.release()

        log_msg("Teller", teller_id, "Customer", cust_id, f"finishes {customer['transaction']} transaction.")
        log_msg("Teller", teller_id, "Customer", cust_id, "wait for customer to leave.")
        # Signal that the transaction is complete.
        customer["ev_transaction_done"].set()
        # Wait for the customer to signal that they have left.
        customer["ev_customer_left"].wait()
        # Mark the customer task as done in the queue.
        customer_queue.task_done()
        log_msg("Teller", teller_id, "", None, "ready to serve")
        log_msg("Teller", teller_id, "", None, "waiting for a customer")
    log_msg("Teller", teller_id, "", None, "leaving for the day")

def main():
    # Start teller threads.
    tellers = []
    for t in range(3):
        th = threading.Thread(target=teller_thread, args=(t,))
        th.start()
        tellers.append(th)
    
    # Start customer threads.
    customers = []
    for c in range(50):
        th = threading.Thread(target=customer_thread, args=(c,))
        th.start()
        customers.append(th)
    
    # Wait for all customer threads to finish.
    for th in customers:
        th.join()
    
    # Ensure that all customers in the queue have been processed.
    customer_queue.join()
    
    # Send a sentinel (None) for each teller to signal them to finish.
    for _ in range(3):
        customer_queue.put(None)
    for th in tellers:
        th.join()
    
    # Final message after all tellers have finished.
    with print_lock:
        print("The bank closes for the day.")

if __name__ == '__main__':
    main()
