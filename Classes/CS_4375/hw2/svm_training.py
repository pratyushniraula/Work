import numpy as np
from scipy.optimize import minimize

def poly2_map(X):
    """Polynomial feature mapping to degree 2"""
    x1, x2, x3, x4 = X[:,0], X[:,1], X[:,2], X[:,3]
    inter = np.column_stack([np.sqrt(2)*x1*x2, np.sqrt(2)*x1*x3, np.sqrt(2)*x1*x4,
                             np.sqrt(2)*x2*x3, np.sqrt(2)*x2*x4, np.sqrt(2)*x3*x4])
    Phi = np.column_stack([np.ones(len(X)), X, X**2, inter])
    return Phi

class PrimalSVM:
    def __init__(self, C=1.0):
        self.C = C
        self.w = None
        self.support_vectors = None
        self.support_vector_labels = None
        self.margin = None
    
    def fit(self, X, y):
        """
        Solve the primal SVM problem using scipy optimization
        min 0.5 * ||w||^2 + C * sum(xi)
        s.t. yi(w^T phi(xi)) >= 1 - xi
             xi >= 0
        """
        # Transform features
        Phi = poly2_map(X)
        n_samples, n_features = Phi.shape
        
        def objective(params):
            w = params[:n_features]
            xi = params[n_features:]
            return 0.5 * np.dot(w, w) + self.C * np.sum(xi)
        
        def constraint_margin(params):
            w = params[:n_features]
            xi = params[n_features:]
            # yi(w^T phi(xi)) - 1 + xi >= 0
            return y * (Phi @ w) - 1 + xi
        
        def constraint_slack(params):
            xi = params[n_features:]
            # xi >= 0
            return xi
        
        # Initial guess
        x0 = np.zeros(n_features + n_samples)
        
        # Constraints
        constraints = [
            {'type': 'ineq', 'fun': constraint_margin},
            {'type': 'ineq', 'fun': constraint_slack}
        ]
        
        # Solve optimization problem
        result = minimize(objective, x0, method='SLSQP', constraints=constraints)
        
        if not result.success:
            print(f"Warning: Optimization did not converge: {result.message}")
        
        # Extract solution
        self.w = result.x[:n_features]
        xi = result.x[n_features:]
        
        # Find support vectors (points with constraints active)
        margin_scores = y * (Phi @ self.w)
        support_indices = np.where((margin_scores <= 1.01) & (margin_scores >= 0.99))[0]
        
        # Calculate margin
        self.margin = 1.0 / np.linalg.norm(self.w[1:])  # Exclude bias term
        
        # Store support vectors
        self.support_vectors = X[support_indices] if len(support_indices) > 0 else np.array([])
        self.support_vector_labels = y[support_indices] if len(support_indices) > 0 else np.array([])
        
        return self
    
    def predict(self, X):
        """Make predictions"""
        Phi = poly2_map(X)
        scores = Phi @ self.w
        return np.where(scores >= 0, 1.0, -1.0)
    
    def decision_function(self, X):
        """Return decision function values"""
        Phi = poly2_map(X)
        return Phi @ self.w

