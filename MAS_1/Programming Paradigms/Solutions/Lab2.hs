-- 1.

twice f x = f (f (x))

--f :: a -> a
--twice :: (a -> a) -> a -> a

-- It's parametric polymorphism, as the inputs can take any kind of type
-- and are not constrained by things such as Num etc.

-- (twice :: tf -> tx -> tr)
-- (tf = ta -> tb)
-- (tx = ta)
-- (tx = tb)
-- (tr = ta)


-- 2.

dingo (x, y) = [x, y]
-- dingo :: (a, a) -> [a]

-- The function uses parametric polymorphism. Even if the elements in the list
-- have to be of the same type, said type can be any kind therefore no
-- overloading is performed.


-- 3.

-- The function doesn't necessarily have to work with numbers, it can also use
-- booleans, characters etc. It uses Ord and the output of the function is
-- a number.

-- bighead :: (Ord a) => [a] -> Int
-- Ord is a CLASS TYPE, Int is a PRIMITIVE TYPE.
-- Therefore Ord is used on the left and Int is used on the right.

-- 4.


-------------------------------------
-- More problems at your own pace:
------------------------------------

-- a)
mango x y z = x * y + z - 42

-- type of mango 14 is Num a => a -> a -> a because x has been dealt with.

-- b)
bingo :: a -> a
bingo x = x

-- Yes, bingo is parametric polymorphic.

-- c)
--thesame :: (Eq a) = [(a,a)] -> [(a,a)]

-- d)
-- [(+), (*), (+), (-)] :: [a -> a -> a]
-- [(+), (*), (+), (-), (++)] :: type error

-- e)
-- (Lx. xx) (Lx. xx)
-- infinite lambda calculus, we keep switching infinitely.

-- f)
-- map :: (Num a) => (a -> a) -> [a] -> [a]

-- g)
-- crazy x y z = (x == y) && (map 
