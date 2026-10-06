package dev.matheushnt.toolboxapi.repository;

import dev.matheushnt.toolboxapi.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToolRepository extends JpaRepository<Tool, UUID> {
    List<Tool> findAllByUserId(UUID userId);

    int deleteByIdAndUserId(UUID id, UUID userId);

    @Query("""
        SELECT tl
        FROM Tool tl
        JOIN tl.tags tg
        WHERE tl.user.id = :userId
          AND tg.name = :tagName
    """)
    List<Tool> findByUserIdAndTagName(
        @Param("userId") UUID userId,
        @Param("tagName") String tagName
    );
}
