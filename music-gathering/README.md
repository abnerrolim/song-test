# Music Gathering Application
An API to improve music information distributed in different data sources.

## Dependencies

Check if you have installed:
 - Java 10 or >
 - mvn 3 or >
 - git

To test with PostgreSQL, also those below are required:
 - Docker
 - Docker Compose



## Building
First of all, clone the project to your machine:
``` sh
mvn  clean install
```
Just build with maven as usual
``` sh
mvn  clean install
```
If you like, you can run the uber jar directly executing:
```sh
java -jar target/music-gathering-0.0.1-SNAPSHOT.jar
```

## Running

### In memory
You can run the application with all dependencies embedded from the project home directory:
``` sh
mvn spring-boot:run
```
This configuration was designed to make the application runnable with maven but all registers on the database will be volatile.

### Using Postgresql with docker
Edit the application.properties file changing dev to prod
``` properties
spring.profiles.active: prod
```
Then you need to run on the project home directory:
``` sh
sudo docker-compose up
```
Wait until all images (PostgreSQL) are resolved and PostgreSQL starts and run
``` sh
mvn  spring-boot:run
```
This configuration was designed to exemplify a CI environment and extract some embedded implementations. More about this is explained later.

## Architecture
The main problem to solve here is the API's rate limit. Although the proposed architecture here helps to mitigate the problem, in a real scenario (Production) the best approach is to import the MusicBrainz and CoverArt's databases to our own RBDMS, caching almost all possible results. However, these databases are huge and would make the distribution of this
application impossible. So, to avoid that was created a subset of the MusicBrainz/CoverArt entities and an import strategy was considered out of scope, despite the real necessity on production.

As imagination exercise, we can consider adapt the MusicBrainz' relational model to this application or create an ETL process to translate to this current model. Using the MusicBrainz model bring us more flexibility in the future, but a lot of non-used information by now.

The general flow for this application is search on the database by the MBID and on success, the response will be HTTP 200 - OK, with a JSON content of the artist, álbuns and enriched information (description and album image). If not found on the database,
the response will be HTTP 202 - ACCEPTED and a message explaining that currently this information is not indexed by the application. On background, will be published several command messages on a queue to retrieve all this new information of REST API's and saving on the database.
The next request (if the command was already processed), the application will find the artist on the database and will show all the current information, that can be partially complete as maybe not all commands are executed. The number of commands equals the number of external requests. So will be N to find the image of N albums, for instance. All this process is asynchronous.

To achieve this, the application made use of:
 - The RDBS (HSQL or Postgresql): stores the information extracted by REST.
 - Flyway: Auto schema evolution
 - Feign: HTTP client to REST API's
 - Jsoup: HTTP Client and HTML Parser to HTML API's
 - ActiveMQ Artemis: embedded message broker
 - Hystryx: Circuit breacker to external API's
 - Spring data JPA/HIbernate: relational mapping
 - Spring Cache: to cache previous responses from database.

### Calling API's
The first API to be called is invoked by the ArtistCommand. Is the Music Brainz lookup artist API and returns artist details (id, name) and a series of relations. The release groups aren't queried here, because this API doesn't support pagination.
```
http://musicbrainz.org/ws/2/artist/{mbid}?fmt=json&inc=url-rels
```
The second extracts the description from the relations of an artist when an ArtistDescriptionCommand arrives. The strategy here is an ArtistDescriptionExtractor interface and an ArtistDescriptionExtractorFactory that applies the relation to an extractor if it can handles that relation.
With this approach, the application can have many extractors as required to be resilient. On this application, a DiscogExtractor and a RateYourMusicExtractor are implemented. The reasons behind the decision were because few relations have API's and Discog have one. The reason it extends with an additional source (RateYourMusic) is that discog have a low rate limit and not all artists have discog relations. So an HTML parser of RateYourMusic was the solution.
```
#discog
https://api.discogs.com/artists/{discogid}
#RateYourMusic
https://rateyourmusic.com/artist/michael_jackson
```
The third command is ReleasesCommand that retrieves from Music Brainz all releases groups of albums of that artist. If the artist has more than 100 occurrences, it will be iterated on the pagination until reaching the total.
```
http://musicbrainz.org/ws/2/release-group?fmt=json&limit=100&type=album&offset={offset}
```
For all release group, a ReleaseCoverArtCommand is thrown and retrieves from CoverArt image resources. The logic behind the selection of images is front > back > any. If CoverArt returns an exception (404, for example), will be set a default image on this server (http://localhost:8080/not_ready_default_none.jpg)
```
http://coverartarchive.org/release-group/{mbid}
```


### Out of Scope and TODO's
As out 0f scope, mainly was the strategy to use MusicBrainz and CovetArt databases was domain models or an ETL strategy to import data to the current model. This decision was made because would be very time-consuming and creates a problem to distribute the application.

A current list of TODO's:
 - Extract the Queue Consumers to another application, so can scale better. Currently is running on a single @Service. The reason was to avoid complexity to run the application.
 - A not-embedded message broker. The reason was to avoid complexity to run the application.
 - A not-embedded cache. Currently the application uses ConcurrentMapCacheManager. The reason was to avoid complexity to run the application.
 - A not-embedded RBDMS. Currently the application uses HSQL (by default). The reason was to avoid complexity to run the application.
 - As result of using ConcurrentMapCacheManager, it's not possible define a cache TTL. Actually, the cache on database layer is commented to allow a visualization of the asynchronous updates.
 - To make use of lombok. All lombok annotations was commented because lombok doesn't work well with java modules.
 - A better solution for java-module conflicts, some used libs aren't fully compatible with java modules.
 - Control for API's consuming. Currently the strategy for dealing with API's rate limits relay on Hystryx, but it's possible to reject to process a Command Message with some API reach its limits.
 - Better logic to choose which ArtistDescriptionExtractor will execute. Today it's simple executes linear until find one that can extract the description.
 - More ArtistDescriptionExtractor strategies.
 - A recurrent job to maintain the data correctness, searching for null attributes of missing Command Messages (description null, album image null) or /not_ready_default_none.jpg' album images
 - More tests