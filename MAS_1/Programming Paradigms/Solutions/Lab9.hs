-- 1)
data UTree a = Node a [UTree a] deriving Show

instance Functor UTree where
  -- fmap :: (a -> b) -> (UTree a) -> (UTree b)
  fmap g (Node x []) = Node (g x) []
  fmap g (Node x ys) = Node (g x) (map (fmap g) ys)
  -- g :: a -> b
  -- x :: Node a
  -- ys :: UTree a

-- fmap length (Node 3 [Node 2 [ Node 1]])

-- 2)
--instance Functor ((->)r) where
 fmap = (.) (THIS IS THE SOLUTION)
 fmap g r x = g(r x) (THIS IS OK TOO)

-- map :: (c -> a) -> [c] -> [a]
-- fmap :: (c -> a) -> (r -> c) -> (r -> a)

-- fmap length (map (:"a") xs) (map (:"b") ys)

-- (r -> c) takes elements of type r, transforms them into type c
-- (r -> a) the same thing
-- then (c -> a) takes the former and returns the latter.

-- ((.) f g) x = f ( g ( x ) )

-- fmap (+1) (length)

-- ((->)r) Int is the same as r -> Int, which means that any r
-- will be turned into an Int.


-- 3)
-- (<*>) :: Maybe (a -> b) -> Maybe a -> Maybe b
-- Nothing <*> _ = Nothing
-- (Just g) <*> mx = fmap g mx

--instance Applicative [] where
  -- pure :: a -> [a]
  --pure x = [x]
  -- (<*>) :: [a -> b] -> [a] -> [b]
  --gs <*> xs = [g x | g <- gs , x <- xs]

instance Applicative [] where
  pure x = [x]
  gs <*> xs = fmap (g:gs) (x:xs)

(<?>) :: [a -> b] -> [a] -> [b]
gs <?> xs = [g x | g <- gs , x <- xs]

(<%>) :: [a -> b] -> [a] -> [b]
[] <%> xs = []
(g:gs) <%> xs = fmap g xs ++ (gs <%> xs)

-- 4)
-- The expression returns type Bool because of x = plus z y, and x is a free
-- variable which means it's not bounded such as for example y.

-- What we need to do is utilize the rules that are found on the PDF to
-- type check the expression.
