# Game Review Website (Demonstration)
Hundreds of video games get released every year, and there’s not enough time to play them all. This site is here to help you to know which games are worth your time.

# Features
  - Game reviewers can login, view, submit, edit and delete their reviews  
  - Readers can login and comment on reviews
  - Guests are able to see reviews but can't comment or edit reviews
  - Member registration
### Language
  - Java
### Tech

* [Play](https://www.playframework.com/) - Web Framework For Java and Scala
* [Guice](https://github.com/google/guice) - A lightweight dependency injection framework for Java
* [H2](http://www.h2database.com/html/main.html) - In-memory databases
* [ Bootstrap](https://getbootstrap.com/2.3.2/index.html) - Great UI boilerplate for modern web apps
* [jquery](https://jquery.com/) - A fast, small, and feature-rich JavaScript library
* [JBCEncryption](https://github.com/jeremyh/jBCrypt) - An implementation the OpenBSD Blowfish password hashing algorithm
* [JUnit](https://junit.org/junit5/) - A unit testing framework for the Java

### Build and Run Application
JDK 8, Scala 2.12.X, SBT Plugin 1.2.8 are required to run application.
Install the dependencies, then build and run application through your terminal

```sh
$ git clone https://github.com/rickyhai11/GameBlog.git
$ cd GameBlog
$ sbt run
```
To run all tests, run test:
```
$ sbt test
```
### Docker
By default, the Docker will expose port 9000, so change this within the Dockerfile if necessary. When ready, simply use the Dockerfile to build the image.

```sh
$ cd GameBlog
$ docker build -t rickyhai11/game-reviews -f Dockerfile .
```
This will create the web-app image and pull in the necessary dependencies. Once done, run the Docker image and map the port to whatever you wish on your host. In this example, we simply map port 9000 of the host to port 9000 of the Docker (or whatever port was exposed in the Dockerfile):

```sh
$ docker run -i -p 9000:9000 rickyhai11/game-reviews
```
Verify the deployment by navigating to your server address in your preferred browser.

```sh
[YOUR.DOCKER.HOST.IP]:9000
```
