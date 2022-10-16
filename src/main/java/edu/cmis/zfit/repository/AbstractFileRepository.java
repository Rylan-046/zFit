package edu.cmis.zfit.repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AbstractFileRepository {
    private Path basePath;
    private final ObjectMapper jsonMapper = new ObjectMapper();

    protected AbstractFileRepository(Path basePath) {
        this.basePath = basePath;
        jsonMapper.findAndRegisterModules();
    }

    protected Path getFilePath(String userId, String fileNameSuffix) {
        return Paths.get(basePath.toString(), userId + "-" + fileNameSuffix);
    }

    protected ObjectMapper getJsonMapper() {
        return jsonMapper;
    }
}
