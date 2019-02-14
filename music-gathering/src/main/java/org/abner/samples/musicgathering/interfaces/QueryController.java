package org.abner.samples.musicgathering.interfaces;

import org.abner.samples.musicgathering.application.ArtistService;
import org.abner.samples.musicgathering.interfaces.models.DiscographyOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {

    private ArtistService artistService;

    @Autowired
    public QueryController(ArtistService artistService){
        this.artistService = artistService;
    }

    @GetMapping(
            value = "/v1/artists/{mbid}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DiscographyOutput> getDiscography(@PathVariable("mbid") String mbid){
            var optArtist = artistService.findByMDIB(mbid);
            var discoOutput = optArtist.map(DiscographyOutput::from).orElse(DiscographyOutput.from());
            if(optArtist.isPresent())
                return ResponseEntity.ok(discoOutput);
            else
                return ResponseEntity.accepted().body(discoOutput);

    }

}