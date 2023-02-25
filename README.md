# Unconquered Fortress

This is a "Tower Defense" genre game written in Java with LibGDX and Spring Boot frameworks. The game was created in a two-person team as an engineering thesis entitled:

+ *in Polish:*
#### `Gra typu „Tower Defense” wykorzystująca mechanizm generowania proceduralnego`

+ *in English:*
#### `A “Tower Defense” game using the procedural generation mechanism`

&nbsp; &nbsp; &nbsp; &nbsp;

The main idea was to focus on random generated content like for example:
+ map
+ enemies
+ loot
+ events

## Disclaimer
All copyrights to both graphics and code belongs to their authors. All sources and materials have been published just to be saved for history and as an example of game programming in Java for who may concern.


## Download

Game is finished and can be run using generated [exe file](https://drive.google.com/u/0/uc?id=1vqoZ8zdhXwysp8hUJm1gce_xpaSdJDoU&export=download "Download game").

## Building

To compile and run this project firstly clone it from this repository. Then it is recommended to open it with Java IDE like "IntelliJ", as it will download all required files automaticlly. 
After all necessary downloads you can simply run project by running `main` function located in `./desktop/src/com/game/DesktopLauncher.java` class. In special cases you will have to manually change project's JDK to 17. 

## Server

MongoDB Atlas was used a cloud database allowing to login/register and save or download saves globally allowing to get them on all devices. Database is simple and contains only 2 collections.

## Tests

Tests mostly uses JUnit for testing functionality and proper way of working game. Complex tests were hard to write because most content and class usage has place in "Screen" classes so elaboreted tests using the reflection mechanism. 
Tests have their own module called `tests` which contains 2 test files `Tests` and `ScreenTests`. Running them launches tests.
