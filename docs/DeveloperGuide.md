---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

We would like to humbly acknowledge the following for the success of our project:
* The SE-EDU team for creating and maintaining the AddressBook-Level3 project.
* Our course instructors and teaching assistants whose patient guidance and feedback were instrumental throughout the development journey.
* Our peers and collaborators for their support, constructive code reviews, and insightful discussions. 
* The open-source Java and JavaFX communities for providing comprehensive documentation and development tools.
* The developers and maintainers of essential libraries and frameworks used in this project, including Jackson for JSON processing and JUnit for testing.

### **Java Dependencies**

* **JavaFX** - for Graphical User Interface (GUI) rendering 
* **Jackson** - for JSON serialization and deserialization
* **JUnit 5** - for JUnit testing
* **JaCoCo** - for generating test coverage reports 
* **Gradle Shadow Plugin** - for creating executable JAR files with dependencies
* **Checkstyle** - for enforcing coding standards 

### **Documentation Tools**

* **Jeckyll** - for authoring and building the project website
* **PlantUML** - for creating UML diagrams used in the Development Guide

### **Badges**

* **CodeCov** - for providing code coverage badge
* **GitHub Actions** - for providing the Java CI badge
--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking execution of delete command API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `LogicManager` is called upon to execute a command (e.g. `delete 1`), it passes the input to the `AddressBookParser`.

2. The `AddressBookParser` identifies the command and, if necessary, creates a specific parser (e.g. `DeleteCommandParser`) to parse the input.

3. This results in a `Command` object (e.g. a `DeleteCommand`) which is returned and then executed by `LogicManager`.

4. The command interacts with the `Model` during execution — for example, it may call `setPendingDeletion(person)` to store the target person while waiting for user confirmation.

5. The result of this execution is wrapped in a `CommandResult` object, which is returned from `Logic`.

---

6. When the user types `y` to confirm, the input is passed again to `LogicManager`, which treats it as a **new command**.

7. The `AddressBookParser` parses this as a confirmation and directly returns a `ConfirmCommand`.
   > 📌 *Note:* `AddressBookParser` does **not** create a specific parser for confirm commands, as they are simple and handled directly.

8. `LogicManager` executes the `ConfirmCommand`, which:
    - Retrieves the person marked for deletion via `getPendingDeletion()`
    - Performs the deletion using `deletePerson(person)`

9. A new `CommandResult` is returned, indicating that the deletion was successful.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2425S2-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add New Patient

The `add` feature allows users to register a new **elderly patient** into SilverCare’s address book, ensuring essential information such as name, phone number, address, gender, appointment date, medicines, and medical notes are captured.  
This feature supports home-visit nurses and healthcare workers in efficiently managing patient records.

#### Overview

* **Command format:**  
  `add -n NAME -p PHONE -a ADDRESS -g GENDER [-d APPOINTMENT_DATE] [-c CONDITION]... [-det DETAIL]... [-med MEDICINE]`

* **Required fields:**  
  `-n` (Name), `-p` (Phone), `-a` (Address), `-g` (Gender)

* **Optional fields:**  
  `-d` (Appointment Date), `-c` (Condition Tags), `-det` (Detail Tags), `-med` (Medicine)

* **Behavior:**
    * Adds a new patient entry into SilverCare’s records if the command is valid.
    * Adding beyond the maximum patient limit (30 patients) is disallowed.
    * Duplicate checking is performed based on custom logic (detailed below).


#### Duplicate Checking Logic

SilverCare uses a **custom duplicate definition** suited for elderly care contexts:

* Two persons are treated as **duplicates** **only if both**:
    * Their **names are identical**, and
    * Their **phone numbers are identical**.

* Otherwise, they are treated as **different individuals**, even if:
    * They share the same phone number but have different names (e.g. shared phones).
    * They share the same name but have different phone numbers.

This design acknowledges that in settings like old age homes or multi-residence households, different patients may share contact numbers or addresses.


#### Key Classes & Logic

* `AddressBookParser`
    * Recognizes the `add` command.
    * Passes arguments to `AddCommandParser`.

* `AddCommandParser`
    * Tokenizes and parses input arguments based on defined prefixes.
    * Validates presence of mandatory fields.
    * Parses fields via `ParserUtil`, applying constraints (e.g., name length, phone format).
    * Constructs a `Person` object.
    * Returns an `AddCommand` initialized with the new `Person`.

* `AddCommand`
    * Executes the addition of the new `Person` into the model's address book.
    * Enforces duplicate checking and the patient limit constraint.

* `UniquePersonList`
    * Adds the `Person` if no duplicate is found and capacity permits.
    * Automatically sorts the list by upcoming appointment dates.

* `Person`
    * Represents an elderly patient record, holding immutable fields such as name, phone, address, gender, appointment date, medicines, conditions, and details.


#### Design Considerations

* **Name Length Restriction:**
    * Names must be between **1 to 50 characters**.
    * This ensures proper UI display without breaking layout across different window sizes.

