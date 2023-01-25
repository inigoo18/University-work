-- 1:
-- IMPORTANT: (f x) :: Type tells us what type we expect back from a function.
-- For example, (read w) :: Int expects that w can be turned into an int.

-- What this function does, is read an input, convert it into a number, and then with the
-- auxiliary function loop we recursively print numbers based on the nature of the input.
-- If the input is even, we rerun the function with input/2 as its argument.
-- If the input is odd, we rerun the function with input*3 + 1 as its argument, making
-- an even number.

-- 2:

letters :: IO ()
letters = do
  w <- getLine
  loop w
  where
    loop [] = return ()
    loop (x:xs) = do
      putStrLn (show x)
      loop xs

-- 3:

letters' :: IO ()
letters' = do
  w <- getLine
  loop w
  where
    loop [] = return ()
    loop (x:xs) = sequence_ [putStrLn (show x), loop xs]

-- 3':
letters'' :: IO()
letters'' = do
  x <- getLine
  sequence_ . map print $ x

-- 4:
hugorm :: IO Int
hugorm = do
  putStrLn "How many numbers would you like to add: "
  n <- getLine
  loop (read n :: Int) 0
  where
    loop :: Int -> Int -> IO Int
    loop 0 x = return x
    --loop 0 x = putStrLn ( "Sum is " ++ show x)
    loop m x = do
      l <- getLine
      loop (m-1) (x + (read l :: Int))


-- a:
-- IMPORTANT: return is extremely useful, return :: a -> IO a
-- IO can be caught with "catch" in order to perform some other code in case it goes wrong.
sumInts :: Int -> IO Int
sumInts x = do
  w <- getLine
  loop ( (read w) :: Int)
  where
    loop 0 = return x
    loop n = sumInts (n+x)

-- b:
--whileIO :: IO () -> (a -> Bool) -> (a -> a -> a) -> a -> IO()
--whileIO getIO condF foldF x = do
--  w <- getIO
--  if condF ((read w) :: a) then
--    return x
--  else
--   foldF x ((read w) :: a)
