package com.project.lessonlog.lesson.repository;

import com.project.lessonlog.lesson.domain.LessonNote;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LessonNoteRepositoryImpl implements LessonNoteRepository {

    private final AtomicLong idGenerator = new AtomicLong();
    private final Map<Long, LessonNote> map = new HashMap<>();

    @Override
    public LessonNote save(LessonNote lessonNote) {
        Long id = idGenerator.incrementAndGet();
        lessonNote.setId(id);
        map.put(id, lessonNote);
        return lessonNote;
    }
}
