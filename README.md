# Full Stack Assesment (Full Time)
## Introduction
You will be implementing an Android application using Android Studio that will call the national weather api and populate the forecast on a screen.

All resources have been provided to you. It will be your job to implement these resources into a functioning weather forecast app.

This should take a few hours to complete, please keep track of your time as you go and get as much done as you can.

## The UI
The following are the two screens we would like to see implemented.
<p align=center>
  <img src="https://user-images.githubusercontent.com/82067568/210892468-8f4c94ff-b085-43af-97a4-2e55150b7b7c.png" height = "500">
</p>

### Screen #1 
Should be implmented as closely as possible to the provided screen. Margins, text size, and shades of colors don't have to be precise.

### Screen #2
Should consist of a textview on top and a recyclerview on the remainder of the screen. 

Every forecast should have a corresponding:
- icon
- time of day
- temperature

If the Api fails to call then display the failure in the textView


## Function 

When Screen #1's forecast button is pressed the application should initialize the forecast activity, which will call the national weather api for the forecast of a given location.

**Note: Please pay attention to viewModels and the special properties they provide for asynchronous network calls**

Calling the weather api is a two step process.

- The first step is to send a location request. The response will be two grid coordinates. 

- The second step will be to send a forecast request with the previously given grid coordinates. The response will be the finale forecast for the next couple of days.

Please reference the following link: https://weather-gov.github.io/api/general-faqs

After receiving the weekly forecasts load them into a recyclerView.

## When Finished

When you have completed the assessment, email molly@ascentitech.com with the link to your new repository and answer the following questions as honestly as you can.  

## Final Questions
1) How long did the assessment take to complete?  Was there any task that took significantly longer than the others, and if so why?
2) Were there any tasks that you initially did not know how to complete? And if so, how did you approach them and take the steps to learn how to complete them?
3) Was this assessment feasible for the time given and relatively easy to follow? Any points of feedback? 

