package org.abner.samples.musicgathering.infrastructure.rest.description;

import org.abner.samples.musicgathering.domain.References;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistDescriptionExtractorFactory {

    private final List<ArtistDescriptionExtractor> extractors;

    public ArtistDescriptionExtractorFactory(@Autowired List<ArtistDescriptionExtractor> extractors) {
        this.extractors = extractors;
    }

    public List<ArtistDescriptionExtractor> extractors(Collection<References> references) {
        //TODO: better inteligence to elect extractor
        return extractors.stream().filter(artistDescriptionExtractor ->
                references.stream().anyMatch(artistDescriptionExtractor::apply)
        ).collect(Collectors.toList());
    }
}
