# Final project

This was my school final project. I have created a Windows App in JavaFX with MySQL DB.

The database contains different connected tables that were necessary for this system. This repository contains "Dump" folder with the database information.

There are two types of system users. One is elevated user (admin) and the other is basic user (which would be bus station worker). Each user is prompted
with a login form when opening the app. Their interfaces and options are different.

1. Admin has interfaces for managing all tables of the database. The tables primarily define information about bus routes and bus schedule, but also user information.
2. User has options for searching through bus schedule using different parameteres. After an item from the schedule is selected, user has the option to create
a ticket. Options for deleting tickets are also available.
