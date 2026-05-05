# Pass Task 9.1

## MainActivity.java

This file contains my java code. The java code is used for when the user interacts with my app it calls the other java classes

## AddActivity.java
This class is used to manage the backend when a advert is created by the user.

## ImageUtil.java
Used as helper class to help convert image from bitmap to base64 code.

## ItemActivity.java
This class manages interacts with individual adverts and what information is displayed. Also removing a advert.

## MainActivity.java
Is starting point and handles navigation to other activites.

## ViewActivity.java
Connects event data to the RecyclerView. It displays events in a list and handles user interactions such as opening the update dialog.

## adapter.java
Helps communicate betweeen the database and user input and interactions.

## DBHelper.java
This class is used to initate database and also manage and update it.

## MapActvitity.java
This file contains all the logic for the Geo map feautures. It is resposible for controlling how the map is displayed and the points on the map. It also makes sure points are not shown outside of the radius.

## activity_main.xml

This file was used to design the UI of my app and the different UI widgets. I have used a linear layout instead of a constrait layout for my project

## activity_add.xml
UI layout for adding new events.

## activity_list.xml
Displays the list of events using a RecyclerView.

## activity_details.xml
Defines how each event appears in the list.

## activity_map.xml

This sets up the google map as a fragment so the user can use the google API.

## item.xml
Layout for each individual item in the RecyclerView.

## colors.xml

This file contains all the colors I have created. I have assigned them a name and a HEX color code.

## strings.xml

This file contains all the strings I am using and the value of the string.

## google_maps_api.xml

This contains my API key to connect to Google Geo features

## Hardware

I have used an emulator for a android phone running Android 16.0 (Baklava)
