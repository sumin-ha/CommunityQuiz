package com.callbuslab.community.service;

import com.callbuslab.community.constraint.ResponseMessage;
import com.callbuslab.community.constraint.ResultCode;
import com.callbuslab.community.domain.entity.*;
import com.callbuslab.community.web.dto.BoardCommentDto;
import com.callbuslab.community.web.dto.BoardDto;
import com.callbuslab.community.web.dto.BoardListDto;
import com.callbuslab.community.web.dto.BoardWriteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 글 관련 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;

    private final MemberService memberService;

    // 게시판 목록 불러오는 메서드
    public List<BoardListDto> getBoardList(Long memberId) {

        // 등록된 게시글 리스트 습득
        List<Board> boardList = boardRepository.findAllDesc();
        // 좋아요 여부를 확인하기 위한 좋아요 리스트 획득
        List<Long> favoriteList = favoriteRepository.findByMemberId(memberId)
                .stream()
                .map(Favorite::getBoardId)
                .collect(Collectors.toList());

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
        return resultList;
    }

    // 게시글 상세를 불러오는 메서드
    public BoardDto getBoard(Long boardId, Long memberId) {

        // 등록된 게시글 습득
        Board board = boardRepository.getById(boardId);

        // 좋아요 여부를 확인하기 위한 좋아요 테이블 탐색
        Favorite favorite = favoriteRepository.findByBoardIdAndMemberId(boardId, memberId);
        boolean favoriteFlag = false;
        // 좋아요 여부 확인
        if(favorite != null) {
            favoriteFlag = true;
        }

        // 글쓴이명 취득
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
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .deleteDate(board.getDeleteDate())
                .favoriteCount(board.getFavoriteCount())
                .writer(writer)
                .favoriteFlag(favoriteFlag)
                .favoriteCheckerList(favoriteCheckerList)
                .boardCommentList(boardCommentList)
                .build();

        return boardDto;
    }

    // 게시판의 글을 올리는 메서드
    public String registerBoard(BoardWriteDto dto, Long memberId) {

        Board board = Board.builder().title(dto.getTitle())
                .content(dto.getContent())
                .writerId(memberId)
                .favoriteCount(0)
                .delAble("1")
                .build();

        Board resultEntity = boardRepository.save(board);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.boardRegisterFail;
        }
    }

    // 게시판의 글을 수정하는 메서드
    public String updateBoard(BoardWriteDto dto, Long boardId, Long memberId) {

        // 글 등록 여부 확인
        Board checkEntity = boardRepository.getById(boardId);

        // 글 id로 습득이 되지 않거나, 해당 유저가 쓴 글이 아닐 경우 에러
        if(checkEntity == null || checkEntity.getWriterId() != memberId) {
            return ResponseMessage.authError;
        }

        // 글 수정용 객체 생성
        Board board = Board.builder().title(dto.getTitle())
                .content(dto.getContent())
                .build();
        board.setUpdateId(boardId);

        // 수정 등록
        Board resultEntity = boardRepository.save(board);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.boardUpdateFail;
        }
    }

    // 게시판의 글을 삭제하는 메서드
    public String deleteBoard(Long id, Long memberId) {

        // 글 등록 여부 확인
        Board checkEntity = boardRepository.getById(id);

        // 글 id로 습득이 되지 않거나, 해당 유저가 쓴 글이 아닐 경우 에러
        if(checkEntity == null || checkEntity.getWriterId() != memberId) {
            return ResponseMessage.authError;
        }

        // 글 수정용 객체 생성
        Board board = Board.builder().delAble("0")
                .build();
        board.setUpdateId(id);
        board.setDeleteDate(LocalDateTime.now());

        // 삭제 등록
        // 논리 삭제를 하기때문에, 삭제여부와 삭제시간을 업데이트
        Board resultEntity = boardRepository.save(board);

        // 결과 반환
        if(resultEntity != null) {
            return ResponseMessage.successMessage;
        } else {
            return ResponseMessage.boardDeleteFail;
        }
    }
}
