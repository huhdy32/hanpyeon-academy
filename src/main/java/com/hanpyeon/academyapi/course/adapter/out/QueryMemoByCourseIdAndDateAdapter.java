package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoByCourseIdAndDatePort;
import com.hanpyeon.academyapi.course.domain.Memo;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryMemoByCourseIdAndDateAdapter implements QueryMemoByCourseIdAndDatePort {

    private final MemoRepository memoRepository;

    @Override
    public MemoView query(MemoQueryByCourseIdAndDateCommand command) {
        final List<com.hanpyeon.academyapi.course.adapter.out.Memo> memos = memoRepository.findAllByCourseIdAndTargetDate(
                command.getCourseId(),
                command.getLocalDate()
        );
        validate(memos);
        return mapTomMemoView(memos.get(0));
    }

    private void validate(List<com.hanpyeon.academyapi.course.adapter.out.Memo> memos) {
        if (memos.size() > 1 || memos.isEmpty()) {
            throw new CourseException("두개 이상의 메모가 발견됐습니다 : "+ memos.size(),ErrorCode.MEMO_DUPLICATED_EXCEPTION);
        }
    }

    private MemoView mapTomMemoView(final com.hanpyeon.academyapi.course.adapter.out.Memo memo) {
        return new MemoView(
                memo.getId(),
                memo.getProgressed(),
                memo.getHomework(),
                memo.getTargetDate().toLocalDate(),
                null,
                null
        );
    }
}
