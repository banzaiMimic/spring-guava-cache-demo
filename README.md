spring web app cache example using guava classes

### pre-req
- java sdk [tested w 18]
- maven [tested w 3.8.6]

### running
```
mvn package
java -jar target\demo-0.0.1-SNAPSHOT.jar
```

hit `localhost:8080/greeting?name=dev` from a browser
- console will print [doing some process] when running calculation (not reading from cache)
- refresh browser and you will get a result again but this time from cache
- after 5 seconds, the cache will expire and the subsequent refresh will re-calculate (not read from cache again)