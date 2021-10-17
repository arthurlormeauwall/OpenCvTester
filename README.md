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

This project is aim to achieve that and its purpose is mostly educationnal. Moreover, users of this framework are expected to know Java and OpenCv. 


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

>disclaimer : The main goal of this entire project is to provide en enterractive way of testing Open Cv, therefore I am perfecty aware it can not be achieved with no GUI. This project is still work in progress ! 
Since the rendering part is operationnal, I decided to share it to other people anyway. This section describe how you can test it as it is now. 

To use this framework you have to import `FakeGui` classe from *fakeGui* package. 
First you create a `FakeGui` object passing the path to the image you want to work with.

`FakeGui` classe provide several methods that can mimic all the message/event the GUI will be able to send to the system :

`public void addControlInLayer(int maskedLayerIndex, int controlIndex, int controlIndexInDataBase)` : add a control in a certain maskedlayer at a certain index. The third parameter is the index, in the control database, of the control that will be added. 

`public void delControlInLayer(int maskedLayerIndex, int controlIndex)` : delete a certain control in a certain masked layer.  

`public void addLayer(int maskedLayerIndex, Stack<Integer> stackOfindexInDataBase)` : add a masked layer. The second parameter is a stack of the control indexes, in the control database, of the new controls of this masked layer.

`public void delLayer(int maskedLayerIndex)` : delete a certain masked layer.

`public void setAlpha(int maskedLayerIndex, Frame alpha)` 	: set the alpha mask of a certain masked layer with a `Frame` object.

`public void setAlpha(int maskedLayerIndex, int opacity)` : set the opacity of a certain masked layer with an integer data.

`public void setParameters(int maskedLayerIndex, int controlIndex, Stack<Float> parameters)` : set parameters of a certain control.

`public void setBypass(int maskedLayerIndex, int controlIndex, Boolean parameter)` : set bypass state of a certain control. 

`public void undo()` : undo the last thing that have been changed in the entire system ; could either be : parameters change (including opacity/alpha), maskedLayer added/deleted, control added/deleted. Change of the bypass state is not including in the undoable things.

`public void redo()` 

`public void store()` : store the state of an action in the history of the last teaked control or control chain. This method has to be called if you want to be able to undo/redo the last action. 

`public void play()` : refresh the image displayed.

`public void addControlInDataBase(AdjustControlFloat newControl)` : add your own control to the database.


* How add your own algorithm the the control dataBase :

In the beginning, your control database is empty. 
To write your own control you have to create a class that extends the `AdjustControlFloat` class.

`AdjustControlFloat` class provide several important things : 
* Two `Frame` objects : source and dest (input and output frame as yourAlgorithm(source)=dest). To get the `Mat` object from a Frame object you call the 'getFrame()` mehtod.
* Several parameters that will be tweakable. You can access them calling `getParameter(int index)` method.
* Two abstract method that you have to implement : 
  * `public void setParameterFlags()` : here you create parameters calling `addParameter(String name, Float defaultValue)` for each parameter. You can also set certain special values for those parameters with `setZeroEffectValues(Stack<Float> zeroEffectValues)` : when parameters are set to the those values, bypass is automtically set to true preventing long time computing for nothing.
  * `public void compute()` : here you write your algorithm. Do not forget to update `dest` variable at the end (you can call `setFrame(Mat frame)`).

Then you have to create an object of this new class and add it to the control database calling the `addControlInDataBase(AdjustControlFloat newControl)` method.

Once that is done, you can call methods of the FakeGui object to mimic the GUI and test your algorithm.
In the example folder you may find Main.java and a MultbgrControl.java that show all this process. 


## Contact
Created by [@arthurlormeauwall](https://github.com/arthurlormeauwall) - feel free to contact me!



