import Parsing

-- 1
-- Both sides of the grammar begin with the same symbol: "t <- term"
-- This will not work since no expressions will be parsed, they will all be treated
-- as terms.


-- 2

-- R := a | b | R1 o R2 | R1 U R2 | (R1)*

-- AMBIGUOUS AND LEFT-RECURSIVE
-- E -> E + E | E * E | a | (E)
-- so we convert it to:
-- E -> T + E | T
-- T -> F * T | F
-- F -> a | (E)

-- S -> T o S | T U S | T
-- T -> a | b | (S)

parseS = do parseT
            char 'o'
            parseS
            <|> do parseT
                   char 'U'
                   parseS
                   <|> parseT

parseT = char 'a'
            <|> char 'b'
            <|> do char '('
                   parseS
                   char ')'


--3



data RExp = Exp Char | OOp RExp RExp | UOp RExp RExp | Par RExp deriving Show

parseS' :: Parser RExp
parseS' = do a <- parseT'
             char 'o'
             b <- parseS'
             return (OOp a b)
            <|> do a <- parseT'
                   char 'U'
                   b <- parseS'
                   return (UOp a b)
                   <|> parseT'

parseT' :: Parser RExp
parseT' = do char 'a'
             return (Exp 'a')
            <|> do char 'b'
                   return (Exp 'b')
            <|> do char '('
                   e <- parseS'
                   char ')'
                   return (Par e)
     
              
-- 4
parser :: RExp -> String
parser (Par e) = "(" ++ (parser e) ++ ")"
parser (Exp x) = [x]
parser (OOp e1 e2) = (parser e1) ++ "o" ++ (parser e2)
parser (UOp e1 e2) = (parser e1) ++ "U" ++ (parser e2)

isSame :: String -> RExp -> Bool 
isSame s (Exp e) = s == (parser (Exp e))
isSame s (Par e) = s == (parser (Par e))
isSame s (OOp e1 e2) = s == (parser (OOp e1 e2))
isSame s (UOp e1 e2) = s == (parser (UOp e1 e2))

--isSame "aUb" (UOp (Exp 'a') (Exp 'b'))


-- a

-- BIMS :: skip | x := a | S1 ; S2 | if a1 = a2 then S1 else S2 | while b do S


