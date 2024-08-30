package com.hanpyeon.academyapi.dir.service.media.upload;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
class BasicChunkedFile implements ChunkedFile {

    private final MultipartFile multipartFile;
    private final ChunkGroupInfo chunkGroupInfo;
    private final Boolean isLast;

    @Override
    public ChunkGroupInfo getChunkGroupInfo() {
        return chunkGroupInfo;
    }

    @Override
    public boolean isLast() {
        return isLast;
    }

    @Override
    public String getUniqueFileName() {
        return chunkGroupInfo.getChunkUniqueId();
    }

    @Override
    public InputStream getInputStream() {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ChunkException("청크 파일에 접근할 수 없음",ErrorCode.CHUNK_ACCESS_EXCEPTION);
        }
    }

    @Override
    public String getExtension() {
        return null;
    }
}
