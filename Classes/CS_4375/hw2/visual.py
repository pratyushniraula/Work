#visualize every point in magic.data with color based on its label
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns

# Load the dataset. dataset has 4 input features and 1 output label
data = pd.read_csv('mystery.data', header=None)
data.columns = ['f1', 'f2', 'f3', 'f4', 'class']
# Display first few rows of the dataset
print(data.head())
# Summary statistics
print(data.describe())
# Class distribution
print(data['class'].value_counts())

# Map class labels to colors
data['color'] = data['class'].map({1: 'blue', -1: 'red'})
# Plot pairplot
sns.pairplot(data, hue='class', palette={1: 'blue', -1: 'red'}, diag_kind='kde')
plt.suptitle('Pairplot of MAGIC Gamma Telescope Data', y=1.02)
plt.show()
# Save the plot
plt.savefig('magic_pairplot.png')

#this plot shows that the data is not linearly separable in the original 4D feature space.
#we will need to use a non-linear kernel (polynomial or RBF) to achieve good classification performance.
#the pairplot also shows that some features are more informative than others.
#for example, f1 and f4 seem to provide better separation between the two classes compared to f2 and f3.
#this insight can help in feature selection or engineering for improving model
#f1 to f4 represent each feature in the dataset
#where f1 f1 graph re