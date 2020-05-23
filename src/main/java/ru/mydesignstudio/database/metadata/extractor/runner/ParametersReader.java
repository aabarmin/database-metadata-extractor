package ru.mydesignstudio.database.metadata.extractor.runner;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class ParametersReader {
  @Value("${parameters.file}")
  private String parametersFilepath;

  @Autowired
  private ObjectMapper objectMapper;

  @SneakyThrows
  public Parameters read() {
    if (StringUtils.startsWith(parametersFilepath, "classpath")) {
      final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
      final Resource paramsResource = resourceLoader.getResource(parametersFilepath);
      @Cleanup final InputStream configStream = paramsResource.getInputStream();
      return objectMapper.readValue(configStream, Parameters.class);
    }

    final Path paramsPath = Paths.get(parametersFilepath);
    if (!Files.exists(paramsPath)) {
      throw new RuntimeException("Can't find parameters file " + parametersFilepath);
    }
    @Cleanup final InputStream configStream = Files.newInputStream(paramsPath, StandardOpenOption.READ);
    return objectMapper.readValue(configStream, Parameters.class);
  }
}