* **Phone Number Validation:**
    * Must consist of only **numeric digits (0-9)**.
    * Must be between **3 to 15 digits**.
    * No `+`, `-`, spaces, or special characters are permitted.

  *Rationale:*
    * Ensures simplicity in validation and consistency in stored data.
    * Avoids ambiguity across international formats if country codes are added separately in the future.

* **Patient-Focused Data Entry:**
    * SilverCare is designed to manage elderly patients, not caregivers or next-of-kin.
    * If the contact number provided belongs to a caregiver or family member instead of the patient, it is recommended to clearly mention this information using the `-det` (detail tag) field.
    * This helps users maintain clarity when viewing patient records, but it is **not strictly enforced** by the system.

* **Fault Tolerance:**
    * If mandatory fields are missing, field formats are invalid (e.g., wrong phone number, invalid date), or constraints are violated (e.g., exceeding name length), the `add` command fails safely.
    * Clear error messages guide the user to correct the input without crashing the system.

* **Extensibility:**
    * Adding more optional fields (e.g., emergency contacts) can be done by extending `Person`, `AddCommandParser`, and `AddCommand` accordingly.

* **Consistency:**
    * All new patients are validated, parsed, and added through a consistent, centralized flow, ensuring system-wide data integrity.


### Delete Patient or Clear List

The `delete` and `clear` commands allow users to remove individual patients or clear the entire address book.  
Both operations require confirmation (`y` to proceed, `n` to abort) before the action is finalized.  
This two-step process helps prevent accidental data loss.

#### Overview

* **Delete command format:** `delete INDEX` – Initiates a request to delete the patient at the specified index from the displayed list.
* **Clear command format:** `clear` – Initiates a request to clear all patient records from the address book.
* **Confirmation format:** `y` – Confirms and proceeds with the pending delete or clear operation.
* **Abort format:** `n` – Cancels the pending delete or clear operation.

Important behavior notes:
* The deletion or clearing does **not** happen immediately upon `delete` or `clear`.
* The system enters a **pending** state, waiting for explicit user confirmation.
* If the user types a recognized new command (other than `y` or `n`), the pending operation is **abandoned** automatically.
* If the user types an unrecognized input while a pending operation exists, the system **prompts the user again** for a clear yes (`y`) or no (`n`).

---

#### Key Classes & Logic

1. `DeleteCommand`
    * Parses the provided index.
    * Validates that the index is within bounds and refers to an existing patient.
    * Sets the `pendingDeletion` field in `ModelManager`.
    * Returns a `CommandResult` prompting the user to confirm or abort.

2. `ClearCommand`
    * Checks if there are any patients to clear.
    * Sets the `pendingClear` flag in `ModelManager`.
    * Returns a `CommandResult` prompting the user to confirm or abort.

3. `ConfirmCommand`
    * If a pending deletion exists, deletes the targeted `Person`.
    * If a pending clear exists, clears the entire address book.
    * If both deletion and clear are pending simultaneously (should not normally happen), throws a `CommandException`.

4. `AbortCommand`
    * If a pending deletion exists, cancels the pending delete operation.
    * If a pending clear exists, cancels the pending clear operation.
    * If both are pending simultaneously, throws a `CommandException`.

5. `ModelManager`
    * `pendingDeletion`: holds the `Person` object marked for deletion (or `null` if none).
    * `pendingClear`: boolean flag indicating if a clear operation is pending.
    * Provides methods to set, check, and clear these pending states.

6. `LogicManager`
    * `checkPendingConfirmation(String commandText)` method checks for pending confirmations.
    * If user input is unrelated to confirmation:
        * If input is a **recognized command**, pending operations are **cleared automatically**.
        * If input is **unrecognized**, the system **throws a `ParseException`** prompting the user to confirm properly.

---

#### Design Considerations

* **Fault Tolerance:**  
  Deleting or clearing does not happen immediately, ensuring users cannot accidentally delete important data with a single misclick.

* **User Flexibility:**  
  If users change their mind midway and type another valid command, the app cancels the pending operation and executes the new command normally.

* **Consistent Confirmation Flow:**  
  Whether deleting one patient or clearing the whole address book, users confirm with a simple `y` (yes) or `n` (no).

* **Extensibility:**  
  Future operations that require user confirmation can reuse the same pending confirmation mechanism (e.g., batch deletions, bulk updates).


### **Find patients by name**

The `find` command allows users to search and filter patients by their name. This is particularly useful for home-visit nurses who need to locate patient records quickly during appointments.

#### **Overview**

* **Find command format:** `find -n KEYWORDS` 
* `KEYWORDS` can be a full name or a partial name. 
* Multiple keywords can be provided and are space-separated.


* **Example usage:**
  ```
  find -n John
  find -n Alice Bob
  ```

#### **Behavior**

* Matches patients whose names contain **all** of the specified keywords.
* Matching is **case-insensitive** and allows for **partial matches**.
* **All provided keywords must be found in the same patient's name** for a match to occur.
    - Example: If the user searches `find -n Alice Bob`:
        - "Alice Tan" matches "Alice" but not "Bob".
        - "Bob Lee" matches "Bob" but not "Alice".
        - **Result:** No patient will be displayed because neither patient matches both keywords simultaneously.
