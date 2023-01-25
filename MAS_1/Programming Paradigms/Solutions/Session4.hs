replicate2 :: Int -> a -> [a]
replicate2 0 _ = []
replicate2 1 x = [x]
replicate2 n x = (x: (replicate (n-1) x))


improve :: [a] -> [a]
improve [] = []
improve [x] = [x]
improve (x:_:xs) = x:improve xs
