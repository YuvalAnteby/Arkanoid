# Arkanoid - Java
This project is an implementation of the classic Arkanoid game with slight alternations, developed as a project for the OOP course in Bar-Ilan university.
The game features a GUI, multiple levels, score tracking, sound effects and file managment while adhering to OOP principles and design patterns.

## Contents
[Description](#arkanoid---java) <br />
[Main Features](#main-features) <br />
[Technologies Used](#technologies-used) 

## Main Features
1. GUI: Built using Java and the biuoop-1.4.jar given by the course's lecturer and TAs.
2. File Management: High-scores are saved locally using JSON files.
3. Sound Effects: Both original Arkanoid and custom-made sound effects were incorporated into the game using java built in tools.
4. Levels and difficulties: The game consists of several levels, with progressively increasing difficulty.
5. Design Patterns:
    * Singleton: Managing global game settings and resources.
    * Observer: Used in event listening between game components, such as balls and blocks. especially used for hit detection and score updates.
    * Command: For executing actions related to menu selections.

## Technologies Used
* Java: Core programming language, used version 17.
* biuoop library: Provided by Bar-Ilan's course staff for handling GUI components and sensors.
* GSON: Used for reading and writing JSON files.
* Javax Sound API: for handling the game's sound effects.

## Installation and Setup
There are two main ways to run the game:

### Running from command line
1. Clone the repository:
```
git clone https://github.com/YuvalAnteby/Arkanoid.git
```
2. Navigate to the project directory and compile the code:
```
cd Arkanoid
javac -cp biuoop-1.4.jar:*.java
```
Run the game:
```
java -cp biuoop.jar:ArkanoidGame
```
