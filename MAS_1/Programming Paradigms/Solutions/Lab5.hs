within :: (Num a, Eq a, Ord a) => [a] -> (a,a) -> [a]
within xs (x,y) = filter (<=y) (filter (>=x) xs)

within' :: (Num a, Eq a, Ord a) => [a] -> (a,a) -> [a]
within' xs (x,y) = filter (\z -> x<=z && z<=y) xs


sumrows :: [[Int]] -> [Int]
sumrows xs = map sum xs

approx :: (Fractional a, Enum a) => a -> a
approx n = sum (map (\x -> 1 / fact(x)) [0..n])
  where fact k = product [1..k]


remove :: [Char] -> [Char] -> [Char]
remove c = foldr (\x y -> if all (/=x) c then x : y else y) []

---  first
---  second

filter' :: (a -> Bool) -> [a] -> [a]
filter' p = foldr (\x y -> if p x then x : y else y) []
-- (a:(b:(c:(d:[]))))

fingo :: [a] -> [a] -> [a]
fingo xs ys = foldr (:) xs ys

-- mapmap :: [a -> b] -> [[a] -> [b]]
















remove' :: [Char] -> [Char] -> [Char]
remove' xs ys = foldr (\x y -> filter (/=x) y) ys xs
