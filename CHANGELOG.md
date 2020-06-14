# CHANGELOG

## [Unreleased]

## [0.2.0]

### Features
- Introduced new interfaces for mutable graphs

### Bugfix
- Made `DefaultGraph` undirected
- Fixed constructors not to excpect ordered, non-successive node ids

## [0.1.0]

### Features
- Interfaces for graph data structures: `Graph`, specializations `DirectedGraph` and `UndirectedGraph`
- Mutable implementations for the graph interfaces, special implementations like outgoing star
- Algorithms: Graph search, `Dijkstra` (integral and rational), Moore-Bellman-Ford
- Supporting data structures for visualization

[Unreleased]: https://github.com/zetool/graph/compare/graph-0.2.0...HEAD
[0.2.0]: https://github.com/zetool/graph/compare/graph-0.1.0...graph-0.2.0
[0.1.0]: https://github.com/zetool/graph/compare/209c8ec0f39d449ee3908375aec3817d0db2e8cb...graph-0.1.0