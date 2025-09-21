# Assignment 1
### Future implementation of:
1. MergeSort (D&C, Master Case 2)
   Linear merge; reusable buffer; small-n cut-off (e.g., insertion sort).
2. QuickSort (robust)
   Randomised pivot; recurse on the smaller partition, iterate over the larger one (bounded stack ≈ O(log n) typical).
3. Deterministic Select Median-of-Medians, O( n )
   Group by 5, median of medians as pivot, in-place partition; recurse only into the needed side (and prefer recursing into the smaller side).
4. Closest Pair of Points (2D, O(n log n))
   Sort by x, recursive split, “strip” check by y order (classic 7-8 neighbour scan).

#### next is feat(metrics): counters, depth tracker, CSV writer