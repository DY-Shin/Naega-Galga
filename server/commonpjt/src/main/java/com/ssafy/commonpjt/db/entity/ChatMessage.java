package com.ssafy.commonpjt.db.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "chat_message")
@Getter
@Setter
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

    private Timestamp createdAt;

    @ColumnDefault("false")
    private boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_index", referencedColumnName = "chat_index")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_index", referencedColumnName = "user_index")
    private User sender;
}
