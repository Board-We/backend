package com.boardwe.boardwe.entity;

import com.boardwe.boardwe.type.BackgroundType;
import com.boardwe.boardwe.type.OpenType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void dbInit1() {
            ThemeCategory templateCategory = ThemeCategory.builder()
                    .name("TEMPLATE")
                    .build();
            ThemeCategory tempCategory = ThemeCategory.builder()
                    .name("TEMP")
                    .build();
            em.persist(templateCategory);
            em.persist(tempCategory);

            BoardTheme christmasTheme = BoardTheme.builder()
                    .name("크리스마스 산타 테마")
                    .themeCategory(templateCategory)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor("#A52A2A")
                    .font("Arial")
                    .build();
            BoardTheme userTheme = BoardTheme.builder()
                    .name("TEMP")
                    .themeCategory(tempCategory)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor("#FFFFFF")
                    .font("Arial")
                    .build();
            em.persist(christmasTheme);
            em.persist(userTheme);

            MemoTheme christmasMemoTheme1 = MemoTheme.builder()
                    .boardTheme(christmasTheme)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor("#FF0000")
                    .textColor("#000000")
                    .build();
            MemoTheme christmasMemoTheme2 = MemoTheme.builder()
                    .boardTheme(christmasTheme)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor("#008000")
                    .textColor("#000000")
                    .build();
            MemoTheme basicMemoTheme1 = MemoTheme.builder()
                    .boardTheme(userTheme)
                    .backgroundType(BackgroundType.COLOR)
                    .backgroundColor("#FFFFE0")
                    .textColor("#000000")
                    .build();
            em.persist(christmasMemoTheme1);
            em.persist(christmasMemoTheme2);
            em.persist(basicMemoTheme1);

            LocalDateTime currentTime = LocalDateTime.now();
            Board beforeWritingBoard = Board.builder()
                    .boardTheme(christmasTheme)
                    .name("크리스마스 축하 기념 보드1")
                    .description("크리스마스를 축하하자!1")
                    .code(UUID.randomUUID().toString())
                    .writingStartTime(currentTime.plusDays(1))
                    .writingEndTime(currentTime.plusDays(2))
                    .openStartTime(currentTime.plusDays(3))
                    .openEndTime(currentTime.plusDays(4))
                    .openType(OpenType.PUBLIC)
                    .password("1234")
                    .views(0)
                    .build();
            Board writingBoard = Board.builder()
                    .boardTheme(christmasTheme)
                    .name("크리스마스 축하 기념 보드2")
                    .description("크리스마스를 축하하자!2")
                    .code(UUID.randomUUID().toString())
                    .writingStartTime(currentTime.minusDays(1))
                    .writingEndTime(currentTime.plusDays(2))
                    .openStartTime(currentTime.plusDays(3))
                    .openEndTime(currentTime.plusDays(4))
                    .openType(OpenType.PUBLIC)
                    .password("1234")
                    .views(0)
                    .build();
            Board beforeOpenBoard = Board.builder()
                    .boardTheme(christmasTheme)
                    .name("크리스마스 축하 기념 보드3")
                    .description("크리스마스를 축하하자!3")
                    .code(UUID.randomUUID().toString())
                    .writingStartTime(currentTime.minusDays(2))
                    .writingEndTime(currentTime.minusDays(1))
                    .openStartTime(currentTime.plusDays(3))
                    .openEndTime(currentTime.plusDays(4))
                    .openType(OpenType.PUBLIC)
                    .password("1234")
                    .views(0)
                    .build();
            Board openBoard = Board.builder()
                    .boardTheme(christmasTheme)
                    .name("크리스마스 축하 기념 보드4")
                    .description("크리스마스를 축하하자!4")
                    .code(UUID.randomUUID().toString())
                    .writingStartTime(currentTime.minusDays(5))
                    .writingEndTime(currentTime.minusDays(4))
                    .openStartTime(currentTime.minusDays(1))
                    .openEndTime(currentTime.plusDays(4))
                    .openType(OpenType.PUBLIC)
                    .password("1234")
                    .views(0)
                    .build();
            Board closedBoard = Board.builder()
                    .boardTheme(christmasTheme)
                    .name("크리스마스 축하 기념 보드5")
                    .description("크리스마스를 축하하자!5")
                    .code(UUID.randomUUID().toString())
                    .writingStartTime(currentTime.minusDays(4))
                    .writingEndTime(currentTime.minusDays(3))
                    .openStartTime(currentTime.minusDays(2))
                    .openEndTime(currentTime.minusDays(1))
                    .openType(OpenType.PUBLIC)
                    .password("1234")
                    .views(0)
                    .build();
            Board privateBoard = Board.builder()
                    .boardTheme(userTheme)
                    .name("생일 축하해주기")
                    .description("내 생일을 축하해줘")
                    .code(UUID.randomUUID().toString())
                    .writingStartTime(currentTime.minusDays(4))
                    .writingEndTime(currentTime.plusDays(5))
                    .openStartTime(currentTime.plusDays(6))
                    .openEndTime(currentTime.plusDays(10))
                    .openType(OpenType.PRIVATE)
                    .password("1234")
                    .views(0)
                    .build();
            em.persist(beforeWritingBoard);
            em.persist(writingBoard);
            em.persist(beforeOpenBoard);
            em.persist(openBoard);
            em.persist(closedBoard);
            em.persist(privateBoard);

            Tag christmasTag1 = Tag.builder()
                    .board(beforeWritingBoard)
                    .value("크리스마스")
                    .build();
            Tag christmasTag2 = Tag.builder()
                    .board(writingBoard)
                    .value("크리스마스")
                    .build();
            Tag christmasTag3 = Tag.builder()
                    .board(beforeOpenBoard)
                    .value("크리스마스")
                    .build();
            Tag christmasTag4 = Tag.builder()
                    .board(openBoard)
                    .value("크리스마스")
                    .build();
            Tag christmasTag5 = Tag.builder()
                    .board(closedBoard)
                    .value("크리스마스")
                    .build();
            Tag userTag = Tag.builder()
                    .board(privateBoard)
                    .value("생일")
                    .build();
            em.persist(christmasTag1);
            em.persist(christmasTag2);
            em.persist(christmasTag3);
            em.persist(christmasTag4);
            em.persist(christmasTag5);
            em.persist(userTag);

            Memo christmasMemo1 = Memo.builder()
                    .board(writingBoard)
                    .memoTheme(christmasMemoTheme1)
                    .content("산타할아버지 선물 주세요!")
                    .build();
            Memo christmasMemo2 = Memo.builder()
                    .board(beforeOpenBoard)
                    .memoTheme(christmasMemoTheme1)
                    .content("선물 받고 싶다!!")
                    .build();
            Memo christmasMemo3 = Memo.builder()
                    .board(openBoard)
                    .memoTheme(christmasMemoTheme1)
                    .content("선물 받고 싶어요!!")
                    .build();
            Memo christmasMemo4 = Memo.builder()
                    .board(openBoard)
                    .memoTheme(christmasMemoTheme2)
                    .content("아이패드 갖고싶다")
                    .build();
            Memo christmasMemo5 = Memo.builder()
                    .board(closedBoard)
                    .memoTheme(christmasMemoTheme1)
                    .content("맥북 갖고싶다")
                    .build();
            Memo birthdayMemo1 = Memo.builder()
                    .board(privateBoard)
                    .memoTheme(basicMemoTheme1)
                    .content("생일 너무 축하해!! - 친구가")
                    .build();
            Memo birthdayMemo2 = Memo.builder()
                    .board(privateBoard)
                    .memoTheme(basicMemoTheme1)
                    .content("앞으로 더 친해지자~ - 짝꿍")
                    .build();
            Memo birthdayMemo3 = Memo.builder()
                    .board(privateBoard)
                    .memoTheme(basicMemoTheme1)
                    .content("나랑 100번째 생일에도 같이 놀자>< - 너의 소울 메이트")
                    .build();
            em.persist(christmasMemo1);
            em.persist(christmasMemo2);
            em.persist(christmasMemo3);
            em.persist(christmasMemo4);
            em.persist(christmasMemo5);
            em.persist(birthdayMemo1);
            em.persist(birthdayMemo2);
            em.persist(birthdayMemo3);
        }
    }
}
