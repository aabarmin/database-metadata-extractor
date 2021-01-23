package ru.mydesignstudio.database.metadata.extractor.output;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.destination.OutputDestination;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;

import java.util.Map;

@Slf4j
@Component
public class ConfluenceCloudOutput extends ConfluenceOutput implements OutputDestination {
  @Override
  ConfluenceParams confluenceParams(Map<String, String> params) {
    return ConfluenceParams.builder()
        .space(params.get("space"))
        .parentPageId(Integer.parseInt(params.get("parentPageId")))
        .confluenceHost(params.get("host"))
        .confluencePort(Integer.parseInt(params.get("port")))
        .confluenceProtocol(params.get("protocol"))
        .username(params.get("username"))
        .password(params.get("password"))
        .confluenceType("cloud")
        .build();
  }

  @NotNull
  @Override
  public String getDestinationType() {
    return "CONFLUENCE_CLOUD";
  }
}
