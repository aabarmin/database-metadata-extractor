package ru.mydesignstudio.database.metadata.extractor.output;

import com.google.common.collect.Sets;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.destination.OutputDestination;
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.FindResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.FindResult;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.request.UpdateRequest;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Slf4j
public abstract class ConfluenceOutput implements OutputDestination {
  @Autowired
  private HtmlMetadataOutput htmlOutput;

  @Autowired
  private Confluence confluence;

  abstract ConfluenceParams confluenceParams(Map<String, String> params);

  @Override
  public boolean isValidParams(@NotNull Map<String, String> params) {
    final Set<String> paramNames = Sets.newHashSet(
        "space",
        "parentPageId",
        "host",
        "port",
        "protocol",
        "username",
        "password"
    );
    for (String name : paramNames) {
      if (!params.containsKey(name)) {
        log.warn("There is no key {} in params", name);
        return false;
      }
    }
    return true;
  }

  @NotNull
  @Override
  public Collection<Output> output(@NotNull DatabaseMetadata metadata, @NotNull Map<String, String> params) {
    final Collection<Output> htmlOutputs = htmlOutput.output(metadata, params);

    final ConfluenceParams confluenceParams = confluenceParams(params);
    for (Output output : htmlOutputs) {
      final FindResponse findResponse = confluence.find(output.getTitle(), confluenceParams);
      if (nothingFound(findResponse)) {
        // create a new page
        final CreateRequest request = CreateRequest.builder()
            .title(output.getTitle())
            .content(getContent(output))
            .space(confluenceParams.getSpace())
            .parentId(confluenceParams.getParentPageId())
            .labels(output.getLabels())
            .build();
        confluence.create(request, confluenceParams);
      } else {
        // update existing page
        for (final FindResult findResult : findResponse.getResults()) {
          final UpdateRequest request = UpdateRequest.builder()
              .id(findResult.getId())
              .title(output.getTitle())
              .content(getContent(output))
              .space(confluenceParams.getSpace())
              .parentId(confluenceParams.getParentPageId())
              .version(findResult.getVersion().getNumber() + 1)
              .labels(output.getLabels())
              .build();
          confluence.update(request, confluenceParams);
        }
      }
    }

    return htmlOutputs;
  }

  private boolean nothingFound(final FindResponse findResponse) {
    return findResponse.getResults().isEmpty();
  }

  @SneakyThrows
  private String getContent(@NonNull Output output) {
    @Cleanup final BufferedReader reader = Files.newBufferedReader(output.getFilePath(), Charset.forName("UTF-8"));
    return IOUtils.toString(reader);
  }
}
