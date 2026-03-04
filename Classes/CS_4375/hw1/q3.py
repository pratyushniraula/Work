import numpy as np
from pathlib import Path

def load_perceptron_data(path="perceptron.data"):
    p = Path(path)
    if not p.exists():
        print(f"[WARN] Could not find {path}. Please place perceptron.data in this directory.")
        return None, None
    D = np.loadtxt(path, delimiter=',')
    X = D[:, :2]   # x1, x2
    y = D[:, 2]    # labels in {-1, +1}
    return X, y

def phi(X):
    # X: (M,2) -> Phi: (M,3) with [x1, x2, x1^2 + x2^2]
    x1 = X[:, 0]
    x2 = X[:, 1]
    r2 = x1*x1 + x2*x2
    return np.column_stack([x1, x2, r2])

def perceptron_loss(w, b, Phi, y):
    # average perceptron loss: (1/M) sum max(0, -y*(w^T phi + b))
    margins = y * (Phi @ w + b)
    return np.maximum(0.0, -margins).mean()

def print_if_checkpoint(t, w, b, loss, checkpoints):
    if t in checkpoints:
        print(f"iter={t:>6d} | w={w} | b={b:.6f} | loss={loss:.6f}")

#(i) subgradient descent
def perceptron(Phi, y, iters=100000):
    gamma = 1.0
    M, d = Phi.shape
    w = np.zeros(d)
    b = 0.0
    checkpoints = {1, 10, 100, 1000, 10000, 100000}

    for t in range(1, iters + 1):
        margins = y * (Phi @ w + b)
        # Subgradient of max(0, -margin): contributes if margin <= 0
        mask = margins <= 0
        # Average subgradient for w: -(1/M) * sum_{m in mask} y_m * phi_m
        grad_w = -(y[mask][:, None] * Phi[mask]).sum(axis=0) / M
        # Average subgradient for b: -(1/M) * sum_{m in mask} y_m
        grad_b = -(y[mask]).sum() / M

        # Update with fixed step size gamma_t = 1
        w = w - gamma * grad_w
        b = b - gamma * grad_b

        if t in checkpoints:
            loss = perceptron_loss(w, b, Phi, y)
            print_if_checkpoint(t, w, b, loss, checkpoints)
    return w, b

# (ii) Stochastic subgradient
def perceptron_sgd(Phi, y, iters=100000, gamma=1.0):
    M, d = Phi.shape
    w = np.zeros(d)
    b = 0.0
    checkpoints = {1, 10, 100, 1000, 10000, 100000}

    for t in range(1, iters + 1):
        i = (t - 1) % M  # cycle deterministically
        margin = y[i] * (np.dot(w, Phi[i]) + b)
        if margin <= 0:
            # subgradient step on the single violated example
            # grad_w = -y_i * phi_i; grad_b = -y_i  ==> w += gamma * y_i * phi_i; b += gamma * y_i
            w = w + gamma * y[i] * Phi[i]
            b = b + gamma * y[i]

        if t in checkpoints:
            loss = perceptron_loss(w, b, Phi, y)
            print_if_checkpoint(t, w, b, loss, checkpoints)
    return w, b

# ---------- (iii) Fixed step size discussion helper ----------
def run_all(path="perceptron.data", iters=100000):
    X, y = load_perceptron_data(path)
    if X is None:
        return  # file missing

    PhiX = phi(X)

    print("(i) subgradient")
    perceptron(PhiX, y, iters=iters)
    print("\n--------------------------------\n")

    print("\n(ii) stochastic perceptron")
    perceptron_sgd(PhiX, y, iters=iters, gamma=1.0)
    print("\n--------------------------------\n")

    print("(iii) fixed step size changes\n")
    print("\nperceptron (gamma=0.5)")
    perceptron_sgd(PhiX, y, iters=iters, gamma=0.5)

    print("\nperceptron (gamma=2.0)")
    perceptron_sgd(PhiX, y, iters=iters, gamma=2.0)

if __name__ == "__main__":
    run_all("perceptron.data")