package ru.mydesignstudio.database.metadata.extractor.output.service;

import org.springframework.lang.NonNull;
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
  CreateResponse create(@NonNull CreateRequest request);

  CreateResponse update(@NonNull UpdateRequest request);

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