def train_svm_and_evaluate():
    """Train SVM on mystery.data and evaluate performance"""
    
    # Load data
    data = np.loadtxt("mystery.data", delimiter=',')
    X = data[:, :4]  # Features
    y = data[:, 4]   # Labels
    
    print(f"Dataset shape: {X.shape}")
    print(f"Class distribution: {np.bincount(y.astype(int) + 1)}")  # Convert -1,1 to 0,2 for bincount
    
    # Train SVM with different C values to find best performance
    C_values = [0.1, 1.0, 10.0, 100.0]
    best_C = None
    best_accuracy = 0
    best_svm = None
    
    for C in C_values:
        print(f"\nTraining SVM with C = {C}")
        svm = PrimalSVM(C=C)
        svm.fit(X, y)
        
        # Evaluate on training data
        predictions = svm.predict(X)
        accuracy = np.mean(predictions == y)
        
        print(f"  Training accuracy: {accuracy:.4f} ({accuracy*100:.2f}%)")
        print(f"  Number of support vectors: {len(svm.support_vectors)}")
        print(f"  Margin: {svm.margin:.6f}")
        
        if accuracy > best_accuracy:
            best_accuracy = accuracy
            best_C = C
            best_svm = svm
    
    print(f"\n" + "="*50)
    print(f"BEST MODEL RESULTS (C = {best_C}):")
    print(f"="*50)
    print(f"Training accuracy: {best_accuracy:.4f} ({best_accuracy*100:.2f}%)")
    print(f"Number of support vectors: {len(best_svm.support_vectors)}")
    print(f"Optimal margin: {best_svm.margin:.6f}")
    print(f"Learned weights (W_LEARNED):")
    print(f"W_LEARNED = np.array({best_svm.w.tolist()})")
    
    # Analyze support vectors
    print(f"\nSupport Vectors Analysis:")
    print(f"Support vector indices: {len(best_svm.support_vectors)} out of {len(X)} total points")
    if len(best_svm.support_vectors) > 0:
        print(f"Support vector labels: {best_svm.support_vector_labels}")
    
    # Create and test the eval function with learned weights
    print(f"\n" + "="*50)
    print("IMPLEMENTING AND TESTING EVAL FUNCTION:")
    
    # Define the eval function with the learned weights
    def eval_function(X):
        """
        Evaluate the trained SVM on input data X
        X: numpy array of shape (n_samples, 4) containing the 4 features
        Returns: numpy array of shape (n_samples,) with predictions (+1 or -1)
        """
        # Apply polynomial feature mapping
        Phi = poly2_map(X)
        
        # Use learned weights from the best SVM
        W_LEARNED = best_svm.w
        
        # Make predictions
        return np.where(Phi @ W_LEARNED >= 0, 1.0, -1.0)
    
    # Test the eval function on the training data
    print("Testing eval function on training data...")
    eval_predictions = eval_function(X)
    eval_accuracy = np.mean(eval_predictions == y)
    
    print(f"Eval function accuracy: {eval_accuracy:.4f} ({eval_accuracy*100:.2f}%)")
    print(f"Matches SVM predict method: {np.array_equal(eval_predictions, best_svm.predict(X))}")
    
    print(f"\nSample predictions (first 10 points):")
    print(f"True labels:      {y[:10]}")
    print(f"SVM predictions:  {best_svm.predict(X[:10])}")
    print(f"Eval predictions: {eval_predictions[:10]}")
    
    # Show the final weights for copy-paste
    print(f"\n" + "="*50)
    print("FINAL WEIGHTS FOR ps2_eval.py:")
    print("="*50)
    print("Copy this into your ps2_eval.py file:")
    print(f"W_LEARNED = np.array({best_svm.w.tolist()})")
    
    return best_svm, eval_function

if __name__ == "__main__":
    # Install scipy if not already installed
    try:
        import scipy
    except ImportError:
        print("Installing scipy...")
        import subprocess
        subprocess.check_call(["pip", "install", "scipy"])
        import scipy
    
    # Train and evaluate
    best_svm, eval_func = train_svm_and_evaluate()
    
    # Additional testing
    print(f"\n" + "="*50)
    print("ADDITIONAL TESTING:")
    print("="*50)
    
    # Test eval function on mystery.data
    data = np.loadtxt("mystery.data", delimiter=',')
    X_test = data[:, :4]
    y_test = data[:, 4]
    
    final_predictions = eval_func(X_test)
    final_accuracy = np.mean(final_predictions == y_test)
    
    print(f"Final eval function test:")
    print(f"  - Dataset: mystery.data ({len(X_test)} samples)")
    print(f"  - Accuracy: {final_accuracy:.4f} ({final_accuracy*100:.2f}%)")
    print(f"  - Predictions range: [{final_predictions.min()}, {final_predictions.max()}]")
    print(f"  - Class distribution in predictions: +1: {np.sum(final_predictions == 1)}, -1: {np.sum(final_predictions == -1)}")
    
    print(f"\nSUCCESS! The eval function is ready for your homework submission.")
