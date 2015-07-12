# clippercard_automation_selenium_java
Clippercard website test automation with Selenium WebDriver Java API and TestNG framework. It is showed that data to run the automation can be fetched from both Excell and SQL database. SQL tables are created on “dbInitialization.sql” file. Login test results are also saved to database in the test_result_login table.

LoginTest: This class is the final version of Login Tests. Data Access Objects in which input data is taken from database and test results are written to the database.

LoginTestV1 : Only one test is executed for both success and unsuccess cases. Data provider was not used.
LoginTestV2 : Success and unsuccess cases are separated into two different tests. Data provider is still not in use.
LoginTestV3 : @Beforemethod and @Aftermethod annotations are added. Data provider is still not in use.
LoginTestV4&V5: @Dataprovider annotation is added. Login users are taken from Memory.
LoginTestV6 : Login users are taken from resources/TestData.xls excell file.
LoginTestV7 : It is shown that Pageobjects can be found in a seperate class and used in another class. Until this version all used pased objects were founded in the same class.
LoginTestV8 : Screenshot is taken if there is a failure during the testing process.

