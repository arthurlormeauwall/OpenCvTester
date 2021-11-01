# Open Cv tester

> This project provides a Java framework to any developer willing to test their own OpenCV based filters in a simple GUI and real-time rendering environment.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Usage](#usage)
* [Project Status](#project-status)
* [Contact](#contact)


## General Information

When I started to learn OpenCv, one of the first things I did was to access pixels data and change their values ; at that time I really just wanted to have real-time control over filters while running the application.

After connecting a slider to one of my filter parameters, I thought it would be a good exercise to add undo/redo functionality, and a Photoshop like layer structured rendering system with control over each layer opacity.

This project is aim to achieve that and its purpose is mostly educational. Moreover, users of this framework are expected to know Java and OpenCv.


## Technologies Used

- OpenCv version 3.4.15
- Java(TM) SE Runtime Environment (build 16.0.1+9-24)


## Features
- Add and delete filters. Group them in layers with opacity control over each layer in a Photoshop like style.
- Tweak parameters and see changes to the resulting frame in real-time.
- Undo and redo with no limitation.


## Setup
You can download OpenCv here https://opencv.org/releases/

If you are an Eclipse user, you can follow this tutorial to setup OpenCv in Eclipse :
https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html


## Project Status
This project is still work in progress.
The rendering part is fully working. The GUI does not exist yet.
>The main goal of this entire project is to provide an interactive way of testing Open Cv, therefore I am perfecty aware it can not be achieved without GUI. 
Since the rendering part is operational, I decided to share it anyway. Usage section describes how you can test it as it is now.


## Usage

To use this framework, you first have to import `FakeGui` classe from *fakeGui* package. Then you create a `FakeGui` object, passing the path to the image you want to work with to its constructor.

`FakeGui` classe provides several methods that can mimic all the messages/events the GUI will be able to send to the system; moreover it shows the final frame in a window and refresh it each time the frame is changed.

### 1. Filters database :

In this framework filters are objects that you create and add to the `FiltersDataBase` object, managed by the `FakeGui` object (so you never have to deal directly with the `FiltersDataBase`).  
In the beginning, the filters database is empty. 

To write your own filter class you have to create a class that extends the `FilterControlledByFloat` class (`import baseClasses.filter.FilterControlledByFloat;`).

`FilterControlledByFloat` class provide several important things : 
* Two `Frame` objects : `source` and `dest` (input and output frame as yourFilter(source)=dest). To get the `Mat` object from a `Frame` object you call the `getMat()` mehtod.
* Several float parameters that will be tweakable in the GUI. You can access them calling `getParameter(String name)` method.
* Three abstract methods that you have to implement : 
  * `public void setParameterFlags()` : here you create parameters calling `addParameterFlag(String name, Float defaultValue, Float zeroEffectValues)` for each parameter. When parameters are set to values specified by zeroEffectValues arguments, bypass is automtically set to true.
  * `public void execute()` : here you write your algorithm. Do not forget to update `dest` variable at the end (you can call `setFrame(Mat frame)`).
  * `public YourType createNew() {	
		return new YourType();
	}`

In your code, you create an object of this new class and add it to the filters database calling `addFilterInDatabase(String name, FilterControlledByFloat filter)` method of the `fakeGui` object.

Once that is done, you can call methods of the FakeGui object to mimic the GUI and test your algorithm.
In the example folder you may find Main.java with example of tests and two filter of my own : MultbgrControl and GrayScaleControl. 

### 2. The FakeGui classe

Here are all the methods that you can use :

- `public void addFilterInLayer(int layerIndex, int filterIndex, String filterNames)` : add a filter in a certain layer at a certain index. The third parameter is the name in the filter database of the filter to add.

- `void delFilterInLayer(int layerIndex, int filterIndex)` : delete a certain filter in a certain layer.

- `public void addLayer(int layerIndex, Stack<String> filtersNames)` : add a layer. The second parameter is a stack of names, in the filter database, of the new filters of this layer.

- `public void delLayer(int layerIndex))` : delete a certain layer.

- `public void setAlpha(int layerIndex, Frame alpha)` 	: set the alpha mask of a certain layer with a `Frame` object.

- `public void setAlpha(int layerIndex, int opacity)` : set the opacity of a certain layer with an integer data.

- `public void setParameters(int layerIndex, int filterIndex, HashMap<String,Float> parametersValues)` : set parameters of a certain filter.

- `public void setBypass(int layerIndex, int filterIndex, Boolean bypass)` : set bypass state of a certain control. 

- `public void undo()` and  `public void redo()`  : undo/redo the last thing that have been changed in the entire system ; could either be : parameters change (including opacity/alpha), layer added/deleted, filter added/deleted. Change of the bypass state is not including in the undoable things.

- `public void addFilterInDataBase(String name, FilterControlledByFloat newfilter)` : add your own filter to the database.


## Contact
Created by [@arthurlormeauwall](https://github.com/arthurlormeauwall) - feel free to contact me!



