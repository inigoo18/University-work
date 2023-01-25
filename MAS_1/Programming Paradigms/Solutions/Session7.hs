-- 1

\x. letrec sumN = \y. if y = 0
                      then 0
                      else plus y (sumN (minus y 1))
        in sumN x

-- 2
data LExp = Var String | Abs String LExp | App LExp LExp | Let String LExp LExp | IF LExp LExp LExp | LetRec String LExp LExp | C String
