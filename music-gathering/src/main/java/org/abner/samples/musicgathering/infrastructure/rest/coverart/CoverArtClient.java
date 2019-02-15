package org.abner.samples.musicgathering.infrastructure.rest.coverart;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "coverart"
        , url = "${feign.coverart.url}"
        , fallback = CoverArtClient.EmptyCoverArt.class
)
public interface CoverArtClient {

    @RequestMapping(method = RequestMethod.GET, value = "/release-group/{mbid}", consumes = "application/json")
    CoverArt coverArt(@PathVariable(name = "mbid") String mbid);

    class EmptyCoverArt implements CoverArtClient {

        @Override
        public CoverArt coverArt(String mbid) {
            return new CoverArt();
        }
    }
}
