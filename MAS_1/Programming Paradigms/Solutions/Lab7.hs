-- b
data Expr = Val Int | Add Expr Expr

value :: Expr -> Int
value (Val n) = n
value (Add x y) = value x + value y

foldexp :: (Int -> a) -> (a -> a -> a) -> Expr -> a
foldexp f g (Val x) = f x
foldexp f g (Add x y) = g a b
                        where a = foldexp f g x
                              b = foldexp f g y

eval :: Expr -> Int
eval = foldexp id (+)

pretty :: Expr -> String
pretty = foldexp show (\x y -> x ++ " + " ++ y)

testExp = Add (Val 3) (Add (Val 2) (Val 1))
