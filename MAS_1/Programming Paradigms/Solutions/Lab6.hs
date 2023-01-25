data Aexp = Numb Integer | Var [Char] | Add Aexp Aexp | Mul Aexp Aexp
-- 1
type Assoc k v = [(k, v)]

-- 2
find :: Eq k => k -> Assoc k v -> v
find k t = head [v | (k', v) <- t, k == k']

eval :: Aexp -> Assoc [Char] Integer -> Integer
eval (Var x) [] = error("no possible assignment")
eval (Var x) ass = find x ass
eval (Numb x) _ = x
eval (Add e1 e2) ass = (eval e1 ass) + (eval e2 ass)
eval (Mul e1 e2) ass = (eval e1 ass) * (eval e2 ass)


testExp = (Add (Var "x") (Numb 2))

a1 = [("x", 4), ("y", 5)]

-- 3

data Dir = Folder String [Dir] | File String Int
--d1 = Folder "root" [Folder "home"]


-- 4
data Tree a = Leaf a | Empty | Node ( Tree a ) a ( Tree a ) deriving Show

insert :: Ord a => Tree a -> a -> Tree a
insert Empty x = Leaf x
insert (Leaf l) x = (Node (Leaf x) l Empty)
insert (Node l ro ri) x = if x < ro then insert l x else insert ri x

testTree = Node (Leaf 1) 2 (Leaf 3)
testTree2 = Node (Node (Leaf 1) 2 (Leaf 3)) 0 (Node Empty 4 (Node Empty 9 (Node Empty 10 Empty)))

-- 5
balanced :: Tree a -> Bool
balanced Empty = True
balanced (Leaf _) = True
balanced (Node l _ r) = c && d && (a == b)
                        where a = leaves l
                              b = leaves r
                              c = balanced l
                              d = balanced r


leaves :: Tree a -> Int
leaves Empty = 0
leaves (Leaf _) = 1
leaves (Node l x r) = leaves l + leaves r
