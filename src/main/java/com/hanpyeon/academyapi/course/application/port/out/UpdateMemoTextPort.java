package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Memo;

public interface UpdateMemoTextPort {
    void update(final Memo memo);
}
