use boardwe;

# insert theme categories
insert into theme_category (theme_category_name) values ('크리스마스');
set @christmas_theme_category_id = (select LAST_INSERT_ID());

insert into theme_category (theme_category_name) values ('생일');
set @birthday_theme_category_id = (select LAST_INSERT_ID());

insert into theme_category (theme_category_name) values ('TEMP');
set @temp_theme_category_id = (select LAST_INSERT_ID());


# insert board themes
insert into board_theme (theme_category_id, board_theme_name, board_theme_background_type, board_theme_background_color, board_theme_font)
    VALUES (@christmas_theme_category_id, '크리스마스', 'COLOR', '#A52A2A', '바탕');
set @christmas_theme_id = (select LAST_INSERT_ID());

insert into board_theme (theme_category_id, board_theme_name, board_theme_background_type, board_theme_background_color, board_theme_font)
VALUES (@birthday_theme_category_id, '생일', 'COLOR', '#F4BCF5', '나눔고딕');
set @birthday_theme_id = (select LAST_INSERT_ID());

insert into board_theme (theme_category_id, board_theme_name, board_theme_background_type, board_theme_background_color, board_theme_font)
VALUES (@temp_theme_category_id, 'TEMP', 'COLOR', '#FFFFFF', '궁서');
set @temp1_theme_id = (select LAST_INSERT_ID());

insert into board_theme (theme_category_id, board_theme_name, board_theme_background_type, board_theme_background_color, board_theme_font)
VALUES (@temp_theme_category_id, 'TEMP', 'COLOR', '#000000', '궁서');
set @temp2_theme_id = (select LAST_INSERT_ID());


# insert memo themes
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
    VALUES (@christmas_theme_id, 'COLOR', '#FF0000', '#000000');
set @christmas_memo_theme1_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@christmas_theme_id, 'COLOR', '#008000', '#000000');
set @christmas_memo_theme2_id = (select LAST_INSERT_ID());

insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@birthday_theme_id, 'COLOR', '#59065A', '#FFFFFF');
set @birthday_memo_theme1_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@birthday_theme_id, 'COLOR', '#8F1063', '#FFFFFF');
set @birthday_memo_theme2_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@birthday_theme_id, 'COLOR', '#FFFFFF', '#59065A');
set @birthday_memo_theme3_id = (select LAST_INSERT_ID());

insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp1_theme_id, 'COLOR', '#000000', '#FFFFFF');
set @temp1_memo_theme1_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp1_theme_id, 'COLOR', '#FFFFE0', '#000000');
set @temp1_memo_theme2_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp1_theme_id, 'COLOR', '#8F1063', '#FFFFFF');
set @temp1_memo_theme3_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp1_theme_id, 'COLOR', '#59065A', '#FFFFFF');
set @temp1_memo_theme4_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp1_theme_id, 'COLOR', '#122761', '#FFFFFF');
set @temp1_memo_theme5_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp1_theme_id, 'COLOR', '#BECDF8', '#000000');
set @temp1_memo_theme6_id = (select LAST_INSERT_ID());

insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp2_theme_id, 'COLOR', '#FFFFFF', '#000000');
set @temp2_memo_theme1_id = (select LAST_INSERT_ID());
insert into memo_theme (board_theme_id, memo_theme_background_type, memo_theme_background_color, memo_theme_text_color)
VALUES (@temp2_theme_id, 'COLOR', '#FFFFE0', '#000000');
set @temp2_memo_theme2_id = (select LAST_INSERT_ID());

# insert boards
set @date_now = NOW();

# # for creating permanent boards
# set @date_before_1 = DATE_SUB(@date_now, INTERVAL 1 MONTH);
# set @date_before_2 = DATE_SUB(@date_before_1, INTERVAL 1 MONTH);
# set @date_before_3 = DATE_SUB(@date_before_2, INTERVAL 1 MONTH);
# set @date_before_4 = DATE_SUB(@date_before_3, INTERVAL 1 MONTH);
# set @date_after_1 = DATE_ADD(@date_now, INTERVAL 10 YEAR);
# set @date_after_2 = DATE_ADD(@date_after_1, INTERVAL 1 MONTH);
# set @date_after_3 = DATE_ADD(@date_after_2, INTERVAL 1 MONTH);
# set @date_after_4 = DATE_ADD(@date_after_3, INTERVAL 1 MONTH);

