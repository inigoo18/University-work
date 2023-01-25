-- 1
fourfirst xs = do
  x <- xs
  return (4, x)

-- with x <- xs we unpack the monad value, so x is each element in the list.
-- then using return we reapply the monad ([]) to the tuple for each element.
-- therefore we obtain [(4,1), (4,2)...] for a list s.t [1,2,...].


--2 & 3

-- Defining states and state transformers


newtype State = SE [Int] deriving Show
newtype ST a = ST (State -> (a, State))

app :: ST a -> State -> (a,State)
app (ST st) x = st x


-- Defining the ST monad
-- We must first make ST a functor
instance Functor ST where
  -- fmap :: (a->b) -> ST(a->b)
   fmap g st = ST (\s -> let (x,s') = app st s in (g x,s'))

-- Then we must make ST an applicative functor

instance Applicative ST where
  -- pure :: a -> ST a
  pure x = ST (\s -> (x,s))

  -- <*> :: ST (a->b) -> ST a -> ST b

  stf <*> stx = ST (\s ->
                  let (f,s')  = app stf s
                      (x,s'') = app stx s' in (f x,s''))

-- Finally, we can make ST a monad; we only need to define >>= as return is simply the -- pure function
instance Monad ST where
  -- >>= :: a -> (a -> ST b) -> ST b
  st >>= f = ST (\s ->
                let (x,s') = app st s in app (f x) s')


get :: ST Int
get = ST (\s -> let (SE (x:xs)) = s in (x,s))

hansGet = ST(\s -> case s of
                (SE []) -> (0,s)
                (SE (x:_)) -> (x, s))

put :: Int -> ST Int
put x = ST (\s -> let (SE (xs)) = s in (x, (SE(x:xs))))
-- (app (put 6) state)


remove :: ST Int
remove = ST (\s -> let (SE (x:xs)) = s in (head xs, (SE(xs))))
-- app remove state


state = SE [1,2,3,4,5]


push :: Int -> ST Int
push y = do put y

pop :: ST Int
pop = do remove

add = do
  x <- pop
  y <- pop
  push (x+y)

mult :: ST Int
mult = do
  x <- pop
  y <- pop
  push (x*y)

myprog = do
  push 2
  push 3
  add
  push 5
  mult
  pop

emptyState = SE []
