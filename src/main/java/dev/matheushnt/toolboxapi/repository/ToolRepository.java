package dev.matheushnt.toolboxapi.repository;

import dev.matheushnt.toolboxapi.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ToolRepository extends JpaRepository<Tool, UUID> {
}
