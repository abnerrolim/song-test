package org.abner.samples.musicgathering.infrastructure.rest.description;


import org.abner.samples.musicgathering.TestUtils;
import org.abner.samples.musicgathering.domain.References;
import org.abner.samples.musicgathering.infrastructure.rest.description.discog.DiscogClient;
import org.abner.samples.musicgathering.infrastructure.rest.description.discog.DiscogExtractor;
import org.abner.samples.musicgathering.infrastructure.rest.description.discog.DiscogProfile;
import org.abner.samples.musicgathering.infrastructure.rest.description.rateyourmusic.RateYourMusicExtractor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collections;

@RunWith(PowerMockRunner.class)
@PrepareForTest( Jsoup.class )
public class ArtistDescriptionExtractorFactoryTest {


    private static final String RTYM_URL = "https://rateyourmusic.com/artist/michael-jackson";
    private static final String DICOG_ID = "15885";

    private ArtistDescriptionExtractorFactory extractorFactory;
    private DiscogExtractor discogExtractor;
    private RateYourMusicExtractor rateYourMusicExtractor;


    @Before
    public void setup() throws Exception{
        PowerMockito.mockStatic(Jsoup.class);
        var conn  = PowerMockito.mock(Connection.class);
        PowerMockito.when(Jsoup.connect(RTYM_URL)).thenReturn(conn);
        PowerMockito.when(conn.get()).thenReturn(buildMichaelDocument());
        rateYourMusicExtractor = new RateYourMusicExtractor();

        var feignDiscogClient = PowerMockito.mock(DiscogClient.class);
        PowerMockito.when(feignDiscogClient.artistProfile(DICOG_ID)).thenReturn(buildMichaelDiscogProfile());
        discogExtractor = new DiscogExtractor(feignDiscogClient);

        extractorFactory = new ArtistDescriptionExtractorFactory(Arrays.asList(rateYourMusicExtractor, discogExtractor));
    }

    @Test
    public void shouldSuccessfullyExtractFromRateYourMusic(){
        var ref = buildRateYMRef();
        var extractors = extractorFactory.extractors(Collections.singletonList(ref));
        assertThat(extractors, hasSize(1));
        var desc = extractors.get(0).extract();
        assertThat(desc, is(containsString("the infant Jackson")));
    }

    @Test
    public void shouldSuccessfullyExtractFromDiscog(){
        var ref = buildDiscogRef();
        var extractors = extractorFactory.extractors(Collections.singletonList(ref));
        assertThat(extractors, hasSize(1));
        var desc = extractors.get(0).extract();
        assertThat(desc, is(containsString("American singer, dancer, entertainer, songwriter")));
    }

    private static References buildRateYMRef(){
        var ref = new References();
        ref.setType("Other Database");
        ref.setResource("https://rateyourmusic.com/artist/michael-jackson");
        return ref;
    }
    private static References buildDiscogRef(){
        var ref = new References();
        ref.setType("discogs");
        ref.setResource("https://www.discogs.com/artist/15885");
        return ref;
    }


    private static Document buildMichaelDocument(){
        var doc = Document.createShell(RTYM_URL);
        var bodyString = TestUtils.getResourceAsString("html/michael.html");
        doc.body().html(bodyString);
        return doc;
    }

    private static DiscogProfile buildMichaelDiscogProfile() {
        return TestUtils.getResourceJsonAsObject("json/michael.json", DiscogProfile.class);
    }

}