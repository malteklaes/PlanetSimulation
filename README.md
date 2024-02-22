# PlanetSimulation

## about 
Simulation of planets movement through octree datastructure and Barnes-Hut-algorithm.
- (from a former software project at the Vienna University of Technology, Prof. Franz Puntigam)

## data structure: Octree

> How are the groups found that can be combined? A suitable data structure is the quadtree in 2D or the octree in 3D. The concepts of the quadtree are:

1. The first step is to insert all celestial bodies of the simulation into a tree structure (the quadtree or octree). The insertion is done recursively: If a node is empty (N), the celestial body is inserted and a leaf node is created that contains exactly one celestial body. If the node into which the insertion is made is a leaf node with exactly one celestial body, the leaf node is divided into four - initially empty - quadrants and both celestial bodies are inserted into the corresponding quadrants. Each quadrant corresponds to a subtree. To avoid multiple calculations, each node (at each level of the tree) should store the total mass and center of gravity of the celestial bodies it contains in a variable. Sizes are updated upon insertion.

<br>

2. Calculation of gravity: The force acting on each celestial body is calculated. The tree structure is used. For subtrees whose quadrants fulfill the properties, that is, are far enough away from the celestial body, the force emanating from the quadrant can be determined without having to climb further down the tree. When traversing the tree, it is tested whether a quadrant fulfills the property. If this is not the case, as long as there are still some, all sub-quadrants are checked, in the worst case up to the leaf nodes. This means that for T = 0 you get the same solution as with the direct summation of all the forces (but even slower due to the way the data is managed by the tree).

<br>

3. After all celestial bodies have been moved according to the forces acting on them, the entire tree must be rebuilt, that is, points 1 and 2 are repeated in a loop in the simulation.

<img src="./quadtree.png" alt="BILD" style="width:200%; border:0; float:left, margin:5px" >

## algorithm: Barnes-Hut
J. Barnes und P. Hut: ''A hierarchical O(N log N) force-calculation algorithm'' in _Nature_, 324:446-449, 1986.


<img src="./barnes-hut.png" alt="BILD" style="width:200%; border:0; float:left, margin:5px" >


## final result (also visible in .jar)
<img src="./simulation2.png" alt="BILD" style="width:200%; border:0; float:left, margin:5px" >
