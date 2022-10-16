package edu.cmis.zfit.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AbstractFileRepository {
    private Path basePath;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    protected AbstractFileRepository(Path basePath) {
        this.basePath = basePath;
//        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.findAndRegisterModules();
//        jsonMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    protected Path getFilePath(String userId, String fileNameSuffix) {
        return Paths.get(basePath.toString(), userId + "-" + fileNameSuffix);
    }

    protected ObjectMapper getJsonMapper() {
        return jsonMapper;
    }
}
