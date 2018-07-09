# Popcorn

## What it does?
Popcorn is a cool Android app that lets you search for movies from omdbapi.com and will show it to you in a very fancy list that includes the movie poster!

## How it looks?
Some screenshots:

![alt text](https://github.com/francoj11/popcorn/blob/master/screenshots/1small.png "Screenshot 1")
![alt text](https://github.com/francoj11/popcorn/blob/master/screenshots/2small.png "Screenshot 2")
![alt text](https://github.com/francoj11/popcorn/blob/master/screenshots/3small.png "Screenshot 3")
![alt text](https://github.com/francoj11/popcorn/blob/master/screenshots/4small.png "Screenshot 4")

## How it works?
The app uses Google as an authentication provider and is the entry point of the app. You must sign in with your Google account. After that, you can search for movies by title and year and the the latest 10 results. You can also mark some movies as favorites and see them in the Favorites sections of the app. You can also download the movie poster to your external storage.

## Technical stuff
The app is based in the new Architecture Components provided by Google in 2017 and is written in Kotlin (the new official language to write Android apps since 2017 as well). 

The app is constructed with an MVVM architecture and uses these well-known libraries:
* Dagger: For Dependency Injection
* Room: For easy SQLite interaction and save the favorites
* LiveData: To implement the Reactive paradigm with lifecycle-component-aware objects
* ViewModel: To implement the MVVM architecture
* Retrofit: For easy REST requests
* Picasso: To manage async images and cache efficiently
    
-----------------
## Developed by:
Franco Jaramillo - francoj11@gmail.com

## License:
    Copyright 2018 francoj11

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
