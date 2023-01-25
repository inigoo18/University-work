
allbutsecond :: [a] -> [a]
allbutsecond [] = []
allbutsecond [x] = [x]
allbutsecond (x:xs) = x : (drop 1 xs)


midtover :: [a] -> ([a],[a])
midtover xs = (take d xs, drop d xs)
               where d = (length xs) `div` 2

bingo :: Integral a => (a,a) -> a
bingo (x,y) = x `mod` z
  where z = y + 42


qsort :: Ord a => [a] -> [a]
qsort [] = []
qsort [x] = [x]
qsort (x:xs) = big ++ [x] ++ small
             where small = qsort [a | a <- xs, a < x]
                   big = qsort [a | a <- xs, a > x]
