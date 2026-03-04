import numpy as np
import argparse

def fit_lad(X, y, iters=8000, eta0=None):
    M, n = X.shape
    Z = np.hstack([X, np.ones((M, 1))])    # add intercept column
    theta = np.zeros(n + 1)                # [a ; b]

    # scale a reasonable starting step from data size
    if eta0 is None:
        eta0 = 0.1 / (np.mean(np.linalg.norm(Z, axis=1)) + 1e-8)

    for t in range(1, iters + 1):
        r = Z @ theta - y                  # residuals
        s = np.sign(r)                     # subgradient signs
        s[r == 0.0] = 0.0                  # valid choice at the kink
        g = Z.T @ s                        # subgradient wrt theta

        eta = eta0 / np.sqrt(t)            # diminishing step size
        theta -= eta * g

    a, b = theta[:-1], theta[-1]
    return a, b