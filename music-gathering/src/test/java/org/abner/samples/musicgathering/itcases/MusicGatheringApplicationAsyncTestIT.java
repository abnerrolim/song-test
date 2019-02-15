package org.abner.samples.musicgathering.itcases;

import org.abner.samples.musicgathering.application.ArtistRepository;
import org.abner.samples.musicgathering.domain.messages.ArtistCommand;
import org.abner.samples.musicgathering.infrastructure.message.AsyncArtistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DirtiesContext
public class MusicGatheringApplicationAsyncTestIT {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private JmsListenerJoiner jmsListenerJoiner;
    @Autowired
    private AsyncArtistService asyncArtistService;
    @Autowired
    @Qualifier("database")
    private ArtistRepository database;

    private AsyncArtistService observableAsyncArtistService;

    @Before
    public void intialize() {
        observableAsyncArtistService = Mockito.spy(asyncArtistService);
    }

    @Test(timeout = 30000L)
    public void newArtistShouldCommandAsyncGathering() throws Exception {
        var artistCmd = new ArtistCommand("75359245-49cf-4991-932f-e078408481f2");

        this.mockMvc.perform(get("/v1/artists/" + artistCmd.getMbid())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("message").exists())
                .andReturn();

        jmsListenerJoiner.await();
        Mockito.verify(observableAsyncArtistService, Mockito.times(1)).receiveMessage(artistCmd);
        var optArtist = database.findByMBID(artistCmd.getMbid());
        assertThat(optArtist.isPresent(), is(true));
        assertThat(optArtist.get().getMbid(), is(equalTo(artistCmd.getMbid())));

        this.mockMvc.perform(get("/v1/artists/" + artistCmd.getMbid())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("artist").exists())
                .andExpect(jsonPath("artist.mbid").value(equalTo(artistCmd.getMbid())))
                .andReturn();

    }

}

