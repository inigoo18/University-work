pyt :: Int -> [(Int, Int, Int)]
pyt k = [(a,b,c) | a <- [1..k-1], b <- [1..k-1], c <- [1..k], a^2 + b^2 == c^2]

sevens :: Int -> [Int]
sevens k = [a | a <- [1..k-1], a `mod` 7 == 0] 

-- 3:
-- The type in any case is Num a => [a] -> Bool, as Num can't be a basic type. (its a type
-- class!)
-- Moreover, it's missing on the Eq constraint.
-- The if statement is not required because == already does the job for True or False.

-- 4:
-- (\x. x + (\y. y + (\z. z)))
-- (\x . (\y . (\z . x+y+z)))

-- 5:
--(Ord a1, Eq a2) => a2 −> a2 −> (a1, a1) −> a1
crazy x y (za,zb) = if x == y then
                if za < zb then
                  za
                else
                  zb
              else
                za
                
-- EXTRA
-- 1:
flop :: [(a,a)] -> [(a,a)]
flop xs = [(snd(x),fst(x)) | x <- xs] 

-- 2:
dupli :: [a] -> [a]
dupli xs = concat [[x,x] | x <- xs]

-- 3:
isperfect :: Int -> Bool
isperfect n = n == sum [x | x <- [1..n-1], n `mod` x == 0]

--4 :
bighead :: Ord a => [a] -> Int
bighead xs = length [x | x <- xs, x > head xs]

--5 :
sums m n = [ x+y | x <- [1..m], y <- [1..n]]
sums' m n = concat [[x+y | y <- [1..n]] | x <- [1..m]]
-- instance inside another instance

--EXTRA
headsup (x:(y:_)) = True
headsup _ = False


perverse x y (u,v) = u
                     where bingo = x == y
                           dingo = u < v
