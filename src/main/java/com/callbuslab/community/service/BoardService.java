package com.callbuslab.community.service;

import com.callbuslab.community.constraint.DeleteFlag;
import com.callbuslab.community.constraint.ResponseMessage;
import com.callbuslab.community.domain.entity.*;
import com.callbuslab.community.web.dto.BoardCommentDto;
import com.callbuslab.community.web.dto.BoardDto;
import com.callbuslab.community.web.dto.BoardListDto;
import com.callbuslab.community.web.dto.BoardWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 글 관련 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class BoardService {

    /** 글 레포지토리 */
    private final BoardRepository boardRepository;
    /** 댓글 레포지토리 */
    private final CommentRepository commentRepository;
    /** 좋아요 레포지토리 */
    private final FavoriteRepository favoriteRepository;
    /** 구성원 서비스 */
    private final MemberService memberService;

    /**
     * 커뮤니티 게시글 목록 불러오기
     *
     * <p>
     *     게시판은 임대인, 임차인, 공인중개사, 외부이용자 모두 이용가능합니다.<br>
     *     글id, 제목, 글쓴이, 좋아요 여부, 좋아요 수를 확인 할 수 있습니다.
     * </p>
     *
     * @param memberId 이용자 id
     * @return 게시글 목록
     */
    public List<BoardListDto> getBoardList(Long memberId) {

        // 등록된 게시글 리스트 습득
        List<Board> boardList = boardRepository.findAllDesc();
        System.out.println("1 :" + boardList.size());
        // 좋아요 여부를 확인하기 위한 좋아요 리스트 획득
        List<Long> favoriteList = favoriteRepository.findByMemberId(memberId)
                .stream()
                .map(Favorite::getBoardId)
                .collect(Collectors.toList());
        System.out.println("2 :" + favoriteList.size());
        // 화면에 표시할 글 목록 리스트 생성
        List<BoardListDto> resultList = new ArrayList<>();
        for(Board board : boardList) {
            boolean flag = false;
            // 좋아요 여부 확인
            if(favoriteList.contains(board.getId())) {
                flag = true;
            }
            // 글쓴이명 취득
            String writer = memberService.getMemberName(board.getWriterId());

            // 객체 생성
            BoardListDto boardListDto = BoardListDto.builder().id(String.valueOf(board.getId()))
                    .title(board.getTitle())
                    .writer(writer)
                    .favoriteFlag(flag)
                    .favoriteCount(String.valueOf(board.getFavoriteCount()))
                    .build();
            resultList.add(boardListDto);
        }
        System.out.println("3 :" + resultList.size());
        return resultList;
    }

    /**
     * 게시글 상세 습득
     *
     * <p>
     *     글 id와 이용자 id를 이용해 글 상세 내용을 습득합니다.<br>
     *     글id, 글제목, 글내용, 작성자, 좋아요수, 삭제시간, 삭제여부를 확인 할 수 있습니다.<br>
     *
     * </p>
     * @param boardId 글 id
     * @param memberId 이용자 id
     * @return 글 id에 해당하는 게시글의 상세
     */
    public BoardDto getBoard(Long boardId, Long memberId) {

        // 등록된 게시글
        Board board = boardRepository.getById(boardId);

        // 좋아요 여부를 확인하기 위한 좋아요 테이블 탐색
        Favorite favorite = favoriteRepository.findByBoardIdAndMemberId(boardId, memberId);
        boolean favoriteFlag = false;
        // 좋아요 여부 확인
        if(favorite != null) {
            favoriteFlag = true;
        }

        // 글쓴이명
        String writer = memberService.getMemberName(board.getWriterId());

        // 좋아요 체크한 사람의 리스트
        List<String> favoriteCheckerList = new ArrayList<>();
        List<Favorite> favoriteList = favoriteRepository.findByBoardId(boardId);
        for(Favorite entity : favoriteList) {
            favoriteCheckerList.add(memberService.getMemberName(entity.getMemberId()));
        }

        // 댓글 리스트
        List<Comment> commentList = commentRepository.findByBoardId(board.getId());
        List<BoardCommentDto> boardCommentList = new ArrayList<>();
        for(Comment comment : commentList) {
            BoardCommentDto boardCommentDto = BoardCommentDto.builder()
                    .commentId(comment.getId())
                    .commentWriter(memberService.getMemberName(comment.getCommentWriterId()))
                    .content(comment.getContent()).build();
            boardCommentList.add(boardCommentDto);
        }

        // 글 상세보기 DTO 생성
        BoardDto boardDto = BoardDto.builder().id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(LocalDateTimeToString(board.getCreatedDate()))
                .modifiedDate(LocalDateTimeToString(board.getModifiedDate()))
                .deleteDate(LocalDateTimeToString(board.getDeleteDate()))
                .favoriteCount(board.getFavoriteCount())
                .writer(writer)
                .favoriteFlag(favoriteFlag)
                .favoriteCheckerList(favoriteCheckerList)
                .boardCommentList(boardCommentList)
                .build();

        return boardDto;
    }

    /**
     * 게시글 등록
     *
     * <p>
     *     게시글 신규 등록
     * </p>
     *
     * @param dto 등록할 글 내용
     * @param memberId 이용자 id
     * @return 처리 결과 메세지
     */
    public String registerBoard(BoardWriteDto dto, Long memberId) {

        Board board = Board.builder().title(dto.getTitle())
                .content(dto.getContent())
                .writerId(memberId)
                .favoriteCount(0)
                .delAble(DeleteFlag.NOT_DELETE.getDeleteFlag())
                .build();

        Board resultEntity = boardRepository.save(board);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.boardRegisterFail;
        }
    }

    /**
     * 게시글 수정
     *
     * <p>
     *     게시글 수정<br>
     *     존재하지 않는 글 id거나 해당 글의 글쓴이가 아니라면 에러
     * </p>
     * @param dto 수정 할 내용
     * @param boardId 수정 대상 글 id
     * @param memberId 이용자 id
     * @return 처리 결과 메세지
     */
    public String updateBoard(BoardWriteDto dto, Long boardId, Long memberId) {

        // 글 등록 여부 확인
        Board targetEntity = boardRepository.getById(boardId);

        // 글 id로 습득이 되지 않거나, 해당 유저가 쓴 글이 아닐 경우 에러
        if(targetEntity == null || targetEntity.getWriterId() != memberId) {
            return ResponseMessage.authError;
        }

        // 수정 대상 데이터 세팅
        targetEntity.setUpdateContent(dto.getTitle(), dto.getContent());

        // 수정 등록
        Board resultEntity = boardRepository.save(targetEntity);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.boardUpdateFail;
        }
    }

    /**
     * 게시글 삭제
     *
     * <p>
     *     게시글 삭제<br>
     *     존재하지 않는 글 id거나 해당 글의 글쓴이가 아니라면 에러
     * </p>
     *
     * @param boardId 삭제 대상 글 id
     * @param memberId 이용자 id
     * @return 처리 결과 메세지
     */
    public String deleteBoard(Long boardId, Long memberId) {

        // 글 등록 여부 확인
        Board targetEntity = boardRepository.getById(boardId);

        // 글 id로 습득이 되지 않거나, 해당 유저가 쓴 글이 아닐 경우 에러
        if(targetEntity == null || targetEntity.getWriterId() != memberId) {
            return ResponseMessage.authError;
        }

        // 글 삭제용 파라메터 세팅
        targetEntity.setDeleteContent(LocalDateTime.now());

        // 삭제 등록
        // 논리 삭제를 하기때문에, 삭제여부와 삭제시간을 업데이트
        Board resultEntity = boardRepository.save(targetEntity);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.boardDeleteFail;
        }
    }

    /**
     * LocalDateTime 형식을 String으로 변환
     *
     * <p>
     *     yyyy-MM-dd HH:mm:ss 형식으로 변환
     * </p>
     *
     * @param time 변환 대상 시간
     * @return 변환 후 시간
     */
    private String LocalDateTimeToString(LocalDateTime time) {
        if(time == null) {
            return null;
        } else {
            return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
