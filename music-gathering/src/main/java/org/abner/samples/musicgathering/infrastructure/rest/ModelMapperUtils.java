package org.abner.samples.musicgathering.infrastructure.rest;

import org.abner.samples.musicgathering.domain.Artist;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.domain.ReleaseGroup;
import org.abner.samples.musicgathering.infrastructure.rest.musicbrainz.ArtistLookupMB;
import org.abner.samples.musicgathering.infrastructure.rest.musicbrainz.ReleaseGroupPageMB;

import java.util.List;
import java.util.stream.Collectors;

public final class ModelMapperUtils {
    private ModelMapperUtils() {
    }

    public static Artist map(ArtistLookupMB lookup) {
        var artist = new Artist();
        artist.setMbid(lookup.getId());
        artist.setDescription("[]");
        artist.setName(lookup.getName());
        artist.syncStatus();
        artist.setReferences(mapRefFromLookup(lookup));
        return artist;
    }

    private static List<References> mapRefFromLookup(ArtistLookupMB lookup) {
        return lookup.getRelations().stream()
                .map(relation -> map(relation, lookup))
                .collect(Collectors.toList());
    }

    private static References map(ArtistLookupMB.Relation relation, ArtistLookupMB lookup) {
        var ref = new References();
        ref.setId(relation.getUrl().getId());
        ref.setType(relation.getType());
        ref.setResource(relation.getUrl().getResource());
        ref.setActorMbid(lookup.getId());
        return ref;
    }

    public static ReleaseGroup map(ReleaseGroupPageMB.ReleaseGroupMB releaseGroupDB, String artistMbid) {
        var releaseGroup = new ReleaseGroup();
        releaseGroup.setImageResource(releaseGroupDB.getCoverArtImage());
        releaseGroup.setMbid(releaseGroupDB.getId());
        releaseGroup.setTitle(releaseGroupDB.getTitle());
        releaseGroup.setActorMbid(artistMbid);
        return releaseGroup;
    }
}
