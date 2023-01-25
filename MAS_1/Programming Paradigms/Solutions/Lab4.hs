-- 1.
rev :: [a] -> [a]
rev [] = []
rev (x:xs) = rev xs ++ [x]

-- a)
isolate' :: Eq a => [a] -> a -> ([a], [a])
isolate' xs n = ([x | x <- xs, x == n], [x | x <- xs, x /= n])

-- b)
amy :: (a -> Bool) -> [a] -> Bool
amy f [] = False
amy f (x:xs) = if f x == True then True
                              else amy f xs

-- 2
myLast :: [a] -> a
myLast [] = error "empty"
myLast [a] = a -- do we need to define a base case for myLast []?
myLast (_:xs) = myLast xs

-- c)
removeChar :: [Char] -> Char -> [Char]
removeChar xs c = [x | x <- xs, x /= c]

frequencies :: [Char] -> [(Char, Int)]
frequencies [] = []
frequencies (c:s) = (c, 1 + sum [1 | cc <- s, c == cc]) : frequencies (removeChar s c)

-- 3
wrapup :: Eq a => [a] -> [[a]]
wrapup [] = []
wrapup [x] = [[x]]
wrapup (x:xs) = if x == h then (x:fst):rst
                          else [x]:(fst:rst)
                          where fst:rst = wrapup xs
                                (h:t) = fst

--MY VERSION :) however it uses destructors such as head and tail! we can fix that with
-- pattern matching.
wrapup' [] = []
wrapup' [x] = [[x]]
wrapup' (x:y:xs) | x == y = (x: head (wrapup' (y:xs))) : tail (wrapup' (y:xs))
                | otherwise = [x] : wrapup' (y:xs)


-- 4
rle :: Eq a => [a] -> [(a, Int)]
rle [] = []
rle [x] = [(x, 1)]
rle (x:xs) = if x == h then (x, num + 1) : rst
                       else (x, 1):(h, num) : rst
             where ((h, num) : rst) = rle xs


triples :: Num a => [(a,a,a)] -> ([a],[a],[a])
triples [] = ([],[],[])
triples [(a,b,c)] = ([a], [b], [c])
triples ((x,y,z):xs) = (x:a,y:b,z:c)
  where (a,b,c) = triples xs
