# Arkanoid - Java

<img src="https://github.com/YuvalAnteby/Arkanoid/blob/main/resources/logo.png" alt="logo">

This project is an implementation of the classic Arkanoid game with slight alternations, developed as a project for the OOP course in Bar-Ilan university.
The game features a GUI, multiple levels, score tracking, sound effects and file management while adhering to OOP principles and design patterns.<br />
Feel free to visit my [GitHub profile](https://github.com/YuvalAnteby) for more projects and my [LinkedIn](https://www.linkedin.com/in/yuval-anteby/) for professional details.

<p align="center">
   
![alt text](https://github.com/YuvalAnteby/Arkanoid/blob/main/resources/all_lvls.gif 'Gameplay')

</p>


## Contents

- [Description](#arkanoid---java)
- [Main Features](#main-features)
- [Technologies Used](#technologies-used)
- Installation and Running:
   - [macOS and linux](#macos-and-linux)
   - [Windows](#windows)
- [Future Plans](#future-plans)
- [Credits](#credits)

## Main Features

1. GUI: Built using Java and the biuoop-1.4.jar given by the course's lecturer and TAs.
2. File Management: High-scores are saved locally using JSON files.
3. Sound Effects: Both original Arkanoid and custom-made sound effects were incorporated into the game using java built in tools.
4. Levels and difficulties: The game consists of several levels, with progressively increasing difficulty.
5. Design Patterns:
    * Singleton: Managing global game settings and resources. (e.g. screen dimensions and game resources)
    * Observer: Used in event listening between game components. especially used for hit detection between the ball and the blocks as well as score updates.
    * Command: For executing actions related to menu selections.

## Technologies Used
* Java: Core programming language, used version 17.
* biuoop library: Provided by Bar-Ilan's course staff for handling GUI components and sensors.
* GSON: For reading and writing JSON files used in the high score management code.  
* Javax Sound API: Used for handling the game's sound effects.

## Installation and Running
Installing and running the game will depend on your operating system. Using the built-in terminal of macOS/ linux and built in cmd of windows you can follow these steps to easily run the game. 

#### macOS and linux

<details>
<summary>Click to expand macOS and linux instructions</summary>

1. Clone the repository:
```bash
git clone https://github.com/YuvalAnteby/Arkanoid.git
```
2. Navigate to the project directory:
```bash
cd Arkanoid
```
3. Compile the code:
```bash
javac -cp biuoop-1.4.jar:*.java
```
4. Run the game:
```bash
java -cp biuoop.jar:ArkanoidGame
```
</details>

#### Windows

<details>
<summary>Click to expand Windows (cmd) instructions</summary>

1. Clone the repository:
```bash
git clone https://github.com/YuvalAnteby/Arkanoid.git
```
2. Navigate to the project directory:
```bash
cd Arkanoid
```
3. Compile all the source code:
```bash
for /R src %f in (*.java) do javac -cp "lib/*" -d out "%f"
```
4. Run the code:
```bash
java -cp "out;lib/*" Main
```
</details>

## Future Plans

This project is complete, and there are currently no plans to expand its features. Further contributions or suggestions for improvements are welcome.

## Credits
- Impact Sounds: The impact sounds used in this project were sourced from "The Essential Retro Video Game Sound Effects Collection [512 sounds]" by Juhani Junkala.<br />
All rights for these sounds remain with the original creator.<br />
  You can find the collection [here](https://opengameart.org/content/512-sound-effects-8-bit-style)
- biuoop-1.4.jar: The GUI library used in this project was provided by the Object-Oriented Programming (OOP) course staff at Bar-Ilan University.
