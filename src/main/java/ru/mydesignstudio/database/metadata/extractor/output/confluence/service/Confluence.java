package ru.mydesignstudio.database.metadata.extractor.output.confluence.service;

import org.springframework.lang.NonNull;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.FindResponse;

public interface Confluence {

  /**
   * Create a page with a given content, title in the given space.
   *
   * @param request request to create a page
   * @return creation response
   */
  CreateResponse create(@NonNull CreateRequest request);

  /**
   * Find pages by title and space name.
   *
   * @param title
   * @param space
   * @return
   */
  FindResponse find(@NonNull String title, @NonNull String space);

  /**
   * Delete a page from Confluence.
   *
   * @param contentId page id
   * @return
   */
  boolean delete(@NonNull String contentId);
}
