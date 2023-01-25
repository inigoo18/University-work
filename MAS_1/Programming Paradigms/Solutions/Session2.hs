-- 1. Write down Haskell definitions of quango and tango
-- quango :: a -> [a]
-- tnago :: Num p1 => (a, b) -> p2 -> p1

quango :: a -> [a]
quango a = [a, a, a, a]

tango :: Num tp1 => (ta, tb) -> tp2 -> tp1
tango (a, b) p2 = 42

-- Tango has parametric polymorphism (because of Num)

-- 2. Find a terminating reduction sequence of the lambda-expression
-- (λx.xy)(λz.(λu.uu)) => (λz.(λu.uu)y => λu.uu
-- (Lx.xy)(Lz.(Lu.uu)) => Lz.(Lu.uu)y => Lu.uu