# for creating real-time boards
set @date_before_1 = DATE_SUB(@date_now, INTERVAL 2 WEEK);
set @date_before_2 = DATE_SUB(@date_before_1, INTERVAL 2 WEEK);
set @date_before_3 = DATE_SUB(@date_before_2, INTERVAL 2 WEEK);
set @date_before_4 = DATE_SUB(@date_before_3, INTERVAL 2 WEEK);
set @date_after_1 = DATE_ADD(@date_now, INTERVAL 2 WEEK);
set @date_after_2 = DATE_ADD(@date_after_1, INTERVAL 2 WEEK);
set @date_after_3 = DATE_ADD(@date_after_2, INTERVAL 2 WEEK);
set @date_after_4 = DATE_ADD(@date_after_3, INTERVAL 2 WEEK);

insert into board (board_theme_id, board_name, board_description, board_code, board_writing_start_time, board_writing_end_time, board_open_start_time, board_open_end_time, board_password, board_open_type, board_views)
VALUES (@christmas_theme_id, '크리스마스 축하 보드', '크리스마스를 축하해조!', 'c60ce626-b9ea-4dfa-8f2f-f6e9008ed4ee', @date_before_4, @date_before_3, @date_before_2, @date_after_1, '1234', 'PUBLIC', 100);
set @christmas_board_id = (select LAST_INSERT_ID());
insert into board (board_theme_id, board_name, board_description, board_code, board_writing_start_time, board_writing_end_time, board_open_start_time, board_open_end_time, board_password, board_open_type, board_views)
VALUES (@birthday_theme_id, '생일 축하 보드', '내 생일을 축하해줘~', '81967f65-807d-4920-b3c4-4dbfc0993a46', @date_before_4, @date_before_3, @date_before_2, @date_after_1, '1234', 'PRIVATE', 23);
set @birthday_board_id = (select LAST_INSERT_ID());
insert into board (board_theme_id, board_name, board_description, board_code, board_writing_start_time, board_writing_end_time, board_open_start_time, board_open_end_time, board_password, board_open_type, board_views)
VALUES (@christmas_theme_id, '작성 이전 보드', '작성 기간 이전 보드', 'c5dcb7d7-7b0a-4d1c-9a54-98add70685f8', @date_after_1, @date_after_2, @date_after_2, @date_after_3, '1234', 'PUBLIC', 0);
set @before_writing_board_id = (select LAST_INSERT_ID());
insert into board (board_theme_id, board_name, board_description, board_code, board_writing_start_time, board_writing_end_time, board_open_start_time, board_open_end_time, board_password, board_open_type, board_views)
VALUES (@temp1_theme_id, '작성중 보드', '작성 기간 보드', '115a6aa8-bb82-4a2f-a789-d11b42ff17de', @date_before_1, @date_after_1, @date_after_2, @date_after_3, '1234', 'PUBLIC', 5);
set @writing_board_id = (select LAST_INSERT_ID());
insert into board (board_theme_id, board_name, board_description, board_code, board_writing_start_time, board_writing_end_time, board_open_start_time, board_open_end_time, board_password, board_open_type, board_views)
VALUES (@birthday_theme_id, '공개 이전 보드', '공개 기간 이전 보드', 'd614b0b1-2d32-4473-8719-87ccd933c986', @date_before_2, @date_before_1, @date_after_2, @date_after_3, '1234', 'PUBLIC', 23);
set @before_open_board_id = (select LAST_INSERT_ID());
insert into board (board_theme_id, board_name, board_description, board_code, board_writing_start_time, board_writing_end_time, board_open_start_time, board_open_end_time, board_password, board_open_type, board_views)
VALUES (@temp2_theme_id, '공개중 보드', '공개 기간 보드', 'b5ab3ef1-5501-410f-9c36-52a31b7ec9ac', @date_before_3, @date_before_2, @date_before_2, @date_after_1, '1234', 'PUBLIC', 13);
set @open_board_id = (select LAST_INSERT_ID());
insert into board (board_theme_id, board_name, board_description, board_code, board_writing_start_time, board_writing_end_time, board_open_start_time, board_open_end_time, board_password, board_open_type, board_views)
VALUES (@christmas_theme_id, '닫힌 보드', '공개 기간 끝난 보드', '0591247b-9558-4bf2-add2-7e27462fdf12', @date_before_3, @date_before_2, @date_before_2, @date_before_1, '1234', 'PUBLIC', 10);
set @closed_board_id = (select LAST_INSERT_ID());


# insert tags
insert into tag (board_id, tag_value) VALUES (@christmas_board_id, '크리스마스');
insert into tag (board_id, tag_value) VALUES (@christmas_board_id, '산타');
insert into tag (board_id, tag_value) VALUES (@christmas_board_id, '선물');

