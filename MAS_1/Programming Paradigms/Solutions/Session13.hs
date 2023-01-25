-- 1.

-- length (xs ++ ys) = length xs + length ys

-- WE KNOW:
-- length [] = 0
-- length (x:s) = 1 + length s
-- [] ++ ys = ys
-- (x:s) ++ ys = x:(s++ys)

-- SO,
-- BASE CASE: xs = []
-- We must show
-- length ([] ++ ys) = length ys
-- length [] + length ys = 0 + length ys = length ys

-- INDUCTIVE STEP:
-- xs = x : xs'
-- we must show:
-- length ( (x:xs) ++ ys) = length (x:xs) + length ys

-- length ((x:xs') ++ ys) = length (x:(xs'++ys)) = 1 + length (xs' ++ ys) <-- INDUCTIVE HYPOTHESIS
-- = 1 + length xs' + length ys
-- = length (x:xs') + length ys
-- = length xs + length ys


-- 2.

-- length (flatten t) =