* If no patients match the keywords, an appropriate message is displayed to the user.

#### Highlighting Matches

* After search results are displayed, the **matching portions** of the names are **highlighted**.
* Highlighting is case-insensitive and works even for partial matches.
    * Example: Searching for `find -n ann` will highlight "Ann" in "Annabelle" and "Johann".

This improves usability by helping users quickly spot the relevant matches in the results list.


#### **Key Classes & Logic**

1. `FindCommand`
    * Accepts a `Predicate<Person>` when constructed.
    * During execution, calls `model.updateFilteredPersonList(predicate)`.
    * Returns a `CommandResult` indicating the number of patients found, or a message if none were found.

2. `NameContainsKeywordsPredicate`
    * Implements `Predicate<Person>`.
    * Takes a list of keywords.
    * Returns `true` if the `Person`'s name contains any of the keywords, ignoring case.

3. `ModelManager`
    * Handles updating the filtered person list via `updateFilteredPersonList(predicate)`.
    * The updated list is then shown in the UI automatically.

4. `PersonCard`
    * Handles **highlighting** of matching name parts in the UI after a successful search.

#### **Design Considerations**

* **Case-Insensitive Search:**  
  Improves usability by ignoring capitalization differences between user input and stored names.

* **Partial Matching:**  
  Allows flexible searches — e.g., entering "Ann" matches "Annabelle" and "Johann".  
  This enables nurses to find patients even if they cannot remember the full name or exact spelling, improving efficiency during home visits.

* **Independent Patient Matching:**  
  Keywords are checked independently against each patient’s name. There is no cross-patient aggregation of keywords.  
  This ensures that search results accurately reflect a single patient's record matching all criteria, avoiding confusion where unrelated patients might otherwise satisfy different parts of the search unintentionally.

* **Fault Tolerance:**  
  No crashes occur if there are no matching results. An informative message is displayed.

* **Extensibility:**  
  The `Predicate<Person>` architecture makes it easy to extend to other fields (e.g., conditions, addresses) without changing the core logic of `FindCommand`.

#### Example

| User Input | Behavior                                                                                              |
|------------|-------------------------------------------------------------------------------------------------------|
| `find -n John` | Displays all patients whose names contain the word "John" (case-insensitive).                         |
| `find -n joHN` | Displays the same results as `find -n John` — the search is case-insensitive.                         |
| `find -n` | Error — name keyword cannot be empty.                                                                 |
| `find -n John Doe` | Displays all patients whose names contain both "John" and "Doe" in that order. |
| `find -n Annette John` | Displays patients whose names contain both "Annette" and "John".                                      |


### Find Patients by Appointment Date

The `find` feature allows users to search for patients who have a specific appointment date scheduled.  
This functionality is helpful when nurses want to quickly check who they are scheduled to visit on a particular day.

#### Overview

* **Command format:** `find -d APPOINTMENT_DATE`
* Filters patients whose appointment date exactly matches the provided date input.
* Supports both date-only format (e.g., `yyyy-MM-dd`) and full datetime format (e.g., `yyyy-MM-dd HH:mm`).

#### Behavior

* Users must provide a valid date format. Otherwise, parsing fails and an error is shown.
* Matching is **exact** — the appointment must match the date input provided.
    - If the input includes only the date (e.g., `2025-04-10`), only appointments on that date (ignoring time) are matched.
    - If the input includes date and time (e.g., `2025-04-10 14:30`), the match is stricter, including both date and time.
* Partial matches (e.g., just matching the year or month) are not supported.


#### Key Classes & Logic

1. `FindCommandParser`
    * Detects the `-d` prefix in user input.
    * Parses the appointment date string provided.
    * Creates a `FindCommand` with an `AppointmentDatePredicate`.
    * Throws a `ParseException` if the date format is invalid.

2. `AppointmentDatePredicate`
    * Implements `Predicate<Person>`.
    * Checks if a patient's appointment date matches the parsed input date.
    * Supports parsing both:
        * **Date-only** (`yyyy-MM-dd`) — matches any appointment with that date.
        * **Datetime** (`yyyy-MM-dd HH:mm`) — matches the exact appointment datetime.

3. `FindCommand`
    * Accepts the `AppointmentDatePredicate` during construction.
    * During execution, calls `model.updateFilteredPersonList(predicate)`, updating the view to show only matching patients.

4. `AppointmentDate`
    * Represents a patient's appointment date field.
    * Ensures that date strings stored in the system are valid and consistent.

5. `PersonCard`
    * Handles **highlighting** of matching appointment dates in the UI after a successful search.


#### Design Considerations

* **Strict Date Parsing:**  
  The command enforces strict formatting to prevent confusion caused by ambiguous or partial dates.

* **Two-level Matching Flexibility:**
    - If users input only the date (no time), matches are based on the date alone.
    - If users input full date and time, matches are based on both.

  This balances ease of use (for day-based searches) with precision (for exact-time searches).

* **Fault Tolerance:**  
  When invalid date formats are entered, the system throws a `ParseException` with a clear message, guiding users to correct their input without crashing the app.

