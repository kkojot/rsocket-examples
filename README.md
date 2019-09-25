# rsocket-examples
Examples of using RSocket with Java, JavaScript and Spring Webflux.

## Overview
Read more about this project http://kojotdev.com/2019/09/rsocket-examples-java-javascript-spring-webflux/

![rsocket-javascript-java-live-example](http://kojotdev.com/wp-content/uploads/2019/09/javascript-java-live.gif)

## Java
* Open `java` folder as Maven project
* Run `Server` class
* Run `Client` class
* See in the console how communication works
You can change the logging level to `Level.DEBUG` to see more information

## JavaScript
Open `javascript` and then:
* `npm install`
* `npm run serve`
With the running `Server` from `java` folder, you can test the message passing with the Browser.

## Spring WebFlux
Open `webflux/client` and `webflux/server` as two separete maven projects
* Run Server
* Run Client
* Send to `POST` request with the REST endpoint `http://localhost:8081/message` and body `{ "message": "message from REST endpoint" }`
* See the response and console logs to understand the message flow
You can also use cURL (Windows terminal example) `curl -H "Content-Type: application/json" -X POST -d "{\"message\":\"message from curl\"}" http://localhost:8081/message`
