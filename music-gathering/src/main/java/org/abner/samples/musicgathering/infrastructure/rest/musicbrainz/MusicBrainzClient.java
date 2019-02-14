package org.abner.samples.musicgathering.infrastructure.rest.musicbrainz;

import org.abner.samples.musicgathering.infrastructure.database.ReleaseGroupDB;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "musicbrainz"
        , url = "${feign.musicbrainz.url}"
        , fallback =   MusicBrainzClient.EmptyMusicBrainzClient.class
)
public interface MusicBrainzClient {

    @RequestMapping(method = RequestMethod.GET, value = "/artist/{mbid}?fmt=json&inc=url-rels", consumes = "application/json")
    ArtistLookupMB lookupArtist(@PathVariable(name = "mbid") String mbid);

    @RequestMapping(method = RequestMethod.GET, value = "/release-group?fmt=json&limit=100&type=album", consumes = "application/json")
    ReleaseGroupPageMB releaseGroups(@RequestParam(name = "artist") String artist, @RequestParam(name = "offset") Integer offset);

    @RequestMapping(method = RequestMethod.GET, value = "/release-group/{mbid}?fmt=json", consumes = "application/json")
    ReleaseGroupPageMB.ReleaseGroupMB releaseGroup(@PathVariable(name = "mbid") String mbid);


    @Component
    class EmptyMusicBrainzClient implements MusicBrainzClient {

        @Override
        public ArtistLookupMB lookupArtist(String mbid) {
            return null;
        }

        @Override
        public ReleaseGroupPageMB releaseGroups(String artist, Integer offset) {
            return null;
        }

        @Override
        public ReleaseGroupPageMB.ReleaseGroupMB releaseGroup(String mbid) {
            return null;
        }
    }

}
