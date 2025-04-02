---
layout: page
title: User Guide
---

**Welcome to SilverCare!**

SilverCare is a **simple desktop app** that helps you keep track of your patients during home visits.
It’s built to be **fast** and **easy** to use, so you can spend less time on admin and more time with your patients.

Most actions, like adding a new patient or looking someone up, are done by typing **short commands**.
It might seem unfamiliar initially, but it becomes second nature with some practice.

There are also a few helpful buttons for things like getting help, changing the colour theme, or closing the app, just what you need, without the clutter.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Check if Java is installed
   
    Make sure your computer has Java 17 or above installed.
   
    **Installation:** Please follow the installation instructions for [Windows](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html#GUID-DAF345BA-B3E7-4CF2-B87A-B6662D691840)/[Mac](https://se-education.org/guides/tutorials/javaInstallationMac.html)/[Linux](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-linux-platforms.html) users.
2. Click [here](https://github.com/AY2425S2-CS2103T-T12-4/tp/releases) to download SilverCare's latest .jar file (this is the app).
3. Create a folder named ‘SilverCare’ in your Desktop.
4. Move the downloaded file into the folder.
5. Open the app
   * For Windows users, open Command Prompt and enter the following:
     1. `cd Desktop/SilverCare`
     
        OR `cd OneDrive/Desktop/SilverCare` if your Desktop is in OneDrive
     2. `java -jar silvercare.jar`
   * For Mac/Linux users, open Terminal and enter the following:
     1. `cd Desktop/SilverCare`
     2. `java -jar silvercare.jar`


   ![Ui](images/Ui.png)

6. Start fresh!

   When you first open SilverCare, you might see some sample patient data already filled in.
   To clear this and start with your own records, just type: `clear`

7. Need [help](#viewing-help--help)? It’s always nearby
   
   You can open a quick help window anytime by:
   * Typing: help
   * Pressing F1, or
   * Clicking the Help button at the top menu bar.

The Help window shows example commands to get you started, and even includes a link back to this full user guide, so you can return here anytime if you need more details.

8. Explore more [features](#features) below
   
   Scroll down to learn how to add, search for, and manage your patients using simple commands.

9. You're ready to go! That’s it, SilverCare is set up and ready. Start managing your patient records with ease!

> For a quick overview, check out our [Command Summary](#command-summary), which lists all the key commands in one place.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* `APPOINTMENT_DATE` given must be of the form either `yyyy-MM-dd` or `yyyy-MM-dd HH:mm`.

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add -n NAME`, `NAME` is a parameter which can be used as `add -n John Doe`.

* Items in square brackets are optional.<br>
  e.g `-n NAME [-t TAG]` can be used as `-n John Doe -det friend` or as `-n John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[-t TAG]…​` can be used as ` ` (i.e. 0 times), `-t friend`, `-t friend -t family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `-n NAME -p PHONE_NUMBER`, `-p PHONE_NUMBER -n NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add -n NAME -p PHONE_NUMBER -a ADDRESS [-d APPOINTMENT_DATE] [-t TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add -n John Doe -p 98765432 -a John street, block 123, #01-01`
* `add -n Betsy Crowe -t friend -a Newgate Prison -p 1234567 -d 2023-01-01 -t criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [-n NAME] [-p PHONE] [-a ADDRESS] [-d APPOINTMENT_DATE] [-t TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.

Examples:
*  `edit 1 -p 91234567` Edits the phone number  of the 1st person to be `91234567`.
*  `edit 2 -n Betsy Crower -t friend` Edits the name of the 2nd person to be `Betsy Crower` and edits tag to `friend`.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Partial search is permitted e.g. `Han` and `Hans` will both show the result `Hans Bo`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
* The keywords being searched will be highlighted in the search results.

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Before finalised deletion, the command will ask for a confirmation:
 "Are you sure you want to delete this person? <person details> Type y for yes and n for no".

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

SilverCare data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

SilverCare data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, SilverCare will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause SilverCare to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous SilverCare home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add -n NAME -p PHONE_NUMBER -a ADDRESS -g GENDER [-d APPOINTMENT_DATE] [-t TAG]…​` <br> e.g., `add -n James Ho -p 22224444 -a 123, Clementi Rd, 1234665 -g male -d 2023-10-10 -t condition 1 -t condition 2`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [-n NAME] [-p PHONE_NUMBER] [-a ADDRESS] [-d APPOINTMENT_DATE] [-t TAG]…​`<br> e.g.,`edit 2 -n James Lee -d 2024-01-02`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Remark** | `remark INDEX r/remark`
**List** | `list`
**Help** | `help`
