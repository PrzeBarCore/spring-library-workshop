# Spring library workshop

Spring library workshop is web application which is being created for educational purposes.
Current functionality allows users to store, read and manipulate basic data. 
Development of application is focused on a backend so just ignore the ugly frontend-side :).

### Deployed application available at: https://library-app-barcore.herokuapp.com/books 
Sometimes Heroku needs some time to run this app, so be patient.

## Technologies
* Java 11
* SQL
* HTML / CSS
* Spring Boot 2.3.4
* Hibernate
* Junit/ Mockito 
* Thymeleaf
* FLyway

## What's all this then?
### 1. Structure of the model
In the application, the highlight of the show are books. One book has a title, may include one or  more authors, 
is part of certain section and must have at least one edition, which has its publisher and date of publication. <br>
   Copies of the book are part of its edition
and represent an actual book in real life. There are three states of copy: available, borrowed or reserved. If the copy
   is borrowed or/and reserved, it's also contains dates until when it is reserved/borrowed.

### 2. Application contains 4 main views:
* Books <br>
  Main view contains all saved books. At the bottom of the displayed list, are page numbers and numbers 
  of book to be displayed at one page. At the top of the page is button which displays form for creating book.
  During the process od creation it's possible to assign book to existing authors and sections or to create new ones. 
  Publishers of editions are similar case. <br>
  Editing or deleting options are visible in a book display chose in main view. It is possible to add new copies 
  for an edition or even new edition for a book. Deletion is possible as well though when we're deleting book 
  and its content, edition and its content or copy itself, we must remember that it is not possible to delete 
  book copy which is not available.
  <br><br>
* Authors <br> 
  In main view it displays all authors. By clicking at the author it is possible to check which
  books have been written by him. It is also possible to change his name or last name if there were
  some mistakes during creating. Input of name and last name has basic validations.
  <br><br>
* Sections <br>
  Similar way of work like authors and publishers.
  <br><br>
* Publishers <br>
  Similar way of work like Sections and publishers.


## Setup on PC

### Using IDE:
1. Download zip. file from repository
2. Extract files
3. Open project in IDE
4. Compile and run project
5. Application is available at http://localhost:8080/ address

### Using command line and maven wrapper
1. Download zip. file from repository
2. Extract files
3. Change directory to spring-library-workshop-master

`$ cd example\path` 
<br>For example:

![](https://github.com/PrzeBarCore/images-library/blob/main/runningConsole1.PNG)

4. Compile project with maven

`$ .\mvnw package` 
<br>For example:

![](https://github.com/PrzeBarCore/images-library/blob/main/runningConsole2.PNG)


<br>You should get something like:

![](https://github.com/PrzeBarCore/images-library/blob/main/runningConsole3.PNG)

5. Run application

`$ java -jar target\spring-library-workshop-0.0.1-SNAPSHOT.jar` 
<br>For example:

![](https://github.com/PrzeBarCore/images-library/blob/main/runningConsole4.PNG)


<br>You should get something like:

![](https://github.com/PrzeBarCore/images-library/blob/main/runningConsole5.PNG)

6. Application is available at http://localhost:8080/ address