insert into tag (board_id, tag_value) VALUES (@birthday_board_id, '선물');
insert into tag (board_id, tag_value) VALUES (@birthday_board_id, '생일');
insert into tag (board_id, tag_value) VALUES (@birthday_board_id, '축하');

insert into tag (board_id, tag_value) VALUES (@before_writing_board_id, '보드');
insert into tag (board_id, tag_value) VALUES (@before_writing_board_id, '테스트');
insert into tag (board_id, tag_value) VALUES (@before_writing_board_id, 'aaa');

insert into tag (board_id, tag_value) VALUES (@writing_board_id, '보드');
insert into tag (board_id, tag_value) VALUES (@writing_board_id, '테스트');
insert into tag (board_id, tag_value) VALUES (@writing_board_id, 'bbb');

insert into tag (board_id, tag_value) VALUES (@before_open_board_id, '보드');
insert into tag (board_id, tag_value) VALUES (@before_open_board_id, 'test');
insert into tag (board_id, tag_value) VALUES (@before_open_board_id, 'ccc');

insert into tag (board_id, tag_value) VALUES (@open_board_id, '보드');
insert into tag (board_id, tag_value) VALUES (@open_board_id, 'test');
insert into tag (board_id, tag_value) VALUES (@open_board_id, 'ddd');

insert into tag (board_id, tag_value) VALUES (@closed_board_id, '보드');
insert into tag (board_id, tag_value) VALUES (@closed_board_id, '테스트');
insert into tag (board_id, tag_value) VALUES (@closed_board_id, 'eee');


# insert memos
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@christmas_board_id, @christmas_memo_theme1_id, '크리스마스 너무 좋아!! 최고야!! 선물은 아이패드로 주세요><');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@christmas_board_id, @christmas_memo_theme2_id, '크리스마스에는 밖에 나가지 말자... 사람 너무 많아ㅠㅠ');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@christmas_board_id, @christmas_memo_theme1_id, '나는 집에만 있어야징~ 나 홀로 집에 봐야겠다');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@christmas_board_id, @christmas_memo_theme2_id, '산타 할아버지 선물 주떼여! 애플워치!');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@christmas_board_id, @christmas_memo_theme2_id, '벌써 크리스마스! 내년에도 화이팅해야겠다~');

insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme1_id, '춘식아 생일 너무 추카해>< -라이언-');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme2_id, '나 누구게~ 생일축하해 춘식아! 우리 평생 친구하자~');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme3_id, '춘식이 너무 기여워. 최고야.');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme1_id, '내가 고구마 오백개 사줄게!!');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme1_id, '안뇽~ 생일 축하해! 나랑 같이 고구마 먹으러 갈래? -어피치-');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme2_id, '반가워 춘식아~ 아직 별로 안 친하지만 다음 생일에는 꼭 친해져 있자!');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme2_id, '춘식이 바보!');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@birthday_board_id, @birthday_memo_theme3_id, '생일 축하해요^^');

insert into memo (board_id, memo_theme_id, memo_content) VALUES (@writing_board_id, @temp1_memo_theme1_id, '보드 테스트');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@writing_board_id, @temp1_memo_theme2_id, '이 메모가 보이면 안됨!');

insert into memo (board_id, memo_theme_id, memo_content) VALUES (@before_open_board_id, @birthday_memo_theme1_id, '이 메모가 보이면 안됨!!!');

insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '공개 기간인 보드 테스트 메모입니다.');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, '공개 기간인 보드 테스트 메모입니다2222.');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '헬로');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, '하이하이 방가방가');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '안뇽!');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, '메모메모 테스트');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '글자가 잘 보이나요?');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, '테스트용 메모');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '안녕하세요 이건 글자 100자를 채우기 위한 테스트 메모입니다. 100자를 어떻게 채우지 너무 길다 그래도 쓰다보면 금방 쓸 것 같기도 하고.. 하여튼 100자를 채웠습니다 드디어');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, '춘식이는 귀여웡');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '보드위 보드미 화이팅');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, '보듬이도 귀여워');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '다들 성공하자');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, '이직 성공 기원');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme1_id, '화이티이이잉');
insert into memo (board_id, memo_theme_id, memo_content) VALUES (@open_board_id, @temp2_memo_theme2_id, 'ㅎㅎㅎㅎㅎ');

insert into memo (board_id, memo_theme_id, memo_content) VALUES (@closed_board_id, @christmas_memo_theme1_id, '이 메모가 보이면 안됨!!!');
