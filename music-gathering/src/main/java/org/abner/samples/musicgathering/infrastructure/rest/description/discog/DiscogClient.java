package org.abner.samples.musicgathering.infrastructure.rest.description.discog;

import org.abner.samples.musicgathering.infrastructure.rest.musicbrainz.MusicBrainzClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "discog"
        , url =  "${feign.discog.url}"
        , fallback =   DiscogClient.EmptyDiscogProfile.class
)
public interface DiscogClient {

    @RequestMapping(method = RequestMethod.GET, value = "/artists/{discogid}", consumes = "application/json")
    DiscogProfile artistProfile(@PathVariable(name = "discogid") String discogid);

    @Component
    class EmptyDiscogProfile implements DiscogClient{

        @Override
        public DiscogProfile artistProfile(String discogid) {
            return new DiscogProfile();
        }
    }
}
