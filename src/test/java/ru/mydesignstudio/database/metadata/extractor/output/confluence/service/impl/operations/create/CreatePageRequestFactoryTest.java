package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;

@ExtendWith(MockitoExtension.class)
class CreatePageRequestFactoryTest {
  @Spy
  private HtmlSanitizer htmlSanitizer;

  @Spy
  private TitleSanitizer titleSanitizer;

  @InjectMocks
  private CreatePageRequestFactory unitUnderTest;

  @Test
  void check_contextStarts() {
    assertThat(unitUnderTest).isNotNull();
  }

  @Test
  void create_withoutParentShouldNotAddAncestors() {
    final CreatePageRequest request = unitUnderTest.createRequest(CreateRequest.builder()
        .space("space")
        .content("content")
        .title("title")
        .build());

    assertThat(request).isNotNull();
    assertThat(request.getAncestors()).isNull();
  }

  @Test
  void create_withParentShouldAddAncestors() {
    final CreatePageRequest request = unitUnderTest.createRequest(CreateRequest.builder()
        .space("space")
        .content("content")
        .title("title")
        .parentId(1234)
        .build());

    assertThat(request).isNotNull();
    assertThat(request.getAncestors()).hasSize(1);
    assertThat(request.getAncestors().get(0).getId()).isEqualTo(1234);
  }
}