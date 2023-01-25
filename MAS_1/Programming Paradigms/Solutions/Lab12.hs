--1
indflet :: a -> [a] -> [a]
indflet _ [] = []
indflet _ [x] = [x]
indflet e (x:y:ys) = x : e : indflet e (y:ys)
-- the second pattern match is the same as indflet _ (x:[]) = ... 
-- calling head (indflet 1 (2:undefined)) causes an exception.
-- (because of syntax sugar, we try to evaluate whether undefined == [] which throws an exception)


-- 2
fletind :: a -> [a] -> [a]
fletind _ undefined = undefined
fletind _ [] = []
fletind _ [x] = [x]
fletind e (x:y:ys) = x : e : fletind e (y:ys)

fletind' _ [] = []
fletind' e (x:ys) = x : e : indflet e (ys)


-- 3
allBinaries :: [String]
allBinaries = ["0", "1"] ++ [y : x | x <- allBinaries, last x /= '0', y <- ['0', '1']]
