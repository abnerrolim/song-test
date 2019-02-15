package org.abner.samples.musicgathering.infrastructure.rest.description;

import org.abner.samples.musicgathering.domain.References;

public interface ArtistDescriptionExtractor {

    String extract();

    Boolean apply(References reference);
}
