package com.callbuslab.community.service;

import com.callbuslab.community.domain.entity.Board;
import com.callbuslab.community.domain.entity.BoardRepository;
import com.callbuslab.community.domain.entity.Favorite;
import com.callbuslab.community.domain.entity.FavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 좋아요 관련 서비스 클래스
 */
@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BoardRepository boardRepository;

    // 좋아요 누르기
    public int enableFavorite(Long boardId, Long memberId) {

        // 좋아요 눌렀는지 확인
        Favorite favorite = favoriteRepository.findByBoardIdAndMemberId(boardId, memberId);

        // 이미 같은 내용의 좋아요가 등록되었는지 확인.

        if(favorite == null) {
            // 등록 되지않은 상태라면 신규 등록함.
            Favorite registerEntity = Favorite.builder().boardId(boardId).memberId(memberId).build();
            favoriteRepository.save(registerEntity);

            // 해당 글에도 좋아요 카운트를 1 증가시킴.
            Board board = boardRepository.getById(boardId);
            int count = board.getFavoriteCount();
            board.setFavoriteCount(count+1);
            boardRepository.save(board);

            return ResultCode.SUCCESS.getValue();
        } else {
            // 존재하면 (좋아요는 1계정 1글 1회제한)에 따라 추가 등록하지 않음.
            return ResultCode.ERROR.getValue();
        }
    }
}
