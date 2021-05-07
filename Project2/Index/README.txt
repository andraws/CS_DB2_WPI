Name: Andrew Shanaj, Student ID: 587515544

Section I:

    There are two ways of running the code:

    1) Production Ready Method (Current Method)
        (Note: Only tested on a Mac)

        From Terminal, enter the production file.
            - cd .../Index/out/production/Index
        From there type in java Controller.
            - The java program is going to start and greet with you "Program is ready and waiting for user command"

    2) Directly from the IDE
        (Note: Only tested on IntelliJ & Visual Studio Code)

    Main resides in the Control class.
    One of the issues is where the resources folder resides. You might need to change the file path to the directory.
    The directory needs to be changed on FileHelper Class and RecordLocation Class
        - The code currently works fine with IntelliJ and Visual Studio Code

Section II:

    From my testing, all parts are working. All the characteristics of the project have been followed.

Section III:

    For this project, I used the beauty of OOP.

    I have 2 helper classes.
        FileHelper Class: This class is used to read and retrieve all the records in a file as List<String>
        InputHelper Class: Used to parse the users input and decide what the user is asking

    I have 3 Classes that are used for Queries:
        TableScan: Does all 3 Queries, reads all files prints out the records based on what was asked
        HashIndex: Is used for Equality search based on a Hash Map
        ArrayIndex: Is used for Range search based on a Array

   RecordLocation Class:
        Used to store the Block (file) and Offset of the record.
        RecordLocation class can hold multiple locations.
        Used to find how many blocks we searched
        Prints all records that it holds.

   Controller Class:
        User input
        Building the Indexes
        Making the decision of the search.


FUTURE WORK:
    Input Filtering and Input Error Filtering
    Turning V into an int from the start instead of doing it on all my functions.