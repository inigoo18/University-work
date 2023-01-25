data Unary = I Unary | Z

unary2int :: Unary -> Int
unary2int Z = 0
unary2int (I n) = 1 + unary2int n

testUnary = (I(I(I(Z))))

data Tree a = Leaf a | Node (Tree a) a (Tree a)

least :: Ord a => Tree a -> a
least (Leaf x) = x
least (Node l x r) = if lx < x && lx < lr then lx else
                       if lr < lx && lr < x then lr else
                         x
                         where
                           lx = least (l)
                           lr = least (r)

testTree = Node (Node (Leaf 1) 2 (Leaf 5)) 3 (Node (Leaf 6) 7 (Leaf 0))

--                     3
--                 2      7
--              1    5  6   0
-- minimum of a tree is the minimum of its subtrees compared with its root

testTree2 = Node (Leaf 2) 3 (Node (Leaf 1) 4 (Leaf 5))
--                     3
--                2          4
--                        1     5
