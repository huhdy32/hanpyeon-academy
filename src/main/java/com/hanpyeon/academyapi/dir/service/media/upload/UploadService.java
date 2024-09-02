package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.dto.RequireNextChunk;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.ChunkProcessorManager;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkFactory;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator.ChunkPreValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UploadService {

    private final ChunkFactory chunkFactory;
    private final @Qualifier(value = "chunkStorage") ChunkStorage chunkStorage;
    private final ChunkProcessorManager chunkProcessorManager;
    private final ChunkPreValidator chunkPreValidator;

    public RequireNextChunk upload(final UploadMediaDto uploadMediaDto) {
        final ChunkedFile chunkedFile = getValidatedChunkFile(uploadMediaDto);
        return chunkProcessorManager.process(chunkedFile, chunkStorage);
    }

    private ChunkedFile getValidatedChunkFile(final UploadMediaDto uploadMediaDto) {
        final ChunkedFile chunkedFile = chunkFactory.create(uploadMediaDto);
        chunkPreValidator.preValidate(chunkedFile);
        return chunkedFile;
    }
}
