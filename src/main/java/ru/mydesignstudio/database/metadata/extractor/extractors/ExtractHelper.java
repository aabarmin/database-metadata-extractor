package ru.mydesignstudio.database.metadata.extractor.extractors;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ExtractHelper {
  @Autowired
  private JdbcTemplate jdbcTemplate;

  public <T> List<T> extract(@NonNull String query, @NonNull Object[] params, Class<T> targetClass) {
    return jdbcTemplate.query(query, params, new BeanPropertyRowMapper<>(targetClass));
  }
}
