package com.osckorea.openmsa.starter.pagination.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(
    in = ParameterIn.QUERY,
    name = "page",
    description = "페이지 번호에 대한 파라미터입니다.",
    schema = @Schema(
        type = "Integer", 
        minimum = "1"
    ),
    example = "1",
    required = false
)
@Parameter(
    in = ParameterIn.QUERY,
    name = "size",
    description = "페이지 당 크기에 대한 파라미터입니다.",
    schema = @Schema(
        type = "Integer",
        minimum = "1",
        maximum = "30"
    ),
    example = "10",
    required = false
)
@Parameter(
    in = ParameterIn.QUERY,
    name = "sort",
    description = "정렬 기준 및 정렬 방법을 설정하는 파라미터입니다."
    +"<br>  &middot;  정렬 기준은 Entity Class Object의 멤버 변수 이름으로 작성합니다."
    +"<br>  &middot;  정렬 방법은 asc(오름차순) 또는 desc(내림차순) 중 선택하여 기입합니다."
    +"<br>  &middot;  정렬 방법을 기입하지 않는 경우, 기본적으로 오름차순 정렬 되어있습니다.",
    array = @ArraySchema(schema = @Schema(type = "string")),
    example = "${Entity Class Object Mebmber Variable Name}, desc",
    required = true
)
public @interface PageableSchema {
    
}
