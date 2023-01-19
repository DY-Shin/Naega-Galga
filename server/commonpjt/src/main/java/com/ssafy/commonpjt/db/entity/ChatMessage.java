package com.ssafy.commonpjt.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @Column(name = "chat_message_index", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatMessageIndex;

    @Column(length = 2000)
    private String message;

    @CreationTimestamp
    private Timestamp createdAt;

    @ColumnDefault("false")
    private boolean isRead;
}
