--1
nsonly :: Int -> [Int]
nsonly n = [i * n | i <- [0..]]

nsonlyRec :: Int -> Int -> [Int]
nsonlyRec n i = [n*i] ++ (nsonlyRec n (i+1))

--2