* **Extensibility:**  
  The `Predicate<Person>` structure means the logic for date matching is fully encapsulated. Future enhancements (e.g., range searches, week-based searches) can be added by introducing new predicates without modifying core classes like `FindCommand`.

* **Highlighting Matches:**  
  Matching appointment dates are **highlighted** in the UI after search results are shown.  
  This improves visibility and quickly draws attention to relevant fields.


#### Example

| User Input | Behavior |
|------------|----------|
| `find -d 2025-04-10` | Displays all patients with appointments on 10 April 2025, regardless of time. |
| `find -d 2025-04-10 14:30` | Displays only patients with an appointment exactly on 10 April 2025 at 2:30 PM. |
| `find -d 2025-4-10` | Error — invalid format. Requires leading zeros (e.g., `2025-04-10`). |


### Find upcoming appointments

The `upcoming` feature under the `find` command allows users to filter and display only those persons who have future appointments scheduled. This functionality is useful for quickly identifying clients with pending appointments.

#### Overview

* Command format: `find upcoming`
* Filters out persons whose appointment dates are **before or equal** to the current date and time.
* Supports both date-only and full datetime formats (e.g., `2025-04-01` or `2025-04-01 14:30`).

#### Key Classes & Logic

1. `FindCommandParser`
    * Detects if the user input is exactly `"upcoming"` (case-insensitive).
    * If matched, it returns a `FindCommand` initialized with an `UpcomingAppointmentPredicate`.
    * This diverges from the usual `-n` (name) or `-d` (appointment date) prefixes.
2. `UpcomingAppointmentPredicate`
    * Implements `Predicate<Person>`.
    * Extracts the `appointmentDate` from each `Person`.
    * Attempts to parse the string as either:
      * a full datetime using format `yyyy-MM-dd HH:mm`, or
      * a date-only format `yyyy-MM-dd` (defaults to `00:00` for comparison).
    * Compares the parsed result to `LocalDateTime.now()` and returns `true` only if the appointment is strictly after the current moment.
3. `AppointmentDate`
   * Normalizes and validates user-inputted date strings.
   * Accepts and validates both formats using regex and Java's `DateTimeFormatter`.
   * Helps maintain consistent formatting throughout the system.
4. `FindCommand`
   * When executed, it passes the predicate to the model's `updateFilteredPersonList()`, causing the filtered list to update with only persons matching the predicate.

The following class diagram shows the relationship between key classes involved:

![UpcomingAppointmentClassDiagram](images/UpcomingAppointmentClassDiagram.png)

#### Design Considerations

* **Fault Tolerance:**  
  Parsing errors during predicate evaluation (such as invalid appointment dates) result in a safe `false` return.  
  This ensures the app remains stable and does not crash even if invalid data is encountered during a search.

* **Extensibility:**  
  The predicate-based design allows easy addition of new search filters (e.g., finding past appointments, filtering by appointment week) without modifying existing logic.  
  Developers can simply introduce new `Predicate<Person>` classes to extend functionality.

* **Single Responsibility Principle (SRP):**  
  Each class involved in the find-upcoming feature has a clear and separate responsibility:
    - `FindCommandParser` handles parsing the user’s input.
    - `UpcomingAppointmentPredicate` handles checking appointment timing logic.
    - `AppointmentDate` handles formatting and validation of date strings.  
      This separation improves maintainability and readability of the codebase.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* nurses who do home visits for elderly patients
* has a need to manage a significant number of contacts
* is reasonably comfortable using CLI apps

