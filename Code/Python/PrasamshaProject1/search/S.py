# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem: SearchProblem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print("Start:", problem.getStartState())
    print("Is the start a goal?", problem.isGoalState(problem.getStartState()))
    print("Start's successors:", problem.getSuccessors(problem.getStartState()))
    """
    "*** YOUR CODE HERE ***"
    stack = util.Stack()
    visited = set()
    start = problem.getStartState()
    stack.push((start, []))
    while not stack.isEmpty():
        state, actions = stack.pop()
        if problem.isGoalState(state):
            return actions
        if state not in visited:
            visited.add(state)
            for successor, action, _ in problem.getSuccessors(state):
                if successor not in visited:
                    stack.push((successor, actions + [action]))
    #if we go through without finding end state, return empty
    return []

def breadthFirstSearch(problem: SearchProblem):
    """Search the shallowest nodes in the search tree first."""
    "*** YOUR CODE HERE ***"
    #create queue and visited
    queue = util.Queue()
    visited = set()
    #start
    start = problem.getStartState()
    queue.push((start, []))
    #loop until queue is empty
    while not queue.isEmpty():
        #in the beginning of the queue find the next unvisited node and add its children to the end of the queue
        state, actions = queue.pop()
        if problem.isGoalState(state):
            #found the end state, return the path to it
            return actions
        if state not in visited:
            #mark the node as visited
            visited.add(state)
            #add all unvisited children to the queue   
            #for each successor of the current node, if it is unvisited, add it to the end of the queue with the path to it
            for successor, action, stepCost in problem.getSuccessors(state):
                if successor not in visited:
                    queue.push((successor, actions + [action]))
    #if we go through without finding end state, return empty
    return []

def uniformCostSearch(problem: SearchProblem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    #priority queue and visited
    pq = util.PriorityQueue()
    visited = set()
    #get starting state and push
    start = problem.getStartState()
    pq.push((start, [], 0), 0)

    #while pq is not empty
    while not pq.isEmpty():
        #keep popping in the beginning for the lowest cost node and add children
        state, actions, cost = pq.pop()
        if problem.isGoalState(state):
            #found end state
            return actions
        if state not in visited:
            #mark in visited
            visited.add(state)
            #add all unvisited children to the queue
            for successor, action, stepCost in problem.getSuccessors(state):
                if successor not in visited:
                    #add up cost and push to the queue with the new cost
                    newCost = cost + stepCost
                    pq.push((successor, actions + [action], newCost), newCost)
    #if we go through without finding end state, return empty
    return []

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0

def aStarSearch(problem: SearchProblem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    #priority queue and visited
    pq = util.PriorityQueue()
    visited = set()
    #get starting state and push
    start = problem.getStartState()
    pq.push((start, [], 0), 0 + heuristic(start, problem))
    #while pq is not empty
    while not pq.isEmpty():
        #keep popping in the beginning for the lowest cost node and add children
        state, actions, cost = pq.pop()
        if problem.isGoalState(state):
            #found it
            return actions
        #mark in visited
        if state not in visited:
            visited.add(state)
            #add all unvisited to queue
            for successor, action, stepCost in problem.getSuccessors(state):
                if successor not in visited:
                    newCost = cost + stepCost
                    pq.push((successor, actions + [action], newCost), newCost + heuristic(successor, problem))
    #if we go through without finding end state, return empty
    return []


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
