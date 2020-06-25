package ru.mydesignstudio.database.metadata.extractor.output.confluence;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.DatabaseMetadata;
import ru.mydesignstudio.database.metadata.extractor.extractors.model.TableMetadata;
import ru.mydesignstudio.database.metadata.extractor.output.MetadataOutput;
import ru.mydesignstudio.database.metadata.extractor.output.Output;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.Confluence;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.FindResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.FindResult;
import ru.mydesignstudio.database.metadata.extractor.output.html.HtmlMetadataOutput;

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
  public List<Output> output(List<DatabaseMetadata> databaseMetadata, List<TableMetadata> tableMetadata) {
    final List<Output> htmlOutput = this.htmlOutput.output(databaseMetadata, tableMetadata);

    for (Output output : htmlOutput) {
      // remove old pages
      final FindResponse findResponse = confluence.find(output.getTitle(), confluenceSpace);
      for (FindResult findResult : findResponse.getResults()) {
        confluence.delete(findResult.getId());
      }

      // create a new page
      final CreateRequest request = CreateRequest.builder()
          .title(output.getTitle())
          .content(getContent(output))
          .space(confluenceSpace)
          .parentId(parentId)
          .labels(output.getLabels())
          .build();
      confluence.create(request);
    }
    return htmlOutput;
  }

  @SneakyThrows
  private String getContent(@NonNull Output output) {
    @Cleanup final BufferedReader reader = Files.newBufferedReader(output.getFilePath(), Charset.forName("UTF-8"));
    return IOUtils.toString(reader);
  }
}
