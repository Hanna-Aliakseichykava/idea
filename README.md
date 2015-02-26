# Idea

This is a single page web application for submitting your best ideas on how we can improve Mogilev city. It’s also a place where you can discover and vote on other people’s ideas, as well as communicate with them.


###Technology stack on the server side
App uses a number of open source projects to work properly:
1. [Spring MVC REST service](http://spring.io/guides/gs/rest-service/) with [JSON payload](https://github.com/FasterXML/jackson). Uses spring-test for integration tests.
2. [Gradle](http://gradle.org/) configuration for building and testing
3. [Spring Security](http://projects.spring.io/spring-security/)
4. [Spring HATEOAS](http://projects.spring.io/spring-hateoas/)
4. We use [Hibernate](http://hibernate.org/orm/) because it is the most common JPA provider.
4. [Spring Data JPA](http://projects.spring.io/spring-data-jpa/) hides the used JPA provider behind its repository abstraction.
5. We use the [HikariCP](https://github.com/brettwooldridge/HikariCP) datasource because it is the fastest datasource on this planet.
6. We use the [H2 in-memory database](http://www.h2database.com/html/main.html) because it makes our application easier to run.

###Technology stack on the client side
Single Web page application:
* [AngularJS](https://angularjs.org/) - HTML enhanced for web apps!
* [Twitter Bootstrap](http://twitter.github.com/bootstrap/) - great UI boilerplate for modern web apps
* [jQuery](http://jquery.com) - duh

Easy installation of new JavaScript libraries with [Bower](http://bower.io/).
Build, optimization and live reload with [Grunt](http://gruntjs.com/). Testing with [Karma](http://karma-runner.github.io/0.12/index.html)

### Version
0.0.1

### Installation

You need Gradle installed:

```sh
$ git clone [git-repo-url] idea
$ cd idea
$ gradle jettyRun
```
