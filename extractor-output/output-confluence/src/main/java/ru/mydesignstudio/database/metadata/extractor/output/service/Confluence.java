package ru.mydesignstudio.database.metadata.extractor.output.service;

import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.model.ConfluenceParams;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.find.FindResponse;
import ru.mydesignstudio.database.metadata.extractor.output.service.impl.operations.update.request.UpdateRequest;

public interface Confluence {

  /**
   * Create a page with a given content, title in the given space.
   *
   * @param request request to create a page
   * @return creation response
   */
  CreateResponse create(@NonNull CreateRequest request, @NonNull ConfluenceParams confluenceParams);

  CreateResponse update(@NonNull UpdateRequest request, @NonNull ConfluenceParams confluenceParams);

  /**
   * Find pages by title and space name.
   *
   * @param title
   * @return
   */
  FindResponse find(@NonNull String title, @NonNull ConfluenceParams confluenceParams);

  /**
   * Delete a page from Confluence.
   *
   * @param contentId page id
   * @return
   */
  boolean delete(@NonNull String contentId, @NonNull ConfluenceParams confluenceParams);
}
