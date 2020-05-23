package ru.mydesignstudio.database.metadata.extractor.resource;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ResourceLoaderBeanPostProcessor implements BeanPostProcessor {

  @Override
  @SneakyThrows
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

    for (Field field : bean.getClass().getDeclaredFields()) {
      final StringResource annotation = field.getAnnotation(StringResource.class);
      if (annotation != null) {
        final String resourceName = annotation.value();
        field.setAccessible(true);
        field.set(bean, readResource(resourceName, resourceLoader));
      }
    }

    return bean;
  }

  @SneakyThrows
  private String readResource(@NonNull String resourceName, @NonNull DefaultResourceLoader resourceLoader) {
    final Resource resource = resourceLoader.getResource(resourceName);
    @Cleanup final InputStream stream = resource.getInputStream();
    return IOUtils.toString(stream, Charset.forName("UTF-8"));
  }
}
