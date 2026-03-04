import threading
import time
import random
import queue

# Only 2 customers can enter the bank at once.
doorSemaphore = threading.Semaphore(2)
# Only 2 tellers can be inside the safe at any one time.
safeSemaphore = threading.Semaphore(2)
# Only 1 teller can interact with the manager at any one time.
managerSemaphore = threading.Semaphore(1)

# To simulate the line we will use a queue data structure.
customerLine = queue.Queue()

print_lock = threading.Lock()  # Ensure prints are not interleaved by different threads.

def log_msg(role, id, role2, partner_id, msg):
    """
    Prints a log message in the format:
      <customer | teller> <ID> [<customer | teller> OTHER_ID]: <message>
    """
    with print_lock:
        if role2 and partner_id is not None:
            print(f"{role} {id} [{role2} {partner_id}]: {msg}")
        else:
            print(f"{role} {id} []: {msg}")

def customerThread(customerId):
    # Select a choice: withdraw or deposit.
    choice = random.choice(["withdraw", "deposit"])
    
    # Wait between 0 to 100 milliseconds before entering the bank.
    wait_time = random.uniform(0, 0.1)
    log_msg("Customer", customerId, "", None, f"wants to perform a {choice} transaction")
    time.sleep(wait_time)
    
    doorSemaphore.acquire()
    log_msg("Customer", customerId, "", None, "entering bank.")
    doorSemaphore.release()  # Release the door semaphore immediately after entering.
    
    # Acquire doorSemaphore (only 2 customers allowed) before proceeding.
    log_msg("Customer", customerId, "", None, "getting in line.")
    log_msg("Customer", customerId, "", None, "selecting a teller.")
    
    # Create a customer object (dictionary) to hold data and all semaphores (all initialized to 0).
    customer = {
        "id": customerId,
        "transaction": choice,
        "teller": None,                    # Will be set by the teller thread.
        "tellerAsked": threading.Semaphore(0),     # Teller signals readiness.
        "transactionGiven": threading.Semaphore(0),# Customer signals transaction request.
        "transactionDone": threading.Semaphore(0), # Teller signals transaction complete.
        "customerLeft": threading.Semaphore(0)     # Customer signals that it has left.
    }
    
    # Put the customer in the waiting line.
    customerLine.put(customer)
    
    # Wait until a teller picks up this customer (the teller releases the semaphore).
    customer["tellerAsked"].acquire()
    log_msg("Customer", customerId, "Teller", customer["teller"], "selects teller")
    
    time.sleep(0.01)  # Brief pause before stating the transaction.
    log_msg("Customer", customerId, "Teller", customer["teller"], f"asks for {choice} transaction")
    # Signal that the transaction request has been given.
    customer["transactionGiven"].release()
    
    # Wait for the teller to complete the transaction.
    customer["transactionDone"].acquire()
    log_msg("Customer", customerId, "Teller", customer["teller"], "leaves teller")
    log_msg("Customer", customerId, "", None, "goes to door")
    log_msg("Customer", customerId, "", None, "leaves the bank")
    
    # Release the door semaphore so that other customers can enter.
    # Signal that the customer has left.
    customer["customerLeft"].release()

def tellerThread(tellerId):
    """
    The teller thread runs in a loop and serves customers one-by-one.
    For each customer:
      - Sets its teller ID into the customer record and prints that it is serving that customer.
      - Prints that it "asks for transaction" and then signals the customer.
      - Waits until the customer signals that it has provided the transaction request.
      - If the transaction is a withdrawal, it goes to the manager (with a delay and semaphore to ensure only one teller can interact at once).
      - Then, it acquires the safe, simulates the transaction (with a delay), and leaves the safe.
      - The teller prints that it finished the transaction and waits for the customer to leave.
      - Once complete, it prints that it is ready for the next customer.
    When a None is received in the queue, the teller prints a "leaving for the day" message and exits.
    """
    log_msg("Teller", tellerId, "", None, "ready to serve")
    log_msg("Teller", tellerId, "", None, "waiting for a customer")
    
    while True:
        customer = customerLine.get()
        if customer is None:
            # Sentinel received—exit loop.
            break
        
        cust_id = customer["id"]
        # Assign this teller to the customer.
        customer["teller"] = tellerId
        
        log_msg("Teller", tellerId, "Customer", cust_id, "serving a customer")
        log_msg("Teller", tellerId, "Customer", cust_id, "asks for transaction")
        # Signal to the customer that the teller is ready.
        customer["tellerAsked"].release()
        # Wait until the customer signals that the transaction is given.
        customer["transactionGiven"].acquire()
        
        # Process the transaction.
        if customer["transaction"] == "deposit":
            log_msg("Teller", tellerId, "Customer", cust_id, "handling deposit transaction")
        else:
            log_msg("Teller", tellerId, "Customer", cust_id, "handling withdrawal transaction")
            log_msg("Teller", tellerId, "Customer", cust_id, "going to the manager")
            log_msg("Teller", tellerId, "Customer", cust_id, "getting manager's permission")
            managerSemaphore.acquire()  # Ensure only one teller interacts with the manager.
            time.sleep(random.uniform(0.005, 0.03))  # Simulate manager delay (5-30ms).
            log_msg("Teller", tellerId, "Customer", cust_id, "got manager's permission")
            managerSemaphore.release()
        
        # Both transaction types then proceed to the safe.
        log_msg("Teller", tellerId, "Customer", cust_id, "going to safe")
        safeSemaphore.acquire()
        log_msg("Teller", tellerId, "Customer", cust_id, "enter safe")
        time.sleep(random.uniform(0.01, 0.05))  # Simulate processing in safe (10-50ms).
        log_msg("Teller", tellerId, "Customer", cust_id, "leaving safe")
        safeSemaphore.release()
        
        log_msg("Teller", tellerId, "Customer", cust_id, f"finishes {customer['transaction']} transaction.")
        log_msg("Teller", tellerId, "Customer", cust_id, "wait for customer to leave.")
        # Signal that the transaction is complete.
        customer["transactionDone"].release()
        # Wait for the customer to signal that they have left.
        customer["customerLeft"].acquire()
        
        # Mark the customer task as done in the queue.
        customerLine.task_done()
        log_msg("Teller", tellerId, "", None, "ready to serve")
        log_msg("Teller", tellerId, "", None, "waiting for a customer")
    
    log_msg("Teller", tellerId, "", None, "leaving for the day")

def main():
    # Create 3 teller threads.
    tellerThreads = []
    for i in range(3):
        tThread = threading.Thread(target=tellerThread, args=(i,))
        tThread.start()
        tellerThreads.append(tThread)
    
    # Create 50 customer threads.
    customerThreads = []
    for i in range(50):
        cThread = threading.Thread(target=customerThread, args=(i,))
        cThread.start()
        customerThreads.append(cThread)
    
    # Wait for all customer threads to finish.
    for thread in customerThreads:
        thread.join()
    
    # Wait until the customerLine queue is fully processed.
    customerLine.join()
    
    # Send a sentinel (None) for each teller to signal them to exit.
    for _ in range(3):
        customerLine.put(None)
    for thread in tellerThreads:
        thread.join()
    
    with print_lock:
        print("The bank closes for the day.")

if __name__ == "__main__":
    main()
