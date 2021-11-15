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

When I started to learn OpenCv, one of the first things I did was to access pixels data and change their values ; at that time I really just wanted to have real-time control over filters while running the application and be able to undo and redo action.

This project is aim to achieve that and its purpose is mostly educational. 

Moreover, users of this framework are expected to know Java and OpenCv.

This frameWork can also be used without Open Cv.


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
What is currently missing :
* undo/redo functionalities
* bypass filter


## Usage

To use this framework, you first have to import `App` classe from *guiManager* package. Then you create an `App` object.

If you want to use open Cv you call `app.initOpenCv();`

Then you call `initialize(String fileName)` passing the path to the image you want to work with.

Filters are objects that you create and add to the `FiltersDataBase` object, managed by the `App` object (so you never have to deal directly with the `FiltersDataBase`).  
In the beginning, the filters database is empty. 

To write your own filter class you have to create a class that extends the `FilterControlledByFloat` class (`import baseClasses.filter.FilterControlledByFloat;`).

`FilterControlledByFloat` class provide several important things : 
* Two `Frame` objects : `source` and `dest` (input and output frame as yourFilter(source)=dest). You can use these 2 methods do access and set pixels at certain row and column : 
	* `public double[] getPixelAt(int row, int col);`
	* `public void setPixelAt(int row, int col, double[] data);`
	*   When using open cv you can call `getMat()` mehtod to get `Mat` object. 
	*   If you are not using open cv you can call `public BufferedImage getBufferedImage()`.
* Several float parameters that will be tweakable in the GUI. You can access them calling `getParameter(String name)` method.
* Three abstract methods that you have to implement : 
  * `public void setParameterFlags()` : here you create parameters calling `addParameterFlag(String name, Float defaultValue, Float zeroEffectValues)` for each parameter. When parameters are set to values specified by zeroEffectValues, bypass is automtically set to true.
  * `public void execute()` : here you write your algorithm. 
  * `public YourType createNew() {	
		return new YourType();
	}`

In the main method you create an object of this new class and add it to the filters database calling `addFilterInDatabase(String name, FilterControlledByFloat filter)` method of the `App` object.

Once that is done, you can run the programm. 

In the example folder you may find Main.java and two filter of my own : BlueGreenRedMultiplierFilter and GrayScaleFilter. 

## Contact
Created by [@arthurlormeauwall](https://github.com/arthurlormeauwall) - feel free to contact me!



