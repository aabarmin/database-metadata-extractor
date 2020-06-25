package ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreatePageRequest;
import ru.mydesignstudio.database.metadata.extractor.output.confluence.service.impl.operations.create.request.CreateRequest;
import ru.mydesignstudio.database.metadata.extractor.output.html.label.Label;

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

  @Test
  void create_withLabels() {
    final CreatePageRequest request = unitUnderTest.createRequest(CreateRequest.builder()
        .space("space")
        .content("content")
        .title("title")
        .labels(Sets.newHashSet(
            new Label("global", "label1"),
            new Label("global", "label2"),
            new Label("global", "label3")
        ))
        .build());

    assertAll(
        () -> assertNotNull(request),
        () -> assertNotNull(request.getMetadata()),
        () -> assertNotNull(request.getMetadata().getLabels()),
        () -> assertEquals(3, request.getMetadata().getLabels().size())
    );
  }
}