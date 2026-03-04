# Real-world perceptron loss with batch subgradient descent
# Dataset: Wisconsin Breast Cancer (sklearn) — binary classification
# We'll train on standardized features and evaluate accuracy on a held-out test set.

import numpy as np
import matplotlib.pyplot as plt
from sklearn.datasets import load_breast_cancer
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
import pandas as pd
from caas_jupyter_tools import display_dataframe_to_user

# 1) Load a real dataset
data = load_breast_cancer()
X = data.data.astype(float)
y01 = data.target  # {0,1} where 1=malignant? In this dataset, 0=malignant, 1=benign
# Convert to {-1, +1} with +1 = benign, -1 = malignant
y = np.where(y01 == 1, +1, -1).astype(float)

# Train/test split
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.25, random_state=42, stratify=y
)

# Standardize features for stable optimization
scaler = StandardScaler()
X_train = scaler.fit_transform(X_train)
X_test = scaler.transform(X_test)

# 2) Define perceptron loss and batch subgradients
def perceptron_loss(X, y, w, b):
    margins = y * (X @ w + b)
    return np.maximum(0.0, -margins).sum()

def perceptron_subgradients(X, y, w, b):
    margins = y * (X @ w + b)
    mis_idx = margins <= 0
    if not np.any(mis_idx):
        return np.zeros_like(w), 0.0
    grad_w = -(y[mis_idx, None] * X[mis_idx]).sum(axis=0)
    grad_b = -y[mis_idx].sum()
    return grad_w, grad_b

# 3) Train with batch subgradient descent
n_features = X_train.shape[1]
rng = np.random.default_rng(0)
w = rng.normal(scale=0.01, size=n_features)
b = 0.0

epochs = 60
eta0 = 0.5  # initial learning rate
loss_history = []

for t in range(1, epochs + 1):
    eta = eta0 / np.sqrt(t)  # simple decay
    gw, gb = perceptron_subgradients(X_train, y_train, w, b)
    w -= eta * gw
    b -= eta * gb
    loss_history.append(perceptron_loss(X_train, y_train, w, b))

# 4) Evaluate
def predict(X, w, b):
    scores = X @ w + b
    return np.where(scores >= 0, +1, -1), scores

y_pred_train, train_scores = predict(X_train, w, b)
y_pred_test, test_scores = predict(X_test, w, b)

train_acc = (y_pred_train == y_train).mean()
test_acc = (y_pred_test == y_test).mean()

# 5) Show loss curve
plt.figure()
plt.plot(range(1, epochs + 1), loss_history, marker="o")
plt.xlabel("Epoch")
plt.ylabel("Perceptron Loss (train)")
plt.title("Perceptron Loss on Breast Cancer Dataset")
plt.show()

# 6) Show a small metrics table (accuracy and a few most influential features)
# Feature importances: absolute weights
abs_w = np.abs(w)
top_idx = np.argsort(-abs_w)[:8]
top_features = [(data.feature_names[i], w[i]) for i in top_idx]

metrics_df = pd.DataFrame({
    "Metric": ["Train Accuracy", "Test Accuracy", "Final Train Loss", "Bias b"],
    "Value": [train_acc, test_acc, loss_history[-1], b]
})
display_dataframe_to_user("Perceptron Metrics", metrics_df)

top_df = pd.DataFrame({
    "Feature": [data.feature_names[i] for i in top_idx],
    "Weight": [w[i] for i in top_idx]
})
display_dataframe_to_user("Top Features by |Weight| (influence on the boundary)", top_df)

# Return final params for reference
(w[:10], b, train_acc, test_acc, loss_history[-5:])