**Value proposition**: The app will help nurses caring for elderly patients manage contact details of their home-visit patients within one platform.


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                     | I want to …​                                                    | So that I can…​                                                    |
|----------|---------------------------------------------|-----------------------------------------------------------------|--------------------------------------------------------------------|
| `* * *`  | user                                        | see usage instructions                                          | refer to instructions when I forget how to use the app             |
| `* * *`  | new user                                    | have a quick-start guide                                        | learn how to use the app with minimal confusion from the beginning |
| `* * *`  | user                                        | view a help window while using the app                          | access reminders of the commands                                   |
| `* * *`  | home-visit nurse                            | add a new patient’s contact details                             | keep track of their information and reach out when needed          |
| `* * *`  | home-vist nurse                             | add a new patient’s full information, including medical details | keep track of the patients I visit                                 |
| `* * *`  | home-visit nurse managing multiple patients | update an existing patient's information                        | always have accurate and up-to-date records                        |
| `* * *`  | home-visit nurse                            | add appointment dates for patients                              | know when each patient is scheduled for a visit                    |
| `* * *`  | home-visit nurse                            | add custom notes or details about a patient                     | remember important context like accessibility needs                |
| `* * *`  | home-visit nurse                            | store multiple conditions for a patient                         | keep track of their medical history                                |
| `* * *`  | home-visit nurse                            | add a list of prescribed medications                            | log the patient's current treatment                                |
| `* * *`  | user                                        | delete a patient                                                | clean up records I no longer need                                  |
| `* * *`  | user                                        | see a confirmation message before deleting a patient record     | avoid accidentally removing important data                         |
| `* * *`  | home-visit nurse during visits              | search for a patient by name                                    | find important information quickly during a visit                  |
| `* * *`  | user                                        | search for a patients using partial names                       | find a patient even if I don’t remember their full name            |
| `* * *`  | home-visit nurse                            | search for patients by appointment date                         | check who I need to visit on a specific day                        |
| `* * *`  | home-visit nurse                            | view all upcoming appointments                                  | plan and prepare for upcoming visits                               |
| `* * *`  | user                                        | have search results highlight matching text                     | quickly identify relevant records                                  |
| `* * *`  | user                                        | list all patients                                               | get a full overview of who is currently in the system              |
| `* * *`  | user                                        | clear all my patient records                                    | reset and start with a clean slate                                 |
| `* * *`  | user                                        | see a confirmation message before clearing all patient records  | avoid accidentally removing important data                         |
| `* *`    | home-visit nurse with many patients         | sort my patient list by name                                    | locate a person easily                                             |
| `* *`    | user                                        | search for patients by phone number                             | retrieve contact details even if I only have a number available    |
| `* *`    | user                                        | see a warning when I exceed the patient limit                   | know that the app has reached capacity                             |
| `* *`    | user                                        | get error messages that include example usage                   | quickly learn how to correct errors I made                         |
| `* *`    | security-conscious user                     | lock or hide sensitive patient contact details                  | prevent unauthorized individuals from accessing them               |
| `* *`    | user                                        | toggle between light and dark mode                              | choose a display that's comfortable for my eyes                    |
| `*`      | busy nurse                                  | categorize patients based on their conditions or priority level | focus on urgent cases first                                        |
| `*`      | home-visit nurse with many patients         | filter my patient list by location                              | optimize my travel schedule for house visits                       |


### Use cases

(For all use cases below, the **System** is the `SilverCare` and the **Actor** is the `nurse`, unless specified otherwise)

**Use Case 1: Add a new patient**

**Goal: Add a new patient’s contact and medical information.**

**MSS:**
1. Nurse provides the required patient information: name, phone number, address, and gender.
2. Nurse may also provide optional information: appointment date, medical condition, medicine issued and additional notes.
3. System validates the data provided.
4. System checks whether a patient with same identifying details already exists.
5. System creates a new patient record and assigns it a unique ID.
6. System confirms addition and displays the new patient in the patient list.
7. Use case ends.

**Extensions:**
   * 2a. System detects invalid or incomplete entered data.
     * 2a1. System requests for the correct data.
     * 2a2. Nurse enters new data
     * Steps 2a1-2a2 are repeated until the data entered are correct.
     * Use case resumes from step 3.
   * 3a. System detects duplicate patient
     * 3a1. System informs Nurse that the patient already exists.
     * 3a2. System cancels the add request.
     * Use case ends.

**Use case 2: Delete an existing patient**

**Goal: Delete an existing patient’s contact and medical information.**

**MSS**

1. Nurse requests to delete an existing patient contact.
2. Nurse provides the patient's index in list.
3. System validates the provided patient index.
4. If valid, System prompts Nurse to confirm the deletion.
5. Nurse confirms the deletion.
6. System deletes the corresponding patient record.
7. System informs Nurse that the deletion is successful.
8. Use case ends.

**Extensions**

* 3a. System detects invalid patient index.
    * 3a1. System requests for the correct data.
    * 3a2. Nurse enters new data
    * Steps 3a1-3a2 are repeated until the data entered are correct.
    * Use case resumes from step 4.

* 3b. System does not find a patient with the provided index.
    * 3b1. System informs the Nurse that no matching patient exists.
    * 3b2. System requests a different patient index.
    * 3b3. Nurse provides a new patient index.
    * Steps 3b1–3b3 are repeated until a matching patient is found.
    * Use case resumes from step 4.

* 4a. Nurse cancels deletion.
    * Nurse declines the confirmation prompt.
    * System informs Nurse that the deletion has been cancelled.
    * Use case ends.

* 4b. Nurse types unrecognised, non-command input for confirmation of delete.
  * 4b1. System re-prompts Nurse to confirm deletion (expects yes or no).
  * 4b2. Steps 4 continues until a valid response is provided.
  * Use case resumes from 5 (if yes) or 4a (if no).

* 4c. Nurse requests for another command.
  * 4c1. System assumes the deletion has been abandoned.
  * 4c2. System cancels the deletion request without deleting any patient.
  * Use case ends.

**Use case 3: Find a patient by name**

**Goal: Search for a patient’s record by their name.**

**MSS**

1. Nurse requests to search for a patient by name.
2. Nurse provides a name to search for.
3. System validates the provided name (e.g., non-empty, valid characters).
4. System searches for patient records containing the name (case-insensitive).
5. System displays a list of matching patient records.
6. Use case ends.

**Extensions**

* 3a. Nurse provides empty name query.
    * 3a1. System informs the Nurse that the name cannot be empty.
    * 3a2. System requests a valid name.
    * 3a3. Nurse provides a new search name.
    * Steps 3a1–3a3 are repeated until a valid name is entered.
    * Use case resumes from step 4.

