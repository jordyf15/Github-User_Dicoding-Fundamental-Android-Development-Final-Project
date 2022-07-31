# Github Users
## Final Project for Dicoding's Fundamental Android Development Course  
Github Users is an android application where users can view informations about users in Github. This project was made in order to pass the Dicoding's Fundamental Android Development Course.
## Application Detail
The application consist of 5 activities. They are Splash Screen, Main Activity, Detail Activity, Setting Activity and Favorites Activity.
### Splash Screen
The splash screen of the application which appears when the user opens the application.
<p align="center"><img width="300px" src="https://i.pinimg.com/564x/ae/a6/1e/aea61e51c79d715df0ecf3405ae551d9.jpg" alt="splash screen"></p>  

### Main Activity
The activity / page where the user can view a list of users from Github. There is also a search bar where the user can search for users based on their username. If one of the github users were clicked. Then the Detail Activity of that clicked github user will be opened.
<p align="center">
<img width="300px" src="https://i.pinimg.com/564x/4e/0a/84/4e0a84dbc25d5347ceb6af1d8ac66ebf.jpg" alt="main activity 1">
<img width="300px" src="https://i.pinimg.com/564x/4c/b4/9f/4cb49ffb9bb8c2dd4506acb1b30e697e.jpg" alt="main activity 2">
</p>
  
### Detail Activity
The activity / page where user can see more detailed information about the chosen github user such as username, fullname, location, workplace, followers, followings, etc. There is also a favorite button in the bottom right corner of the screen which will add the chosen github user to the favorite list or remove them from the favorite list if they are already in it.

<p align="center">
<img width="300px" src="https://i.pinimg.com/564x/da/bd/a5/dabda5f6fb705aa80ee5b7b1d78468b5.jpg" alt="detail activity 1">
<img width="300px" src="https://i.pinimg.com/564x/22/bf/ac/22bfac5c8b7ea25aaa63bcae63de6706.jpg" alt="detail activity 2">
</p>

### Setting Activity
The activity / page where the user can toggle between dark mode or light mode theme for the application. This page can be accessed by clicking the setting icon in the upper right corner of the application.  

<p align="center">
<img width="300px" src="https://i.pinimg.com/564x/85/b2/fa/85b2fa1ae1b1e2e92776992a3e11fb6d.jpg" alt="setting activity">
</p>

### Favorite Activity 
The activity / page where the user can view a list of their favorite github users. This page can be accessed by clicking the heart icon in the upper right corner of the application.

<p align="center">
<img width="300px" src="https://i.pinimg.com/564x/33/79/3b/33793b07e44fdb36d9ba983e3b4f5662.jpg" alt="favorite activity">
</p>

## Installation
Clone this repository and import it into android studio
https://github.com/jordyf15/Github-User_Dicoding-Fundamental-Android-Development-Final-Project.git

Note: You will need to provide your own [github api token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token) in a file named "env.properties" in the root folder of the project. In order to build and run this application properly.  
Example:  
```
GITHUB_TOKEN = "loremipsumdolorloremipsumdolorlorem"
```