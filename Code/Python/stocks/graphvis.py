import networkx as nx
import matplotlib.pyplot as plt

G = nx.Graph()
#bipartite graph with two sets of nodes and nodes connect with multiple edges between them
G.add_edges_from([('A', 1), ('A', 2), ('B', 1), ('B', 3), ('C', 2), ('C', 3)], bipartite=0)
nx.draw(G, with_labels=True, node_color='maroon', edge_color='gray', node_size=1000, font_size=10)
plt.show()