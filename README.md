# Arkanoid - Java
This project is an implementation of the classic Arkanoid game with slight alternations, developed as a project for the OOP course in Bar-Ilan university.
The game features a GUI, multiple levels, score tracking, sound effects and file managment while adhering to OOP principles and design patterns.

## Contents
[Description](#arkanoid---java) <br />
[Main Features](#main-features)
## Main Features
1. GUI: Built using Java and the biuoop-1.4.jar given by the course's lecturer and TAs.
2. File Management: High-scores are saved locally using JSON files.
3. Sound Effects: Both original Arkanoid and custom made sound effects were incorporated into the game using java built in tools.
4. Levels and difficulties: The game consists of several levels, with progressively increasing difficulty.
5. Design Patterns:
    1. Singleton: Managing global game settings and resources.
    2. Observer: Used in event listening between game components, such as balls and blocks. especially used for hit detection and score updates.
    3. Command: For executing actions related to menu selections.

