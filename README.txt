Title: Appointment Manager
Purpose: The purpose of Appointment Manager is to allow users to manage appointments for their users.
Managing appointments includes: Viewing, editing, and deleting appointments. Generating reports based on appointment information.
Receiving alerts for appointments occurring soon.

Author: Kyle Guillory
Contact Information: kguil39@wgu.edu
Student Application Version:
Date: 1/7/2023 - 1/16/2023

IDE: IntelliJ IDEA 2021.1.3 (Community Edition)
JDK: Java SE 17.0.1
JavaFX: JavaFX-SDK-17.0.1
MySQL Connection Driver: mysql-connector-java-8.0.25

Directions to Run Application:
- Place executable in a folder named AppointmentApplication
- Run the application
- Login using your username and password.

    If you do not have a username yet, use this information:

    Username: test
    Password: test

- Upon logging in you will be displayed a menu of buttons and a table for upcoming appointments.
    If you have any appointments that occurs in 15 minutes or sooner from your login time, the appointments will be
    partially displayed so you can act upon it. The table will be empty otherwise.

- You are now able to explore the application:

- View Customers by selecting Customer Records.
    From the Customer Record page you can:

        - Create a new customer
            Click the "New Customer" Button and enter information into the prompt.
            You'll need to a choose a country before you can select a division.

        - Update a customer
            Select a customer from the table of customers so that they are highlighted.
            Click "Edit Customer" to edit the fields.
            To save changes, click the "save changes" button.
            To cancel changes, click the "cancel changes" button.

        - Delete a customer.
            Select a customer from the table of customers so that they are highlighted.
            Click "Delete Customer".

            WARNING: Deleting a customer also deletes all appointments that the customer has.

- View Appointments by selecting Customer Appointments.
    From the Customer Appointment page you can:
        - View appointments.
            You can choose between three views by selecting the radio buttons: All Appointments, Next Month of Appointments,
            and Next Week of Appointments.

        - Create a new appointment.
            Click the "New Appointment" Button and enter information into the prompt.
            You'll need to a choose a customer by id before you can select a date.
            You'll need to select a date before you can select a start time.
            Finally, you'll need to select a start time before an end time.
            This allows appointments to be created with no conflicts in the customer's schedule.

        - Update an appointment.
            Select an  appointment from the table of appointments so that it is highlighted.
            Click "Edit Appointment" to edit the fields below the table.
            To save changes, click the "save changes" button.
            To cancel changes, click the "cancel changes" button.

            Note: Adjusting Customer, Start Date, or Start time will reset other data.
                    This ensures no schedule conflict is created.

- View reports by selecting Reports:
    This brings you to the report menu.

    You'll find the login_activity.txt path to direct you where to look locally in the application folder.

    From the report menu you can view three different reports to include:
    - Appointment Type report
        This contains information on the frequency of the types of appointments by month.

    - Contact Schedule report
        Select a contact from the drop down combo box in order to view all appointments they have.

    - Customers Acquired report
        This contains information of how many new customers were added to the database in the last year
        (year from today).




Additional Report Description:
    The Customers Acquired report provides a metric of how many new customers were added to the database.
    This is a crucial metric that could impact a lot of decisions for businesses.
    Some impact this report could have:
        - Used as a form of moral boost if the company is seeing lots of growth.
        - Used to justify increasing the budget to hire more employees.
        - Viewed yearly to see the trend of customers.
        - Metric to gauge marketing efforts.
        - Comparison data if recorded yearly (or more frequently).


