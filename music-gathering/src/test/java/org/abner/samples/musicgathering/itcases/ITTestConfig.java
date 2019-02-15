package org.abner.samples.musicgathering.itcases;

import org.abner.samples.musicgathering.MusicGatheringConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableAspectJAutoProxy
@EnableJpaRepositories({
        "org.abner.samples.musicgathering.infrastructure.database"
})

@EntityScan({
        "org.abner.samples.musicgathering.infrastructure.database"
})
@Import(value = MusicGatheringConfiguration.class)
public class ITTestConfig {


    @Bean
    public JmsListenerJoiner jmsListenerJoiner() {
        return new JmsListenerJoiner();
    }

}
