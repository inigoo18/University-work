onlytwo :: [a] -> Bool
onlytwo (x:y:[]) = True
onlytwo _ = False

alldots :: Num a => [(a,a)] -> [(a,a)] -> [a]
alldots xs ys = [(fst x) * (fst y) + (snd x) * (snd y) | x <- xs, y <- ys]

-- alldots [(1,2),(3,4)] [(5,6),(7,8)]
-- alldots [(1,1),(1,2),(1,0),(0,3)] [(2,1)]

alldots' :: Num a => [(a,a)] -> [(a,a)] -> [a]
alldots' xs ys = [a * c + b * d | (a,b) <- xs, (c,d) <- ys]
