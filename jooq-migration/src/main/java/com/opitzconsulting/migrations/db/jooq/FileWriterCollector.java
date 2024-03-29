package com.opitzconsulting.migrations.db.jooq;

import java.io.FileWriter;

public class FileWriterCollector implements StatementCollector, AutoCloseable {
    private final FileWriter fileWriter;

    public FileWriterCollector(String fileName) {
        try {
            fileWriter = new FileWriter(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void collect(String statement) {
        try {
            fileWriter.write(statement + ";\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        fileWriter.close();
    }
}
