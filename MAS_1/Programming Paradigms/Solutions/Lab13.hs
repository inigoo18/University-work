--1
-- BASE CASE:
-- xs = []
-- length ( reverse ( [] )) = length []
-- We know that the reverse of an empty list is still the empty list, therefore:
-- => length [] = length [] = 0


-- INDUCTIVE CASE:
-- length ( reverse ( xs )) = length xs holds. Prove it for length ( reverse ( x:xs)) = length (x:xs)

-- <=> length (reverse x) ++ length (reverse xs) = length(x:xs)
-- The reverse of [x] is still [x].
-- Therefore, length x ++ length (reverse xs) = length(x:xs)
-- from the inductive hypothesis,
-- length x ++ length xs = length (x:xs)


-- 2

-- First law: fmap id _ = id _

--BASE CASE:
-- T is Leaf a and g is id
-- we know that : fmap id (Leaf x) = Leaf (id x) = Leaf x

-- INDUCTIVE STEP:
-- fmap id Node l r = id Node l r (FOR SUBTREES) <-- INDUCTION HYPOTHESIS (not the same for natural numbers!! there we treat n and n+1)
-- fmap id (Node l r) = Node (fmap id l) (fmap id r)
-- Mapping the id function on the subtrees return the id function (IND. HYPOTHESIS)
-- => Node l r    which is the same as     id Node l r.


-- Second law: fmap (g.h) _ = fmap g . fmap h _

-- BASE CASE:
-- T is Leaf a.
-- fmap (g.h) (Leaf x) = (fmap g . fmap h) (Leaf x) =  (fmap g ( fmap h ( Leaf x ) ) ) = fmap g . fmap h (Leaf x)

-- INDUCTIVE STEP:
-- fmap (g.h) (Node l r) = fmap g . fmap h (Node l r) (FOR SUBTREES) <-- INDUCTION HYPOTHESIS




-- a

fib :: Int -> Int
fib n = head (reverse (fibList n [0,1]))

fibList :: Int -> [Int] -> [Int]
fibList n xs = if length(xs) < n then fibList n ( xs ++ [(sum(take 2 (reverse xs)))])
                                  else xs



-- a2
fibList' :: [Int] -> [Int]
fibList' xs = fibList' ( xs ++ [(sum(take 2 (reverse xs)))])
