import Parsing

data Onion = Core Int | Layer Onion deriving Show

theonion :: Parser (Onion)
theonion = do char 'L'
              o <- theonion
              return (Layer o)
                <|> do x <- int
                       return (Core x)

ab :: Parser String
ab = do char 'a'
        x <- ab
        char 'b'
        return ('a' : (x ++ "b"))
        <|> do string ""