* 4a. No matching patient records found.
    * 4a1. System informs the Nurse that no matching records were found.
    * 4a2. System requests a new search input.
    * 4a3. Nurse provides a new search name.
    * Steps 4a1–4a3 are repeated until a match is found or the search is cancelled.
    * Use case resumes from step 4 or ends if cancelled.

**Use case 4: Find a patient by appointment date**

**Goal: Search for a patient’s record by appointment date.**

**MSS**

1. Nurse requests to search for a patient by appointment date.
2. Nurse provides a date to search for.
3. System validates the provided date format.
4. System searches for patient records with matching appointment dates.
5. System displays a list of matching patient records.
6. Use case ends.

**Extensions**

* 3a. Nurse provides wrongly formatted date query.
    * 3a1. System informs the Nurse that the date format is wrong.
    * 3a2. System requests a correctly formatted date input.
    * 3a3. Nurse provides a new date.
    * Steps 3a1–3a3 are repeated until a valid date is entered.
    * Use case resumes from step 4.

* 3b. Nurse provides invalid date query.
    * 3b1. System informs the Nurse that the date entered does not exist.
    * 3b2. System requests a valid date input.
    * 3b3. Nurse provides a new date.
    * Steps 3b1–3b3 are repeated until a valid date is entered.
    * Use case resumes from step 4.

* 4a. No matching patient records found.
    * 4a1. System informs the Nurse that no matching records were found.
    * Use case ends.

**Use case 5: Find upcoming appointments**

**Goal: Retrieve all patient records with appointment dates that are scheduled after the current system time.**

**MSS**

1. Nurse requests to view upcoming appointments.
2. System retrieves the current date and time.
3. System searches for all patients whose appointment dates are scheduled after the current system time.
4. System displays a list of matching patient records.
5. Use case ends.

**Extensions**

* 3a. No upcoming appointments found.
    * 3a1. System informs Nurse that there are no upcoming appointments.
    * Use case ends.

**Use case 6: Edit a patient record**

**Goal: Modify an existing patient's contact or medical information.**

**MSS**

1. Nurse requests to edit a patient record
2. Nurse provides the patient index and specifies one or more fields to update (e.g., name, phone, address, gender, etc.).
3. System validates the patient index and each field to be updated.
4. System updates the patient record with the new information.
5. System confirms the successful update.
6. Use case ends.

**Extensions**

* 3a. Invalid patient index provided
    * 3a1. System informs Nurse that the command is invalid or patient index is out of range.
    * 3a2. System requests a valid patient index.
    * 3a3. Nurse provides a new index.
    * Steps 3a1–3a3 repeat until a valid patient index is provided.
    * Use case resumes from step 3.

* 3b. Invalid input for one or more fields
    * 3b1. System informs the Nurse that the command is invalid.
    * 3b2. System requests corrected values for the invalid fields.
    * 3b3. Nurse provides corrected input.
    * Steps 3b1–3b3 repeat until all inputs are valid.
    * Use case resumes from step 4.

* 3c. No fields updated
    * 3c1. System informs Nurse that at least one field has to be edited.
    * 3a2. System requests at least one field to be updated.
    * 3a3. Nurse provides a new valid input.
    * Steps 3a1–3a3 repeat until a valid input is provided.
    * Use case resumes from step 3.

**Use case 7: Clear all patient records**

**Goal: Remove all existing patient records from the system.**

**MSS**

1. Nurse requests to clear all patient records.
2. System prompts Nurse for confirmation before proceeding.
3. Nurse confirms the action.
4. System deletes all patient records from the system.
5. System informs Nurse that the address book has been cleared.
6. Use case ends.

**Extensions**

* 3a. Nurse cancels the clear request
    * 3a1. Nurse declines the confirmation prompt.
    * 3a2. System informs Nurse that the clear action has been cancelled.
    * Use case ends.

* 4a. No records to clear
    * 4a1. System detects that there are no patient records to delete.
    * 4a2. System informs the Nurse that the address book is already empty.
    * Use case ends.

**Use case 8: List all patients**

**Goal: View all active patient records.**

**MSS**

1. Nurse requests to view all patient records.
2. System retrieves all stored patient records.
3. System displays the list of patients.
4. Use case ends.

**Extensions**

* 2a. No patients found in the system.
    * 2a1. System informs the Nurse that there are no patient records available.
    * Use case ends.

**Use case 9: Toggle application theme**

**Goal: Switch the application’s appearance between light and dark mode.**

**MSS**

1. Nurse toggles the application theme.
2. System determines the current theme (light or dark).
3. System switches to the opposite theme.
4. System applies the new theme across the user interface.
5. Use case ends.

**Use case 10: Display help information**

**Goal: Provide guidance to Nurse on how to use the system’s commands and features.**

**MSS**

1. Nurse requests to view help information.
2. System opens a help window.
3. System displays example commands and usage instructions.
4. System provides a hyperlink to view the full user guide.
5. Use case ends.

**Use case 11: Exit the application**

**Goal: Safely terminate the SilverCare application.**

**MSS**

