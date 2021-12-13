# Brick Destroyer
## Info
This is a simple video game inspired by the classic arcade game.
The player's goal is to destroy a brick wall with a small ball.

## Game controls
| Keys  | Description |
|-------| ---- |
| SPACE | Start/pause the game |
| A     | Move left |
| D     | Move right |
| ESC   | Pause menu |
| F1    | Open console |

The game *automatically pauses* if the frame loses focus e.g. clicking on another window.

Enjoy 🙃 

# Changes
## Ball
- Changed variable names (`center` to `ballCenter`)
- Removed the extra methods (`wall.setBallXSpeed` and `wall.setBallYSpeed`) so `DebugPanel` now uses the existing methods (`ball.setXSpeed` and `ball.setYSpeed`) in `Ball.java` instead
- Spaced out brackets and commas for clarity
- Changed ball colour to fully black for theming

## BlackBall (formerly RubberBall)
- Changed class name for accuracy to ball colour
- Changed ball colour to black for theming
- Spaced out brackets and commas for clarity

## Brick, BrickOne, BrickTwo, BrickThree
- Changed names of `ClayBrick.java`, `CementBrick.java`, and `SteelBrick.java` to `BrickOne.java`, `BrickTwo.java`, and `BrickThree.java`
- Changed colour of the three bricks
- Changed order of `CEMENT` and `STEEL` in `Wall.java` so it reflects accurately
- Spaced out brackets and commas for clarity

## BrickFour
**Special level that is similar to levels featuring BrickTwo and BrickThree**.

The file is similar to the other bricks.

## Crack
- Put `Crack` class into its own file called `Crack.java` (original file had `Crack` inside `Brick.java`, which is fine, but that's too much responsibility for a single class)
- Made changes to `Wall.java`, `CementBrick.java`, and `Brick.java` so variables and their values are shared properly
- Spaced out the brackets and commas for clarity

## DebugConsole, DebugPanel
Spaced out brackets and commas for clarity

## GameBoard
Spaced out brackets and commas for clarity

## GameFrame
Spaced out brackets and commas for clarity

## InfoPage
A clone of `MenuPage` with only some text and a back button to main menu

## MainGameApp
Renamed to make it clearer to users

## MenuPage
- Added background image
- Added info button that links to menu page
- Spaced out brackets and commas for clarity

## Player
- Fixed typo `movRight()` to `moveRight()`
- Changed paddle colour to fully black
- Changed variable name of `ballPoint` to `initPos` for clarity
- Changed method name of `move()` to `movePaddle()` for clarity
- Spaced out brackets and commas for clarity

## Wall
- Changed the words in Pause Menu (_Continue_ -> _Resume_, and _Exit_ -> _Retreat_)
- Changed variable names of `brickCnt` and `lineCnt` to `brickTotal` and `lineTotal` respectively for the sake of clarity
- Spaced out brackets and commas for clarity