package com.boardwe.boardwe.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {

    @Id
    @GeneratedValue
    @Column(name = "memo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="board_id",
            foreignKey = @ForeignKey(name="FK_board_TO_memo_1")
    )
    @NotNull
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name="memo_theme_id",
            foreignKey = @ForeignKey(name="FK_memo_theme_TO_memo_1")
    )
    @NotNull
    private MemoTheme memoTheme;

    @Column(
            columnDefinition = "NVARCHAR(100)",
            name = "memo_content"
    )
    @NotNull
    private String content;

    @Builder
    public Memo(Board board, MemoTheme memoTheme, String content) {
        this.board = board;
        this.memoTheme = memoTheme;
        this.content = content;
    }
}
