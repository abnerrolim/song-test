package org.abner.samples.musicgathering.infrastructure.rest.description.rateyourmusic;

import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.infrastructure.rest.description.ArtistDescriptionExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RateYourMusicExtractor implements ArtistDescriptionExtractor {

    private static final String RATE_YOUR_MUSIC = "rateyourmusic.com";
    private static final Logger LOG = Logger.getLogger(RateYourMusicExtractor.class.getName());
    private References reference;


    @Override
    public String extract() {
        Objects.requireNonNull(reference);
        try {
            var doc = Jsoup.connect(reference.getResource()).get();
            var element = Optional.ofNullable(doc.select(".section_artist_biography").first());
            return element.map(Element::text).orElse("");
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Couldn't extract data from {}. Error {}", reference);
        }
        return "";
    }

    @Override
    public Boolean apply(References referenceCandidate) {
        var applied = referenceCandidate.getResource().contains(RATE_YOUR_MUSIC);
        if (applied)
            this.reference = referenceCandidate;
        return applied;
    }
}