1. Nurse requests to exit the application.
2. System saves any unsaved data.
3. System terminates the application.
4. Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2. The system should efficiently handle at least 30 patient records, with all operations (e.g., search, add, delete) completing within 1 second on typical hardware.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The system should be able to search patient records within 2 seconds.
5. The interface should be accessible and navigable by nurses with basic training within 1 hour.
   The app should allow viewing patient details even without an internet connection.
6. The system’s user interface (UI) and documentation must be clear, comprehensive, and understandable for nurses and healthcare professionals without advanced IT skills.
7. The app is not required to connect to the hospital’s database.
8. The application must function fully offline, with all patient details accessible without an internet connection.
9. The app is not required to handle phone calls to the contact numbers of entries.
10. The application should gracefully handle invalid inputs and unexpected errors, providing clear error messages without crashing.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **User**: A home-visit nurse who interacts with the SilverCare system to manage patient information.
* **New User**: A first-time user of the system who may require guidance on how to use the application.
* **Home-Visit Nurse**: A nurse who provides medical care to elderly patients in their homes, rather than at a hospital or clinic.
* **Patient Management**: The ability to store, update, and retrieve patient details such as name, contact information, and medical history.
* **Contact Details**: A patient’s phone number, address, and any other means of communication stored within the system.
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Patient Record**: A stored entry containing a patient's personal details, contact information, and relevant medical notes.
* **Unique Identifier (Patient Index)**: A system-generated number assigned to each patient record to ensure easy identification and management.


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. **Initial Launch**
    1. Download the JAR file and copy it into an empty folder.
    2. Open your **Command Prompt** (Windows) or **Terminal** (Mac/Linux) and navigate to the folder with the JAR file.
       ```sh
       cd path/to/file
       ```
    3. Run the following command:
       ```sh
       java -jar silvercare.jar
       ```
    4. **Expected:** The GUI appears with a set of sample patient records.



2. **Shutdown**
    1. Close the application using the exit command:
       ```sh
       exit
       ```
    2. **Expected:** The application closes successfully.

### Managing Patients


