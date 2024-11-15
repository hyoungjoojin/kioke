package com.kioke.journal.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JournalTest {

  public static Journal buildTestData(String jid) {
    return Journal.builder()
        .id(jid)
        .title("Title for testdata")
        .template("Template for testdata")
        .pages(new ArrayList<Page>())
        .createdAt(LocalDateTime.now())
        .lastUpdated(LocalDateTime.now())
        .build();
  }
}
