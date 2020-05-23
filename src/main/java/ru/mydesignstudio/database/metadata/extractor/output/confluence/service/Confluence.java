package ru.mydesignstudio.database.metadata.extractor.output.confluence.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.response.CreateResponse;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.find.FindResponse;

public interface Confluence {

  /**
   * Create a page with a given content, title in the given space.
   *
   * @param title
   * @param content
   * @param space
   * @param parentId
   * @return
   */
  CreateResponse create(@NonNull String title, @NonNull String content, @NonNull String space, @Nullable Integer parentId);

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
