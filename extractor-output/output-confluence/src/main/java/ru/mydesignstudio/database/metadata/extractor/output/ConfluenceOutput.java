package ru.mydesignstudio.database.metadata.extractor.output;

import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extract.result.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extract.result.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.FindResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.FindResult;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.request.UpdateRequest;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Primary
@Component
@ConditionalOnProperty(name = "output.target", havingValue = "confluence", matchIfMissing = false)
public class ConfluenceOutput implements MetadataOutput {
  @Autowired
  private HtmlMetadataOutput htmlOutput;

  @Value("${confluence.space}")
  private String confluenceSpace;

  @Value("${confluence.parent.page.id}")
  private Integer parentId;

  @Autowired
  private Confluence confluence;

  @Override
  public List<Output> output(@NonNull List<DatabaseMetadata> databaseMetadata, @NonNull List<TableMetadata> tableMetadata) {
    checkNotNull(databaseMetadata, "Database metadata should not be null");
    checkNotNull(tableMetadata, "Table metadata should not be null");

    final List<Output> htmlOutput = this.htmlOutput.output(databaseMetadata, tableMetadata);

    for (Output output : htmlOutput) {
      final FindResponse findResponse = confluence.find(output.getTitle(), confluenceSpace);
      if (nothingFound(findResponse)) {
        // create a new page
        final CreateRequest request = CreateRequest.builder()
            .title(output.getTitle())
            .content(getContent(output))
            .space(confluenceSpace)
            .parentId(parentId)
            .labels(output.getLabels())
            .build();
        confluence.create(request);
      } else {
        // update existing page
        for (final FindResult findResult : findResponse.getResults()) {
          final UpdateRequest request = UpdateRequest.builder()
              .id(findResult.getId())
              .title(output.getTitle())
              .content(getContent(output))
              .space(confluenceSpace)
              .parentId(parentId)
              .version(findResult.getVersion().getNumber() + 1)
              .labels(output.getLabels())
              .build();
          confluence.update(request);
        }
      }
    }
    return htmlOutput;
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
