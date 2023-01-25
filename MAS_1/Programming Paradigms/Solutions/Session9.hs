-- 1
data Onion a = Core a | Layer (Onion a)

instance Functor Onion where
  -- fmap :: (a -> b) -> Onion a -> Onion b
  fmap g (Core x) = Core (g x)
  fmap g (Layer x) = Layer (fmap g x)
  -- g :: a -> b
  -- x :: Onion a

myOnion = Layer ( Layer ( Core "onion" ) ) )
-- fmap length myOnion
-- >> Layer ( Layer ( Core 5 ) ) )

-- 2
-- Laws:
-- pure id <*> x = x
-- pure (g x) = pure g <*> pure x

-- We know: pure x = Just x
-- Nothing <*> _ = Nothing
-- (Just g) <*> mx = fmap g mx

-- From first law,
-- pure id <*> x
-- = (Just id) <*> x
-- = <*> takes id, puts it with x and returns x.
