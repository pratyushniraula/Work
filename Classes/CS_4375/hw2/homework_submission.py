"""
CS 4375 Homework 2 - SVM Solution
Student: [Your Name]
Date: October 6, 2025

Complete solution for the mystery.data classification problem using Support Vector Machines
with polynomial feature mapping.
"""

import numpy as np
from scipy.optimize import minimize

def poly2_map(X):
    """
    Polynomial feature mapping to degree 2
    
    Transforms 4D input [x1, x2, x3, x4] to 15D feature space:
    φ(x) = [1, x1, x2, x3, x4, x1², x2², x3², x4², √2·x1x2, √2·x1x3, √2·x1x4, √2·x2x3, √2·x2x4, √2·x3x4]
    
    Args:
        X: numpy array of shape (n_samples, 4) - input features
    
    Returns:
        Phi: numpy array of shape (n_samples, 15) - transformed features
    """
    x1, x2, x3, x4 = X[:,0], X[:,1], X[:,2], X[:,3]
    
    # Interaction terms with √2 factor for proper kernel correspondence
    inter = np.column_stack([np.sqrt(2)*x1*x2, np.sqrt(2)*x1*x3, np.sqrt(2)*x1*x4,
                             np.sqrt(2)*x2*x3, np.sqrt(2)*x2*x4, np.sqrt(2)*x3*x4])
    
    # Combine all features: [bias, linear, quadratic, interactions]
    Phi = np.column_stack([np.ones(len(X)), X, X**2, inter])
    return Phi

class PrimalSVM:
    """
    Primal SVM implementation using scipy optimization
    
    Solves: min 0.5 * ||w||² + C * Σξᵢ
    s.t.    yᵢ(w^T φ(xᵢ)) ≥ 1 - ξᵢ  ∀i
            ξᵢ ≥ 0                  ∀i
    """
    
    def __init__(self, C=1.0):
        self.C = C
        self.w = None
        self.support_vectors = None
        self.support_vector_labels = None
        self.support_vector_indices = None
        self.margin = None
        self.n_support_vectors = 0
    
    def fit(self, X, y):
        """Train the SVM on input data"""
        # Transform features using polynomial mapping
        Phi = poly2_map(X)
        n_samples, n_features = Phi.shape
        
        def objective(params):
            """SVM objective function"""
            w = params[:n_features]
            xi = params[n_features:]
            return 0.5 * np.dot(w, w) + self.C * np.sum(xi)
        
        def constraint_margin(params):
            """Margin constraints: yᵢ(w^T φ(xᵢ)) ≥ 1 - ξᵢ"""
            w = params[:n_features]
            xi = params[n_features:]
            return y * (Phi @ w) - 1 + xi
        
        def constraint_slack(params):
            """Non-negativity constraints: ξᵢ ≥ 0"""
            xi = params[n_features:]
            return xi
        
        # Initial guess: all zeros
        x0 = np.zeros(n_features + n_samples)
        
        # Define constraints
        constraints = [
            {'type': 'ineq', 'fun': constraint_margin},
            {'type': 'ineq', 'fun': constraint_slack}
        ]
        
        # Solve optimization problem
        result = minimize(objective, x0, method='SLSQP', constraints=constraints, 
                         options={'maxiter': 1000, 'ftol': 1e-9})
        
        if not result.success:
            print(f"Warning: Optimization convergence issue: {result.message}")
        
        # Extract solution
        self.w = result.x[:n_features]
        xi = result.x[n_features:]
        
        # Identify support vectors (points with active constraints)
        margin_scores = y * (Phi @ self.w)
        tolerance = 1e-3
        
        # Support vectors are points with margin ≈ 1 or violations (margin < 1)
        support_indices = np.where(margin_scores <= 1 + tolerance)[0]
        
        # Calculate geometric margin
        self.margin = 1.0 / np.linalg.norm(self.w[1:])  # Exclude bias term
        
        # Store support vector information
        self.support_vector_indices = support_indices
        self.support_vectors = X[support_indices] if len(support_indices) > 0 else np.array([])
        self.support_vector_labels = y[support_indices] if len(support_indices) > 0 else np.array([])
        self.n_support_vectors = len(support_indices)
        
        return self
    
    def predict(self, X):
        """Make predictions on new data"""
        Phi = poly2_map(X)
        scores = Phi @ self.w
        return np.where(scores >= 0, 1.0, -1.0)
    
    def decision_function(self, X):
        """Return decision function values"""
        Phi = poly2_map(X)
        return Phi @ self.w

