# Open Cv tester

> This project provides a JAVA framework to any developer willing to test their own algorithm in a simple GUI and real-time rendering  environment. 

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Contact](#contact)


## General Information

When I started to learn OpenCv, one of the first things I did was to access pixels data and change their values ; at that time I really just wanted to have real-time control over algorithms and effects while running the application.

After connecting a simple slider to one of my algorithm parameters, I thought it would be a good exercise to add undo/redo functionality, and a Photoshop like layer structured rendering system with control over each layer opacity. 


## Technologies Used

- OpenCv version 3.4.15


## Features
- Add and delete algorithms. Group them in layers with opacity control over each layer in a Photoshop like style. 
- Tweak parameters and see changes to the resulting image in real-time.
- Undo and redo with no limitation


## Setup
You can download OpenCv here https://opencv.org/releases/
If you are an Eclipse user, you can follow this tutorial to setup OpenCv in Eclipse :
https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html





## Usage

In the whole project each frame is represented by a `Frame` object so you may want to check the `Frame` class first in the *baseClasses.openCvFacade package*. To access the `Mat` object just call the `getFrame()` method. 

To write your own algorithm you have to add a class that extends the `AdjustControlFloat` class in the *Algo.database package*. 
This class has several important things : 
* Two `Frame` objects : m_source and m_dest (input and output frame as yourAlgorithm(m_source)=m_dest)
* Several parameters that will be tweakable. You can access them via the m_history object calling `m_history.getState()` method. This method returns an object of type `HistoryParameter<Stack<Float>>` ; then call `getParameter()` to get the `Stack<Float>` parameters : 
  * `m_history.getState().getParameters.get(0)` is a first parameter, 
  * `m_history.getState().getParameters.get(1)` is a second parameter and so on
* A `ControlFlags<Float>` object (“m_flags”) that you have to initialize in your algorithm constructor or in a method called by the constructor, to let the system know about several things that you can check in the ControlFlags class in *baseClasses.enums_structs* package (number of parameters, their names, default values etc.).
* A `compute()` method where you write your algorithm.

You may really want to check `MultBgrControl` class to get a good example of how to create and set up and algorithms. 

After adding the class you just made to the package, you need to add a variable of this new type in the `DbControls` class in the *Alog.database* package. Then create the object and push it in “m_controls” in its constructor .

Once that is done, you can compile and run the program. 

About the main structure 
> I decided to fully separate the rendering part from the GUI. `App` class contains a `Renderer` object that has a working input frame and an output frame that is displayed in its own window.  It also has a `UIImp` object that creates the GUI and calls the renderer method when events occur.
To be more specific, each element of the GUI will be able to communicate its events (ex: a moving slider) to the system by creating an `Action` object (see in *baseClasses.enums_structs* package) and then passing it to the `UIImp` object, calling the `dealOrder(Action parameter)` method. This method then decides, with a switch statement, which method of the `Renderer` object to call (it can also call a method of its own).  



## Project Status
This project is still work in progress. 
The rendering part is fully working. The GUI does not exist yet. 
If you want to test the program, you have to write your code in the `Test` class constructor (in the test package). Since there is no GUI, you need to mimic it and manually create your own `Action` object and pass it to the `dealOrder()` method via the `getMainWin()` method. 


## Contact
Created by @arthurlormeauwall - feel free to contact me!



