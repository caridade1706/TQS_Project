Feature: Staff Interface CliniConnect
    
    Background:
        Given I am on the CliniConnect Staff homepage on "http://deti-tqs-09.ua.pt:3000/"

    Scenario: Create a new Account
        When I click on the Sign Up button
        And I fill in the form with the following information:
            | Name  | Roberto Castro    |
            | DOB   | 2002-11-10    |
            | Email | robertorcastro@ua.pt  |
            | Password  | 123456    |
            | Phone | 916162223 |
            | Address   | Caminho da Alegria 5  |
            | City  | Monção    |
            | Departament   | Administrive    |
            | Task  | Receptionist  |
        And I click on the Register Button
        Then I should get an alert saying "Registration successful" and accpet it

    Scenario: Login with the new Account
        Given I am on the CliniConnect Staff homepage on "http://deti-tqs-09.ua.pt:3000/" and I am not logged in
        When I click on the HomePgae Login button
        And I fill in the form with the following login information:
            | Email | robertorcastro@ua.pt  |
            | Password  | 123456    |
        And I click on the Login Button
        Then I should be redirected to the Staff homepage