# RPAM

RPAM is a simple ad delivery application.

## Usage

### API
One must always provide a channel to get ads from (available channels are: fashion, cooking, design, health and food).

For specific, already known ad id call `/ads/:channel/:id`.

If there is such an ad available, response will be looking like this:

```
{
  "code": "OK",
  "data": {
    "id": 0,
    "url": "http://example.com/0.jpg"
  }
}
```

If ad is not found, response looks like this:

```
{
  "code": "No Content",
  "data": []
}
```


For list of available ads in channel, call `/ads/:channel/:country/:language`

If there are ads available, response will be looking like this:

```
{
  "code": "OK",
  "data": [
    {
      "id": 2,
      "url": "http://example.com/2.jpg"
    },
    {
      "id": 3,
      "url": "http://example.com/3.jpg"
    }
  ]
}
```

If ads for params not found, response looks like this:

```
{
  "code": "No Content",
  "data": []
}
```



### Run the application locally and experiment with API in Swagger

`lein ring server`

### Run the tests

`lein midje`

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`
