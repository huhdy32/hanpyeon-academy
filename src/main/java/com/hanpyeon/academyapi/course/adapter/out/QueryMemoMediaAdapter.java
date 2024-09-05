package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaView;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoMediaPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QueryMemoMediaAdapter implements QueryMemoMediaPort {
    private final MemoMediaRepository memoMediaRepository;

    @Override
    public List<MemoMediaView> queryMedias(Long memoId) {
        if (Objects.isNull(memoId)) {
            throw new CourseException("메모를 찾을 수 없음 : " + memoId, ErrorCode.MEMO_NOT_EXIST);
        }
        final List<MemoMedia> memoMedias = memoMediaRepository.findAllByMemo_Id(memoId);
        return memoMedias.stream()
                .map(memoMedia -> {
                    final Media media = memoMedia.getMedia();
                    return new MemoMediaView(media.getMediaName(), media.getSrc(), memoMedia.getSequence());
                })
                .collect(Collectors.toList());
    }
}