def train_svm_and_find_best_model():
    """
    Train SVM with different C values and return the best model
    """
    print("="*60)
    print("SVM TRAINING AND MODEL SELECTION")
    print("="*60)
    
    # Load mystery.data
    print("Loading mystery.data...")
    data = np.loadtxt("mystery.data", delimiter=',')
    X = data[:, :4]  # Features (first 4 columns)
    y = data[:, 4]   # Labels (5th column)
    
    print(f"Dataset loaded: {X.shape[0]} samples, {X.shape[1]} features")
    print(f"Class distribution: +1: {np.sum(y == 1)}, -1: {np.sum(y == -1)}")
    
    # Try different C values
    C_values = [0.1, 1.0, 10.0, 100.0, 1000.0]
    best_C = None
    best_accuracy = 0
    best_svm = None
    
    print("\nTraining SVMs with different regularization parameters:")
    print("-" * 60)
    
    for C in C_values:
        print(f"Training with C = {C:>8}")
        svm = PrimalSVM(C=C)
        svm.fit(X, y)
        
        # Evaluate performance
        predictions = svm.predict(X)
        accuracy = np.mean(predictions == y)
        
        print(f"  Accuracy: {accuracy:.4f} ({accuracy*100:.2f}%)")
        print(f"  Support vectors: {svm.n_support_vectors:>3}/{len(X)}")
        print(f"  Margin: {svm.margin:.6f}")
        print()
        
        # Keep track of best model
        if accuracy > best_accuracy:
            best_accuracy = accuracy
            best_C = C
            best_svm = svm
    
    return best_svm, X, y

def report_final_results(svm, X, y):
    """
    Report comprehensive results as required by homework
    """
    print("="*60)
    print("HOMEWORK SOLUTION REPORT")
    print("="*60)
    
    print("1. FEATURE MAP DESIGN:")
    print("   - Type: Polynomial degree 2")
    print("   - Input dimension: 4")
    print("   - Output dimension: 15")
    print("   - Features: [1, x₁, x₂, x₃, x₄, x₁², x₂², x₃², x₄², √2·x₁x₂, √2·x₁x₃, √2·x₁x₄, √2·x₂x₃, √2·x₂x₄, √2·x₃x₄]")
    
    print(f"\n2. LEARNED PARAMETERS:")
    print(f"   - Regularization parameter C: {svm.C}")
    print(f"   - Weight vector (w) shape: {svm.w.shape}")
    print(f"   - Bias term (w₀): {svm.w[0]:.6f}")
    print(f"   - Feature weights:")
    feature_names = ['bias', 'x₁', 'x₂', 'x₃', 'x₄', 'x₁²', 'x₂²', 'x₃²', 'x₄²', 
                     '√2·x₁x₂', '√2·x₁x₃', '√2·x₁x₄', '√2·x₂x₃', '√2·x₂x₄', '√2·x₃x₄']
    for i, (name, weight) in enumerate(zip(feature_names, svm.w)):
        print(f"     w[{i:2d}] ({name:>8}): {weight:>12.6f}")
    
    print(f"\n3. OPTIMAL MARGIN:")
    print(f"   - Geometric margin: {svm.margin:.6f}")
    print(f"   - Margin = 1/||w|| where ||w|| = {np.linalg.norm(svm.w[1:]):.6f}")
    
    print(f"\n4. SUPPORT VECTORS:")
    print(f"   - Number of support vectors: {svm.n_support_vectors} out of {len(X)} training points")
    print(f"   - Percentage: {svm.n_support_vectors/len(X)*100:.2f}%")
    if svm.n_support_vectors > 0:
        print(f"   - Support vector indices: {svm.support_vector_indices.tolist()}")
        print(f"   - Support vector labels: {svm.support_vector_labels.tolist()}")
    
    # Test accuracy
    predictions = svm.predict(X)
    accuracy = np.mean(predictions == y)
    print(f"\n5. MODEL PERFORMANCE:")
    print(f"   - Training accuracy: {accuracy:.4f} ({accuracy*100:.2f}%)")
    print(f"   - Correctly classified: {np.sum(predictions == y):>4}/{len(y)}")
    print(f"   - Misclassified: {np.sum(predictions != y):>4}/{len(y)}")
    
    print(f"\n6. FINAL WEIGHTS FOR EVAL FUNCTION:")
    print("W_LEARNED = np.array([")
    for i, w in enumerate(svm.w):
        if i == len(svm.w) - 1:
            print(f"    {w}")
        else:
            print(f"    {w},")
    print("])")

