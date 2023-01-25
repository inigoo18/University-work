positions :: [Char] -> [Int]
positions xs = map ((-96)+) (map fromEnum xs)
-- positions ['a','b','b','a'] => [1,2,2,1]

sumsq :: Int -> Int
sumsq n = foldr (\x y -> x*x+y) 0 [1..n]
-- sumsq 4 => 30
