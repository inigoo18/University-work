-- 1
tuple :: Monad m => m a -> m b -> m (a, b)
tuple ma mb = ma >>= (\x -> mb >>= (\y -> pure (x, y)))

tuple' :: Monad m => m a -> m b -> m (a, b)
tuple' ma mb = do a <- ma
                  b <- mb
                  return (a,b)

-- tuple (Just 1) (Just 2)

-- 2
-- do
-- y <- z
-- s y
-- return (f y)

-- becomes:
-- z >>= (\y -> s y >> return $ f y)

pairs :: [a] -> [b] -> [(a,b)]
pairs xs ys= do x <- xs
                y <- ys
                return (x,y)