### Adding a Patient
- **Prerequisites:**
  - Adding a patient must be done with all compulsory fields (name, phone, address, gender).
  - Command format for each field should follow as stated in [User Guide](UserGuide.md#field-options)

    
- **Test case 1 (With compulosry fields):**
  ```sh
  add -n John Doe -p 91234567 -a 123 Clementi Ave 34 -g Male
  ```
  **Expected:** John Doe appears in the patient list with correct details.



- **Test case 2 (With optional fields):**
  ```sh
  add -n Johnny -p 92345678 -a 123 Clementi Ave 34 -g Male -d 2025-04-05 -c High BP -det lives alone -med Panadol
  ```
  **Expected:** Johnny appears in the patient list with correct details.



### Editing a Patient
- **Prerequisites:**
    - Index given must be positive (1,2,3...) and not out of range of list of patients.
    - Command format for each field should follow as stated in [User Guide](UserGuide.md#field-options)


- **Test case 1 (Edits name of patient at index 1):**
  ```sh
  list
  edit 1 -n Bobby
  ```
  **Expected:** The name of the first patient index updates correctly to Bobby.


- **Test case 2 (Edits name and phone number of patient at index 1):**
  ```sh
  list
  edit 1 -n Bobby -p 91283131
  ```
  **Expected:** The name and phone number of the first patient index updates correctly to Bobby and 91283131 respectively.


- **Test case 3 (Edits condition of patient at index 2 to the 3 new conditions stated):**
  ```sh
  list
  edit 2 -c Dementia -c Asthma -c Diabetic
  ```
  **Expected:** The condition of the second patient index updates correctly to the three stated as above.


- **Test case 4 (Invalid Command):**
  ```sh
  edit hi
  ```
  **Expected:** Error message pops up due to incorrect formatting.

### Deleting a Patient
- **Prerequisites:** 
  - Index given must be positive (1,2,3...) and not out of range of list of patients.


- **Test case 1 (Successfully deletes patient at index 1):**
  ```sh
  list
  delete 1
  y
  ```
  **Expected:** A confirmation prompt pops up and successfully deletes patient 1 after inputting [y].


- **Test case 2 (Abort deletion of patient at index 1):**
  ```sh
  list
  delete 1
  n
  ```
  **Expected:** A confirmation prompt pops up and aborts deleting patient 1 after inputting [n].


- **Test case 3 (Invalid command):**
  ```sh
  list
  delete HI
  ```
  **Expected:** Error message pops up due to incorrect formatting.


---

## Searching and Filtering
- **Prerequisites:**
    - Index given must be positive (1,2,3...) and not out of range of list of patients.
    - Command format should follow as stated in [User Guide](UserGuide.md#locating-persons-by-name-find)


- **Test case 1 (Find by name):**
  ```sh
  list 
  find -n John
  ```
  **Expected:** Only matching patients containing name "John" are shown.


- **Test case 2 (Find by date):**
  ```sh
  list
  find -d 2025-04-12
  ```
  **Expected:** Only matching patients with appointment date of "2025-04-12" are shown.


- **Test case 3 (Find upcoming appointments):**
  ```sh
  list
  find upcoming
  ```
  **Expected:** Only matching patients with appointment dates after current system timing are shown.

---

## Data Handling

### Saving Data
- **Expected:** Changes persist after restarting the application.

### Handling Missing/Corrupted Files
- **To simulate:** Delete or corrupt the data file before launching the app.
- **Expected:** The application should recover gracefully and provide an error message or reset data appropriately.

---

## Edge Cases

### Invalid Inputs:
- Enter an invalid phone number (`-p abcd1234`).
- Delete a non-existent patient.  
  **Expected:** Proper error messages.



Testers should verify that expected results match actual outcomes and report any inconsistencies as bugs. 

Ensure that all covered features work as intended while considering possible edge cases. 

For full command details, refer to the [User Guide](UserGuide.md).


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**
The development of **SilverCare** involved adapting and extending the AddressBook-Level3 codebase to better suit the needs of home-visit nurses.
The team focused on improving usability, expanding patient-related functionality, and ensuring the application remained robust and maintainable.

**Key development efforts included:**

* Enhancing core commands (`add`, `edit`, `find`, etc.) to support patient-specific data such as appointment dates, conditions, and medication

* Refactoring the model and storage structure to accommodate new fields and tag types

* Updating and writing unit tests to ensure system correctness after changes

* Streamlining the user interface and user guide to better support a non-technical audience

The team maintained regular weekly check-ins to coordinate tasks, discuss challenges, and review progress.
All members contributed equally across design, implementation, and testing phases, ensuring a balanced and collaborative workflow.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Planned Enhancements**

Team size: 5

1. **Disallow adding of past appointment dates**

   **Current Flaw:** The application allows users to add appointments in the past, which is unrealistic in most use cases.

   **Planned Enhancement:** We will reject commands that attempt to schedule appointments for dates earlier than the current date.

   **Sample Output:** `Appointment date must be today or later.`

2. **Warn users of overlapping appointment dates**

   **Current Flaw:** Users can accidentally schedule multiple appointments for the same time without any warning.

   **Planned Enhancement:** The system will display a warning if a new appointment overlaps with an existing one for the same patient.

   **Sample Output:** `Warning: Appointment overlaps with an existing one on 2025-07-20.`

3. **Allow adding recurring appointment dates**

   **Current Flaw:** Users must manually input each recurring appointment, which is inefficient.

   **Planned Enhancement:** Support a `-r weekly` or `-r monthly` flag to automatically add recurring appointments.

   **Sample Input:** `add -n John -d 2025-07-20 -r weekly -t 5` (creates 5 weekly recurring appointments)

4. **Make medicine field more detailed**

   **Current Flaw:** Only medicine names are captured, with no information on dosage or frequency.

   **Planned Enhancement:** Allow inputs like `"Paracetamol (500mg, twice a day)"` in the medicine field.

   **Sample Input:** `add ... -med Paracetamol (500mg, twice a day)`

5. **Add find-and-replace functionality for editing tags (Conditions/Details)**

   **Current Flaw:** Users must retype all tags when editing a single condition or detail.

   **Planned Enhancement:** Support syntax like `edit -c Migraine->Chronic Migraine` to update one tag.

   **Sample Input:** `edit 1 -c Sleep Apnea->Mild Sleep Apnea -det Fall Prone->Bedridden`

6. **Color-code urgent appointments in the UI**

   **Current Flaw:** All appointments look the same regardless of urgency.

   **Planned Enhancement:** Use colored backgrounds (e.g. red, orange, yellow) in appointment cards to indicate urgency.

   **Sample UI:**
    * Red background → urgent
    * Orange background → moderate urgency
    * Yellow background → low urgency

7. **Allow users to choose sorting method**

   **Current Flaw:** Appointments are sorted by date only, and users cannot change this.

   **Planned Enhancement:** Provide a command like `sort name`, `sort medicine`, or `sort date`.

   **Sample Input:** `sort name`

   **Sample Output:** Sorted contact list by name (A-Z)

8. **Change command input field from single line to text box**

   **Current Flaw:** Long input commands are hard to navigate and edit due to the limited width of the input field.

   **Planned Enhancement:** Replace the single-line input with a multi-line text box to improve visibility and ease of editing.

   **Sample UI:** A resizable input box that allows scrolling and full command visibility.

9. **Allow uploading of patient images**

   **Current Flaw:** Users rely solely on names to identify patients, which may lead to errors.

   **Planned Enhancement:** Allow users to attach an image file to each patient profile for clearer visual identification.

   **Sample Input:** `add -n John -img /path/to/john_photo.jpg`
10. **Improve input validation for name-based search**

    **Current Flaw:** The find -n command accepts any input, including numbers or symbols. When the input is invalid, the system still returns “No such patients in the list” instead of indicating that the search was not meaningful.

    **Planned Enhancement:** Detect and reject clearly invalid name inputs such as empty strings, purely non-alphabetic characters, or excessively short queries. Provide a helpful message to guide users to enter a valid name.

    **Sample Output:** Search term is invalid. Please enter a proper name.