# REQUIRED eval FUNCTION FOR HOMEWORK SUBMISSION
def eval(X):
    """
    REQUIRED FUNCTION FOR HOMEWORK SUBMISSION
    
    Evaluate the trained SVM on input data X
    
    Args:
        X: numpy array of shape (n_samples, 4) containing the 4 features
           (excludes the label column as specified in homework)
    
    Returns:
        numpy array of shape (n_samples,) with predictions (+1 or -1)
    """
    # These are the learned weights from training the SVM
    W_LEARNED = np.array([
        1.2901401585971932,    # bias
        16.914737113525774,    # x₁
        -10.749867615748446,   # x₂
        4.985817148300378,     # x₃
        17.9991017086339,      # x₄
        13.762051531724927,    # x₁²
        -21.78765811715739,    # x₂²
        -1.4839802641249957,   # x₃²
        2.1383816772225344,    # x₄²
        -5.84864015701812,     # √2·x₁x₂
        1.9495405389746863,    # √2·x₁x₃
        -4.644175679512277,    # √2·x₁x₄
        -10.36342693438365,    # √2·x₂x₃
        -9.100441294111796,    # √2·x₂x₄
        1.5828701471214137     # √2·x₃x₄
    ])
    
    # Apply polynomial feature mapping
    Phi = poly2_map(X)
    
    # Make predictions: sign(w^T φ(x))
    decision_values = Phi @ W_LEARNED
    return np.where(decision_values >= 0, 1.0, -1.0)

def test_eval_function():
    """
    Test the eval function on mystery.data to verify correctness
    """
    print("="*60)
    print("TESTING EVAL FUNCTION")
    print("="*60)
    
    # Load test data
    data = np.loadtxt("mystery.data", delimiter=',')
    X_test = data[:, :4]  # Features only (as required by homework)
    y_true = data[:, 4]   # True labels for verification
    
    # Test eval function
    print("Testing eval function on mystery.data...")
    predictions = eval(X_test)
    accuracy = np.mean(predictions == y_true)
    
    print(f"Eval function results:")
    print(f"  - Input shape: {X_test.shape}")
    print(f"  - Output shape: {predictions.shape}")
    print(f"  - Accuracy: {accuracy:.4f} ({accuracy*100:.2f}%)")
    print(f"  - Prediction range: [{predictions.min()}, {predictions.max()}]")
    print(f"  - Class distribution: +1: {np.sum(predictions == 1)}, -1: {np.sum(predictions == -1)}")
    
    # Sample predictions
    print(f"\nSample predictions (first 10):")
    print(f"  True:       {y_true[:10]}")
    print(f"  Predicted:  {predictions[:10]}")
    print(f"  Match:      {predictions[:10] == y_true[:10]}")
    
    return accuracy >= 0.99  # Should achieve high accuracy

if __name__ == "__main__":
    """
    Main execution: Train SVM, report results, and test eval function
    """
    try:
        # Step 1: Train SVM and find best model
        best_svm, X, y = train_svm_and_find_best_model()
        
        # Step 2: Report comprehensive results
        report_final_results(best_svm, X, y)
        
        # Step 3: Test the eval function
        success = test_eval_function()
        
        if success:
            print("\n" + "="*60)
            print("SUCCESS: Solution is ready for homework submission!")
            print("The eval() function is working correctly and should")
            print("generalize well to unseen test data.")
            print("="*60)
        else:
            print("\nWARNING: eval function accuracy is lower than expected.")
            
    except Exception as e:
        print(f"Error: {e}")
        print("Make sure mystery.data is in the current directory.")
