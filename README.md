# Open Cv tester

> This project provides a Java framework to any developer willing to test their own OpenCV based filters in a simple GUI and real-time rendering environment.

## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Usage](#usage)
* [Project Status](#project-status)
* [Contact](#contact)


## General Information

When I started to learn OpenCv, one of the first things I did was to access pixels data and change their values ; at that time I really just wanted to have real-time control over filters while running the application and be able to undo and redo action.

This project is aim to achieve that and its purpose is mostly educational. 

Moreover, users of this framework are expected to know Java and OpenCv.

## Technologies Used

- OpenCv version 3.4.15
- Java(TM) SE Runtime Environment (build 16.0.1+9-24)
- Maven 4.0.0


## Features
- Add and delete filters. Group them in layers with opacity control over each layer in a Photoshop like style.
- Tweak parameters and see changes to the resulting frame in real-time.
- Undo and redo with no limitation.


## Project Status
This project is still work in progress.
What is currently missing :
* Integrate image and control in a monolith UI for better UX
* Undo/Redo add and delete layer and filter
* open image from directory/ Save image to directory


## Usage

To use this framework, you first have to import `App` classe from *com.opencvtester.app* package. Then you create an `App` object passing passing the path to the image you want to work with tp its constructor.

### Filters
Filters are objects that you create and add to the `FiltersDataBase` object.
In the beginning, the filters database is empty. 

To write your own filter class you have to create a class that extends the `FilterControlledByFloat` class (`import com.opencvtester.baseClasses.filter.FilterControlledByFloat;`).

`FilterControlledByFloat` class provide several important things : 
* Two `Frame` objects : `frameIn` and `frameOut` (input and output frame as yourFilter(frameIn)=frameOut). Important methods of `Frame` class : 
	* `double[] getPixelAt(int row, int col);`
	* `void setPixelAt(int row, int col, double[] data);`
	*   `getMat()` to get `Mat` object and `set(Mat mat)` to set `Mat`
	*   You can also call `BufferedImage getBufferedImage()` if you want to work with BufferedImage. 
* Several float parameters that will be tweakable in the GUI. You can access them calling `getParameter(String name)` method.
* Three abstract methods that you have to implement : 
  * `public void setParameterFlags()` : here you create parameters calling `addParameterFlag(String name, Float defaultValue, Float zeroEffectValues)` for each parameter. When parameters are set to values specified by zeroEffectValues, bypass is automtically set to true.
  * `void execute()` : here you write your algorithm. 
  * `YourType createNew() {	
		return new YourType();
	}`

In the main method you create an object of this new class and add it to the filters database calling `addFilterInDatabase(String name, FilterControlledByFloat filter)` method of the `App` object.

Once that is done, you can run the programm. 

In the example folder you may find Main.java and two filter of my own : BlueGreenRedMultiplierFilter and GrayScaleFilter. 

## Contact
Created by [@arthurlormeauwall](https://github.com/arthurlormeauwall) - feel free to contact me!



