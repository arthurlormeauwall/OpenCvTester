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

This project is nothing less, nothing more. 

Users of this framework are expected to know Java and OpenCv. 


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


## Project Status
This project is still work in progress. 
The rendering part is fully working. The GUI does not exist yet. 


## Usage

>disclaimer : The main goal of this entire project is to provide en enterractive way of testing Open Cv, therefore I am perfecty aware it can not be achieved with no Gui. This project is still work in progress ! 
Since the rendering part is operationnal, I decided to share it to other people anyway. This section describe how you can test it as it is now. 

To use this framework you have to import FakeGui classe from fakeGui package. 
First you create a FakeGui object passing the path to the image you want to work with.

FakeGui classe provide methods that can mimic all the message/event the Gui will be able to send to the system :

`public void addControlInLayer(int maskedLayerIndex, int controlIndex, int controlIndexInDataBase)` : add a control in a masked layer. The first two parameters tell in wich maskedLayer and where in the chain of controls already there you want to add the new control (its coordinate in the rendering chain). The third one is the index, in the control database, of the control that will be added. 

`public void delControlInLayer(int maskedLayerIndex, int controlIndex)` : delete a control in a masked layer.  

`public void addLayer(int maskedLayerIndex, Stack<Integer> stackOfindexInDataBase)` : add a masked layer. The first parameter tell where to add the new masked layer in the chain of masked layers already there. The second one is a stack of the control indexes, in the control database, of the new controls of this masked layer.

`public void delLayer(int maskedLayerIndex)` : delete a masked layer. The first two parameters are the coordinates in the rendering chain of the control you want to delete.

`public void setAlpha(int maskedLayerIndex, Frame alpha)` 	: set the alpha mask of a masked layer with a Frame object.

`public void setAlpha(int maskedLayerIndex, int opacity)` : set the opacity of a masked layer with an int.

`public void setParameters(int maskedLayerIndex, int controlIndex, Stack<Float> parameters)` : set parameters of a given control. The first two parameters are the coordinates in the rendering chain of the control you want to set. The third one is the new values of this control parameters.


`public void setBypass(int maskedLayerIndex, int controlIndex, Boolean parameter)` : set bypass state of a control. The first two parameters are the coordinates in the rendering chain of the control you want to set. The third one is the new bypass state of this control.

`public void undo()` : undo the last thing that have been changed in the system ; could either be : parameters change (including opacity/alpha), maskedLayer added/deleted, control added/deleted. Change of the bypass state is not including in the undoable item.

`public void redo()` : see undo.

`public void store()` : store the state of an action in the history system. This method has to be called if you want to be able to undo/redo the last action. 

`public void play()` : refresh the image displayed.

`public void addAlgorithm(AdjustControlFloat algorithm)` : add your own algorithm to the database.

* How add your own algorithm the the control dataBase :

In the whole project each frame is represented by a `Frame` object so you may want to check the `Frame` class first in the *baseClasses.openCvFacade* package. 

To write your own algorithm you have to add a class that extends the `AdjustControlFloat` class (see in *baseClasses.adjustControl* package) in the *algorithmsDataBase* package. 
This class has several important things : 
* Two `Frame` objects variables : m_source and m_dest (input and output frame as yourAlgorithm(m_source)=m_dest)
* Several parameters that will be tweakable. You can access them via the m_history variable calling `m_history.getState()` method. This method returns an object of type `HistoryParameter<Stack<Float>>` ; then call `getParameter()` to get the `Stack<Float>` parameters : 
  * `m_history.getState().getParameters.get(0)` is a first parameter, 
  * `m_history.getState().getParameters.get(1)` is a second parameter and so on
* A `ControlFlags<Float>` variable that you have to initialize in your algorithm constructor or in a method called by the constructor, to let the system know about several things that you can check in the `ControlFlags` class in *algorithmsDataBase* package (number of parameters, their names, default values etc.).
* A `compute()` method where you write your algorithm.

You may really want to check `MultBgrControl` class to get a good example of how to create and set up an algorithms. 

After adding the class you just made to the package, you need to add a variable of this new type in the `DbControls` class in the *algorithmsDataBase* package. Then create the object and push it in “m_controls” stack in its constructor .

Once that is done, you can compile and run the program. 

About the main structure 
> In *application* package,`App` class contains a `Renderer` object that has a working input frame and an output frame that is displayed in its own window.  It also has a `UIImp` object that creates the GUI and calls the renderer method when events occur.
To be more specific, each element of the GUI will be able to communicate its events (ex: a moving slider) to the system by creating an `Action` object (see in *application* package) and then passing it to the `UIImp` object, calling the `dealOrder(Action parameter)` method. This method then decides, with a switch statement, which method of the `Renderer` object to call (it can also call a method of its own).   







## Contact
Created by [@arthurlormeauwall](https://github.com/arthurlormeauwall) - feel free to contact me!



