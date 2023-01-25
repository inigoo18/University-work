hello :: IO()
hello = do putStrLn "What is your name?"
           name <- getLine
           putStr "Hello "
           putStrLn name
