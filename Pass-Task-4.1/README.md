# Pass Task 4.1

## MainActivity.java

This file contains my java code. The java code is used for when the user interacts with my app. It populates the spinners, calcuations and user input.

## Event.java
This is the Room Entity class. It defines the structure of the event table in the database, including fields such as title, category, location, date, and time.

## EventDAO.java
Contains all database operations.

## EventDatabase.java
This class sets up the Room database instance and provides access to the DAO.

## AddEventFragment.java
Handles the creation of new events. It collects user input, validates the data, and saves it to the database.

## EventAdapter.java
Connects event data to the RecyclerView. It displays events in a list and handles user interactions such as opening the update dialog.

## EventListFragment.java
Displays all saved events using a RecyclerView. It retrieves data from the Room database and refreshes when the fragment resumes.

## activity_main.xml

This file was used to design the UI of my app and the different UI widgets. Everything you see is designed in this file. I have used a linear layout instead of a constrait layout for my project

## create_fragment.xml
UI layout for adding new events.

## List_fragment.xml
Displays the list of events using a RecyclerView.

## list_item.xml
Defines how each event appears in the list.

## update_dialog.xml
Layout for the update dialog. Allows users to edit all event fields and delete events.

## colors.xml

This file contains all the colors I have created. I have assigned them a name and a HEX color code.

## strings.xml

This file contains all the strings I am using and the value of the string.

## Hardware

I have used an emulator for a android phone running Android 16.0 (Baklava)

