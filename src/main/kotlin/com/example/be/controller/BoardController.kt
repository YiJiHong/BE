package com.example.be.controller

import com.example.be.dto.BoardDto
import com.example.be.dto.InsertBoardDto
import com.example.be.dto.UpdateBoardDto
import com.example.be.service.BoardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import lombok.RequiredArgsConstructor
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
class BoardController(val boardService: BoardService) {

    @Operation(summary = "특정 유저가 등록한 모든 글 검색")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "OK")
    )
    @GetMapping("/{userEmail}")
    fun getAllBoard(
        @PathVariable userEmail: String,
        @PageableDefault pageable: Pageable
    ): ResponseEntity<Page<BoardDto>> {
        return ResponseEntity(boardService.getAllBoard(pageable, userEmail), HttpStatus.OK)
    }

    @Operation(summary = "특정 글 검색")
    @GetMapping()
    fun getBoard(@RequestParam boardId: String): ResponseEntity<BoardDto> {
        return ResponseEntity(boardService.getBoard(boardId), HttpStatus.OK)
    }

    @PostMapping
    fun insertBoard(@RequestBody insertBoardDto: InsertBoardDto): ResponseEntity<Boolean> {
        return ResponseEntity(boardService.insertBoard(insertBoardDto), HttpStatus.OK)
    }

    @PutMapping
    fun updateBoard(@RequestBody updateBoardDto: UpdateBoardDto): ResponseEntity<Boolean> {
        return ResponseEntity(boardService.updateBoard(updateBoardDto), HttpStatus.OK)
    }

    @DeleteMapping("/{boardId}")
    fun deleteBoard(@PathVariable boardId: String): ResponseEntity<Boolean> {
        val deleteBoard = boardService.deleteBoard(boardId)

        return if (deleteBoard) {
            ResponseEntity(deleteBoard, HttpStatus.OK)
        } else {
            ResponseEntity(deleteBoard, HttpStatus.NOT_FOUND)
        }
    }
}
