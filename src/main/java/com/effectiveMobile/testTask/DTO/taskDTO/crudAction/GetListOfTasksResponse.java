package com.effectiveMobile.testTask.DTO.taskDTO.crudAction;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ c списком задач")
public class GetListOfTasksResponse {

    @ArraySchema(schema = @Schema(implementation = TaskResponse.class))
    private List<TaskResponse> taskResponseList;

}
