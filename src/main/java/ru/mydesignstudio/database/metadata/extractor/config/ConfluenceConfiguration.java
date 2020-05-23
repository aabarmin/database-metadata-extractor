package ru.mydesignstudio.database.metadata.extractor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceCredentials;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.ConfluenceFactory;

@Configuration
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceConfiguration {
  @Bean
  public ConfluenceCredentials confluenceCredentials(@Value("${confluence.username}") String confluenceUsername,
      @Value("${confluence.password}") String confluencePassword) {

    return new ConfluenceCredentials(confluenceUsername, confluencePassword);
  }

  @Bean
  public Confluence confluence(ConfluenceFactory confluenceFactory,
      ConfluenceCredentials credentials) {
    return confluenceFactory.create(credentials);
  }
}
