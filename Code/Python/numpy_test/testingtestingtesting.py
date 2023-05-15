import numpy as np
import matplotlib.pyplot as plt

x = np.linspace(-5, 5, 100)
# f(x) = e ^ (-x^2)
y = np.exp(-x**2)

plt.plot(x, y)
plt.show()