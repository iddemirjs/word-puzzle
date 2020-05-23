# Project: Word Puzzle

Dokuz Eylül University 
Project Based Learning Lesson Pratical Project May. 2020

The aim of this project is to develop a two-player game that guesses correctly the specific words in the puzzle
table. When all spaces are properly filled with the words, the puzzle will be completed.

# General Information
Word puzzle game consists of a puzzle matrix (15 x 15) and a word list (dictionary, max. 100 words), given as
text files, with the names puzzle.txt and word_list.txt respectively. The puzzle.txt file includes binary values
where the 1s symbolize the letters and 0s indicate empty spaces. After these files are read, the screen is
designed and the game starts.

# Game Steps
Each player guesses a letter from the word list and try to find a word. When a player places a letter in a position,
if the placement is correct, the player gets point and continues to insert letters, if it is inappropriate, the turn
goes to the second player. The second player tries to guess another word by placing a letter. The player gets
one point for each correct action. A word is completed when its last letter is guessed. The player who completes
a word gets 10 points. When all the words are completed on the board, the game ends and the player who has
the highest score wins the game. 


![screenshot](https://raw.githubusercontent.com/idoelogy/word-puzzle/master/libs/screen.jpeg)
