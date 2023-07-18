package com.hanex.fms.starter.member.controller;

import com.hanex.fms.global.api.ApiResponseDto;
import com.hanex.fms.global.security.CustomUser;
import com.hanex.fms.starter.member.dto.MemberDto;
import com.hanex.fms.starter.member.dto.MemberSearchCondition;
import com.hanex.fms.starter.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Slf4j
@Tag(name = "사용자 (거래처,실행사,택배사) API",description = "사용자 (거래처,실행사,택배사) API")
@RequestMapping("/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "사용자 생성", description = "Customer (고객사) 가 사용자를 생성")
	@PostMapping
	public ApiResponseDto save (@Valid @RequestBody MemberDto.SaveRequest save
							, @AuthenticationPrincipal CustomUser securityUser){
		// CustomUser : JWT 에 포함된 user 정보로 생성된 객체
		return ApiResponseDto.createOK(memberService.save(save,securityUser));
	}


	@Operation(summary = "사용자 리스트 조회", description = "Customer (고객사) 가 사용자 리스트 조회")
	@GetMapping
	public ApiResponseDto findAll(@PageableDefault(page = 0, size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable
			 					,  @RequestParam MemberSearchCondition memberSearchCondition){
		return ApiResponseDto.createOK(memberService.findByCustomerIdAndSearchCondition(pageable,memberSearchCondition));
	}

	@Operation(summary = "사용자 단건 조회", description = "Customer (고객사) 가 사용자를 조회")
	@GetMapping("/{id}")
	public ApiResponseDto findById(@NotBlank @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$") @PathVariable String id){
		return ApiResponseDto.createOK(memberService.findById(id));
	}


	@Operation(summary = "사용자 수정", description = "Customer (고객사) 가 사용자를 수정")
	@PutMapping("/{id}")
	public ApiResponseDto update(@NotBlank @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$") @PathVariable String id,
								 @Valid @RequestBody MemberDto.UpdateRequest update
								, @AuthenticationPrincipal CustomUser securityUser){
		memberService.update(id, update,securityUser);
		return ApiResponseDto.DEFAULT_OK;
	}

	@Operation(summary = "사용자 삭제", description = "Customer (고객사) 가 사용자를 삭제")
	@DeleteMapping("/{id}")
	public ApiResponseDto delete(@NotBlank @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$") @PathVariable String id){
		memberService.delete(id);
		return ApiResponseDto.DEFAULT_OK;
	}

}
